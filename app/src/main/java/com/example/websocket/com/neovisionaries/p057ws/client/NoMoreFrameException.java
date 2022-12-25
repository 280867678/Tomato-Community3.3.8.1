package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.NoMoreFrameException */
/* loaded from: classes2.dex */
class NoMoreFrameException extends WebSocketException {
    private static final long serialVersionUID = 1;

    public NoMoreFrameException() {
        super(WebSocketError.NO_MORE_FRAME, "No more WebSocket frame from the server.");
    }
}
