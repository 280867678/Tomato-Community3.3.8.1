package master.flame.danmaku.danmaku.renderer.android;

import master.flame.danmaku.controller.DanmakuFilters;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.ICacheManager;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.IDrawingCache;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.renderer.Renderer;
import master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer;

/* loaded from: classes4.dex */
public class DanmakuRenderer extends Renderer {
    private ICacheManager mCacheManager;
    private final DanmakuContext mContext;
    private final DanmakusRetainer mDanmakusRetainer;
    private IRenderer.OnDanmakuShownListener mOnDanmakuShownListener;
    private DanmakuTimer mStartTimer;
    private DanmakusRetainer.Verifier mVerifier;
    private final DanmakusRetainer.Verifier verifier = new DanmakusRetainer.Verifier() { // from class: master.flame.danmaku.danmaku.renderer.android.DanmakuRenderer.1
        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.Verifier
        public boolean skipLayout(BaseDanmaku baseDanmaku, float f, int i, boolean z) {
            if (baseDanmaku.priority != 0 || !DanmakuRenderer.this.mContext.mDanmakuFilters.filterSecondary(baseDanmaku, i, 0, DanmakuRenderer.this.mStartTimer, z, DanmakuRenderer.this.mContext)) {
                return false;
            }
            baseDanmaku.setVisibility(false);
            return true;
        }
    };
    private Consumer mConsumer = new Consumer();

    /* loaded from: classes4.dex */
    private class Consumer extends IDanmakus.DefaultConsumer<BaseDanmaku> {
        public IDisplayer disp;
        private BaseDanmaku lastItem;
        public IRenderer.RenderingState renderingState;
        public long startRenderTime;

        private Consumer() {
        }

        @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
        public int accept(BaseDanmaku baseDanmaku) {
            this.lastItem = baseDanmaku;
            if (baseDanmaku.isTimeOut()) {
                this.disp.recycle(baseDanmaku);
                return this.renderingState.isRunningDanmakus ? 2 : 0;
            } else if (!this.renderingState.isRunningDanmakus && baseDanmaku.isOffset()) {
                return 0;
            } else {
                if (!baseDanmaku.hasPassedFilter()) {
                    DanmakuFilters danmakuFilters = DanmakuRenderer.this.mContext.mDanmakuFilters;
                    IRenderer.RenderingState renderingState = this.renderingState;
                    danmakuFilters.filter(baseDanmaku, renderingState.indexInScreen, renderingState.totalSizeInScreen, renderingState.timer, false, DanmakuRenderer.this.mContext);
                }
                if (baseDanmaku.getActualTime() >= this.startRenderTime && (baseDanmaku.priority != 0 || !baseDanmaku.isFiltered())) {
                    if (baseDanmaku.isLate()) {
                        IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                        if (DanmakuRenderer.this.mCacheManager != null && (drawingCache == null || drawingCache.get() == null)) {
                            DanmakuRenderer.this.mCacheManager.addDanmaku(baseDanmaku);
                        }
                        return 1;
                    }
                    if (baseDanmaku.getType() == 1) {
                        this.renderingState.indexInScreen++;
                    }
                    if (!baseDanmaku.isMeasured()) {
                        baseDanmaku.measure(this.disp, false);
                    }
                    if (!baseDanmaku.isPrepared()) {
                        baseDanmaku.prepare(this.disp, false);
                    }
                    DanmakuRenderer.this.mDanmakusRetainer.fix(baseDanmaku, this.disp, DanmakuRenderer.this.mVerifier);
                    if (!baseDanmaku.isShown() || (baseDanmaku.lines == null && baseDanmaku.getBottom() > this.disp.getHeight())) {
                        return 0;
                    }
                    int draw = baseDanmaku.draw(this.disp);
                    if (draw == 1) {
                        this.renderingState.cacheHitCount++;
                    } else if (draw == 2) {
                        this.renderingState.cacheMissCount++;
                        if (DanmakuRenderer.this.mCacheManager != null) {
                            DanmakuRenderer.this.mCacheManager.addDanmaku(baseDanmaku);
                        }
                    }
                    this.renderingState.addCount(baseDanmaku.getType(), 1);
                    this.renderingState.addTotalCount(1);
                    this.renderingState.appendToRunningDanmakus(baseDanmaku);
                    if (DanmakuRenderer.this.mOnDanmakuShownListener != null && baseDanmaku.firstShownFlag != DanmakuRenderer.this.mContext.mGlobalFlagValues.FIRST_SHOWN_RESET_FLAG) {
                        baseDanmaku.firstShownFlag = DanmakuRenderer.this.mContext.mGlobalFlagValues.FIRST_SHOWN_RESET_FLAG;
                        DanmakuRenderer.this.mOnDanmakuShownListener.onDanmakuShown(baseDanmaku);
                    }
                }
                return 0;
            }
        }

        @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
        public void after() {
            this.renderingState.lastDanmaku = this.lastItem;
            super.after();
        }
    }

    public DanmakuRenderer(DanmakuContext danmakuContext) {
        this.mContext = danmakuContext;
        this.mDanmakusRetainer = new DanmakusRetainer(danmakuContext.isAlignBottom());
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void clear() {
        clearRetainer();
        this.mContext.mDanmakuFilters.clear();
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void clearRetainer() {
        this.mDanmakusRetainer.clear();
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void release() {
        this.mDanmakusRetainer.release();
        this.mContext.mDanmakuFilters.clear();
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void setVerifierEnabled(boolean z) {
        this.mVerifier = z ? this.verifier : null;
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void draw(IDisplayer iDisplayer, IDanmakus iDanmakus, long j, IRenderer.RenderingState renderingState) {
        this.mStartTimer = renderingState.timer;
        Consumer consumer = this.mConsumer;
        consumer.disp = iDisplayer;
        consumer.renderingState = renderingState;
        consumer.startRenderTime = j;
        iDanmakus.forEachSync(consumer);
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void setCacheManager(ICacheManager iCacheManager) {
        this.mCacheManager = iCacheManager;
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void setOnDanmakuShownListener(IRenderer.OnDanmakuShownListener onDanmakuShownListener) {
        this.mOnDanmakuShownListener = onDanmakuShownListener;
    }

    @Override // master.flame.danmaku.danmaku.renderer.IRenderer
    public void alignBottom(boolean z) {
        DanmakusRetainer danmakusRetainer = this.mDanmakusRetainer;
        if (danmakusRetainer != null) {
            danmakusRetainer.alignBottom(z);
        }
    }
}
