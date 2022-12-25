package com.p065io.liquidlink.p072g;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.io.liquidlink.g.g */
/* loaded from: classes3.dex */
public class C2169g implements Cloneable {

    /* renamed from: i */
    private static final byte[] f1473i = new byte[0];

    /* renamed from: a */
    public int f1474a;

    /* renamed from: b */
    public int f1475b;

    /* renamed from: c */
    public int f1476c;

    /* renamed from: d */
    public int f1477d;

    /* renamed from: e */
    public long f1478e;

    /* renamed from: f */
    public long f1479f;

    /* renamed from: g */
    public byte[] f1480g = f1473i;

    /* renamed from: h */
    public long f1481h;

    /* renamed from: a */
    public int m3928a() {
        return this.f1480g.length + 22;
    }

    /* renamed from: a */
    public ByteBuffer m3927a(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(m3928a());
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putInt(101010256);
        allocate.putShort((short) this.f1474a);
        allocate.putShort((short) this.f1475b);
        allocate.putShort((short) this.f1476c);
        allocate.putShort((short) this.f1477d);
        allocate.putInt((int) this.f1478e);
        allocate.putInt((int) j);
        allocate.putShort((short) this.f1480g.length);
        allocate.put(this.f1480g);
        allocate.flip();
        return allocate;
    }

    /* renamed from: a */
    public void m3926a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            bArr = f1473i;
        }
        this.f1480g = bArr;
    }

    /* renamed from: b */
    public C2169g clone() {
        try {
            return (C2169g) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
