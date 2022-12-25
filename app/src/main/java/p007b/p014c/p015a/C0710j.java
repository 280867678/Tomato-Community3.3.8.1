package p007b.p014c.p015a;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Comparator;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.c.a.j */
/* loaded from: classes2.dex */
public class C0710j implements Comparator<InetAddress> {
    @Override // java.util.Comparator
    /* renamed from: a */
    public int compare(InetAddress inetAddress, InetAddress inetAddress2) {
        boolean z = inetAddress instanceof Inet4Address;
        if (!z || !(inetAddress2 instanceof Inet4Address)) {
            if ((inetAddress instanceof Inet6Address) && (inetAddress2 instanceof Inet6Address)) {
                return 0;
            }
            return (!z || !(inetAddress2 instanceof Inet6Address)) ? 1 : -1;
        }
        return 0;
    }
}
