package com.tencent.liteav.network.p128a.p129a;

import com.tencent.liteav.network.p128a.DnsException;
import com.tencent.liteav.network.p128a.Record;
import com.tencent.liteav.network.p128a.p130b.BitSet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.IDN;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;

/* renamed from: com.tencent.liteav.network.a.a.b */
/* loaded from: classes3.dex */
public final class DnsMessage {
    /* renamed from: a */
    public static byte[] m1176a(String str, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        BitSet bitSet = new BitSet();
        bitSet.m1168a(8);
        try {
            dataOutputStream.writeShort((short) i);
            dataOutputStream.writeShort((short) bitSet.m1169a());
            dataOutputStream.writeShort(1);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeShort(0);
            dataOutputStream.flush();
            m1171b(byteArrayOutputStream, str);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: a */
    private static void m1177a(OutputStream outputStream, String str) throws IOException {
        for (String str2 : str.split("[.。．｡]")) {
            byte[] bytes = IDN.toASCII(str2).getBytes();
            outputStream.write(bytes.length);
            outputStream.write(bytes, 0, bytes.length);
        }
        outputStream.write(0);
    }

    /* renamed from: b */
    private static void m1171b(OutputStream outputStream, String str) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        m1177a(outputStream, str);
        dataOutputStream.writeShort(1);
        dataOutputStream.writeShort(1);
    }

    /* renamed from: a */
    public static Record[] m1175a(byte[] bArr, int i, String str) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        if (readUnsignedShort != i) {
            throw new DnsException(str, "the answer id " + readUnsignedShort + " is not match " + i);
        }
        int readUnsignedShort2 = dataInputStream.readUnsignedShort();
        boolean z = true;
        boolean z2 = ((readUnsignedShort2 >> 8) & 1) == 1;
        if (((readUnsignedShort2 >> 7) & 1) != 1) {
            z = false;
        }
        if (!z || !z2) {
            throw new DnsException(str, "the dns server cant support recursion ");
        }
        int readUnsignedShort3 = dataInputStream.readUnsignedShort();
        int readUnsignedShort4 = dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        m1178a(dataInputStream, bArr, readUnsignedShort3);
        return m1172b(dataInputStream, bArr, readUnsignedShort4);
    }

    /* renamed from: a */
    private static String m1179a(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        int readUnsignedByte = dataInputStream.readUnsignedByte();
        if ((readUnsignedByte & 192) == 192) {
            int readUnsignedByte2 = ((readUnsignedByte & 63) << 8) + dataInputStream.readUnsignedByte();
            HashSet hashSet = new HashSet();
            hashSet.add(Integer.valueOf(readUnsignedByte2));
            return m1174a(bArr, readUnsignedByte2, hashSet);
        } else if (readUnsignedByte == 0) {
            return "";
        } else {
            byte[] bArr2 = new byte[readUnsignedByte];
            dataInputStream.readFully(bArr2);
            String unicode = IDN.toUnicode(new String(bArr2));
            String m1179a = m1179a(dataInputStream, bArr);
            if (m1179a.length() <= 0) {
                return unicode;
            }
            return unicode + "." + m1179a;
        }
    }

    /* renamed from: a */
    private static String m1174a(byte[] bArr, int i, HashSet<Integer> hashSet) throws IOException {
        int i2 = bArr[i] & 255;
        if ((i2 & 192) == 192) {
            int i3 = ((i2 & 63) << 8) + (bArr[i + 1] & 255);
            if (hashSet.contains(Integer.valueOf(i3))) {
                throw new DnsException("", "Cyclic offsets detected.");
            }
            hashSet.add(Integer.valueOf(i3));
            return m1174a(bArr, i3, hashSet);
        } else if (i2 == 0) {
            return "";
        } else {
            int i4 = i + 1;
            String str = new String(bArr, i4, i2);
            String m1174a = m1174a(bArr, i4 + i2, hashSet);
            if (m1174a.length() <= 0) {
                return str;
            }
            return str + "." + m1174a;
        }
    }

    /* renamed from: a */
    private static void m1178a(DataInputStream dataInputStream, byte[] bArr, int i) throws IOException {
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                m1179a(dataInputStream, bArr);
                dataInputStream.readUnsignedShort();
                dataInputStream.readUnsignedShort();
                i = i2;
            } else {
                return;
            }
        }
    }

    /* renamed from: b */
    private static Record[] m1172b(DataInputStream dataInputStream, byte[] bArr, int i) throws IOException {
        Record[] recordArr = new Record[i];
        int i2 = 0;
        while (true) {
            int i3 = i - 1;
            if (i > 0) {
                recordArr[i2] = m1173b(dataInputStream, bArr);
                i2++;
                i = i3;
            } else {
                return recordArr;
            }
        }
    }

    /* renamed from: b */
    private static Record m1173b(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        String hostAddress;
        m1179a(dataInputStream, bArr);
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        long readUnsignedShort2 = (dataInputStream.readUnsignedShort() << 16) + dataInputStream.readUnsignedShort();
        int readUnsignedShort3 = dataInputStream.readUnsignedShort();
        if (readUnsignedShort == 1) {
            byte[] bArr2 = new byte[4];
            dataInputStream.readFully(bArr2);
            hostAddress = InetAddress.getByAddress(bArr2).getHostAddress();
        } else if (readUnsignedShort != 5) {
            for (int i = 0; i < readUnsignedShort3; i++) {
                dataInputStream.readByte();
            }
            hostAddress = null;
        } else {
            hostAddress = m1179a(dataInputStream, bArr);
        }
        if (hostAddress == null) {
            throw new UnknownHostException("no record");
        }
        return new Record(hostAddress, readUnsignedShort, (int) readUnsignedShort2, System.currentTimeMillis() / 1000);
    }
}
