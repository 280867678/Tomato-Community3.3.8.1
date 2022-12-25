package com.amazonaws.mobileconnectors.p053s3.transfermanager.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.p053s3.transfermanager.PauseStatus;

@Deprecated
/* renamed from: com.amazonaws.mobileconnectors.s3.transfermanager.exception.PauseException */
/* loaded from: classes2.dex */
public class PauseException extends AmazonClientException {
    private static final long serialVersionUID = 1;
    private final PauseStatus status;

    @Override // com.amazonaws.AmazonClientException
    public boolean isRetryable() {
        return false;
    }

    public PauseException(PauseStatus pauseStatus) {
        super("Failed to pause operation; status=" + pauseStatus);
        if (pauseStatus == null || pauseStatus == PauseStatus.SUCCESS) {
            throw new IllegalArgumentException();
        }
        this.status = pauseStatus;
    }

    public PauseStatus getPauseStatus() {
        return this.status;
    }
}
