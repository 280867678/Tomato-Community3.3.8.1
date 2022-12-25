package com.tencent.liteav.txcvodplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.ISurfaceTextureHolder;
import com.tencent.ijk.media.player.ISurfaceTextureHost;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.txcvodplayer.IRenderView;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TargetApi(14)
/* loaded from: classes3.dex */
public class TextureRenderView extends TextureView implements IRenderView {
    private static final String TAG = "TextureRenderView";
    private MeasureHelper mMeasureHelper;
    private TextureView$SurfaceTextureListenerC3654b mSurfaceCallback;

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public View getView() {
        return this;
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public boolean shouldWaitForResize() {
        return false;
    }

    public TextureRenderView(Context context) {
        super(context);
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    @TargetApi(21)
    public TextureRenderView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView(context);
    }

    private void initView(Context context) {
        this.mMeasureHelper = new MeasureHelper(this);
        this.mSurfaceCallback = new TextureView$SurfaceTextureListenerC3654b(this);
        setSurfaceTextureListener(this.mSurfaceCallback);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        try {
            this.mSurfaceCallback.m685a();
            super.onDetachedFromWindow();
            this.mSurfaceCallback.m680b();
        } catch (Exception unused) {
        }
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setVideoSize(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        this.mMeasureHelper.m644a(i, i2);
        requestLayout();
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setVideoSampleAspectRatio(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        this.mMeasureHelper.m641b(i, i2);
        requestLayout();
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setVideoRotation(int i) {
        this.mMeasureHelper.m645a(i);
        setRotation(i);
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setAspectRatio(int i) {
        this.mMeasureHelper.m642b(i);
        requestLayout();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        this.mMeasureHelper.m640c(i, i2);
        setMeasuredDimension(this.mMeasureHelper.m646a(), this.mMeasureHelper.m643b());
    }

    public IRenderView.AbstractC3656b getSurfaceHolder() {
        return new C3653a(this, this.mSurfaceCallback.f5367a, this.mSurfaceCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.txcvodplayer.TextureRenderView$a */
    /* loaded from: classes3.dex */
    public static final class C3653a implements IRenderView.AbstractC3656b {

        /* renamed from: a */
        private TextureRenderView f5363a;

        /* renamed from: b */
        private SurfaceTexture f5364b;

        /* renamed from: c */
        private ISurfaceTextureHost f5365c;

        /* renamed from: d */
        private Surface f5366d;

        public C3653a(@NonNull TextureRenderView textureRenderView, @Nullable SurfaceTexture surfaceTexture, @NonNull ISurfaceTextureHost iSurfaceTextureHost) {
            this.f5363a = textureRenderView;
            this.f5364b = surfaceTexture;
            this.f5365c = iSurfaceTextureHost;
        }

        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3656b
        @TargetApi(16)
        /* renamed from: a */
        public void mo668a(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 16 && (iMediaPlayer instanceof ISurfaceTextureHolder)) {
                ISurfaceTextureHolder iSurfaceTextureHolder = (ISurfaceTextureHolder) iMediaPlayer;
                this.f5363a.mSurfaceCallback.m681a(false);
                if (this.f5363a.getSurfaceTexture() != null) {
                    this.f5364b = this.f5363a.getSurfaceTexture();
                }
                try {
                    SurfaceTexture surfaceTexture = iSurfaceTextureHolder.getSurfaceTexture();
                    if (surfaceTexture != null) {
                        this.f5363a.setSurfaceTexture(surfaceTexture);
                        this.f5363a.mSurfaceCallback.m684a(surfaceTexture);
                    } else {
                        if (this.f5366d != null) {
                            iMediaPlayer.setSurface(this.f5366d);
                        }
                        iSurfaceTextureHolder.setSurfaceTexture(this.f5364b);
                        iSurfaceTextureHolder.setSurfaceTextureHost(this.f5363a.mSurfaceCallback);
                    }
                    this.f5366d = iMediaPlayer.getSurface();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            this.f5366d = m686b();
            iMediaPlayer.setSurface(this.f5366d);
        }

        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3656b
        @NonNull
        /* renamed from: a */
        public IRenderView mo670a() {
            return this.f5363a;
        }

        @Nullable
        /* renamed from: b */
        public Surface m686b() {
            SurfaceTexture surfaceTexture = this.f5364b;
            if (surfaceTexture == null) {
                return null;
            }
            if (this.f5366d == null) {
                this.f5366d = new Surface(surfaceTexture);
            }
            return this.f5366d;
        }
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void addRenderCallback(IRenderView.AbstractC3655a abstractC3655a) {
        this.mSurfaceCallback.m682a(abstractC3655a);
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void removeRenderCallback(IRenderView.AbstractC3655a abstractC3655a) {
        this.mSurfaceCallback.m679b(abstractC3655a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.txcvodplayer.TextureRenderView$b */
    /* loaded from: classes3.dex */
    public static final class TextureView$SurfaceTextureListenerC3654b implements TextureView.SurfaceTextureListener, ISurfaceTextureHost {

        /* renamed from: a */
        private SurfaceTexture f5367a;

        /* renamed from: b */
        private boolean f5368b;

        /* renamed from: c */
        private int f5369c;

        /* renamed from: d */
        private int f5370d;

        /* renamed from: h */
        private WeakReference<TextureRenderView> f5374h;

        /* renamed from: e */
        private boolean f5371e = true;

        /* renamed from: f */
        private boolean f5372f = false;

        /* renamed from: g */
        private boolean f5373g = false;

        /* renamed from: i */
        private Map<IRenderView.AbstractC3655a, Object> f5375i = new ConcurrentHashMap();

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        public TextureView$SurfaceTextureListenerC3654b(@NonNull TextureRenderView textureRenderView) {
            this.f5374h = new WeakReference<>(textureRenderView);
        }

        /* renamed from: a */
        public void m681a(boolean z) {
            this.f5371e = z;
        }

        /* renamed from: a */
        public void m684a(SurfaceTexture surfaceTexture) {
            this.f5367a = surfaceTexture;
        }

        /* renamed from: a */
        public void m682a(@NonNull IRenderView.AbstractC3655a abstractC3655a) {
            C3653a c3653a;
            this.f5375i.put(abstractC3655a, abstractC3655a);
            if (this.f5367a != null) {
                c3653a = new C3653a(this.f5374h.get(), this.f5367a, this);
                abstractC3655a.mo676a(c3653a, this.f5369c, this.f5370d);
            } else {
                c3653a = null;
            }
            if (this.f5368b) {
                if (c3653a == null) {
                    c3653a = new C3653a(this.f5374h.get(), this.f5367a, this);
                }
                abstractC3655a.mo675a(c3653a, 0, this.f5369c, this.f5370d);
            }
        }

        /* renamed from: b */
        public void m679b(@NonNull IRenderView.AbstractC3655a abstractC3655a) {
            this.f5375i.remove(abstractC3655a);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            this.f5367a = surfaceTexture;
            this.f5368b = false;
            this.f5369c = 0;
            this.f5370d = 0;
            C3653a c3653a = new C3653a(this.f5374h.get(), surfaceTexture, this);
            for (IRenderView.AbstractC3655a abstractC3655a : this.f5375i.keySet()) {
                abstractC3655a.mo676a(c3653a, 0, 0);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            this.f5367a = surfaceTexture;
            this.f5368b = true;
            this.f5369c = i;
            this.f5370d = i2;
            C3653a c3653a = new C3653a(this.f5374h.get(), surfaceTexture, this);
            for (IRenderView.AbstractC3655a abstractC3655a : this.f5375i.keySet()) {
                abstractC3655a.mo675a(c3653a, 0, i, i2);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            this.f5367a = surfaceTexture;
            this.f5368b = false;
            this.f5369c = 0;
            this.f5370d = 0;
            C3653a c3653a = new C3653a(this.f5374h.get(), surfaceTexture, this);
            for (IRenderView.AbstractC3655a abstractC3655a : this.f5375i.keySet()) {
                abstractC3655a.mo677a(c3653a);
            }
            TXCLog.m2915d(TextureRenderView.TAG, "onSurfaceTextureDestroyed: destroy: " + this.f5371e);
            return this.f5371e;
        }

        @Override // com.tencent.ijk.media.player.ISurfaceTextureHost
        public void releaseSurfaceTexture(SurfaceTexture surfaceTexture) {
            if (surfaceTexture == null) {
                TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: null");
            } else if (this.f5373g) {
                if (surfaceTexture != this.f5367a) {
                    TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: didDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (!this.f5371e) {
                    TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: didDetachFromWindow(): release detached SurfaceTexture");
                    surfaceTexture.release();
                } else {
                    TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: didDetachFromWindow(): already released by TextureView");
                }
            } else if (this.f5372f) {
                if (surfaceTexture != this.f5367a) {
                    TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: willDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (!this.f5371e) {
                    TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: willDetachFromWindow(): re-attach SurfaceTexture to TextureView");
                    m681a(true);
                } else {
                    TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: willDetachFromWindow(): will released by TextureView");
                }
            } else if (surfaceTexture != this.f5367a) {
                TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: alive: release different SurfaceTexture");
                surfaceTexture.release();
            } else if (!this.f5371e) {
                TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: alive: re-attach SurfaceTexture to TextureView");
                m681a(true);
            } else {
                TXCLog.m2915d(TextureRenderView.TAG, "releaseSurfaceTexture: alive: will released by TextureView");
            }
        }

        /* renamed from: a */
        public void m685a() {
            TXCLog.m2915d(TextureRenderView.TAG, "willDetachFromWindow()");
            this.f5372f = true;
        }

        /* renamed from: b */
        public void m680b() {
            TXCLog.m2915d(TextureRenderView.TAG, "didDetachFromWindow()");
            this.f5373g = true;
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(TextureRenderView.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(TextureRenderView.class.getName());
    }
}
