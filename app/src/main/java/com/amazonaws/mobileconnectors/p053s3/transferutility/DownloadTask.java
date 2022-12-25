package com.amazonaws.mobileconnectors.p053s3.transferutility;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.services.p054s3.AmazonS3;
import com.amazonaws.services.p054s3.model.GetObjectRequest;
import com.amazonaws.services.p054s3.model.S3Object;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.DownloadTask */
/* loaded from: classes2.dex */
public class DownloadTask implements Callable<Boolean> {
    private static final Log LOGGER = LogFactory.getLog(DownloadTask.class);
    private final TransferRecord download;

    /* renamed from: s3 */
    private final AmazonS3 f1173s3;
    private final TransferStatusUpdater updater;

    public DownloadTask(TransferRecord transferRecord, AmazonS3 amazonS3, TransferStatusUpdater transferStatusUpdater) {
        this.download = transferRecord;
        this.f1173s3 = amazonS3;
        this.updater = transferStatusUpdater;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    /* renamed from: call */
    public Boolean mo5820call() {
        try {
            if (TransferNetworkLossHandler.getInstance() != null && !TransferNetworkLossHandler.getInstance().isNetworkConnected()) {
                Log log = LOGGER;
                log.info("Thread:[" + Thread.currentThread().getId() + "]: Network wasn't available.");
                this.updater.updateState(this.download.f1174id, TransferState.WAITING_FOR_NETWORK);
                return false;
            }
        } catch (TransferUtilityException e) {
            Log log2 = LOGGER;
            log2.error("TransferUtilityException: [" + e + "]");
        }
        this.updater.updateState(this.download.f1174id, TransferState.IN_PROGRESS);
        ProgressListener newProgressListener = this.updater.newProgressListener(this.download.f1174id);
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(this.download.bucketName, this.download.key);
            TransferUtility.appendTransferServiceUserAgentString(getObjectRequest);
            File file = new File(this.download.file);
            long length = file.length();
            if (length > 0) {
                LOGGER.debug(String.format("Resume transfer %d from %d bytes", Integer.valueOf(this.download.f1174id), Long.valueOf(length)));
                getObjectRequest.setRange(length, -1L);
            }
            getObjectRequest.setGeneralProgressListener(newProgressListener);
            S3Object object = this.f1173s3.getObject(getObjectRequest);
            if (object == null) {
                this.updater.throwError(this.download.f1174id, new IllegalStateException("AmazonS3.getObject returns null"));
                this.updater.updateState(this.download.f1174id, TransferState.FAILED);
                return false;
            }
            long instanceLength = object.getObjectMetadata().getInstanceLength();
            this.updater.updateProgress(this.download.f1174id, length, instanceLength, true);
            saveToFile(object.getObjectContent(), file);
            this.updater.updateProgress(this.download.f1174id, instanceLength, instanceLength, true);
            this.updater.updateState(this.download.f1174id, TransferState.COMPLETED);
            return true;
        } catch (Exception e2) {
            if (TransferState.CANCELED.equals(this.download.state)) {
                Log log3 = LOGGER;
                log3.info("Transfer is " + this.download.state);
                return false;
            } else if (TransferState.PAUSED.equals(this.download.state)) {
                Log log4 = LOGGER;
                log4.info("Transfer is " + this.download.state);
                new ProgressEvent(0L).setEventCode(32);
                newProgressListener.progressChanged(new ProgressEvent(0L));
                return false;
            } else {
                if (RetryUtils.isInterrupted(e2)) {
                    try {
                        if (TransferNetworkLossHandler.getInstance() != null && !TransferNetworkLossHandler.getInstance().isNetworkConnected()) {
                            Log log5 = LOGGER;
                            log5.info("Thread:[" + Thread.currentThread().getId() + "]: Network wasn't available.");
                            this.updater.updateState(this.download.f1174id, TransferState.WAITING_FOR_NETWORK);
                            LOGGER.debug("Network Connection Interrupted: Moving the TransferState to WAITING_FOR_NETWORK");
                            new ProgressEvent(0L).setEventCode(32);
                            newProgressListener.progressChanged(new ProgressEvent(0L));
                            return false;
                        }
                    } catch (TransferUtilityException e3) {
                        Log log6 = LOGGER;
                        log6.error("TransferUtilityException: [" + e3 + "]");
                    }
                }
                Log log7 = LOGGER;
                log7.debug("Failed to download: " + this.download.f1174id + " due to " + e2.getMessage());
                this.updater.throwError(this.download.f1174id, e2);
                this.updater.updateState(this.download.f1174id, TransferState.FAILED);
                return false;
            }
        }
    }

    private void saveToFile(InputStream inputStream, File file) {
        BufferedOutputStream bufferedOutputStream;
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, file.length() > 0));
            } catch (Throwable th) {
                th = th;
            }
            try {
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        bufferedOutputStream.write(bArr, 0, read);
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            LOGGER.warn("got exception", e);
                        }
                    }
                }
                bufferedOutputStream.close();
                if (inputStream == null) {
                    return;
                }
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    LOGGER.warn("got exception", e2);
                }
            } catch (SocketTimeoutException e3) {
                e = e3;
                String str = "SocketTimeoutException: Unable to retrieve contents over network: " + e.getMessage();
                LOGGER.error(str);
                throw new AmazonClientException(str, e);
            } catch (IOException e4) {
                e = e4;
                throw new AmazonClientException("Unable to store object contents to disk: " + e.getMessage(), e);
            } catch (Throwable th2) {
                th = th2;
                bufferedOutputStream2 = bufferedOutputStream;
                if (bufferedOutputStream2 != null) {
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e5) {
                        LOGGER.warn("got exception", e5);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e6) {
                        LOGGER.warn("got exception", e6);
                    }
                }
                throw th;
            }
        } catch (SocketTimeoutException e7) {
            e = e7;
        } catch (IOException e8) {
            e = e8;
        }
    }
}
