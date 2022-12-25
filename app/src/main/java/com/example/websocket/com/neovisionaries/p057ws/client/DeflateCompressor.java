package com.example.websocket.com.neovisionaries.p057ws.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.DeflateCompressor */
/* loaded from: classes2.dex */
class DeflateCompressor {
    DeflateCompressor() {
    }

    public static byte[] compress(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater createDeflater = createDeflater();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, createDeflater);
        deflaterOutputStream.write(bArr, 0, bArr.length);
        deflaterOutputStream.close();
        createDeflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    private static Deflater createDeflater() {
        return new Deflater(-1, true);
    }
}
