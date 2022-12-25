package com.p065io.liquidlink.p072g;

/* renamed from: com.io.liquidlink.g.f */
/* loaded from: classes3.dex */
class C2168f {

    /* renamed from: a */
    byte[] f1472a;

    public C2168f(byte[] bArr) {
        this.f1472a = bArr;
    }

    /* renamed from: a */
    public byte[] m3929a() {
        byte[] bArr = this.f1472a;
        byte[] bArr2 = new byte[bArr.length - 12];
        System.arraycopy(bArr, 12, bArr2, 0, bArr2.length);
        return bArr2;
    }
}
