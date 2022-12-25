package com.tencent.liteav.beauty;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCGPUOESTextureFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.p113a.p114a.EglCore;
import com.tencent.liteav.beauty.p113a.p114a.WindowSurface;
import com.tencent.liteav.beauty.p115b.SemaphoreHandle;
import com.tencent.liteav.beauty.p115b.TXCBeautyInterFace;
import com.tencent.liteav.beauty.p115b.TXCGPUBeautyFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUGaussianBlurFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUGreenScreenFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUI4202RGBAFilter;
import com.tencent.liteav.beauty.p115b.TXCGPULookupFilterGroup;
import com.tencent.liteav.beauty.p115b.TXCGPUPituInterface;
import com.tencent.liteav.beauty.p115b.TXCGPURGBA2I420Filter;
import com.tencent.liteav.beauty.p115b.TXCGPUWatermarkFilter;
import com.tencent.liteav.beauty.p115b.p116a.TXCBeauty2Filter;
import com.tencent.liteav.beauty.p115b.p117b.TXCBeauty3Filter;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.tencent.liteav.beauty.b */
/* loaded from: classes3.dex */
public class TXCFilterDrawer extends HandlerThread {

    /* renamed from: D */
    private float[] f2790D;

    /* renamed from: O */
    private Bitmap f2801O;

    /* renamed from: P */
    private Bitmap f2802P;

    /* renamed from: Q */
    private float f2803Q;

    /* renamed from: R */
    private float f2804R;

    /* renamed from: S */
    private float f2805S;

    /* renamed from: T */
    private TXCGPULookupFilterGroup f2806T;

    /* renamed from: ad */
    private boolean f2825ad;

    /* renamed from: ag */
    private Handler f2828ag;

    /* renamed from: ah */
    private HandlerC3381a f2829ah;

    /* renamed from: m */
    private Context f2859m;

    /* renamed from: g */
    private int f2853g = 0;

    /* renamed from: h */
    private int f2854h = 0;

    /* renamed from: i */
    private int f2855i = 0;

    /* renamed from: j */
    private int f2856j = 0;

    /* renamed from: k */
    private int f2857k = 0;

    /* renamed from: l */
    private int f2858l = 0;

    /* renamed from: n */
    private boolean f2860n = true;

    /* renamed from: o */
    private TXCVideoPreprocessor.C3397d f2861o = null;

    /* renamed from: p */
    private int f2862p = -1;

    /* renamed from: q */
    private int f2863q = -1;

    /* renamed from: r */
    private int f2864r = -1;

    /* renamed from: s */
    private int f2865s = -1;

    /* renamed from: t */
    private int f2866t = -1;

    /* renamed from: u */
    private int f2867u = -1;

    /* renamed from: v */
    private int f2868v = -1;

    /* renamed from: w */
    private int f2869w = -1;

    /* renamed from: x */
    private float f2870x = 1.0f;

    /* renamed from: y */
    private int f2871y = -1;

    /* renamed from: z */
    private int f2872z = -1;

    /* renamed from: A */
    private int f2787A = 0;

    /* renamed from: B */
    private int f2788B = 0;

    /* renamed from: C */
    private boolean f2789C = false;

    /* renamed from: E */
    private int f2791E = 0;

    /* renamed from: F */
    private int f2792F = 0;

    /* renamed from: G */
    private CropRect f2793G = null;

    /* renamed from: H */
    private Bitmap f2794H = null;

    /* renamed from: I */
    private TXCGPUI4202RGBAFilter f2795I = null;

    /* renamed from: J */
    private TXCGPURGBA2I420Filter f2796J = null;

    /* renamed from: K */
    private TXCBeautyInterFace f2797K = null;

    /* renamed from: L */
    private TXCBeauty2Filter f2798L = null;

    /* renamed from: M */
    private TXCBeauty3Filter f2799M = null;

    /* renamed from: N */
    private TXCGPUBeautyFilter f2800N = null;

    /* renamed from: U */
    private TXCGPUPituInterface f2807U = null;

    /* renamed from: V */
    private TXCGPUWatermarkFilter f2808V = null;

    /* renamed from: W */
    private TXCGPUGreenScreenFilter f2809W = null;

    /* renamed from: X */
    private TXCGPUGaussianBlurFilter f2810X = null;

    /* renamed from: Y */
    private TXCGPUFilter f2811Y = null;

    /* renamed from: Z */
    private TXCGPUFilter f2812Z = null;

    /* renamed from: aa */
    private TXCGPUOESTextureFilter f2822aa = null;

    /* renamed from: ab */
    private TXCGPUFilter f2823ab = null;

    /* renamed from: ac */
    private final Queue<Runnable> f2824ac = new LinkedList();

    /* renamed from: a */
    boolean f2813a = false;

    /* renamed from: ae */
    private Object f2826ae = new Object();

    /* renamed from: af */
    private Object f2827af = new Object();

    /* renamed from: ai */
    private float f2830ai = 0.5f;

    /* renamed from: aj */
    private int f2831aj = 0;

    /* renamed from: ak */
    private int f2832ak = 0;

    /* renamed from: al */
    private int f2833al = 0;

    /* renamed from: am */
    private int f2834am = 0;

    /* renamed from: an */
    private int f2835an = 0;

    /* renamed from: ao */
    private boolean f2836ao = false;

    /* renamed from: ap */
    private WindowSurface f2837ap = null;

    /* renamed from: aq */
    private EglCore f2838aq = null;

    /* renamed from: ar */
    private Bitmap f2839ar = null;

    /* renamed from: as */
    private List<TXCVideoPreprocessor.C3397d> f2840as = null;

    /* renamed from: at */
    private long f2841at = 0;

    /* renamed from: au */
    private int f2842au = 0;

    /* renamed from: av */
    private final int f2843av = 100;

    /* renamed from: aw */
    private final float f2844aw = 1000.0f;

    /* renamed from: ax */
    private byte[] f2845ax = null;

    /* renamed from: ay */
    private int[] f2846ay = null;

