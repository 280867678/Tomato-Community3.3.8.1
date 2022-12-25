package com.tencent.liteav.p126k;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.p115b.TXCGPULoopupFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUloopupInvertFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;
import java.io.IOException;

/* renamed from: com.tencent.liteav.k.g */
/* loaded from: classes3.dex */
public class TXCGPULightingFilter {

    /* renamed from: c */
    private Context f4519c;

    /* renamed from: a */
    private TXCGPUloopupInvertFilter f4517a = null;

    /* renamed from: b */
    private TXCGPULoopupFilter f4518b = null;

    /* renamed from: d */
    private String f4520d = "Lighting";

    /* renamed from: e */
    private TXCVideoEffect.C3541h f4521e = null;

    public TXCGPULightingFilter(Context context) {
        this.f4519c = null;
        this.f4519c = context;
    }

    /* renamed from: a */
    public boolean m1339a(int i, int i2) {
        return m1335c(i, i2);
    }

    /* renamed from: c */
    private boolean m1335c(int i, int i2) {
        Context context = this.f4519c;
        if (context == null) {
            TXCLog.m2914e(this.f4520d, "mContext is null!");
            return false;
        }
        AssetManager assets = context.getResources().getAssets();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("fennen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.f4517a == null) {
            this.f4517a = new TXCGPUloopupInvertFilter(bitmap);
            this.f4517a.mo1353a(true);
            if (!this.f4517a.mo2653c()) {
                TXCLog.m2914e(this.f4520d, "mLoopupInvertFilter init error!");
                return false;
            }
        }
        this.f4517a.mo1333a(i, i2);
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("qingliang.png"));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (this.f4518b == null) {
            this.f4518b = new TXCGPULoopupFilter(bitmap);
            this.f4518b.mo1353a(true);
            if (!this.f4518b.mo2653c()) {
                TXCLog.m2914e(this.f4520d, "mLoopupFilter init error!");
                return false;
            }
        }
        this.f4518b.mo1333a(i, i2);
        return true;
    }

    /* renamed from: a */
    public void m1341a() {
        TXCGPUloopupInvertFilter tXCGPUloopupInvertFilter = this.f4517a;
        if (tXCGPUloopupInvertFilter != null) {
            tXCGPUloopupInvertFilter.mo1351e();
            this.f4517a = null;
        }
        TXCGPULoopupFilter tXCGPULoopupFilter = this.f4518b;
        if (tXCGPULoopupFilter != null) {
            tXCGPULoopupFilter.mo1351e();
            this.f4518b = null;
        }
    }

    /* renamed from: b */
    public void m1336b(int i, int i2) {
        m1335c(i, i2);
    }

    /* renamed from: a */
    public void m1338a(TXCVideoEffect.C3541h c3541h) {
        this.f4521e = c3541h;
        TXCVideoEffect.C3541h c3541h2 = this.f4521e;
        if (c3541h2 != null) {
            TXCGPUloopupInvertFilter tXCGPUloopupInvertFilter = this.f4517a;
            if (tXCGPUloopupInvertFilter != null) {
                tXCGPUloopupInvertFilter.m2659a(c3541h2.f4610a / 5.0f);
                this.f4517a.m2724b(this.f4521e.f4610a * 1.5f);
            }
            TXCGPULoopupFilter tXCGPULoopupFilter = this.f4518b;
            if (tXCGPULoopupFilter == null) {
                return;
            }
            tXCGPULoopupFilter.m2659a(this.f4521e.f4610a / 5.0f);
        }
    }

    /* renamed from: a */
    public int m1340a(int i) {
        TXCVideoEffect.C3541h c3541h = this.f4521e;
        if (c3541h == null || c3541h.f4610a <= 0.0f) {
            return i;
        }
        TXCGPUloopupInvertFilter tXCGPUloopupInvertFilter = this.f4517a;
        if (tXCGPUloopupInvertFilter != null) {
            i = tXCGPUloopupInvertFilter.mo2294a(i);
        }
        TXCGPULoopupFilter tXCGPULoopupFilter = this.f4518b;
        return tXCGPULoopupFilter != null ? tXCGPULoopupFilter.mo2294a(i) : i;
    }

    /* renamed from: b */
    public void m1337b() {
        m1341a();
    }
}
