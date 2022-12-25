package com.example.websocket.com.neovisionaries.p057ws.client;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.PongSender */
/* loaded from: classes2.dex */
public class PongSender extends PeriodicalFrameSender {
    private static final String TIMER_NAME = "PongSender";

    public PongSender(WebSocket webSocket, PayloadGenerator payloadGenerator) {
        super(webSocket, TIMER_NAME, payloadGenerator);
    }

    @Override // com.example.websocket.com.neovisionaries.p057ws.client.PeriodicalFrameSender
    protected WebSocketFrame createFrame(byte[] bArr) {
        return WebSocketFrame.createPongFrame(bArr);
    }
}
