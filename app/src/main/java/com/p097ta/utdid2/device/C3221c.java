package com.p097ta.utdid2.device;

import android.content.Context;
import android.os.Binder;
import android.provider.Settings;
import android.text.TextUtils;
import com.p097ta.utdid2.p098a.p099a.C3198b;
import com.p097ta.utdid2.p098a.p099a.C3203d;
import com.p097ta.utdid2.p098a.p099a.C3204e;
import com.p097ta.utdid2.p098a.p099a.C3205f;
import com.p097ta.utdid2.p098a.p099a.C3208g;
import com.p097ta.utdid2.p100b.p101a.C3214c;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.ta.utdid2.device.c */
/* loaded from: classes3.dex */
public class C3221c {

    /* renamed from: a */
    private static C3221c f1947a;

    /* renamed from: e */
    private static final Object f1948e = new Object();

    /* renamed from: k */
    private static final String f1949k = ".UTSystemConfig" + File.separator + "Global";

    /* renamed from: a */
    private C3214c f1950a;

    /* renamed from: a */
    private C3222d f1951a;

    /* renamed from: b */
    private C3214c f1952b;

    /* renamed from: i */
    private String f1955i;

    /* renamed from: j */
    private String f1956j;
    private Context mContext;

    /* renamed from: h */
    private String f1954h = null;

    /* renamed from: b */
    private Pattern f1953b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    private C3221c(Context context) {
        this.mContext = null;
        this.f1951a = null;
        this.f1955i = "xx_utdid_key";
        this.f1956j = "xx_utdid_domain";
        this.f1950a = null;
        this.f1952b = null;
        this.mContext = context;
        this.f1952b = new C3214c(context, f1949k, "Alvin2", false, true);
        this.f1950a = new C3214c(context, ".DataStorage", "ContextData", false, true);
        this.f1951a = new C3222d();
        this.f1955i = String.format("K_%d", Integer.valueOf(C3208g.m3648a(this.f1955i)));
        this.f1956j = String.format("D_%d", Integer.valueOf(C3208g.m3648a(this.f1956j)));
    }

    /* renamed from: c */
    private void m3581c() {
        C3214c c3214c = this.f1952b;
        if (c3214c != null) {
            if (C3208g.m3647a(c3214c.getString("UTDID2"))) {
                String string = this.f1952b.getString("UTDID");
                if (!C3208g.m3647a(string)) {
                    m3578f(string);
                }
            }
            boolean z = false;
            if (!C3208g.m3647a(this.f1952b.getString("DID"))) {
                this.f1952b.remove("DID");
                z = true;
            }
            if (!C3208g.m3647a(this.f1952b.getString("EI"))) {
                this.f1952b.remove("EI");
                z = true;
            }
            if (!C3208g.m3647a(this.f1952b.getString("SI"))) {
                this.f1952b.remove("SI");
                z = true;
            }
            if (!z) {
                return;
            }
            this.f1952b.commit();
        }
    }

    /* renamed from: a */
    public static C3221c m3584a(Context context) {
        if (context != null && f1947a == null) {
            synchronized (f1948e) {
                if (f1947a == null) {
                    f1947a = new C3221c(context);
                    f1947a.m3581c();
                }
            }
        }
        return f1947a;
    }

