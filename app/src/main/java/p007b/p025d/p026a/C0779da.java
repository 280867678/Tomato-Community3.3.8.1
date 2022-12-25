package p007b.p025d.p026a;

import p007b.p025d.p026a.p029c.C0762a;

/* renamed from: b.d.a.da */
/* loaded from: classes2.dex */
public class C0779da {

    /* renamed from: a */
    public static final int f602a = C0762a.m5166b().m5165c();

    /* renamed from: b */
    public int f603b = 0;

    /* renamed from: c */
    public int f604c = 0;

    /* renamed from: d */
    public int f605d = 0;

    /* renamed from: g */
    public boolean f607g = false;

    /* renamed from: f */
    public byte[] f606f = new byte[f602a];

    /* renamed from: a */
    public int m5044a() {
        return this.f605d;
    }

    /* renamed from: a */
    public int m5042a(byte[] bArr, int i) {
        if (i <= 0) {
            return 0;
        }
        int i2 = this.f604c;
        int i3 = this.f605d;
        if (i3 <= i) {
            i = i3;
        }
        if (i == 0) {
            return i;
        }
        int i4 = i2 + i;
        int i5 = f602a;
        if (i4 > i5) {
            int i6 = i5 - i2;
            System.arraycopy(this.f606f, i2, bArr, 0, i6);
            System.arraycopy(this.f606f, 0, bArr, i6, i - i6);
        } else {
            System.arraycopy(this.f606f, i2, bArr, 0, i);
        }
        this.f604c = i4 % f602a;
        this.f605d -= i;
        return i;
    }

    /* renamed from: a */
    public boolean m5043a(int i) {
        int i2;
        if (i >= 0) {
            int i3 = this.f605d;
            if (i3 < i + 1) {
                return false;
            }
            this.f604c = (this.f604c + i) % f602a;
            this.f605d = i3 - i;
            return true;
        }
        if (!this.f607g) {
            i2 = this.f604c;
        } else {
            int i4 = this.f604c - this.f603b;
            int i5 = f602a;
            i2 = (i4 + i5) % i5;
        }
        if (Math.abs(i) > i2) {
            return false;
        }
        int i6 = f602a;
        this.f604c = ((this.f604c + i) + i6) % i6;
        this.f605d += Math.abs(i);
        return true;
    }

    /* renamed from: b */
    public int m5041b() {
        return f602a - this.f605d;
    }

    /* renamed from: b */
    public int m5040b(byte[] bArr, int i) {
        if (i <= 0) {
            return 0;
        }
        int i2 = f602a - this.f605d;
        if (i2 <= i) {
            i = i2;
        }
        if (i == 0) {
            return i;
        }
        if (this.f603b + i >= f602a) {
            this.f607g = true;
        }
        int i3 = this.f603b;
        int i4 = i3 + i;
        int i5 = f602a;
        if (i4 > i5) {
            int i6 = i5 - i3;
            System.arraycopy(bArr, 0, this.f606f, i3, i6);
            System.arraycopy(bArr, i6, this.f606f, 0, i - i6);
        } else {
            System.arraycopy(bArr, 0, this.f606f, i3, i);
        }
        this.f605d += i;
        this.f603b = (this.f603b + i) % f602a;
        return i;
    }

    /* renamed from: c */
    public void m5039c() {
        this.f603b = 0;
        this.f604c = 0;
        this.f605d = 0;
        this.f607g = false;
    }
}
