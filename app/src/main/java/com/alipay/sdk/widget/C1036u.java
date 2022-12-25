package com.alipay.sdk.widget;

import java.util.Iterator;
import java.util.Stack;

/* renamed from: com.alipay.sdk.widget.u */
/* loaded from: classes2.dex */
public class C1036u {

    /* renamed from: a */
    private Stack<C1028p> f1118a = new Stack<>();

    /* renamed from: a */
    public C1028p m4308a() {
        return this.f1118a.pop();
    }

    /* renamed from: a */
    public void m4307a(C1028p c1028p) {
        this.f1118a.push(c1028p);
    }

    /* renamed from: b */
    public boolean m4306b() {
        return this.f1118a.isEmpty();
    }

    /* renamed from: c */
    public void m4305c() {
        if (m4306b()) {
            return;
        }
        Iterator<C1028p> it2 = this.f1118a.iterator();
        while (it2.hasNext()) {
            it2.next().m4331a();
        }
        this.f1118a.clear();
    }
}
