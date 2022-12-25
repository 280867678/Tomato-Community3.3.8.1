package master.flame.danmaku.danmaku.renderer.android;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes4.dex */
public class DanmakusRetainer {
    private IDanmakusRetainer rldrInstance = null;
    private IDanmakusRetainer lrdrInstance = null;
    private IDanmakusRetainer ftdrInstance = null;
    private IDanmakusRetainer fbdrInstance = null;

    /* loaded from: classes4.dex */
    public interface IDanmakusRetainer {
        void clear();

        void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier);
    }

    /* loaded from: classes4.dex */
    public interface Verifier {
        boolean skipLayout(BaseDanmaku baseDanmaku, float f, int i, boolean z);
    }

    public DanmakusRetainer(boolean z) {
        alignBottom(z);
    }

    public void alignBottom(boolean z) {
        this.rldrInstance = z ? new AlignBottomRetainer() : new AlignTopRetainer();
        this.lrdrInstance = z ? new AlignBottomRetainer() : new AlignTopRetainer();
        if (this.ftdrInstance == null) {
            this.ftdrInstance = new FTDanmakusRetainer();
        }
        if (this.fbdrInstance == null) {
            this.fbdrInstance = new AlignBottomRetainer();
        }
    }

    public void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier) {
        int type = baseDanmaku.getType();
        if (type == 1) {
            this.rldrInstance.fix(baseDanmaku, iDisplayer, verifier);
        } else if (type == 4) {
            this.fbdrInstance.fix(baseDanmaku, iDisplayer, verifier);
        } else if (type == 5) {
            this.ftdrInstance.fix(baseDanmaku, iDisplayer, verifier);
        } else if (type == 6) {
            this.lrdrInstance.fix(baseDanmaku, iDisplayer, verifier);
        } else if (type != 7) {
        } else {
            baseDanmaku.layout(iDisplayer, 0.0f, 0.0f);
        }
    }

    public void clear() {
        IDanmakusRetainer iDanmakusRetainer = this.rldrInstance;
        if (iDanmakusRetainer != null) {
            iDanmakusRetainer.clear();
        }
        IDanmakusRetainer iDanmakusRetainer2 = this.lrdrInstance;
        if (iDanmakusRetainer2 != null) {
            iDanmakusRetainer2.clear();
        }
        IDanmakusRetainer iDanmakusRetainer3 = this.ftdrInstance;
        if (iDanmakusRetainer3 != null) {
            iDanmakusRetainer3.clear();
        }
        IDanmakusRetainer iDanmakusRetainer4 = this.fbdrInstance;
        if (iDanmakusRetainer4 != null) {
            iDanmakusRetainer4.clear();
        }
    }

    public void release() {
        clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class RetainerState {
        public BaseDanmaku firstItem;
        public BaseDanmaku insertItem;
        public BaseDanmaku lastItem;
        public int lines;
        public BaseDanmaku minRightRow;
        public boolean overwriteInsert;
        public BaseDanmaku removeItem;
        public boolean shown;
        public boolean willHit;

        private RetainerState() {
            this.lines = 0;
            this.insertItem = null;
            this.firstItem = null;
            this.lastItem = null;
            this.minRightRow = null;
            this.removeItem = null;
            this.overwriteInsert = false;
            this.shown = false;
            this.willHit = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class AlignTopRetainer implements IDanmakusRetainer {
        protected boolean mCancelFixingFlag;
        protected RetainerConsumer mConsumer;
        protected Danmakus mVisibleDanmakus;

        /* loaded from: classes4.dex */
        protected class RetainerConsumer extends IDanmakus.Consumer<BaseDanmaku, RetainerState> {
            public IDisplayer disp;
            int lines = 0;
            public BaseDanmaku insertItem = null;
            public BaseDanmaku firstItem = null;
            public BaseDanmaku lastItem = null;
            public BaseDanmaku minRightRow = null;
            public BaseDanmaku drawItem = null;
            boolean overwriteInsert = false;
            boolean shown = false;
            boolean willHit = false;

            protected RetainerConsumer() {
            }

            @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
            public void before() {
                this.lines = 0;
                this.minRightRow = null;
                this.lastItem = null;
                this.firstItem = null;
                this.insertItem = null;
                this.willHit = false;
                this.shown = false;
                this.overwriteInsert = false;
            }

            @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
            public int accept(BaseDanmaku baseDanmaku) {
                if (AlignTopRetainer.this.mCancelFixingFlag) {
                    return 1;
                }
                this.lines++;
                if (baseDanmaku == this.drawItem) {
                    this.insertItem = baseDanmaku;
                    this.lastItem = null;
                    this.shown = true;
                    this.willHit = false;
                    return 1;
                }
                if (this.firstItem == null) {
                    this.firstItem = baseDanmaku;
                }
                if (this.drawItem.paintHeight + baseDanmaku.getTop() > this.disp.getHeight()) {
                    this.overwriteInsert = true;
                    return 1;
                }
                BaseDanmaku baseDanmaku2 = this.minRightRow;
                if (baseDanmaku2 == null) {
                    this.minRightRow = baseDanmaku;
                } else if (baseDanmaku2.getRight() >= baseDanmaku.getRight()) {
                    this.minRightRow = baseDanmaku;
                }
                IDisplayer iDisplayer = this.disp;
                BaseDanmaku baseDanmaku3 = this.drawItem;
                this.willHit = DanmakuUtils.willHitInDuration(iDisplayer, baseDanmaku, baseDanmaku3, baseDanmaku3.getDuration(), this.drawItem.getTimer().currMillisecond);
                if (!this.willHit) {
                    this.insertItem = baseDanmaku;
                    return 1;
                }
                this.lastItem = baseDanmaku;
                return 0;
            }

            public RetainerState result() {
                RetainerState retainerState = new RetainerState();
                retainerState.lines = this.lines;
                retainerState.firstItem = this.firstItem;
                retainerState.insertItem = this.insertItem;
                retainerState.lastItem = this.lastItem;
                retainerState.minRightRow = this.minRightRow;
                retainerState.overwriteInsert = this.overwriteInsert;
                retainerState.shown = this.shown;
                retainerState.willHit = this.willHit;
                return retainerState;
            }
        }

        private AlignTopRetainer() {
            this.mVisibleDanmakus = new Danmakus(1);
            this.mCancelFixingFlag = false;
            this.mConsumer = new RetainerConsumer();
        }

        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier) {
            float f;
            BaseDanmaku baseDanmaku2;
            boolean z;
            boolean z2;
            boolean z3;
            BaseDanmaku baseDanmaku3;
            BaseDanmaku baseDanmaku4;
            BaseDanmaku baseDanmaku5;
            BaseDanmaku baseDanmaku6;
            boolean z4;
            int i;
            boolean z5;
            boolean z6;
            boolean z7;
            if (baseDanmaku.isOutside()) {
                return;
            }
            float allMarginTop = iDisplayer.getAllMarginTop();
            boolean isShown = baseDanmaku.isShown();
            int i2 = 1;
            boolean z8 = false;
            boolean z9 = !isShown && !this.mVisibleDanmakus.isEmpty();
            int margin = iDisplayer.getMargin();
            if (!isShown) {
                this.mCancelFixingFlag = false;
                RetainerConsumer retainerConsumer = this.mConsumer;
                retainerConsumer.disp = iDisplayer;
                retainerConsumer.drawItem = baseDanmaku;
                this.mVisibleDanmakus.forEachSync(retainerConsumer);
                RetainerState result = this.mConsumer.result();
                if (result != null) {
                    int i3 = result.lines;
                    baseDanmaku3 = result.insertItem;
                    baseDanmaku4 = result.firstItem;
                    baseDanmaku5 = result.lastItem;
                    baseDanmaku6 = result.minRightRow;
                    boolean z10 = result.overwriteInsert;
                    z2 = result.shown;
                    z3 = result.willHit;
                    i = i3;
                    z4 = z10;
                } else {
                    z2 = isShown;
                    z3 = z9;
                    baseDanmaku3 = null;
                    baseDanmaku4 = null;
                    baseDanmaku5 = null;
                    baseDanmaku6 = null;
                    z4 = false;
                    i = 0;
                }
                if (baseDanmaku3 != null) {
                    if (baseDanmaku5 != null) {
                        f = baseDanmaku5.getBottom() + margin;
                    } else {
                        f = baseDanmaku3.getTop();
                    }
                    z5 = z3;
                    if (baseDanmaku3 != baseDanmaku) {
                        baseDanmaku2 = baseDanmaku3;
                        z7 = true;
                        z6 = false;
                    }
                    z6 = z2;
                    z7 = true;
                    baseDanmaku2 = null;
                } else if (z4 && baseDanmaku6 != null) {
                    z5 = z3;
                    f = baseDanmaku6.getTop();
                    baseDanmaku2 = null;
                    z7 = false;
                    z6 = false;
                } else if (baseDanmaku5 != null) {
                    f = baseDanmaku5.getBottom() + margin;
                    z6 = z2;
                    z7 = true;
                    z5 = false;
                    baseDanmaku2 = null;
                } else if (baseDanmaku4 != null) {
                    z5 = z3;
                    f = baseDanmaku4.getTop();
                    baseDanmaku2 = baseDanmaku4;
                    z7 = true;
                    z6 = false;
                } else {
                    z5 = z3;
                    f = iDisplayer.getAllMarginTop();
                    z6 = z2;
                    z7 = true;
                    baseDanmaku2 = null;
                }
                z = z7 ? isOutVerticalEdge(z4, baseDanmaku, iDisplayer, f, baseDanmaku4, baseDanmaku5) : false;
                if (z) {
                    f = iDisplayer.getAllMarginTop();
                    z9 = true;
                } else {
                    i2 = baseDanmaku2 != null ? i - 1 : i;
                    z9 = z5;
                }
                if (f != iDisplayer.getAllMarginTop()) {
                    z8 = z6;
                }
            } else {
                f = allMarginTop;
                z8 = isShown;
                baseDanmaku2 = null;
                z = false;
                i2 = 0;
            }
            if (verifier != null && verifier.skipLayout(baseDanmaku, f, i2, z9)) {
                return;
            }
            if (z) {
                clear();
            }
            baseDanmaku.layout(iDisplayer, baseDanmaku.getLeft(), f);
            if (z8) {
                return;
            }
            this.mVisibleDanmakus.removeItem(baseDanmaku2);
            this.mVisibleDanmakus.addItem(baseDanmaku);
        }

        protected boolean isOutVerticalEdge(boolean z, BaseDanmaku baseDanmaku, IDisplayer iDisplayer, float f, BaseDanmaku baseDanmaku2, BaseDanmaku baseDanmaku3) {
            if (f >= iDisplayer.getAllMarginTop()) {
                return (baseDanmaku2 != null && baseDanmaku2.getTop() > 0.0f) || f + baseDanmaku.paintHeight > ((float) iDisplayer.getHeight());
            }
            return true;
        }

        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void clear() {
            this.mCancelFixingFlag = true;
            this.mVisibleDanmakus.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class FTDanmakusRetainer extends AlignTopRetainer {
        private FTDanmakusRetainer() {
            super();
        }

        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer
        protected boolean isOutVerticalEdge(boolean z, BaseDanmaku baseDanmaku, IDisplayer iDisplayer, float f, BaseDanmaku baseDanmaku2, BaseDanmaku baseDanmaku3) {
            return f + baseDanmaku.paintHeight > ((float) iDisplayer.getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class AlignBottomRetainer extends FTDanmakusRetainer {
        protected RetainerConsumer mConsumer;
        protected Danmakus mVisibleDanmakus;

        /* loaded from: classes4.dex */
        protected class RetainerConsumer extends IDanmakus.Consumer<BaseDanmaku, RetainerState> {
            public IDisplayer disp;
            float topPos;
            int lines = 0;
            public BaseDanmaku removeItem = null;
            public BaseDanmaku firstItem = null;
            public BaseDanmaku drawItem = null;
            boolean willHit = false;

            protected RetainerConsumer() {
            }

            @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
            public void before() {
                this.lines = 0;
                this.firstItem = null;
                this.removeItem = null;
                this.willHit = false;
            }

            @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
            public int accept(BaseDanmaku baseDanmaku) {
                if (AlignBottomRetainer.this.mCancelFixingFlag) {
                    return 1;
                }
                this.lines++;
                if (baseDanmaku == this.drawItem) {
                    this.removeItem = null;
                    this.willHit = false;
                    return 1;
                }
                if (this.firstItem == null) {
                    this.firstItem = baseDanmaku;
                    if (this.firstItem.getBottom() != this.disp.getHeight()) {
                        return 1;
                    }
                }
                if (this.topPos < this.disp.getAllMarginTop()) {
                    this.removeItem = null;
                    return 1;
                }
                IDisplayer iDisplayer = this.disp;
                BaseDanmaku baseDanmaku2 = this.drawItem;
                this.willHit = DanmakuUtils.willHitInDuration(iDisplayer, baseDanmaku, baseDanmaku2, baseDanmaku2.getDuration(), this.drawItem.getTimer().currMillisecond);
                if (!this.willHit) {
                    this.removeItem = baseDanmaku;
                    return 1;
                }
                this.topPos = (baseDanmaku.getTop() - this.disp.getMargin()) - this.drawItem.paintHeight;
                return 0;
            }

            public RetainerState result() {
                RetainerState retainerState = new RetainerState();
                retainerState.lines = this.lines;
                retainerState.firstItem = this.firstItem;
                retainerState.removeItem = this.removeItem;
                retainerState.willHit = this.willHit;
                return retainerState;
            }
        }

        private AlignBottomRetainer() {
            super();
            this.mConsumer = new RetainerConsumer();
            this.mVisibleDanmakus = new Danmakus(2);
        }

        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer, master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier) {
            boolean z;
            float f;
            BaseDanmaku baseDanmaku2;
            boolean z2;
            boolean z3;
            BaseDanmaku baseDanmaku3;
            int i;
            if (baseDanmaku.isOutside()) {
                return;
            }
            boolean isShown = baseDanmaku.isShown();
            float top2 = isShown ? baseDanmaku.getTop() : -1.0f;
            boolean z4 = true;
            int i2 = 0;
            boolean z5 = !isShown && !this.mVisibleDanmakus.isEmpty();
            if (top2 < iDisplayer.getAllMarginTop()) {
                top2 = iDisplayer.getHeight() - baseDanmaku.paintHeight;
            }
            if (!isShown) {
                this.mCancelFixingFlag = false;
                RetainerConsumer retainerConsumer = this.mConsumer;
                retainerConsumer.topPos = top2;
                retainerConsumer.disp = iDisplayer;
                retainerConsumer.drawItem = baseDanmaku;
                this.mVisibleDanmakus.forEachSync(retainerConsumer);
                RetainerState result = this.mConsumer.result();
                f = this.mConsumer.topPos;
                if (result != null) {
                    int i3 = result.lines;
                    BaseDanmaku baseDanmaku4 = result.firstItem;
                    BaseDanmaku baseDanmaku5 = result.removeItem;
                    boolean z6 = result.shown;
                    i = i3;
                    z3 = result.willHit;
                    baseDanmaku3 = baseDanmaku4;
                    baseDanmaku2 = baseDanmaku5;
                    z = z6;
                } else {
                    z = isShown;
                    z3 = z5;
                    baseDanmaku3 = null;
                    baseDanmaku2 = null;
                    i = 0;
                }
                z2 = isOutVerticalEdge(false, baseDanmaku, iDisplayer, f, baseDanmaku3, null);
                if (z2) {
                    f = iDisplayer.getHeight() - baseDanmaku.paintHeight;
                    i2 = 1;
                } else {
                    z4 = f >= ((float) iDisplayer.getAllMarginTop()) ? false : z3;
                    i2 = baseDanmaku2 != null ? i - 1 : i;
                }
            } else {
                z = isShown;
                f = top2;
                z4 = z5;
                baseDanmaku2 = null;
                z2 = false;
            }
            if (verifier != null && verifier.skipLayout(baseDanmaku, f, i2, z4)) {
                return;
            }
            if (z2) {
                clear();
            }
            baseDanmaku.layout(iDisplayer, baseDanmaku.getLeft(), f);
            if (z) {
                return;
            }
            this.mVisibleDanmakus.removeItem(baseDanmaku2);
            this.mVisibleDanmakus.addItem(baseDanmaku);
        }

        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.FTDanmakusRetainer, master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer
        protected boolean isOutVerticalEdge(boolean z, BaseDanmaku baseDanmaku, IDisplayer iDisplayer, float f, BaseDanmaku baseDanmaku2, BaseDanmaku baseDanmaku3) {
            if (f >= iDisplayer.getAllMarginTop()) {
                return (baseDanmaku2 == null || baseDanmaku2.getBottom() == ((float) iDisplayer.getHeight())) ? false : true;
            }
            return true;
        }

        @Override // master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.AlignTopRetainer, master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.IDanmakusRetainer
        public void clear() {
            this.mCancelFixingFlag = true;
            this.mVisibleDanmakus.clear();
        }
    }
}
