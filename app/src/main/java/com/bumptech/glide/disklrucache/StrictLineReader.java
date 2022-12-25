package com.bumptech.glide.disklrucache;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class StrictLineReader implements Closeable {

    /* renamed from: CR */
    private static final byte f1196CR = 13;

    /* renamed from: LF */
    private static final byte f1197LF = 10;
    private byte[] buf;
    private final Charset charset;
    private int end;

    /* renamed from: in */
    private final InputStream f1198in;
    private int pos;

    public StrictLineReader(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    public StrictLineReader(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        }
        if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        if (!charset.equals(Util.US_ASCII)) {
            throw new IllegalArgumentException("Unsupported encoding");
        }
        this.f1198in = inputStream;
        this.charset = charset;
        this.buf = new byte[i];
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.f1198in) {
            if (this.buf != null) {
                this.buf = null;
                this.f1198in.close();
            }
        }
    }

    public String readLine() throws IOException {
        int i;
        int i2;
        synchronized (this.f1198in) {
            if (this.buf == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.pos >= this.end) {
                fillBuf();
            }
            for (int i3 = this.pos; i3 != this.end; i3++) {
                if (this.buf[i3] == 10) {
                    if (i3 != this.pos) {
                        i2 = i3 - 1;
                        if (this.buf[i2] == 13) {
                            String str = new String(this.buf, this.pos, i2 - this.pos, this.charset.name());
                            this.pos = i3 + 1;
                            return str;
                        }
                    }
                    i2 = i3;
                    String str2 = new String(this.buf, this.pos, i2 - this.pos, this.charset.name());
                    this.pos = i3 + 1;
                    return str2;
                }
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((this.end - this.pos) + 80) { // from class: com.bumptech.glide.disklrucache.StrictLineReader.1
                @Override // java.io.ByteArrayOutputStream
                public String toString() {
                    int i4 = ((ByteArrayOutputStream) this).count;
                    try {
                        return new String(((ByteArrayOutputStream) this).buf, 0, (i4 <= 0 || ((ByteArrayOutputStream) this).buf[i4 + (-1)] != 13) ? ((ByteArrayOutputStream) this).count : i4 - 1, StrictLineReader.this.charset.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                }
            };
            loop1: while (true) {
                byteArrayOutputStream.write(this.buf, this.pos, this.end - this.pos);
                this.end = -1;
                fillBuf();
                i = this.pos;
                while (i != this.end) {
                    if (this.buf[i] == 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.pos) {
                byteArrayOutputStream.write(this.buf, this.pos, i - this.pos);
            }
            this.pos = i + 1;
            return byteArrayOutputStream.toString();
        }
    }

    public boolean hasUnterminatedLine() {
        return this.end == -1;
    }

    private void fillBuf() throws IOException {
        InputStream inputStream = this.f1198in;
        byte[] bArr = this.buf;
        int read = inputStream.read(bArr, 0, bArr.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = read;
    }
}
