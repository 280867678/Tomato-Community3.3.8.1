package com.amazonaws.services.p054s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.p054s3.model.SetObjectTaggingResult;

/* renamed from: com.amazonaws.services.s3.internal.SetObjectTaggingResponseHeaderHandler */
/* loaded from: classes2.dex */
public class SetObjectTaggingResponseHeaderHandler implements HeaderHandler<SetObjectTaggingResult> {
    @Override // com.amazonaws.services.p054s3.internal.HeaderHandler
    public void handle(SetObjectTaggingResult setObjectTaggingResult, HttpResponse httpResponse) {
        setObjectTaggingResult.setVersionId(httpResponse.getHeaders().get("x-amz-version-id"));
    }
}
