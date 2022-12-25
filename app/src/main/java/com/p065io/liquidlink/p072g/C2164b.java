package com.p065io.liquidlink.p072g;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/* renamed from: com.io.liquidlink.g.b */
/* loaded from: classes3.dex */
public class C2164b {
    /* renamed from: a */
    private static int m3950a(FileChannel fileChannel, long j, ByteBuffer byteBuffer) {
        int read;
        int i = 0;
        while (byteBuffer.hasRemaining() && (read = fileChannel.read(byteBuffer, j)) != -1) {
            j += read;
            i += read;
        }
        return i;
    }

    /* renamed from: a */
    private static int m3949a(FileChannel fileChannel, long j, byte[] bArr, int i, int i2) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr, i, i2);
        int i3 = 0;
        while (i3 < i2) {
            int read = fileChannel.read(wrap, i3 + j);
            if (read == -1) {
                break;
            }
            i3 += read;
        }
        return i3;
    }

    /* renamed from: a */
    public static C2163a m3951a(FileChannel fileChannel) {
        C2169g m3946b = m3946b(fileChannel);
        if (m3946b == null) {
            return null;
        }
        long j = m3946b.f1479f;
        if (j < 32) {
            return new C2163a(m3946b);
        }
        byte[] bArr = new byte[24];
        m3949a(fileChannel, j - bArr.length, bArr, 0, bArr.length);
        long m3940c = C2165c.m3940c(bArr, bArr.length - 24, ByteOrder.LITTLE_ENDIAN);
        long m3940c2 = C2165c.m3940c(bArr, bArr.length - 16, ByteOrder.LITTLE_ENDIAN);
        long m3940c3 = C2165c.m3940c(bArr, bArr.length - 8, ByteOrder.LITTLE_ENDIAN);
        if (m3940c2 != 2334950737559900225L || m3940c3 != 3617552046287187010L) {
            return new C2163a(m3946b);
        }
        int i = (int) (8 + m3940c);
        long j2 = i;
        long j3 = m3946b.f1479f - j2;
        if (i < 32 || j3 < 0) {
            return new C2163a(m3946b);
        }
        if (j2 > 20971520) {
            return new C2163a(m3946b);
        }
        ByteBuffer allocate = ByteBuffer.allocate(i - 24);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        if (m3950a(fileChannel, j3, allocate) != allocate.capacity() || ((ByteBuffer) allocate.flip()).getLong() != m3940c) {
            return new C2163a(m3946b);
        }
        C2167e c2167e = new C2167e(j3);
        while (allocate.hasRemaining()) {
            long j4 = allocate.getLong();
            int i2 = allocate.getInt();
            byte[] bArr2 = new byte[(int) (j4 - 4)];
            allocate.get(bArr2, 0, bArr2.length);
            c2167e.m3937a(i2, bArr2);
        }
        return new C2163a(c2167e, m3946b);
    }

    /* renamed from: a */
    private static void m3948a(FileChannel fileChannel, FileChannel fileChannel2, long j, long j2) {
        while (j2 > 0) {
            long transferTo = fileChannel.transferTo(j, j2, fileChannel2);
            j += transferTo;
            j2 -= transferTo;
        }
    }

    /* renamed from: a */
    public static void m3947a(byte[] bArr, File file, File file2) {
        ByteBuffer[] m3931e;
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            FileChannel channel = fileInputStream.getChannel();
            FileChannel channel2 = fileOutputStream.getChannel();
            C2163a m3951a = m3951a(channel);
            channel.position(0L);
            if (m3951a == null) {
                m3948a(channel, channel2, 0L, channel.size());
                return;
            }
            m3951a.m3955a(bArr);
            C2167e m3954b = m3951a.m3954b();
            C2169g m3956a = m3951a.m3956a();
            if (m3954b != null) {
                m3948a(channel, channel2, 0L, m3954b.m3935b());
                for (ByteBuffer byteBuffer : m3954b.m3931e()) {
                    while (byteBuffer.hasRemaining()) {
                        channel2.write(byteBuffer);
                    }
                }
                m3948a(channel, channel2, m3956a.f1479f, m3956a.f1481h - m3956a.f1479f);
            } else {
                m3948a(channel, channel2, 0L, m3956a.f1481h);
            }
            ByteBuffer m3927a = m3956a.m3927a(m3954b != null ? m3954b.m3939a() : m3956a.f1479f);
            while (m3927a.hasRemaining()) {
                channel2.write(m3927a);
            }
        } finally {
            fileInputStream.close();
            fileOutputStream.close();
        }
    }

    /* renamed from: b */
    private static C2169g m3946b(FileChannel fileChannel) {
        int i;
        byte[] bArr = new byte[128];
        long size = fileChannel.size();
        if (size < 22) {
            return null;
        }
        long j = 0;
        long length = (size > 65557 ? size - 65557 : 0L) - (bArr.length - 22);
        long length2 = size - bArr.length;
        while (length2 >= length) {
            int i2 = (length2 > j ? 1 : (length2 == j ? 0 : -1));
            if (i2 < 0) {
                int i3 = (int) (-length2);
                Arrays.fill(bArr, 0, i3, (byte) 0);
                i = i3;
            } else {
                i = 0;
            }
            long j2 = length2;
            m3949a(fileChannel, i2 < 0 ? j : length2, bArr, i, bArr.length - i);
            for (int length3 = bArr.length - 22; length3 >= 0; length3--) {
                if (bArr[length3 + 0] == 80 && bArr[length3 + 1] == 75 && bArr[length3 + 2] == 5 && bArr[length3 + 3] == 6) {
                    int m3941b = C2165c.m3941b(bArr, length3 + 20, ByteOrder.LITTLE_ENDIAN) & 65535;
                    long j3 = j2 + length3;
                    if (j3 + 22 + m3941b == size) {
                        C2169g c2169g = new C2169g();
                        c2169g.f1481h = j3;
                        c2169g.f1474a = C2165c.m3941b(bArr, length3 + 4, ByteOrder.LITTLE_ENDIAN) & 65535;
                        c2169g.f1475b = C2165c.m3941b(bArr, length3 + 6, ByteOrder.LITTLE_ENDIAN) & 65535;
                        c2169g.f1476c = C2165c.m3941b(bArr, length3 + 8, ByteOrder.LITTLE_ENDIAN) & 65535;
                        c2169g.f1477d = 65535 & C2165c.m3941b(bArr, length3 + 10, ByteOrder.LITTLE_ENDIAN);
                        c2169g.f1478e = C2165c.m3942a(bArr, length3 + 12, ByteOrder.LITTLE_ENDIAN) & 4294967295L;
                        c2169g.f1479f = C2165c.m3942a(bArr, length3 + 16, ByteOrder.LITTLE_ENDIAN) & 4294967295L;
                        if (m3941b > 0) {
                            c2169g.f1480g = new byte[m3941b];
                            m3949a(fileChannel, c2169g.f1481h + 22, c2169g.f1480g, 0, m3941b);
                        }
                        return c2169g;
                    }
                }
            }
            length2 = j2 - (bArr.length - 22);
            j = 0;
        }
        return null;
    }
}
