package master.flame.danmaku.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import java.util.LinkedList;
import master.flame.danmaku.controller.IDrawTask;
import master.flame.danmaku.danmaku.model.AbsDanmakuSync;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.util.SystemClock;
import tv.cjump.jni.DeviceUtils;

/* loaded from: classes4.dex */
public class DrawHandler extends Handler {
    public IDrawTask drawTask;
    private Callback mCallback;
    private DanmakuContext mContext;
    private IDanmakuViewController mDanmakuView;
    private boolean mDanmakusVisible;
    private long mDesireSeekingTime;
    private AbsDisplayer mDisp;
    private FrameCallback mFrameCallback;
    private boolean mInSeekingAction;
    private boolean mInSyncAction;
    private boolean mInWaitingState;
    private long mLastDeltaTime;
    private boolean mNonBlockModeEnable;
    private BaseDanmakuParser mParser;
    private boolean mReady;
    private long mRemainingTime;
    private UpdateThread mThread;
    private long mTimeBase;
    private boolean mUpdateInSeparateThread;
    private long pausedPosition = 0;
    private boolean quitFlag = true;
    private DanmakuTimer timer = new DanmakuTimer();
    private final IRenderer.RenderingState mRenderingState = new IRenderer.RenderingState();
    private LinkedList<Long> mDrawTimes = new LinkedList<>();
    private long mCordonTime = 30;
    private long mCordonTime2 = 60;
    private long mFrameUpdateRate = 16;
    private boolean mIdleSleep = true ^ DeviceUtils.isProblemBoxDevice();

    /* loaded from: classes4.dex */
    public interface Callback {
        void danmakuShown(BaseDanmaku baseDanmaku);

        void drawingFinished();

        void prepared();

        void updateTimer(DanmakuTimer danmakuTimer);
    }

    public DrawHandler(Looper looper, IDanmakuViewController iDanmakuViewController, boolean z) {
        super(looper);
        this.mDanmakusVisible = true;
        bindView(iDanmakuViewController);
        if (z) {
            showDanmakus(null);
        } else {
            hideDanmakus(false);
        }
        this.mDanmakusVisible = z;
    }

    private void bindView(IDanmakuViewController iDanmakuViewController) {
        this.mDanmakuView = iDanmakuViewController;
    }

    public void setIdleSleep(boolean z) {
        this.mIdleSleep = z;
    }

    public void enableNonBlockMode(boolean z) {
        this.mNonBlockModeEnable = z;
    }

    public void setConfig(DanmakuContext danmakuContext) {
        this.mContext = danmakuContext;
    }

