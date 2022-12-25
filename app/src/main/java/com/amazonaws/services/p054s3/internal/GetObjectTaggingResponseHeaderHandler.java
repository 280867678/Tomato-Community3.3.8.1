package com.amazonaws.services.p054s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.p054s3.model.GetObjectTaggingResult;

/* renamed from: com.amazonaws.services.s3.internal.GetObjectTaggingResponseHeaderHandler */
/* loaded from: classes2.dex */
public class GetObjectTaggingResponseHeaderHandler implements HeaderHandler<GetObjectTaggingResult> {
    @Override // com.amazonaws.services.p054s3.internal.HeaderHandler
    public void handle(GetObjectTaggingResult getObjectTaggingResult, HttpResponse httpResponse) {
        getObjectTaggingResult.setVersionId(httpResponse.getHeaders().get("x-amz-version-id"));
    }
}
