package org.xutils.http.body;

import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.xutils.common.Callback;
import org.xutils.common.util.IOUtil;
import org.xutils.http.ProgressHandler;

/* loaded from: classes4.dex */
public class InputStreamBody implements ProgressBody {
    private ProgressHandler callBackHandler;
    private InputStream content;
    private String contentType;
    private long current;
    private final long total;

    public InputStreamBody(InputStream inputStream) {
        this(inputStream, null);
    }

    public InputStreamBody(InputStream inputStream, String str) {
        this.current = 0L;
        this.content = inputStream;
        this.contentType = str;
        this.total = getInputStreamLength(inputStream);
    }

    @Override // org.xutils.http.body.ProgressBody
    public void setProgressHandler(ProgressHandler progressHandler) {
        this.callBackHandler = progressHandler;
    }

    @Override // org.xutils.http.body.RequestBody
    public long getContentLength() {
        return this.total;
    }

    @Override // org.xutils.http.body.RequestBody
    public void setContentType(String str) {
        this.contentType = str;
    }

    @Override // org.xutils.http.body.RequestBody
    public String getContentType() {
        return TextUtils.isEmpty(this.contentType) ? "application/octet-stream" : this.contentType;
    }

    @Override // org.xutils.http.body.RequestBody
    public void writeTo(OutputStream outputStream) throws IOException {
        ProgressHandler progressHandler = this.callBackHandler;
        if (progressHandler == null || progressHandler.updateProgress(this.total, this.current, true)) {
            byte[] bArr = new byte[1024];
            while (true) {
                try {
                    int read = this.content.read(bArr);
                    if (read != -1) {
                        outputStream.write(bArr, 0, read);
                        this.current += read;
                        if (this.callBackHandler != null && !this.callBackHandler.updateProgress(this.total, this.current, false)) {
                            throw new Callback.CancelledException("upload stopped!");
                        }
                    } else {
                        outputStream.flush();
                        if (this.callBackHandler != null) {
                            this.callBackHandler.updateProgress(this.total, this.total, true);
                        }
                        return;
                    }
                } finally {
                    IOUtil.closeQuietly(this.content);
                }
            }
        } else {
            throw new Callback.CancelledException("upload stopped!");
        }
    }

    public static long getInputStreamLength(InputStream inputStream) {
        try {
            if (!(inputStream instanceof FileInputStream) && !(inputStream instanceof ByteArrayInputStream)) {
                return -1L;
            }
            return inputStream.available();
        } catch (Throwable unused) {
            return -1L;
        }
    }
}
