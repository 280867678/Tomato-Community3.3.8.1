package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.p054s3.internal.ObjectExpirationResult;
import com.amazonaws.services.p054s3.internal.ObjectRestoreResult;
import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult;
import com.amazonaws.util.DateUtils;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/* renamed from: com.amazonaws.services.s3.model.ObjectMetadata */
/* loaded from: classes2.dex */
public class ObjectMetadata implements ServerSideEncryptionResult, S3RequesterChargedResult, ObjectExpirationResult, ObjectRestoreResult, Cloneable, Serializable {
    public static final String AES_256_SERVER_SIDE_ENCRYPTION = SSEAlgorithm.AES256.getAlgorithm();
    public static final String KMS_SERVER_SIDE_ENCRYPTION = SSEAlgorithm.KMS.getAlgorithm();
    private Date expirationTime;
    private String expirationTimeRuleId;
    private Date httpExpiresDate;
    private Map<String, Object> metadata;
    private Boolean ongoingRestore;
    private Date restoreExpirationTime;
    private Map<String, String> userMetadata;

    public ObjectMetadata() {
        this.userMetadata = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        this.metadata = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    }

    private ObjectMetadata(ObjectMetadata objectMetadata) {
        this.userMetadata = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        this.metadata = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Map<String, String> map = objectMetadata.userMetadata;
        TreeMap treeMap = null;
        this.userMetadata = map == null ? null : new TreeMap(map);
        Map<String, Object> map2 = objectMetadata.metadata;
        this.metadata = map2 != null ? new TreeMap(map2) : treeMap;
        this.expirationTime = DateUtils.cloneDate(objectMetadata.expirationTime);
        this.expirationTimeRuleId = objectMetadata.expirationTimeRuleId;
        this.httpExpiresDate = DateUtils.cloneDate(objectMetadata.httpExpiresDate);
        this.ongoingRestore = objectMetadata.ongoingRestore;
        this.restoreExpirationTime = DateUtils.cloneDate(objectMetadata.restoreExpirationTime);
    }

    public Map<String, String> getUserMetadata() {
        return this.userMetadata;
    }

    public void setUserMetadata(Map<String, String> map) {
        this.userMetadata = map;
    }

    public void setHeader(String str, Object obj) {
        this.metadata.put(str, obj);
    }

    public void addUserMetadata(String str, String str2) {
        this.userMetadata.put(str, str2);
    }

    public Map<String, Object> getRawMetadata() {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        treeMap.putAll(this.metadata);
        return Collections.unmodifiableMap(treeMap);
    }

    public Object getRawMetadataValue(String str) {
        return this.metadata.get(str);
    }

    public Date getLastModified() {
        return DateUtils.cloneDate((Date) this.metadata.get("Last-Modified"));
    }

    public void setLastModified(Date date) {
        this.metadata.put("Last-Modified", date);
    }

