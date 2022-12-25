package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.event.ProgressListener;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.AbstractPutObjectRequest */
/* loaded from: classes2.dex */
public abstract class AbstractPutObjectRequest extends AmazonWebServiceRequest implements SSECustomerKeyProvider, SSEAwsKeyManagementParamsProvider, S3DataSource, Serializable {
    private AccessControlList accessControlList;
    private String bucketName;
    private CannedAccessControlList cannedAcl;
    private File file;
    private transient InputStream inputStream;
    private String key;
    private ObjectMetadata metadata;
    private String redirectLocation;
    private SSEAwsKeyManagementParams sseAwsKeyManagementParams;
    private SSECustomerKey sseCustomerKey;
    private String storageClass;
    private ObjectTagging tagging;

    public AbstractPutObjectRequest(String str, String str2, File file) {
        this.bucketName = str;
        this.key = str2;
        this.file = file;
    }

    public AbstractPutObjectRequest(String str, String str2, String str3) {
        this.bucketName = str;
        this.key = str2;
        this.redirectLocation = str3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractPutObjectRequest(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata) {
        this.bucketName = str;
        this.key = str2;
        this.inputStream = inputStream;
        this.metadata = objectMetadata;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withBucketName */
    public <T extends AbstractPutObjectRequest> T mo5839withBucketName(String str) {
        setBucketName(str);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withKey */
    public <T extends AbstractPutObjectRequest> T mo5844withKey(String str) {
        setKey(str);
        return this;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String str) {
        this.storageClass = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withStorageClass */
    public <T extends AbstractPutObjectRequest> T mo5851withStorageClass(String str) {
        setStorageClass(str);
        return this;
    }

    public void setStorageClass(StorageClass storageClass) {
        this.storageClass = storageClass.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withStorageClass */
    public <T extends AbstractPutObjectRequest> T mo5850withStorageClass(StorageClass storageClass) {
        setStorageClass(storageClass);
        return this;
    }

    public File getFile() {
        return this.file;
    }

    @Override // com.amazonaws.services.p054s3.model.S3DataSource
    public void setFile(File file) {
        this.file = file;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withFile */
    public <T extends AbstractPutObjectRequest> T mo5841withFile(File file) {
        setFile(file);
        return this;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withMetadata */
    public <T extends AbstractPutObjectRequest> T mo5845withMetadata(ObjectMetadata objectMetadata) {
        setMetadata(objectMetadata);
        return this;
    }

    public CannedAccessControlList getCannedAcl() {
        return this.cannedAcl;
    }

    public void setCannedAcl(CannedAccessControlList cannedAccessControlList) {
        this.cannedAcl = cannedAccessControlList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withCannedAcl */
    public <T extends AbstractPutObjectRequest> T mo5840withCannedAcl(CannedAccessControlList cannedAccessControlList) {
        setCannedAcl(cannedAccessControlList);
        return this;
    }

    public AccessControlList getAccessControlList() {
        return this.accessControlList;
    }

    public void setAccessControlList(AccessControlList accessControlList) {
        this.accessControlList = accessControlList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withAccessControlList */
    public <T extends AbstractPutObjectRequest> T mo5838withAccessControlList(AccessControlList accessControlList) {
        setAccessControlList(accessControlList);
        return this;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override // com.amazonaws.services.p054s3.model.S3DataSource
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withInputStream */
    public <T extends AbstractPutObjectRequest> T mo5843withInputStream(InputStream inputStream) {
        setInputStream(inputStream);
        return this;
    }

    public void setRedirectLocation(String str) {
        this.redirectLocation = str;
    }

    public String getRedirectLocation() {
        return this.redirectLocation;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withRedirectLocation */
    public <T extends AbstractPutObjectRequest> T mo5847withRedirectLocation(String str) {
        this.redirectLocation = str;
        return this;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sSECustomerKey) {
        if (sSECustomerKey != null && this.sseAwsKeyManagementParams != null) {
            throw new IllegalArgumentException("Either SSECustomerKey or SSEAwsKeyManagementParams must not be set at the same time.");
        }
        this.sseCustomerKey = sSECustomerKey;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withSSECustomerKey */
    public <T extends AbstractPutObjectRequest> T mo5849withSSECustomerKey(SSECustomerKey sSECustomerKey) {
        setSSECustomerKey(sSECustomerKey);
        return this;
    }

    public ObjectTagging getTagging() {
        return this.tagging;
    }

    public void setTagging(ObjectTagging objectTagging) {
        this.tagging = objectTagging;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withTagging */
    public <T extends AbstractPutObjectRequest> T mo5852withTagging(ObjectTagging objectTagging) {
        setTagging(objectTagging);
        return this;
    }

    @Deprecated
    public void setProgressListener(ProgressListener progressListener) {
        setGeneralProgressListener(new LegacyS3ProgressListener(progressListener));
    }

    @Deprecated
    public ProgressListener getProgressListener() {
        ProgressListener generalProgressListener = getGeneralProgressListener();
        if (generalProgressListener instanceof LegacyS3ProgressListener) {
            return ((LegacyS3ProgressListener) generalProgressListener).unwrap();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    /* renamed from: withProgressListener */
    public <T extends AbstractPutObjectRequest> T mo5846withProgressListener(ProgressListener progressListener) {
        setProgressListener(progressListener);
        return this;
    }

    public SSEAwsKeyManagementParams getSSEAwsKeyManagementParams() {
        return this.sseAwsKeyManagementParams;
    }

    public void setSSEAwsKeyManagementParams(SSEAwsKeyManagementParams sSEAwsKeyManagementParams) {
        if (sSEAwsKeyManagementParams != null && this.sseCustomerKey != null) {
            throw new IllegalArgumentException("Either SSECustomerKey or SSEAwsKeyManagementParams must not be set at the same time.");
        }
        this.sseAwsKeyManagementParams = sSEAwsKeyManagementParams;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: withSSEAwsKeyManagementParams */
    public <T extends AbstractPutObjectRequest> T mo5848withSSEAwsKeyManagementParams(SSEAwsKeyManagementParams sSEAwsKeyManagementParams) {
        setSSEAwsKeyManagementParams(sSEAwsKeyManagementParams);
        return this;
    }

    @Override // com.amazonaws.AmazonWebServiceRequest
    /* renamed from: clone  reason: collision with other method in class */
    public AbstractPutObjectRequest mo5854clone() {
        return (AbstractPutObjectRequest) super.m5819clone();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <T extends AbstractPutObjectRequest> T copyPutObjectBaseTo(T t) {
        copyBaseTo(t);
        ObjectMetadata metadata = getMetadata();
        return (T) t.mo5838withAccessControlList(getAccessControlList()).mo5840withCannedAcl(getCannedAcl()).mo5843withInputStream(getInputStream()).mo5845withMetadata(metadata == null ? null : metadata.m5835clone()).mo5847withRedirectLocation(getRedirectLocation()).mo5851withStorageClass(getStorageClass()).mo5848withSSEAwsKeyManagementParams(getSSEAwsKeyManagementParams()).mo5849withSSECustomerKey(getSSECustomerKey());
    }
}
