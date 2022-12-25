package org.xutils.http.body;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.xutils.common.Callback;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.KeyValue;
import org.xutils.http.ProgressHandler;

/* loaded from: classes4.dex */
public class MultipartBody implements ProgressBody {
    private static byte[] BOUNDARY_PREFIX_BYTES = "--------7da3d81520810".getBytes();
    private static byte[] END_BYTES = "\r\n".getBytes();
    private static byte[] TWO_DASHES_BYTES = "--".getBytes();
    private byte[] boundaryPostfixBytes;
    private ProgressHandler callBackHandler;
    private String charset;
    private String contentType;
    private long current = 0;
    private List<KeyValue> multipartParams;
    private long total;

    public MultipartBody(List<KeyValue> list, String str) {
        this.charset = "UTF-8";
        this.total = 0L;
        if (!TextUtils.isEmpty(str)) {
            this.charset = str;
        }
        this.multipartParams = list;
        generateContentType();
        CounterOutputStream counterOutputStream = new CounterOutputStream(this);
        try {
            writeTo(counterOutputStream);
            this.total = counterOutputStream.total.get();
        } catch (IOException unused) {
            this.total = -1L;
        }
    }

    @Override // org.xutils.http.body.ProgressBody
    public void setProgressHandler(ProgressHandler progressHandler) {
        this.callBackHandler = progressHandler;
    }

    private void generateContentType() {
        String hexString = Double.toHexString(Math.random() * 65535.0d);
        this.boundaryPostfixBytes = hexString.getBytes();
        this.contentType = "multipart/form-data; boundary=" + new String(BOUNDARY_PREFIX_BYTES) + hexString;
    }

    @Override // org.xutils.http.body.RequestBody
    public long getContentLength() {
        return this.total;
    }

    @Override // org.xutils.http.body.RequestBody
    public void setContentType(String str) {
        int indexOf = this.contentType.indexOf(";");
        this.contentType = "multipart/" + str + this.contentType.substring(indexOf);
    }

    @Override // org.xutils.http.body.RequestBody
    public String getContentType() {
        return this.contentType;
    }

    @Override // org.xutils.http.body.RequestBody
    public void writeTo(OutputStream outputStream) throws IOException {
        ProgressHandler progressHandler = this.callBackHandler;
        if (progressHandler != null && !progressHandler.updateProgress(this.total, this.current, true)) {
            throw new Callback.CancelledException("upload stopped!");
        }
        for (KeyValue keyValue : this.multipartParams) {
            String str = keyValue.key;
            Object obj = keyValue.value;
            if (!TextUtils.isEmpty(str) && obj != null) {
                writeEntry(outputStream, str, obj);
            }
        }
        byte[] bArr = TWO_DASHES_BYTES;
        writeLine(outputStream, bArr, BOUNDARY_PREFIX_BYTES, this.boundaryPostfixBytes, bArr);
        outputStream.flush();
        ProgressHandler progressHandler2 = this.callBackHandler;
        if (progressHandler2 == null) {
            return;
        }
        long j = this.total;
        progressHandler2.updateProgress(j, j, true);
    }

    private void writeEntry(OutputStream outputStream, String str, Object obj) throws IOException {
        String str2;
        String str3;
        byte[] bytes;
        writeLine(outputStream, TWO_DASHES_BYTES, BOUNDARY_PREFIX_BYTES, this.boundaryPostfixBytes);
        if (obj instanceof BodyItemWrapper) {
            BodyItemWrapper bodyItemWrapper = (BodyItemWrapper) obj;
            Object value = bodyItemWrapper.getValue();
            str2 = bodyItemWrapper.getFileName();
            str3 = bodyItemWrapper.getContentType();
            obj = value;
        } else {
            str2 = "";
            str3 = null;
        }
        if (obj instanceof File) {
            File file = (File) obj;
            if (TextUtils.isEmpty(str2)) {
                str2 = file.getName();
            }
            if (TextUtils.isEmpty(str3)) {
                str3 = FileBody.getFileContentType(file);
            }
            writeLine(outputStream, buildContentDisposition(str, str2, this.charset));
            writeLine(outputStream, buildContentType(obj, str3, this.charset));
            writeLine(outputStream, new byte[0]);
            writeFile(outputStream, file);
            writeLine(outputStream, new byte[0]);
            return;
        }
        writeLine(outputStream, buildContentDisposition(str, str2, this.charset));
        writeLine(outputStream, buildContentType(obj, str3, this.charset));
        writeLine(outputStream, new byte[0]);
        if (obj instanceof InputStream) {
            writeStreamAndCloseIn(outputStream, (InputStream) obj);
            writeLine(outputStream, new byte[0]);
            return;
        }
        if (obj instanceof byte[]) {
            bytes = (byte[]) obj;
        } else {
            bytes = String.valueOf(obj).getBytes(this.charset);
        }
        writeLine(outputStream, bytes);
        this.current += bytes.length;
        ProgressHandler progressHandler = this.callBackHandler;
        if (progressHandler != null && !progressHandler.updateProgress(this.total, this.current, false)) {
            throw new Callback.CancelledException("upload stopped!");
        }
    }

