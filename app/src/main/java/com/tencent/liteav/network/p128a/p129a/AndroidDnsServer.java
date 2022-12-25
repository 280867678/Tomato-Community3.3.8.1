package com.tencent.liteav.network.p128a.p129a;

import com.tencent.liteav.network.p128a.Domain;
import com.tencent.liteav.network.p128a.IResolver;
import com.tencent.liteav.network.p128a.NetworkInfo;
import com.tencent.liteav.network.p128a.Record;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: com.tencent.liteav.network.a.a.a */
/* loaded from: classes3.dex */
public final class AndroidDnsServer {
    /* renamed from: a */
    public static InetAddress[] m1182a() {
        String hostAddress;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream()));
            ArrayList arrayList = new ArrayList(5);
            while (true) {
                String readLine = lineNumberReader.readLine();
                if (readLine == null) {
                    break;
                }
                int indexOf = readLine.indexOf("]: [");
                if (indexOf != -1) {
                    String substring = readLine.substring(1, indexOf);
                    String substring2 = readLine.substring(indexOf + 4, readLine.length() - 1);
                    if (substring.endsWith(".dns") || substring.endsWith(".dns1") || substring.endsWith(".dns2") || substring.endsWith(".dns3") || substring.endsWith(".dns4")) {
                        InetAddress byName = InetAddress.getByName(substring2);
                        if (byName != null && (hostAddress = byName.getHostAddress()) != null && hostAddress.length() != 0) {
                            arrayList.add(byName);
                        }
                    }
                }
            }
            if (arrayList.size() <= 0) {
                return null;
            }
            return (InetAddress[]) arrayList.toArray(new InetAddress[arrayList.size()]);
        } catch (IOException e) {
            Logger.getLogger("AndroidDnsServer").log(Level.WARNING, "Exception in findDNSByExec", (Throwable) e);
            return null;
        }
    }

    /* renamed from: b */
    public static InetAddress[] m1181b() {
        InetAddress byName;
        String hostAddress;
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            ArrayList arrayList = new ArrayList(5);
            String[] strArr = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = (String) method.invoke(null, strArr[i]);
                if (str != null && str.length() != 0 && (byName = InetAddress.getByName(str)) != null && (hostAddress = byName.getHostAddress()) != null && hostAddress.length() != 0 && !arrayList.contains(byName)) {
                    arrayList.add(byName);
                }
            }
            if (arrayList.size() > 0) {
                return (InetAddress[]) arrayList.toArray(new InetAddress[arrayList.size()]);
            }
        } catch (Exception e) {
            Logger.getLogger("AndroidDnsServer").log(Level.WARNING, "Exception in findDNSByReflection", (Throwable) e);
        }
        return null;
    }

    /* renamed from: c */
    public static IResolver m1180c() {
        return new IResolver() { // from class: com.tencent.liteav.network.a.a.a.1
            @Override // com.tencent.liteav.network.p128a.IResolver
            /* renamed from: a */
            public Record[] mo1167a(Domain domain, NetworkInfo networkInfo) throws IOException {
                InetAddress[] m1181b = AndroidDnsServer.m1181b();
                if (m1181b == null) {
                    m1181b = AndroidDnsServer.m1182a();
                }
                if (m1181b == null) {
                    throw new IOException("cant get local dns server");
                }
                return new Resolver(m1181b[0]).mo1167a(domain, networkInfo);
            }
        };
    }
}