    public long getContentLength() {
        Long l = (Long) this.metadata.get("Content-Length");
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    public long getInstanceLength() {
        int lastIndexOf;
        String str = (String) this.metadata.get("Content-Range");
        if (str != null && (lastIndexOf = str.lastIndexOf("/")) >= 0) {
            return Long.parseLong(str.substring(lastIndexOf + 1));
        }
        return getContentLength();
    }

    public void setContentLength(long j) {
        this.metadata.put("Content-Length", Long.valueOf(j));
    }

    public String getContentType() {
        return (String) this.metadata.get("Content-Type");
    }

    public void setContentType(String str) {
        this.metadata.put("Content-Type", str);
    }

    public String getContentLanguage() {
        return (String) this.metadata.get("Content-Language");
    }

    public void setContentLanguage(String str) {
        this.metadata.put("Content-Language", str);
    }

    public String getContentEncoding() {
        return (String) this.metadata.get("Content-Encoding");
    }

    public void setContentEncoding(String str) {
        this.metadata.put("Content-Encoding", str);
    }

    public String getCacheControl() {
        return (String) this.metadata.get("Cache-Control");
    }

    public void setCacheControl(String str) {
        this.metadata.put("Cache-Control", str);
    }

    public void setContentMD5(String str) {
        if (str == null) {
            this.metadata.remove("Content-MD5");
        } else {
            this.metadata.put("Content-MD5", str);
        }
    }

    public String getContentMD5() {
        return (String) this.metadata.get("Content-MD5");
    }

    public void setContentDisposition(String str) {
        this.metadata.put("Content-Disposition", str);
    }

    public String getContentDisposition() {
        return (String) this.metadata.get("Content-Disposition");
    }

    public String getETag() {
        return (String) this.metadata.get("ETag");
    }

    public String getVersionId() {
        return (String) this.metadata.get("x-amz-version-id");
    }

    public String getSSEAlgorithm() {
        return (String) this.metadata.get("x-amz-server-side-encryption");
    }

    @Deprecated
    public String getServerSideEncryption() {
        return (String) this.metadata.get("x-amz-server-side-encryption");
    }

    @Override // com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult
    public void setSSEAlgorithm(String str) {
        this.metadata.put("x-amz-server-side-encryption", str);
    }

    @Deprecated
    public void setServerSideEncryption(String str) {
        this.metadata.put("x-amz-server-side-encryption", str);
    }

    public String getSSECustomerAlgorithm() {
        return (String) this.metadata.get("x-amz-server-side-encryption-customer-algorithm");
    }

    @Override // com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult
    public void setSSECustomerAlgorithm(String str) {
        this.metadata.put("x-amz-server-side-encryption-customer-algorithm", str);
    }

    public String getSSECustomerKeyMd5() {
        return (String) this.metadata.get("x-amz-server-side-encryption-customer-key-MD5");
    }

    @Override // com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult
    public void setSSECustomerKeyMd5(String str) {
        this.metadata.put("x-amz-server-side-encryption-customer-key-MD5", str);
    }

    public Date getExpirationTime() {
        return DateUtils.cloneDate(this.expirationTime);
    }

    @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
    public void setExpirationTime(Date date) {
        this.expirationTime = date;
    }

    public String getExpirationTimeRuleId() {
        return this.expirationTimeRuleId;
    }

    @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
    public void setExpirationTimeRuleId(String str) {
        this.expirationTimeRuleId = str;
    }

    public Date getRestoreExpirationTime() {
        return DateUtils.cloneDate(this.restoreExpirationTime);
    }

    @Override // com.amazonaws.services.p054s3.internal.ObjectRestoreResult
    public void setRestoreExpirationTime(Date date) {
        this.restoreExpirationTime = date;
    }

    @Override // com.amazonaws.services.p054s3.internal.ObjectRestoreResult
    public void setOngoingRestore(boolean z) {
        this.ongoingRestore = Boolean.valueOf(z);
    }

    public Boolean getOngoingRestore() {
        return this.ongoingRestore;
    }

    public void setHttpExpiresDate(Date date) {
        this.httpExpiresDate = date;
    }

    public Date getHttpExpiresDate() {
        return DateUtils.cloneDate(this.httpExpiresDate);
    }

    public void setStorageClass(StorageClass storageClass) {
        this.metadata.put("x-amz-storage-class", storageClass);
    }

    public String getStorageClass() {
        Object obj = this.metadata.get("x-amz-storage-class");
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public String getUserMetaDataOf(String str) {
        Map<String, String> map = this.userMetadata;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    /* renamed from: clone */
    public ObjectMetadata m5835clone() {
        return new ObjectMetadata(this);
    }

    public String getSSEAwsKmsKeyId() {
        return (String) this.metadata.get("x-amz-server-side-encryption-aws-kms-key-id");
    }

    public boolean isRequesterCharged() {
        return this.metadata.get("x-amz-request-charged") != null;
    }

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
        if (z) {
            this.metadata.put("x-amz-request-charged", "requester");
        }
    }

    public Integer getPartCount() {
        return (Integer) this.metadata.get("x-amz-mp-parts-count");
    }

    public Long[] getContentRange() {
        String str = (String) this.metadata.get("Content-Range");
        if (str != null) {
            String[] split = str.split("[ -/]+");
            try {
                return new Long[]{Long.valueOf(Long.parseLong(split[1])), Long.valueOf(Long.parseLong(split[2]))};
            } catch (NumberFormatException e) {
                throw new AmazonClientException("Unable to parse content range. Header 'Content-Range' has corrupted data" + e.getMessage(), e);
            }
        }
        return null;
    }

    public String getReplicationStatus() {
        return (String) this.metadata.get("x-amz-replication-status");
    }
}
