package com.dianping.logan;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import p007b.p012b.p013a.AbstractC0587f;
import p007b.p012b.p013a.AbstractC0590i;
import p007b.p012b.p013a.C0581a;

/* loaded from: classes2.dex */
public class CLoganProtocol implements AbstractC0587f {

    /* renamed from: a */
    public static CLoganProtocol f1229a;

    /* renamed from: b */
    public static boolean f1230b;

    /* renamed from: c */
    public boolean f1231c;

    /* renamed from: d */
    public boolean f1232d;

    /* renamed from: e */
    public AbstractC0590i f1233e;

    /* renamed from: f */
    public Set<Integer> f1234f = Collections.synchronizedSet(new HashSet());

    static {
        try {
            System.loadLibrary("logan");
            f1230b = true;
        } catch (Throwable th) {
            th.printStackTrace();
            f1230b = false;
        }
    }

    /* renamed from: b */
    public static boolean m4175b() {
        return f1230b;
    }

    /* renamed from: c */
    public static CLoganProtocol m4174c() {
        if (f1229a == null) {
            synchronized (CLoganProtocol.class) {
                if (f1229a == null) {
                    f1229a = new CLoganProtocol();
                }
            }
        }
        return f1229a;
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4182a() {
        if (!this.f1232d || !f1230b) {
            return;
        }
        try {
            clogan_flush();
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4181a(int i, String str, long j, String str2, long j2, boolean z) {
        if (!this.f1232d || !f1230b) {
            return;
        }
        try {
            int clogan_write = clogan_write(i, str, j, str2, j2, z ? 1 : 0);
            if (clogan_write == -4010 && !C0581a.f125c) {
                return;
            }
            m4178a("clogan_write", clogan_write);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            m4178a("clogan_write", -4060);
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4180a(AbstractC0590i abstractC0590i) {
        this.f1233e = abstractC0590i;
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4179a(String str) {
        if (!this.f1231c || !f1230b) {
            return;
        }
        try {
            int clogan_open = clogan_open(str);
            this.f1232d = true;
            m4178a("clogan_open", clogan_open);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            m4178a("clogan_open", -2070);
        }
    }

    /* renamed from: a */
    public final void m4178a(String str, int i) {
        if (i < 0) {
            if ("clogan_write".endsWith(str) && i != -4060) {
                if (this.f1234f.contains(Integer.valueOf(i))) {
                    return;
                }
                this.f1234f.add(Integer.valueOf(i));
            }
            AbstractC0590i abstractC0590i = this.f1233e;
            if (abstractC0590i == null) {
                return;
            }
            abstractC0590i.mo5254a(str, i);
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4177a(String str, String str2, int i, String str3, String str4) {
        if (this.f1231c) {
            return;
        }
        if (!f1230b) {
            m4178a("logan_loadso", -5020);
            return;
        }
        try {
            int clogan_init = clogan_init(str, str2, i, str3, str4);
            this.f1231c = true;
            m4178a("clogan_init", clogan_init);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            m4178a("clogan_init", -1060);
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4176a(boolean z) {
        if (!this.f1231c || !f1230b) {
            return;
        }
        try {
            clogan_debug(z);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public final native void clogan_debug(boolean z);

    public final native void clogan_flush();

    public final native int clogan_init(String str, String str2, int i, String str3, String str4);

    public final native int clogan_open(String str);

    public final native int clogan_write(int i, String str, long j, String str2, long j2, int i2);
}
