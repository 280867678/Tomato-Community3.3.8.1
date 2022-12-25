package com.alipay.sdk.protocol;

import android.text.TextUtils;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.tid.C0992b;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.protocol.b */
/* loaded from: classes2.dex */
public class C0987b {

    /* renamed from: a */
    private EnumC0986a f1011a;

    /* renamed from: c */
    private String[] f1012c;

    public C0987b(String str, EnumC0986a enumC0986a) {
        this.f1011a = enumC0986a;
    }

    /* renamed from: a */
    public static void m4501a(C0987b c0987b) {
        String[] m4496c = c0987b.m4496c();
        if (m4496c.length == 3 && TextUtils.equals("tid", m4496c[0])) {
            C0992b m4467a = C0992b.m4467a(C0990b.m4478a().m4476b());
            if (TextUtils.isEmpty(m4496c[1]) || TextUtils.isEmpty(m4496c[2])) {
                return;
            }
            m4467a.m4466a(m4496c[1], m4496c[2]);
        }
    }

    /* renamed from: a */
    public static List<C0987b> m4499a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        String[] m4497b = m4497b(jSONObject.optString("name", ""));
        for (int i = 0; i < m4497b.length; i++) {
            EnumC0986a m4502a = EnumC0986a.m4502a(m4497b[i]);
            if (m4502a != EnumC0986a.None) {
                C0987b c0987b = new C0987b(m4497b[i], m4502a);
                c0987b.f1012c = m4500a(m4497b[i]);
                arrayList.add(c0987b);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private static String[] m4500a(String str) {
        ArrayList arrayList = new ArrayList();
        int indexOf = str.indexOf(40);
        int lastIndexOf = str.lastIndexOf(41);
        if (indexOf == -1 || lastIndexOf == -1 || lastIndexOf <= indexOf) {
            return null;
        }
        for (String str2 : str.substring(indexOf + 1, lastIndexOf).split("' *, *'", -1)) {
            arrayList.add(str2.trim().replaceAll("'", "").replaceAll("\"", ""));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    /* renamed from: b */
    private static String[] m4497b(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.split(";");
        }
        return null;
    }

    /* renamed from: b */
    public EnumC0986a m4498b() {
        return this.f1011a;
    }

    /* renamed from: c */
    public String[] m4496c() {
        return this.f1012c;
    }
}
