package com.alipay.apmobilesecuritysdk.p041a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.alipay.apmobilesecuritysdk.common.C0921a;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.p042b.C0918a;
import com.alipay.apmobilesecuritysdk.p043c.C0919a;
import com.alipay.apmobilesecuritysdk.p043c.C0920b;
import com.alipay.apmobilesecuritysdk.p044d.C0926e;
import com.alipay.apmobilesecuritysdk.p045e.C0927a;
import com.alipay.apmobilesecuritysdk.p045e.C0928b;
import com.alipay.apmobilesecuritysdk.p045e.C0929c;
import com.alipay.apmobilesecuritysdk.p045e.C0930d;
import com.alipay.apmobilesecuritysdk.p045e.C0933g;
import com.alipay.apmobilesecuritysdk.p045e.C0934h;
import com.alipay.apmobilesecuritysdk.p045e.C0935i;
import com.alipay.security.mobile.module.http.C1059d;
import com.alipay.security.mobile.module.http.model.C1062c;
import com.alipay.security.mobile.module.http.model.C1063d;
import com.alipay.security.mobile.module.http.p052v2.AbstractC1064a;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p049b.C1043b;
import com.alipay.security.mobile.module.p051d.C1053b;
import com.eclipsesource.p056v8.Platform;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* renamed from: com.alipay.apmobilesecuritysdk.a.a */
/* loaded from: classes2.dex */
public final class C0917a {

    /* renamed from: a */
    private Context f842a;

    /* renamed from: b */
    private C0918a f843b = C0918a.m4786a();

    /* renamed from: c */
    private int f844c = 4;

    public C0917a(Context context) {
        this.f842a = context;
    }

    /* renamed from: a */
    public static String m4792a(Context context) {
        String m4788b = m4788b(context);
        return C1037a.m4303a(m4788b) ? C0934h.m4724f(context) : m4788b;
    }

