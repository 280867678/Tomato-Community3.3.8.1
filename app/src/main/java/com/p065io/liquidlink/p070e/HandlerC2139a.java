package com.p065io.liquidlink.p070e;

import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.p065io.liquidlink.C2122a;
import com.p065io.liquidlink.C2129c;
import com.p065io.liquidlink.p066a.C2125c;
import com.p065io.liquidlink.p067b.C2128b;
import com.p065io.liquidlink.p073h.RunnableC2171b;
import com.p089pm.liquidlink.listener.AppInstallListener;
import com.p089pm.liquidlink.listener.AppWakeUpListener;
import com.p089pm.liquidlink.listener.GetUpdateApkListener;
import com.p089pm.liquidlink.model.AppData;
import com.p089pm.liquidlink.p092c.C3054b;
import com.p089pm.liquidlink.p092c.C3056d;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* renamed from: com.io.liquidlink.e.a */
/* loaded from: classes3.dex */
public class HandlerC2139a extends AbstractHandlerC2152n {
    public HandlerC2139a(Context context, Looper looper, C2122a c2122a, C2129c c2129c, C2128b c2128b) {
        super(context, looper, c2122a, c2129c, c2128b);
        C3056d.m3731a(HandlerC2139a.class);
    }

    /* renamed from: a */
    private long m4023a(long j) {
        if (j <= 0) {
            return 10L;
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public C2125c m4022a(C2125c c2125c) {
        C2125c m4047e = this.f1431h.m4047e();
        if (m4047e != null) {
            return m4047e;
        }
        this.f1431h.m4057a(c2125c);
        return c2125c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public String m4018a(boolean z, String str) {
        Object[] objArr = new Object[3];
        objArr[0] = z ? C3054b.f1825a : "api.liquidlink.cn";
        objArr[1] = this.f1429f;
        objArr[2] = str;
        return String.format("https://%s/api/v2/android/%s/%s", objArr);
    }

    /* renamed from: b */
    private void m4017b(long j, AppInstallListener appInstallListener) {
        RunnableC2171b runnableC2171b = new RunnableC2171b(this.f1425b, new CallableC2150l(this, j), new C2151m(this, appInstallListener));
        runnableC2171b.m3923a(j);
        this.f1424a.execute(runnableC2171b);
    }

    /* renamed from: b */
    private void m4016b(Uri uri, AppWakeUpListener appWakeUpListener) {
        this.f1424a.execute(new RunnableC2171b(this.f1425b, new CallableC2148j(this, uri), new C2149k(this, appWakeUpListener, uri)));
    }

    /* renamed from: b */
    private void m4015b(GetUpdateApkListener getUpdateApkListener) {
        String str = this.f1426c.getApplicationInfo().sourceDir;
        this.f1424a.execute(new RunnableC2143e(this, str, this.f1426c.getFilesDir() + File.separator + this.f1426c.getPackageName() + ".apk", getUpdateApkListener));
    }

    /* renamed from: e */
    private void m4013e(String str) {
        this.f1424a.execute(new RunnableC2141c(this, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public AppData m4012f(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        AppData appData = new AppData();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("c")) {
            appData.setChannel(jSONObject.optString("c"));
        }
        if (jSONObject.has("d") && !jSONObject.isNull("d")) {
            appData.setData(jSONObject.optString("d"));
        }
        return appData;
    }

    /* renamed from: j */
    private void m4011j() {
        this.f1424a.execute(new RunnableC2147i(this));
    }

    /* renamed from: k */
    private void m4010k() {
        this.f1424a.execute(new RunnableC2142d(this));
    }

    @Override // com.p065io.liquidlink.p070e.AbstractHandlerC2152n
    /* renamed from: a */
    protected ThreadPoolExecutor mo3993a() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(10), new ThreadFactoryC2140b(this), new RejectedExecutionHandlerC2144f(this));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    @Override // com.p065io.liquidlink.p070e.AbstractHandlerC2152n
    /* renamed from: b */
    protected ThreadPoolExecutor mo3991b() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(10), new ThreadFactoryC2145g(this), new RejectedExecutionHandlerC2146h(this));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    /* renamed from: c */
    public AppData m4014c() {
        AppData appData = new AppData();
        appData.setChannel("");
        appData.setData("");
        String m3971a = this.f1432i.m3971a();
        if (!TextUtils.isEmpty(m3971a)) {
            try {
                JSONObject jSONObject = new JSONObject(new String(Base64.decode(m3971a, 10)));
                String string = jSONObject.getString("d");
                if (TextUtils.isEmpty(string)) {
                    appData.setData("");
                } else {
                    appData.setData(string);
                }
                String string2 = jSONObject.getString("c");
                if (TextUtils.isEmpty(string2)) {
                    appData.setChannel("");
                } else {
                    appData.setChannel(string2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return appData;
        }
        appData.setData("");
        return appData;
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
        if (i == 1) {
            m4011j();
        } else if (i == 2) {
            C2153o c2153o = (C2153o) message.obj;
            m4016b((Uri) c2153o.m3996a(), (AppWakeUpListener) c2153o.m3994c());
        } else if (i == 3) {
            C2153o c2153o2 = (C2153o) message.obj;
            m4017b(m4023a(c2153o2.m3995b().longValue()), (AppInstallListener) c2153o2.m3994c());
        } else if (i == 12) {
            m4013e(((Uri) ((C2153o) message.obj).m3996a()).toString());
        } else if (i == 11) {
            m4010k();
        } else if (i == 31) {
            m4015b((GetUpdateApkListener) ((C2153o) message.obj).m3994c());
        } else if (i != 0) {
        } else {
            mo3989d();
        }
    }
}
