package com.tencent.rtmp.p134ui;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.tencent.liteav.TXICaptureSource;
import com.tencent.liteav.renderer.TXCFocusIndicatorView;
import com.tencent.liteav.renderer.TXCGLSurfaceView;

/* renamed from: com.tencent.rtmp.ui.TXCloudVideoView */
/* loaded from: classes3.dex */
public class TXCloudVideoView extends FrameLayout implements View.OnTouchListener {
    private static final int FOCUS_AREA_SIZE_DP = 70;
    private static final String TAG = "TXCloudVideoView";
    private TXICaptureSource mCapture;
    private int mCurrentScale;
    private boolean mFocus;
    private int mFocusAreaSize;
    private TXCFocusIndicatorView mFocusIndicatorView;
    private TXCGLSurfaceView mGLSurfaceView;
    private TXLogView mLogView;
    private ScaleGestureDetector mScaleGestureDetector;
    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener;
    private RunnableC3738a mTouchFocusRunnable;
    private TextureView mVideoView;
    private boolean mZoom;

    private int clamp(int i, int i2, int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i;
    }

    public void adjustVideoSize() {
    }

    public void enableHardwareDecode(boolean z) {
    }

    public void onDestroy() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void refreshLastFrame() {
    }

    public void setGLOnTouchListener(View.OnTouchListener onTouchListener) {
    }

    public void setMirror(boolean z) {
    }

    public void setRenderMode(int i) {
    }

    public void setRenderRotation(int i) {
    }

