package com.amazonaws.mobileconnectors.p053s3.transferutility;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.services.p054s3.AmazonS3;
import com.amazonaws.services.p054s3.model.CannedAccessControlList;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.ObjectMetadata;
import com.amazonaws.services.p054s3.model.ObjectTagging;
import com.amazonaws.services.p054s3.model.PutObjectRequest;
import com.amazonaws.services.p054s3.model.SSEAwsKeyManagementParams;
import com.amazonaws.services.p054s3.model.Tag;
import com.amazonaws.services.p054s3.model.UploadPartRequest;
import com.amazonaws.services.p054s3.util.Mimetypes;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.UploadTask */
/* loaded from: classes2.dex */
public class UploadTask implements Callable<Boolean> {
    private final TransferDBUtil dbUtil;
    private List<UploadPartRequest> requestList;

    /* renamed from: s3 */
    private final AmazonS3 f1176s3;
    private final TransferStatusUpdater updater;
    private final TransferRecord upload;
    Map<Integer, UploadPartTaskMetadata> uploadPartTasks = new HashMap();
    private static final Log LOGGER = LogFactory.getLog(UploadTask.class);
    private static final Map<String, CannedAccessControlList> CANNED_ACL_MAP = new HashMap();

    static {
        CannedAccessControlList[] values;
        for (CannedAccessControlList cannedAccessControlList : CannedAccessControlList.values()) {
            CANNED_ACL_MAP.put(cannedAccessControlList.toString(), cannedAccessControlList);
        }
    }

