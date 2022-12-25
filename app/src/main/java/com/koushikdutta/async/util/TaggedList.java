package com.koushikdutta.async.util;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TaggedList<T> extends ArrayList<T> {

    /* renamed from: a */
    public Object f1554a;

    /* renamed from: a */
    public synchronized <V> V m3849a() {
        return (V) this.f1554a;
    }

    /* renamed from: a */
    public synchronized <V> void m3848a(V v) {
        if (this.f1554a == null) {
            this.f1554a = v;
        }
    }
}
