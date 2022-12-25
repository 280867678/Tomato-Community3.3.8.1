package com.amazonaws.mobileconnectors.p053s3.transferutility;

import android.database.Cursor;
import android.net.ConnectivityManager;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.services.p054s3.AmazonS3;
import com.amazonaws.util.json.JsonUtils;
import com.google.gson.Gson;
import com.j256.ormlite.field.FieldType;
import java.util.Map;
import java.util.concurrent.Future;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferRecord */
/* loaded from: classes2.dex */
public class TransferRecord {
    private static final Log LOGGER = LogFactory.getLog(TransferRecord.class);
    public String bucketName;
    public long bytesCurrent;
    public long bytesTotal;
    public String cannedAcl;
    public String eTag;
    public String expirationTimeRuleId;
    public String file;
    public long fileOffset;
    private Gson gson = new Gson();
    public String headerCacheControl;
    public String headerContentDisposition;
    public String headerContentEncoding;
    public String headerContentType;
    public String headerStorageClass;
    public String httpExpires;

    /* renamed from: id */
    public int f1174id;
    public int isLastPart;
    public int isMultipart;
    public String key;
    public int mainUploadId;
    public String md5;
    public String multipartId;
    public int partNumber;
    public String sseAlgorithm;
    public String sseKMSKey;
    public TransferState state;
    private Future<?> submittedTask;
    public TransferUtilityOptions transferUtilityOptions;
    public TransferType type;
    public Map<String, String> userMetadata;

    public TransferRecord(int i) {
        this.f1174id = i;
    }

