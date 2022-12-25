package com.example.websocket.com.neovisionaries.p057ws.client;

import java.util.concurrent.Callable;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.Connectable */
/* loaded from: classes2.dex */
class Connectable implements Callable<WebSocket> {
    private final WebSocket mWebSocket;

    public Connectable(WebSocket webSocket) {
        this.mWebSocket = webSocket;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    /* renamed from: call */
    public WebSocket mo5915call() throws WebSocketException {
        return this.mWebSocket.connect();
    }
}
