package com.zzz.ipfssdk;

import android.content.Context;
import android.util.Pair;
import com.zzz.ipfssdk.callback.OnStateChangeListenner;
import com.zzz.ipfssdk.callback.exception.CodeState;
import com.zzz.ipfssdk.crypt.DoneCallBack;
import com.zzz.ipfssdk.crypt.ResultCallBack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p007b.p025d.p026a.AbstractC0789ea;
import p007b.p025d.p026a.C0733D;
import p007b.p025d.p026a.C0738I;
import p007b.p025d.p026a.C0754Z;
import p007b.p025d.p026a.RunnableC0810v;
import p007b.p025d.p026a.RunnableC0812x;
import p007b.p025d.p026a.RunnableC0814z;
import p007b.p025d.p026a.p028b.C0759a;

/* loaded from: classes4.dex */
public class IpfsSDK implements AbstractC0789ea {

    /* renamed from: a */
    public static IpfsSDK f5956a;

    /* renamed from: b */
    public ExecutorService f5957b = Executors.newFixedThreadPool(1);

    public static IpfsSDK getInstance() {
        if (f5956a == null) {
            synchronized (IpfsSDK.class) {
                if (f5956a == null) {
                    f5956a = new IpfsSDK();
                }
            }
        }
        return f5956a;
    }

    public void enableIpfs(String str, boolean z) {
        C0738I.m5275a().m5267a(str, z);
    }

    public void enableIpfs(boolean z) {
        C0738I.m5275a().m5266a(z);
    }

    public String getVersion() {
        C0738I.m5275a();
        return C0738I.m5261c();
    }

    public Pair<String, CodeState> getVodResourceUrl(String str, String str2) {
        return C0738I.m5275a().m5269a(str, str2);
    }

    public void getVodResourceUrl(String str, String str2, ResultCallBack resultCallBack) {
        this.f5957b.execute(new RunnableC0810v(this, str, str2, resultCallBack));
    }

    public int getWorkMode(String str) {
        return C0738I.m5275a().m5263b(str);
    }

    public void init(Context context, String str, String str2) {
        C0738I.m5275a().m5273a(context, str, str2);
    }

    public boolean isCdnEnable() {
        return C0738I.m5275a().m5257e();
    }

    public void release() {
        C0738I.m5275a().m5256f();
    }

    public void setCdnAutoEnable(boolean z) {
        C0738I.m5275a().m5262b(z);
    }

    public boolean setMaxPlays(int i) {
        if (i > 5) {
            return false;
        }
        C0759a.f500b = i;
        C0754Z.m5186d().m5184f();
        return true;
    }

    public void setOnStateChangeListenner(OnStateChangeListenner onStateChangeListenner) {
        C0738I.m5275a().m5271a(new C0733D(this, onStateChangeListenner));
    }

    public void stop(String str) {
        C0738I.m5275a().m5259c(str);
    }

    public void stop(String str, DoneCallBack doneCallBack) {
        this.f5957b.execute(new RunnableC0812x(this, str, doneCallBack));
    }

    public void stopAll() {
        C0738I.m5275a().m5255g();
    }

    public void stopAll(DoneCallBack doneCallBack) {
        this.f5957b.execute(new RunnableC0814z(this, doneCallBack));
    }
}
