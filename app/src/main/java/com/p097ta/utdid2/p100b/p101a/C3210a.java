package com.p097ta.utdid2.p100b.p101a;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

/* renamed from: com.ta.utdid2.b.a.a */
/* loaded from: classes3.dex */
class C3210a implements XmlSerializer {

    /* renamed from: a */
    private OutputStream f1908a;

    /* renamed from: a */
    private Writer f1909a;

    /* renamed from: a */
    private CharsetEncoder f1911a;

    /* renamed from: e */
    private boolean f1913e;
    private int mPos;

    /* renamed from: a */
    private static final String[] f1907a = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};

    /* renamed from: a */
    private static String f1906a = "xmlpull.org/v1/doc/features.html#indent-output";

    /* renamed from: a */
    private final char[] f1912a = new char[8192];

    /* renamed from: a */
    private ByteBuffer f1910a = ByteBuffer.allocate(8192);

    private void append(char c) throws IOException {
        int i = this.mPos;
        if (i >= 8191) {
            flush();
            i = this.mPos;
        }
        this.f1912a[i] = c;
        this.mPos = i + 1;
    }

    /* renamed from: a */
    private void m3644a(String str, int i, int i2) throws IOException {
        if (i2 > 8192) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = i + 8192;
                m3644a(str, i, i4 < i3 ? 8192 : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > 8192) {
            flush();
            i5 = this.mPos;
        }
        str.getChars(i, i + i2, this.f1912a, i5);
        this.mPos = i5 + i2;
    }

    private void append(char[] cArr, int i, int i2) throws IOException {
        if (i2 > 8192) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = i + 8192;
                append(cArr, i, i4 < i3 ? 8192 : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > 8192) {
            flush();
            i5 = this.mPos;
        }
        System.arraycopy(cArr, i, this.f1912a, i5, i2);
        this.mPos = i5 + i2;
    }

    private void append(String str) throws IOException {
        m3644a(str, 0, str.length());
    }

    /* renamed from: a */
    private void m3645a(String str) throws IOException {
        String str2;
        int length = str.length();
        String[] strArr = f1907a;
        char length2 = (char) strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt < length2 && (str2 = strArr[charAt]) != null) {
                if (i2 < i) {
                    m3644a(str, i2, i - i2);
                }
                i2 = i + 1;
                append(str2);
            }
            i++;
        }
        if (i2 < i) {
            m3644a(str, i2, i - i2);
        }
    }

    /* renamed from: a */
    private void m3643a(char[] cArr, int i, int i2) throws IOException {
        String str;
        String[] strArr = f1907a;
        char length = (char) strArr.length;
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            char c = cArr[i];
            if (c < length && (str = strArr[c]) != null) {
                if (i4 < i) {
                    append(cArr, i4, i - i4);
                }
                i4 = i + 1;
                append(str);
            }
            i++;
        }
        if (i4 < i) {
            append(cArr, i4, i - i4);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IOException, IllegalArgumentException, IllegalStateException {
        append(' ');
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        append("=\"");
        m3645a(str3);
        append('\"');
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IOException, IllegalArgumentException, IllegalStateException {
        flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.f1913e) {
            append(" />\n");
        } else {
            append("</");
            if (str != null) {
                append(str);
                append(':');
            }
            append(str2);
            append(">\n");
        }
        this.f1913e = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    /* renamed from: a */
    private void m3646a() throws IOException {
        int position = this.f1910a.position();
        if (position > 0) {
            this.f1910a.flip();
            this.f1908a.write(this.f1910a.array(), 0, position);
            this.f1910a.clear();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        int i = this.mPos;
        if (i > 0) {
            if (this.f1908a != null) {
                CharBuffer wrap = CharBuffer.wrap(this.f1912a, 0, i);
                CoderResult encode = this.f1911a.encode(wrap, this.f1910a, true);
                while (!encode.isError()) {
                    if (encode.isOverflow()) {
                        m3646a();
                        encode = this.f1911a.encode(wrap, this.f1910a, true);
                    } else {
                        m3646a();
                        this.f1908a.flush();
                    }
                }
                throw new IOException(encode.toString());
            }
            this.f1909a.write(this.f1912a, 0, i);
            this.f1909a.flush();
            this.mPos = 0;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: d */
    public static String m3642d() {
        return "http://" + f1906a;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) throws IllegalArgumentException, IllegalStateException {
        if (str.equals(m3642d())) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IOException, IllegalArgumentException, IllegalStateException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        try {
            this.f1911a = Charset.forName(str).newEncoder();
            this.f1908a = outputStream;
        } catch (IllegalCharsetNameException e) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e));
        } catch (UnsupportedCharsetException e2) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e2));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) throws IOException, IllegalArgumentException, IllegalStateException {
        this.f1909a = writer;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IOException, IllegalArgumentException, IllegalStateException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='utf-8' standalone='");
        sb.append(bool.booleanValue() ? "yes" : "no");
        sb.append("' ?>\n");
        append(sb.toString());
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.f1913e) {
            append(">\n");
        }
        append('<');
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        this.f1913e = true;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i, int i2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.f1913e) {
            append(SimpleComparison.GREATER_THAN_OPERATION);
            this.f1913e = false;
        }
        m3643a(cArr, i, i2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.f1913e) {
            append(SimpleComparison.GREATER_THAN_OPERATION);
            this.f1913e = false;
        }
        m3645a(str);
        return this;
    }
}
