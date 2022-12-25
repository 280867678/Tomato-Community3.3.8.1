package master.flame.danmaku.p144ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.util.LinkedList;
import java.util.Locale;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.DrawHelper;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.controller.IDanmakuViewController;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.util.SystemClock;

/* renamed from: master.flame.danmaku.ui.widget.DanmakuSurfaceView */
/* loaded from: classes4.dex */
public class DanmakuSurfaceView extends SurfaceView implements IDanmakuView, IDanmakuViewController, SurfaceHolder.Callback {
    private DrawHandler handler;
    private boolean isSurfaceCreated;
    private LinkedList<Long> mDrawTimes;
    private IDanmakuView.OnDanmakuClickListener mOnDanmakuClickListener;
    private boolean mShowFps;
    private SurfaceHolder mSurfaceHolder;
    private DanmakuTouchHelper mTouchHelper;
    private float mXOff;
    private float mYOff;
    private boolean mEnableDanmakuDrwaingCache = true;
    private boolean mDanmakuVisible = true;

    public View getView() {
        return this;
    }

    @Override // android.view.View, master.flame.danmaku.controller.IDanmakuViewController
    public boolean isHardwareAccelerated() {
        return false;
    }

    public void setDrawingThreadType(int i) {
    }

    public DanmakuSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setZOrderMediaOverlay(true);
        setWillNotCacheDrawing(true);
        setDrawingCacheEnabled(false);
        setWillNotDraw(true);
        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this);
        this.mSurfaceHolder.setFormat(-2);
        DrawHelper.useDrawColorToClearCanvas(true, true);
        this.mTouchHelper = DanmakuTouchHelper.instance(this);
    }

    public DanmakuSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public DanmakuSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public IDanmakus getCurrentVisibleDanmakus() {
        DrawHandler drawHandler = this.handler;
        if (drawHandler != null) {
            return drawHandler.getCurrentVisibleDanmakus();
        }
        return null;
    }

    public void setCallback(DrawHandler.Callback callback) {
        DrawHandler drawHandler = this.handler;
        if (drawHandler != null) {
            drawHandler.setCallback(callback);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.isSurfaceCreated = true;
        Canvas lockCanvas = surfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            DrawHelper.clearCanvas(lockCanvas);
            surfaceHolder.unlockCanvasAndPost(lockCanvas);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        DrawHandler drawHandler = this.handler;
        if (drawHandler != null) {
            drawHandler.notifyDispSizeChanged(i2, i3);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.isSurfaceCreated = false;
    }

    public DanmakuContext getConfig() {
        DrawHandler drawHandler = this.handler;
        if (drawHandler == null) {
            return null;
        }
        return drawHandler.getConfig();
    }

    private float fps() {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mDrawTimes.addLast(Long.valueOf(uptimeMillis));
        Long peekFirst = this.mDrawTimes.peekFirst();
        if (peekFirst == null) {
            return 0.0f;
        }
        float longValue = (float) (uptimeMillis - peekFirst.longValue());
        if (this.mDrawTimes.size() > 50) {
            this.mDrawTimes.removeFirst();
        }
        if (longValue <= 0.0f) {
            return 0.0f;
        }
        return (this.mDrawTimes.size() * 1000) / longValue;
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public long drawDanmakus() {
        if (!this.isSurfaceCreated) {
            return 0L;
        }
        if (!isShown()) {
            return -1L;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        Canvas lockCanvas = this.mSurfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            DrawHandler drawHandler = this.handler;
            if (drawHandler != null) {
                IRenderer.RenderingState draw = drawHandler.draw(lockCanvas);
                if (this.mShowFps) {
                    if (this.mDrawTimes == null) {
                        this.mDrawTimes = new LinkedList<>();
                    }
                    SystemClock.uptimeMillis();
                    DrawHelper.drawFPS(lockCanvas, String.format(Locale.getDefault(), "fps %.2f,time:%d s,cache:%d,miss:%d", Float.valueOf(fps()), Long.valueOf(getCurrentTime() / 1000), Long.valueOf(draw.cacheHitCount), Long.valueOf(draw.cacheMissCount)));
                }
            }
            if (this.isSurfaceCreated) {
                this.mSurfaceHolder.unlockCanvasAndPost(lockCanvas);
            }
        }
        return SystemClock.uptimeMillis() - uptimeMillis;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = this.mTouchHelper.onTouchEvent(motionEvent);
        return !onTouchEvent ? super.onTouchEvent(motionEvent) : onTouchEvent;
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public boolean isDanmakuDrawingCacheEnabled() {
        return this.mEnableDanmakuDrwaingCache;
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public boolean isViewReady() {
        return this.isSurfaceCreated;
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public int getViewWidth() {
        return super.getWidth();
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public int getViewHeight() {
        return super.getHeight();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void setOnDanmakuClickListener(IDanmakuView.OnDanmakuClickListener onDanmakuClickListener) {
        this.mOnDanmakuClickListener = onDanmakuClickListener;
    }

    public void setOnDanmakuClickListener(IDanmakuView.OnDanmakuClickListener onDanmakuClickListener, float f, float f2) {
        this.mOnDanmakuClickListener = onDanmakuClickListener;
        this.mXOff = f;
        this.mYOff = f2;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public IDanmakuView.OnDanmakuClickListener getOnDanmakuClickListener() {
        return this.mOnDanmakuClickListener;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public float getXOff() {
        return this.mXOff;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public float getYOff() {
        return this.mYOff;
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public void clear() {
        Canvas lockCanvas;
        if (isViewReady() && (lockCanvas = this.mSurfaceHolder.lockCanvas()) != null) {
            DrawHelper.clearCanvas(lockCanvas);
            this.mSurfaceHolder.unlockCanvasAndPost(lockCanvas);
        }
    }

    @Override // android.view.View
    public boolean isShown() {
        return this.mDanmakuVisible && super.isShown();
    }

    public long getCurrentTime() {
        DrawHandler drawHandler = this.handler;
        if (drawHandler != null) {
            return drawHandler.getCurrentTime();
        }
        return 0L;
    }
}
