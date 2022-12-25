package com.p065io.liquidlink.p071f;

import android.content.Context;

/* renamed from: com.io.liquidlink.f.a */
/* loaded from: classes3.dex */
public abstract class AbstractC2157a implements AbstractC2161e {

    /* renamed from: a */
    protected Context f1444a;

    /* renamed from: b */
    private volatile String f1445b = null;

    /* renamed from: c */
    private volatile boolean f1446c = false;

    public AbstractC2157a(Context context) {
        this.f1444a = context;
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2161e
    /* renamed from: a */
    public synchronized String mo3974a(String str) {
        if (this.f1446c) {
            return this.f1445b;
        }
        this.f1445b = mo3980c(str);
        this.f1446c = true;
        return this.f1445b;
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2161e
    /* renamed from: a */
    public synchronized void mo3973a(String str, String str2) {
        if (str2 == null) {
            return;
        }
        if (this.f1446c && str2.equals(this.f1445b)) {
            return;
        }
        if (mo3981b(str, str2)) {
            this.f1446c = true;
        } else {
            this.f1446c = false;
        }
        this.f1445b = str2;
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2161e
    /* renamed from: b */
    public synchronized void mo3972b(String str) {
        if (mo3979d(str)) {
            this.f1445b = null;
            this.f1446c = true;
        }
    }

    /* renamed from: b */
    abstract boolean mo3981b(String str, String str2);

    /* renamed from: c */
    abstract String mo3980c(String str);

    /* renamed from: d */
    abstract boolean mo3979d(String str);
}
