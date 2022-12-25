package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.InsufficientDataException */
/* loaded from: classes2.dex */
class InsufficientDataException extends WebSocketException {
    private static final long serialVersionUID = 1;
    private final int mReadByteCount;
    private final int mRequestedByteCount;

    public InsufficientDataException(int i, int i2) {
        super(WebSocketError.INSUFFICENT_DATA, "The end of the stream has been reached unexpectedly.");
        this.mRequestedByteCount = i;
        this.mReadByteCount = i2;
    }

    public int getRequestedByteCount() {
        return this.mRequestedByteCount;
    }

    public int getReadByteCount() {
        return this.mReadByteCount;
    }
}
