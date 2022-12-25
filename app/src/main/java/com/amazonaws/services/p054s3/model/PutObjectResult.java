package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.internal.ObjectExpirationResult;
import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import com.amazonaws.services.p054s3.internal.S3VersionResult;
import com.amazonaws.services.p054s3.internal.SSEResultBase;
import java.util.Date;

/* renamed from: com.amazonaws.services.s3.model.PutObjectResult */
/* loaded from: classes2.dex */
public class PutObjectResult extends SSEResultBase implements ObjectExpirationResult, S3RequesterChargedResult, S3VersionResult {
    public void setETag(String str) {
    }

    @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
    public void setExpirationTime(Date date) {
    }

    @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
    public void setExpirationTimeRuleId(String str) {
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
    }

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
    }

    @Override // com.amazonaws.services.p054s3.internal.S3VersionResult
    public void setVersionId(String str) {
    }
}
