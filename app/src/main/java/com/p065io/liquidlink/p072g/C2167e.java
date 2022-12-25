package com.p065io.liquidlink.p072g;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* renamed from: com.io.liquidlink.g.e */
/* loaded from: classes3.dex */
public class C2167e implements Cloneable {

    /* renamed from: a */
    private final long f1470a;

    /* renamed from: b */
    private final List f1471b = new ArrayList();

    public C2167e(long j) {
        this.f1470a = j;
    }

    /* renamed from: a */
    private byte[] m3938a(int i) {
        for (C2166d c2166d : this.f1471b) {
            if (((Integer) c2166d.f1468a).intValue() == i) {
                return ((C2168f) c2166d.f1469b).m3929a();
            }
        }
        return null;
    }

    /* renamed from: b */
    private void m3934b(int i) {
        Iterator it2 = this.f1471b.iterator();
        while (it2.hasNext()) {
            if (((Integer) ((C2166d) it2.next()).f1468a).intValue() == i) {
                it2.remove();
            }
        }
    }

    /* renamed from: a */
    public long m3939a() {
        return this.f1470a + m3933c();
    }

    /* renamed from: a */
    public void m3937a(int i, byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 8 + 4];
        ByteBuffer order = ByteBuffer.wrap(bArr2).order(ByteOrder.LITTLE_ENDIAN);
        order.putLong(bArr2.length - 8).putInt(i);
        order.put(bArr);
        C2166d c2166d = new C2166d(Integer.valueOf(i), new C2168f(bArr2));
        ListIterator listIterator = this.f1471b.listIterator();
        while (listIterator.hasNext()) {
            if (((Integer) ((C2166d) listIterator.next()).f1468a).intValue() == i) {
                listIterator.set(c2166d);
                return;
            }
        }
        this.f1471b.add(c2166d);
    }

    /* renamed from: a */
    public void m3936a(byte[] bArr) {
        if (bArr == null) {
            m3934b(987894612);
        } else {
            m3937a(987894612, bArr);
        }
    }

    /* renamed from: b */
    public long m3935b() {
        return this.f1470a;
    }

    /* renamed from: c */
    public long m3933c() {
        long j = 32;
        for (C2166d c2166d : this.f1471b) {
            j += ((C2168f) c2166d.f1469b).f1472a.length;
        }
        return j;
    }

    /* renamed from: d */
    public byte[] m3932d() {
        return m3938a(987894612);
    }

    /* renamed from: e */
    public ByteBuffer[] m3931e() {
        ByteBuffer[] byteBufferArr = new ByteBuffer[this.f1471b.size() + 2];
        long m3933c = m3933c() - 8;
        byteBufferArr[0] = (ByteBuffer) ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(m3933c).flip();
        int i = 1;
        for (C2166d c2166d : this.f1471b) {
            byteBufferArr[i] = ByteBuffer.wrap(((C2168f) c2166d.f1469b).f1472a);
            i++;
        }
        byteBufferArr[i] = (ByteBuffer) ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN).putLong(m3933c).putLong(2334950737559900225L).putLong(3617552046287187010L).flip();
        return byteBufferArr;
    }

    /* renamed from: f */
    public C2167e clone() {
        C2167e c2167e = new C2167e(this.f1470a);
        for (C2166d c2166d : this.f1471b) {
            c2167e.f1471b.add(new C2166d(c2166d.f1468a, c2166d.f1469b));
        }
        return c2167e;
    }
}
