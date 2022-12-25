package master.flame.danmaku.p144ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.LinkedList;
import java.util.Locale;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.DrawHelper;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.controller.IDanmakuViewController;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.util.SystemClock;

/* renamed from: master.flame.danmaku.ui.widget.DanmakuView */
/* loaded from: classes4.dex */
public class DanmakuView extends View implements IDanmakuView, IDanmakuViewController {
    protected volatile DrawHandler handler;
    private boolean isSurfaceCreated;
    private DrawHandler.Callback mCallback;
    protected boolean mClearFlag;
    private LinkedList<Long> mDrawTimes;
    private HandlerThread mHandlerThread;
    private IDanmakuView.OnDanmakuClickListener mOnDanmakuClickListener;
    private boolean mShowFps;
    private DanmakuTouchHelper mTouchHelper;
    private long mUiThreadId;
    private float mXOff;
    private float mYOff;
    private boolean mEnableDanmakuDrwaingCache = true;
    private boolean mDanmakuVisible = true;
    protected int mDrawingThreadType = 0;
    private Object mDrawMonitor = new Object();
    private boolean mDrawFinished = false;
    protected boolean mRequestRender = false;
    private int mResumeTryCount = 0;
    private Runnable mResumeRunnable = new Runnable() { // from class: master.flame.danmaku.ui.widget.DanmakuView.1
        @Override // java.lang.Runnable
        public void run() {
            DrawHandler drawHandler = DanmakuView.this.handler;
            if (drawHandler == null) {
                return;
            }
            DanmakuView.access$008(DanmakuView.this);
            if (DanmakuView.this.mResumeTryCount <= 4 && !DanmakuView.super.isShown()) {
                drawHandler.postDelayed(this, DanmakuView.this.mResumeTryCount * 100);
            } else {
                drawHandler.resume();
            }
        }
    };

    public View getView() {
        return this;
    }

    static /* synthetic */ int access$008(DanmakuView danmakuView) {
        int i = danmakuView.mResumeTryCount;
        danmakuView.mResumeTryCount = i + 1;
        return i;
    }

