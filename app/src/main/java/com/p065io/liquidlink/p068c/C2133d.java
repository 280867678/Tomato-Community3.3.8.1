package com.p065io.liquidlink.p068c;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.TextUtils;
import com.eclipsesource.p056v8.Platform;
import com.p065io.liquidlink.p074i.C2173a;
import com.p089pm.liquidlink.p092c.C3056d;
import java.io.File;

/* renamed from: com.io.liquidlink.c.d */
/* loaded from: classes3.dex */
public class C2133d {

    /* renamed from: f */
    private static C2133d f1390f;

    /* renamed from: g */
    private static final Object f1391g = new Object();

    private C2133d() {
        C3056d.m3731a(C2133d.class);
    }

    /* renamed from: a */
    private int m4037a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        return str.split("package:").length;
    }

    /* renamed from: a */
    public static C2133d m4039a() {
        synchronized (f1391g) {
            if (f1390f == null) {
                f1390f = new C2133d();
            }
        }
        return f1390f;
    }

    /* renamed from: b */
    private String m4036b(String str) {
        String m4042a = C2130a.m4044a().m4042a(str);
        if (TextUtils.isEmpty(m4042a)) {
            return null;
        }
        return m4042a;
    }

    /* renamed from: a */
    public boolean m4038a(Context context) {
        String m4036b = m4036b(C2173a.m3921a("Z3NtLnZlcnNpb24uYmFzZWJhbmQ="));
        int i = (m4036b == null || m4036b.contains("1.0.0.0")) ? 1 : 0;
        String m4036b2 = m4036b(C2173a.m3921a("cm8uYnVpbGQuZmxhdm9y"));
        if (m4036b2 == null || m4036b2.contains("vbox") || m4036b2.contains("sdk_gphone") || m4036b2.contains("sdk_phone")) {
            i++;
        }
        String m4036b3 = m4036b(C2173a.m3921a("cm8ucHJvZHVjdC5ib2FyZA=="));
        if (m4036b3 == null || m4036b3.contains(Platform.ANDROID) || m4036b3.contains("goldfish")) {
            i++;
        }
        String m4036b4 = m4036b(C2173a.m3921a("cm8uYm9hcmQucGxhdGZvcm0="));
        if (m4036b4 == null || m4036b4.contains(Platform.ANDROID)) {
            i++;
        }
        String m4036b5 = m4036b(C2173a.m3921a("cm8uaGFyZHdhcmU="));
        if (m4036b5 == null) {
            i++;
        } else if (m4036b5.toLowerCase().contains("ttvm") || m4036b5.toLowerCase().contains("nox")) {
            i += 5;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (String str : new String[]{"L3N5c3RlbS9iaW4vZHJvaWQ0eC1wcm9w", "L3N5c3RlbS9saWIvbGlibm94ZC5zbw==", "L3N5c3RlbS9iaW4vdHRWTS1wcm9w", "L3N5c3RlbS9iaW4vYnN0c2h1dGRvd24=", "L3N5c3RlbS9iaW4vbWljcm92aXJ0LXByb3A=", "L3N5c3RlbS9iaW4vbmVtdVZNLXByb3A=", "L3N5c3RlbS9iaW4vYW5kcm9WTS1wcm9w", "L3N5c3RlbS9iaW4vZ2VueW1vdGlvbi12Ym94LXNm"}) {
            String m3921a = C2173a.m3921a(str);
            if (new File(m3921a).exists()) {
                sb.append(m3921a);
                sb.append(";");
                z = true;
            }
        }
        if (z) {
            i += 5;
        }
        if (new File(C2173a.m3921a("L3N5c3RlbS9ldGMvZXhjbHVkZWQtaW5wdXQtZGV2aWNlcy54bWw=")).exists()) {
            i = Build.VERSION.SDK_INT < 26 ? i + 3 : i + 1;
        }
        if (!context.getPackageManager().hasSystemFeature(C2173a.m3921a("YW5kcm9pZC5oYXJkd2FyZS5ibHVldG9vdGg="))) {
            i += 3;
        }
        if (!context.getPackageManager().hasSystemFeature(C2173a.m3921a("YW5kcm9pZC5oYXJkd2FyZS5jYW1lcmEuZmxhc2g="))) {
            i += 3;
        }
        if (((SensorManager) context.getSystemService("sensor")).getSensorList(-1).size() < 9) {
            i++;
        }
        if (m4037a(C2130a.m4044a().m4041b(C2173a.m3921a("cG0gbGlzdCBwYWNrYWdlIC0z"))) < 5) {
            i++;
        }
        return i >= 5;
    }
}
