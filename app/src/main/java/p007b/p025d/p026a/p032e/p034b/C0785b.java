package p007b.p025d.p026a.p032e.p034b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.util.net.NetworkType;

/* renamed from: b.d.a.e.b.b */
/* loaded from: classes2.dex */
public class C0785b {

    /* renamed from: a */
    public static C0785b f613a;

    /* renamed from: b */
    public AbstractC0787b f614b;

    /* renamed from: c */
    public C0786a f615c;

    /* renamed from: d */
    public Context f616d;

    /* renamed from: e */
    public long f617e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b.d.a.e.b.b$a */
    /* loaded from: classes2.dex */
    public class C0786a extends BroadcastReceiver {

        /* renamed from: a */
        public NetworkType f618a = null;

        public C0786a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (System.currentTimeMillis() - C0785b.this.f617e < 300) {
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "粘性广播的意外回调，忽略。。。");
            } else if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            } else {
                NetworkType m5027a = C0784a.m5027a(context);
                LogUtil.m121d("NetWorkWatcher", "NetStateChangeReceiver onReceive: " + m5027a + ",action = " + intent.getAction());
                if (this.f618a == m5027a || m5027a == NetworkType.NETWORK_NO) {
                    return;
                }
                this.f618a = m5027a;
                if (C0785b.this.f614b == null) {
                    return;
                }
                C0785b.this.f614b.mo5020a(m5027a != NetworkType.NETWORK_NO, m5027a);
            }
        }
    }

    /* renamed from: b.d.a.e.b.b$b */
    /* loaded from: classes2.dex */
    public interface AbstractC0787b {
        /* renamed from: a */
        void mo5020a(boolean z, NetworkType networkType);
    }

    /* renamed from: a */
    public static C0785b m5025a() {
        if (f613a == null) {
            synchronized (C0785b.class) {
                f613a = new C0785b();
            }
        }
        return f613a;
    }

    /* renamed from: a */
    public void m5024a(Context context, AbstractC0787b abstractC0787b) {
        this.f617e = System.currentTimeMillis();
        this.f614b = abstractC0787b;
        this.f616d = context;
        this.f615c = new C0786a();
        this.f616d.registerReceiver(this.f615c, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    /* renamed from: b */
    public void m5022b() {
        Context context = this.f616d;
        if (context != null) {
            context.unregisterReceiver(this.f615c);
            this.f616d = null;
            this.f615c = null;
            this.f614b = null;
        }
    }
}
