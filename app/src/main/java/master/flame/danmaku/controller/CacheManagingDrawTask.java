package master.flame.danmaku.controller;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import master.flame.danmaku.controller.IDrawTask;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.ICacheManager;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDrawingCache;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.DanmakuFactory;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.DrawingCache;
import master.flame.danmaku.danmaku.model.android.DrawingCachePoolManager;
import master.flame.danmaku.danmaku.model.objectpool.Pool;
import master.flame.danmaku.danmaku.model.objectpool.Pools;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.util.DanmakuUtils;
import master.flame.danmaku.danmaku.util.SystemClock;
import tv.cjump.jni.NativeBitmapFactory;

/* loaded from: classes4.dex */
public class CacheManagingDrawTask extends DrawTask {
    private CacheManager mCacheManager;
    private DanmakuTimer mCacheTimer;
    private final Object mDrawingNotify = new Object();
    private int mMaxCacheSize;
    private int mRemaininCacheCount;

    public CacheManagingDrawTask(DanmakuTimer danmakuTimer, DanmakuContext danmakuContext, IDrawTask.TaskListener taskListener) {
        super(danmakuTimer, danmakuContext, taskListener);
        this.mMaxCacheSize = 2;
        NativeBitmapFactory.loadLibs();
        this.mMaxCacheSize = (int) Math.max(4194304.0f, ((float) Runtime.getRuntime().maxMemory()) * danmakuContext.cachingPolicy.maxCachePoolSizeFactorPercentage);
        this.mCacheManager = new CacheManager(this.mMaxCacheSize, 3);
        this.mRenderer.setCacheManager(this.mCacheManager);
    }

