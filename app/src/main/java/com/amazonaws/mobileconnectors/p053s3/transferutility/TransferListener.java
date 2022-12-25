package com.amazonaws.mobileconnectors.p053s3.transferutility;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferListener */
/* loaded from: classes2.dex */
public interface TransferListener {
    void onError(int i, Exception exc);

    void onProgressChanged(int i, long j, long j2);

    void onStateChanged(int i, TransferState transferState);
}
