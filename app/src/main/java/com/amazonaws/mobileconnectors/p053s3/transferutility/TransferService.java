package com.amazonaws.mobileconnectors.p053s3.transferutility;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferService */
/* loaded from: classes2.dex */
public class TransferService extends Service {
    private static final Log LOGGER = LogFactory.getLog(TransferService.class);
    static TransferNetworkLossHandler transferNetworkLossHandler;
    boolean isReceiverNotRegistered = true;
    private int ongoingNotificationId = 0;
    private boolean removeNotification = true;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Can't bind to TransferService");
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LOGGER.info("Starting Transfer Service to listen for network connectivity changes.");
        transferNetworkLossHandler = TransferNetworkLossHandler.getInstance(getApplicationContext());
        synchronized (this) {
            if (this.isReceiverNotRegistered) {
                try {
                    LOGGER.info("Registering the network receiver");
                    registerReceiver(transferNetworkLossHandler, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                    this.isReceiverNotRegistered = false;
                } catch (IllegalArgumentException unused) {
                    LOGGER.warn("Ignoring the exception trying to register the receiver for connectivity change.");
                } catch (IllegalStateException unused2) {
                    LOGGER.warn("Ignoring the leak in registering the receiver.");
                }
            }
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                synchronized (this) {
                    Notification notification = (Notification) intent.getParcelableExtra("notification");
                    if (notification != null) {
                        this.ongoingNotificationId = intent.getIntExtra("ongoing-notification-id", this.ongoingNotificationId);
                        this.removeNotification = intent.getBooleanExtra("remove-notification", this.removeNotification);
                        LOGGER.info("Putting the service in Foreground state.");
                        startForeground(this.ongoingNotificationId, notification);
                    } else {
                        LOGGER.error("No notification is passed in the intent. Unable to transition to foreground.");
                    }
                }
            } catch (Exception e) {
                Log log = LOGGER;
                log.error("Error in moving the service to foreground state: " + e);
            }
        }
        synchronized (this) {
            if (this.isReceiverNotRegistered) {
                try {
                    LOGGER.info("Registering the network receiver");
                    registerReceiver(transferNetworkLossHandler, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                    this.isReceiverNotRegistered = false;
                } catch (IllegalArgumentException unused) {
                    LOGGER.warn("Ignoring the exception trying to register the receiver for connectivity change.");
                } catch (IllegalStateException unused2) {
                    LOGGER.warn("Ignoring the leak in registering the receiver.");
                }
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                LOGGER.info("Moving the service out of the Foreground state.");
                synchronized (this) {
                    stopForeground(this.removeNotification);
                }
            }
        } catch (Exception e) {
            Log log = LOGGER;
            log.error("Error in moving the service out of the foreground state: " + e);
        }
        try {
            LOGGER.info("De-registering the network receiver.");
            synchronized (this) {
                if (!this.isReceiverNotRegistered) {
                    unregisterReceiver(transferNetworkLossHandler);
                    this.isReceiverNotRegistered = true;
                    transferNetworkLossHandler = null;
                }
            }
        } catch (IllegalArgumentException unused) {
            LOGGER.warn("Exception trying to de-register the network receiver");
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if ((getApplicationInfo().flags & 2) == 0) {
            return;
        }
        printWriter.printf("network status: %s\n", Boolean.valueOf(transferNetworkLossHandler.isNetworkConnected()));
        Map<Integer, TransferRecord> transfers = TransferStatusUpdater.getInstance(this).getTransfers();
        printWriter.printf("# of active transfers: %d\n", Integer.valueOf(transfers.size()));
        for (TransferRecord transferRecord : transfers.values()) {
            printWriter.printf("bucket: %s, key: %s, status: %s, total size: %d, current: %d\n", transferRecord.bucketName, transferRecord.key, transferRecord.state, Long.valueOf(transferRecord.bytesTotal), Long.valueOf(transferRecord.bytesCurrent));
        }
        printWriter.flush();
    }
}
