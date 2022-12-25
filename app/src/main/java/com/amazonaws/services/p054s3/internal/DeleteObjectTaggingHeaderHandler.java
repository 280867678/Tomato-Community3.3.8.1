package com.amazonaws.services.p054s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.p054s3.model.DeleteObjectTaggingResult;

/* renamed from: com.amazonaws.services.s3.internal.DeleteObjectTaggingHeaderHandler */
/* loaded from: classes2.dex */
public class DeleteObjectTaggingHeaderHandler implements HeaderHandler<DeleteObjectTaggingResult> {
    @Override // com.amazonaws.services.p054s3.internal.HeaderHandler
    public void handle(DeleteObjectTaggingResult deleteObjectTaggingResult, HttpResponse httpResponse) {
        deleteObjectTaggingResult.setVersionId(httpResponse.getHeaders().get("x-amz-version-id"));
    }
}
