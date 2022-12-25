package com.p065io.liquidlink.p066a;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import com.p065io.liquidlink.p074i.C2173a;
import com.p089pm.liquidlink.p092c.C3053a;
import com.p089pm.liquidlink.p092c.C3056d;

/* renamed from: com.io.liquidlink.a.a */
/* loaded from: classes3.dex */
public class C2123a {

    /* renamed from: d */
    private static volatile C2123a f1370d;

    /* renamed from: b */
    private ClipboardManager f1371b;

    /* renamed from: e */
    private Runnable f1373e = new RunnableC2124b(this);

    /* renamed from: c */
    private Handler f1372c = new Handler();

    private C2123a(Context context) {
        C3056d.m3731a(C2123a.class);
        this.f1371b = (ClipboardManager) context.getSystemService("clipboard");
    }

    /* renamed from: a */
    public static C2123a m4098a(Context context) {
        if (f1370d == null) {
            synchronized (C2123a.class) {
                if (f1370d == null) {
                    f1370d = new C2123a(context);
                }
            }
        }
        return f1370d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m4099a(ClipData clipData) {
        try {
            this.f1371b.setPrimaryClip(clipData);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public ClipData m4093c() {
        try {
            return this.f1371b.getPrimaryClip();
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C2125c m4100a() {
        ClipData.Item itemAt;
        C2125c c2125c = new C2125c();
        ClipData m4093c = m4093c();
        if (m4093c == null || m4093c.getItemCount() <= 0 || (itemAt = m4093c.getItemAt(0)) == null) {
            return c2125c;
        }
        String str = null;
        String htmlText = Build.VERSION.SDK_INT >= 16 ? itemAt.getHtmlText() : null;
        if (itemAt.getText() != null) {
            str = itemAt.getText().toString();
        }
        return m4095a(htmlText, str);
    }

    /* renamed from: a */
    public C2125c m4095a(String str, String str2) {
        C2125c c2125c = new C2125c();
        if (str != null && str.contains(C3053a.f1824d)) {
            c2125c.m4086b(str);
            c2125c.m4087b(2);
        }
        if (str2 != null && C2173a.m3920a(str2, 8).contains(C3053a.f1824d)) {
            c2125c.m4089a(str2);
            c2125c.m4087b(1);
        }
        return c2125c;
    }

    /* renamed from: b */
    public void m4094b() {
        this.f1372c.postDelayed(this.f1373e, 2000L);
    }
}
