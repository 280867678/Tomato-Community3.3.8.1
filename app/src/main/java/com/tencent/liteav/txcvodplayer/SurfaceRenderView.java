package com.tencent.liteav.txcvodplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.ISurfaceTextureHolder;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.txcvodplayer.IRenderView;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class SurfaceRenderView extends SurfaceView implements IRenderView {

    /* renamed from: a */
    private MeasureHelper f5276a;

    /* renamed from: b */
    private SurfaceHolder$CallbackC3635b f5277b;

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public View getView() {
        return this;
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public boolean shouldWaitForResize() {
        return true;
    }

    public SurfaceRenderView(Context context) {
        super(context);
        m755a(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m755a(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m755a(context);
    }

    /* renamed from: a */
    private void m755a(Context context) {
        this.f5276a = new MeasureHelper(this);
        this.f5277b = new SurfaceHolder$CallbackC3635b(this);
        getHolder().addCallback(this.f5277b);
        getHolder().setType(0);
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setVideoSize(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        this.f5276a.m644a(i, i2);
        getHolder().setFixedSize(i, i2);
        requestLayout();
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setVideoSampleAspectRatio(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        this.f5276a.m641b(i, i2);
        requestLayout();
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setVideoRotation(int i) {
        TXCLog.m2914e("", "SurfaceView doesn't support rotation (" + i + ")!\n");
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void setAspectRatio(int i) {
        this.f5276a.m642b(i);
        requestLayout();
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        this.f5276a.m640c(i, i2);
        setMeasuredDimension(this.f5276a.m646a(), this.f5276a.m643b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.txcvodplayer.SurfaceRenderView$a */
    /* loaded from: classes3.dex */
    public static final class C3634a implements IRenderView.AbstractC3656b {

        /* renamed from: a */
        private SurfaceRenderView f5278a;

        /* renamed from: b */
        private SurfaceHolder f5279b;

        public C3634a(@NonNull SurfaceRenderView surfaceRenderView, @Nullable SurfaceHolder surfaceHolder) {
            this.f5278a = surfaceRenderView;
            this.f5279b = surfaceHolder;
        }

        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3656b
        /* renamed from: a */
        public void mo668a(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer != null) {
                if (Build.VERSION.SDK_INT >= 16 && (iMediaPlayer instanceof ISurfaceTextureHolder)) {
                    ((ISurfaceTextureHolder) iMediaPlayer).setSurfaceTexture(null);
                }
                iMediaPlayer.setDisplay(this.f5279b);
            }
        }

        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3656b
        @NonNull
        /* renamed from: a */
        public IRenderView mo670a() {
            return this.f5278a;
        }
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void addRenderCallback(IRenderView.AbstractC3655a abstractC3655a) {
        this.f5277b.m754a(abstractC3655a);
    }

    @Override // com.tencent.liteav.txcvodplayer.IRenderView
    public void removeRenderCallback(IRenderView.AbstractC3655a abstractC3655a) {
        this.f5277b.m753b(abstractC3655a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.txcvodplayer.SurfaceRenderView$b */
    /* loaded from: classes3.dex */
    public static final class SurfaceHolder$CallbackC3635b implements SurfaceHolder.Callback {

        /* renamed from: a */
        private SurfaceHolder f5280a;

        /* renamed from: b */
        private boolean f5281b;

        /* renamed from: c */
        private int f5282c;

        /* renamed from: d */
        private int f5283d;

        /* renamed from: e */
        private int f5284e;

        /* renamed from: f */
        private WeakReference<SurfaceRenderView> f5285f;

        /* renamed from: g */
        private Map<IRenderView.AbstractC3655a, Object> f5286g = new ConcurrentHashMap();

        public SurfaceHolder$CallbackC3635b(@NonNull SurfaceRenderView surfaceRenderView) {
            this.f5285f = new WeakReference<>(surfaceRenderView);
        }

        /* renamed from: a */
        public void m754a(@NonNull IRenderView.AbstractC3655a abstractC3655a) {
            C3634a c3634a;
            this.f5286g.put(abstractC3655a, abstractC3655a);
            if (this.f5280a != null) {
                c3634a = new C3634a(this.f5285f.get(), this.f5280a);
                abstractC3655a.mo676a(c3634a, this.f5283d, this.f5284e);
            } else {
                c3634a = null;
            }
            if (this.f5281b) {
                if (c3634a == null) {
                    c3634a = new C3634a(this.f5285f.get(), this.f5280a);
                }
                abstractC3655a.mo675a(c3634a, this.f5282c, this.f5283d, this.f5284e);
            }
        }

        /* renamed from: b */
        public void m753b(@NonNull IRenderView.AbstractC3655a abstractC3655a) {
            this.f5286g.remove(abstractC3655a);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            this.f5280a = surfaceHolder;
            this.f5281b = false;
            this.f5282c = 0;
            this.f5283d = 0;
            this.f5284e = 0;
            C3634a c3634a = new C3634a(this.f5285f.get(), this.f5280a);
            for (IRenderView.AbstractC3655a abstractC3655a : this.f5286g.keySet()) {
                abstractC3655a.mo676a(c3634a, 0, 0);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            this.f5280a = null;
            this.f5281b = false;
            this.f5282c = 0;
            this.f5283d = 0;
            this.f5284e = 0;
            C3634a c3634a = new C3634a(this.f5285f.get(), this.f5280a);
            for (IRenderView.AbstractC3655a abstractC3655a : this.f5286g.keySet()) {
                abstractC3655a.mo677a(c3634a);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            this.f5280a = surfaceHolder;
            this.f5281b = true;
            this.f5282c = i;
            this.f5283d = i2;
            this.f5284e = i3;
            C3634a c3634a = new C3634a(this.f5285f.get(), this.f5280a);
            for (IRenderView.AbstractC3655a abstractC3655a : this.f5286g.keySet()) {
                abstractC3655a.mo675a(c3634a, i, i2, i3);
            }
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SurfaceRenderView.class.getName());
    }

    @Override // android.view.View
    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (Build.VERSION.SDK_INT >= 14) {
            accessibilityNodeInfo.setClassName(SurfaceRenderView.class.getName());
        }
    }
}