    public void setStreamUrl(String str) {
    }

    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
    }

    public void setUseBeautyView(boolean z) {
    }

    public TXCloudVideoView(Context context) {
        this(context, null);
    }

    public TXCloudVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFocusAreaSize = 0;
        this.mFocus = false;
        this.mZoom = false;
        this.mCurrentScale = 1;
        this.mScaleGestureDetector = null;
        this.mScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.tencent.rtmp.ui.TXCloudVideoView.3
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                int mo1032e = TXCloudVideoView.this.mCapture != null ? TXCloudVideoView.this.mCapture.mo1032e() : 0;
                if (mo1032e > 0) {
                    float scaleFactor = scaleGestureDetector.getScaleFactor();
                    if (scaleFactor > 1.0f) {
                        scaleFactor = ((0.2f / mo1032e) * (mo1032e - TXCloudVideoView.this.mCurrentScale)) + 1.0f;
                        if (scaleFactor <= 1.1f) {
                            scaleFactor = 1.1f;
                        }
                    } else if (scaleFactor < 1.0f) {
                        scaleFactor = 1.0f - ((0.2f / mo1032e) * TXCloudVideoView.this.mCurrentScale);
                        if (scaleFactor >= 0.9f) {
                            scaleFactor = 0.9f;
                        }
                    }
                    int round = Math.round(TXCloudVideoView.this.mCurrentScale * scaleFactor);
                    if (round == TXCloudVideoView.this.mCurrentScale) {
                        if (scaleFactor > 1.0f) {
                            round++;
                        } else if (scaleFactor < 1.0f) {
                            round--;
                        }
                    }
                    if (round < mo1032e) {
                        mo1032e = round;
                    }
                    if (mo1032e <= 1) {
                        mo1032e = 1;
                    }
                    if (scaleFactor > 1.0f) {
                        if (mo1032e < TXCloudVideoView.this.mCurrentScale) {
                            mo1032e = TXCloudVideoView.this.mCurrentScale;
                        }
                    } else if (scaleFactor < 1.0f && mo1032e > TXCloudVideoView.this.mCurrentScale) {
                        mo1032e = TXCloudVideoView.this.mCurrentScale;
                    }
                    TXCloudVideoView.this.mCurrentScale = mo1032e;
                    if (TXCloudVideoView.this.mCapture != null) {
                        TXCloudVideoView.this.mCapture.mo1048a(TXCloudVideoView.this.mCurrentScale);
                    }
                }
                return false;
            }
        };
        this.mTouchFocusRunnable = new RunnableC3738a();
        this.mLogView = new TXLogView(context);
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this.mScaleGestureListener);
    }

    public void addVideoView(TXCGLSurfaceView tXCGLSurfaceView) {
        TXCGLSurfaceView tXCGLSurfaceView2 = this.mGLSurfaceView;
        if (tXCGLSurfaceView2 != null) {
            removeView(tXCGLSurfaceView2);
        }
        this.mGLSurfaceView = tXCGLSurfaceView;
        addView(this.mGLSurfaceView);
        removeView(this.mLogView);
        addView(this.mLogView);
    }

    public void addVideoView(TextureView textureView) {
        TextureView textureView2 = this.mVideoView;
        if (textureView2 != null) {
            removeView(textureView2);
        }
        this.mVideoView = textureView;
        addView(this.mVideoView);
        removeView(this.mLogView);
        addView(this.mLogView);
    }

    public void removeVideoView() {
        TextureView textureView = this.mVideoView;
        if (textureView != null) {
            removeView(textureView);
            this.mVideoView = null;
        }
        TXCGLSurfaceView tXCGLSurfaceView = this.mGLSurfaceView;
        if (tXCGLSurfaceView != null) {
            removeView(tXCGLSurfaceView);
            this.mGLSurfaceView = null;
        }
    }

    public void removeFocusIndicatorView() {
        TXCFocusIndicatorView tXCFocusIndicatorView = this.mFocusIndicatorView;
        if (tXCFocusIndicatorView != null) {
            removeView(tXCFocusIndicatorView);
            this.mFocusIndicatorView = null;
        }
    }

    public TextureView getVideoView() {
        return this.mVideoView;
    }

    public TXCGLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }

    public TextureView getHWVideoView() {
        return this.mVideoView;
    }

    public void clearLastFrame(boolean z) {
        if (z) {
            setVisibility(8);
        }
    }

    public void onTouchFocus(int i, int i2) {
        if (this.mGLSurfaceView == null) {
            return;
        }
        if (i < 0 || i2 < 0) {
            TXCFocusIndicatorView tXCFocusIndicatorView = this.mFocusIndicatorView;
            if (tXCFocusIndicatorView == null) {
                return;
            }
            tXCFocusIndicatorView.setVisibility(8);
            return;
        }
        if (this.mFocusIndicatorView == null) {
            this.mFocusIndicatorView = new TXCFocusIndicatorView(getContext());
            this.mFocusIndicatorView.setVisibility(0);
            addView(this.mFocusIndicatorView);
        }
        Rect touchRect = getTouchRect(i, i2, this.mGLSurfaceView.getWidth(), this.mGLSurfaceView.getHeight(), 1.0f);
        TXCFocusIndicatorView tXCFocusIndicatorView2 = this.mFocusIndicatorView;
        int i3 = touchRect.left;
        tXCFocusIndicatorView2.show(i3, touchRect.top, touchRect.right - i3);
    }

    private Rect getTouchRect(int i, int i2, int i3, int i4, float f) {
        TXCGLSurfaceView tXCGLSurfaceView;
        if (this.mFocusAreaSize == 0 && (tXCGLSurfaceView = this.mGLSurfaceView) != null) {
            this.mFocusAreaSize = (int) ((tXCGLSurfaceView.getResources().getDisplayMetrics().density * 70.0f) + 0.5f);
        }
        int intValue = Float.valueOf(this.mFocusAreaSize * f).intValue();
        int i5 = intValue / 2;
        int clamp = clamp(i - i5, 0, i3 - intValue);
        int clamp2 = clamp(i2 - i5, 0, i4 - intValue);
        return new Rect(clamp, clamp2, clamp + intValue, intValue + clamp2);
    }

    public void disableLog(boolean z) {
        this.mLogView.m276b(z);
    }

    public void showLog(boolean z) {
        this.mLogView.m277a(z);
    }

    public void clearLog() {
        this.mLogView.m283a();
    }

    public void setLogText(Bundle bundle, Bundle bundle2, int i) {
        this.mLogView.m279a(bundle, bundle2, i);
    }

    public void setLogMargin(int i, int i2, int i3, int i4) {
        FrameLayout.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = this.mLogView.getLayoutParams();
        if (layoutParams2 != null) {
            layoutParams = (FrameLayout.LayoutParams) layoutParams2;
        } else {
            layoutParams = new FrameLayout.LayoutParams(-1, -1);
        }
        layoutParams.leftMargin = TXLogView.m281a(getContext(), i);
        layoutParams.rightMargin = TXLogView.m281a(getContext(), i2);
        layoutParams.topMargin = TXLogView.m281a(getContext(), i3);
        layoutParams.bottomMargin = TXLogView.m281a(getContext(), i4);
        this.mLogView.setLayoutParams(layoutParams);
    }

    public void start(boolean z, boolean z2, TXICaptureSource tXICaptureSource) {
        this.mFocus = z;
        this.mZoom = z2;
        if (this.mFocus || this.mZoom) {
            setOnTouchListener(this);
            this.mCapture = tXICaptureSource;
        }
        post(new Runnable() { // from class: com.tencent.rtmp.ui.TXCloudVideoView.1
            @Override // java.lang.Runnable
            public void run() {
                if (TXCloudVideoView.this.mGLSurfaceView != null) {
                    TXCloudVideoView.this.mGLSurfaceView.setVisibility(0);
                }
            }
        });
    }

    public void stop(boolean z) {
        if (this.mFocus || this.mZoom) {
            setOnTouchListener(null);
        }
        this.mCapture = null;
        if (z) {
            post(new Runnable() { // from class: com.tencent.rtmp.ui.TXCloudVideoView.2
                @Override // java.lang.Runnable
                public void run() {
                    if (TXCloudVideoView.this.mGLSurfaceView != null) {
                        TXCloudVideoView.this.mGLSurfaceView.setVisibility(8);
                    }
                }
            });
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() == 1 && motionEvent.getAction() == 0) {
            this.mTouchFocusRunnable.m284a(view);
            this.mTouchFocusRunnable.m285a(motionEvent);
            postDelayed(this.mTouchFocusRunnable, 100L);
        } else if (motionEvent.getPointerCount() > 1 && motionEvent.getAction() == 2) {
            removeCallbacks(this.mTouchFocusRunnable);
            onTouchFocus(-1, -1);
            ScaleGestureDetector scaleGestureDetector = this.mScaleGestureDetector;
            if (scaleGestureDetector != null && this.mZoom) {
                scaleGestureDetector.onTouchEvent(motionEvent);
            }
        }
        if (this.mZoom && motionEvent.getAction() == 0) {
            performClick();
        }
        return this.mZoom;
    }

    /* renamed from: com.tencent.rtmp.ui.TXCloudVideoView$a */
    /* loaded from: classes3.dex */
    private class RunnableC3738a implements Runnable {

        /* renamed from: b */
        private View f5749b;

        /* renamed from: c */
        private MotionEvent f5750c;

        private RunnableC3738a() {
        }

        /* renamed from: a */
        public void m284a(View view) {
            this.f5749b = view;
        }

        /* renamed from: a */
        public void m285a(MotionEvent motionEvent) {
            this.f5750c = motionEvent;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TXCloudVideoView.this.mCapture != null && TXCloudVideoView.this.mFocus) {
                TXCloudVideoView.this.mCapture.mo1049a(this.f5750c.getX() / this.f5749b.getWidth(), this.f5750c.getY() / this.f5749b.getHeight());
            }
            if (TXCloudVideoView.this.mFocus) {
                TXCloudVideoView.this.onTouchFocus((int) this.f5750c.getX(), (int) this.f5750c.getY());
            }
        }
    }
}
