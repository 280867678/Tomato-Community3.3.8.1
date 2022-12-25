package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.app.PayResultActivity;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.cons.C0961a;
import com.alipay.sdk.data.C0962a;
import com.alipay.sdk.encrypt.C0967a;
import com.alipay.sdk.packet.impl.C0985f;
import com.alipay.sdk.protocol.C0987b;
import com.alipay.sdk.protocol.EnumC0986a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.tid.C0992b;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C0998e;
import com.alipay.sdk.util.C1003i;
import com.alipay.sdk.util.C1006l;
import com.alipay.sdk.util.C1008n;
import com.alipay.sdk.util.H5PayResultModel;
import com.alipay.sdk.widget.C1011a;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PayTask {

    /* renamed from: a */
    static final Object f903a = C0998e.class;

    /* renamed from: h */
    private static long f904h = 0;

    /* renamed from: j */
    private static long f905j = -1;

    /* renamed from: b */
    private Activity f906b;

    /* renamed from: c */
    private C1011a f907c;

    /* renamed from: d */
    private String f908d = "wappaygw.alipay.com/service/rest.htm";

    /* renamed from: e */
    private String f909e = "mclient.alipay.com/service/rest.htm";

    /* renamed from: f */
    private String f910f = "mclient.alipay.com/home/exterfaceAssign.htm";

    /* renamed from: g */
    private Map<String, C0943a> f911g = new HashMap();

    public String getVersion() {
        return "15.7.4";
    }

    public PayTask(Activity activity) {
        this.f906b = activity;
        C0990b.m4478a().m4477a(this.f906b);
        this.f907c = new C1011a(activity, "去支付宝付款");
    }

    public synchronized String pay(String str, boolean z) {
        return m4670a(new C0988a(this.f906b, str, "pay"), str, z);
    }

    /* renamed from: a */
    private synchronized String m4670a(C0988a c0988a, String str, boolean z) {
        if (m4663b()) {
            C0954a.m4633a(c0988a, "biz", "RepPay", "");
            return C0952j.m4642d();
        }
        if (z) {
            showLoading();
        }
        if (str.contains("payment_inst=")) {
            String substring = str.substring(str.indexOf("payment_inst=") + 13);
            int indexOf = substring.indexOf(38);
            if (indexOf > 0) {
                substring = substring.substring(0, indexOf);
            }
            C0951i.m4650a(substring.replaceAll("\"", "").toLowerCase(Locale.getDefault()).replaceAll(RechargeTypeAndMoney.RECHARGE_ALIPAY, ""));
        } else {
            C0951i.m4650a("");
        }
        if (str.contains("service=alipay.acquire.mr.ord.createandpay")) {
            C0961a.f961u = true;
        }
        if (C0961a.f961u) {
            if (str.startsWith("https://wappaygw.alipay.com/home/exterfaceAssign.htm?")) {
                str = str.substring(str.indexOf("https://wappaygw.alipay.com/home/exterfaceAssign.htm?") + 53);
            } else if (str.startsWith("https://mclient.alipay.com/home/exterfaceAssign.htm?")) {
                str = str.substring(str.indexOf("https://mclient.alipay.com/home/exterfaceAssign.htm?") + 52);
            }
        }
        C0996c.m4435b("mspl", "pay prepared: " + str);
        String m4668a = m4668a(str, c0988a);
        C0996c.m4435b("mspl", "pay raw result: " + m4668a);
        C1003i.m4411a(c0988a, this.f906b.getApplicationContext(), m4668a);
        C0954a.m4628b(c0988a, "biz", "PgReturn", "" + SystemClock.elapsedRealtime());
        C0962a.m4581j().m4593a(c0988a, this.f906b.getApplicationContext());
        dismissLoading();
        C0954a.m4629b(this.f906b.getApplicationContext(), c0988a, str, c0988a.f1013p);
        C0996c.m4435b("mspl", "pay returning: " + m4668a);
        return m4668a;
    }

    public synchronized Map<String, String> payV2(String str, boolean z) {
        C0988a c0988a;
        c0988a = new C0988a(this.f906b, str, "payV2");
        return C1006l.m4402a(c0988a, m4670a(c0988a, str, z));
    }

    public synchronized String fetchTradeToken() {
        return C1003i.m4412a(new C0988a(this.f906b, "", "fetchTradeToken"), this.f906b.getApplicationContext());
    }

    public synchronized boolean payInterceptorWithUrl(String str, boolean z, H5PayCallback h5PayCallback) {
        String fetchOrderInfoFromH5PayUrl;
        fetchOrderInfoFromH5PayUrl = fetchOrderInfoFromH5PayUrl(str);
        if (!TextUtils.isEmpty(fetchOrderInfoFromH5PayUrl)) {
            C0996c.m4435b("mspl", "intercepted: " + fetchOrderInfoFromH5PayUrl);
            new Thread(new RunnableC0949g(this, fetchOrderInfoFromH5PayUrl, z, h5PayCallback)).start();
        }
        return !TextUtils.isEmpty(fetchOrderInfoFromH5PayUrl);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x00f1, code lost:
        if (r9.startsWith("http://" + r16.f909e) != false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x01a1, code lost:
        if (r9.startsWith("http://" + r16.f910f) != false) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0041, code lost:
        if (r9.startsWith("http://" + r16.f908d) != false) goto L95;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized String fetchOrderInfoFromH5PayUrl(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                String trim = str.trim();
                if (!trim.startsWith("https://" + this.f908d)) {
                }
                String trim2 = trim.replaceFirst("(http|https)://" + this.f908d + "\\?", "").trim();
                if (!TextUtils.isEmpty(trim2)) {
                    String m4383a = C1008n.m4383a("<request_token>", "</request_token>", C1008n.m4376b(trim2).get("req_data"));
                    C0988a c0988a = new C0988a(this.f906b, "", "");
                    return "_input_charset=\"utf-8\"&ordertoken=\"" + m4383a + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + c0988a.m4492a("sc", "h5tonative") + "\"";
                }
                if (!trim.startsWith("https://" + this.f909e)) {
                }
                String trim3 = trim.replaceFirst("(http|https)://" + this.f909e + "\\?", "").trim();
                if (!TextUtils.isEmpty(trim3)) {
                    String m4383a2 = C1008n.m4383a("<request_token>", "</request_token>", C1008n.m4376b(trim3).get("req_data"));
                    C0988a c0988a2 = new C0988a(this.f906b, "", "");
                    return "_input_charset=\"utf-8\"&ordertoken=\"" + m4383a2 + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + c0988a2.m4492a("sc", "h5tonative") + "\"";
                }
                if (!trim.startsWith("https://" + this.f910f)) {
                }
                if (trim.contains("alipay.wap.create.direct.pay.by.user") || trim.contains("create_forex_trade_wap")) {
                    if (!TextUtils.isEmpty(trim.replaceFirst("(http|https)://" + this.f910f + "\\?", "").trim())) {
                        C0988a c0988a3 = new C0988a(this.f906b, "", "");
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("url", str);
                        jSONObject.put("bizcontext", c0988a3.m4492a("sc", "h5tonative"));
                        return "new_external_info==" + jSONObject.toString();
                    }
                }
                if (Pattern.compile("^(http|https)://(maliprod\\.alipay\\.com/w/trade_pay\\.do.?|mali\\.alipay\\.com/w/trade_pay\\.do.?|mclient\\.alipay\\.com/w/trade_pay\\.do.?)").matcher(str).find()) {
                    String m4383a3 = C1008n.m4383a("?", "", str);
                    if (!TextUtils.isEmpty(m4383a3)) {
                        Map<String, String> m4376b = C1008n.m4376b(m4383a3);
                        StringBuilder sb = new StringBuilder();
                        if (m4665a(false, true, "trade_no", sb, m4376b, "trade_no", "alipay_trade_no")) {
                            m4665a(true, false, "pay_phase_id", sb, m4376b, "payPhaseId", "pay_phase_id", "out_relation_id");
                            sb.append("&biz_sub_type=\"TRADE\"");
                            sb.append("&biz_type=\"trade\"");
                            String str2 = m4376b.get("app_name");
                            if (TextUtils.isEmpty(str2) && !TextUtils.isEmpty(m4376b.get("cid"))) {
                                str2 = "ali1688";
                            } else if (TextUtils.isEmpty(str2) && (!TextUtils.isEmpty(m4376b.get("sid")) || !TextUtils.isEmpty(m4376b.get("s_id")))) {
                                str2 = "tb";
                            }
                            sb.append("&app_name=\"" + str2 + "\"");
                            if (!m4665a(true, true, "extern_token", sb, m4376b, "extern_token", "cid", "sid", "s_id")) {
                                return "";
                            }
                            m4665a(true, false, "appenv", sb, m4376b, "appenv");
                            sb.append("&pay_channel_id=\"alipay_sdk\"");
                            C0943a c0943a = new C0943a(this, null);
                            c0943a.m4661a(m4376b.get("return_url"));
                            c0943a.m4657c(m4376b.get("show_url"));
                            c0943a.m4659b(m4376b.get("pay_order_id"));
                            C0988a c0988a4 = new C0988a(this.f906b, "", "");
                            String str3 = sb.toString() + "&bizcontext=\"" + c0988a4.m4492a("sc", "h5tonative") + "\"";
                            this.f911g.put(str3, c0943a);
                            return str3;
                        }
                    }
                }
                if (!trim.contains("mclient.alipay.com/cashier/mobilepay.htm") && (!EnvUtils.isSandBox() || !trim.contains("mobileclientgw.alipaydev.com/cashier/mobilepay.htm"))) {
                    if (C0962a.m4581j().m4587d() && Pattern.compile("^https?://(maliprod\\.alipay\\.com|mali\\.alipay\\.com)/batch_payment\\.do\\?").matcher(trim).find()) {
                        Uri parse = Uri.parse(trim);
                        String queryParameter = parse.getQueryParameter("return_url");
                        String queryParameter2 = parse.getQueryParameter("show_url");
                        String queryParameter3 = parse.getQueryParameter("pay_order_id");
                        String m4664a = m4664a(parse.getQueryParameter("trade_nos"), parse.getQueryParameter("alipay_trade_no"));
                        String m4664a2 = m4664a(parse.getQueryParameter("payPhaseId"), parse.getQueryParameter("pay_phase_id"), parse.getQueryParameter("out_relation_id"));
                        String[] strArr = new String[4];
                        strArr[0] = parse.getQueryParameter("app_name");
                        strArr[1] = !TextUtils.isEmpty(parse.getQueryParameter("cid")) ? "ali1688" : "";
                        strArr[2] = !TextUtils.isEmpty(parse.getQueryParameter("sid")) ? "tb" : "";
                        strArr[3] = !TextUtils.isEmpty(parse.getQueryParameter("s_id")) ? "tb" : "";
                        String m4664a3 = m4664a(strArr);
                        String m4664a4 = m4664a(parse.getQueryParameter("extern_token"), parse.getQueryParameter("cid"), parse.getQueryParameter("sid"), parse.getQueryParameter("s_id"));
                        String m4664a5 = m4664a(parse.getQueryParameter("appenv"));
                        if (!TextUtils.isEmpty(m4664a) && !TextUtils.isEmpty(m4664a3) && !TextUtils.isEmpty(m4664a4)) {
                            String format = String.format("trade_no=\"%s\"&pay_phase_id=\"%s\"&biz_type=\"trade\"&biz_sub_type=\"TRADE\"&app_name=\"%s\"&extern_token=\"%s\"&appenv=\"%s\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"%s\"", m4664a, m4664a2, m4664a3, m4664a4, m4664a5, new C0988a(this.f906b, "", "").m4492a("sc", "h5tonative"));
                            C0943a c0943a2 = new C0943a(this, null);
                            c0943a2.m4661a(queryParameter);
                            c0943a2.m4657c(queryParameter2);
                            c0943a2.m4659b(queryParameter3);
                            c0943a2.m4655d(m4664a);
                            this.f911g.put(format, c0943a2);
                            return format;
                        }
                    }
                }
                String m4492a = new C0988a(this.f906b, "", "").m4492a("sc", "h5tonative");
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("url", trim);
                jSONObject2.put("bizcontext", m4492a);
                return String.format("new_external_info==%s", jSONObject2.toString());
            }
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
        return "";
    }

    /* renamed from: a */
    private static final String m4664a(String... strArr) {
        if (strArr == null) {
            return "";
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return "";
    }

    public static synchronized boolean fetchSdkConfig(Context context) {
        synchronized (PayTask.class) {
            try {
                C0990b.m4478a().m4477a(context);
                long elapsedRealtime = SystemClock.elapsedRealtime() / 1000;
                if (elapsedRealtime - f904h < C0962a.m4581j().m4585f()) {
                    return false;
                }
                f904h = elapsedRealtime;
                C0962a.m4581j().m4593a(C0988a.m4495a(), context.getApplicationContext());
                return true;
            } catch (Exception e) {
                C0996c.m4436a(e);
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alipay.sdk.app.PayTask$a */
    /* loaded from: classes2.dex */
    public class C0943a {

        /* renamed from: b */
        private String f912b;

        /* renamed from: c */
        private String f913c;

        /* renamed from: d */
        private String f914d;

        /* renamed from: e */
        private String f915e;

        private C0943a(PayTask payTask) {
            this.f912b = "";
            this.f913c = "";
            this.f914d = "";
            this.f915e = "";
        }

        /* synthetic */ C0943a(PayTask payTask, RunnableC0949g runnableC0949g) {
            this(payTask);
        }

        /* renamed from: a */
        public String m4662a() {
            return this.f912b;
        }

        /* renamed from: a */
        public void m4661a(String str) {
            this.f912b = str;
        }

        /* renamed from: b */
        public String m4660b() {
            return this.f914d;
        }

        /* renamed from: b */
        public void m4659b(String str) {
            this.f914d = str;
        }

        /* renamed from: c */
        public String m4658c() {
            return this.f913c;
        }

        /* renamed from: c */
        public void m4657c(String str) {
            this.f913c = str;
        }

        /* renamed from: d */
        public String m4656d() {
            return this.f915e;
        }

        /* renamed from: d */
        public void m4655d(String str) {
            this.f915e = str;
        }
    }

    /* renamed from: a */
    private boolean m4665a(boolean z, boolean z2, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str2 = "";
                break;
            }
            String str3 = strArr[i];
            if (!TextUtils.isEmpty(map.get(str3))) {
                str2 = map.get(str3);
                break;
            }
            i++;
        }
        if (TextUtils.isEmpty(str2)) {
            return !z2;
        } else if (z) {
            sb.append("&");
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
            return true;
        } else {
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
            return true;
        }
    }

    public synchronized H5PayResultModel h5Pay(C0988a c0988a, String str, boolean z) {
        H5PayResultModel h5PayResultModel;
        h5PayResultModel = new H5PayResultModel();
        String[] split = m4670a(c0988a, str, z).split(";");
        HashMap hashMap = new HashMap();
        for (String str2 : split) {
            int indexOf = str2.indexOf("={");
            if (indexOf >= 0) {
                String substring = str2.substring(0, indexOf);
                hashMap.put(substring, m4667a(str2, substring));
            }
        }
        if (hashMap.containsKey("resultStatus")) {
            h5PayResultModel.setResultCode(hashMap.get("resultStatus"));
        }
        h5PayResultModel.setReturnUrl(m4666a(str, hashMap));
        if (TextUtils.isEmpty(h5PayResultModel.getReturnUrl())) {
            C0954a.m4633a(c0988a, "biz", "H5CbUrlEmpty", "");
        }
        return h5PayResultModel;
    }

    /* renamed from: a */
    private String m4666a(String str, Map<String, String> map) throws UnsupportedEncodingException {
        boolean equals = ConstantUtils.DEFAULT_GIFT_INTERVAL_MILLISECOND.equals(map.get("resultStatus"));
        String str2 = map.get("result");
        C0943a remove = this.f911g.remove(str);
        String[] strArr = new String[2];
        strArr[0] = remove != null ? remove.m4660b() : "";
        strArr[1] = remove != null ? remove.m4656d() : "";
        m4664a(strArr);
        if (map.containsKey("callBackUrl")) {
            return map.get("callBackUrl");
        }
        if (str2.length() > 15) {
            String m4664a = m4664a(C1008n.m4383a("&callBackUrl=\"", "\"", str2), C1008n.m4383a("&call_back_url=\"", "\"", str2), C1008n.m4383a("&return_url=\"", "\"", str2), URLDecoder.decode(C1008n.m4383a("&return_url=", "&", str2), EncryptUtil.CHARSET), URLDecoder.decode(C1008n.m4383a("&callBackUrl=", "&", str2), EncryptUtil.CHARSET), C1008n.m4383a("call_back_url=\"", "\"", str2));
            if (!TextUtils.isEmpty(m4664a)) {
                return m4664a;
            }
        }
        if (remove != null) {
            String m4662a = equals ? remove.m4662a() : remove.m4658c();
            if (!TextUtils.isEmpty(m4662a)) {
                return m4662a;
            }
        }
        return remove != null ? C0962a.m4581j().m4586e() : "";
    }

    /* renamed from: a */
    private String m4667a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf("}"));
    }

    /* renamed from: a */
    private C0998e.AbstractC0999a m4676a() {
        return new C0950h(this);
    }

    public void showLoading() {
        C1011a c1011a = this.f907c;
        if (c1011a != null) {
            c1011a.m4359b();
        }
    }

    public void dismissLoading() {
        C1011a c1011a = this.f907c;
        if (c1011a != null) {
            c1011a.m4357c();
            this.f907c = null;
        }
    }

    /* renamed from: a */
    private String m4668a(String str, C0988a c0988a) {
        String m4493a = c0988a.m4493a(str);
        if (m4493a.contains("paymethod=\"expressGateway\"")) {
            return m4672a(c0988a, m4493a);
        }
        List<C0962a.C0963a> m4582i = C0962a.m4581j().m4582i();
        if (!C0962a.m4581j().f968a || m4582i == null) {
            m4582i = C0951i.f930a;
        }
        if (C1008n.m4378b(c0988a, this.f906b, m4582i)) {
            C0998e c0998e = new C0998e(this.f906b, c0988a, m4676a());
            C0996c.m4435b("mspl", "pay inner started: " + m4493a);
            String m4422a = c0998e.m4422a(m4493a);
            C0996c.m4435b("mspl", "pay inner raw result: " + m4422a);
            c0998e.m4427a();
            if (TextUtils.equals(m4422a, "failed") || TextUtils.equals(m4422a, "scheme_failed")) {
                C0954a.m4634a(c0988a, "biz", "LogBindCalledH5");
                return m4672a(c0988a, m4493a);
            } else if (TextUtils.isEmpty(m4422a)) {
                return C0952j.m4643c();
            } else {
                if (!m4422a.contains("{\"isLogin\":\"false\"}")) {
                    return m4422a;
                }
                C0954a.m4634a(c0988a, "biz", "LogHkLoginByIntent");
                return m4671a(c0988a, m4493a, m4582i, m4422a, this.f906b);
            }
        }
        C0954a.m4634a(c0988a, "biz", "LogCalledH5");
        return m4672a(c0988a, m4493a);
    }

    /* renamed from: a */
    private static String m4671a(C0988a c0988a, String str, List<C0962a.C0963a> list, String str2, Activity activity) {
        C1008n.C1009a m4389a = C1008n.m4389a(c0988a, activity, list);
        if (m4389a == null || m4389a.m4362a(c0988a) || m4389a.m4363a() || !TextUtils.equals(m4389a.f1069a.packageName, "hk.alipay.wallet")) {
            return str2;
        }
        C0996c.m4438a("mspl", "PayTask not_login");
        String valueOf = String.valueOf(str.hashCode());
        PayResultActivity.f899b.put(valueOf, new Object());
        Intent intent = new Intent(activity, PayResultActivity.class);
        intent.putExtra("orderSuffix", str);
        intent.putExtra("externalPkgName", activity.getPackageName());
        intent.putExtra("phonecashier.pay.hash", valueOf);
        C0988a.C0989a.m4481a(c0988a, intent);
        activity.startActivity(intent);
        synchronized (PayResultActivity.f899b.get(valueOf)) {
            try {
                C0996c.m4438a("mspl", "PayTask wait");
                PayResultActivity.f899b.get(valueOf).wait();
            } catch (InterruptedException unused) {
                C0996c.m4438a("mspl", "PayTask interrupted");
                return C0952j.m4643c();
            }
        }
        String str3 = PayResultActivity.C0942a.f902b;
        C0996c.m4438a("mspl", "PayTask ret: " + str3);
        return str3;
    }

    /* renamed from: a */
    private String m4672a(C0988a c0988a, String str) {
        String m4674a;
        showLoading();
        EnumC0953k enumC0953k = null;
        try {
            try {
                JSONObject m4527c = new C0985f().mo4506a(c0988a, this.f906b.getApplicationContext(), str).m4527c();
                String optString = m4527c.optString("end_code", null);
                List<C0987b> m4499a = C0987b.m4499a(m4527c.optJSONObject("form").optJSONObject("onload"));
                for (int i = 0; i < m4499a.size(); i++) {
                    if (m4499a.get(i).m4498b() == EnumC0986a.Update) {
                        C0987b.m4501a(m4499a.get(i));
                    }
                }
                m4669a(c0988a, m4527c);
                dismissLoading();
                C0954a.m4635a(this.f906b, c0988a, str, c0988a.f1013p);
                for (int i2 = 0; i2 < m4499a.size(); i2++) {
                    C0987b c0987b = m4499a.get(i2);
                    if (c0987b.m4498b() == EnumC0986a.WapPay) {
                        m4674a = m4674a(c0988a, c0987b);
                    } else if (c0987b.m4498b() == EnumC0986a.OpenWeb) {
                        m4674a = m4673a(c0988a, c0987b, optString);
                    }
                    return m4674a;
                }
            } finally {
                dismissLoading();
                C0954a.m4635a(this.f906b, c0988a, str, c0988a.f1013p);
            }
        } catch (IOException e) {
            EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.NETWORK_ERROR.m4640a());
            C0954a.m4630a(c0988a, "net", e);
            dismissLoading();
            C0954a.m4635a(this.f906b, c0988a, str, c0988a.f1013p);
            enumC0953k = m4636b;
        } catch (Throwable th) {
            C0996c.m4436a(th);
            C0954a.m4632a(c0988a, "biz", "H5PayDataAnalysisError", th);
        }
        if (enumC0953k == null) {
            enumC0953k = EnumC0953k.m4636b(EnumC0953k.FAILED.m4640a());
        }
        return C0952j.m4647a(enumC0953k.m4640a(), enumC0953k.m4637b(), "");
    }

    /* renamed from: a */
    private void m4669a(C0988a c0988a, JSONObject jSONObject) {
        try {
            String optString = jSONObject.optString("tid");
            String optString2 = jSONObject.optString("client_key");
            if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2)) {
                return;
            }
            C0992b.m4467a(C0990b.m4478a().m4476b()).m4466a(optString, optString2);
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "ParserTidClientKeyEx", th);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0099, code lost:
        r0 = r6.m4496c();
        r11 = com.alipay.sdk.app.C0952j.m4647a(java.lang.Integer.valueOf(r0[1]).intValue(), r0[0], com.alipay.sdk.util.C1008n.m4377b(r10, r0[2]));
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String m4673a(C0988a c0988a, C0987b c0987b, String str) {
        boolean m4644b;
        String m4648a;
        String[] m4496c = c0987b.m4496c();
        Intent intent = new Intent(this.f906b, H5PayActivity.class);
        try {
            JSONObject m4373c = C1008n.m4373c(new String(C0967a.m4557a(m4496c[2])));
            intent.putExtra("url", m4496c[0]);
            intent.putExtra("title", m4496c[1]);
            intent.putExtra(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, "v2");
            intent.putExtra("method", m4373c.optString("method", "POST"));
            C0952j.m4645a(false);
            C0952j.m4646a((String) null);
            C0988a.C0989a.m4481a(c0988a, intent);
            this.f906b.startActivity(intent);
            synchronized (f903a) {
                try {
                    f903a.wait();
                    m4644b = C0952j.m4644b();
                    m4648a = C0952j.m4648a();
                    C0952j.m4645a(false);
                    C0952j.m4646a((String) null);
                } catch (InterruptedException e) {
                    C0996c.m4436a(e);
                    return C0952j.m4643c();
                }
            }
            String str2 = "";
            if (m4644b) {
                try {
                    List<C0987b> m4499a = C0987b.m4499a(C1008n.m4373c(new String(C0967a.m4557a(m4648a))));
                    int i = 0;
                    while (true) {
                        if (i >= m4499a.size()) {
                            break;
                        }
                        C0987b c0987b2 = m4499a.get(i);
                        if (c0987b2.m4498b() == EnumC0986a.SetResult) {
                            break;
                        }
                        i++;
                    }
                } catch (Throwable th) {
                    C0996c.m4436a(th);
                    C0954a.m4631a(c0988a, "biz", "H5PayDataAnalysisError", th, m4648a);
                }
            }
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
            try {
                return C0952j.m4647a(Integer.valueOf(str).intValue(), "", "");
            } catch (Throwable th2) {
                C0954a.m4631a(c0988a, "biz", "H5PayDataAnalysisError", th2, "endCode: " + str);
                return C0952j.m4647a(8000, "", "");
            }
        } catch (Throwable th3) {
            C0996c.m4436a(th3);
            C0954a.m4631a(c0988a, "biz", "H5PayDataAnalysisError", th3, Arrays.toString(m4496c));
            return C0952j.m4643c();
        }
    }

    /* renamed from: a */
    private String m4674a(C0988a c0988a, C0987b c0987b) {
        String[] m4496c = c0987b.m4496c();
        Intent intent = new Intent(this.f906b, H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", m4496c[0]);
        if (m4496c.length == 2) {
            bundle.putString("cookie", m4496c[1]);
        }
        intent.putExtras(bundle);
        C0988a.C0989a.m4481a(c0988a, intent);
        this.f906b.startActivity(intent);
        synchronized (f903a) {
            try {
                f903a.wait();
            } catch (InterruptedException e) {
                C0996c.m4436a(e);
                return C0952j.m4643c();
            }
        }
        String m4648a = C0952j.m4648a();
        return TextUtils.isEmpty(m4648a) ? C0952j.m4643c() : m4648a;
    }

    /* renamed from: b */
    private static boolean m4663b() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - f905j >= 3000) {
            f905j = elapsedRealtime;
            return false;
        }
        return true;
    }
}