    public UploadTask(TransferRecord transferRecord, AmazonS3 amazonS3, TransferDBUtil transferDBUtil, TransferStatusUpdater transferStatusUpdater) {
        this.upload = transferRecord;
        this.f1176s3 = amazonS3;
        this.dbUtil = transferDBUtil;
        this.updater = transferStatusUpdater;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    /* renamed from: call */
    public Boolean mo5822call() throws Exception {
        try {
            if (TransferNetworkLossHandler.getInstance() != null && !TransferNetworkLossHandler.getInstance().isNetworkConnected()) {
                LOGGER.info("Network not connected. Setting the state to WAITING_FOR_NETWORK.");
                this.updater.updateState(this.upload.f1174id, TransferState.WAITING_FOR_NETWORK);
                return false;
            }
        } catch (TransferUtilityException e) {
            Log log = LOGGER;
            log.error("TransferUtilityException: [" + e + "]");
        }
        this.updater.updateState(this.upload.f1174id, TransferState.IN_PROGRESS);
        TransferRecord transferRecord = this.upload;
        if (transferRecord.isMultipart == 1 && transferRecord.partNumber == 0) {
            return uploadMultipartAndWaitForCompletion();
        }
        if (this.upload.isMultipart == 0) {
            return uploadSinglePartAndWaitForCompletion();
        }
        return false;
    }

    private Boolean uploadMultipartAndWaitForCompletion() throws ExecutionException {
        long j;
        String str = this.upload.multipartId;
        if (str == null || str.isEmpty()) {
            PutObjectRequest createPutObjectRequest = createPutObjectRequest(this.upload);
            TransferUtility.appendMultipartTransferServiceUserAgentString(createPutObjectRequest);
            try {
                this.upload.multipartId = initiateMultipartUpload(createPutObjectRequest);
                TransferDBUtil transferDBUtil = this.dbUtil;
                TransferRecord transferRecord = this.upload;
                transferDBUtil.updateMultipartId(transferRecord.f1174id, transferRecord.multipartId);
                j = 0;
            } catch (AmazonClientException e) {
                LOGGER.error("Error initiating multipart upload: " + this.upload.f1174id + " due to " + e.getMessage(), e);
                this.updater.throwError(this.upload.f1174id, e);
                this.updater.updateState(this.upload.f1174id, TransferState.FAILED);
                return false;
            }
        } else {
            long queryBytesTransferredByMainUploadId = this.dbUtil.queryBytesTransferredByMainUploadId(this.upload.f1174id);
            if (queryBytesTransferredByMainUploadId > 0) {
                LOGGER.info(String.format("Resume transfer %d from %d bytes", Integer.valueOf(this.upload.f1174id), Long.valueOf(queryBytesTransferredByMainUploadId)));
            }
            j = queryBytesTransferredByMainUploadId;
        }
        UploadTaskProgressListener uploadTaskProgressListener = new UploadTaskProgressListener(this.upload);
        TransferStatusUpdater transferStatusUpdater = this.updater;
        TransferRecord transferRecord2 = this.upload;
        transferStatusUpdater.updateProgress(transferRecord2.f1174id, j, transferRecord2.bytesTotal, false);
        TransferDBUtil transferDBUtil2 = this.dbUtil;
        TransferRecord transferRecord3 = this.upload;
        this.requestList = transferDBUtil2.getNonCompletedPartRequestsFromDB(transferRecord3.f1174id, transferRecord3.multipartId);
        LOGGER.info("Multipart upload " + this.upload.f1174id + " in " + this.requestList.size() + " parts.");
        for (UploadPartRequest uploadPartRequest : this.requestList) {
            TransferUtility.appendMultipartTransferServiceUserAgentString(uploadPartRequest);
            UploadPartTaskMetadata uploadPartTaskMetadata = new UploadPartTaskMetadata(this);
            uploadPartTaskMetadata.bytesTransferredSoFar = 0L;
            uploadPartTaskMetadata.state = TransferState.WAITING;
            this.uploadPartTasks.put(Integer.valueOf(uploadPartRequest.getPartNumber()), uploadPartTaskMetadata);
            uploadPartTaskMetadata.uploadPartTask = TransferThreadPool.submitTask(new UploadPartTask(uploadPartTaskMetadata, uploadTaskProgressListener, uploadPartRequest, this.f1176s3, this.dbUtil));
        }
        try {
            boolean z = true;
            for (UploadPartTaskMetadata uploadPartTaskMetadata2 : this.uploadPartTasks.values()) {
                z &= uploadPartTaskMetadata2.uploadPartTask.get().booleanValue();
            }
            if (!z) {
                if (this.dbUtil.checkWaitingForNetworkPartRequestsFromDB(this.upload.f1174id)) {
                    LOGGER.info("Network Connection Interrupted: Transfer " + this.upload.f1174id + " waits for network");
                    this.updater.updateState(this.upload.f1174id, TransferState.WAITING_FOR_NETWORK);
                }
                return false;
            }
            LOGGER.info("Completing the multi-part upload transfer for " + this.upload.f1174id);
            try {
                completeMultiPartUpload(this.upload.f1174id, this.upload.bucketName, this.upload.key, this.upload.multipartId);
                this.updater.updateProgress(this.upload.f1174id, this.upload.bytesTotal, this.upload.bytesTotal, true);
                this.updater.updateState(this.upload.f1174id, TransferState.COMPLETED);
                return true;
            } catch (AmazonClientException e2) {
                LOGGER.error("Failed to complete multipart: " + this.upload.f1174id + " due to " + e2.getMessage(), e2);
                this.updater.throwError(this.upload.f1174id, e2);
                this.updater.updateState(this.upload.f1174id, TransferState.FAILED);
                return false;
            }
        } catch (Exception e3) {
            LOGGER.error("Upload resulted in an exception. " + e3);
            for (UploadPartTaskMetadata uploadPartTaskMetadata3 : this.uploadPartTasks.values()) {
                uploadPartTaskMetadata3.uploadPartTask.cancel(true);
            }
            if (TransferState.CANCELED.equals(this.upload.state) || TransferState.PAUSED.equals(this.upload.state)) {
                LOGGER.info("Transfer is " + this.upload.state);
                return false;
            }
            for (UploadPartTaskMetadata uploadPartTaskMetadata4 : this.uploadPartTasks.values()) {
                if (TransferState.WAITING_FOR_NETWORK.equals(uploadPartTaskMetadata4.state)) {
                    LOGGER.info("Individual part is WAITING_FOR_NETWORK.");
                    this.updater.updateState(this.upload.f1174id, TransferState.WAITING_FOR_NETWORK);
                    return false;
                }
            }
            if (RetryUtils.isInterrupted(e3)) {
                LOGGER.info("Transfer is interrupted. " + e3);
                return false;
            }
            LOGGER.error("Error encountered during multi-part upload: " + this.upload.f1174id + " due to " + e3.getMessage(), e3);
            this.updater.throwError(this.upload.f1174id, e3);
            this.updater.updateState(this.upload.f1174id, TransferState.FAILED);
            return false;
        }
    }

    private Boolean uploadSinglePartAndWaitForCompletion() {
        PutObjectRequest createPutObjectRequest = createPutObjectRequest(this.upload);
        ProgressListener newProgressListener = this.updater.newProgressListener(this.upload.f1174id);
        long length = createPutObjectRequest.getFile().length();
        TransferUtility.appendTransferServiceUserAgentString(createPutObjectRequest);
        createPutObjectRequest.setGeneralProgressListener(newProgressListener);
        try {
            this.f1176s3.putObject(createPutObjectRequest);
            this.updater.updateProgress(this.upload.f1174id, length, length, true);
            this.updater.updateState(this.upload.f1174id, TransferState.COMPLETED);
            return true;
        } catch (Exception e) {
            if (TransferState.CANCELED.equals(this.upload.state)) {
                Log log = LOGGER;
                log.info("Transfer is " + this.upload.state);
                return false;
            } else if (TransferState.PAUSED.equals(this.upload.state)) {
                Log log2 = LOGGER;
                log2.info("Transfer is " + this.upload.state);
                new ProgressEvent(0L).setEventCode(32);
                newProgressListener.progressChanged(new ProgressEvent(0L));
                return false;
            } else {
                if (RetryUtils.isInterrupted(e)) {
                    try {
                        if (TransferNetworkLossHandler.getInstance() != null && !TransferNetworkLossHandler.getInstance().isNetworkConnected()) {
                            Log log3 = LOGGER;
                            log3.info("Thread:[" + Thread.currentThread().getId() + "]: Network wasn't available.");
                            this.updater.updateState(this.upload.f1174id, TransferState.WAITING_FOR_NETWORK);
                            LOGGER.debug("Network Connection Interrupted: Moving the TransferState to WAITING_FOR_NETWORK");
                            new ProgressEvent(0L).setEventCode(32);
                            newProgressListener.progressChanged(new ProgressEvent(0L));
                            return false;
                        }
                    } catch (TransferUtilityException e2) {
                        Log log4 = LOGGER;
                        log4.error("TransferUtilityException: [" + e2 + "]");
                    }
                }
                Log log5 = LOGGER;
                log5.debug("Failed to upload: " + this.upload.f1174id + " due to " + e.getMessage());
                this.updater.throwError(this.upload.f1174id, e);
                this.updater.updateState(this.upload.f1174id, TransferState.FAILED);
                return false;
            }
        }
    }

    private void completeMultiPartUpload(int i, String str, String str2, String str3) {
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(str, str2, str3, this.dbUtil.queryPartETagsOfUpload(i));
        TransferUtility.appendMultipartTransferServiceUserAgentString(completeMultipartUploadRequest);
        this.f1176s3.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private String initiateMultipartUpload(PutObjectRequest putObjectRequest) {
        InitiateMultipartUploadRequest withSSEAwsKeyManagementParams = new InitiateMultipartUploadRequest(putObjectRequest.getBucketName(), putObjectRequest.getKey()).withCannedACL(putObjectRequest.getCannedAcl()).withObjectMetadata(putObjectRequest.getMetadata()).withSSEAwsKeyManagementParams(putObjectRequest.getSSEAwsKeyManagementParams());
        TransferUtility.appendMultipartTransferServiceUserAgentString(withSSEAwsKeyManagementParams);
        return this.f1176s3.initiateMultipartUpload(withSSEAwsKeyManagementParams).getUploadId();
    }

    private PutObjectRequest createPutObjectRequest(TransferRecord transferRecord) {
        File file = new File(transferRecord.file);
        PutObjectRequest putObjectRequest = new PutObjectRequest(transferRecord.bucketName, transferRecord.key, file);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.length());
        String str = transferRecord.headerCacheControl;
        if (str != null) {
            objectMetadata.setCacheControl(str);
        }
        String str2 = transferRecord.headerContentDisposition;
        if (str2 != null) {
            objectMetadata.setContentDisposition(str2);
        }
        String str3 = transferRecord.headerContentEncoding;
        if (str3 != null) {
            objectMetadata.setContentEncoding(str3);
        }
        String str4 = transferRecord.headerContentType;
        if (str4 != null) {
            objectMetadata.setContentType(str4);
        } else {
            objectMetadata.setContentType(Mimetypes.getInstance().getMimetype(file));
        }
        String str5 = transferRecord.headerStorageClass;
        if (str5 != null) {
            putObjectRequest.setStorageClass(str5);
        }
        String str6 = transferRecord.expirationTimeRuleId;
        if (str6 != null) {
            objectMetadata.setExpirationTimeRuleId(str6);
        }
        String str7 = transferRecord.httpExpires;
        if (str7 != null) {
            objectMetadata.setHttpExpiresDate(new Date(Long.valueOf(str7).longValue()));
        }
        String str8 = transferRecord.sseAlgorithm;
        if (str8 != null) {
            objectMetadata.setSSEAlgorithm(str8);
        }
        Map<String, String> map = transferRecord.userMetadata;
        if (map != null) {
            objectMetadata.setUserMetadata(map);
            String str9 = transferRecord.userMetadata.get("x-amz-tagging");
            if (str9 != null) {
                try {
                    String[] split = str9.split("&");
                    ArrayList arrayList = new ArrayList();
                    for (String str10 : split) {
                        String[] split2 = str10.split(SimpleComparison.EQUAL_TO_OPERATION);
                        arrayList.add(new Tag(split2[0], split2[1]));
                    }
                    putObjectRequest.setTagging(new ObjectTagging(arrayList));
                } catch (Exception e) {
                    LOGGER.error("Error in passing the object tags as request headers.", e);
                }
            }
            String str11 = transferRecord.userMetadata.get("x-amz-website-redirect-location");
            if (str11 != null) {
                putObjectRequest.setRedirectLocation(str11);
            }
            String str12 = transferRecord.userMetadata.get("x-amz-request-payer");
            if (str12 != null) {
                putObjectRequest.setRequesterPays("requester".equals(str12));
            }
        }
        String str13 = transferRecord.md5;
        if (str13 != null) {
            objectMetadata.setContentMD5(str13);
        }
        String str14 = transferRecord.sseKMSKey;
        if (str14 != null) {
            putObjectRequest.setSSEAwsKeyManagementParams(new SSEAwsKeyManagementParams(str14));
        }
        putObjectRequest.setMetadata(objectMetadata);
        putObjectRequest.setCannedAcl(getCannedAclFromString(transferRecord.cannedAcl));
        return putObjectRequest;
    }

    private static CannedAccessControlList getCannedAclFromString(String str) {
        if (str == null) {
            return null;
        }
        return CANNED_ACL_MAP.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.UploadTask$UploadTaskProgressListener */
    /* loaded from: classes2.dex */
    public class UploadTaskProgressListener implements ProgressListener {
        private long prevTotalBytesTransferredOfAllParts;

        @Override // com.amazonaws.event.ProgressListener
        public void progressChanged(ProgressEvent progressEvent) {
        }

        UploadTaskProgressListener(TransferRecord transferRecord) {
            this.prevTotalBytesTransferredOfAllParts = transferRecord.bytesCurrent;
        }

        public synchronized void onProgressChanged(int i, long j) {
            UploadPartTaskMetadata uploadPartTaskMetadata = UploadTask.this.uploadPartTasks.get(Integer.valueOf(i));
            if (uploadPartTaskMetadata == null) {
                UploadTask.LOGGER.info("Update received for unknown part. Ignoring.");
                return;
            }
            uploadPartTaskMetadata.bytesTransferredSoFar = j;
            long j2 = 0;
            for (Map.Entry<Integer, UploadPartTaskMetadata> entry : UploadTask.this.uploadPartTasks.entrySet()) {
                j2 += entry.getValue().bytesTransferredSoFar;
            }
            if (j2 > this.prevTotalBytesTransferredOfAllParts) {
                UploadTask.this.updater.updateProgress(UploadTask.this.upload.f1174id, j2, UploadTask.this.upload.bytesTotal, true);
                this.prevTotalBytesTransferredOfAllParts = j2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.UploadTask$UploadPartTaskMetadata */
    /* loaded from: classes2.dex */
    public class UploadPartTaskMetadata {
        long bytesTransferredSoFar;
        TransferState state;
        Future<Boolean> uploadPartTask;

        UploadPartTaskMetadata(UploadTask uploadTask) {
        }
    }
}
