package com.p097ta.utdid2.p100b.p101a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.p097ta.utdid2.p098a.p099a.C3208g;
import com.p097ta.utdid2.p100b.p101a.AbstractC3211b;
import java.io.File;
import java.util.Map;

/* renamed from: com.ta.utdid2.b.a.c */
/* loaded from: classes3.dex */
public class C3214c {

    /* renamed from: a */
    private SharedPreferences f1915a;

    /* renamed from: a */
    private AbstractC3211b f1917a;

    /* renamed from: a */
    private C3215d f1918a;

    /* renamed from: b */
    private String f1919b;

    /* renamed from: c */
    private String f1920c;

    /* renamed from: f */
    private boolean f1921f;

    /* renamed from: g */
    private boolean f1922g;

    /* renamed from: h */
    private boolean f1923h;

    /* renamed from: i */
    private boolean f1924i;
    private Context mContext;

    /* renamed from: a */
    private SharedPreferences.Editor f1914a = null;

    /* renamed from: a */
    private AbstractC3211b.AbstractC3212a f1916a = null;

    /* JADX WARN: Removed duplicated region for block: B:16:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0167 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0183 A[Catch: Exception -> 0x018f, TRY_LEAVE, TryCatch #1 {Exception -> 0x018f, blocks: (B:37:0x017f, B:39:0x0183), top: B:36:0x017f }] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C3214c(Context context, String str, String str2, boolean z, boolean z2) {
        long j;
        long j2;
        long j3;
        int i;
        boolean z3;
        SharedPreferences sharedPreferences;
        this.f1919b = "";
        this.f1920c = "";
        this.f1921f = false;
        this.f1922g = false;
        this.f1923h = false;
        String str3 = null;
        this.f1915a = null;
        this.f1917a = null;
        this.mContext = null;
        this.f1918a = null;
        this.f1924i = false;
        this.f1921f = z;
        this.f1924i = z2;
        this.f1919b = str2;
        this.f1920c = str;
        this.mContext = context;
        if (context != null) {
            this.f1915a = context.getSharedPreferences(str2, 0);
            j = this.f1915a.getLong("t", 0L);
        } else {
            j = 0;
        }
        try {
            str3 = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (C3208g.m3647a(str3)) {
            this.f1923h = false;
            this.f1922g = false;
        } else if (str3.equals("mounted")) {
            this.f1923h = true;
            this.f1922g = true;
        } else if (str3.equals("mounted_ro")) {
            this.f1922g = true;
            this.f1923h = false;
        } else {
            this.f1923h = false;
            this.f1922g = false;
        }
        try {
            if ((this.f1922g || this.f1923h) && context != null && !C3208g.m3647a(str)) {
                this.f1918a = m3638a(str);
                C3215d c3215d = this.f1918a;
                if (c3215d != null) {
                    try {
                        this.f1917a = c3215d.m3630a(str2, 0);
                        j2 = this.f1917a.getLong("t", 0L);
                    } catch (Exception unused) {
                    }
                    if (!z2) {
                        int i2 = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                        if (i2 > 0) {
                            m3640a(this.f1915a, this.f1917a);
                            this.f1917a = this.f1918a.m3630a(str2, 0);
                        } else if (i2 >= 0) {
                            if (i2 == 0) {
                                m3640a(this.f1915a, this.f1917a);
                                this.f1917a = this.f1918a.m3630a(str2, 0);
                            }
                            if (j != j2 && (j != 0 || j2 != 0)) {
                                return;
                            }
                            long currentTimeMillis = System.currentTimeMillis();
                            z3 = this.f1924i;
                            if (!z3 && (!z3 || j != 0 || j2 != 0)) {
                                return;
                            }
                            sharedPreferences = this.f1915a;
                            if (sharedPreferences != null) {
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putLong("t2", currentTimeMillis);
                                edit.commit();
                            }
                            if (this.f1917a == null) {
                                return;
                            }
                            AbstractC3211b.AbstractC3212a mo3627a = this.f1917a.mo3627a();
                            mo3627a.mo3614a("t2", currentTimeMillis);
                            mo3627a.commit();
                            return;
                        } else {
                            m3639a(this.f1917a, this.f1915a);
                            this.f1915a = context.getSharedPreferences(str2, 0);
                        }
                    } else {
                        long j4 = this.f1915a.getLong("t2", 0L);
                        try {
                            j3 = this.f1917a.getLong("t2", 0L);
                            i = (j4 > j3 ? 1 : (j4 == j3 ? 0 : -1));
                        } catch (Exception unused2) {
                        }
                        if (i < 0 && j4 > 0) {
                            m3640a(this.f1915a, this.f1917a);
                            this.f1917a = this.f1918a.m3630a(str2, 0);
                        } else if (i <= 0 || j3 <= 0) {
                            int i3 = (j4 > 0L ? 1 : (j4 == 0L ? 0 : -1));
                            if (i3 == 0 && j3 > 0) {
                                m3639a(this.f1917a, this.f1915a);
                                this.f1915a = context.getSharedPreferences(str2, 0);
                            } else if (j3 != 0 || i3 <= 0) {
                                if (i == 0) {
                                    m3640a(this.f1915a, this.f1917a);
                                    this.f1917a = this.f1918a.m3630a(str2, 0);
                                }
                                j2 = j3;
                                j = j4;
                            } else {
                                m3640a(this.f1915a, this.f1917a);
                                this.f1917a = this.f1918a.m3630a(str2, 0);
                            }
                        } else {
                            m3639a(this.f1917a, this.f1915a);
                            this.f1915a = context.getSharedPreferences(str2, 0);
                        }
                        j2 = j3;
                        j = j4;
                    }
                    if (j != j2) {
                    }
                    long currentTimeMillis2 = System.currentTimeMillis();
                    z3 = this.f1924i;
                    if (!z3) {
                    }
                    sharedPreferences = this.f1915a;
                    if (sharedPreferences != null) {
                    }
                    if (this.f1917a == null) {
                    }
                }
            }
            if (this.f1917a == null) {
            }
        } catch (Exception unused3) {
            return;
        }
        j2 = 0;
        if (j != j2) {
        }
        long currentTimeMillis22 = System.currentTimeMillis();
        z3 = this.f1924i;
        if (!z3) {
        }
        sharedPreferences = this.f1915a;
        if (sharedPreferences != null) {
        }
    }

    /* renamed from: a */
    private C3215d m3638a(String str) {
        File m3637a = m3637a(str);
        if (m3637a != null) {
            this.f1918a = new C3215d(m3637a.getAbsolutePath());
            return this.f1918a;
        }
        return null;
    }

