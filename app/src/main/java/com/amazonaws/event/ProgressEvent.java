package com.amazonaws.event;

/* loaded from: classes2.dex */
public class ProgressEvent {
    protected long bytesTransferred;
    protected int eventCode;

    public ProgressEvent(long j) {
        this.bytesTransferred = j;
    }

    public ProgressEvent(int i, long j) {
        this.eventCode = i;
        this.bytesTransferred = j;
    }

    public long getBytesTransferred() {
        return this.bytesTransferred;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(int i) {
        this.eventCode = i;
    }
}