    /* renamed from: f */
    private void m3578f(String str) {
        C3214c c3214c;
        if (m3583b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() != 24 || (c3214c = this.f1952b) == null) {
                return;
            }
            c3214c.putString("UTDID2", str);
            this.f1952b.commit();
        }
    }

    /* renamed from: g */
    private void m3576g(String str) {
        C3214c c3214c;
        if (str == null || (c3214c = this.f1950a) == null || str.equals(c3214c.getString(this.f1955i))) {
            return;
        }
        this.f1950a.putString(this.f1955i, str);
        this.f1950a.commit();
    }

    /* renamed from: h */
    private void m3574h(String str) {
        if (!m3579f() || !m3583b(str)) {
            return;
        }
        if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        if (24 != str.length()) {
            return;
        }
        String str2 = null;
        try {
            str2 = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
        } catch (Exception unused) {
        }
        if (m3583b(str2)) {
            return;
        }
        try {
            Settings.System.putString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk", str);
        } catch (Exception unused2) {
        }
    }

    /* renamed from: i */
    private void m3572i(String str) {
        String str2;
        try {
            str2 = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused) {
            str2 = null;
        }
        if (!str.equals(str2)) {
            try {
                Settings.System.putString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp", str);
            } catch (Exception unused2) {
            }
        }
    }

    /* renamed from: j */
    private void m3571j(String str) {
        if (!m3579f() || str == null) {
            return;
        }
        m3572i(str);
    }

    /* renamed from: g */
    private String m3577g() {
        C3214c c3214c = this.f1952b;
        if (c3214c != null) {
            String string = c3214c.getString("UTDID2");
            if (!C3208g.m3647a(string) && this.f1951a.m3570c(string) != null) {
                return string;
            }
            return null;
        }
        return null;
    }

    /* renamed from: b */
    private boolean m3583b(String str) {
        if (str != null) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length() && !this.f1953b.matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    public synchronized String getValue() {
        if (this.f1954h != null) {
            return this.f1954h;
        }
        return m3575h();
    }

    /* renamed from: h */
    public synchronized String m3575h() {
        this.f1954h = m3573i();
        if (!TextUtils.isEmpty(this.f1954h)) {
            return this.f1954h;
        }
        try {
            byte[] m3580c = m3580c();
            if (m3580c != null) {
                this.f1954h = C3198b.encodeToString(m3580c, 2);
                m3578f(this.f1954h);
                String m3569c = this.f1951a.m3569c(m3580c);
                if (m3569c != null) {
                    m3571j(m3569c);
                    m3576g(m3569c);
                }
                return this.f1954h;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* renamed from: i */
    public synchronized String m3573i() {
        String str;
        String string;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
        } catch (Exception unused) {
        }
        if (m3583b(string)) {
            return string;
        }
        C3223e c3223e = new C3223e();
        boolean z = false;
        try {
            str = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused2) {
            str = null;
        }
        if (!C3208g.m3647a(str)) {
            String m3566e = c3223e.m3566e(str);
            if (m3583b(m3566e)) {
                m3574h(m3566e);
                return m3566e;
            }
            String m3567d = c3223e.m3567d(str);
            if (m3583b(m3567d)) {
                String m3570c = this.f1951a.m3570c(m3567d);
                if (!C3208g.m3647a(m3570c)) {
                    m3571j(m3570c);
                    try {
                        str = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
                    } catch (Exception unused3) {
                    }
                }
            }
            String m3568d = this.f1951a.m3568d(str);
            if (m3583b(m3568d)) {
                this.f1954h = m3568d;
                m3578f(m3568d);
                m3576g(str);
                m3574h(this.f1954h);
                return this.f1954h;
            }
        } else {
            z = true;
        }
        String m3577g = m3577g();
        if (m3583b(m3577g)) {
            String m3570c2 = this.f1951a.m3570c(m3577g);
            if (z) {
                m3571j(m3570c2);
            }
            m3574h(m3577g);
            m3576g(m3570c2);
            this.f1954h = m3577g;
            return m3577g;
        }
        String string2 = this.f1950a.getString(this.f1955i);
        if (!C3208g.m3647a(string2)) {
            String m3567d2 = c3223e.m3567d(string2);
            if (!m3583b(m3567d2)) {
                m3567d2 = this.f1951a.m3568d(string2);
            }
            if (m3583b(m3567d2)) {
                String m3570c3 = this.f1951a.m3570c(m3567d2);
                if (!C3208g.m3647a(m3567d2)) {
                    this.f1954h = m3567d2;
                    if (z) {
                        m3571j(m3570c3);
                    }
                    m3578f(this.f1954h);
                    return this.f1954h;
                }
            }
        }
        return null;
    }

    /* renamed from: c */
    private byte[] m3580c() throws Exception {
        String str;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int nextInt = new Random().nextInt();
        byte[] bytes = C3203d.getBytes(currentTimeMillis);
        byte[] bytes2 = C3203d.getBytes(nextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            str = C3204e.m3656a(this.mContext);
        } catch (Exception unused) {
            str = "" + new Random().nextInt();
        }
        byteArrayOutputStream.write(C3203d.getBytes(C3208g.m3648a(str)), 0, 4);
        byteArrayOutputStream.write(C3203d.getBytes(C3208g.m3648a(m3582b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    /* renamed from: b */
    public static String m3582b(byte[] bArr) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(C3205f.m3650a(new byte[]{69, 114, 116, -33, 125, -54, -31, 86, -11, 11, -78, -96, -17, -99, 64, 23, -95, -126, -82, -64, 113, 116, -16, -103, 49, -30, 9, -39, 33, -80, -68, -78, -117, 53, 30, -122, 64, -104, 74, -49, 106, 85, -38, -93}), mac.getAlgorithm()));
        return C3198b.encodeToString(mac.doFinal(bArr), 2);
    }

    /* renamed from: f */
    private boolean m3579f() {
        return this.mContext.checkPermission("android.permission.WRITE_SETTINGS", Binder.getCallingPid(), Binder.getCallingUid()) == 0;
    }
}
