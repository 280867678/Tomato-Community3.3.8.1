package p007b.p014c.p015a.p018c.p021c;

import com.koushikdutta.async.http.server.MimeEncodingException;
import java.nio.ByteBuffer;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0723u;

/* renamed from: b.c.a.c.c.r */
/* loaded from: classes2.dex */
public class C0682r extends C0723u {

    /* renamed from: h */
    public static final /* synthetic */ boolean f322h = !C0682r.class.desiredAssertionStatus();

    /* renamed from: i */
    public byte[] f323i;

    /* renamed from: j */
    public int f324j = 2;

    @Override // p007b.p014c.p015a.C0723u, p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        MimeEncodingException mimeEncodingException;
        MimeEncodingException mimeEncodingException2;
        if (this.f324j > 0) {
            ByteBuffer m5319b = C0714n.m5319b(this.f323i.length);
            m5319b.put(this.f323i, 0, this.f324j);
            m5319b.flip();
            c0714n.m5318b(m5319b);
            this.f324j = 0;
        }
        byte[] bArr = new byte[c0714n.m5303m()];
        c0714n.m5323a(bArr);
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = this.f324j;
            int i4 = -1;
            if (i3 >= 0) {
                byte b = bArr[i];
                byte[] bArr2 = this.f323i;
                if (b == bArr2[i3]) {
                    this.f324j = i3 + 1;
                    if (this.f324j != bArr2.length) {
                    }
                    this.f324j = i4;
                } else if (i3 > 0) {
                    i -= i3;
                    this.f324j = 0;
                }
            } else if (i3 != -1) {
                i4 = -3;
                if (i3 == -2) {
                    if (bArr[i] != 45) {
                        mimeEncodingException2 = new MimeEncodingException("Invalid multipart/form-data. Expected -");
                    }
                    this.f324j = i4;
                } else if (i3 != -3) {
                    if (i3 == -4) {
                        if (bArr[i] == 10) {
                            i2 = i + 1;
                            this.f324j = 0;
                        } else {
                            mimeEncodingException = new MimeEncodingException("Invalid multipart/form-data. Expected \n");
                        }
                    } else if (!f322h) {
                        throw new AssertionError();
                    } else {
                        mimeEncodingException = new MimeEncodingException("Invalid multipart/form-data. Unknown state?");
                    }
                    mo3859b(mimeEncodingException);
                } else if (bArr[i] == 13) {
                    this.f324j = -4;
                    int i5 = i - i2;
                    ByteBuffer put = C0714n.m5319b((i5 - this.f323i.length) - 2).put(bArr, i2, (i5 - this.f323i.length) - 2);
                    put.flip();
                    C0714n c0714n2 = new C0714n();
                    c0714n2.m5326a(put);
                    super.mo3861a(this, c0714n2);
                    mo5370k();
                } else {
                    mimeEncodingException2 = new MimeEncodingException("Invalid multipart/form-data. Expected \r");
                }
                mo3859b(mimeEncodingException2);
                return;
            } else if (bArr[i] == 13) {
                this.f324j = -4;
                int length = (i - i2) - this.f323i.length;
                if (i2 != 0 || length != 0) {
                    ByteBuffer put2 = C0714n.m5319b(length).put(bArr, i2, length);
                    put2.flip();
                    C0714n c0714n3 = new C0714n();
                    c0714n3.m5326a(put2);
                    super.mo3861a(this, c0714n3);
                }
                mo5369l();
            } else if (bArr[i] != 45) {
                mimeEncodingException2 = new MimeEncodingException("Invalid multipart/form-data. Expected \r or -");
                mo3859b(mimeEncodingException2);
                return;
            } else {
                this.f324j = -2;
            }
            i++;
        }
        if (i2 < bArr.length) {
            int max = Math.max(this.f324j, 0);
            ByteBuffer put3 = C0714n.m5319b((bArr.length - i2) - max).put(bArr, i2, (bArr.length - i2) - max);
            put3.flip();
            C0714n c0714n4 = new C0714n();
            c0714n4.m5326a(put3);
            super.mo3861a(this, c0714n4);
        }
    }

    /* renamed from: a */
    public void m5371a(String str) {
        this.f323i = ("\r\n--" + str).getBytes();
    }

    /* renamed from: k */
    public void mo5370k() {
    }

    /* renamed from: l */
    public void mo5369l() {
    }
}
