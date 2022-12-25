package com.amazonaws.services.p054s3.model;

import com.amazonaws.event.ProgressListener;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.PutObjectRequest */
/* loaded from: classes2.dex */
public class PutObjectRequest extends AbstractPutObjectRequest implements Serializable {
    private boolean isRequesterPays;

    public PutObjectRequest(String str, String str2, File file) {
        super(str, str2, file);
    }

    public PutObjectRequest(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    public PutObjectRequest(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata) {
        super(str, str2, inputStream, objectMetadata);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest, com.amazonaws.AmazonWebServiceRequest
    /* renamed from: clone */
    public PutObjectRequest mo5854clone() {
        return (PutObjectRequest) copyPutObjectBaseTo((PutObjectRequest) super.mo5854clone());
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withBucketName  reason: collision with other method in class */
    public PutObjectRequest mo5839withBucketName(String str) {
        return (PutObjectRequest) super.mo5839withBucketName(str);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withKey  reason: collision with other method in class */
    public PutObjectRequest mo5844withKey(String str) {
        return (PutObjectRequest) super.mo5844withKey(str);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withStorageClass  reason: collision with other method in class */
    public PutObjectRequest mo5851withStorageClass(String str) {
        return (PutObjectRequest) super.mo5851withStorageClass(str);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withStorageClass  reason: collision with other method in class */
    public PutObjectRequest mo5850withStorageClass(StorageClass storageClass) {
        return (PutObjectRequest) super.mo5850withStorageClass(storageClass);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withFile  reason: collision with other method in class */
    public PutObjectRequest mo5841withFile(File file) {
        return (PutObjectRequest) super.mo5841withFile(file);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withMetadata  reason: collision with other method in class */
    public PutObjectRequest mo5845withMetadata(ObjectMetadata objectMetadata) {
        return (PutObjectRequest) super.mo5845withMetadata(objectMetadata);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withCannedAcl  reason: collision with other method in class */
    public PutObjectRequest mo5840withCannedAcl(CannedAccessControlList cannedAccessControlList) {
        return (PutObjectRequest) super.mo5840withCannedAcl(cannedAccessControlList);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withAccessControlList  reason: collision with other method in class */
    public PutObjectRequest mo5838withAccessControlList(AccessControlList accessControlList) {
        return (PutObjectRequest) super.mo5838withAccessControlList(accessControlList);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withInputStream  reason: collision with other method in class */
    public PutObjectRequest mo5843withInputStream(InputStream inputStream) {
        return (PutObjectRequest) super.mo5843withInputStream(inputStream);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withRedirectLocation  reason: collision with other method in class */
    public PutObjectRequest mo5847withRedirectLocation(String str) {
        return (PutObjectRequest) super.mo5847withRedirectLocation(str);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withSSECustomerKey  reason: collision with other method in class */
    public PutObjectRequest mo5849withSSECustomerKey(SSECustomerKey sSECustomerKey) {
        return (PutObjectRequest) super.mo5849withSSECustomerKey(sSECustomerKey);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withTagging  reason: collision with other method in class */
    public PutObjectRequest mo5852withTagging(ObjectTagging objectTagging) {
        super.setTagging(objectTagging);
        return this;
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    @Deprecated
    /* renamed from: withProgressListener  reason: collision with other method in class */
    public PutObjectRequest mo5846withProgressListener(ProgressListener progressListener) {
        return (PutObjectRequest) super.mo5846withProgressListener(progressListener);
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest
    /* renamed from: withSSEAwsKeyManagementParams  reason: collision with other method in class */
    public PutObjectRequest mo5848withSSEAwsKeyManagementParams(SSEAwsKeyManagementParams sSEAwsKeyManagementParams) {
        return (PutObjectRequest) super.mo5848withSSEAwsKeyManagementParams(sSEAwsKeyManagementParams);
    }

    @Override // com.amazonaws.AmazonWebServiceRequest
    /* renamed from: withGeneralProgressListener  reason: collision with other method in class */
    public PutObjectRequest mo5842withGeneralProgressListener(ProgressListener progressListener) {
        return (PutObjectRequest) super.mo5842withGeneralProgressListener(progressListener);
    }

    public boolean isRequesterPays() {
        return this.isRequesterPays;
    }

    public void setRequesterPays(boolean z) {
        this.isRequesterPays = z;
    }

    public PutObjectRequest withRequesterPays(boolean z) {
        setRequesterPays(z);
        return this;
    }
}
