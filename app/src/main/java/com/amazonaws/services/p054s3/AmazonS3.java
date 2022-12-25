package com.amazonaws.services.p054s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.p054s3.internal.S3DirectSpi;
import com.amazonaws.services.p054s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.p054s3.model.GetObjectRequest;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.p054s3.model.PutObjectRequest;
import com.amazonaws.services.p054s3.model.PutObjectResult;
import com.amazonaws.services.p054s3.model.S3Object;
import com.amazonaws.services.p054s3.model.UploadPartRequest;
import com.amazonaws.services.p054s3.model.UploadPartResult;

/* renamed from: com.amazonaws.services.s3.AmazonS3 */
/* loaded from: classes2.dex */
public interface AmazonS3 extends S3DirectSpi {
    void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws AmazonClientException, AmazonServiceException;

    CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws AmazonClientException, AmazonServiceException;

    S3Object getObject(GetObjectRequest getObjectRequest) throws AmazonClientException, AmazonServiceException;

    InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws AmazonClientException, AmazonServiceException;

    PutObjectResult putObject(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException;

    void setEndpoint(String str);

    void setRegion(Region region) throws IllegalArgumentException;

    void setS3ClientOptions(S3ClientOptions s3ClientOptions);

    UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws AmazonClientException, AmazonServiceException;
}