    public DanmakuView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.mUiThreadId = Thread.currentThread().getId();
        setBackgroundColor(0);
        setDrawingCacheBackgroundColor(0);
        DrawHelper.useDrawColorToClearCanvas(true, false);
        this.mTouchHelper = DanmakuTouchHelper.instance(this);
    }

    public DanmakuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public DanmakuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void addDanmaku(BaseDanmaku baseDanmaku) {
        if (this.handler != null) {
            this.handler.addDanmaku(baseDanmaku);
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public IDanmakus getCurrentVisibleDanmakus() {
        if (this.handler != null) {
            return this.handler.getCurrentVisibleDanmakus();
        }
        return null;
    }

    public void setCallback(DrawHandler.Callback callback) {
        this.mCallback = callback;
        if (this.handler != null) {
            this.handler.setCallback(callback);
        }
    }

    public void release() {
        stop();
        LinkedList<Long> linkedList = this.mDrawTimes;
        if (linkedList != null) {
            linkedList.clear();
        }
    }

    public void stop() {
        stopDraw();
    }

    private synchronized void stopDraw() {
        if (this.handler == null) {
            return;
        }
        DrawHandler drawHandler = this.handler;
        this.handler = null;
        unlockCanvasAndPost();
        if (drawHandler != null) {
            drawHandler.quit();
        }
        HandlerThread handlerThread = this.mHandlerThread;
        this.mHandlerThread = null;
        if (handlerThread != null) {
            try {
                handlerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handlerThread.quit();
        }
    }

    protected synchronized Looper getLooper(int i) {
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quit();
            this.mHandlerThread = null;
        }
        if (i == 1) {
            return Looper.getMainLooper();
        }
        int i2 = i != 2 ? i != 3 ? 0 : 19 : -8;
        this.mHandlerThread = new HandlerThread("DFM Handler Thread #" + i2, i2);
        this.mHandlerThread.start();
        return this.mHandlerThread.getLooper();
    }

    private void prepare() {
        if (this.handler == null) {
            this.handler = new DrawHandler(getLooper(this.mDrawingThreadType), this, this.mDanmakuVisible);
        }
    }

    public void prepare(BaseDanmakuParser baseDanmakuParser, DanmakuContext danmakuContext) {
        prepare();
        this.handler.setConfig(danmakuContext);
        this.handler.setParser(baseDanmakuParser);
        this.handler.setCallback(this.mCallback);
        this.handler.prepare();
    }

    public boolean isPrepared() {
        return this.handler != null && this.handler.isPrepared();
    }

    public DanmakuContext getConfig() {
        if (this.handler == null) {
            return null;
        }
        return this.handler.getConfig();
    }

    public void showFPS(boolean z) {
        this.mShowFps = z;
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
        lockCanvas();
        return SystemClock.uptimeMillis() - uptimeMillis;
    }

    @SuppressLint({"NewApi"})
    private void postInvalidateCompat() {
        this.mRequestRender = true;
        if (Build.VERSION.SDK_INT >= 16) {
            postInvalidateOnAnimation();
        } else {
            postInvalidate();
        }
    }

    protected void lockCanvas() {
        if (!this.mDanmakuVisible) {
            return;
        }
        postInvalidateCompat();
        synchronized (this.mDrawMonitor) {
            while (!this.mDrawFinished && this.handler != null) {
                try {
                    this.mDrawMonitor.wait(200L);
                } catch (InterruptedException unused) {
                    if (!this.mDanmakuVisible || this.handler == null || this.handler.isStop()) {
                        break;
                    }
                    Thread.currentThread().interrupt();
                }
            }
            this.mDrawFinished = false;
        }
    }

    private void lockCanvasAndClear() {
        this.mClearFlag = true;
        lockCanvas();
    }

    private void unlockCanvasAndPost() {
        synchronized (this.mDrawMonitor) {
            this.mDrawFinished = true;
            this.mDrawMonitor.notifyAll();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (!this.mDanmakuVisible && !this.mRequestRender) {
            super.onDraw(canvas);
            return;
        }
        if (this.mClearFlag) {
            DrawHelper.clearCanvas(canvas);
            this.mClearFlag = false;
        } else if (this.handler != null) {
            IRenderer.RenderingState draw = this.handler.draw(canvas);
            if (this.mShowFps) {
                if (this.mDrawTimes == null) {
                    this.mDrawTimes = new LinkedList<>();
                }
                DrawHelper.drawFPS(canvas, String.format(Locale.getDefault(), "fps %.2f,time:%d s,cache:%d,miss:%d", Float.valueOf(fps()), Long.valueOf(getCurrentTime() / 1000), Long.valueOf(draw.cacheHitCount), Long.valueOf(draw.cacheMissCount)));
            }
        }
        this.mRequestRender = false;
        unlockCanvasAndPost();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.handler != null) {
            this.handler.notifyDispSizeChanged(i3 - i, i4 - i2);
        }
        this.isSurfaceCreated = true;
    }

    public void pause() {
        if (this.handler != null) {
            this.handler.removeCallbacks(this.mResumeRunnable);
            this.handler.pause();
        }
    }

    public void resume() {
        if (this.handler != null && this.handler.isPrepared()) {
            this.mResumeTryCount = 0;
            this.handler.post(this.mResumeRunnable);
        } else if (this.handler != null) {
        } else {
            restart();
        }
    }

    public boolean isPaused() {
        if (this.handler != null) {
            return this.handler.isStop();
        }
        return false;
    }

    public void restart() {
        stop();
        start();
    }

    public void start() {
        start(0L);
    }

    public void start(long j) {
        DrawHandler drawHandler = this.handler;
        if (drawHandler == null) {
            prepare();
            drawHandler = this.handler;
        } else {
            drawHandler.removeCallbacksAndMessages(null);
        }
        if (drawHandler != null) {
            drawHandler.obtainMessage(1, Long.valueOf(j)).sendToTarget();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = this.mTouchHelper.onTouchEvent(motionEvent);
        return !onTouchEvent ? super.onTouchEvent(motionEvent) : onTouchEvent;
    }

    public void enableDanmakuDrawingCache(boolean z) {
        this.mEnableDanmakuDrwaingCache = z;
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

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public void clear() {
        if (!isViewReady()) {
            return;
        }
        if (!this.mDanmakuVisible || Thread.currentThread().getId() == this.mUiThreadId) {
            this.mClearFlag = true;
            postInvalidateCompat();
            return;
        }
        lockCanvasAndClear();
    }

    @Override // android.view.View
    public boolean isShown() {
        return this.mDanmakuVisible && super.isShown();
    }

    public void setDrawingThreadType(int i) {
        this.mDrawingThreadType = i;
    }

    public long getCurrentTime() {
        if (this.handler != null) {
            return this.handler.getCurrentTime();
        }
        return 0L;
    }

    @Override // android.view.View, master.flame.danmaku.controller.IDanmakuViewController
    @SuppressLint({"NewApi"})
    public boolean isHardwareAccelerated() {
        if (Build.VERSION.SDK_INT >= 11) {
            return super.isHardwareAccelerated();
        }
        return false;
    }

    public void clearDanmakusOnScreen() {
        if (this.handler != null) {
            this.handler.clearDanmakusOnScreen();
        }
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
}
