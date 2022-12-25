package com.tencent.liteav.network.p128a.p129a;

import com.tencent.liteav.network.p128a.DnsException;
import com.tencent.liteav.network.p128a.Domain;
import com.tencent.liteav.network.p128a.IResolver;
import com.tencent.liteav.network.p128a.NetworkInfo;
import com.tencent.liteav.network.p128a.Record;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/* renamed from: com.tencent.liteav.network.a.a.c */
/* loaded from: classes3.dex */
public final class Resolver implements IResolver {

    /* renamed from: b */
    private static final Random f4798b = new Random();

    /* renamed from: a */
    final InetAddress f4799a;

    /* renamed from: c */
    private final int f4800c;

    public Resolver(InetAddress inetAddress) {
        this(inetAddress, 10);
    }

    public Resolver(InetAddress inetAddress, int i) {
        this.f4799a = inetAddress;
        this.f4800c = i;
    }

    @Override // com.tencent.liteav.network.p128a.IResolver
    /* renamed from: a */
    public Record[] mo1167a(Domain domain, NetworkInfo networkInfo) throws IOException {
        int nextInt;
        synchronized (f4798b) {
            nextInt = f4798b.nextInt() & 255;
        }
        byte[] m1170a = m1170a(DnsMessage.m1176a(domain.f4801a, nextInt));
        if (m1170a == null) {
            throw new DnsException(domain.f4801a, "cant get answer");
        }
        return DnsMessage.m1175a(m1170a, nextInt, domain.f4801a);
    }

    /* renamed from: a */
    private byte[] m1170a(byte[] bArr) throws IOException {
        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket();
        } catch (Throwable th) {
            th = th;
            datagramSocket = null;
        }
        try {
            DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length, this.f4799a, 53);
            datagramSocket.setSoTimeout(this.f4800c * 1000);
            datagramSocket.send(datagramPacket);
            DatagramPacket datagramPacket2 = new DatagramPacket(new byte[1500], 1500);
            datagramSocket.receive(datagramPacket2);
            byte[] data = datagramPacket2.getData();
            datagramSocket.close();
            return data;
        } catch (Throwable th2) {
            th = th2;
            if (datagramSocket != null) {
                datagramSocket.close();
            }
            throw th;
        }
    }
}
