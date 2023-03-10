package com.amazonaws.mobileconnectors.p053s3.transferutility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.services.p054s3.AmazonS3;
import com.j256.ormlite.field.FieldType;
import java.util.ArrayList;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler */
/* loaded from: classes2.dex */
public class TransferNetworkLossHandler extends BroadcastReceiver {
    private static final Log LOGGER = LogFactory.getLog(TransferNetworkLossHandler.class);
    private static TransferNetworkLossHandler transferNetworkLossHandler;
    final ConnectivityManager connManager;
    private TransferDBUtil dbUtil;
    TransferStatusUpdater updater;

    private TransferNetworkLossHandler(Context context) {
        this.connManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.dbUtil = new TransferDBUtil(context);
        this.updater = TransferStatusUpdater.getInstance(context);
    }

    public static synchronized TransferNetworkLossHandler getInstance(Context context) {
        TransferNetworkLossHandler transferNetworkLossHandler2;
        synchronized (TransferNetworkLossHandler.class) {
            if (transferNetworkLossHandler == null) {
                transferNetworkLossHandler = new TransferNetworkLossHandler(context);
            }
            transferNetworkLossHandler2 = transferNetworkLossHandler;
        }
        return transferNetworkLossHandler2;
    }

    public static synchronized TransferNetworkLossHandler getInstance() throws TransferUtilityException {
        TransferNetworkLossHandler transferNetworkLossHandler2;
        synchronized (TransferNetworkLossHandler.class) {
            if (transferNetworkLossHandler == null) {
                LOGGER.error("TransferNetworkLossHandler is not created. Please call `TransferNetworkLossHandler.getInstance(Context)` to instantiate it before retrieving");
                throw new TransferUtilityException("TransferNetworkLossHandler is not created. Please call `TransferNetworkLossHandler.getInstance(Context)` to instantiate it before retrieving");
            }
            transferNetworkLossHandler2 = transferNetworkLossHandler;
        }
        return transferNetworkLossHandler2;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            LOGGER.info("Network connectivity changed detected.");
            boolean isNetworkConnected = isNetworkConnected();
            Log log = LOGGER;
            log.info("Network connected: " + isNetworkConnected);
            new Thread(new Runnable() { // from class: com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TransferNetworkLossHandler.this.isNetworkConnected()) {
                        TransferNetworkLossHandler.this.resumeAllTransfersOnNetworkAvailability();
                    } else {
                        TransferNetworkLossHandler.this.pauseAllTransfersDueToNetworkInterruption();
                    }
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = this.connManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void resumeAllTransfersOnNetworkAvailability() {
        TransferRecord transfer;
        int i = 0;
        TransferState[] transferStateArr = {TransferState.WAITING_FOR_NETWORK};
        LOGGER.debug("Loading transfers from database...");
        ArrayList<Integer> arrayList = new ArrayList();
        Cursor queryTransfersWithTypeAndStates = this.dbUtil.queryTransfersWithTypeAndStates(TransferType.ANY, transferStateArr);
        while (queryTransfersWithTypeAndStates.moveToNext()) {
            int i2 = queryTransfersWithTypeAndStates.getInt(queryTransfersWithTypeAndStates.getColumnIndexOrThrow(FieldType.FOREIGN_ID_FIELD_SUFFIX));
            if (this.updater.getTransfer(i2) == null) {
                TransferRecord transferRecord = new TransferRecord(i2);
                transferRecord.updateFromDB(queryTransfersWithTypeAndStates);
                this.updater.addTransfer(transferRecord);
                i++;
            }
            arrayList.add(Integer.valueOf(i2));
        }
        if (queryTransfersWithTypeAndStates != null) {
            LOGGER.debug("Closing the cursor for resumeAllTransfers");
            queryTransfersWithTypeAndStates.close();
        }
        try {
            for (Integer num : arrayList) {
                AmazonS3 amazonS3 = S3ClientReference.get(num);
                if (amazonS3 != null && (transfer = this.updater.getTransfer(num.intValue())) != null && !transfer.isRunning()) {
                    transfer.start(amazonS3, this.dbUtil, this.updater, this.connManager);
                }
            }
        } catch (Exception e) {
            Log log = LOGGER;
            log.error("Error in resuming the transfers." + e.getMessage());
        }
        Log log2 = LOGGER;
        log2.debug(i + " transfers are loaded from database.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void pauseAllTransfersDueToNetworkInterruption() {
        for (TransferRecord transferRecord : this.updater.getTransfers().values()) {
            AmazonS3 amazonS3 = S3ClientReference.get(Integer.valueOf(transferRecord.f1174id));
            if (amazonS3 != null && transferRecord != null && transferRecord.pauseIfRequiredForNetworkInterruption(amazonS3, this.updater, this.connManager)) {
                this.updater.updateState(transferRecord.f1174id, TransferState.WAITING_FOR_NETWORK);
            }
        }
    }
}
