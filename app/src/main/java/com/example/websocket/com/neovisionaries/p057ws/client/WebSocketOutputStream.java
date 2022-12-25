package com.example.websocket.com.neovisionaries.p057ws.client;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocketOutputStream */
/* loaded from: classes2.dex */
class WebSocketOutputStream extends FilterOutputStream {
    public WebSocketOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void write(String str) throws IOException {
        write(Misc.getBytesUTF8(str));
    }

    public void write(WebSocketFrame webSocketFrame) throws IOException {
        writeFrame0(webSocketFrame);
        writeFrame1(webSocketFrame);
        writeFrameExtendedPayloadLength(webSocketFrame);
        byte[] nextBytes = Misc.nextBytes(4);
        write(nextBytes);
        writeFramePayload(webSocketFrame, nextBytes);
    }

    private void writeFrame0(WebSocketFrame webSocketFrame) throws IOException {
        int i = 0;
        int i2 = (webSocketFrame.getFin() ? 128 : 0) | (webSocketFrame.getRsv1() ? 64 : 0) | (webSocketFrame.getRsv2() ? 32 : 0);
        if (webSocketFrame.getRsv3()) {
            i = 16;
        }
        write((webSocketFrame.getOpcode() & 15) | i2 | i);
    }

    private void writeFrame1(WebSocketFrame webSocketFrame) throws IOException {
        int payloadLength = webSocketFrame.getPayloadLength();
        write(payloadLength <= 125 ? payloadLength | 128 : payloadLength <= 65535 ? 254 : 255);
    }

    private void writeFrameExtendedPayloadLength(WebSocketFrame webSocketFrame) throws IOException {
        int payloadLength = webSocketFrame.getPayloadLength();
        if (payloadLength <= 125) {
            return;
        }
        if (payloadLength <= 65535) {
            write((payloadLength >> 8) & 255);
            write(payloadLength & 255);
            return;
        }
        write(0);
        write(0);
        write(0);
        write(0);
        write((payloadLength >> 24) & 255);
        write((payloadLength >> 16) & 255);
        write((payloadLength >> 8) & 255);
        write(payloadLength & 255);
    }

    private void writeFramePayload(WebSocketFrame webSocketFrame, byte[] bArr) throws IOException {
        byte[] payload = webSocketFrame.getPayload();
        if (payload == null) {
            return;
        }
        for (int i = 0; i < payload.length; i++) {
            write((payload[i] ^ bArr[i % 4]) & 255);
        }
    }
}