    /* renamed from: a */
    public static String m4791a(Context context, String str) {
        try {
            m4789b();
            String m4716a = C0935i.m4716a(str);
            if (!C1037a.m4303a(m4716a)) {
                return m4716a;
            }
            String m4739a = C0933g.m4739a(context, str);
            C0935i.m4715a(str, m4739a);
            return !C1037a.m4303a(m4739a) ? m4739a : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: a */
    private static boolean m4793a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.C_TIME_PATTON_DEFAULT);
        String[] strArr = {"2017-01-27 2017-01-28", "2017-11-10 2017-11-11", "2017-12-11 2017-12-12"};
        int random = ((int) (Math.random() * 24.0d * 60.0d * 60.0d)) * 1;
        for (int i = 0; i < 3; i++) {
            try {
                String[] split = strArr[i].split(ConstantUtils.PLACEHOLDER_STR_ONE);
                if (split != null && split.length == 2) {
                    Date date = new Date();
                    Date parse = simpleDateFormat.parse(split[0] + " 00:00:00");
                    Date parse2 = simpleDateFormat.parse(split[1] + " 23:59:59");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(parse2);
                    calendar.add(13, random);
                    Date time = calendar.getTime();
                    if (date.after(parse) && date.before(time)) {
                        return true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: b */
    private C1062c m4787b(Map<String, String> map) {
        String str;
        C0928b m4762b;
        C0928b m4760c;
        String str2 = "";
        try {
            Context context = this.f842a;
            C1063d c1063d = new C1063d();
            String m4300a = C1037a.m4300a(map, "appName", str2);
            String m4300a2 = C1037a.m4300a(map, "sessionId", str2);
            String m4300a3 = C1037a.m4300a(map, "rpcVersion", str2);
            String m4791a = m4791a(context, m4300a);
            String securityToken = UmidSdkWrapper.getSecurityToken(context);
            String m4728d = C0934h.m4728d(context);
            if (C1037a.m4299b(m4300a2)) {
                c1063d.f1157c = m4300a2;
            } else {
                c1063d.f1157c = m4791a;
            }
            c1063d.f1158d = securityToken;
            c1063d.f1159e = m4728d;
            c1063d.f1155a = Platform.ANDROID;
            C0929c m4753c = C0930d.m4753c(context);
            if (m4753c != null) {
                str = m4753c.f852a;
                String str3 = m4753c.f854c;
            } else {
                str = str2;
            }
            if (C1037a.m4303a(str) && (m4760c = C0927a.m4760c(context)) != null) {
                str = m4760c.f849a;
                String str4 = m4760c.f851c;
            }
            C0929c m4755b = C0930d.m4755b();
            if (m4755b != null) {
                str2 = m4755b.f852a;
                String str5 = m4755b.f854c;
            }
            if (C1037a.m4303a(str2) && (m4762b = C0927a.m4762b()) != null) {
                str2 = m4762b.f849a;
                String str6 = m4762b.f851c;
            }
            c1063d.f1161j = m4300a3;
            if (C1037a.m4303a(str)) {
                c1063d.f1156b = str2;
            } else {
                c1063d.f1156b = str;
            }
            c1063d.f1160f = C0926e.m4770a(context, map);
            return C1059d.m4194b(this.f842a, this.f843b.m4783c()).mo4187a(c1063d);
        } catch (Throwable th) {
            th.printStackTrace();
            C0919a.m4780a(th);
            return null;
        }
    }

    /* renamed from: b */
    private static String m4788b(Context context) {
        try {
            String m4714b = C0935i.m4714b();
            if (!C1037a.m4303a(m4714b)) {
                return m4714b;
            }
            C0929c m4754b = C0930d.m4754b(context);
            if (m4754b != null) {
                C0935i.m4717a(m4754b);
                String str = m4754b.f852a;
                if (C1037a.m4299b(str)) {
                    return str;
                }
            }
            C0928b m4761b = C0927a.m4761b(context);
            if (m4761b == null) {
                return "";
            }
            C0935i.m4718a(m4761b);
            String str2 = m4761b.f849a;
            return C1037a.m4299b(str2) ? str2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: b */
    private static void m4789b() {
        try {
            String[] strArr = {"device_feature_file_name", "wallet_times", "wxcasxx_v3", "wxcasxx_v4", "wxxzyy_v1"};
            for (int i = 0; i < 5; i++) {
                String str = strArr[i];
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(externalStorageDirectory, ".SystemConfig/" + str);
                if (file.exists() && file.canWrite()) {
                    file.delete();
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0206 A[Catch: Exception -> 0x0244, TryCatch #0 {Exception -> 0x0244, blocks: (B:3:0x0009, B:5:0x003b, B:8:0x0044, B:12:0x00c2, B:15:0x01ea, B:17:0x0206, B:19:0x020c, B:21:0x0212, B:25:0x021b, B:27:0x0221, B:32:0x00d7, B:34:0x00ef, B:39:0x00fc, B:40:0x010d, B:41:0x0115, B:46:0x0127, B:48:0x0177, B:50:0x0181, B:51:0x0189, B:53:0x0196, B:55:0x01a0, B:56:0x01a8, B:57:0x01a4, B:58:0x0185, B:60:0x0059, B:62:0x0067, B:65:0x0072, B:67:0x0078, B:70:0x0083, B:73:0x008c, B:76:0x0099, B:80:0x00a6, B:82:0x00b4), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x020c A[Catch: Exception -> 0x0244, TryCatch #0 {Exception -> 0x0244, blocks: (B:3:0x0009, B:5:0x003b, B:8:0x0044, B:12:0x00c2, B:15:0x01ea, B:17:0x0206, B:19:0x020c, B:21:0x0212, B:25:0x021b, B:27:0x0221, B:32:0x00d7, B:34:0x00ef, B:39:0x00fc, B:40:0x010d, B:41:0x0115, B:46:0x0127, B:48:0x0177, B:50:0x0181, B:51:0x0189, B:53:0x0196, B:55:0x01a0, B:56:0x01a8, B:57:0x01a4, B:58:0x0185, B:60:0x0059, B:62:0x0067, B:65:0x0072, B:67:0x0078, B:70:0x0083, B:73:0x008c, B:76:0x0099, B:80:0x00a6, B:82:0x00b4), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x021b A[Catch: Exception -> 0x0244, TryCatch #0 {Exception -> 0x0244, blocks: (B:3:0x0009, B:5:0x003b, B:8:0x0044, B:12:0x00c2, B:15:0x01ea, B:17:0x0206, B:19:0x020c, B:21:0x0212, B:25:0x021b, B:27:0x0221, B:32:0x00d7, B:34:0x00ef, B:39:0x00fc, B:40:0x010d, B:41:0x0115, B:46:0x0127, B:48:0x0177, B:50:0x0181, B:51:0x0189, B:53:0x0196, B:55:0x01a0, B:56:0x01a8, B:57:0x01a4, B:58:0x0185, B:60:0x0059, B:62:0x0067, B:65:0x0072, B:67:0x0078, B:70:0x0083, B:73:0x008c, B:76:0x0099, B:80:0x00a6, B:82:0x00b4), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d7 A[Catch: Exception -> 0x0244, TryCatch #0 {Exception -> 0x0244, blocks: (B:3:0x0009, B:5:0x003b, B:8:0x0044, B:12:0x00c2, B:15:0x01ea, B:17:0x0206, B:19:0x020c, B:21:0x0212, B:25:0x021b, B:27:0x0221, B:32:0x00d7, B:34:0x00ef, B:39:0x00fc, B:40:0x010d, B:41:0x0115, B:46:0x0127, B:48:0x0177, B:50:0x0181, B:51:0x0189, B:53:0x0196, B:55:0x01a0, B:56:0x01a8, B:57:0x01a4, B:58:0x0185, B:60:0x0059, B:62:0x0067, B:65:0x0072, B:67:0x0078, B:70:0x0083, B:73:0x008c, B:76:0x0099, B:80:0x00a6, B:82:0x00b4), top: B:2:0x0009 }] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m4790a(Map<String, String> map) {
        String m4300a;
        boolean z;
        boolean z2;
        int i;
        String str;
        AbstractC1064a m4194b;
        Context context;
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager;
        try {
            C0919a.m4782a(this.f842a, C1037a.m4300a(map, "tid", ""), C1037a.m4300a(map, "utdid", ""), m4792a(this.f842a));
            m4300a = C1037a.m4300a(map, "appName", "");
            m4789b();
            m4788b(this.f842a);
            m4791a(this.f842a, m4300a);
            C0935i.m4720a();
            z = false;
        } catch (Exception e) {
            C0919a.m4780a(e);
        }
        if (!m4793a() && !C0921a.m4778a(this.f842a)) {
            C0926e.m4771a();
            if (!(!C1037a.m4302a(C0926e.m4768b(this.f842a, map), C0935i.m4712c()))) {
                String m4300a2 = C1037a.m4300a(map, "tid", "");
                String m4300a3 = C1037a.m4300a(map, "utdid", "");
                if (C1037a.m4299b(m4300a2)) {
                    if (!C1037a.m4302a(m4300a2, C0935i.m4710d())) {
                    }
                }
                if (C1037a.m4299b(m4300a3)) {
                    if (!C1037a.m4302a(m4300a3, C0935i.m4708e())) {
                    }
                }
                if (C0935i.m4719a(this.f842a, m4300a)) {
                    if (!C1037a.m4303a(m4791a(this.f842a, m4300a))) {
                        if (C1037a.m4303a(m4788b(this.f842a))) {
                        }
                        z2 = false;
                        Context context2 = this.f842a;
                        C1043b.m4281a();
                        C0934h.m4731b(context2, String.valueOf(C1043b.m4254n()));
                        if (z2) {
                            new C0920b();
                            UmidSdkWrapper.startUmidTaskSync(this.f842a, C0918a.m4786a().m4784b());
                            C1062c m4787b = m4787b(map);
                            int m4191a = m4787b != null ? m4787b.m4191a() : 2;
                            if (m4191a != 1) {
                                if (m4191a != 3) {
                                    if (m4787b != null) {
                                        str = "Server error, result:" + m4787b.f1145b;
                                    } else {
                                        str = "Server error, returned null";
                                    }
                                    C0919a.m4781a(str);
                                    if (C1037a.m4303a(m4791a(this.f842a, m4300a))) {
                                        i = 4;
                                    }
                                } else {
                                    i = 1;
                                }
                                this.f844c = i;
                                m4194b = C1059d.m4194b(this.f842a, this.f843b.m4783c());
                                context = this.f842a;
                                networkInfo = null;
                                connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                                if (connectivityManager != null) {
                                    networkInfo = connectivityManager.getActiveNetworkInfo();
                                }
                                if (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 1) {
                                    z = true;
                                }
                                if (z && C0934h.m4730c(context)) {
                                    new C1053b(context.getFilesDir().getAbsolutePath() + "/log/ap", m4194b).m4210a();
                                }
                                return this.f844c;
                            }
                            C0934h.m4733a(this.f842a, m4787b.m4190b());
                            C0934h.m4727d(this.f842a, m4787b.m4189c());
                            C0934h.m4725e(this.f842a, m4787b.f1150l);
                            C0934h.m4736a(this.f842a, m4787b.f1151m);
                            C0934h.m4723f(this.f842a, m4787b.f1152n);
                            C0934h.m4722g(this.f842a, m4787b.f1154p);
                            C0935i.m4711c(C0926e.m4768b(this.f842a, map));
                            C0935i.m4715a(m4300a, m4787b.f1147i);
                            C0935i.m4713b(m4787b.f1146h);
                            C0935i.m4709d(m4787b.f1153o);
                            String m4300a4 = C1037a.m4300a(map, "tid", "");
                            if (!C1037a.m4299b(m4300a4) || C1037a.m4302a(m4300a4, C0935i.m4710d())) {
                                m4300a4 = C0935i.m4710d();
                            } else {
                                C0935i.m4707e(m4300a4);
                            }
                            C0935i.m4707e(m4300a4);
                            String m4300a5 = C1037a.m4300a(map, "utdid", "");
                            if (!C1037a.m4299b(m4300a5) || C1037a.m4302a(m4300a5, C0935i.m4708e())) {
                                m4300a5 = C0935i.m4708e();
                            } else {
                                C0935i.m4705f(m4300a5);
                            }
                            C0935i.m4705f(m4300a5);
                            C0935i.m4720a();
                            C0930d.m4757a(this.f842a, C0935i.m4704g());
                            C0930d.m4759a();
                            C0927a.m4764a(this.f842a, new C0928b(C0935i.m4714b(), C0935i.m4712c(), C0935i.m4706f()));
                            C0927a.m4766a();
                            C0933g.m4738a(this.f842a, m4300a, C0935i.m4716a(m4300a));
                            C0933g.m4741a();
                            C0934h.m4735a(this.f842a, m4300a, System.currentTimeMillis());
                        }
                        i = 0;
                        this.f844c = i;
                        m4194b = C1059d.m4194b(this.f842a, this.f843b.m4783c());
                        context = this.f842a;
                        networkInfo = null;
                        connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                        if (connectivityManager != null) {
                        }
                        if (networkInfo != null) {
                            z = true;
                        }
                        if (z) {
                            new C1053b(context.getFilesDir().getAbsolutePath() + "/log/ap", m4194b).m4210a();
                        }
                        return this.f844c;
                    }
                }
            }
            z2 = true;
            Context context22 = this.f842a;
            C1043b.m4281a();
            C0934h.m4731b(context22, String.valueOf(C1043b.m4254n()));
            if (z2) {
            }
            i = 0;
            this.f844c = i;
            m4194b = C1059d.m4194b(this.f842a, this.f843b.m4783c());
            context = this.f842a;
            networkInfo = null;
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
            }
            if (networkInfo != null) {
            }
            if (z) {
            }
            return this.f844c;
        }
        if (!C1037a.m4303a(m4791a(this.f842a, m4300a))) {
            if (C1037a.m4303a(m4788b(this.f842a))) {
            }
            z2 = false;
            Context context222 = this.f842a;
            C1043b.m4281a();
            C0934h.m4731b(context222, String.valueOf(C1043b.m4254n()));
            if (z2) {
            }
            i = 0;
            this.f844c = i;
            m4194b = C1059d.m4194b(this.f842a, this.f843b.m4783c());
            context = this.f842a;
            networkInfo = null;
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
            }
            if (networkInfo != null) {
            }
            if (z) {
            }
            return this.f844c;
        }
        z2 = true;
        Context context2222 = this.f842a;
        C1043b.m4281a();
        C0934h.m4731b(context2222, String.valueOf(C1043b.m4254n()));
        if (z2) {
        }
        i = 0;
        this.f844c = i;
        m4194b = C1059d.m4194b(this.f842a, this.f843b.m4783c());
        context = this.f842a;
        networkInfo = null;
        connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
        }
        if (networkInfo != null) {
        }
        if (z) {
        }
        return this.f844c;
    }
}
