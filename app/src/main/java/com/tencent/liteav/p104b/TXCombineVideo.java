package com.tencent.liteav.p104b;

import android.content.Context;
import android.opengl.EGLContext;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.p104b.TXCombine;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.VideoSourceListConfig;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoJoiner;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* renamed from: com.tencent.liteav.b.f */
/* loaded from: classes3.dex */
public class TXCombineVideo {

    /* renamed from: b */
    private Context f2252b;

    /* renamed from: c */
    private TXCVideoJoiner.AbstractC3527a f2253c;

    /* renamed from: d */
    private TXCombineEncAndMuxer f2254d;

    /* renamed from: e */
    private TXCombineDecAndRender f2255e;

    /* renamed from: f */
    private TXCombine.AbstractC3314c f2256f;

    /* renamed from: g */
    private TXCombine.AbstractC3313b f2257g;

    /* renamed from: h */
    private List<String> f2258h;

    /* renamed from: i */
    private Handler f2259i = new Handler(Looper.getMainLooper());

    /* renamed from: a */
    private VideoSourceListConfig f2251a = VideoSourceListConfig.m1480a();

    static {
        TXCSystemUtil.m2873e();
    }

    public TXCombineVideo(Context context) {
        this.f2252b = context;
        this.f2255e = new TXCombineDecAndRender(this.f2252b);
        this.f2254d = new TXCombineEncAndMuxer(this.f2252b);
        m3240c();
    }

    /* renamed from: c */
    private void m3240c() {
        this.f2256f = new TXCombine.AbstractC3314c() { // from class: com.tencent.liteav.b.f.1
            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3314c
            /* renamed from: a */
            public void mo3236a(EGLContext eGLContext) {
                TXCombineVideo.this.f2254d.m3276a(TXCombineVideo.this.f2255e.m3304b());
                TXCombineVideo.this.f2254d.m3264b(TXCombineVideo.this.f2255e.m3316a());
                TXCombineVideo.this.f2254d.m3272a(eGLContext);
            }

            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3314c
            /* renamed from: a */
            public void mo3237a(int i, int i2, int i3, Frame frame) {
                TXCombineVideo.this.f2254d.m3274a(i, i2, i3, frame);
            }

            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3314c
            /* renamed from: a */
            public void mo3235a(Frame frame) {
                TXCombineVideo.this.f2254d.m3267a(frame);
            }

            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3314c
            /* renamed from: b */
            public void mo3234b(Frame frame) {
                TXCombineVideo.this.f2254d.m3261b(frame);
            }

            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3314c
            /* renamed from: c */
            public void mo3233c(Frame frame) {
                TXCombineVideo.this.f2254d.m3258c(frame);
            }
        };
        this.f2257g = new TXCombine.AbstractC3313b() { // from class: com.tencent.liteav.b.f.2
            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3313b
            /* renamed from: a */
            public void mo3232a(final float f) {
                TXCombineVideo.this.f2259i.post(new Runnable() { // from class: com.tencent.liteav.b.f.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TXCombineVideo.this.f2253c != null) {
                            TXCombineVideo.this.f2253c.mo258a(f);
                        }
                    }
                });
            }

            @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3313b
            /* renamed from: a */
            public void mo3231a(final int i, final String str) {
                TXCLog.m2914e("TXCombineVideo", "===onEncodedComplete===");
                TXCombineVideo.this.f2259i.post(new Runnable() { // from class: com.tencent.liteav.b.f.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TXCombineVideo.this.f2253c != null) {
                            TXCVideoEditConstants.C3514d c3514d = new TXCVideoEditConstants.C3514d();
                            int i2 = i;
                            c3514d.f4372a = i2;
                            c3514d.f4373b = str;
                            if (i2 == 0) {
                                TXCombineVideo.this.f2253c.mo258a(1.0f);
                            }
                            TXCombineVideo.this.f2253c.mo257a(c3514d);
                        }
                    }
                });
            }
        };
        this.f2255e.m3314a(this.f2256f);
        this.f2254d.m3271a(this.f2257g);
    }

    /* renamed from: a */
    public void m3246a(TXCVideoJoiner.AbstractC3527a abstractC3527a) {
        this.f2253c = abstractC3527a;
    }

    /* renamed from: a */
    public void m3244a(List<String> list) {
        this.f2258h = list;
    }

    /* renamed from: a */
    public void m3245a(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.f2254d.m3266a(str);
    }

    /* renamed from: a */
    public void m3248a() {
        this.f2255e.m3306a(this.f2258h);
        this.f2254d.m3273a(this.f2251a.m1466n());
        this.f2255e.m3301c();
    }

    /* renamed from: b */
    public void m3242b() {
        this.f2255e.m3299d();
        this.f2254d.m3277a();
    }

    /* renamed from: a */
    public void m3243a(List<TXCVideoEditConstants.C3511a> list, int i, int i2) {
        int i3 = ((i + 15) / 16) * 16;
        int i4 = ((i2 + 15) / 16) * 16;
        this.f2254d.m3275a(i3, i4);
        this.f2255e.m3305a(list, i3, i4);
    }
}
