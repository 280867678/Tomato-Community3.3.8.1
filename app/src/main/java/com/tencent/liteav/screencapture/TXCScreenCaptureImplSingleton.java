package com.tencent.liteav.screencapture;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Surface;
import android.view.WindowManager;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.video.TXScreenCapture;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

@TargetApi(21)
/* renamed from: com.tencent.liteav.screencapture.b */
/* loaded from: classes3.dex */
public class TXCScreenCaptureImplSingleton {

    /* renamed from: b */
    private static final String f5244b = "b";

    /* renamed from: c */
    private static TXCScreenCaptureImplSingleton f5245c = new TXCScreenCaptureImplSingleton();

    /* renamed from: j */
    private Handler f5253j;

    /* renamed from: o */
    private Handler f5258o;

    /* renamed from: d */
    private HashMap<Surface, VirtualDisplay> f5247d = new HashMap<>();

    /* renamed from: e */
    private Context f5248e = null;

    /* renamed from: f */
    private MediaProjectionManager f5249f = null;

    /* renamed from: g */
    private MediaProjection f5250g = null;

    /* renamed from: h */
    private HashSet<C3633b> f5251h = new HashSet<>();

    /* renamed from: i */
    private int f5252i = 1;

    /* renamed from: k */
    private HashSet<Object> f5254k = new HashSet<>();

    /* renamed from: l */
    private boolean f5255l = false;

    /* renamed from: m */
    private WeakReference<TXINotifyListener> f5256m = null;

    /* renamed from: n */
    private HandlerThread f5257n = new HandlerThread("TXCScreenCaptureImplSingleton");

    /* renamed from: p */
    private int f5259p = 0;

    /* renamed from: q */
    private HashSet<AbstractC3632a> f5260q = new HashSet<>();

    /* renamed from: r */
    private Runnable f5261r = new Runnable() { // from class: com.tencent.liteav.screencapture.b.1
        @Override // java.lang.Runnable
        public void run() {
            TXCScreenCaptureImplSingleton.this.m766f();
            synchronized (this) {
                if (TXCScreenCaptureImplSingleton.this.f5248e == null) {
                    return;
                }
                int rotation = ((WindowManager) TXCScreenCaptureImplSingleton.this.f5248e.getSystemService("window")).getDefaultDisplay().getRotation();
                if (rotation == 0 || rotation == 2) {
                    if (TXCScreenCaptureImplSingleton.this.f5259p != 0) {
                        String str = TXCScreenCaptureImplSingleton.f5244b;
                        TXCLog.m2915d(str, "ORIENTATION_PORTRAIT, mDelegateSet size = " + TXCScreenCaptureImplSingleton.this.f5260q.size());
                        Iterator it2 = TXCScreenCaptureImplSingleton.this.f5260q.iterator();
                        while (it2.hasNext()) {
                            AbstractC3632a abstractC3632a = (AbstractC3632a) it2.next();
                            if (abstractC3632a != null) {
                                abstractC3632a.mo759a(0);
                            }
                        }
                    }
                    TXCScreenCaptureImplSingleton.this.f5259p = 0;
                    return;
                }
                if (TXCScreenCaptureImplSingleton.this.f5259p != 90) {
                    String str2 = TXCScreenCaptureImplSingleton.f5244b;
                    TXCLog.m2915d(str2, "ORIENTATION_LANDSCAPE, mDelegateSet size = " + TXCScreenCaptureImplSingleton.this.f5260q.size());
                    Iterator it3 = TXCScreenCaptureImplSingleton.this.f5260q.iterator();
                    while (it3.hasNext()) {
                        AbstractC3632a abstractC3632a2 = (AbstractC3632a) it3.next();
                        if (abstractC3632a2 != null) {
                            abstractC3632a2.mo759a(90);
                        }
                    }
                }
                TXCScreenCaptureImplSingleton.this.f5259p = 90;
            }
        }
    };