    @Override // master.flame.danmaku.controller.DrawTask
    protected void initTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
        this.mCacheTimer = new DanmakuTimer();
        this.mCacheTimer.update(danmakuTimer.currMillisecond);
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void addDanmaku(BaseDanmaku baseDanmaku) {
        super.addDanmaku(baseDanmaku);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager == null) {
            return;
        }
        cacheManager.addDanmaku(baseDanmaku);
    }

    @Override // master.flame.danmaku.controller.DrawTask
    protected void onDanmakuRemoved(BaseDanmaku baseDanmaku) {
        super.onDanmakuRemoved(baseDanmaku);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            int i = this.mRemaininCacheCount + 1;
            this.mRemaininCacheCount = i;
            if (i <= 5) {
                return;
            }
            cacheManager.requestClearTimeout();
            this.mRemaininCacheCount = 0;
            return;
        }
        IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
        if (drawingCache == null) {
            return;
        }
        if (drawingCache.hasReferences()) {
            drawingCache.decreaseReference();
        } else {
            drawingCache.destroy();
        }
        baseDanmaku.cache = null;
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public IRenderer.RenderingState draw(AbsDisplayer absDisplayer) {
        CacheManager cacheManager;
        IRenderer.RenderingState draw = super.draw(absDisplayer);
        synchronized (this.mDrawingNotify) {
            this.mDrawingNotify.notify();
        }
        if (draw != null && (cacheManager = this.mCacheManager) != null && draw.totalDanmakuCount - draw.lastTotalDanmakuCount < -20) {
            cacheManager.requestClearTimeout();
            this.mCacheManager.requestBuild(-this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
        }
        return draw;
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void seek(long j) {
        super.seek(j);
        if (this.mCacheManager == null) {
            start();
        }
        this.mCacheManager.seek(j);
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void start() {
        super.start();
        NativeBitmapFactory.loadLibs();
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager == null) {
            this.mCacheManager = new CacheManager(this.mMaxCacheSize, 3);
            this.mCacheManager.begin();
            this.mRenderer.setCacheManager(this.mCacheManager);
            return;
        }
        cacheManager.resume();
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void quit() {
        super.quit();
        reset();
        this.mRenderer.setCacheManager(null);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.end();
            this.mCacheManager = null;
        }
        NativeBitmapFactory.releaseLibs();
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void prepare() {
        BaseDanmakuParser baseDanmakuParser = this.mParser;
        if (baseDanmakuParser == null) {
            return;
        }
        loadDanmakus(baseDanmakuParser);
        this.mCacheManager.begin();
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void onPlayStateChanged(int i) {
        super.onPlayStateChanged(i);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.onPlayStateChanged(i);
        }
    }

    @Override // master.flame.danmaku.controller.DrawTask, master.flame.danmaku.controller.IDrawTask
    public void requestSync(long j, long j2, long j3) {
        super.requestSync(j, j2, j3);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.seek(j2);
        }
    }

    /* loaded from: classes4.dex */
    public class CacheManager implements ICacheManager {
        private CacheHandler mHandler;
        private int mMaxSize;
        private int mScreenSize;
        public HandlerThread mThread;
        Danmakus mCaches = new Danmakus();
        DrawingCachePoolManager mCachePoolManager = new DrawingCachePoolManager();
        Pool<DrawingCache> mCachePool = Pools.finitePool(this.mCachePoolManager, 800);
        private boolean mEndFlag = false;
        private int mRealSize = 0;

        public CacheManager(int i, int i2) {
            this.mScreenSize = 3;
            this.mMaxSize = i;
            this.mScreenSize = i2;
        }

        public void seek(long j) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.requestCancelCaching();
            this.mHandler.removeMessages(3);
            this.mHandler.obtainMessage(5, Long.valueOf(j)).sendToTarget();
        }

        @Override // master.flame.danmaku.danmaku.model.ICacheManager
        public void addDanmaku(BaseDanmaku baseDanmaku) {
            if (this.mHandler != null) {
                if (baseDanmaku.isLive && baseDanmaku.forceBuildCacheInSameThread) {
                    if (baseDanmaku.isTimeOut()) {
                        return;
                    }
                    this.mHandler.createCache(baseDanmaku);
                    return;
                }
                this.mHandler.obtainMessage(2, baseDanmaku).sendToTarget();
            }
        }

        public void begin() {
            this.mEndFlag = false;
            if (this.mThread == null) {
                this.mThread = new HandlerThread("DFM Cache-Building Thread");
                this.mThread.start();
            }
            if (this.mHandler == null) {
                this.mHandler = new CacheHandler(this.mThread.getLooper());
            }
            this.mHandler.begin();
        }

        public void end() {
            this.mEndFlag = true;
            synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                CacheManagingDrawTask.this.mDrawingNotify.notifyAll();
            }
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.removeCallbacksAndMessages(null);
                this.mHandler.pause();
                this.mHandler = null;
            }
            HandlerThread handlerThread = this.mThread;
            if (handlerThread != null) {
                try {
                    handlerThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.mThread.quit();
                this.mThread = null;
            }
        }

        public void resume() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.resume();
            } else {
                begin();
            }
        }

        public void onPlayStateChanged(int i) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                cacheHandler.onPlayStateChanged(z);
            }
        }

        public float getPoolPercent() {
            int i = this.mMaxSize;
            if (i == 0) {
                return 0.0f;
            }
            return this.mRealSize / i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void evictAll() {
            Danmakus danmakus = this.mCaches;
            if (danmakus != null) {
                danmakus.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.1
                    @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        CacheManager.this.entryRemoved(true, baseDanmaku, null);
                        return 0;
                    }
                });
                this.mCaches.clear();
            }
            this.mRealSize = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void evictAllNotInScreen() {
            Danmakus danmakus = this.mCaches;
            if (danmakus != null) {
                danmakus.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.2
                    @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        if (baseDanmaku.isOutside()) {
                            CacheManager.this.entryRemoved(true, baseDanmaku, null);
                            return 2;
                        }
                        return 0;
                    }
                });
            }
        }

        protected void entryRemoved(boolean z, BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
            if (drawingCache != null) {
                long clearCache = clearCache(baseDanmaku);
                if (baseDanmaku.isTimeOut()) {
                    CacheManagingDrawTask.this.mContext.getDisplayer().getCacheStuffer().releaseResource(baseDanmaku);
                }
                if (clearCache <= 0) {
                    return;
                }
                this.mRealSize = (int) (this.mRealSize - clearCache);
                this.mCachePool.release((DrawingCache) drawingCache);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long clearCache(BaseDanmaku baseDanmaku) {
            IDrawingCache<?> iDrawingCache = baseDanmaku.cache;
            if (iDrawingCache == null) {
                return 0L;
            }
            if (iDrawingCache.hasReferences()) {
                iDrawingCache.decreaseReference();
                baseDanmaku.cache = null;
                return 0L;
            }
            long sizeOf = sizeOf(baseDanmaku);
            iDrawingCache.destroy();
            baseDanmaku.cache = null;
            return sizeOf;
        }

        protected int sizeOf(BaseDanmaku baseDanmaku) {
            IDrawingCache<?> iDrawingCache = baseDanmaku.cache;
            if (iDrawingCache == null || iDrawingCache.hasReferences()) {
                return 0;
            }
            return baseDanmaku.cache.size();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCachePool() {
            while (true) {
                DrawingCache acquire = this.mCachePool.acquire();
                if (acquire != null) {
                    acquire.destroy();
                } else {
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean push(BaseDanmaku baseDanmaku, int i, boolean z) {
            if (i > 0) {
                clearTimeOutAndFilteredCaches(i, z);
            }
            this.mCaches.addItem(baseDanmaku);
            this.mRealSize += i;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeOutCaches() {
            this.mCaches.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.3
                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (baseDanmaku.isTimeOut()) {
                        IDrawingCache<?> iDrawingCache = baseDanmaku.cache;
                        if (CacheManagingDrawTask.this.mContext.cachingPolicy.periodOfRecycle == -1 && iDrawingCache != null && !iDrawingCache.hasReferences() && iDrawingCache.size() / CacheManagingDrawTask.this.mMaxCacheSize < CacheManagingDrawTask.this.mContext.cachingPolicy.forceRecyleThreshold) {
                            return 0;
                        }
                        if (!CacheManager.this.mEndFlag) {
                            synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                                try {
                                    try {
                                        CacheManagingDrawTask.this.mDrawingNotify.wait(30L);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        return 1;
                                    }
                                } finally {
                                }
                            }
                        }
                        CacheManager.this.entryRemoved(false, baseDanmaku, null);
                        return 2;
                    }
                    return 1;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v2, types: [master.flame.danmaku.danmaku.model.IDanmakus$Consumer, master.flame.danmaku.controller.CacheManagingDrawTask$CacheManager$4] */
        public BaseDanmaku findReusableCache(final BaseDanmaku baseDanmaku, final boolean z, final int i) {
            final int slopPixel = (!z ? CacheManagingDrawTask.this.mDisp.getSlopPixel() * 2 : 0) + CacheManagingDrawTask.this.mContext.cachingPolicy.reusableOffsetPixel;
            ?? r0 = new IDanmakus.Consumer<BaseDanmaku, BaseDanmaku>(this) { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.4
                int count = 0;
                BaseDanmaku mResult;

                public BaseDanmaku result() {
                    return this.mResult;
                }

                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku2) {
                    int i2 = this.count;
                    this.count = i2 + 1;
                    if (i2 >= i) {
                        return 1;
                    }
                    IDrawingCache<?> drawingCache = baseDanmaku2.getDrawingCache();
                    if (drawingCache != null && drawingCache.get() != null) {
                        float f = baseDanmaku2.paintWidth;
                        BaseDanmaku baseDanmaku3 = baseDanmaku;
                        if (f == baseDanmaku3.paintWidth && baseDanmaku2.paintHeight == baseDanmaku3.paintHeight && baseDanmaku2.underlineColor == baseDanmaku3.underlineColor && baseDanmaku2.borderColor == baseDanmaku3.borderColor && baseDanmaku2.textColor == baseDanmaku3.textColor && baseDanmaku2.text.equals(baseDanmaku3.text) && baseDanmaku2.tag == baseDanmaku.tag) {
                            this.mResult = baseDanmaku2;
                            return 1;
                        } else if (z) {
                            return 0;
                        } else {
                            if (!baseDanmaku2.isTimeOut()) {
                                return 1;
                            }
                            if (drawingCache.hasReferences()) {
                                return 0;
                            }
                            float width = drawingCache.width() - baseDanmaku.paintWidth;
                            float height = drawingCache.height() - baseDanmaku.paintHeight;
                            if (width >= 0.0f) {
                                int i3 = slopPixel;
                                if (width <= i3 && height >= 0.0f && height <= i3) {
                                    this.mResult = baseDanmaku2;
                                    return 1;
                                }
                            }
                        }
                    }
                    return 0;
                }
            };
            this.mCaches.forEach(r0);
            return (BaseDanmaku) r0.result();
        }

        /* loaded from: classes4.dex */
        public class CacheHandler extends Handler {
            private boolean mCancelFlag;
            private boolean mIsPlayerPause;
            private boolean mPause;
            private boolean mSeekedFlag;

            public CacheHandler(Looper looper) {
                super(looper);
            }

            public void requestCancelCaching() {
                this.mCancelFlag = true;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                switch (i) {
                    case 1:
                        CacheManager.this.evictAllNotInScreen();
                        for (int i2 = 0; i2 < 300; i2++) {
                            CacheManager.this.mCachePool.release(new DrawingCache());
                        }
                        break;
                    case 2:
                        addDanmakuAndBuildCache((BaseDanmaku) message.obj);
                        return;
                    case 3:
                        removeMessages(3);
                        CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                        boolean z = (cacheManagingDrawTask.mTaskListener != null && !cacheManagingDrawTask.mReadyState) || this.mSeekedFlag;
                        prepareCaches(z);
                        if (z) {
                            this.mSeekedFlag = false;
                        }
                        CacheManagingDrawTask cacheManagingDrawTask2 = CacheManagingDrawTask.this;
                        IDrawTask.TaskListener taskListener = cacheManagingDrawTask2.mTaskListener;
                        if (taskListener == null || cacheManagingDrawTask2.mReadyState) {
                            return;
                        }
                        taskListener.ready();
                        CacheManagingDrawTask.this.mReadyState = true;
                        return;
                    case 4:
                        CacheManager.this.clearTimeOutCaches();
                        return;
                    case 5:
                        Long l = (Long) message.obj;
                        if (l == null) {
                            return;
                        }
                        long longValue = l.longValue();
                        long j = CacheManagingDrawTask.this.mCacheTimer.currMillisecond;
                        CacheManagingDrawTask.this.mCacheTimer.update(longValue);
                        this.mSeekedFlag = true;
                        long firstCacheTime = CacheManager.this.getFirstCacheTime();
                        if (longValue <= j) {
                            CacheManager cacheManager = CacheManager.this;
                            if (firstCacheTime - longValue <= CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                                cacheManager.clearTimeOutCaches();
                                prepareCaches(true);
                                resume();
                                return;
                            }
                        }
                        CacheManager.this.evictAllNotInScreen();
                        prepareCaches(true);
                        resume();
                        return;
                    case 6:
                        removeCallbacksAndMessages(null);
                        this.mPause = true;
                        CacheManager.this.evictAll();
                        CacheManager.this.clearCachePool();
                        getLooper().quit();
                        return;
                    case 7:
                        CacheManager.this.evictAll();
                        DanmakuTimer danmakuTimer = CacheManagingDrawTask.this.mCacheTimer;
                        CacheManagingDrawTask cacheManagingDrawTask3 = CacheManagingDrawTask.this;
                        danmakuTimer.update(cacheManagingDrawTask3.mTimer.currMillisecond - cacheManagingDrawTask3.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                        this.mSeekedFlag = true;
                        return;
                    case 8:
                        CacheManager.this.evictAllNotInScreen();
                        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                        return;
                    case 9:
                        CacheManager.this.evictAllNotInScreen();
                        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                        CacheManagingDrawTask.this.requestClear();
                        return;
                    default:
                        switch (i) {
                            case 16:
                                break;
                            case 17:
                                BaseDanmaku baseDanmaku = (BaseDanmaku) message.obj;
                                if (baseDanmaku == null) {
                                    return;
                                }
                                IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                                if (!((baseDanmaku.requestFlags & 1) != 0) && drawingCache != null && drawingCache.get() != null && !drawingCache.hasReferences()) {
                                    CacheManagingDrawTask cacheManagingDrawTask4 = CacheManagingDrawTask.this;
                                    baseDanmaku.cache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, cacheManagingDrawTask4.mDisp, (DrawingCache) baseDanmaku.cache, cacheManagingDrawTask4.mContext.cachingPolicy.bitsPerPixelOfCache);
                                    CacheManager.this.push(baseDanmaku, 0, true);
                                    return;
                                } else if (baseDanmaku.isLive) {
                                    CacheManager.this.clearCache(baseDanmaku);
                                    createCache(baseDanmaku);
                                    return;
                                } else {
                                    if (drawingCache != null && drawingCache.hasReferences()) {
                                        drawingCache.destroy();
                                    }
                                    CacheManager.this.entryRemoved(true, baseDanmaku, null);
                                    addDanmakuAndBuildCache(baseDanmaku);
                                    return;
                                }
                            case 18:
                                this.mCancelFlag = false;
                                return;
                            default:
                                return;
                        }
                }
                long dispatchAction = dispatchAction();
                if (dispatchAction <= 0) {
                    dispatchAction = CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION / 2;
                }
                sendEmptyMessageDelayed(16, dispatchAction);
            }

            private long dispatchAction() {
                long j = CacheManagingDrawTask.this.mCacheTimer.currMillisecond;
                CacheManager cacheManager = CacheManager.this;
                CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                long j2 = cacheManagingDrawTask.mTimer.currMillisecond;
                DanmakuContext danmakuContext = cacheManagingDrawTask.mContext;
                if (j <= j2 - danmakuContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                    if (danmakuContext.cachingPolicy.periodOfRecycle != -1) {
                        cacheManager.evictAllNotInScreen();
                    }
                    CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                    sendEmptyMessage(3);
                    return 0L;
                }
                float poolPercent = cacheManager.getPoolPercent();
                BaseDanmaku first = CacheManager.this.mCaches.first();
                long actualTime = first != null ? first.getActualTime() - CacheManagingDrawTask.this.mTimer.currMillisecond : 0L;
                CacheManagingDrawTask cacheManagingDrawTask2 = CacheManagingDrawTask.this;
                long j3 = cacheManagingDrawTask2.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
                long j4 = 2 * j3;
                if (poolPercent < 0.6f && actualTime > j3) {
                    cacheManagingDrawTask2.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                    removeMessages(3);
                    sendEmptyMessage(3);
                    return 0L;
                } else if (poolPercent > 0.4f && actualTime < (-j4)) {
                    removeMessages(4);
                    sendEmptyMessage(4);
                    return 0L;
                } else if (poolPercent >= 0.9f) {
                    return 0L;
                } else {
                    long j5 = CacheManagingDrawTask.this.mCacheTimer.currMillisecond - CacheManagingDrawTask.this.mTimer.currMillisecond;
                    if (first != null && first.isTimeOut()) {
                        CacheManagingDrawTask cacheManagingDrawTask3 = CacheManagingDrawTask.this;
                        if (j5 < (-cacheManagingDrawTask3.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION)) {
                            cacheManagingDrawTask3.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                            sendEmptyMessage(8);
                            sendEmptyMessage(3);
                            return 0L;
                        }
                    }
                    if (j5 > j4) {
                        return 0L;
                    }
                    removeMessages(3);
                    sendEmptyMessage(3);
                    return 0L;
                }
            }

            private void releaseDanmakuCache(BaseDanmaku baseDanmaku, DrawingCache drawingCache) {
                if (drawingCache == null) {
                    drawingCache = (DrawingCache) baseDanmaku.cache;
                }
                baseDanmaku.cache = null;
                if (drawingCache == null) {
                    return;
                }
                drawingCache.destroy();
                CacheManager.this.mCachePool.release(drawingCache);
            }

            private void preMeasure() {
                IDanmakus iDanmakus;
                try {
                    long j = CacheManagingDrawTask.this.mTimer.currMillisecond;
                    iDanmakus = CacheManagingDrawTask.this.danmakuList.subnew(j - CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION, (CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * 2) + j);
                } catch (Exception unused) {
                    iDanmakus = null;
                }
                if (iDanmakus == null || iDanmakus.isEmpty()) {
                    return;
                }
                iDanmakus.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.CacheHandler.1
                    @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        if (CacheHandler.this.mPause || CacheHandler.this.mCancelFlag) {
                            return 1;
                        }
                        if (!baseDanmaku.hasPassedFilter()) {
                            DanmakuContext danmakuContext = CacheManagingDrawTask.this.mContext;
                            danmakuContext.mDanmakuFilters.filter(baseDanmaku, 0, 0, null, true, danmakuContext);
                        }
                        if (baseDanmaku.isFiltered()) {
                            return 0;
                        }
                        if (!baseDanmaku.isMeasured()) {
                            baseDanmaku.measure(CacheManagingDrawTask.this.mDisp, true);
                        }
                        if (!baseDanmaku.isPrepared()) {
                            baseDanmaku.prepare(CacheManagingDrawTask.this.mDisp, true);
                        }
                        return 0;
                    }
                });
            }

            /* JADX WARN: Code restructure failed: missing block: B:17:0x0069, code lost:
                r19.this$1.this$0.mCacheTimer.update(r12);
             */
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0074, code lost:
                return 0;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            private long prepareCaches(final boolean z) {
                IDanmakus iDanmakus;
                boolean z2;
                preMeasure();
                final long j = CacheManagingDrawTask.this.mCacheTimer.currMillisecond - 30;
                CacheManager cacheManager = CacheManager.this;
                long j2 = j + (CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * cacheManager.mScreenSize);
                final long j3 = 0;
                if (j2 < CacheManagingDrawTask.this.mTimer.currMillisecond) {
                    return 0L;
                }
                final long uptimeMillis = SystemClock.uptimeMillis();
                IDanmakus iDanmakus2 = null;
                int i = 0;
                boolean z3 = false;
                while (true) {
                    try {
                        boolean z4 = z3;
                        iDanmakus = CacheManagingDrawTask.this.danmakuList.subnew(j, j2);
                        z2 = z4;
                    } catch (Exception unused) {
                        SystemClock.sleep(10L);
                        iDanmakus = iDanmakus2;
                        z2 = true;
                    }
                    i++;
                    if (i >= 3 || iDanmakus != null || !z2) {
                        break;
                    }
                    IDanmakus iDanmakus3 = iDanmakus;
                    z3 = z2;
                    iDanmakus2 = iDanmakus3;
                }
                BaseDanmaku first = iDanmakus.first();
                final BaseDanmaku last = iDanmakus.last();
                if (first == null || last == null) {
                    CacheManagingDrawTask.this.mCacheTimer.update(j2);
                    return 0L;
                }
                long actualTime = first.getActualTime();
                CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                long j4 = actualTime - cacheManagingDrawTask.mTimer.currMillisecond;
                long min = Math.min(100L, j4 < 0 ? 30L : ((j4 * 10) / cacheManagingDrawTask.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) + 30);
                if (!z) {
                    j3 = min;
                }
                final int size = iDanmakus.size();
                iDanmakus.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.CacheHandler.2
                    int orderInScreen = 0;
                    int currScreenIndex = 0;

                    @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        CacheManager cacheManager2;
                        if (CacheHandler.this.mPause || CacheHandler.this.mCancelFlag || last.getActualTime() < CacheManagingDrawTask.this.mTimer.currMillisecond) {
                            return 1;
                        }
                        IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                        if (drawingCache != null && drawingCache.get() != null) {
                            return 0;
                        }
                        if (!z && (baseDanmaku.isTimeOut() || !baseDanmaku.isOutside())) {
                            return 0;
                        }
                        if (!baseDanmaku.hasPassedFilter()) {
                            DanmakuContext danmakuContext = CacheManagingDrawTask.this.mContext;
                            danmakuContext.mDanmakuFilters.filter(baseDanmaku, this.orderInScreen, size, null, true, danmakuContext);
                        }
                        if (baseDanmaku.priority == 0 && baseDanmaku.isFiltered()) {
                            return 0;
                        }
                        if (baseDanmaku.getType() == 1) {
                            int actualTime2 = (int) ((baseDanmaku.getActualTime() - j) / CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                            if (this.currScreenIndex == actualTime2) {
                                this.orderInScreen++;
                            } else {
                                this.orderInScreen = 0;
                                this.currScreenIndex = actualTime2;
                            }
                        }
                        if (!z && !CacheHandler.this.mIsPlayerPause) {
                            try {
                                synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                                    CacheManagingDrawTask.this.mDrawingNotify.wait(j3);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return 1;
                            }
                        }
                        CacheHandler.this.buildCache(baseDanmaku, false);
                        if (!z) {
                            DanmakuFactory danmakuFactory = CacheManagingDrawTask.this.mContext.mDanmakuFactory;
                            if (SystemClock.uptimeMillis() - uptimeMillis >= cacheManager2.mScreenSize * 3800) {
                                return 1;
                            }
                        }
                        return 0;
                    }
                });
                long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                CacheManagingDrawTask.this.mCacheTimer.update(j2);
                return uptimeMillis2;
            }

            public boolean createCache(BaseDanmaku baseDanmaku) {
                DrawingCache drawingCache;
                if (!baseDanmaku.isMeasured()) {
                    baseDanmaku.measure(CacheManagingDrawTask.this.mDisp, true);
                }
                try {
                } catch (Exception unused) {
                    drawingCache = null;
                } catch (OutOfMemoryError unused2) {
                    drawingCache = null;
                }
                try {
                    drawingCache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, CacheManagingDrawTask.this.mDisp, CacheManager.this.mCachePool.acquire(), CacheManagingDrawTask.this.mContext.cachingPolicy.bitsPerPixelOfCache);
                    baseDanmaku.cache = drawingCache;
                    return true;
                } catch (Exception unused3) {
                    if (drawingCache != null) {
                        CacheManager.this.mCachePool.release(drawingCache);
                    }
                    baseDanmaku.cache = null;
                    return false;
                } catch (OutOfMemoryError unused4) {
                    if (drawingCache != null) {
                        CacheManager.this.mCachePool.release(drawingCache);
                    }
                    baseDanmaku.cache = null;
                    return false;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public byte buildCache(BaseDanmaku baseDanmaku, boolean z) {
                DrawingCache drawingCache;
                if (!baseDanmaku.isMeasured()) {
                    baseDanmaku.measure(CacheManagingDrawTask.this.mDisp, true);
                }
                try {
                    BaseDanmaku findReusableCache = CacheManager.this.findReusableCache(baseDanmaku, true, CacheManagingDrawTask.this.mContext.cachingPolicy.maxTimesOfStrictReusableFinds);
                    drawingCache = findReusableCache != null ? (DrawingCache) findReusableCache.cache : null;
                } catch (Exception unused) {
                    drawingCache = null;
                } catch (OutOfMemoryError unused2) {
                    drawingCache = null;
                }
                try {
                    if (drawingCache == null) {
                        BaseDanmaku findReusableCache2 = CacheManager.this.findReusableCache(baseDanmaku, false, CacheManagingDrawTask.this.mContext.cachingPolicy.maxTimesOfReusableFinds);
                        if (findReusableCache2 != null) {
                            drawingCache = (DrawingCache) findReusableCache2.cache;
                        }
                        if (drawingCache != null) {
                            findReusableCache2.cache = null;
                            baseDanmaku.cache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, CacheManagingDrawTask.this.mDisp, drawingCache, CacheManagingDrawTask.this.mContext.cachingPolicy.bitsPerPixelOfCache);
                            CacheManagingDrawTask.this.mCacheManager.push(baseDanmaku, 0, z);
                            return (byte) 0;
                        }
                        int cacheSize = DanmakuUtils.getCacheSize((int) baseDanmaku.paintWidth, (int) baseDanmaku.paintHeight, CacheManagingDrawTask.this.mContext.cachingPolicy.bitsPerPixelOfCache / 8);
                        if (cacheSize * 2 > CacheManagingDrawTask.this.mMaxCacheSize) {
                            return (byte) 1;
                        }
                        if (!z && CacheManager.this.mRealSize + cacheSize > CacheManager.this.mMaxSize) {
                            CacheManagingDrawTask.this.mCacheManager.clearTimeOutAndFilteredCaches(cacheSize, false);
                            return (byte) 1;
                        }
                        DrawingCache buildDanmakuDrawingCache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, CacheManagingDrawTask.this.mDisp, CacheManager.this.mCachePool.acquire(), CacheManagingDrawTask.this.mContext.cachingPolicy.bitsPerPixelOfCache);
                        baseDanmaku.cache = buildDanmakuDrawingCache;
                        boolean push = CacheManagingDrawTask.this.mCacheManager.push(baseDanmaku, CacheManager.this.sizeOf(baseDanmaku), z);
                        if (!push) {
                            releaseDanmakuCache(baseDanmaku, buildDanmakuDrawingCache);
                        }
                        return !push ? 1 : 0 ? (byte) 1 : (byte) 0;
                    }
                    drawingCache.increaseReference();
                    baseDanmaku.cache = drawingCache;
                    CacheManagingDrawTask.this.mCacheManager.push(baseDanmaku, 0, z);
                    return (byte) 0;
                } catch (Exception unused3) {
                    releaseDanmakuCache(baseDanmaku, drawingCache);
                    return (byte) 1;
                } catch (OutOfMemoryError unused4) {
                    releaseDanmakuCache(baseDanmaku, drawingCache);
                    return (byte) 1;
                }
            }

            private final void addDanmakuAndBuildCache(BaseDanmaku baseDanmaku) {
                if (!baseDanmaku.isTimeOut()) {
                    if (baseDanmaku.getActualTime() > CacheManagingDrawTask.this.mCacheTimer.currMillisecond + CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION && !baseDanmaku.isLive) {
                        return;
                    }
                    if (baseDanmaku.priority == 0 && baseDanmaku.isFiltered()) {
                        return;
                    }
                    IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                    if (drawingCache != null && drawingCache.get() != null) {
                        return;
                    }
                    buildCache(baseDanmaku, true);
                }
            }

            public void begin() {
                sendEmptyMessage(1);
                sendEmptyMessageDelayed(4, CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
            }

            public void pause() {
                this.mPause = true;
                sendEmptyMessage(6);
            }

            public void resume() {
                sendEmptyMessage(18);
                this.mPause = false;
                removeMessages(16);
                sendEmptyMessage(16);
                sendEmptyMessageDelayed(4, CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
            }

            public void requestBuildCacheAndDraw(long j) {
                removeMessages(3);
                this.mSeekedFlag = true;
                sendEmptyMessage(18);
                CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond + j);
                sendEmptyMessage(3);
            }

            public void onPlayStateChanged(boolean z) {
                this.mIsPlayerPause = !z;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeOutAndFilteredCaches(final int i, final boolean z) {
            this.mCaches.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.CacheManager.5
                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (!CacheManager.this.mEndFlag && CacheManager.this.mRealSize + i > CacheManager.this.mMaxSize) {
                        if (!baseDanmaku.isTimeOut() && !baseDanmaku.isFiltered()) {
                            return z ? 1 : 0;
                        }
                        CacheManager.this.entryRemoved(false, baseDanmaku, null);
                        return 2;
                    }
                    return 1;
                }
            });
        }

        public long getFirstCacheTime() {
            BaseDanmaku first;
            Danmakus danmakus = this.mCaches;
            if (danmakus == null || danmakus.size() <= 0 || (first = this.mCaches.first()) == null) {
                return 0L;
            }
            return first.getActualTime();
        }

        public void requestBuild(long j) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.requestBuildCacheAndDraw(j);
            }
        }

        public void requestClearAll() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.removeMessages(3);
            this.mHandler.removeMessages(18);
            this.mHandler.requestCancelCaching();
            this.mHandler.removeMessages(7);
            this.mHandler.sendEmptyMessage(7);
        }

        public void requestClearUnused() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.removeMessages(9);
            this.mHandler.sendEmptyMessage(9);
        }

        public void requestClearTimeout() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.removeMessages(4);
            this.mHandler.sendEmptyMessage(4);
        }

        public void post(Runnable runnable) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.post(runnable);
        }
    }

    @Override // master.flame.danmaku.controller.DrawTask
    public boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object... objArr) {
        CacheManager cacheManager;
        CacheManager cacheManager2;
        if (!super.handleOnDanmakuConfigChanged(danmakuContext, danmakuConfigTag, objArr)) {
            if (DanmakuContext.DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(danmakuConfigTag)) {
                this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
                requestClear();
            } else if (danmakuConfigTag.isVisibilityRelatedTag()) {
                if (objArr != null && objArr.length > 0 && objArr[0] != null && ((!(objArr[0] instanceof Boolean) || ((Boolean) objArr[0]).booleanValue()) && (cacheManager2 = this.mCacheManager) != null)) {
                    cacheManager2.requestBuild(0L);
                }
                requestClear();
            } else if (DanmakuContext.DanmakuConfigTag.TRANSPARENCY.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.SCALE_TEXTSIZE.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.DANMAKU_STYLE.equals(danmakuConfigTag)) {
                if (DanmakuContext.DanmakuConfigTag.SCALE_TEXTSIZE.equals(danmakuConfigTag)) {
                    this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
                }
                CacheManager cacheManager3 = this.mCacheManager;
                if (cacheManager3 != null) {
                    cacheManager3.requestClearAll();
                    this.mCacheManager.requestBuild(-this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                }
            } else {
                CacheManager cacheManager4 = this.mCacheManager;
                if (cacheManager4 != null) {
                    cacheManager4.requestClearUnused();
                    this.mCacheManager.requestBuild(0L);
                }
            }
        }
        if (this.mTaskListener == null || (cacheManager = this.mCacheManager) == null) {
            return true;
        }
        cacheManager.post(new Runnable() { // from class: master.flame.danmaku.controller.CacheManagingDrawTask.1
            @Override // java.lang.Runnable
            public void run() {
                CacheManagingDrawTask.this.mTaskListener.onDanmakuConfigChanged();
            }
        });
        return true;
    }
}
