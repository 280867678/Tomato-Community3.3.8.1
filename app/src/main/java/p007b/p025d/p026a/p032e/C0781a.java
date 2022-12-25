package p007b.p025d.p026a.p032e;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;

/* renamed from: b.d.a.e.a */
/* loaded from: classes2.dex */
public class C0781a {
    /* renamed from: a */
    public static Boolean m5037a(String str, int i) {
        try {
            try {
                Socket socket = new Socket();
                socket.bind(new InetSocketAddress(str, i));
                socket.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m5038a(int i) {
        boolean z;
        new Socket();
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    arrayList.add(inetAddresses.nextElement().getHostAddress());
                }
            }
            z = false;
            for (int i2 = 0; i2 < arrayList.size() && (z = m5037a((String) arrayList.get(i2), i).booleanValue()); i2++) {
            }
        } catch (Exception unused) {
        }
        return z;
    }
}
