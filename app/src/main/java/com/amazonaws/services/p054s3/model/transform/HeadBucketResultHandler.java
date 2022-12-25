package com.amazonaws.services.p054s3.model.transform;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.p054s3.internal.AbstractS3ResponseHandler;
import com.amazonaws.services.p054s3.model.HeadBucketResult;

/* renamed from: com.amazonaws.services.s3.model.transform.HeadBucketResultHandler */
/* loaded from: classes2.dex */
public class HeadBucketResultHandler extends AbstractS3ResponseHandler<HeadBucketResult> {
    @Override // com.amazonaws.http.HttpResponseHandler
    /* renamed from: handle */
    public AmazonWebServiceResponse<HeadBucketResult> mo5855handle(HttpResponse httpResponse) throws Exception {
        AmazonWebServiceResponse<HeadBucketResult> amazonWebServiceResponse = new AmazonWebServiceResponse<>();
        HeadBucketResult headBucketResult = new HeadBucketResult();
        headBucketResult.setBucketRegion(httpResponse.getHeaders().get("x-amz-bucket-region"));
        amazonWebServiceResponse.setResult(headBucketResult);
        return amazonWebServiceResponse;
    }
}
