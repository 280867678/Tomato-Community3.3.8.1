package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.PerMessageCompressionExtension */
/* loaded from: classes2.dex */
abstract class PerMessageCompressionExtension extends WebSocketExtension {
    /* JADX INFO: Access modifiers changed from: protected */
    public abstract byte[] compress(byte[] bArr) throws WebSocketException;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract byte[] decompress(byte[] bArr) throws WebSocketException;

    public PerMessageCompressionExtension(String str) {
        super(str);
    }

    public PerMessageCompressionExtension(WebSocketExtension webSocketExtension) {
        super(webSocketExtension);
    }
}
