package com.amazonaws.services.p054s3.internal;

/* renamed from: com.amazonaws.services.s3.internal.SSEResultBase */
/* loaded from: classes2.dex */
public abstract class SSEResultBase implements ServerSideEncryptionResult {
    private String sseAlgorithm;
    private String sseCustomerAlgorithm;
    private String sseCustomerKeyMD5;

    public final String getSSEAlgorithm() {
        return this.sseAlgorithm;
    }

    @Override // com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult
    public final void setSSEAlgorithm(String str) {
        this.sseAlgorithm = str;
    }

    public final String getSSECustomerAlgorithm() {
        return this.sseCustomerAlgorithm;
    }

    @Override // com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult
    public final void setSSECustomerAlgorithm(String str) {
        this.sseCustomerAlgorithm = str;
    }

    public final String getSSECustomerKeyMd5() {
        return this.sseCustomerKeyMD5;
    }

    @Override // com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult
    public final void setSSECustomerKeyMd5(String str) {
        this.sseCustomerKeyMD5 = str;
    }

    @Deprecated
    public final String getServerSideEncryption() {
        return this.sseAlgorithm;
    }
}
