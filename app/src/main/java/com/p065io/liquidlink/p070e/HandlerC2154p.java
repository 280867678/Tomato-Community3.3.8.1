package com.p065io.liquidlink.p070e;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.gen.p059mh.webapps.listener.WebAppInformation;
import com.p065io.liquidlink.C2122a;
import com.p065io.liquidlink.C2129c;
import com.p065io.liquidlink.p067b.C2128b;
import com.p089pm.liquidlink.p091b.C3048a;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import com.p089pm.liquidlink.p092c.C3054b;
import com.p089pm.liquidlink.p092c.C3055c;
import com.p089pm.liquidlink.p092c.C3056d;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/* renamed from: com.io.liquidlink.e.p */
/* loaded from: classes3.dex */
public class HandlerC2154p extends AbstractHandlerC2152n {

    /* renamed from: n */
    private File f1439n;

    /* renamed from: o */
    private Long f1440o;

    /* renamed from: p */
    private long f1441p = System.currentTimeMillis();

    /* renamed from: q */
    private boolean f1442q;

    public HandlerC2154p(Context context, Looper looper, C2122a c2122a, C2129c c2129c, C2128b c2128b) {
        super(context, looper, c2122a, c2129c, c2128b);
        C3056d.m3731a(HandlerC2154p.class);
        this.f1439n = new File(context.getFilesDir(), "AliveLog.txt");
        this.f1442q = c2129c.m4046f();
        c2128b.m4075a(new C2155q(this));
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006b  */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void m3990c() {
        FileReader fileReader;
        if (!this.f1427d.m4106a()) {
            this.f1431h.m4050c();
        } else if (!this.f1428e.m4063e()) {
        } else {
            StringBuilder sb = new StringBuilder();
            if (!this.f1439n.exists()) {
                return;
            }
            BufferedReader bufferedReader = null;
            try {
                try {
                    fileReader = new FileReader(this.f1439n);
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(fileReader);
                        while (true) {
                            try {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                            } catch (IOException unused) {
                                bufferedReader = bufferedReader2;
                                if (fileReader != null) {
                                    fileReader.close();
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (!TextUtils.isEmpty(sb)) {
                                }
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                if (fileReader != null) {
                                    try {
                                        fileReader.close();
                                    } catch (IOException unused2) {
                                        throw th;
                                    }
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                throw th;
                            }
                        }
                        fileReader.close();
                        bufferedReader2.close();
                    } catch (IOException unused3) {
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException unused4) {
                }
            } catch (IOException unused5) {
                fileReader = null;
            } catch (Throwable th3) {
                th = th3;
                fileReader = null;
            }
            if (!TextUtils.isEmpty(sb)) {
                return;
            }
            C3049b m3754a = C3048a.m3749a(false).m3754a(m3988e("stats/events"), m3997i(), sb.toString());
            m4001d(m3754a.m3738e());
            if (m3754a.m3748a() == EnumC3050c.SUCCESS) {
                if (C3055c.f1826a) {
                    C3055c.m3734b("statEvents success : %s", m3754a.m3740d());
                }
                if (!TextUtils.isEmpty(m3754a.m3742c()) && C3055c.f1826a) {
                    C3055c.m3733c("statEvents warning : %s", m3754a.m3742c());
                }
            } else if (C3055c.f1826a) {
                C3055c.m3732d("statEvents fail : %s", m3754a.m3742c());
            }
            if (m3754a.m3748a() != EnumC3050c.SUCCESS) {
                return;
            }
            if (this.f1439n.exists()) {
                this.f1439n.delete();
            }
            this.f1441p = System.currentTimeMillis();
            if (!this.f1442q) {
                return;
            }
            this.f1431h.m4053a(false);
            this.f1442q = false;
        }
    }

    /* renamed from: e */
    private String m3988e(String str) {
        return String.format("https://%s/api/v2/android/%s/%s", C3054b.f1825a, this.f1429f, str);
    }

    /* renamed from: f */
    private void m3987f(String str) {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                if (!this.f1439n.exists()) {
                    this.f1439n.getParentFile().mkdirs();
                    this.f1439n.createNewFile();
                }
                if (this.f1439n.length() >= WebAppInformation.maxCacheSize) {
                    this.f1439n.delete();
                    this.f1439n.createNewFile();
                }
                fileWriter = new FileWriter(this.f1439n, true);
                try {
                    bufferedWriter = new BufferedWriter(fileWriter);
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                }
            } catch (IOException unused2) {
                return;
            }
        } catch (IOException unused3) {
            fileWriter = null;
        } catch (Throwable th2) {
            th = th2;
            fileWriter = null;
        }
        try {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            m3999g();
            bufferedWriter.close();
        } catch (IOException unused4) {
            bufferedWriter2 = bufferedWriter;
            if (bufferedWriter2 != null) {
                bufferedWriter2.close();
            }
            if (fileWriter == null) {
                return;
            }
            fileWriter.close();
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            if (bufferedWriter2 != null) {
                try {
                    bufferedWriter2.close();
                } catch (IOException unused5) {
                    throw th;
                }
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
            throw th;
        }
        fileWriter.close();
    }

    /* renamed from: j */
    private boolean m3986j() {
        if (this.f1440o == null) {
            return false;
        }
        if (this.f1442q) {
            return true;
        }
        return this.f1440o.longValue() * 1000 < System.currentTimeMillis() - this.f1441p;
    }

    @Override // com.p065io.liquidlink.p070e.AbstractHandlerC2152n
    /* renamed from: a */
    protected ThreadPoolExecutor mo3993a() {
        return null;
    }

    @Override // com.p065io.liquidlink.p070e.AbstractHandlerC2152n
    /* renamed from: b */
    protected ThreadPoolExecutor mo3991b() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.p065io.liquidlink.p070e.AbstractHandlerC2152n
    /* renamed from: d */
    public void mo3989d() {
        super.mo3989d();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 21) {
            m3987f((String) ((C2153o) message.obj).m3996a());
        } else if (i == 22) {
            if (!m3986j()) {
                return;
            }
            m3990c();
        } else if (i != 0) {
        } else {
            mo3989d();
        }
    }
}
