package com.p065io.liquidlink;

import android.content.SharedPreferences;
import com.p065io.liquidlink.p066a.C2125c;
import com.p065io.liquidlink.p067b.C2128b;
import com.p089pm.liquidlink.p090a.C3043a;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* renamed from: com.io.liquidlink.c */
/* loaded from: classes3.dex */
public class C2129c {

    /* renamed from: a */
    private Future f1388a;

    public C2129c(Future future) {
        this.f1388a = future;
        new HashMap();
    }

    /* renamed from: a */
    public C3043a m4058a() {
        try {
            return C3043a.m3761a(((SharedPreferences) this.f1388a.get()).getInt("FM_init_state", C3043a.f1801a.m3762a()));
        } catch (InterruptedException | ExecutionException unused) {
            return C3043a.f1801a;
        }
    }

    /* renamed from: a */
    public C3043a m4055a(String str) {
        try {
            return C3043a.m3761a(((SharedPreferences) this.f1388a.get()).getInt(str, C3043a.f1801a.m3762a()));
        } catch (InterruptedException | ExecutionException unused) {
            return C3043a.f1801a;
        }
    }

    /* renamed from: a */
    public void m4057a(C2125c c2125c) {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.putString("FM_pb_data", C2125c.m4090a(c2125c));
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* renamed from: a */
    public void m4056a(C2128b c2128b) {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.putString("FM_config_data", c2128b.toString());
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* renamed from: a */
    public void m4054a(String str, C3043a c3043a) {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.putInt(str, c3043a.m3762a());
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* renamed from: a */
    public void m4053a(boolean z) {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.putBoolean("FM_first_background", z);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* renamed from: b */
    public String m4052b() {
        try {
            return ((SharedPreferences) this.f1388a.get()).getString("FM_init_data", "");
        } catch (InterruptedException | ExecutionException unused) {
            return "";
        }
    }

    /* renamed from: b */
    public void m4051b(String str) {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.putString("FM_init_data", str);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* renamed from: c */
    public String m4050c() {
        try {
            return ((SharedPreferences) this.f1388a.get()).getString("FM_init_msg", "");
        } catch (InterruptedException | ExecutionException unused) {
            return "";
        }
    }

    /* renamed from: c */
    public void m4049c(String str) {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.putString("FM_init_msg", str);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    /* renamed from: d */
    public C2128b m4048d() {
        try {
            return C2128b.m4068b(((SharedPreferences) this.f1388a.get()).getString("FM_config_data", ""));
        } catch (InterruptedException | ExecutionException unused) {
            return new C2128b();
        }
    }

    /* renamed from: e */
    public C2125c m4047e() {
        try {
            return C2125c.m4083c(((SharedPreferences) this.f1388a.get()).getString("FM_pb_data", ""));
        } catch (InterruptedException | ExecutionException unused) {
            return null;
        }
    }

    /* renamed from: f */
    public boolean m4046f() {
        try {
            return ((SharedPreferences) this.f1388a.get()).getBoolean("FM_first_background", true);
        } catch (InterruptedException | ExecutionException unused) {
            return true;
        }
    }

    /* renamed from: g */
    public void m4045g() {
        try {
            SharedPreferences.Editor edit = ((SharedPreferences) this.f1388a.get()).edit();
            edit.clear();
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }
}
