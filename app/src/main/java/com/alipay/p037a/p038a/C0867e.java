package com.alipay.p037a.p038a;

import com.alipay.p037a.p039b.C0875a;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.json.alipay.C5327a;
import org.json.alipay.C5328b;

/* renamed from: com.alipay.a.a.e */
/* loaded from: classes2.dex */
public final class C0867e {

    /* renamed from: a */
    static List<AbstractC0871i> f744a;

    static {
        ArrayList arrayList = new ArrayList();
        f744a = arrayList;
        arrayList.add(new C0874l());
        f744a.add(new C0866d());
        f744a.add(new C0865c());
        f744a.add(new C0870h());
        f744a.add(new C0873k());
        f744a.add(new C0864b());
        f744a.add(new C0863a());
        f744a.add(new C0869g());
    }

    /* renamed from: a */
    public static final <T> T m4883a(Object obj, Type type) {
        T t;
        for (AbstractC0871i abstractC0871i : f744a) {
            if (abstractC0871i.mo4878a(C0875a.m4874a(type)) && (t = (T) abstractC0871i.mo4876a(obj, type)) != null) {
                return t;
            }
        }
        return null;
    }

    /* renamed from: a */
    public static final Object m4882a(String str, Type type) {
        Object c5328b;
        if (str == null || str.length() == 0) {
            return null;
        }
        String trim = str.trim();
        if (trim.startsWith("[") && trim.endsWith("]")) {
            c5328b = new C5327a(trim);
        } else if (!trim.startsWith("{") || !trim.endsWith("}")) {
            return m4883a((Object) trim, type);
        } else {
            c5328b = new C5328b(trim);
        }
        return m4883a(c5328b, type);
    }
}
