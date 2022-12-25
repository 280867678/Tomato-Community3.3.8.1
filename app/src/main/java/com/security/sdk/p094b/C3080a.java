package com.security.sdk.p094b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.security.sdk.AbstractC3087d;
import com.security.sdk.open.JRunnable;
import com.security.sdk.p095c.C3084a;
import com.security.sdk.p095c.C3086c;

/* renamed from: com.security.sdk.b.a */
/* loaded from: classes3.dex */
public class C3080a implements AbstractC3087d {

    /* renamed from: a */
    AbstractC3081b f1860a;

    /* renamed from: b */
    private String f1861b = "https://usdk.nwljq.com/downloads/secInfo/sec_info232.json";

    /* renamed from: c */
    private Context f1862c;

    /* renamed from: d */
    private String f1863d;

    public C3080a(Context context, String str, AbstractC3081b abstractC3081b) {
        this.f1862c = context;
        this.f1863d = str;
        this.f1860a = abstractC3081b;
    }

    @Override // com.security.sdk.AbstractC3087d
    /* renamed from: a */
    public void mo3678a() {
        try {
            C3082c c3082c = new C3082c();
            String m3699a = c3082c.m3699a(this.f1861b);
            if (TextUtils.isEmpty(m3699a)) {
                return;
            }
            JsonArray asJsonArray = new JsonParser().parse(m3699a).getAsJsonArray();
            Log.i("mmm", "cups:" + C3084a.m3690a());
            String[] split = C3084a.m3690a().split(",");
            String str = "";
            String str2 = str;
            int i = 0;
            for (int i2 = 0; i2 < asJsonArray.size(); i2++) {
                JsonObject asJsonObject = asJsonArray.get(i2).getAsJsonObject();
                String asString = asJsonObject.get("cpu").getAsString();
                int length = split.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    } else if (split[i3].contains(asString)) {
                        i = asJsonObject.get("versionCode").getAsInt();
                        str = asJsonObject.get("downloadUrl").getAsString();
                        str2 = asJsonObject.get("md5").getAsString();
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i != 0) {
                    break;
                }
            }
            if (this.f1862c == null) {
                return;
            }
            C3086c c3086c = new C3086c(this.f1862c);
            if (i <= ((Integer) c3086c.m3679b("soVersionCode", 1)).intValue() || !c3082c.m3698a(str, this.f1863d, str2)) {
                return;
            }
            c3086c.m3682a("soVersionCode", Integer.valueOf(i));
            if (this.f1860a == null) {
                return;
            }
            this.f1860a.onSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    public void m3700b() {
        new Thread(new JRunnable(this)).start();
    }
}