    /* renamed from: a */
    private File m3637a(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory != null) {
            File file = new File(String.format("%s%s%s", externalStorageDirectory.getAbsolutePath(), File.separator, str));
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    /* renamed from: a */
    private void m3640a(SharedPreferences sharedPreferences, AbstractC3211b abstractC3211b) {
        AbstractC3211b.AbstractC3212a mo3627a;
        if (sharedPreferences == null || abstractC3211b == null || (mo3627a = abstractC3211b.mo3627a()) == null) {
            return;
        }
        mo3627a.mo3611b();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                mo3627a.mo3613a(key, (String) value);
            } else if (value instanceof Integer) {
                mo3627a.mo3615a(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                mo3627a.mo3614a(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                mo3627a.mo3616a(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                mo3627a.mo3612a(key, ((Boolean) value).booleanValue());
            }
        }
        try {
            mo3627a.commit();
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    private void m3639a(AbstractC3211b abstractC3211b, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor edit;
        if (abstractC3211b == null || sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
            return;
        }
        edit.clear();
        for (Map.Entry<String, ?> entry : abstractC3211b.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                edit.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                edit.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, ((Boolean) value).booleanValue());
            }
        }
        edit.commit();
    }

    /* renamed from: c */
    private boolean m3635c() {
        AbstractC3211b abstractC3211b = this.f1917a;
        if (abstractC3211b != null) {
            boolean mo3620b = abstractC3211b.mo3620b();
            if (!mo3620b) {
                commit();
            }
            return mo3620b;
        }
        return false;
    }

    /* renamed from: b */
    private void m3636b() {
        AbstractC3211b abstractC3211b;
        SharedPreferences sharedPreferences;
        if (this.f1914a == null && (sharedPreferences = this.f1915a) != null) {
            this.f1914a = sharedPreferences.edit();
        }
        if (this.f1923h && this.f1916a == null && (abstractC3211b = this.f1917a) != null) {
            this.f1916a = abstractC3211b.mo3627a();
        }
        m3635c();
    }

    public void putString(String str, String str2) {
        if (C3208g.m3647a(str) || str.equals("t")) {
            return;
        }
        m3636b();
        SharedPreferences.Editor editor = this.f1914a;
        if (editor != null) {
            editor.putString(str, str2);
        }
        AbstractC3211b.AbstractC3212a abstractC3212a = this.f1916a;
        if (abstractC3212a == null) {
            return;
        }
        abstractC3212a.mo3613a(str, str2);
    }

    public void remove(String str) {
        if (C3208g.m3647a(str) || str.equals("t")) {
            return;
        }
        m3636b();
        SharedPreferences.Editor editor = this.f1914a;
        if (editor != null) {
            editor.remove(str);
        }
        AbstractC3211b.AbstractC3212a abstractC3212a = this.f1916a;
        if (abstractC3212a == null) {
            return;
        }
        abstractC3212a.mo3617a(str);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:1|(4:3|(1:7)|8|(9:10|11|(1:15)|16|17|18|19|(4:21|(2:23|(2:25|(3:27|(1:29)(1:31)|30))(3:32|33|(1:35)))|40|(3:46|47|(1:49)))|52))|57|11|(2:13|15)|16|17|18|19|(0)|52|(1:(7:37|40|(2:42|44)|46|47|(0)|52))) */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0085, code lost:
        if (r6.f1916a.commit() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0038, code lost:
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0039, code lost:
        r2.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x009f A[Catch: Exception -> 0x00a9, TRY_LEAVE, TryCatch #0 {Exception -> 0x00a9, blocks: (B:47:0x009b, B:49:0x009f), top: B:46:0x009b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean commit() {
        boolean z;
        String str;
        Context context;
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences.Editor editor = this.f1914a;
        if (editor != null) {
            if (!this.f1924i && this.f1915a != null) {
                editor.putLong("t", currentTimeMillis);
            }
            if (!this.f1914a.commit()) {
                z = false;
                if (this.f1915a != null && (context = this.mContext) != null) {
                    this.f1915a = context.getSharedPreferences(this.f1919b, 0);
                }
                str = null;
                str = Environment.getExternalStorageState();
                if (!C3208g.m3647a(str)) {
                    if (str.equals("mounted")) {
                        if (this.f1917a == null) {
                            C3215d m3638a = m3638a(this.f1920c);
                            if (m3638a != null) {
                                this.f1917a = m3638a.m3630a(this.f1919b, 0);
                                if (!this.f1924i) {
                                    m3640a(this.f1915a, this.f1917a);
                                } else {
                                    m3639a(this.f1917a, this.f1915a);
                                }
                                this.f1916a = this.f1917a.mo3627a();
                            }
                        } else {
                            try {
                                if (this.f1916a != null) {
                                }
                            } catch (Exception unused) {
                            }
                        }
                    }
                    if (!str.equals("mounted") || (str.equals("mounted_ro") && this.f1917a != null)) {
                        try {
                            if (this.f1918a != null) {
                                this.f1917a = this.f1918a.m3630a(this.f1919b, 0);
                            }
                        } catch (Exception unused2) {
                        }
                    }
                }
                return z;
            }
        }
        z = true;
        if (this.f1915a != null) {
            this.f1915a = context.getSharedPreferences(this.f1919b, 0);
        }
        str = null;
        str = Environment.getExternalStorageState();
        if (!C3208g.m3647a(str)) {
        }
        return z;
        z = false;
        if (!str.equals("mounted")) {
        }
        if (this.f1918a != null) {
        }
        return z;
    }

    public String getString(String str) {
        m3635c();
        SharedPreferences sharedPreferences = this.f1915a;
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString(str, "");
            if (!C3208g.m3647a(string)) {
                return string;
            }
        }
        AbstractC3211b abstractC3211b = this.f1917a;
        return abstractC3211b != null ? abstractC3211b.getString(str, "") : "";
    }
}
