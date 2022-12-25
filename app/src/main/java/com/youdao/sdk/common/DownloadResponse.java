package com.youdao.sdk.common;

import com.youdao.sdk.common.util.Streams;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.http.HttpResponse;

/* loaded from: classes4.dex */
public class DownloadResponse {
    private byte[] mBytes;

    public DownloadResponse(HttpResponse httpResponse) {
        BufferedInputStream bufferedInputStream;
        this.mBytes = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream2 = null;
        try {
            bufferedInputStream = new BufferedInputStream(httpResponse.getEntity().getContent());
        } catch (Throwable th) {
            th = th;
        }
        try {
            Streams.copyContent(bufferedInputStream, byteArrayOutputStream);
            this.mBytes = byteArrayOutputStream.toByteArray();
            Streams.closeStream(bufferedInputStream);
            Streams.closeStream(byteArrayOutputStream);
            if (httpResponse.getStatusLine() != null) {
                httpResponse.getStatusLine().getStatusCode();
            }
            int length = this.mBytes.length;
            httpResponse.getAllHeaders();
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream2 = bufferedInputStream;
            Streams.closeStream(bufferedInputStream2);
            Streams.closeStream(byteArrayOutputStream);
            throw th;
        }
    }

    public byte[] getByteArray() {
        return this.mBytes;
    }
}
