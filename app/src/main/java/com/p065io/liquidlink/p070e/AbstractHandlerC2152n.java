package com.p065io.liquidlink.p070e;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.p065io.liquidlink.C2122a;
import com.p065io.liquidlink.C2129c;
import com.p065io.liquidlink.C2162g;
import com.p065io.liquidlink.p066a.C2123a;
import com.p065io.liquidlink.p067b.C2128b;
import com.p065io.liquidlink.p071f.C2160d;
import com.p089pm.liquidlink.listener.AppInstallListener;
import com.p089pm.liquidlink.listener.AppWakeUpListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/* renamed from: com.io.liquidlink.e.n */
/* loaded from: classes3.dex */
public abstract class AbstractHandlerC2152n extends Handler {

    /* renamed from: a */
    protected ThreadPoolExecutor f1424a = mo3993a();

    /* renamed from: b */
    protected ThreadPoolExecutor f1425b = mo3991b();

    /* renamed from: c */
    protected Context f1426c;

    /* renamed from: d */
    protected C2122a f1427d;

    /* renamed from: e */
    protected C2128b f1428e;

    /* renamed from: f */
    protected String f1429f;

    /* renamed from: g */
    protected String f1430g;

    /* renamed from: h */
    protected C2129c f1431h;

    /* renamed from: i */
    protected C2162g f1432i;

    /* renamed from: j */
    protected C2123a f1433j;

    /* renamed from: k */
    protected C2160d f1434k;

    /* renamed from: l */
    protected Map f1435l;

    public AbstractHandlerC2152n(Context context, Looper looper, C2122a c2122a, C2129c c2129c, C2128b c2128b) {
        super(looper);
        this.f1426c = context;
        this.f1427d = c2122a;
        this.f1428e = c2128b;
        this.f1431h = c2129c;
        this.f1432i = C2162g.m3970a(context);
        this.f1433j = C2123a.m4098a(context);
        this.f1434k = C2160d.m3978a(context);
    }

    /* renamed from: a */
    protected abstract ThreadPoolExecutor mo3993a();

    /* renamed from: a */
    public void m4006a(long j, AppInstallListener appInstallListener) {
        Message obtain = Message.obtain();
        obtain.what = 3;
        obtain.obj = new C2153o(null, Long.valueOf(j), appInstallListener);
        sendMessage(obtain);
    }

    /* renamed from: a */
    public void m4005a(Uri uri) {
        Message obtain = Message.obtain();
        obtain.what = 12;
        obtain.obj = new C2153o(uri, null, null);
        sendMessage(obtain);
    }

    /* renamed from: a */
    public void m4004a(Uri uri, AppWakeUpListener appWakeUpListener) {
        Message obtain = Message.obtain();
        obtain.what = 2;
        obtain.obj = new C2153o(uri, null, appWakeUpListener);
        sendMessage(obtain);
    }

    /* renamed from: a */
    public void m4003a(String str) {
        Message obtain = Message.obtain();
        obtain.what = 21;
        obtain.obj = new C2153o(str, null, null);
        sendMessage(obtain);
    }

    /* renamed from: b */
    protected abstract ThreadPoolExecutor mo3991b();

    /* renamed from: b */
    public void m4002b(String str) {
        this.f1429f = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: d */
    public void mo3989d() {
        ThreadPoolExecutor threadPoolExecutor = this.f1424a;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }
        ThreadPoolExecutor threadPoolExecutor2 = this.f1425b;
        if (threadPoolExecutor2 != null) {
            threadPoolExecutor2.shutdown();
        }
        getLooper().quit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: d */
    public void m4001d(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        C2128b m4068b = C2128b.m4068b(str);
        if (!this.f1428e.equals(m4068b)) {
            this.f1428e.m4074a(m4068b);
            this.f1431h.m4056a(this.f1428e);
            this.f1428e.m4059i();
        }
        if (TextUtils.isEmpty(this.f1428e.m4060h())) {
            return;
        }
        this.f1434k.m3975b(this.f1429f, this.f1428e.m4060h());
    }

    /* renamed from: e */
    public void m4000e() {
        Message obtain = Message.obtain();
        obtain.what = 1;
        sendMessage(obtain);
    }

    /* renamed from: g */
    public void m3999g() {
        Message obtain = Message.obtain();
        obtain.what = 22;
        obtain.obj = null;
        sendMessage(obtain);
    }

    /* renamed from: h */
    public String m3998h() {
        if (this.f1430g == null) {
            this.f1430g = "";
        }
        return this.f1430g;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: i */
    public Map m3997i() {
        if (this.f1435l == null) {
            this.f1435l = new HashMap();
            this.f1435l.put("deviceId", this.f1432i.m3961j());
            this.f1435l.put("macAddress", this.f1432i.m3960k());
            this.f1435l.put("serialNumber", this.f1432i.m3959l());
            this.f1435l.put("androidId", this.f1432i.m3958m());
            this.f1435l.put("pkg", this.f1432i.m3969b());
            this.f1435l.put("certFinger", this.f1432i.m3968c());
            this.f1435l.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, this.f1432i.m3967d());
            this.f1435l.put("versionCode", String.valueOf(this.f1432i.m3966e()));
            this.f1435l.put("apiVersion", "2.5.0");
            this.f1435l.put("dcc", m3998h());
        }
        this.f1435l.put("installId", TextUtils.isEmpty(this.f1428e.m4060h()) ? this.f1434k.m3977a(this.f1429f) : this.f1428e.m4060h());
        return this.f1435l;
    }
}
