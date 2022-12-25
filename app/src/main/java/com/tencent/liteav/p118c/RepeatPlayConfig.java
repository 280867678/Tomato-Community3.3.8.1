package com.tencent.liteav.p118c;

import com.tencent.liteav.p124i.TXCVideoEditConstants;
import java.util.List;

/* renamed from: com.tencent.liteav.c.f */
/* loaded from: classes3.dex */
public class RepeatPlayConfig {

    /* renamed from: a */
    private static RepeatPlayConfig f3351a;

    /* renamed from: b */
    private List<TXCVideoEditConstants.C3518h> f3352b;

    /* renamed from: a */
    public static RepeatPlayConfig m2482a() {
        if (f3351a == null) {
            synchronized (ReverseConfig.class) {
                if (f3351a == null) {
                    f3351a = new RepeatPlayConfig();
                }
            }
        }
        return f3351a;
    }

    /* renamed from: a */
    public void m2481a(List<TXCVideoEditConstants.C3518h> list) {
        this.f3352b = list;
    }

    /* renamed from: b */
    public TXCVideoEditConstants.C3518h m2480b() {
        List<TXCVideoEditConstants.C3518h> list = this.f3352b;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.f3352b.get(0);
    }

    /* renamed from: c */
    public void m2479c() {
        List<TXCVideoEditConstants.C3518h> list = this.f3352b;
        if (list != null) {
            list.clear();
        }
        this.f3352b = null;
    }
}