    /* renamed from: a */
    MediaProjection.Callback f5246a = new MediaProjection.Callback() { // from class: com.tencent.liteav.screencapture.b.2
        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            if (!TXCScreenCaptureImplSingleton.this.f5255l) {
                return;
            }
            TXCScreenCaptureImplSingleton.this.f5255l = false;
        }
    };

    /* renamed from: s */
    private BroadcastReceiver f5262s = new BroadcastReceiver() { // from class: com.tencent.liteav.screencapture.b.7
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !intent.getAction().equalsIgnoreCase("TXScreenCapture.OnAssistantActivityResult")) {
                return;
            }
            TXCScreenCaptureImplSingleton.this.m785a(intent.getIntExtra("TXScreenCapture.RequestCode", 0), intent.getIntExtra("TXScreenCapture.ResultCode", 0), (Intent) intent.getParcelableExtra("TXScreenCapture.ResultData"));
        }
    };

    /* compiled from: TXCScreenCaptureImplSingleton.java */
    /* renamed from: com.tencent.liteav.screencapture.b$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3632a {
        /* renamed from: a */
        void mo759a(int i);
    }

    private TXCScreenCaptureImplSingleton() {
        this.f5253j = null;
        this.f5258o = null;
        this.f5253j = new Handler(Looper.getMainLooper());
        this.f5257n.start();
        this.f5258o = new Handler(this.f5257n.getLooper());
    }

    /* renamed from: a */
    public static TXCScreenCaptureImplSingleton m786a() {
        return f5245c;
    }

    /* renamed from: a */
    public void m781a(TXINotifyListener tXINotifyListener) {
        if (tXINotifyListener == null) {
            this.f5256m = null;
        } else {
            this.f5256m = new WeakReference<>(tXINotifyListener);
        }
    }

    /* renamed from: a */
    public void m784a(Context context) {
        synchronized (this) {
            if (this.f5248e != context) {
                m775b();
                this.f5249f = null;
                this.f5248e = context;
                if (this.f5248e == null) {
                    return;
                }
                if (this.f5249f == null) {
                    this.f5249f = (MediaProjectionManager) this.f5248e.getSystemService("media_projection");
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0051 A[Catch: all -> 0x0049, TryCatch #1 {, blocks: (B:4:0x0002, B:6:0x0007, B:9:0x000d, B:12:0x0051, B:13:0x005b, B:18:0x0024, B:20:0x002a, B:23:0x0040, B:26:0x004c), top: B:3:0x0002, inners: #0 }] */
    @TargetApi(21)
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean m782a(Surface surface, int i, int i2) {
        boolean z;
        synchronized (this) {
            z = false;
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.f5252i != 3 && this.f5252i != 4) {
                C3633b c3633b = new C3633b();
                c3633b.f5274c = i2;
                c3633b.f5273b = i;
                c3633b.f5272a = surface;
                this.f5251h.add(c3633b);
                z = m762h();
                if (!z) {
                    TXCSystemUtil.m2885a(this.f5256m, (int) TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED, "录屏失败");
                }
            }
            MediaProjection mediaProjection = this.f5250g;
            if (this.f5250g != null) {
                this.f5255l = true;
                VirtualDisplay createVirtualDisplay = this.f5250g.createVirtualDisplay("TXCScreenCapture", i, i2, 1, 1, surface, null, null);
                if (createVirtualDisplay != null) {
                    this.f5252i = 3;
                    this.f5247d.put(surface, createVirtualDisplay);
                    z = true;
                }
            }
            if (!z) {
            }
        }
        return z;
    }

    /* renamed from: a */
    public void m783a(Surface surface) {
        synchronized (this) {
            Iterator<C3633b> it2 = this.f5251h.iterator();
            while (true) {
                if (it2.hasNext()) {
                    C3633b next = it2.next();
                    if (next != null && next.f5272a != null && next.f5273b != 0 && next.f5274c != 0 && next.f5272a == surface) {
                        this.f5251h.remove(next);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!this.f5247d.containsKey(surface)) {
                return;
            }
            this.f5247d.get(surface).release();
            this.f5247d.remove(surface);
            m770d();
        }
    }

    /* renamed from: b */
    public void m775b() {
        synchronized (this) {
            m768e();
        }
    }

    /* renamed from: d */
    private void m770d() {
        this.f5252i = 4;
        this.f5253j.postDelayed(new Runnable() { // from class: com.tencent.liteav.screencapture.b.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (TXCScreenCaptureImplSingleton.this) {
                    if (TXCScreenCaptureImplSingleton.this.f5247d.size() == 0) {
                        TXCScreenCaptureImplSingleton.this.m760i();
                    }
                }
            }
        }, 1000L);
    }

    /* renamed from: e */
    private void m768e() {
        for (VirtualDisplay virtualDisplay : this.f5247d.values()) {
            if (virtualDisplay != null) {
                virtualDisplay.release();
            }
        }
        this.f5247d.clear();
        this.f5254k.clear();
        m760i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m766f() {
        this.f5258o.postDelayed(this.f5261r, 50L);
    }

    /* renamed from: g */
    private void m764g() {
        this.f5258o.removeCallbacks(this.f5261r);
    }

    /* renamed from: a */
    public void m780a(final AbstractC3632a abstractC3632a) {
        this.f5253j.post(new Runnable() { // from class: com.tencent.liteav.screencapture.b.4
            @Override // java.lang.Runnable
            public void run() {
                TXCScreenCaptureImplSingleton.this.f5260q.add(abstractC3632a);
            }
        });
    }

    /* renamed from: b */
    public void m774b(final AbstractC3632a abstractC3632a) {
        this.f5253j.post(new Runnable() { // from class: com.tencent.liteav.screencapture.b.5
            @Override // java.lang.Runnable
            public void run() {
                TXCScreenCaptureImplSingleton.this.f5260q.remove(abstractC3632a);
            }
        });
    }

    @TargetApi(21)
    /* renamed from: h */
    private boolean m762h() {
        if (this.f5252i != 1) {
            return true;
        }
        if (this.f5248e == null || this.f5249f == null) {
            return false;
        }
        m766f();
        this.f5252i = 2;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("TXScreenCapture.OnAssistantActivityResult");
        this.f5248e.registerReceiver(this.f5262s, intentFilter);
        Intent intent = new Intent(this.f5248e, TXScreenCapture.TXScreenCaptureAssistantActivity.class);
        intent.addFlags(268435456);
        intent.putExtra("TXScreenCapture.ScreenCaptureIntent", this.f5249f.createScreenCaptureIntent());
        this.f5248e.startActivity(intent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(21)
    /* renamed from: i */
    public void m760i() {
        if (!this.f5254k.isEmpty()) {
            return;
        }
        this.f5255l = false;
        MediaProjection mediaProjection = this.f5250g;
        if (mediaProjection != null) {
            mediaProjection.stop();
            this.f5250g.unregisterCallback(this.f5246a);
        }
        try {
            if (this.f5248e != null) {
                this.f5248e.unregisterReceiver(this.f5262s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f5250g = null;
        this.f5252i = 1;
        m764g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(21)
    /* renamed from: a */
    public void m785a(int i, int i2, Intent intent) {
        try {
            synchronized (this) {
                try {
                    if (this.f5248e != null) {
                        this.f5248e.unregisterReceiver(this.f5262s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i == 1001 && i2 == -1 && this.f5252i == 2) {
                    this.f5250g = this.f5249f.getMediaProjection(i2, intent);
                    this.f5250g.registerCallback(this.f5246a, this.f5253j);
                    this.f5255l = true;
                    if (this.f5251h.size() == 0) {
                        TXCSystemUtil.m2885a(this.f5256m, (int) TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED, "录屏失败");
                        this.f5252i = 1;
                        return;
                    }
                    Iterator<C3633b> it2 = this.f5251h.iterator();
                    while (it2.hasNext()) {
                        C3633b next = it2.next();
                        if (next != null && next.f5272a != null && next.f5273b != 0 && next.f5274c != 0) {
                            VirtualDisplay createVirtualDisplay = this.f5250g.createVirtualDisplay("TXCScreenCapture", next.f5273b, next.f5274c, 1, 1, next.f5272a, null, null);
                            if (createVirtualDisplay == null) {
                                TXCSystemUtil.m2885a(this.f5256m, (int) TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED, "录屏失败");
                                this.f5252i = 1;
                                return;
                            }
                            this.f5247d.put(next.f5272a, createVirtualDisplay);
                        }
                    }
                    this.f5251h.clear();
                    this.f5252i = 3;
                    if (this.f5256m == null) {
                        return;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.screencapture.b.6
                        @Override // java.lang.Runnable
                        public void run() {
                            TXINotifyListener tXINotifyListener;
                            if (TXCScreenCaptureImplSingleton.this.f5256m == null || (tXINotifyListener = (TXINotifyListener) TXCScreenCaptureImplSingleton.this.f5256m.get()) == null) {
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("EVT_MSG", "录屏启动成功");
                            tXINotifyListener.onNotifyEvent(1004, bundle);
                        }
                    });
                    return;
                }
                this.f5252i = 1;
                TXCSystemUtil.m2885a(this.f5256m, (int) TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED, "录屏失败");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.f5252i = 1;
            TXCSystemUtil.m2885a(this.f5256m, (int) TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED, "录屏失败");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCScreenCaptureImplSingleton.java */
    /* renamed from: com.tencent.liteav.screencapture.b$b */
    /* loaded from: classes3.dex */
    public class C3633b {

        /* renamed from: a */
        Surface f5272a;

        /* renamed from: b */
        int f5273b;

        /* renamed from: c */
        int f5274c;

        private C3633b() {
        }
    }
}
