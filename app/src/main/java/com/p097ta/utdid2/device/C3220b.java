package com.p097ta.utdid2.device;

import android.content.Context;
import com.p097ta.utdid2.p098a.p099a.C3204e;
import com.p097ta.utdid2.p098a.p099a.C3208g;
import java.util.zip.Adler32;

/* renamed from: com.ta.utdid2.device.b */
/* loaded from: classes3.dex */
public class C3220b {

    /* renamed from: a */
    private static C3219a f1945a;

    /* renamed from: d */
    static final Object f1946d = new Object();

    /* renamed from: a */
    static long m3586a(C3219a c3219a) {
        if (c3219a != null) {
            String format = String.format("%s%s%s%s%s", c3219a.m3588f(), c3219a.getDeviceId(), Long.valueOf(c3219a.m3596a()), c3219a.getImsi(), c3219a.m3590e());
            if (C3208g.m3647a(format)) {
                return 0L;
            }
            Adler32 adler32 = new Adler32();
            adler32.reset();
            adler32.update(format.getBytes());
            return adler32.getValue();
        }
        return 0L;
    }

    /* renamed from: a */
    private static C3219a m3587a(Context context) {
        if (context != null) {
            synchronized (f1946d) {
                String value = C3221c.m3584a(context).getValue();
                if (C3208g.m3647a(value)) {
                    return null;
                }
                if (value.endsWith("\n")) {
                    value = value.substring(0, value.length() - 1);
                }
                C3219a c3219a = new C3219a();
                long currentTimeMillis = System.currentTimeMillis();
                String m3656a = C3204e.m3656a(context);
                String m3652c = C3204e.m3652c(context);
                c3219a.m3591d(m3656a);
                c3219a.m3593b(m3656a);
                c3219a.m3594b(currentTimeMillis);
                c3219a.m3592c(m3652c);
                c3219a.m3589e(value);
                c3219a.m3595a(m3586a(c3219a));
                return c3219a;
            }
        }
        return null;
    }

    /* renamed from: b */
    public static synchronized C3219a m3585b(Context context) {
        synchronized (C3220b.class) {
            if (f1945a != null) {
                return f1945a;
            } else if (context == null) {
                return null;
            } else {
                C3219a m3587a = m3587a(context);
                f1945a = m3587a;
                return m3587a;
            }
        }
    }
}
