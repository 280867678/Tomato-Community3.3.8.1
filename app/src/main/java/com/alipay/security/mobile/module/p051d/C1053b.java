package com.alipay.security.mobile.module.p051d;

import com.alipay.security.mobile.module.http.p052v2.AbstractC1064a;
import com.alipay.security.mobile.module.p047a.C1041b;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import org.json.JSONObject;

/* renamed from: com.alipay.security.mobile.module.d.b */
/* loaded from: classes2.dex */
public final class C1053b {

    /* renamed from: a */
    private File f1131a;

    /* renamed from: b */
    private AbstractC1064a f1132b;

    public C1053b(String str, AbstractC1064a abstractC1064a) {
        this.f1131a = null;
        this.f1132b = null;
        this.f1131a = new File(str);
        this.f1132b = abstractC1064a;
    }

    /* renamed from: a */
    private static String m4208a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", DatabaseFieldConfigLoader.FIELD_NAME_ID);
            jSONObject.put("error", str);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public synchronized void m4207b() {
        if (this.f1131a == null) {
            return;
        }
        if (this.f1131a.exists() && this.f1131a.isDirectory() && this.f1131a.list().length != 0) {
            ArrayList arrayList = new ArrayList();
            for (String str : this.f1131a.list()) {
                arrayList.add(str);
            }
            Collections.sort(arrayList);
            String str2 = (String) arrayList.get(arrayList.size() - 1);
            int size = arrayList.size();
            if (str2.equals(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".log")) {
                if (arrayList.size() < 2) {
                    return;
                }
                str2 = (String) arrayList.get(arrayList.size() - 2);
                size--;
            }
            if (!this.f1132b.mo4186a(m4208a(C1041b.m4284a(this.f1131a.getAbsolutePath(), str2)))) {
                size--;
            }
            for (int i = 0; i < size; i++) {
                new File(this.f1131a, (String) arrayList.get(i)).delete();
            }
        }
    }

    /* renamed from: a */
    public final void m4210a() {
        new Thread(new RunnableC1054c(this)).start();
    }
}
