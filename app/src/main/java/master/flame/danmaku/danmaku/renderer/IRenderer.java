package master.flame.danmaku.danmaku.renderer;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.ICacheManager;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.Danmakus;

/* loaded from: classes4.dex */
public interface IRenderer {

    /* loaded from: classes4.dex */
    public interface OnDanmakuShownListener {
        void onDanmakuShown(BaseDanmaku baseDanmaku);
    }

    void alignBottom(boolean z);

    void clear();

    void clearRetainer();

    void draw(IDisplayer iDisplayer, IDanmakus iDanmakus, long j, RenderingState renderingState);

    void release();

    void setCacheManager(ICacheManager iCacheManager);

    void setOnDanmakuShownListener(OnDanmakuShownListener onDanmakuShownListener);

    void setVerifierEnabled(boolean z);

    /* loaded from: classes4.dex */
    public static class RenderingState {
        public long beginTime;
        public long cacheHitCount;
        public long cacheMissCount;
        public long consumingTime;
        public long endTime;
        public int fbDanmakuCount;
        public int ftDanmakuCount;
        public int indexInScreen;
        public boolean isRunningDanmakus;
        public int l2rDanmakuCount;
        public BaseDanmaku lastDanmaku;
        public int lastTotalDanmakuCount;
        private boolean mIsObtaining;
        public boolean nothingRendered;
        public int r2lDanmakuCount;
        public int specialDanmakuCount;
        public long sysTime;
        public int totalDanmakuCount;
        public int totalSizeInScreen;
        public DanmakuTimer timer = new DanmakuTimer();
        private IDanmakus runningDanmakus = new Danmakus(4);

        public int addTotalCount(int i) {
            this.totalDanmakuCount += i;
            return this.totalDanmakuCount;
        }

        public int addCount(int i, int i2) {
            if (i == 1) {
                this.r2lDanmakuCount += i2;
                return this.r2lDanmakuCount;
            } else if (i == 4) {
                this.fbDanmakuCount += i2;
                return this.fbDanmakuCount;
            } else if (i == 5) {
                this.ftDanmakuCount += i2;
                return this.ftDanmakuCount;
            } else if (i == 6) {
                this.l2rDanmakuCount += i2;
                return this.l2rDanmakuCount;
            } else if (i != 7) {
                return 0;
            } else {
                this.specialDanmakuCount += i2;
                return this.specialDanmakuCount;
            }
        }

        public void reset() {
            this.lastTotalDanmakuCount = this.totalDanmakuCount;
            this.totalDanmakuCount = 0;
            this.specialDanmakuCount = 0;
            this.fbDanmakuCount = 0;
            this.ftDanmakuCount = 0;
            this.l2rDanmakuCount = 0;
            this.r2lDanmakuCount = 0;
            this.consumingTime = 0L;
            this.endTime = 0L;
            this.beginTime = 0L;
            this.sysTime = 0L;
            this.nothingRendered = false;
            synchronized (this) {
                this.runningDanmakus.clear();
            }
        }

        public void set(RenderingState renderingState) {
            if (renderingState == null) {
                return;
            }
            this.lastTotalDanmakuCount = renderingState.lastTotalDanmakuCount;
            this.r2lDanmakuCount = renderingState.r2lDanmakuCount;
            this.l2rDanmakuCount = renderingState.l2rDanmakuCount;
            this.ftDanmakuCount = renderingState.ftDanmakuCount;
            this.fbDanmakuCount = renderingState.fbDanmakuCount;
            this.specialDanmakuCount = renderingState.specialDanmakuCount;
            this.totalDanmakuCount = renderingState.totalDanmakuCount;
            this.consumingTime = renderingState.consumingTime;
            this.beginTime = renderingState.beginTime;
            this.endTime = renderingState.endTime;
            this.nothingRendered = renderingState.nothingRendered;
            this.sysTime = renderingState.sysTime;
            this.cacheHitCount = renderingState.cacheHitCount;
            this.cacheMissCount = renderingState.cacheMissCount;
        }

        public void appendToRunningDanmakus(BaseDanmaku baseDanmaku) {
            if (!this.mIsObtaining) {
                this.runningDanmakus.addItem(baseDanmaku);
            }
        }

        public IDanmakus obtainRunningDanmakus() {
            IDanmakus iDanmakus;
            this.mIsObtaining = true;
            synchronized (this) {
                iDanmakus = this.runningDanmakus;
                this.runningDanmakus = new Danmakus(4);
            }
            this.mIsObtaining = false;
            return iDanmakus;
        }
    }
}
