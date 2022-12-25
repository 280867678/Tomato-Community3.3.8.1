package com.example.websocket.com.neovisionaries.p057ws.client;

import java.util.List;
import java.util.Map;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.OpeningHandshakeException */
/* loaded from: classes2.dex */
public class OpeningHandshakeException extends WebSocketException {
    private static final long serialVersionUID = 1;
    private final byte[] mBody;
    private final Map<String, List<String>> mHeaders;
    private final StatusLine mStatusLine;

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpeningHandshakeException(WebSocketError webSocketError, String str, StatusLine statusLine, Map<String, List<String>> map) {
        this(webSocketError, str, statusLine, map, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpeningHandshakeException(WebSocketError webSocketError, String str, StatusLine statusLine, Map<String, List<String>> map, byte[] bArr) {
        super(webSocketError, str);
        this.mStatusLine = statusLine;
        this.mHeaders = map;
        this.mBody = bArr;
    }

    public StatusLine getStatusLine() {
        return this.mStatusLine;
    }

    public Map<String, List<String>> getHeaders() {
        return this.mHeaders;
    }

    public byte[] getBody() {
        return this.mBody;
    }
}
