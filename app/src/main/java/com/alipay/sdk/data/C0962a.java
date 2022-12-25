package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1004j;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.data.a */
/* loaded from: classes2.dex */
public final class C0962a {

    /* renamed from: F */
    private static C0962a f962F;

    /* renamed from: w */
    private int f969w = ConstantUtils.MAX_ITEM_NUM;

    /* renamed from: x */
    private boolean f970x = false;

    /* renamed from: y */
    private String f971y = "https://h5.m.taobao.com/mlapp/olist.html";

    /* renamed from: z */
    private int f972z = 10;

    /* renamed from: A */
    private boolean f963A = true;

    /* renamed from: B */
    private boolean f964B = true;

    /* renamed from: a */
    public boolean f968a = false;

    /* renamed from: C */
    private boolean f965C = false;

    /* renamed from: D */
    private boolean f966D = false;

    /* renamed from: E */
    private List<C0963a> f967E = null;

    /* renamed from: a */
    public int m4597a() {
        int i = this.f969w;
        if (i < 1000 || i > 20000) {
            C0996c.m4438a("DynCon", "time(def) = 10000");
            return ConstantUtils.MAX_ITEM_NUM;
        }
        C0996c.m4438a("DynCon", "time = " + this.f969w);
        return this.f969w;
    }

    /* renamed from: b */
    public boolean m4590b() {
        return this.f970x;
    }

    /* renamed from: c */
    public boolean m4588c() {
        return this.f963A;
    }

    /* renamed from: d */
    public boolean m4587d() {
        return this.f964B;
    }

    /* renamed from: e */
    public String m4586e() {
        return this.f971y;
    }

    /* renamed from: f */
    public int m4585f() {
        return this.f972z;
    }

    /* renamed from: g */
    public boolean m4584g() {
        return this.f965C;
    }

    /* renamed from: h */
    public boolean m4583h() {
        return this.f966D;
    }

    /* renamed from: i */
    public List<C0963a> m4582i() {
        return this.f967E;
    }

    /* renamed from: j */
    public static C0962a m4581j() {
        if (f962F == null) {
            f962F = new C0962a();
            f962F.m4580k();
        }
        return f962F;
    }

    /* renamed from: k */
    private void m4580k() {
        m4592a(C1004j.m4407b(C0988a.m4495a(), C0990b.m4478a().m4476b(), "alipay_cashier_dynamic_config", null));
    }

    /* renamed from: a */
    private void m4592a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            m4591a(new JSONObject(str));
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m4594a(C0988a c0988a) {
        try {
            C1004j.m4408a(c0988a, C0990b.m4478a().m4476b(), "alipay_cashier_dynamic_config", m4579l().toString());
        } catch (Exception e) {
            C0996c.m4436a(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m4589b(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONObject("st_sdk_config");
            if (optJSONObject != null) {
                m4591a(optJSONObject);
            } else {
                C0996c.m4433c("DynCon", "empty config");
            }
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
    }

    /* renamed from: l */
    private JSONObject m4579l() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("timeout", m4597a());
        jSONObject.put("h5_port_degrade", m4590b());
        jSONObject.put("tbreturl", m4586e());
        jSONObject.put("configQueryInterval", m4585f());
        jSONObject.put("launchAppSwitch", C0963a.m4577a(m4582i()));
        jSONObject.put("scheme_pay_2", m4588c());
        jSONObject.put("intercept_batch", m4587d());
        jSONObject.put("deg_log_mcgw", m4584g());
        jSONObject.put("deg_start_srv_first", m4583h());
        return jSONObject;
    }

    /* renamed from: a */
    private void m4591a(JSONObject jSONObject) {
        this.f969w = jSONObject.optInt("timeout", ConstantUtils.MAX_ITEM_NUM);
        this.f970x = jSONObject.optBoolean("h5_port_degrade", false);
        this.f971y = jSONObject.optString("tbreturl", "https://h5.m.taobao.com/mlapp/olist.html").trim();
        this.f972z = jSONObject.optInt("configQueryInterval", 10);
        this.f967E = C0963a.m4576a(jSONObject.optJSONArray("launchAppSwitch"));
        this.f963A = jSONObject.optBoolean("scheme_pay_2", true);
        this.f964B = jSONObject.optBoolean("intercept_batch", true);
        this.f965C = jSONObject.optBoolean("deg_log_mcgw", false);
        this.f966D = jSONObject.optBoolean("deg_start_srv_first", false);
    }

    /* renamed from: a */
    public void m4593a(C0988a c0988a, Context context) {
        new Thread(new RunnableC0964b(this, c0988a, context)).start();
    }

    /* renamed from: com.alipay.sdk.data.a$a */
    /* loaded from: classes2.dex */
    public static final class C0963a {

        /* renamed from: a */
        public final String f973a;

        /* renamed from: b */
        public final int f974b;

        /* renamed from: c */
        public final String f975c;

        public C0963a(String str, int i, String str2) {
            this.f973a = str;
            this.f974b = i;
            this.f975c = str2;
        }

        /* renamed from: a */
        public static C0963a m4575a(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            return new C0963a(jSONObject.optString("pn"), jSONObject.optInt("v", 0), jSONObject.optString("pk"));
        }

        /* renamed from: a */
        public static List<C0963a> m4576a(JSONArray jSONArray) {
            if (jSONArray == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                C0963a m4575a = m4575a(jSONArray.optJSONObject(i));
                if (m4575a != null) {
                    arrayList.add(m4575a);
                }
            }
            return arrayList;
        }

        /* renamed from: a */
        public static JSONObject m4578a(C0963a c0963a) {
            if (c0963a == null) {
                return null;
            }
            try {
                return new JSONObject().put("pn", c0963a.f973a).put("v", c0963a.f974b).put("pk", c0963a.f975c);
            } catch (JSONException e) {
                C0996c.m4436a(e);
                return null;
            }
        }

        /* renamed from: a */
        public static JSONArray m4577a(List<C0963a> list) {
            if (list == null) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            for (C0963a c0963a : list) {
                jSONArray.put(m4578a(c0963a));
            }
            return jSONArray;
        }

        public String toString() {
            return String.valueOf(m4578a(this));
        }
    }
}
