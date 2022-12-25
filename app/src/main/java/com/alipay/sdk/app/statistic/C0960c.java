package com.alipay.sdk.app.statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.tid.C0992b;
import com.alipay.sdk.util.C0994a;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1008n;
import com.eclipsesource.p056v8.Platform;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.app.statistic.c */
/* loaded from: classes2.dex */
public class C0960c {

    /* renamed from: aB */
    private String f950aB;

    /* renamed from: as */
    private String f951as;

    /* renamed from: at */
    private String f952at;

    /* renamed from: au */
    private String f953au;

    /* renamed from: av */
    private String f954av;

    /* renamed from: aw */
    private String f955aw;

    /* renamed from: ax */
    private String f956ax;

    /* renamed from: ay */
    private String f957ay;

    /* renamed from: az */
    private String f958az = "";

    /* renamed from: aA */
    private String f949aA = "";

    public C0960c(Context context, boolean z) {
        context = context != null ? context.getApplicationContext() : context;
        this.f951as = m4603c();
        this.f953au = m4615a(context);
        this.f954av = m4616a(z ? 0L : C0954a.C0958c.m4617a(context));
        this.f955aw = m4600d();
        this.f956ax = m4606b(context);
        this.f957ay = "-";
        this.f950aB = "-";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m4610a(String str, String str2, Throwable th) {
        m4601c(str, str2, m4608a(th));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m4609a(String str, String str2, Throwable th, String str3) {
        String m4608a = m4608a(th);
        m4601c(str, str2, str3 + ": " + m4608a);
    }

    /* renamed from: c */
    private synchronized void m4601c(String str, String str2, String str3) {
        C0996c.m4432d("mspl", String.format("err %s %s %s", str, str2, str3));
        String str4 = "";
        if (!TextUtils.isEmpty(this.f949aA)) {
            str4 = str4 + "^";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        Object[] objArr = new Object[4];
        objArr[0] = str;
        objArr[1] = str2;
        objArr[2] = TextUtils.isEmpty(str3) ? "-" : m4605b(str3);
        objArr[3] = m4605b(m4607b());
        sb.append(String.format("%s,%s,%s,%s", objArr));
        this.f949aA += sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m4611a(String str, String str2, String str3) {
        m4601c(str, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public void m4604b(String str, String str2, String str3) {
        m4598d("", str, str2 + "|" + str3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m4612a(String str, String str2) {
        m4598d("", str, str2);
    }

    /* renamed from: d */
    private synchronized void m4598d(String str, String str2, String str3) {
        C0996c.m4435b("mspl", String.format("event %s %s %s", str, str2, str3));
        String str4 = "";
        if (!TextUtils.isEmpty(this.f958az)) {
            str4 = str4 + "^";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        Object[] objArr = new Object[4];
        objArr[0] = TextUtils.isEmpty(str) ? "-" : m4605b(str);
        objArr[1] = m4605b(str2);
        objArr[2] = m4605b(str3);
        objArr[3] = m4605b(m4607b());
        sb.append(String.format("%s,%s,%s,-,-,-,-,-,-,-,-,-,-,%s", objArr));
        this.f958az += sb.toString();
    }

    /* renamed from: b */
    private static String m4607b() {
        return new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault()).format(new Date());
    }

    /* renamed from: b */
    private static String m4605b(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replace("[", "【").replace("]", "】").replace("(", "（").replace(")", "）").replace(",", "，").replace("^", "~").replace("#", "＃");
    }

    /* renamed from: c */
    private static String m4602c(String str) {
        return TextUtils.isEmpty(str) ? "-" : str;
    }

    /* renamed from: a */
    private static String m4608a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(":");
            stringBuffer.append(th.getMessage());
            stringBuffer.append(" 》 ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                int i = 0;
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString());
                    stringBuffer.append(" 》 ");
                    i++;
                    if (i > 5) {
                        break;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    public String m4613a(String str) {
        this.f952at = m4599d(str);
        return String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", this.f951as, this.f952at, this.f953au, this.f954av, this.f955aw, this.f956ax, this.f957ay, m4602c(this.f958az), m4602c(this.f949aA), this.f950aB);
    }

    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: c */
    private static String m4603c() {
        return String.format("123456789,%s", new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
    }

    /* renamed from: d */
    private static String m4599d(String str) {
        String str2;
        String str3;
        if (str == null) {
            str = "";
        }
        String[] split = str.split("&");
        String str4 = null;
        if (split != null) {
            str2 = null;
            str3 = null;
            String str5 = null;
            for (String str6 : split) {
                String[] split2 = str6.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (split2 != null && split2.length == 2) {
                    if (split2[0].equalsIgnoreCase("partner")) {
                        str2 = split2[1].replace("\"", "");
                    } else if (split2[0].equalsIgnoreCase("out_trade_no")) {
                        str3 = split2[1].replace("\"", "");
                    } else if (split2[0].equalsIgnoreCase("trade_no")) {
                        str5 = split2[1].replace("\"", "");
                    } else if (split2[0].equalsIgnoreCase("biz_content")) {
                        try {
                            JSONObject jSONObject = new JSONObject(C1008n.m4377b(C0988a.m4495a(), split2[1]));
                            if (TextUtils.isEmpty(str3)) {
                                str3 = jSONObject.getString("out_trade_no");
                            }
                        } catch (Throwable unused) {
                        }
                    } else if (split2[0].equalsIgnoreCase("app_id") && TextUtils.isEmpty(str2)) {
                        str2 = split2[1];
                    }
                }
            }
            str4 = str5;
        } else {
            str2 = null;
            str3 = null;
        }
        return String.format("%s,%s,-,%s,-,-,-", m4605b(str4), m4605b(str3), m4605b(str2));
    }

    /* renamed from: a */
    private static String m4615a(Context context) {
        String packageName;
        String str = "-";
        if (context != null) {
            try {
                Context applicationContext = context.getApplicationContext();
                packageName = applicationContext.getPackageName();
                try {
                    PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(packageName, 64);
                    str = packageInfo.versionName + "|" + m4614a(packageInfo);
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
            }
            return String.format("%s,%s,-,-,-", m4605b(packageName), m4605b(str));
        }
        packageName = str;
        return String.format("%s,%s,-,-,-", m4605b(packageName), m4605b(str));
    }

    /* renamed from: a */
    private static String m4614a(PackageInfo packageInfo) {
        Signature[] signatureArr;
        String str;
        String m4385a;
        if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length == 0) {
            return "0";
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(packageInfo.signatures.length);
            for (Signature signature : packageInfo.signatures) {
                try {
                    m4385a = C1008n.m4385a((C0988a) null, signature.toByteArray());
                } catch (Throwable unused) {
                }
                if (TextUtils.isEmpty(m4385a)) {
                    str = "?";
                    sb.append("-");
                    sb.append(str);
                } else {
                    str = C1008n.m4366f(m4385a).substring(0, 8);
                    sb.append("-");
                    sb.append(str);
                }
            }
            return sb.toString();
        } catch (Throwable unused2) {
            return "?";
        }
    }

    /* renamed from: a */
    private static String m4616a(long j) {
        String m4605b = m4605b("15.7.4");
        String m4605b2 = m4605b("h.a.3.7.4");
        return String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,%s,-", m4605b, m4605b2, "~" + j);
    }

    /* renamed from: d */
    private static String m4600d() {
        return String.format("%s,%s,-,-,-", m4605b(C0992b.m4467a(C0990b.m4478a().m4476b()).m4468a()), m4605b(C0990b.m4478a().m4473e()));
    }

    /* renamed from: b */
    private static String m4606b(Context context) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", m4605b(C0994a.m4441d(context)), Platform.ANDROID, m4605b(Build.VERSION.RELEASE), m4605b(Build.MODEL), "-", m4605b(C0994a.m4447a(context).m4448a()), m4605b(C0994a.m4445b(context).m4428b()), "gw", m4605b(C0994a.m4447a(context).m4446b()));
    }
}
