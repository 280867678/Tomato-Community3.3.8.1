package master.flame.danmaku.controller;

import android.graphics.Canvas;
import master.flame.danmaku.controller.IDrawTask;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.renderer.android.DanmakuRenderer;
import master.flame.danmaku.danmaku.util.SystemClock;

/* loaded from: classes4.dex */
public class DrawTask implements IDrawTask {
    protected boolean clearRetainerFlag;
    protected IDanmakus danmakuList;
    protected final DanmakuContext mContext;
    protected final AbsDisplayer mDisp;
    private boolean mIsHidden;
    private long mLastBeginMills;
    private BaseDanmaku mLastDanmaku;
    private long mLastEndMills;
    protected BaseDanmakuParser mParser;
    protected boolean mReadyState;
    final IRenderer mRenderer;
    private boolean mRequestRender;
    private IDanmakus mRunningDanmakus;
    IDrawTask.TaskListener mTaskListener;
    DanmakuTimer mTimer;
    private IDanmakus danmakus = new Danmakus(4);
    private long mStartRenderTime = 0;
    private final IRenderer.RenderingState mRenderingState = new IRenderer.RenderingState();
    private Danmakus mLiveDanmakus = new Danmakus(4);
    private DanmakuContext.ConfigChangedCallback mConfigChangedCallback = new DanmakuContext.ConfigChangedCallback() { // from class: master.flame.danmaku.controller.DrawTask.1
        @Override // master.flame.danmaku.danmaku.model.android.DanmakuContext.ConfigChangedCallback
        public boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object... objArr) {
            return DrawTask.this.onDanmakuConfigChanged(danmakuContext, danmakuConfigTag, objArr);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDanmakuRemoved(BaseDanmaku baseDanmaku) {
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void onPlayStateChanged(int i) {
    }

    public DrawTask(DanmakuTimer danmakuTimer, DanmakuContext danmakuContext, IDrawTask.TaskListener taskListener) {
        if (danmakuContext == null) {
            throw new IllegalArgumentException("context is null");
        }
        this.mContext = danmakuContext;
        this.mDisp = danmakuContext.getDisplayer();
        this.mTaskListener = taskListener;
        this.mRenderer = new DanmakuRenderer(danmakuContext);
        this.mRenderer.setOnDanmakuShownListener(new IRenderer.OnDanmakuShownListener() { // from class: master.flame.danmaku.controller.DrawTask.2
            @Override // master.flame.danmaku.danmaku.renderer.IRenderer.OnDanmakuShownListener
            public void onDanmakuShown(BaseDanmaku baseDanmaku) {
                IDrawTask.TaskListener taskListener2 = DrawTask.this.mTaskListener;
                if (taskListener2 != null) {
                    taskListener2.onDanmakuShown(baseDanmaku);
                }
            }
        });
        this.mRenderer.setVerifierEnabled(this.mContext.isPreventOverlappingEnabled() || this.mContext.isMaxLinesLimited());
        initTimer(danmakuTimer);
        Boolean valueOf = Boolean.valueOf(this.mContext.isDuplicateMergingEnabled());
        if (valueOf == null) {
            return;
        }
        if (valueOf.booleanValue()) {
            this.mContext.mDanmakuFilters.registerFilter("1017_Filter");
        } else {
            this.mContext.mDanmakuFilters.unregisterFilter("1017_Filter");
        }
    }

    protected void initTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public synchronized void addDanmaku(BaseDanmaku baseDanmaku) {
        boolean addItem;
        boolean addItem2;
        if (this.danmakuList == null) {
            return;
        }
        if (baseDanmaku.isLive) {
            this.mLiveDanmakus.addItem(baseDanmaku);
            removeUnusedLiveDanmakusIn(10);
        }
        baseDanmaku.index = this.danmakuList.size();
        boolean z = true;
        if (this.mLastBeginMills <= baseDanmaku.getActualTime() && baseDanmaku.getActualTime() <= this.mLastEndMills) {
            synchronized (this.danmakus) {
                addItem2 = this.danmakus.addItem(baseDanmaku);
            }
            z = addItem2;
        } else if (baseDanmaku.isLive) {
            z = false;
        }
        synchronized (this.danmakuList) {
            addItem = this.danmakuList.addItem(baseDanmaku);
        }
        if (!z || !addItem) {
            this.mLastEndMills = 0L;
            this.mLastBeginMills = 0L;
        }
        if (addItem && this.mTaskListener != null) {
            this.mTaskListener.onDanmakuAdd(baseDanmaku);
        }
        if (this.mLastDanmaku == null || (baseDanmaku != null && this.mLastDanmaku != null && baseDanmaku.getActualTime() > this.mLastDanmaku.getActualTime())) {
            this.mLastDanmaku = baseDanmaku;
        }
    }

    protected synchronized void removeUnusedLiveDanmakusIn(final int i) {
        if (this.danmakuList != null && !this.danmakuList.isEmpty() && !this.mLiveDanmakus.isEmpty()) {
            this.mLiveDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.DrawTask.4
                long startTime = SystemClock.uptimeMillis();

                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    boolean isTimeOut = baseDanmaku.isTimeOut();
                    if (SystemClock.uptimeMillis() - this.startTime <= i && isTimeOut) {
                        DrawTask.this.danmakuList.removeItem(baseDanmaku);
                        DrawTask.this.onDanmakuRemoved(baseDanmaku);
                        return 2;
                    }
                    return 1;
                }
            });
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public IDanmakus getVisibleDanmakusOnTime(long j) {
        IDanmakus iDanmakus;
        long j2 = this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
        long j3 = (j - j2) - 100;
        long j4 = j + j2;
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i >= 3) {
                iDanmakus = null;
                break;
            }
            try {
                iDanmakus = this.danmakuList.subnew(j3, j4);
                break;
            } catch (Exception unused) {
                i = i2;
            }
        }
        final Danmakus danmakus = new Danmakus();
        if (iDanmakus != null && !iDanmakus.isEmpty()) {
            iDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>(this) { // from class: master.flame.danmaku.controller.DrawTask.5
                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (!baseDanmaku.isShown() || baseDanmaku.isOutside()) {
                        return 0;
                    }
                    danmakus.addItem(baseDanmaku);
                    return 0;
                }
            });
        }
        return danmakus;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public synchronized IRenderer.RenderingState draw(AbsDisplayer absDisplayer) {
        return drawDanmakus(absDisplayer, this.mTimer);
    }

    public void reset() {
        if (this.danmakus != null) {
            this.danmakus = new Danmakus();
        }
        IRenderer iRenderer = this.mRenderer;
        if (iRenderer != null) {
            iRenderer.clear();
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void seek(long j) {
        BaseDanmaku last;
        reset();
        this.mContext.mGlobalFlagValues.updateVisibleFlag();
        this.mContext.mGlobalFlagValues.updateFirstShownFlag();
        this.mContext.mGlobalFlagValues.updateSyncOffsetTimeFlag();
        this.mContext.mGlobalFlagValues.updatePrepareFlag();
        this.mRunningDanmakus = new Danmakus(4);
        if (j < 1000) {
            j = 0;
        }
        this.mStartRenderTime = j;
        this.mRenderingState.reset();
        this.mRenderingState.endTime = this.mStartRenderTime;
        this.mLastEndMills = 0L;
        this.mLastBeginMills = 0L;
        IDanmakus iDanmakus = this.danmakuList;
        if (iDanmakus == null || (last = iDanmakus.last()) == null || last.isTimeOut()) {
            return;
        }
        this.mLastDanmaku = last;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void clearDanmakusOnScreen(long j) {
        reset();
        this.mContext.mGlobalFlagValues.updateVisibleFlag();
        this.mContext.mGlobalFlagValues.updateFirstShownFlag();
        this.mStartRenderTime = j;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void start() {
        this.mContext.registerConfigChangedCallback(this.mConfigChangedCallback);
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void quit() {
        this.mContext.unregisterAllConfigChangedCallbacks();
        IRenderer iRenderer = this.mRenderer;
        if (iRenderer != null) {
            iRenderer.release();
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void prepare() {
        BaseDanmakuParser baseDanmakuParser = this.mParser;
        if (baseDanmakuParser == null) {
            return;
        }
        loadDanmakus(baseDanmakuParser);
        this.mLastEndMills = 0L;
        this.mLastBeginMills = 0L;
        IDrawTask.TaskListener taskListener = this.mTaskListener;
        if (taskListener == null) {
            return;
        }
        taskListener.ready();
        this.mReadyState = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadDanmakus(BaseDanmakuParser baseDanmakuParser) {
        this.danmakuList = baseDanmakuParser.setConfig(this.mContext).setDisplayer(this.mDisp).setTimer(this.mTimer).setListener(new BaseDanmakuParser.Listener(this) { // from class: master.flame.danmaku.controller.DrawTask.6
        }).getDanmakus();
        this.mContext.mGlobalFlagValues.resetAll();
        IDanmakus iDanmakus = this.danmakuList;
        if (iDanmakus != null) {
            this.mLastDanmaku = iDanmakus.last();
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void setParser(BaseDanmakuParser baseDanmakuParser) {
        this.mParser = baseDanmakuParser;
        this.mReadyState = false;
    }

    protected IRenderer.RenderingState drawDanmakus(AbsDisplayer absDisplayer, DanmakuTimer danmakuTimer) {
        long j;
        IDanmakus iDanmakus;
        IDanmakus iDanmakus2;
        if (this.clearRetainerFlag) {
            this.mRenderer.clearRetainer();
            this.clearRetainerFlag = false;
        }
        if (this.danmakuList != null) {
            DrawHelper.clearCanvas((Canvas) absDisplayer.mo6799getExtraData());
            if (this.mIsHidden && !this.mRequestRender) {
                return this.mRenderingState;
            }
            this.mRequestRender = false;
            IRenderer.RenderingState renderingState = this.mRenderingState;
            long j2 = danmakuTimer.currMillisecond;
            long j3 = this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
            long j4 = (j2 - j3) - 100;
            long j5 = j3 + j2;
            IDanmakus iDanmakus3 = this.danmakus;
            long j6 = this.mLastBeginMills;
            if (j6 <= j4) {
                j = this.mLastEndMills;
                if (j2 <= j) {
                    iDanmakus = iDanmakus3;
                    iDanmakus2 = this.mRunningDanmakus;
                    beginTracing(renderingState, iDanmakus2, iDanmakus);
                    if (iDanmakus2 != null && !iDanmakus2.isEmpty()) {
                        IRenderer.RenderingState renderingState2 = this.mRenderingState;
                        renderingState2.isRunningDanmakus = true;
                        this.mRenderer.draw(absDisplayer, iDanmakus2, 0L, renderingState2);
                    }
                    this.mRenderingState.isRunningDanmakus = false;
                    if (iDanmakus == null && !iDanmakus.isEmpty()) {
                        this.mRenderer.draw(this.mDisp, iDanmakus, this.mStartRenderTime, renderingState);
                        endTracing(renderingState);
                        if (renderingState.nothingRendered) {
                            BaseDanmaku baseDanmaku = this.mLastDanmaku;
                            if (baseDanmaku != null && baseDanmaku.isTimeOut()) {
                                this.mLastDanmaku = null;
                                IDrawTask.TaskListener taskListener = this.mTaskListener;
                                if (taskListener != null) {
                                    taskListener.onDanmakusDrawingFinished();
                                }
                            }
                            if (renderingState.beginTime == -1) {
                                renderingState.beginTime = j6;
                            }
                            if (renderingState.endTime == -1) {
                                renderingState.endTime = j;
                            }
                        }
                        return renderingState;
                    }
                    renderingState.nothingRendered = true;
                    renderingState.beginTime = j6;
                    renderingState.endTime = j;
                    return renderingState;
                }
            }
            IDanmakus sub = this.danmakuList.sub(j4, j5);
            if (sub != null) {
                this.danmakus = sub;
            }
            this.mLastBeginMills = j4;
            this.mLastEndMills = j5;
            j = j5;
            j6 = j4;
            iDanmakus = sub;
            iDanmakus2 = this.mRunningDanmakus;
            beginTracing(renderingState, iDanmakus2, iDanmakus);
            if (iDanmakus2 != null) {
                IRenderer.RenderingState renderingState22 = this.mRenderingState;
                renderingState22.isRunningDanmakus = true;
                this.mRenderer.draw(absDisplayer, iDanmakus2, 0L, renderingState22);
            }
            this.mRenderingState.isRunningDanmakus = false;
            if (iDanmakus == null) {
            }
            renderingState.nothingRendered = true;
            renderingState.beginTime = j6;
            renderingState.endTime = j;
            return renderingState;
        }
        return null;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestClear() {
        this.mLastEndMills = 0L;
        this.mLastBeginMills = 0L;
        this.mIsHidden = false;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestClearRetainer() {
        this.clearRetainerFlag = true;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestSync(long j, long j2, final long j3) {
        IDanmakus obtainRunningDanmakus = this.mRenderingState.obtainRunningDanmakus();
        this.mRunningDanmakus = obtainRunningDanmakus;
        obtainRunningDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>(this) { // from class: master.flame.danmaku.controller.DrawTask.7
            @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
            public int accept(BaseDanmaku baseDanmaku) {
                if (baseDanmaku.isOutside()) {
                    return 2;
                }
                baseDanmaku.setTimeOffset(j3 + baseDanmaku.timeOffset);
                return baseDanmaku.timeOffset == 0 ? 2 : 0;
            }
        });
        this.mStartRenderTime = j2;
    }

    public boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object... objArr) {
        boolean handleOnDanmakuConfigChanged = handleOnDanmakuConfigChanged(danmakuContext, danmakuConfigTag, objArr);
        IDrawTask.TaskListener taskListener = this.mTaskListener;
        if (taskListener != null) {
            taskListener.onDanmakuConfigChanged();
        }
        return handleOnDanmakuConfigChanged;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean handleOnDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object[] objArr) {
        Boolean bool;
        boolean z = false;
        if (danmakuConfigTag == null || DanmakuContext.DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN.equals(danmakuConfigTag)) {
            return true;
        }
        if (DanmakuContext.DanmakuConfigTag.DUPLICATE_MERGING_ENABLED.equals(danmakuConfigTag)) {
            Boolean bool2 = (Boolean) objArr[0];
            if (bool2 != null) {
                if (bool2.booleanValue()) {
                    this.mContext.mDanmakuFilters.registerFilter("1017_Filter");
                    return true;
                }
                this.mContext.mDanmakuFilters.unregisterFilter("1017_Filter");
                return true;
            }
        } else if (DanmakuContext.DanmakuConfigTag.SCALE_TEXTSIZE.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.DANMAKU_MARGIN.equals(danmakuConfigTag)) {
            requestClearRetainer();
        } else if (DanmakuContext.DanmakuConfigTag.MAXIMUN_LINES.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.OVERLAPPING_ENABLE.equals(danmakuConfigTag)) {
            IRenderer iRenderer = this.mRenderer;
            if (iRenderer == null) {
                return true;
            }
            if (this.mContext.isPreventOverlappingEnabled() || this.mContext.isMaxLinesLimited()) {
                z = true;
            }
            iRenderer.setVerifierEnabled(z);
            return true;
        } else if (DanmakuContext.DanmakuConfigTag.ALIGN_BOTTOM.equals(danmakuConfigTag) && (bool = (Boolean) objArr[0]) != null) {
            IRenderer iRenderer2 = this.mRenderer;
            if (iRenderer2 == null) {
                return true;
            }
            iRenderer2.alignBottom(bool.booleanValue());
            return true;
        }
        return false;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestHide() {
        this.mIsHidden = true;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestRender() {
        this.mRequestRender = true;
    }

    private void beginTracing(IRenderer.RenderingState renderingState, IDanmakus iDanmakus, IDanmakus iDanmakus2) {
        renderingState.reset();
        renderingState.timer.update(SystemClock.uptimeMillis());
        int i = 0;
        renderingState.indexInScreen = 0;
        int size = iDanmakus != null ? iDanmakus.size() : 0;
        if (iDanmakus2 != null) {
            i = iDanmakus2.size();
        }
        renderingState.totalSizeInScreen = size + i;
    }

    private void endTracing(IRenderer.RenderingState renderingState) {
        renderingState.nothingRendered = renderingState.totalDanmakuCount == 0;
        long j = -1;
        if (renderingState.nothingRendered) {
            renderingState.beginTime = -1L;
        }
        BaseDanmaku baseDanmaku = renderingState.lastDanmaku;
        renderingState.lastDanmaku = null;
        if (baseDanmaku != null) {
            j = baseDanmaku.getActualTime();
        }
        renderingState.endTime = j;
        renderingState.consumingTime = renderingState.timer.update(SystemClock.uptimeMillis());
    }
}