    /* renamed from: az */
    private boolean f2847az = false;

    /* renamed from: aA */
    private byte[] f2814aA = null;

    /* renamed from: b */
    protected int[] f2848b = null;

    /* renamed from: c */
    protected int[] f2849c = null;

    /* renamed from: aB */
    private int f2815aB = -1;

    /* renamed from: aC */
    private int f2816aC = 0;

    /* renamed from: aD */
    private int f2817aD = 1;

    /* renamed from: aE */
    private int f2818aE = this.f2815aB;

    /* renamed from: aF */
    private TXIVideoPreprocessorListener f2819aF = null;

    /* renamed from: aG */
    private WeakReference<TXINotifyListener> f2820aG = new WeakReference<>(null);

    /* renamed from: d */
    SemaphoreHandle f2850d = new SemaphoreHandle();

    /* renamed from: e */
    SemaphoreHandle f2851e = new SemaphoreHandle();

    /* renamed from: f */
    SemaphoreHandle f2852f = new SemaphoreHandle();

    /* renamed from: aH */
    private TXCOpenGlUtils.AbstractC3357b f2821aH = new TXCOpenGlUtils.AbstractC3357b() { // from class: com.tencent.liteav.beauty.b.11
    };

    /* renamed from: a */
    public void m2814a(String str) {
    }

    /* renamed from: a */
    public void m2813a(String str, boolean z) {
    }

    /* renamed from: a */
    public void m2810a(boolean z) {
    }

    /* renamed from: g */
    public void m2783g(int i) {
    }

    /* renamed from: h */
    public void m2781h(int i) {
    }

    /* renamed from: i */
    public void m2779i(int i) {
    }

    /* renamed from: j */
    public void m2777j(int i) {
    }

    /* renamed from: k */
    public void m2775k(int i) {
    }