    private void writeLine(OutputStream outputStream, byte[]... bArr) throws IOException {
        if (bArr != null) {
            for (byte[] bArr2 : bArr) {
                outputStream.write(bArr2);
            }
        }
        outputStream.write(END_BYTES);
    }

    private void writeFile(OutputStream outputStream, File file) throws IOException {
        if (outputStream instanceof CounterOutputStream) {
            ((CounterOutputStream) outputStream).addFile(file);
        } else {
            writeStreamAndCloseIn(outputStream, new FileInputStream(file));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0037, code lost:
        throw new org.xutils.common.Callback.CancelledException("upload stopped!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeStreamAndCloseIn(OutputStream outputStream, InputStream inputStream) throws IOException {
        if (outputStream instanceof CounterOutputStream) {
            ((CounterOutputStream) outputStream).addStream(inputStream);
            return;
        }
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    return;
                }
                outputStream.write(bArr, 0, read);
                this.current += read;
                if (this.callBackHandler != null && !this.callBackHandler.updateProgress(this.total, this.current, false)) {
                    break;
                }
            }
        } finally {
            IOUtil.closeQuietly(inputStream);
        }
    }

    private static byte[] buildContentDisposition(String str, String str2, String str3) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder("Content-Disposition: form-data");
        sb.append("; name=\"");
        sb.append(str.replace("\"", "\\\""));
        sb.append("\"");
        if (!TextUtils.isEmpty(str2)) {
            sb.append("; filename=\"");
            sb.append(str2.replace("\"", "\\\""));
            sb.append("\"");
        }
        return sb.toString().getBytes(str3);
    }

    private static byte[] buildContentType(Object obj, String str, String str2) throws UnsupportedEncodingException {
        String replaceFirst;
        StringBuilder sb = new StringBuilder("Content-Type: ");
        if (TextUtils.isEmpty(str)) {
            if (obj instanceof String) {
                replaceFirst = "text/plain; charset=" + str2;
            } else {
                replaceFirst = "application/octet-stream";
            }
        } else {
            replaceFirst = str.replaceFirst("\\/jpg$", "/jpeg");
        }
        sb.append(replaceFirst);
        return sb.toString().getBytes(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class CounterOutputStream extends OutputStream {
        final AtomicLong total = new AtomicLong(0);

        public CounterOutputStream(MultipartBody multipartBody) {
        }

        public void addFile(File file) {
            if (this.total.get() == -1) {
                return;
            }
            this.total.addAndGet(file.length());
        }

        public void addStream(InputStream inputStream) {
            if (this.total.get() == -1) {
                return;
            }
            long inputStreamLength = InputStreamBody.getInputStreamLength(inputStream);
            if (inputStreamLength > 0) {
                this.total.addAndGet(inputStreamLength);
            } else {
                this.total.set(-1L);
            }
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            if (this.total.get() == -1) {
                return;
            }
            this.total.incrementAndGet();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            if (this.total.get() == -1) {
                return;
            }
            this.total.addAndGet(bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.total.get() == -1) {
                return;
            }
            this.total.addAndGet(i2);
        }
    }
}
