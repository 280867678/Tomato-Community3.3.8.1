package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.ConnectThread */
/* loaded from: classes2.dex */
class ConnectThread extends WebSocketThread {
    public ConnectThread(WebSocket webSocket) {
        super("ConnectThread", webSocket, ThreadType.CONNECT_THREAD);
    }

    @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketThread
    public void runMain() {
        try {
            this.mWebSocket.connect();
        } catch (WebSocketException e) {
            handleError(e);
        }
    }

    private void handleError(WebSocketException webSocketException) {
        ListenerManager listenerManager = this.mWebSocket.getListenerManager();
        listenerManager.callOnError(webSocketException);
        listenerManager.callOnConnectError(webSocketException);
    }
}
