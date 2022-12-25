package com.p097ta.utdid2.device;

import android.content.Context;
import com.p097ta.utdid2.p098a.p099a.C3208g;

/* renamed from: com.ta.utdid2.device.UTDevice */
/* loaded from: classes3.dex */
public class UTDevice {
    @Deprecated
    public static String getUtdid(Context context) {
        return m3598d(context);
    }

    @Deprecated
    public static String getUtdidForUpdate(Context context) {
        return m3597e(context);
    }

    /* renamed from: d */
    private static String m3598d(Context context) {
        C3219a m3585b = C3220b.m3585b(context);
        return (m3585b == null || C3208g.m3647a(m3585b.m3588f())) ? "ffffffffffffffffffffffff" : m3585b.m3588f();
    }

    /* renamed from: e */
    private static String m3597e(Context context) {
        String m3575h = C3221c.m3584a(context).m3575h();
        return (m3575h == null || C3208g.m3647a(m3575h)) ? "ffffffffffffffffffffffff" : m3575h;
    }
}
