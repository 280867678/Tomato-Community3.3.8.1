package com.alipay.sdk.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;
import com.alipay.sdk.app.C0951i;
import com.alipay.sdk.app.C0952j;
import com.alipay.sdk.app.EnumC0953k;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.cons.C0961a;
import com.alipay.sdk.data.C0962a;
import com.alipay.sdk.sys.C0988a;
import com.coremedia.iso.boxes.AuthorBox;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.slf4j.Marker;

/* renamed from: com.alipay.sdk.util.n */
/* loaded from: classes2.dex */
public class C1008n {

    /* renamed from: f */
    private static final String[] f1068f = {"10.1.5.1013151", "10.1.5.1013148"};

    /* renamed from: g */
    public static String m4365g(Context context) {
        return "-1;-1";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static String m4398a() {
        if (EnvUtils.isSandBox()) {
            return "com.eg.android.AlipayGphoneRC";
        }
        try {
            return C0951i.f930a.get(0).f973a;
        } catch (Throwable unused) {
            return "com.eg.android.AlipayGphone";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static String m4384a(String str) {
        return (!EnvUtils.isSandBox() || !TextUtils.equals(str, "com.eg.android.AlipayGphoneRC")) ? "com.eg.android.AlipayGphone.IAlixPay" : "com.eg.android.AlipayGphoneRC.IAlixPay";
    }

    /* renamed from: b */
    public static Map<String, String> m4376b(String str) {
        String[] split;
        HashMap hashMap = new HashMap();
        for (String str2 : str.split("&")) {
            int indexOf = str2.indexOf(SimpleComparison.EQUAL_TO_OPERATION, 1);
            if (-1 != indexOf) {
                hashMap.put(str2.substring(0, indexOf), URLDecoder.decode(str2.substring(indexOf + 1)));
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    public static Map<String, String> m4386a(C0988a c0988a, String str) {
        String[] split;
        HashMap hashMap = new HashMap(4);
        int indexOf = str.indexOf(63);
        if (indexOf != -1 && indexOf < str.length() - 1) {
            for (String str2 : str.substring(indexOf + 1).split("&")) {
                int indexOf2 = str2.indexOf(61, 1);
                if (indexOf2 != -1 && indexOf2 < str2.length() - 1) {
                    hashMap.put(str2.substring(0, indexOf2), m4377b(c0988a, str2.substring(indexOf2 + 1)));
                }
            }
        }
        return hashMap;
    }

    /* renamed from: c */
    public static JSONObject m4373c(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable unused) {
            return new JSONObject();
        }
    }

    /* renamed from: b */
    public static String m4377b(C0988a c0988a, String str) {
        try {
            return URLDecoder.decode(str, EncryptUtil.CHARSET);
        } catch (UnsupportedEncodingException e) {
            C0954a.m4632a(c0988a, "biz", "H5PayDataAnalysisError", e);
            return "";
        }
    }

    /* renamed from: a */
    public static String m4383a(String str, String str2, String str3) {
        try {
            int indexOf = str3.indexOf(str) + str.length();
            if (indexOf <= str.length()) {
                return "";
            }
            int i = 0;
            if (!TextUtils.isEmpty(str2)) {
                i = str3.indexOf(str2, indexOf);
            }
            if (i < 1) {
                return str3.substring(indexOf);
            }
            return str3.substring(indexOf, i);
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: a */
    public static String m4385a(C0988a c0988a, byte[] bArr) {
        BigInteger modulus;
        try {
            PublicKey publicKey = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey();
            if ((publicKey instanceof RSAPublicKey) && (modulus = ((RSAPublicKey) publicKey).getModulus()) != null) {
                return modulus.toString(16);
            }
            return null;
        } catch (Exception e) {
            C0954a.m4632a(c0988a, AuthorBox.TYPE, "GetPublicKeyFromSignEx", e);
            return null;
        }
    }

    /* renamed from: a */
    public static C1009a m4389a(C0988a c0988a, Context context, List<C0962a.C0963a> list) {
        C1009a m4390a;
        if (list == null) {
            return null;
        }
        for (C0962a.C0963a c0963a : list) {
            if (c0963a != null && (m4390a = m4390a(c0988a, context, c0963a.f973a, c0963a.f974b, c0963a.f975c)) != null && !m4390a.m4362a(c0988a) && !m4390a.m4363a()) {
                return m4390a;
            }
        }
        return null;
    }

    /* renamed from: a */
    private static C1009a m4390a(C0988a c0988a, Context context, String str, int i, String str2) {
        PackageInfo packageInfo;
        if (EnvUtils.isSandBox() && "com.eg.android.AlipayGphone".equals(str)) {
            str = "com.eg.android.AlipayGphoneRC";
        }
        try {
            packageInfo = m4379b(context, str);
        } catch (Throwable th) {
            C0954a.m4633a(c0988a, AuthorBox.TYPE, "GetPackageInfoEx", th.getMessage());
            packageInfo = null;
        }
        if (!m4388a(c0988a, packageInfo)) {
            return null;
        }
        return m4393a(packageInfo, i, str2);
    }

    /* renamed from: a */
    private static boolean m4388a(C0988a c0988a, PackageInfo packageInfo) {
        String str = "";
        boolean z = false;
        if (packageInfo == null) {
            str = str + "info == null";
        } else {
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null) {
                str = str + "info.signatures == null";
            } else if (signatureArr.length <= 0) {
                str = str + "info.signatures.length <= 0";
            } else {
                z = true;
            }
        }
        if (!z) {
            C0954a.m4633a(c0988a, AuthorBox.TYPE, "NotIncludeSignatures", str);
        }
        return z;
    }

    /* renamed from: b */
    private static PackageInfo m4379b(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    /* renamed from: a */
    private static C1009a m4393a(PackageInfo packageInfo, int i, String str) {
        if (packageInfo == null) {
            return null;
        }
        return new C1009a(packageInfo, i, str);
    }

    /* renamed from: com.alipay.sdk.util.n$a */
    /* loaded from: classes2.dex */
    public static final class C1009a {

        /* renamed from: a */
        public final PackageInfo f1069a;

        /* renamed from: b */
        public final int f1070b;

        /* renamed from: c */
        public final String f1071c;

        public C1009a(PackageInfo packageInfo, int i, String str) {
            this.f1069a = packageInfo;
            this.f1070b = i;
            this.f1071c = str;
        }

        /* renamed from: a */
        public boolean m4362a(C0988a c0988a) {
            Signature[] signatureArr = this.f1069a.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return false;
            }
            for (Signature signature : signatureArr) {
                String m4385a = C1008n.m4385a(c0988a, signature.toByteArray());
                if (m4385a != null && !TextUtils.equals(m4385a, this.f1071c)) {
                    C0954a.m4633a(c0988a, "biz", "PublicKeyUnmatch", String.format("Got %s, expected %s", m4385a, this.f1071c));
                    return true;
                }
            }
            return false;
        }

        /* renamed from: a */
        public boolean m4363a() {
            return this.f1069a.versionCode < this.f1070b;
        }
    }

    /* renamed from: a */
    public static boolean m4396a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.alipay.android.app", 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m4378b(C0988a c0988a, Context context, List<C0962a.C0963a> list) {
        try {
            for (C0962a.C0963a c0963a : list) {
                if (c0963a != null) {
                    String str = c0963a.f973a;
                    if (EnvUtils.isSandBox() && "com.eg.android.AlipayGphone".equals(str)) {
                        str = "com.eg.android.AlipayGphoneRC";
                    }
                    try {
                        if (context.getPackageManager().getPackageInfo(str, 128) != null) {
                            return true;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        continue;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "CheckLaunchAppExistEx", th);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static boolean m4394a(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        try {
            String str = packageInfo.versionName;
            if (!TextUtils.equals(str, f1068f[0])) {
                if (!TextUtils.equals(str, f1068f[1])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m4380b(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(m4398a(), 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode < 99;
        } catch (Throwable th) {
            C0996c.m4436a(th);
            return false;
        }
    }

    /* renamed from: c */
    public static String m4374c(Context context) {
        String m4381b = m4381b();
        String m4375c = m4375c();
        String m4371d = m4371d(context);
        String m4369e = m4369e(context);
        return " (" + m4381b + ";" + m4375c + ";" + m4371d + ";;" + m4369e + ")(sdk android)";
    }

    /* renamed from: b */
    public static String m4381b() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /* renamed from: c */
    public static String m4375c() {
        String m4372d = m4372d();
        int indexOf = m4372d.indexOf("-");
        if (indexOf != -1) {
            m4372d = m4372d.substring(0, indexOf);
        }
        int indexOf2 = m4372d.indexOf("\n");
        if (indexOf2 != -1) {
            m4372d = m4372d.substring(0, indexOf2);
        }
        return "Linux " + m4372d;
    }

    /* renamed from: d */
    private static String m4372d() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(readLine);
            if (!matcher.matches() || matcher.groupCount() < 4) {
                return "Unavailable";
            }
            return matcher.group(1) + "\n" + matcher.group(2) + ConstantUtils.PLACEHOLDER_STR_ONE + matcher.group(3) + "\n" + matcher.group(4);
        } catch (IOException unused) {
            return "Unavailable";
        }
    }

    /* renamed from: d */
    public static String m4371d(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    /* renamed from: e */
    public static String m4369e(Context context) {
        DisplayMetrics m4364h = m4364h(context);
        return m4364h.widthPixels + Marker.ANY_MARKER + m4364h.heightPixels;
    }

    /* renamed from: h */
    private static DisplayMetrics m4364h(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /* renamed from: f */
    public static String m4367f(Context context) {
        String m4399a = C1007m.m4399a(context);
        return m4399a.substring(0, m4399a.indexOf("://"));
    }

    /* renamed from: a */
    public static String m4397a(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            int nextInt = random.nextInt(3);
            if (nextInt == 0) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 65.0d)));
            } else if (nextInt == 1) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 97.0d)));
            } else if (nextInt == 2) {
                sb.append(String.valueOf(new Random().nextInt(10)));
            }
        }
        return sb.toString();
    }

    /* renamed from: d */
    public static boolean m4370d(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static String m4395a(Context context, String str) {
        String str2 = "";
        try {
            String str3 = str2;
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getApplicationContext().getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equals(str)) {
                    str3 = str3 + "#M";
                } else {
                    if (runningAppProcessInfo.processName.startsWith(str + ":")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str3);
                        sb.append("#");
                        sb.append(runningAppProcessInfo.processName.replace(str + ":", str2));
                        str3 = sb.toString();
                    }
                }
            }
            str2 = str3;
        } catch (Throwable unused) {
        }
        if (str2.length() > 0) {
            str2 = str2.substring(1);
        }
        return str2.length() == 0 ? "N" : str2;
    }

    /* renamed from: a */
    public static boolean m4387a(C0988a c0988a, WebView webView, String str, Activity activity) {
        int parseInt;
        String substring;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (activity == null) {
            return false;
        }
        if (str.toLowerCase().startsWith("alipays://platformapi/startApp?".toLowerCase()) || str.toLowerCase().startsWith("intent://platformapi/startapp?".toLowerCase())) {
            try {
                C1009a m4389a = m4389a(c0988a, activity, C0951i.f930a);
                if (m4389a != null && !m4389a.m4363a() && !m4389a.m4362a(c0988a)) {
                    if (str.startsWith("intent://platformapi/startapp")) {
                        str = str.replaceFirst("intent://platformapi/startapp\\?", "alipays://platformapi/startApp?");
                    }
                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            } catch (Throwable unused) {
            }
            return true;
        } else if (TextUtils.equals(str, "sdklite://h5quit") || TextUtils.equals(str, "http://m.alipay.com/?action=h5quit")) {
            C0952j.m4646a(C0952j.m4643c());
            activity.finish();
            return true;
        } else if (!str.startsWith("sdklite://h5quit?result=")) {
            return false;
        } else {
            try {
                String substring2 = str.substring(str.indexOf("sdklite://h5quit?result=") + 24);
                parseInt = Integer.parseInt(substring2.substring(substring2.lastIndexOf("&end_code=") + 10));
            } catch (Exception unused2) {
                C0952j.m4646a(C0952j.m4641e());
            }
            if (parseInt != EnumC0953k.SUCCEEDED.m4640a() && parseInt != EnumC0953k.PAY_WAITTING.m4640a()) {
                EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.FAILED.m4640a());
                C0952j.m4646a(C0952j.m4647a(m4636b.m4640a(), m4636b.m4637b(), ""));
                activity.runOnUiThread(new RunnableC1010o(activity));
                return true;
            }
            if (C0961a.f961u) {
                StringBuilder sb = new StringBuilder();
                String decode = URLDecoder.decode(str);
                String decode2 = URLDecoder.decode(decode);
                String str2 = decode2.substring(decode2.indexOf("sdklite://h5quit?result=") + 24, decode2.lastIndexOf("&end_code=")).split("&return_url=")[0];
                int indexOf = decode.indexOf("&return_url=") + 12;
                sb.append(str2);
                sb.append("&return_url=");
                sb.append(decode.substring(indexOf, decode.indexOf("&", indexOf)));
                sb.append(decode.substring(decode.indexOf("&", indexOf)));
                substring = sb.toString();
            } else {
                String decode3 = URLDecoder.decode(str);
                substring = decode3.substring(decode3.indexOf("sdklite://h5quit?result=") + 24, decode3.lastIndexOf("&end_code="));
            }
            EnumC0953k m4636b2 = EnumC0953k.m4636b(parseInt);
            C0952j.m4646a(C0952j.m4647a(m4636b2.m4640a(), m4636b2.m4637b(), substring));
            activity.runOnUiThread(new RunnableC1010o(activity));
            return true;
        }
    }

    /* renamed from: a */
    public static String m4392a(C0988a c0988a, Context context) {
        return m4391a(c0988a, context, context.getPackageName());
    }

    /* renamed from: a */
    private static String m4391a(C0988a c0988a, Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 128).versionName;
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "GetPackageInfoEx", th);
            return "";
        }
    }

    /* renamed from: e */
    public static String m4368e(String str) {
        try {
            Uri parse = Uri.parse(str);
            return String.format("%s%s", parse.getAuthority(), parse.getPath());
        } catch (Throwable th) {
            C0996c.m4436a(th);
            return "-";
        }
    }

    /* renamed from: f */
    public static String m4366f(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return m4382a(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    /* renamed from: a */
    private static String m4382a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(Character.forDigit((b & 240) >> 4, 16));
            sb.append(Character.forDigit(b & 15, 16));
        }
        return sb.toString();
    }
}