    public void updateFromDB(Cursor cursor) {
        this.f1174id = cursor.getInt(cursor.getColumnIndexOrThrow(FieldType.FOREIGN_ID_FIELD_SUFFIX));
        this.mainUploadId = cursor.getInt(cursor.getColumnIndexOrThrow("main_upload_id"));
        this.type = TransferType.getType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
        this.state = TransferState.getState(cursor.getString(cursor.getColumnIndexOrThrow("state")));
        this.bucketName = cursor.getString(cursor.getColumnIndexOrThrow("bucket_name"));
        this.key = cursor.getString(cursor.getColumnIndexOrThrow("key"));
        cursor.getString(cursor.getColumnIndexOrThrow("version_id"));
        this.bytesTotal = cursor.getLong(cursor.getColumnIndexOrThrow("bytes_total"));
        this.bytesCurrent = cursor.getLong(cursor.getColumnIndexOrThrow("bytes_current"));
        cursor.getLong(cursor.getColumnIndexOrThrow("speed"));
        cursor.getInt(cursor.getColumnIndexOrThrow("is_requester_pays"));
        this.isMultipart = cursor.getInt(cursor.getColumnIndexOrThrow("is_multipart"));
        this.isLastPart = cursor.getInt(cursor.getColumnIndexOrThrow("is_last_part"));
        cursor.getInt(cursor.getColumnIndexOrThrow("is_encrypted"));
        this.partNumber = cursor.getInt(cursor.getColumnIndexOrThrow("part_num"));
        this.eTag = cursor.getString(cursor.getColumnIndexOrThrow("etag"));
        this.file = cursor.getString(cursor.getColumnIndexOrThrow("file"));
        this.multipartId = cursor.getString(cursor.getColumnIndexOrThrow("multipart_id"));
        cursor.getLong(cursor.getColumnIndexOrThrow("range_start"));
        cursor.getLong(cursor.getColumnIndexOrThrow("range_last"));
        this.fileOffset = cursor.getLong(cursor.getColumnIndexOrThrow("file_offset"));
        this.headerContentType = cursor.getString(cursor.getColumnIndexOrThrow("header_content_type"));
        cursor.getString(cursor.getColumnIndexOrThrow("header_content_language"));
        this.headerContentDisposition = cursor.getString(cursor.getColumnIndexOrThrow("header_content_disposition"));
        this.headerContentEncoding = cursor.getString(cursor.getColumnIndexOrThrow("header_content_encoding"));
        this.headerCacheControl = cursor.getString(cursor.getColumnIndexOrThrow("header_cache_control"));
        cursor.getString(cursor.getColumnIndexOrThrow("header_expire"));
        this.userMetadata = JsonUtils.jsonToMap(cursor.getString(cursor.getColumnIndexOrThrow("user_metadata")));
        this.expirationTimeRuleId = cursor.getString(cursor.getColumnIndexOrThrow("expiration_time_rule_id"));
        this.httpExpires = cursor.getString(cursor.getColumnIndexOrThrow("http_expires_date"));
        this.sseAlgorithm = cursor.getString(cursor.getColumnIndexOrThrow("sse_algorithm"));
        this.sseKMSKey = cursor.getString(cursor.getColumnIndexOrThrow("kms_key"));
        this.md5 = cursor.getString(cursor.getColumnIndexOrThrow("content_md5"));
        this.cannedAcl = cursor.getString(cursor.getColumnIndexOrThrow("canned_acl"));
        this.headerStorageClass = cursor.getString(cursor.getColumnIndexOrThrow("header_storage_class"));
        this.transferUtilityOptions = (TransferUtilityOptions) this.gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow("transfer_utility_options")), (Class<Object>) TransferUtilityOptions.class);
    }

    public boolean start(AmazonS3 amazonS3, TransferDBUtil transferDBUtil, TransferStatusUpdater transferStatusUpdater, ConnectivityManager connectivityManager) {
        if (isRunning() || !checkIsReadyToRun() || !checkPreferredNetworkAvailability(transferStatusUpdater, connectivityManager)) {
            return false;
        }
        if (this.type.equals(TransferType.DOWNLOAD)) {
            this.submittedTask = TransferThreadPool.submitTask(new DownloadTask(this, amazonS3, transferStatusUpdater));
            return true;
        }
        this.submittedTask = TransferThreadPool.submitTask(new UploadTask(this, amazonS3, transferDBUtil, transferStatusUpdater));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean pauseIfRequiredForNetworkInterruption(AmazonS3 amazonS3, TransferStatusUpdater transferStatusUpdater, ConnectivityManager connectivityManager) {
        boolean checkPreferredNetworkAvailability = checkPreferredNetworkAvailability(transferStatusUpdater, connectivityManager);
        boolean z = false;
        if (!checkPreferredNetworkAvailability && !isFinalState(this.state)) {
            z = true;
            if (isRunning()) {
                this.submittedTask.cancel(true);
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isRunning() {
        Future<?> future = this.submittedTask;
        return future != null && !future.isDone();
    }

    private boolean isFinalState(TransferState transferState) {
        return TransferState.COMPLETED.equals(transferState) || TransferState.FAILED.equals(transferState) || TransferState.CANCELED.equals(transferState);
    }

    private boolean checkIsReadyToRun() {
        return this.partNumber == 0 && !TransferState.COMPLETED.equals(this.state);
    }

    public String toString() {
        return "[id:" + this.f1174id + ",bucketName:" + this.bucketName + ",key:" + this.key + ",file:" + this.file + ",type:" + this.type + ",bytesTotal:" + this.bytesTotal + ",bytesCurrent:" + this.bytesCurrent + ",fileOffset:" + this.fileOffset + ",state:" + this.state + ",cannedAcl:" + this.cannedAcl + ",mainUploadId:" + this.mainUploadId + ",isMultipart:" + this.isMultipart + ",isLastPart:" + this.isLastPart + ",partNumber:" + this.partNumber + ",multipartId:" + this.multipartId + ",eTag:" + this.eTag + ",storageClass:" + this.headerStorageClass + ",userMetadata:" + this.userMetadata.toString() + ",transferUtilityOptions:" + this.gson.toJson(this.transferUtilityOptions) + "]";
    }

    private boolean checkPreferredNetworkAvailability(TransferStatusUpdater transferStatusUpdater, ConnectivityManager connectivityManager) {
        TransferUtilityOptions transferUtilityOptions;
        if (connectivityManager == null || (transferUtilityOptions = this.transferUtilityOptions) == null || transferUtilityOptions.getTransferNetworkConnectionType().isConnected(connectivityManager)) {
            return true;
        }
        Log log = LOGGER;
        log.info("Network Connection " + this.transferUtilityOptions.getTransferNetworkConnectionType() + " is not available.");
        transferStatusUpdater.updateState(this.f1174id, TransferState.WAITING_FOR_NETWORK);
        return false;
    }
}
