package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocketException */
/* loaded from: classes2.dex */
public class WebSocketException extends Exception {
    private static final long serialVersionUID = 1;
    private final WebSocketError mError;

    public WebSocketException(WebSocketError webSocketError) {
        this.mError = webSocketError;
    }

    public WebSocketException(WebSocketError webSocketError, String str) {
        super(str);
        this.mError = webSocketError;
    }

    public WebSocketException(WebSocketError webSocketError, Throwable th) {
        super(th);
        this.mError = webSocketError;
    }

    public WebSocketException(WebSocketError webSocketError, String str, Throwable th) {
        super(str, th);
        this.mError = webSocketError;
    }

    public WebSocketError getError() {
        return this.mError;
    }
}
