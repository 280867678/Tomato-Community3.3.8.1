package com.alipay.p037a.p038a;

import com.alipay.p037a.p039b.C0875a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.json.alipay.C5327a;
import org.json.alipay.C5328b;

/* renamed from: com.alipay.a.a.f */
/* loaded from: classes2.dex */
public final class C0868f {

    /* renamed from: a */
    private static List<AbstractC0872j> f745a;

    static {
        ArrayList arrayList = new ArrayList();
        f745a = arrayList;
        arrayList.add(new C0874l());
        f745a.add(new C0866d());
        f745a.add(new C0865c());
        f745a.add(new C0870h());
        f745a.add(new C0864b());
        f745a.add(new C0863a());
        f745a.add(new C0869g());
    }

    /* renamed from: a */
    public static String m4881a(Object obj) {
        if (obj == null) {
            return null;
        }
        Object m4880b = m4880b(obj);
        if (C0875a.m4875a(m4880b.getClass())) {
            return C5328b.m60c(m4880b.toString());
        }
        if (Collection.class.isAssignableFrom(m4880b.getClass())) {
            return new C5327a((Collection) ((List) m4880b)).toString();
        }
        if (Map.class.isAssignableFrom(m4880b.getClass())) {
            return new C5328b((Map) m4880b).toString();
        }
        throw new IllegalArgumentException("Unsupported Class : " + m4880b.getClass());
    }

    /* renamed from: b */
    public static Object m4880b(Object obj) {
        Object mo4877a;
        if (obj == null) {
            return null;
        }
        for (AbstractC0872j abstractC0872j : f745a) {
            if (abstractC0872j.mo4878a(obj.getClass()) && (mo4877a = abstractC0872j.mo4877a(obj)) != null) {
                return mo4877a;
            }
        }
        throw new IllegalArgumentException("Unsupported Class : " + obj.getClass());
    }
}