    /* renamed from: l */
    public void m2773l(int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TXCFilterDrawer(Context context, boolean z) {
        super("TXCFilterDrawer");
        this.f2859m = null;
        this.f2859m = context;
        this.f2828ag = new Handler(this.f2859m.getMainLooper());
        this.f2825ad = z;
    }

    /* renamed from: a */
    public synchronized boolean m2817a(TXCVideoPreprocessor.C3395b c3395b) {
        boolean z;
        z = true;
        if (!c3395b.f3239j) {
            if (this.f2829ah == null) {
                start();
                this.f2829ah = new HandlerC3381a(getLooper(), this.f2859m);
            }
            this.f2829ah.obtainMessage(0, c3395b).sendToTarget();
            this.f2829ah.m2759b();
        } else {
            z = m2791c(c3395b);
        }
        return z;
    }

    /* renamed from: a */
    public int m2842a(int i, int i2) {
        int willAddWatermark;
        m2811a(this.f2824ac);
        boolean z = this.f2870x != 1.0f;
        GLES20.glViewport(0, 0, this.f2868v, this.f2869w);
        TXCGPUFilter tXCGPUFilter = this.f2812Z;
        if (tXCGPUFilter != null) {
            if (4 == i2) {
                tXCGPUFilter.mo3010a(this.f2790D);
            }
            i = this.f2812Z.mo2294a(i);
        }
        if (this.f2797K != null && (this.f2832ak > 0 || this.f2833al > 0 || this.f2835an > 0)) {
            i = this.f2797K.mo2294a(i);
        }
        TXCGPULookupFilterGroup tXCGPULookupFilterGroup = this.f2806T;
        if (tXCGPULookupFilterGroup != null) {
            i = tXCGPULookupFilterGroup.mo2294a(i);
        }
        GLES20.glViewport(0, 0, this.f2866t, this.f2867u);
        TXCGPUGreenScreenFilter tXCGPUGreenScreenFilter = this.f2809W;
        if (tXCGPUGreenScreenFilter != null) {
            i = tXCGPUGreenScreenFilter.m2683a(i);
            z = false;
        }
        TXCGPUGaussianBlurFilter tXCGPUGaussianBlurFilter = this.f2810X;
        if (tXCGPUGaussianBlurFilter != null) {
            i = tXCGPUGaussianBlurFilter.mo2294a(i);
            z = false;
        }
        if (z) {
            m2793c(this.f2866t, this.f2867u);
            if (this.f2823ab != null) {
                GLES20.glViewport(0, 0, this.f2866t, this.f2867u);
                i = this.f2823ab.mo2294a(i);
            }
        }
        TXIVideoPreprocessorListener tXIVideoPreprocessorListener = this.f2819aF;
        if (tXIVideoPreprocessorListener != null && (willAddWatermark = tXIVideoPreprocessorListener.willAddWatermark(i, this.f2866t, this.f2867u)) > 0) {
            i = willAddWatermark;
        }
        GLES20.glViewport(0, 0, this.f2866t, this.f2867u);
        TXCGPUWatermarkFilter tXCGPUWatermarkFilter = this.f2808V;
        if (tXCGPUWatermarkFilter != null) {
            i = tXCGPUWatermarkFilter.mo2294a(i);
        }
        if (this.f2811Y != null) {
            GLES20.glViewport(0, 0, this.f2871y, this.f2872z);
            i = this.f2811Y.mo2294a(i);
        }
        m2771m(i);
        return i;
    }

    /* renamed from: a */
    public int m2808a(byte[] bArr, int i) {
        m2809a(bArr);
        if (!this.f2825ad) {
            byte[] bArr2 = (byte[]) bArr.clone();
            this.f2829ah.obtainMessage(2, bArr2).sendToTarget();
            if (!this.f2847az) {
                TXCLog.m2913i("TXCFilterDrawer", "First Frame, clear queue");
                NativeLoad.getInstance();
                NativeLoad.nativeClearQueue();
            }
            this.f2829ah.obtainMessage(3, i, 0).sendToTarget();
            m2807a(bArr2, this.f2847az);
            this.f2847az = true;
            return -1;
        }
        m2796b(bArr);
        return m2769n(i);
    }

    /* renamed from: a */
    public void m2845a(final float f) {
        this.f2830ai = f;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.1
            @Override // java.lang.Runnable
            public void run() {
                if (TXCFilterDrawer.this.f2806T != null) {
                    TXCFilterDrawer.this.f2806T.m2669a(f);
                }
            }
        });
    }

    /* renamed from: a */
    public void m2806a(final float[] fArr) {
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.6
            @Override // java.lang.Runnable
            public void run() {
                TXCFilterDrawer.this.f2790D = fArr;
            }
        });
    }

    /* renamed from: a */
    private void m2835a(CropRect cropRect, int i, int i2, int i3, int i4, boolean z, int i5, int i6) {
        if (this.f2812Z == null) {
            TXCLog.m2913i("TXCFilterDrawer", "Create CropFilter");
            if (4 == i6) {
                this.f2812Z = new TXCGPUFilter("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", true);
            } else {
                this.f2812Z = new TXCGPUFilter();
            }
            if (true == this.f2812Z.mo2653c()) {
                this.f2812Z.mo1353a(true);
            } else {
                TXCLog.m2914e("TXCFilterDrawer", "mInputCropFilter init failed!");
            }
        }
        int i7 = i3;
        this.f2812Z.mo1333a(i7, i4);
        float[] m3033a = this.f2812Z.m3033a(this.f2862p, this.f2863q, null, cropRect, i6);
        int i8 = (720 - i5) % 360;
        int i9 = (i8 == 90 || i8 == 270) ? i4 : i7;
        if (i8 != 90 && i8 != 270) {
            i7 = i4;
        }
        this.f2812Z.m3034a(i, i2, i8, m3033a, i9 / i7, z, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m2839a(int i, int i2, int i3, int i4, int i5, int i6) {
        synchronized (this.f2827af) {
            int i7 = ((i6 - i3) + 360) % 360;
            TXCLog.m2913i("TXCFilterDrawer", "real outputAngle " + i7);
            if (this.f2811Y == null) {
                if (i == i4 && i2 == i5 && i7 == 0) {
                    TXCLog.m2913i("TXCFilterDrawer", "Don't need change output Image, don't create out filter!");
                    return;
                }
                this.f2811Y = new TXCGPUFilter();
                if (true == this.f2811Y.mo2653c()) {
                    this.f2811Y.mo1353a(true);
                } else {
                    TXCLog.m2914e("TXCFilterDrawer", "mOutputZoomFilter init failed!");
                }
            }
            this.f2811Y.mo1333a(i4, i5);
            this.f2811Y.m3031a((720 - i7) % 360, (FloatBuffer) null);
        }
    }

    /* renamed from: a */
    public void m2837a(final Bitmap bitmap, final float f, final float f2, final float f3) {
        if (this.f2861o == null) {
            this.f2861o = new TXCVideoPreprocessor.C3397d();
        }
        Bitmap bitmap2 = this.f2861o.f3256a;
        if (bitmap2 != null && bitmap != null && true == bitmap2.equals(bitmap)) {
            TXCVideoPreprocessor.C3397d c3397d = this.f2861o;
            if (f == c3397d.f3257b && f2 == c3397d.f3258c && f3 == c3397d.f3259d) {
                Log.d("TXCFilterDrawer", "Same Water Mark; don't set again");
                return;
            }
        }
        TXCVideoPreprocessor.C3397d c3397d2 = this.f2861o;
        c3397d2.f3256a = bitmap;
        c3397d2.f3257b = f;
        c3397d2.f3258c = f2;
        c3397d2.f3259d = f3;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.8
            @Override // java.lang.Runnable
            public void run() {
                if (bitmap != null) {
                    ReportDuaManage.m2863a().m2858e();
                }
                if (bitmap == null) {
                    if (TXCFilterDrawer.this.f2808V == null) {
                        return;
                    }
                    TXCFilterDrawer.this.f2808V.mo1351e();
                    TXCFilterDrawer.this.f2808V = null;
                    return;
                }
                if (TXCFilterDrawer.this.f2808V == null) {
                    if (TXCFilterDrawer.this.f2866t <= 0 || TXCFilterDrawer.this.f2867u <= 0) {
                        TXCLog.m2914e("TXCFilterDrawer", "output Width and Height is error!");
                        return;
                    }
                    TXCFilterDrawer.this.f2808V = new TXCGPUWatermarkFilter();
                    TXCFilterDrawer.this.f2808V.mo1353a(true);
                    if (TXCFilterDrawer.this.f2808V.mo2653c()) {
                        TXCFilterDrawer.this.f2808V.mo1333a(TXCFilterDrawer.this.f2866t, TXCFilterDrawer.this.f2867u);
                    } else {
                        TXCLog.m2914e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                        TXCFilterDrawer.this.f2808V.mo1351e();
                        TXCFilterDrawer.this.f2808V = null;
                        return;
                    }
                }
                TXCFilterDrawer.this.f2808V.m2726d(true);
                TXCFilterDrawer.this.f2808V.m2730a(bitmap, f, f2, f3);
            }
        });
    }

    /* renamed from: a */
    public void m2812a(final List<TXCVideoPreprocessor.C3397d> list) {
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.9
            @Override // java.lang.Runnable
            public void run() {
                TXCFilterDrawer.this.f2840as = list;
                List list2 = list;
                if ((list2 == null || list2.size() == 0) && TXCFilterDrawer.this.f2839ar == null && TXCFilterDrawer.this.f2808V != null) {
                    TXCFilterDrawer.this.f2808V.mo1351e();
                    TXCFilterDrawer.this.f2808V = null;
                    return;
                }
                List list3 = list;
                if (list3 == null || list3.size() == 0) {
                    return;
                }
                if (TXCFilterDrawer.this.f2808V == null) {
                    if (TXCFilterDrawer.this.f2866t <= 0 || TXCFilterDrawer.this.f2867u <= 0) {
                        Log.e("TXCFilterDrawer", "output Width and Height is error!");
                        return;
                    }
                    TXCFilterDrawer.this.f2808V = new TXCGPUWatermarkFilter();
                    TXCFilterDrawer.this.f2808V.mo1353a(true);
                    if (TXCFilterDrawer.this.f2808V.mo2653c()) {
                        TXCFilterDrawer.this.f2808V.mo1333a(TXCFilterDrawer.this.f2866t, TXCFilterDrawer.this.f2867u);
                    } else {
                        Log.e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                        TXCFilterDrawer.this.f2808V.mo1351e();
                        TXCFilterDrawer.this.f2808V = null;
                        return;
                    }
                }
                TXCFilterDrawer.this.f2808V.m2726d(true);
                TXCFilterDrawer.this.f2808V.m2728a(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m2816a(TXIVideoPreprocessorListener tXIVideoPreprocessorListener) {
        TXCLog.m2913i("TXCFilterDrawer", "set listener");
        this.f2819aF = tXIVideoPreprocessorListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m2836a(TXINotifyListener tXINotifyListener) {
        TXCLog.m2913i("TXCFilterDrawer", "set notify");
        this.f2820aG = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: m */
    private int m2771m(int i) {
        int i2 = this.f2792F;
        if (i2 == 0) {
            TXIVideoPreprocessorListener tXIVideoPreprocessorListener = this.f2819aF;
            if (tXIVideoPreprocessorListener != null) {
                tXIVideoPreprocessorListener.didProcessFrame(i, this.f2871y, this.f2872z, TXCTimeUtil.getTimeTick());
            }
            return i;
        } else if (1 == i2 || 3 == i2 || 2 == i2) {
            GLES20.glViewport(0, 0, this.f2871y, this.f2872z);
            if (this.f2796J == null) {
                TXCLog.m2914e("TXCFilterDrawer", "mRGBA2I420Filter is null!");
                return i;
            }
            GLES20.glBindFramebuffer(36160, this.f2848b[0]);
            this.f2796J.m3025b(i);
            if (2 == this.f2792F) {
                m2801b(this.f2871y, this.f2872z);
            } else {
                m2801b(this.f2871y, (this.f2872z * 3) / 8);
            }
            GLES20.glBindFramebuffer(36160, 0);
            return i;
        } else {
            TXCLog.m2914e("TXCFilterDrawer", "Don't support format!");
            return -1;
        }
    }

    /* renamed from: b */
    private int m2801b(int i, int i2) {
        if (true == this.f2825ad) {
            if (this.f2819aF != null) {
                NativeLoad.getInstance();
                NativeLoad.nativeGlReadPixs(i, i2, this.f2845ax);
                this.f2819aF.didProcessFrame(this.f2845ax, this.f2871y, this.f2872z, this.f2792F, TXCTimeUtil.getTimeTick());
            } else if (this.f2814aA != null) {
                NativeLoad.getInstance();
                NativeLoad.nativeGlReadPixs(i, i2, this.f2814aA);
            }
        } else if (3 == TXCOpenGlUtils.m3009a()) {
            if (0 == this.f2841at) {
                this.f2841at = TXCTimeUtil.getTimeTick();
            }
            int i3 = this.f2842au + 1;
            this.f2842au = i3;
            if (i3 >= 100) {
                float timeTick = 100.0f / (((float) (TXCTimeUtil.getTimeTick() - this.f2841at)) / 1000.0f);
                TXCLog.m2913i("TXCFilterDrawer", "Real fps " + timeTick);
                this.f2842au = 0;
                this.f2841at = TXCTimeUtil.getTimeTick();
            }
            GLES30.glPixelStorei(3333, 1);
            if (Build.VERSION.SDK_INT >= 18) {
                GLES30.glReadBuffer(1029);
            }
            GLES30.glBindBuffer(35051, this.f2846ay[0]);
            NativeLoad.getInstance();
            ByteBuffer byteBuffer = null;
            NativeLoad.nativeGlReadPixs(i, i2, null);
            if (Build.VERSION.SDK_INT >= 18 && (byteBuffer = (ByteBuffer) GLES30.glMapBufferRange(35051, 0, i * i2 * 4, 1)) == null) {
                TXCLog.m2914e("TXCFilterDrawer", "glMapBufferRange is null");
                return -1;
            }
            NativeLoad.getInstance();
            NativeLoad.nativeGlMapBufferToQueue(i, i2, byteBuffer);
            if (Build.VERSION.SDK_INT >= 18) {
                GLES30.glUnmapBuffer(35051);
            }
            GLES30.glBindBuffer(35051, 0);
        } else {
            NativeLoad.getInstance();
            NativeLoad.nativeGlReadPixsToQueue(i, i2);
        }
        return 0;
    }

    /* renamed from: a */
    public void m2843a(final int i) {
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.10
            @Override // java.lang.Runnable
            public void run() {
                TXCFilterDrawer.this.f2792F = i;
            }
        });
    }

    /* renamed from: a */
    private void m2807a(byte[] bArr, boolean z) {
        if (!z) {
            TXIVideoPreprocessorListener tXIVideoPreprocessorListener = this.f2819aF;
            if (tXIVideoPreprocessorListener != null) {
                tXIVideoPreprocessorListener.didProcessFrame(bArr, this.f2871y, this.f2872z, this.f2792F, TXCTimeUtil.getTimeTick());
                return;
            } else {
                TXCLog.m2913i("TXCFilterDrawer", "First Frame, don't process!");
                return;
            }
        }
        int i = this.f2872z;
        int i2 = (i * 3) / 8;
        if (2 != this.f2792F) {
            i = i2;
        }
        if (this.f2819aF != null) {
            NativeLoad.getInstance();
            if (true == NativeLoad.nativeGlReadPixsFromQueue(this.f2871y, i, this.f2845ax)) {
                this.f2819aF.didProcessFrame(this.f2845ax, this.f2871y, this.f2872z, this.f2792F, TXCTimeUtil.getTimeTick());
                return;
            }
            TXCLog.m2915d("TXCFilterDrawer", "nativeGlReadPixsFromQueue Failed");
            this.f2819aF.didProcessFrame(bArr, this.f2871y, this.f2872z, this.f2792F, TXCTimeUtil.getTimeTick());
            return;
        }
        NativeLoad.getInstance();
        if (NativeLoad.nativeGlReadPixsFromQueue(this.f2871y, i, this.f2814aA)) {
            return;
        }
        TXCLog.m2915d("TXCFilterDrawer", "nativeGlReadPixsFromQueue Failed");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: n */
    public int m2769n(int i) {
        GLES20.glViewport(0, 0, this.f2862p, this.f2863q);
        return m2842a(this.f2795I.m2671r(), i);
    }

    /* renamed from: a */
    public void m2809a(byte[] bArr) {
        this.f2814aA = bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m2796b(byte[] bArr) {
        TXCGPUI4202RGBAFilter tXCGPUI4202RGBAFilter = this.f2795I;
        if (tXCGPUI4202RGBAFilter == null) {
            TXCLog.m2914e("TXCFilterDrawer", "mI4202RGBAFilter is null!");
        } else {
            tXCGPUI4202RGBAFilter.m2672a(bArr);
        }
    }

    /* renamed from: a */
    public void m2846a() {
        if (!this.f2825ad) {
            HandlerC3381a handlerC3381a = this.f2829ah;
            if (handlerC3381a == null) {
                return;
            }
            handlerC3381a.obtainMessage(1).sendToTarget();
            try {
                this.f2850d.b();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        m2804b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m2804b() {
        TXCLog.m2913i("TXCFilterDrawer", "come into releaseInternal");
        this.f2847az = false;
        TXCGPUI4202RGBAFilter tXCGPUI4202RGBAFilter = this.f2795I;
        if (tXCGPUI4202RGBAFilter != null) {
            tXCGPUI4202RGBAFilter.mo1351e();
            this.f2795I = null;
        }
        TXCGPURGBA2I420Filter tXCGPURGBA2I420Filter = this.f2796J;
        if (tXCGPURGBA2I420Filter != null) {
            tXCGPURGBA2I420Filter.mo1351e();
            this.f2796J = null;
        }
        m2795c();
        TXCGPULookupFilterGroup tXCGPULookupFilterGroup = this.f2806T;
        if (tXCGPULookupFilterGroup != null) {
            tXCGPULookupFilterGroup.mo1351e();
            this.f2806T = null;
        }
        TXCGPUPituInterface tXCGPUPituInterface = this.f2807U;
        if (tXCGPUPituInterface != null) {
            tXCGPUPituInterface.m2655a();
            this.f2807U = null;
        }
        TXCGPUOESTextureFilter tXCGPUOESTextureFilter = this.f2822aa;
        if (tXCGPUOESTextureFilter != null) {
            tXCGPUOESTextureFilter.mo1351e();
            this.f2822aa = null;
        }
        TXCGPUFilter tXCGPUFilter = this.f2812Z;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f2812Z = null;
        }
        TXCGPUFilter tXCGPUFilter2 = this.f2811Y;
        if (tXCGPUFilter2 != null) {
            tXCGPUFilter2.mo1351e();
            this.f2811Y = null;
        }
        TXCGPUWatermarkFilter tXCGPUWatermarkFilter = this.f2808V;
        if (tXCGPUWatermarkFilter != null) {
            tXCGPUWatermarkFilter.mo1351e();
            this.f2808V = null;
        }
        TXCGPUGreenScreenFilter tXCGPUGreenScreenFilter = this.f2809W;
        if (tXCGPUGreenScreenFilter != null) {
            tXCGPUGreenScreenFilter.m2684a();
            this.f2809W = null;
        }
        TXCGPUGaussianBlurFilter tXCGPUGaussianBlurFilter = this.f2810X;
        if (tXCGPUGaussianBlurFilter != null) {
            tXCGPUGaussianBlurFilter.mo1351e();
            this.f2810X = null;
        }
        TXCGPUFilter tXCGPUFilter3 = this.f2823ab;
        if (tXCGPUFilter3 != null) {
            tXCGPUFilter3.mo1351e();
            this.f2823ab = null;
        }
        int[] iArr = this.f2848b;
        if (iArr != null) {
            GLES20.glDeleteFramebuffers(1, iArr, 0);
            this.f2848b = null;
        }
        int[] iArr2 = this.f2849c;
        if (iArr2 != null) {
            GLES20.glDeleteTextures(1, iArr2, 0);
            this.f2849c = null;
        }
        this.f2861o = null;
        TXCLog.m2913i("TXCFilterDrawer", "come out releaseInternal");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public boolean m2791c(TXCVideoPreprocessor.C3395b c3395b) {
        TXCLog.m2913i("TXCFilterDrawer", "come into initInternal");
        m2804b();
        this.f2825ad = c3395b.f3239j;
        this.f2862p = c3395b.f3233d;
        this.f2863q = c3395b.f3234e;
        this.f2793G = c3395b.f3242m;
        int i = c3395b.f3236g;
        this.f2864r = i;
        int i2 = c3395b.f3235f;
        this.f2865s = i2;
        this.f2787A = c3395b.f3237h;
        this.f2789C = c3395b.f3238i;
        this.f2871y = c3395b.f3231b;
        this.f2872z = c3395b.f3232c;
        this.f2788B = c3395b.f3230a;
        this.f2866t = i;
        this.f2867u = i2;
        int i3 = this.f2787A;
        if (i3 == 90 || i3 == 270) {
            this.f2866t = c3395b.f3235f;
            this.f2867u = c3395b.f3236g;
        }
        this.f2792F = c3395b.f3241l;
        this.f2791E = c3395b.f3240k;
        this.f2845ax = new byte[this.f2871y * this.f2872z * 4];
        TXCLog.m2913i("TXCFilterDrawer", "processWidth mPituScaleRatio is " + this.f2870x);
        if (this.f2870x != 1.0f) {
            int i4 = this.f2866t;
            int i5 = this.f2867u;
            if (i4 >= i5) {
                i4 = i5;
            }
            if (i4 > 368) {
                this.f2870x = 432.0f / i4;
            }
            if (this.f2870x > 1.0f) {
                this.f2870x = 1.0f;
            }
        }
        float f = this.f2870x;
        this.f2868v = (int) (this.f2866t * f);
        this.f2869w = (int) (this.f2867u * f);
        m2840a(this.f2868v, this.f2869w, this.f2831aj);
        TXCVideoPreprocessor.C3397d c3397d = this.f2861o;
        if (c3397d != null && c3397d.f3256a != null && this.f2808V == null) {
            TXCLog.m2913i("TXCFilterDrawer", "reset water mark!");
            TXCVideoPreprocessor.C3397d c3397d2 = this.f2861o;
            m2837a(c3397d2.f3256a, c3397d2.f3257b, c3397d2.f3258c, c3397d2.f3259d);
        }
        if ((this.f2801O != null || this.f2802P != null) && this.f2806T == null) {
            m2841a(this.f2868v, this.f2869w, this.f2803Q, this.f2801O, this.f2804R, this.f2802P, this.f2805S);
        }
        m2835a(this.f2793G, this.f2864r, this.f2865s, this.f2868v, this.f2869w, this.f2789C, this.f2787A, this.f2791E);
        m2839a(this.f2866t, this.f2867u, this.f2787A, this.f2871y, this.f2872z, this.f2788B);
        int[] iArr = this.f2848b;
        if (iArr == null) {
            this.f2848b = new int[1];
        } else {
            GLES20.glDeleteFramebuffers(1, iArr, 0);
        }
        int[] iArr2 = this.f2849c;
        if (iArr2 == null) {
            this.f2849c = new int[1];
        } else {
            GLES20.glDeleteTextures(1, iArr2, 0);
        }
        m2805a(this.f2848b, this.f2849c, this.f2871y, this.f2872z);
        if (3 == TXCOpenGlUtils.m3009a()) {
            if (this.f2846ay == null) {
                this.f2846ay = new int[1];
            } else {
                TXCLog.m2913i("TXCFilterDrawer", "m_pbo0 is not null, delete Buffers, and recreate");
                GLES30.glDeleteBuffers(1, this.f2846ay, 0);
            }
            TXCLog.m2913i("TXCFilterDrawer", "opengl es 3.0, use PBO");
            TXCOpenGlUtils.m3004a(this.f2864r, this.f2865s, this.f2846ay);
        }
        TXCLog.m2913i("TXCFilterDrawer", "come out initInternal");
        return true;
    }

    /* renamed from: b */
    public boolean m2797b(TXCVideoPreprocessor.C3395b c3395b) {
        if (!this.f2825ad) {
            HandlerC3381a handlerC3381a = this.f2829ah;
            if (handlerC3381a == null) {
                TXCLog.m2914e("TXCFilterDrawer", "mThreadHandler is null!");
                return false;
            }
            handlerC3381a.obtainMessage(5, 0, 0, c3395b).sendToTarget();
            return true;
        }
        m2788d(c3395b);
        return true;
    }

    /* renamed from: a */
    private void m2805a(int[] iArr, int[] iArr2, int i, int i2) {
        GLES20.glGenFramebuffers(1, iArr, 0);
        iArr2[0] = TXCOpenGlUtils.m3005a(i, i2, 6408, 6408, iArr2);
        GLES20.glBindFramebuffer(36160, iArr[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, iArr2[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public boolean m2788d(TXCVideoPreprocessor.C3395b c3395b) {
        int i = c3395b.f3240k;
        if ((1 == i || 3 == i || 2 == i) && this.f2795I == null) {
            this.f2795I = new TXCGPUI4202RGBAFilter(c3395b.f3240k);
            this.f2795I.mo1353a(true);
            if (!this.f2795I.mo2653c()) {
                TXCLog.m2914e("TXCFilterDrawer", "mI4202RGBAFilter init failed!!, break init");
                return false;
            }
            this.f2795I.mo1333a(c3395b.f3233d, c3395b.f3234e);
        }
        int i2 = c3395b.f3241l;
        if ((1 == i2 || 3 == i2 || 2 == i2) && this.f2796J == null) {
            this.f2796J = new TXCGPURGBA2I420Filter(c3395b.f3241l);
            if (!this.f2796J.mo2653c()) {
                TXCLog.m2914e("TXCFilterDrawer", "mRGBA2I420Filter init failed!!, break init");
                return false;
            }
            this.f2796J.mo1333a(c3395b.f3231b, c3395b.f3232c);
        }
        return true;
    }

    /* renamed from: b */
    public void m2802b(final int i) {
        this.f2832ak = i;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.12
            @Override // java.lang.Runnable
            public void run() {
                if (i > 0) {
                    ReportDuaManage.m2863a().m2861b();
                }
                if (TXCFilterDrawer.this.f2797K == null || i < 0) {
                    return;
                }
                TXCFilterDrawer.this.f2797K.mo2710c(i);
            }
        });
    }

    /* renamed from: c */
    public void m2794c(final int i) {
        if (this.f2831aj == i || i > 2 || i < 0) {
            return;
        }
        this.f2831aj = i;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.13
            @Override // java.lang.Runnable
            public void run() {
                TXCFilterDrawer tXCFilterDrawer = TXCFilterDrawer.this;
                tXCFilterDrawer.m2840a(tXCFilterDrawer.f2868v, TXCFilterDrawer.this.f2869w, i);
            }
        });
    }

    /* renamed from: d */
    public void m2790d(final int i) {
        this.f2833al = i;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.14
            @Override // java.lang.Runnable
            public void run() {
                if (i > 0) {
                    ReportDuaManage.m2863a().m2860c();
                }
                if (TXCFilterDrawer.this.f2797K == null || i < 0) {
                    return;
                }
                TXCFilterDrawer.this.f2797K.mo2708d(i);
            }
        });
    }

    /* renamed from: e */
    public void m2787e(final int i) {
        this.f2834am = i;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.2
            @Override // java.lang.Runnable
            public void run() {
                if (i > 0) {
                    ReportDuaManage.m2863a().m2860c();
                }
                if (TXCFilterDrawer.this.f2797K == null || i < 0) {
                    return;
                }
                TXCFilterDrawer.this.f2797K.mo2706f(i);
            }
        });
    }

    /* renamed from: f */
    public void m2785f(final int i) {
        this.f2835an = i;
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.3
            @Override // java.lang.Runnable
            public void run() {
                if (i > 0) {
                    ReportDuaManage.m2863a().m2860c();
                }
                if (TXCFilterDrawer.this.f2797K == null || i < 0) {
                    return;
                }
                TXCFilterDrawer.this.f2797K.mo2707e(i);
            }
        });
    }

    /* renamed from: a */
    public void m2838a(Bitmap bitmap) {
        m2844a(1.0f, bitmap, this.f2830ai, null, 0.0f);
    }

    /* renamed from: a */
    public void m2844a(final float f, final Bitmap bitmap, final float f2, final Bitmap bitmap2, final float f3) {
        if (this.f2801O != bitmap || this.f2802P != bitmap2) {
            this.f2801O = bitmap;
            this.f2802P = bitmap2;
            this.f2803Q = f;
            this.f2804R = f2;
            this.f2805S = f3;
            m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.4
                @Override // java.lang.Runnable
                public void run() {
                    if (TXCFilterDrawer.this.f2806T != null) {
                        ReportDuaManage.m2863a().m2859d();
                    }
                    if (TXCFilterDrawer.this.f2801O != null || TXCFilterDrawer.this.f2802P != null) {
                        if (TXCFilterDrawer.this.f2806T != null) {
                            TXCFilterDrawer.this.f2806T.m2667a(f, bitmap, f2, bitmap2, f3);
                            return;
                        }
                        TXCFilterDrawer tXCFilterDrawer = TXCFilterDrawer.this;
                        tXCFilterDrawer.m2841a(tXCFilterDrawer.f2868v, TXCFilterDrawer.this.f2869w, TXCFilterDrawer.this.f2803Q, TXCFilterDrawer.this.f2801O, TXCFilterDrawer.this.f2804R, TXCFilterDrawer.this.f2802P, TXCFilterDrawer.this.f2805S);
                    } else if (TXCFilterDrawer.this.f2806T == null) {
                    } else {
                        TXCFilterDrawer.this.f2806T.mo1351e();
                        TXCFilterDrawer.this.f2806T = null;
                    }
                }
            });
        } else if (this.f2806T == null) {
        } else {
            if (this.f2803Q == f && this.f2804R == f2 && this.f2805S == f3) {
                return;
            }
            this.f2803Q = f;
            this.f2804R = f2;
            this.f2805S = f3;
            m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.5
                @Override // java.lang.Runnable
                public void run() {
                    TXCFilterDrawer.this.f2806T.m2668a(f, f2, f3);
                }
            });
        }
    }

    /* renamed from: b */
    public void m2803b(final float f) {
        m2815a(new Runnable() { // from class: com.tencent.liteav.beauty.b.7
            @Override // java.lang.Runnable
            public void run() {
                if (f <= 0.0f) {
                    if (TXCFilterDrawer.this.f2810X != null) {
                        TXCFilterDrawer.this.f2810X.mo1351e();
                        TXCFilterDrawer.this.f2810X = null;
                        return;
                    }
                } else if (TXCFilterDrawer.this.f2810X == null) {
                    TXCFilterDrawer.this.f2810X = new TXCGPUGaussianBlurFilter();
                    TXCFilterDrawer.this.f2810X.mo1353a(true);
                    if (TXCFilterDrawer.this.f2810X.mo2653c()) {
                        TXCFilterDrawer.this.f2810X.mo1333a(TXCFilterDrawer.this.f2866t, TXCFilterDrawer.this.f2867u);
                    } else {
                        TXCLog.m2914e("TXCFilterDrawer", "Gaussian Filter init failed!");
                        return;
                    }
                }
                if (TXCFilterDrawer.this.f2810X != null) {
                    TXCFilterDrawer.this.f2810X.m2687a(f / 4.0f);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m2840a(int i, int i2, int i3) {
        TXCLog.m2913i("TXCFilterDrawer", "create Beauty Filter!");
        if (i3 == 0) {
            if (this.f2798L == null) {
                this.f2798L = new TXCBeauty2Filter();
            }
            this.f2797K = this.f2798L;
            Log.i("TXCFilterDrawer", "0 BeautyFilter");
        } else if (1 == i3) {
            if (this.f2799M == null) {
                this.f2799M = new TXCBeauty3Filter();
            }
            this.f2797K = this.f2799M;
            Log.i("TXCFilterDrawer", "1 BeautyFilter");
        } else if (2 == i3) {
            if (this.f2800N == null) {
                this.f2800N = new TXCGPUBeautyFilter();
            }
            this.f2797K = this.f2800N;
            Log.i("TXCFilterDrawer", "2 BeautyFilter");
        }
        TXCBeautyInterFace tXCBeautyInterFace = this.f2797K;
        if (tXCBeautyInterFace == null) {
            TXCLog.m2914e("TXCFilterDrawer", "mBeautyFilter set error!");
            return;
        }
        tXCBeautyInterFace.mo1353a(true);
        if (true == this.f2797K.mo2709c(i, i2)) {
            int i4 = this.f2832ak;
            if (i4 > 0) {
                this.f2797K.mo2710c(i4);
            }
            int i5 = this.f2833al;
            if (i5 > 0) {
                this.f2797K.mo2708d(i5);
            }
            int i6 = this.f2835an;
            if (i6 > 0) {
                this.f2797K.mo2707e(i6);
            }
            int i7 = this.f2834am;
            if (i7 <= 0) {
                return;
            }
            this.f2797K.mo2706f(i7);
            return;
        }
        TXCLog.m2914e("TXCFilterDrawer", "mBeautyFilter init failed!");
    }

    /* renamed from: c */
    private void m2795c() {
        TXCBeauty2Filter tXCBeauty2Filter = this.f2798L;
        if (tXCBeauty2Filter != null) {
            tXCBeauty2Filter.mo1351e();
            this.f2798L = null;
        }
        TXCBeauty3Filter tXCBeauty3Filter = this.f2799M;
        if (tXCBeauty3Filter != null) {
            tXCBeauty3Filter.mo1351e();
            this.f2799M = null;
        }
        TXCGPUBeautyFilter tXCGPUBeautyFilter = this.f2800N;
        if (tXCGPUBeautyFilter != null) {
            tXCGPUBeautyFilter.mo1351e();
            this.f2800N = null;
        }
        this.f2797K = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m2841a(int i, int i2, float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        if (this.f2806T == null) {
            TXCLog.m2913i("TXCFilterDrawer", "createComLooKupFilter");
            this.f2806T = new TXCGPULookupFilterGroup(f, bitmap, f2, bitmap2, f3);
            if (true == this.f2806T.mo2653c()) {
                this.f2806T.mo1353a(true);
                this.f2806T.mo1333a(i, i2);
                return;
            }
            TXCLog.m2914e("TXCFilterDrawer", "mLookupFilterGroup init failed!");
        }
    }

    /* renamed from: c */
    private void m2793c(int i, int i2) {
        if (this.f2823ab == null) {
            TXCLog.m2913i("TXCFilterDrawer", "createRecoverScaleFilter");
            this.f2823ab = new TXCGPUFilter();
            if (true == this.f2823ab.mo2653c()) {
                this.f2823ab.mo1353a(true);
            } else {
                TXCLog.m2914e("TXCFilterDrawer", "mRecoverScaleFilter init failed!");
            }
        }
        TXCGPUFilter tXCGPUFilter = this.f2823ab;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1333a(i, i2);
        }
    }

    /* renamed from: a */
    private void m2815a(Runnable runnable) {
        synchronized (this.f2824ac) {
            this.f2824ac.add(runnable);
        }
    }

    /* renamed from: a */
    private void m2811a(Queue<Runnable> queue) {
        while (true) {
            Runnable runnable = null;
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    runnable = queue.poll();
                }
            }
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCFilterDrawer.java */
    /* renamed from: com.tencent.liteav.beauty.b$a */
    /* loaded from: classes3.dex */
    public class HandlerC3381a extends Handler {

        /* renamed from: b */
        private String f2910b = "EGLDrawThreadHandler";

        HandlerC3381a(Looper looper, Context context) {
            super(looper);
        }

        /* renamed from: a */
        private void m2760a(Object obj) {
            TXCLog.m2913i(this.f2910b, "come into InitEGL");
            TXCVideoPreprocessor.C3395b c3395b = (TXCVideoPreprocessor.C3395b) obj;
            m2761a();
            TXCFilterDrawer.this.f2838aq = new EglCore();
            TXCFilterDrawer tXCFilterDrawer = TXCFilterDrawer.this;
            tXCFilterDrawer.f2837ap = new WindowSurface(tXCFilterDrawer.f2838aq, c3395b.f3236g, c3395b.f3235f, false);
            TXCFilterDrawer.this.f2837ap.m2848b();
            if (!TXCFilterDrawer.this.m2791c(c3395b)) {
                TXCLog.m2914e(this.f2910b, "initInternal failed!");
            } else {
                TXCLog.m2913i(this.f2910b, "come out InitEGL");
            }
        }

        /* renamed from: a */
        public void m2761a() {
            TXCLog.m2913i(this.f2910b, "come into releaseEGL");
            if (TXCFilterDrawer.this.f2846ay != null && TXCFilterDrawer.this.f2846ay[0] > 0) {
                GLES20.glDeleteBuffers(1, TXCFilterDrawer.this.f2846ay, 0);
                TXCFilterDrawer.this.f2846ay = null;
            }
            TXCFilterDrawer.this.m2804b();
            if (TXCFilterDrawer.this.f2837ap != null) {
                TXCFilterDrawer.this.f2837ap.m2847c();
                TXCFilterDrawer.this.f2837ap = null;
            }
            if (TXCFilterDrawer.this.f2838aq != null) {
                TXCFilterDrawer.this.f2838aq.m2856a();
                TXCFilterDrawer.this.f2838aq = null;
            }
            TXCFilterDrawer.this.f2836ao = false;
            NativeLoad.getInstance();
            NativeLoad.nativeDeleteYuv2Yuv();
            TXCLog.m2913i(this.f2910b, "come out releaseEGL");
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x009c  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(Message message) {
            boolean z;
            super.handleMessage(message);
            int i = message.what;
            if (i == 0) {
                m2760a(message.obj);
                TXCFilterDrawer.this.f2836ao = true;
            } else {
                if (i == 1) {
                    m2761a();
                    TXCFilterDrawer.this.f2850d.a();
                } else if (i == 2) {
                    TXCFilterDrawer.this.m2796b((byte[]) message.obj);
                } else if (i == 3) {
                    TXCFilterDrawer.this.m2769n(message.arg1);
                } else if (i == 4) {
                    TXCFilterDrawer.this.f2830ai = (float) (message.arg1 / 100.0d);
                    if (TXCFilterDrawer.this.f2806T != null) {
                        TXCFilterDrawer.this.f2806T.m2669a(TXCFilterDrawer.this.f2830ai);
                    }
                } else if (i == 5) {
                    TXCFilterDrawer.this.m2788d((TXCVideoPreprocessor.C3395b) message.obj);
                } else if (i == 7) {
                    TXCFilterDrawer tXCFilterDrawer = TXCFilterDrawer.this;
                    tXCFilterDrawer.m2839a(tXCFilterDrawer.f2866t, TXCFilterDrawer.this.f2867u, TXCFilterDrawer.this.f2787A, message.arg1, message.arg2, ((Integer) message.obj).intValue());
                    TXCFilterDrawer.this.f2852f.a();
                }
                z = false;
                synchronized (this) {
                    if (true == z) {
                        notify();
                    }
                }
                return;
            }
            z = true;
            synchronized (this) {
            }
        }

        /* renamed from: b */
        void m2759b() {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
