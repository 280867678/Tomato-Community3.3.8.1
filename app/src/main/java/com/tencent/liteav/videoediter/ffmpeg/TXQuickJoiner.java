package com.tencent.liteav.videoediter.ffmpeg;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* renamed from: com.tencent.liteav.videoediter.ffmpeg.b */
/* loaded from: classes3.dex */
public class TXQuickJoiner implements TXFFQuickJointerJNI.AbstractC3671a {

    /* renamed from: a */
    private volatile boolean f5526a;

    /* renamed from: b */
    private volatile boolean f5527b;

    /* renamed from: c */
    private volatile boolean f5528c;

    /* renamed from: g */
    private Handler f5532g;

    /* renamed from: h */
    private HandlerThread f5533h;

    /* renamed from: i */
    private volatile AbstractC3670a f5534i;

    /* renamed from: e */
    private TXFFQuickJointerJNI f5530e = new TXFFQuickJointerJNI();

    /* renamed from: f */
    private Handler f5531f = new Handler(Looper.getMainLooper());

    /* renamed from: d */
    private volatile boolean f5529d = false;

    /* compiled from: TXQuickJoiner.java */
    /* renamed from: com.tencent.liteav.videoediter.ffmpeg.b$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3670a {
        /* renamed from: a */
        void mo256a(TXQuickJoiner tXQuickJoiner, float f);

        /* renamed from: a */
        void mo255a(TXQuickJoiner tXQuickJoiner, int i, String str);
    }

    public TXQuickJoiner() {
        this.f5530e.setOnJoinerCallback(this);
    }

    /* renamed from: g */
    private void m471g() {
        HandlerThread handlerThread = this.f5533h;
        if (handlerThread == null || !handlerThread.isAlive() || this.f5533h.isInterrupted()) {
            this.f5533h = new HandlerThread("Quick Jointer Thread");
            this.f5533h.start();
            this.f5532g = new Handler(this.f5533h.getLooper());
        }
    }

    /* renamed from: h */
    private void m470h() {
        if (this.f5533h != null) {
            this.f5532g.post(new Runnable() { // from class: com.tencent.liteav.videoediter.ffmpeg.b.1
                @Override // java.lang.Runnable
                public void run() {
                    TXQuickJoiner.this.f5533h.quit();
                    TXQuickJoiner.this.f5533h = null;
                    TXQuickJoiner.this.f5532g.removeCallbacksAndMessages(null);
                    TXQuickJoiner.this.f5532g = null;
                }
            });
        }
    }

    /* renamed from: a */
    public int m482a(String str) {
        if (this.f5528c) {
            TXCLog.m2914e("TXFFQuickJointerWrapper", "quick jointer is started, you must stop first!");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.m2914e("TXFFQuickJointerWrapper", "quick jointer setDstPath empty！！！");
            return -1;
        } else {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            try {
                if (!file.createNewFile()) {
                    this.f5526a = false;
                    return -1;
                }
                this.f5530e.setDstPath(str);
                this.f5526a = !TextUtils.isEmpty(str);
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                this.f5526a = false;
                return -1;
            }
        }
    }

    /* renamed from: a */
    public boolean m490a() {
        return this.f5530e.verify() == 0;
    }

    /* renamed from: a */
    public int m481a(List<String> list) {
        if (this.f5528c) {
            TXCLog.m2914e("TXFFQuickJointerWrapper", "qucik jointer is started, you must stop frist!");
            return -1;
        }
        this.f5530e.setSrcPaths(list);
        this.f5527b = true;
        return 0;
    }

    /* renamed from: b */
    public int m480b() {
        if (!this.f5527b || !this.f5526a) {
            return -1;
        }
        if (this.f5528c) {
            TXCLog.m2914e("TXFFQuickJointerWrapper", "qucik jointer is started, you must stop frist!");
            return -1;
        }
        m471g();
        this.f5532g.post(new Runnable() { // from class: com.tencent.liteav.videoediter.ffmpeg.b.2
            @Override // java.lang.Runnable
            public void run() {
                TXQuickJoiner.this.f5528c = true;
                if (TXQuickJoiner.this.f5530e.verify() != 0) {
                    TXQuickJoiner.this.m489a(-1, "不符合快速合成的要求");
                    return;
                }
                int start = TXQuickJoiner.this.f5530e.start();
                if (TXQuickJoiner.this.f5528c) {
                    if (start < 0) {
                        TXQuickJoiner.this.m489a(-2, "合成失败");
                    } else {
                        TXQuickJoiner.this.m489a(0, "合成成功");
                    }
                    TXQuickJoiner.this.f5528c = false;
                    return;
                }
                TXQuickJoiner.this.m489a(1, "取消合成");
            }
        });
        return 0;
    }

    /* renamed from: c */
    public int m478c() {
        if (this.f5528c) {
            this.f5530e.stop();
            m470h();
            this.f5528c = false;
            return 0;
        }
        return -1;
    }

    /* renamed from: d */
    public void m476d() {
        if (!this.f5529d) {
            m478c();
            this.f5530e.setOnJoinerCallback(null);
            this.f5530e.destroy();
            this.f5534i = null;
            this.f5530e = null;
            this.f5529d = true;
        }
    }

    /* renamed from: e */
    public int m474e() {
        TXFFQuickJointerJNI tXFFQuickJointerJNI = this.f5530e;
        if (tXFFQuickJointerJNI != null) {
            return tXFFQuickJointerJNI.getVideoWidth();
        }
        return 0;
    }

    /* renamed from: f */
    public int m472f() {
        TXFFQuickJointerJNI tXFFQuickJointerJNI = this.f5530e;
        if (tXFFQuickJointerJNI != null) {
            return tXFFQuickJointerJNI.getVideoHeight();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m489a(final int i, final String str) {
        if (this.f5534i != null) {
            this.f5531f.post(new Runnable() { // from class: com.tencent.liteav.videoediter.ffmpeg.b.3
                @Override // java.lang.Runnable
                public void run() {
                    if (TXQuickJoiner.this.f5534i != null) {
                        TXQuickJoiner.this.f5534i.mo255a(TXQuickJoiner.this, i, str);
                    }
                }
            });
        }
    }

    /* renamed from: a */
    public void m488a(AbstractC3670a abstractC3670a) {
        this.f5534i = abstractC3670a;
    }

    @Override // com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI.AbstractC3671a
    /* renamed from: a */
    public void mo459a(final float f) {
        if (this.f5534i != null) {
            this.f5531f.post(new Runnable() { // from class: com.tencent.liteav.videoediter.ffmpeg.b.4
                @Override // java.lang.Runnable
                public void run() {
                    if (TXQuickJoiner.this.f5534i != null) {
                        TXQuickJoiner.this.f5534i.mo256a(TXQuickJoiner.this, f);
                    }
                }
            });
        }
    }
}
