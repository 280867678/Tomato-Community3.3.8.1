package com.p097ta.utdid2.device;

import com.p097ta.utdid2.p098a.p099a.C3197a;
import com.p097ta.utdid2.p098a.p099a.C3198b;
import com.p097ta.utdid2.p098a.p099a.C3208g;

/* renamed from: com.ta.utdid2.device.e */
/* loaded from: classes3.dex */
public class C3223e {
    /* renamed from: d */
    public String m3567d(String str) {
        return C3197a.m3662b(str);
    }

    /* renamed from: e */
    public String m3566e(String str) {
        String m3662b = C3197a.m3662b(str);
        if (!C3208g.m3647a(m3662b)) {
            try {
                return new String(C3198b.decode(m3662b, 0));
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }
}