    public void setParser(BaseDanmakuParser baseDanmakuParser) {
        this.mParser = baseDanmakuParser;
        DanmakuTimer timer = baseDanmakuParser.getTimer();
        if (timer != null) {
            this.timer = timer;
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void quit() {
        this.quitFlag = true;
        sendEmptyMessage(6);
    }

    public boolean isStop() {
        return this.quitFlag;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    @Override // android.os.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleMessage(Message message) {
        Long l;
        IDrawTask iDrawTask;
        boolean z;
        IDanmakuViewController iDanmakuViewController;
        IDrawTask iDrawTask2;
        int i = message.what;
        switch (i) {
            case 1:
                l = (Long) message.obj;
                if (l != null) {
                    this.pausedPosition = l.longValue();
                } else {
                    this.pausedPosition = 0L;
                }
                if (i == 4) {
                    this.quitFlag = true;
                    quitUpdateThread();
                    Long l2 = (Long) message.obj;
                    long longValue = l2.longValue();
                    DanmakuTimer danmakuTimer = this.timer;
                    this.mTimeBase -= longValue - danmakuTimer.currMillisecond;
                    danmakuTimer.update(l2.longValue());
                    this.mContext.mGlobalFlagValues.updateMeasureFlag();
                    IDrawTask iDrawTask3 = this.drawTask;
                    if (iDrawTask3 != null) {
                        iDrawTask3.seek(l2.longValue());
                    }
                    this.pausedPosition = l2.longValue();
                }
                removeMessages(7);
                this.quitFlag = false;
                if (this.mReady) {
                    this.mRenderingState.reset();
                    this.mDrawTimes.clear();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long j = this.pausedPosition;
                    this.mTimeBase = uptimeMillis - j;
                    this.timer.update(j);
                    removeMessages(3);
                    sendEmptyMessage(2);
                    this.drawTask.start();
                    notifyRendering();
                    this.mInSeekingAction = false;
                    IDrawTask iDrawTask4 = this.drawTask;
                    if (iDrawTask4 == null) {
                        return;
                    }
                    iDrawTask4.onPlayStateChanged(1);
                    return;
                }
                sendEmptyMessageDelayed(3, 100L);
                return;
            case 2:
                byte b = this.mContext.updateMethod;
                if (b == 0) {
                    updateInChoreographer();
                    return;
                } else if (b == 1) {
                    updateInNewThread();
                    return;
                } else if (b != 2) {
                    return;
                } else {
                    updateInCurrentThread();
                    return;
                }
            case 3:
                removeMessages(7);
                this.quitFlag = false;
                if (this.mReady) {
                }
                break;
            case 4:
                if (i == 4) {
                }
                removeMessages(7);
                this.quitFlag = false;
                if (this.mReady) {
                }
                break;
            case 5:
                this.mTimeBase = SystemClock.uptimeMillis();
                if (this.mParser == null || !this.mDanmakuView.isViewReady()) {
                    sendEmptyMessageDelayed(5, 100L);
                    return;
                } else {
                    prepare(new Runnable() { // from class: master.flame.danmaku.controller.DrawHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DrawHandler.this.pausedPosition = 0L;
                            DrawHandler.this.mReady = true;
                            if (DrawHandler.this.mCallback != null) {
                                DrawHandler.this.mCallback.prepared();
                            }
                        }
                    });
                    return;
                }
            case 6:
                if (i == 6) {
                    removeCallbacksAndMessages(null);
                }
                this.quitFlag = true;
                syncTimerIfNeeded();
                this.pausedPosition = this.timer.currMillisecond;
                if (this.mUpdateInSeparateThread) {
                    notifyRendering();
                    quitUpdateThread();
                }
                if (this.mFrameCallback != null && Build.VERSION.SDK_INT >= 16) {
                    Choreographer.getInstance().removeFrameCallback(this.mFrameCallback);
                }
                if (i != 6) {
                    return;
                }
                IDrawTask iDrawTask5 = this.drawTask;
                if (iDrawTask5 != null) {
                    iDrawTask5.quit();
                }
                BaseDanmakuParser baseDanmakuParser = this.mParser;
                if (baseDanmakuParser != null) {
                    baseDanmakuParser.release();
                }
                if (getLooper() == Looper.getMainLooper()) {
                    return;
                }
                getLooper().quit();
                return;
            case 7:
                removeMessages(3);
                removeMessages(2);
                iDrawTask = this.drawTask;
                if (iDrawTask != null) {
                    iDrawTask.onPlayStateChanged(2);
                }
                if (i == 6) {
                }
                this.quitFlag = true;
                syncTimerIfNeeded();
                this.pausedPosition = this.timer.currMillisecond;
                if (this.mUpdateInSeparateThread) {
                }
                if (this.mFrameCallback != null) {
                    Choreographer.getInstance().removeFrameCallback(this.mFrameCallback);
                    break;
                }
                if (i != 6) {
                }
                break;
            case 8:
                this.mDanmakusVisible = true;
                Long l3 = (Long) message.obj;
                IDrawTask iDrawTask6 = this.drawTask;
                if (iDrawTask6 != null) {
                    if (l3 == null) {
                        this.timer.update(getCurrentTime());
                        this.drawTask.requestClear();
                    } else {
                        iDrawTask6.start();
                        this.drawTask.seek(l3.longValue());
                        this.drawTask.requestClear();
                        z = true;
                        if (this.quitFlag && (iDanmakuViewController = this.mDanmakuView) != null) {
                            iDanmakuViewController.drawDanmakus();
                        }
                        notifyRendering();
                        if (!z) {
                            return;
                        }
                        l = (Long) message.obj;
                        if (l != null) {
                        }
                        if (i == 4) {
                        }
                        removeMessages(7);
                        this.quitFlag = false;
                        if (this.mReady) {
                        }
                    }
                }
                z = false;
                if (this.quitFlag) {
                    iDanmakuViewController.drawDanmakus();
                }
                notifyRendering();
                if (!z) {
                }
                l = (Long) message.obj;
                if (l != null) {
                }
                if (i == 4) {
                }
                removeMessages(7);
                this.quitFlag = false;
                if (this.mReady) {
                }
                break;
            case 9:
                this.mDanmakusVisible = false;
                IDanmakuViewController iDanmakuViewController2 = this.mDanmakuView;
                if (iDanmakuViewController2 != null) {
                    iDanmakuViewController2.clear();
                }
                IDrawTask iDrawTask7 = this.drawTask;
                if (iDrawTask7 != null) {
                    iDrawTask7.requestClear();
                    this.drawTask.requestHide();
                }
                Boolean bool = (Boolean) message.obj;
                if (bool.booleanValue() && (iDrawTask2 = this.drawTask) != null) {
                    iDrawTask2.quit();
                }
                if (!bool.booleanValue()) {
                    return;
                }
                removeMessages(3);
                removeMessages(2);
                iDrawTask = this.drawTask;
                if (iDrawTask != null) {
                }
                if (i == 6) {
                }
                this.quitFlag = true;
                syncTimerIfNeeded();
                this.pausedPosition = this.timer.currMillisecond;
                if (this.mUpdateInSeparateThread) {
                }
                if (this.mFrameCallback != null) {
                }
                if (i != 6) {
                }
                break;
            case 10:
                DanmakuContext danmakuContext = this.mContext;
                danmakuContext.mDanmakuFactory.notifyDispSizeChanged(danmakuContext);
                Boolean bool2 = (Boolean) message.obj;
                if (bool2 == null || !bool2.booleanValue()) {
                    return;
                }
                this.mContext.mGlobalFlagValues.updateMeasureFlag();
                this.mContext.mGlobalFlagValues.updateVisibleFlag();
                this.drawTask.requestClearRetainer();
                return;
            case 11:
                notifyRendering();
                return;
            case 12:
                if (!this.quitFlag || this.mDanmakuView == null) {
                    return;
                }
                this.drawTask.requestClear();
                this.mDanmakuView.drawDanmakus();
                notifyRendering();
                return;
            case 13:
                IDrawTask iDrawTask8 = this.drawTask;
                if (iDrawTask8 == null) {
                    return;
                }
                iDrawTask8.clearDanmakusOnScreen(getCurrentTime());
                return;
            case 14:
                IDrawTask iDrawTask9 = this.drawTask;
                if (iDrawTask9 == null) {
                    return;
                }
                iDrawTask9.requestRender();
                return;
            default:
                return;
        }
    }

    private synchronized void quitUpdateThread() {
        UpdateThread updateThread = this.mThread;
        this.mThread = null;
        if (updateThread != null) {
            synchronized (this.drawTask) {
                this.drawTask.notifyAll();
            }
            updateThread.quit();
            try {
                updateThread.join(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateInCurrentThread() {
        if (this.quitFlag) {
            return;
        }
        long syncTimer = syncTimer(SystemClock.uptimeMillis());
        if (syncTimer < 0 && !this.mNonBlockModeEnable) {
            removeMessages(2);
            sendEmptyMessageDelayed(2, 60 - syncTimer);
            return;
        }
        long drawDanmakus = this.mDanmakuView.drawDanmakus();
        removeMessages(2);
        if (drawDanmakus > this.mCordonTime2) {
            this.timer.add(drawDanmakus);
            this.mDrawTimes.clear();
        }
        if (!this.mDanmakusVisible) {
            waitRendering(10000000L);
            return;
        }
        IRenderer.RenderingState renderingState = this.mRenderingState;
        if (renderingState.nothingRendered && this.mIdleSleep) {
            long j = renderingState.endTime - this.timer.currMillisecond;
            if (j > 500) {
                waitRendering(j - 10);
                return;
            }
        }
        long j2 = this.mFrameUpdateRate;
        if (drawDanmakus < j2) {
            sendEmptyMessageDelayed(2, j2 - drawDanmakus);
        } else {
            sendEmptyMessage(2);
        }
    }

    private void updateInNewThread() {
        if (this.mThread != null) {
            return;
        }
        this.mThread = new UpdateThread("DFM Update") { // from class: master.flame.danmaku.controller.DrawHandler.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                long uptimeMillis = SystemClock.uptimeMillis();
                while (!isQuited() && !DrawHandler.this.quitFlag) {
                    long uptimeMillis2 = SystemClock.uptimeMillis();
                    if (DrawHandler.this.mFrameUpdateRate - (SystemClock.uptimeMillis() - uptimeMillis) <= 1 || DrawHandler.this.mNonBlockModeEnable) {
                        long syncTimer = DrawHandler.this.syncTimer(uptimeMillis2);
                        if (syncTimer >= 0 || DrawHandler.this.mNonBlockModeEnable) {
                            long drawDanmakus = DrawHandler.this.mDanmakuView.drawDanmakus();
                            if (drawDanmakus > DrawHandler.this.mCordonTime2) {
                                DrawHandler.this.timer.add(drawDanmakus);
                                DrawHandler.this.mDrawTimes.clear();
                            }
                            if (!DrawHandler.this.mDanmakusVisible) {
                                DrawHandler.this.waitRendering(10000000L);
                            } else if (DrawHandler.this.mRenderingState.nothingRendered && DrawHandler.this.mIdleSleep) {
                                long j = DrawHandler.this.mRenderingState.endTime - DrawHandler.this.timer.currMillisecond;
                                if (j > 500) {
                                    DrawHandler.this.notifyRendering();
                                    DrawHandler.this.waitRendering(j - 10);
                                }
                            }
                        } else {
                            SystemClock.sleep(60 - syncTimer);
                        }
                        uptimeMillis = uptimeMillis2;
                    } else {
                        SystemClock.sleep(1L);
                    }
                }
            }
        };
        this.mThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(16)
    /* loaded from: classes4.dex */
    public class FrameCallback implements Choreographer.FrameCallback {
        private FrameCallback() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            DrawHandler.this.sendEmptyMessage(2);
        }
    }

    @TargetApi(16)
    private void updateInChoreographer() {
        if (this.quitFlag) {
            return;
        }
        Choreographer.getInstance().postFrameCallback(this.mFrameCallback);
        if (syncTimer(SystemClock.uptimeMillis()) < 0) {
            removeMessages(2);
            return;
        }
        long drawDanmakus = this.mDanmakuView.drawDanmakus();
        removeMessages(2);
        if (drawDanmakus > this.mCordonTime2) {
            this.timer.add(drawDanmakus);
            this.mDrawTimes.clear();
        }
        if (!this.mDanmakusVisible) {
            waitRendering(10000000L);
            return;
        }
        IRenderer.RenderingState renderingState = this.mRenderingState;
        if (!renderingState.nothingRendered || !this.mIdleSleep) {
            return;
        }
        long j = renderingState.endTime - this.timer.currMillisecond;
        if (j <= 500) {
            return;
        }
        waitRendering(j - 10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long syncTimer(long j) {
        long j2;
        Callback callback;
        long j3 = 0;
        if (!this.mInSeekingAction && !this.mInSyncAction) {
            this.mInSyncAction = true;
            long j4 = j - this.mTimeBase;
            if (this.mNonBlockModeEnable) {
                Callback callback2 = this.mCallback;
                if (callback2 != null) {
                    callback2.updateTimer(this.timer);
                    j3 = this.timer.lastInterval();
                }
            } else if (!this.mDanmakusVisible || this.mRenderingState.nothingRendered || this.mInWaitingState) {
                this.timer.update(j4);
                this.mRemainingTime = 0L;
                Callback callback3 = this.mCallback;
                if (callback3 != null) {
                    callback3.updateTimer(this.timer);
                }
            } else {
                long j5 = j4 - this.timer.currMillisecond;
                long max = Math.max(this.mFrameUpdateRate, getAverageRenderingTime());
                if (j5 <= 2000) {
                    long j6 = this.mRenderingState.consumingTime;
                    long j7 = this.mCordonTime;
                    if (j6 <= j7 && max <= j7) {
                        long j8 = this.mFrameUpdateRate;
                        long min = Math.min(this.mCordonTime, Math.max(j8, max + (j5 / j8)));
                        long j9 = this.mLastDeltaTime;
                        long j10 = min - j9;
                        if (j10 > 3 && j10 < 8 && j9 >= this.mFrameUpdateRate && j9 <= this.mCordonTime) {
                            min = j9;
                        }
                        j2 = j5 - min;
                        this.mLastDeltaTime = min;
                        j3 = min;
                        this.mRemainingTime = j2;
                        this.timer.add(j3);
                        callback = this.mCallback;
                        if (callback != null) {
                            callback.updateTimer(this.timer);
                        }
                    }
                }
                j2 = 0;
                j3 = j5;
                this.mRemainingTime = j2;
                this.timer.add(j3);
                callback = this.mCallback;
                if (callback != null) {
                }
            }
            this.mInSyncAction = false;
        }
        return j3;
    }

    private void syncTimerIfNeeded() {
        if (this.mInWaitingState) {
            syncTimer(SystemClock.uptimeMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRenderingConfigs() {
        this.mCordonTime = Math.max(33L, ((float) 16) * 2.5f);
        this.mCordonTime2 = ((float) this.mCordonTime) * 2.5f;
        this.mFrameUpdateRate = Math.max(16L, 15L);
    }

    private void prepare(final Runnable runnable) {
        if (this.drawTask == null) {
            this.drawTask = createDrawTask(this.mDanmakuView.isDanmakuDrawingCacheEnabled(), this.timer, this.mDanmakuView.getContext(), this.mDanmakuView.getViewWidth(), this.mDanmakuView.getViewHeight(), this.mDanmakuView.isHardwareAccelerated(), new IDrawTask.TaskListener() { // from class: master.flame.danmaku.controller.DrawHandler.3
                @Override // master.flame.danmaku.controller.IDrawTask.TaskListener
                public void ready() {
                    DrawHandler.this.initRenderingConfigs();
                    runnable.run();
                }

                @Override // master.flame.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakuAdd(BaseDanmaku baseDanmaku) {
                    if (baseDanmaku.isTimeOut()) {
                        return;
                    }
                    long actualTime = baseDanmaku.getActualTime() - DrawHandler.this.getCurrentTime();
                    if (actualTime >= DrawHandler.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION || (!DrawHandler.this.mInWaitingState && !DrawHandler.this.mRenderingState.nothingRendered)) {
                        if (actualTime <= 0 || actualTime > DrawHandler.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                            return;
                        }
                        DrawHandler.this.sendEmptyMessageDelayed(11, actualTime);
                        return;
                    }
                    DrawHandler.this.notifyRendering();
                }

                @Override // master.flame.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakuShown(BaseDanmaku baseDanmaku) {
                    if (DrawHandler.this.mCallback != null) {
                        DrawHandler.this.mCallback.danmakuShown(baseDanmaku);
                    }
                }

                @Override // master.flame.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakusDrawingFinished() {
                    if (DrawHandler.this.mCallback != null) {
                        DrawHandler.this.mCallback.drawingFinished();
                    }
                }

                @Override // master.flame.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakuConfigChanged() {
                    DrawHandler.this.redrawIfNeeded();
                }
            });
        } else {
            runnable.run();
        }
    }

    public boolean isPrepared() {
        return this.mReady;
    }

    private IDrawTask createDrawTask(boolean z, DanmakuTimer danmakuTimer, Context context, int i, int i2, boolean z2, IDrawTask.TaskListener taskListener) {
        this.mDisp = this.mContext.getDisplayer();
        this.mDisp.setSize(i, i2);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisp.setDensities(displayMetrics.density, displayMetrics.densityDpi, displayMetrics.scaledDensity);
        this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
        this.mDisp.setHardwareAccelerated(z2);
        IDrawTask cacheManagingDrawTask = z ? new CacheManagingDrawTask(danmakuTimer, this.mContext, taskListener) : new DrawTask(danmakuTimer, this.mContext, taskListener);
        cacheManagingDrawTask.setParser(this.mParser);
        cacheManagingDrawTask.prepare();
        obtainMessage(10, false).sendToTarget();
        return cacheManagingDrawTask;
    }

    public void addDanmaku(BaseDanmaku baseDanmaku) {
        if (this.drawTask != null) {
            baseDanmaku.flags = this.mContext.mGlobalFlagValues;
            baseDanmaku.setTimer(this.timer);
            this.drawTask.addDanmaku(baseDanmaku);
            obtainMessage(11).sendToTarget();
        }
    }

    public void resume() {
        removeMessages(7);
        sendEmptyMessage(3);
    }

    public void prepare() {
        boolean z = false;
        this.mReady = false;
        if (Build.VERSION.SDK_INT < 16) {
            DanmakuContext danmakuContext = this.mContext;
            if (danmakuContext.updateMethod == 0) {
                danmakuContext.updateMethod = (byte) 2;
            }
        }
        if (this.mContext.updateMethod == 0) {
            this.mFrameCallback = new FrameCallback();
        }
        if (this.mContext.updateMethod == 1) {
            z = true;
        }
        this.mUpdateInSeparateThread = z;
        sendEmptyMessage(5);
    }

    public void pause() {
        removeMessages(3);
        syncTimerIfNeeded();
        sendEmptyMessage(7);
    }

    public void showDanmakus(Long l) {
        if (this.mDanmakusVisible) {
            return;
        }
        this.mDanmakusVisible = true;
        removeMessages(8);
        removeMessages(9);
        obtainMessage(8, l).sendToTarget();
    }

    public long hideDanmakus(boolean z) {
        if (!this.mDanmakusVisible) {
            return this.timer.currMillisecond;
        }
        this.mDanmakusVisible = false;
        removeMessages(8);
        removeMessages(9);
        obtainMessage(9, Boolean.valueOf(z)).sendToTarget();
        return this.timer.currMillisecond;
    }

    public IRenderer.RenderingState draw(Canvas canvas) {
        AbsDanmakuSync absDanmakuSync;
        boolean isSyncPlayingState;
        if (this.drawTask == null) {
            return this.mRenderingState;
        }
        if (!this.mInWaitingState && (absDanmakuSync = this.mContext.danmakuSync) != null && ((isSyncPlayingState = absDanmakuSync.isSyncPlayingState()) || !this.quitFlag)) {
            int syncState = absDanmakuSync.getSyncState();
            if (syncState == 2) {
                long j = this.timer.currMillisecond;
                long uptimeMillis = absDanmakuSync.getUptimeMillis();
                long j2 = uptimeMillis - j;
                if (Math.abs(j2) > absDanmakuSync.getThresholdTimeMills()) {
                    if (isSyncPlayingState && this.quitFlag) {
                        resume();
                    }
                    this.drawTask.requestSync(j, uptimeMillis, j2);
                    this.timer.update(uptimeMillis);
                    this.mTimeBase -= j2;
                    this.mRemainingTime = 0L;
                }
            } else if (syncState == 1 && isSyncPlayingState && !this.quitFlag) {
                pause();
            }
        }
        this.mDisp.setExtraData(canvas);
        this.mRenderingState.set(this.drawTask.draw(this.mDisp));
        recordRenderingTime();
        return this.mRenderingState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redrawIfNeeded() {
        if (!this.quitFlag || !this.mDanmakusVisible) {
            return;
        }
        removeMessages(12);
        sendEmptyMessageDelayed(12, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRendering() {
        if (!this.mInWaitingState) {
            return;
        }
        IDrawTask iDrawTask = this.drawTask;
        if (iDrawTask != null) {
            iDrawTask.requestClear();
        }
        if (this.mUpdateInSeparateThread) {
            synchronized (this) {
                this.mDrawTimes.clear();
            }
            synchronized (this.drawTask) {
                this.drawTask.notifyAll();
            }
        } else {
            this.mDrawTimes.clear();
            removeMessages(2);
            sendEmptyMessage(2);
        }
        this.mInWaitingState = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitRendering(long j) {
        if (isStop() || !isPrepared() || this.mInSeekingAction) {
            return;
        }
        this.mRenderingState.sysTime = SystemClock.uptimeMillis();
        this.mInWaitingState = true;
        if (!this.mUpdateInSeparateThread) {
            if (j == 10000000) {
                removeMessages(11);
                removeMessages(2);
                return;
            }
            removeMessages(11);
            removeMessages(2);
            sendEmptyMessageDelayed(11, j);
        } else if (this.mThread == null) {
        } else {
            try {
                synchronized (this.drawTask) {
                    if (j == 10000000) {
                        this.drawTask.wait();
                    } else {
                        this.drawTask.wait(j);
                    }
                    sendEmptyMessage(11);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized long getAverageRenderingTime() {
        int size = this.mDrawTimes.size();
        if (size <= 0) {
            return 0L;
        }
        Long peekFirst = this.mDrawTimes.peekFirst();
        Long peekLast = this.mDrawTimes.peekLast();
        if (peekFirst != null && peekLast != null) {
            return (peekLast.longValue() - peekFirst.longValue()) / size;
        }
        return 0L;
    }

    private synchronized void recordRenderingTime() {
        this.mDrawTimes.addLast(Long.valueOf(SystemClock.uptimeMillis()));
        if (this.mDrawTimes.size() > 500) {
            this.mDrawTimes.removeFirst();
        }
    }

    public void notifyDispSizeChanged(int i, int i2) {
        AbsDisplayer absDisplayer = this.mDisp;
        if (absDisplayer == null) {
            return;
        }
        if (absDisplayer.getWidth() == i && this.mDisp.getHeight() == i2) {
            return;
        }
        this.mDisp.setSize(i, i2);
        obtainMessage(10, true).sendToTarget();
    }

    public IDanmakus getCurrentVisibleDanmakus() {
        IDrawTask iDrawTask = this.drawTask;
        if (iDrawTask != null) {
            return iDrawTask.getVisibleDanmakusOnTime(getCurrentTime());
        }
        return null;
    }

    public long getCurrentTime() {
        long j;
        long j2;
        if (!this.mReady) {
            return 0L;
        }
        if (this.mInSeekingAction) {
            return this.mDesireSeekingTime;
        }
        if (this.quitFlag || !this.mInWaitingState) {
            j = this.timer.currMillisecond;
            j2 = this.mRemainingTime;
        } else {
            j = SystemClock.uptimeMillis();
            j2 = this.mTimeBase;
        }
        return j - j2;
    }

    public void clearDanmakusOnScreen() {
        obtainMessage(13).sendToTarget();
    }

    public DanmakuContext getConfig() {
        return this.mContext;
    }
}
