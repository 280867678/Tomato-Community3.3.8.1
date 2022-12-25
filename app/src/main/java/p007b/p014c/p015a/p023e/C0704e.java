package p007b.p014c.p015a.p023e;

import java.io.Closeable;

/* renamed from: b.c.a.e.e */
/* loaded from: classes2.dex */
public class C0704e {
    /* renamed from: a */
    public static void m5336a(Closeable... closeableArr) {
        if (closeableArr == null) {
            return;
        }
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception unused) {
                }
            }
        }
    }
}
