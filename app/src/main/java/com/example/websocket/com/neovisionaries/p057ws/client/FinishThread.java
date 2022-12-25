package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.FinishThread */
/* loaded from: classes2.dex */
class FinishThread extends WebSocketThread {
    public FinishThread(WebSocket webSocket) {
        super("FinishThread", webSocket, ThreadType.FINISH_THREAD);
    }

    @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketThread
    public void runMain() {
        this.mWebSocket.finish();
    }
}
