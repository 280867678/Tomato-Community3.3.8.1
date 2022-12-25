package com.example.websocket.com.neovisionaries.p057ws.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocketInputStream */
/* loaded from: classes2.dex */
class WebSocketInputStream extends FilterInputStream {
    public WebSocketInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public String readLine() throws IOException {
        return Misc.readLine(this, "UTF-8");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public WebSocketFrame readFrame() throws IOException, WebSocketException {
        int i;
        byte b;
        byte[] bArr = new byte[8];
        try {
            readBytes(bArr, 2);
            boolean z = (bArr[0] & 128) != 0;
            boolean z2 = (bArr[0] & 64) != 0;
            boolean z3 = (bArr[0] & 32) != 0;
            boolean z4 = (bArr[0] & 16) != 0;
            int i2 = bArr[0] & 15;
            boolean z5 = (bArr[1] & 128) != 0;
            long j = bArr[1] & Byte.MAX_VALUE;
            if (j != 126) {
                if (j == 127) {
                    readBytes(bArr, 8);
                    if ((bArr[0] & 128) != 0) {
                        throw new WebSocketException(WebSocketError.INVALID_PAYLOAD_LENGTH, "The payload length of a frame is invalid.");
                    }
                    i = ((bArr[6] & 255) << 8) | ((bArr[2] & 255) << 40) | ((bArr[0] & 255) << 56) | ((bArr[1] & 255) << 48) | ((bArr[3] & 255) << 32) | ((bArr[4] & 255) << 24) | ((bArr[5] & 255) << 16);
                    b = bArr[7];
                }
                byte[] bArr2 = null;
                if (z5) {
                    bArr2 = new byte[4];
                    readBytes(bArr2, 4);
                }
                if (2147483647L >= j) {
                    skipQuietly(j);
                    throw new WebSocketException(WebSocketError.TOO_LONG_PAYLOAD, "The payload length of a frame exceeds the maximum array size in Java.");
                }
                return new WebSocketFrame().setFin(z).setRsv1(z2).setRsv2(z3).setRsv3(z4).setOpcode(i2).setMask(z5).setPayload(readPayload(j, z5, bArr2));
            }
            readBytes(bArr, 2);
            i = (bArr[0] & 255) << 8;
            b = bArr[1];
            j = i | (b & 255);
            byte[] bArr22 = null;
            if (z5) {
            }
            if (2147483647L >= j) {
            }
        } catch (InsufficientDataException e) {
            if (e.getReadByteCount() == 0) {
                throw new NoMoreFrameException();
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void readBytes(byte[] bArr, int i) throws IOException, WebSocketException {
        int i2 = 0;
        while (i2 < i) {
            int read = read(bArr, i2, i - i2);
            if (read <= 0) {
                throw new InsufficientDataException(i, i2);
            }
            i2 += read;
        }
    }

    private void skipQuietly(long j) {
        try {
            skip(j);
        } catch (IOException unused) {
        }
    }

    private byte[] readPayload(long j, boolean z, byte[] bArr) throws IOException, WebSocketException {
        if (j == 0) {
            return null;
        }
        try {
            byte[] bArr2 = new byte[(int) j];
            readBytes(bArr2, bArr2.length);
            if (z) {
                WebSocketFrame.mask(bArr, bArr2);
            }
            return bArr2;
        } catch (OutOfMemoryError e) {
            skipQuietly(j);
            WebSocketError webSocketError = WebSocketError.INSUFFICIENT_MEMORY_FOR_PAYLOAD;
            throw new WebSocketException(webSocketError, "OutOfMemoryError occurred during a trial to allocate a memory area for a frame's payload: " + e.getMessage(), e);
        }
    }
}
