package master.flame.danmaku.p144ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.DrawHelper;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.SpecialDanmaku;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

/* renamed from: master.flame.danmaku.ui.widget.FakeDanmakuView */
/* loaded from: classes4.dex */
public class FakeDanmakuView extends DanmakuView implements DrawHandler.Callback {
    private long mBeginTimeMills;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;
    private long mEndTimeMills;
    private boolean mIsRelease;
    private OnFrameAvailableListener mOnFrameAvailableListener;
    private DanmakuTimer mOuterTimer;
    private DanmakuTimer mTimer;
    private int mWidth = 0;
    private int mHeight = 0;
    private float mScale = 1.0f;
    private long mFrameIntervalMills = 16;
    private long mExpectBeginMills = 0;

    /* renamed from: master.flame.danmaku.ui.widget.FakeDanmakuView$OnFrameAvailableListener */
    /* loaded from: classes4.dex */
    public interface OnFrameAvailableListener {
        void onConfig(DanmakuContext danmakuContext);

        void onFailed(int i, String str);

        void onFrameAvailable(long j, Bitmap bitmap);

        void onFramesFinished(long j);
    }

    @Override // master.flame.danmaku.controller.DrawHandler.Callback
    public void danmakuShown(BaseDanmaku baseDanmaku) {
    }

    @Override // master.flame.danmaku.controller.DrawHandler.Callback
    public void drawingFinished() {
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView, android.view.View
    public boolean isShown() {
        return true;
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView, master.flame.danmaku.controller.IDanmakuViewController
    public boolean isViewReady() {
        return true;
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView, android.view.View
    protected void onDraw(Canvas canvas) {
    }

    @Override // master.flame.danmaku.controller.DrawHandler.Callback
    public void prepared() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: master.flame.danmaku.ui.widget.FakeDanmakuView$CustomParser */
    /* loaded from: classes4.dex */
    public class CustomParser extends BaseDanmakuParser {
        private final long edTime;
        private final BaseDanmakuParser mBaseParser;
        private float mDispScaleX;
        private float mDispScaleY;
        private int mViewWidth;
        private final long stTime;

        public CustomParser(FakeDanmakuView fakeDanmakuView, BaseDanmakuParser baseDanmakuParser, long j, long j2) {
            this.mBaseParser = baseDanmakuParser;
            this.stTime = j;
            this.edTime = j2;
        }

        @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
        protected IDanmakus parse() {
            IDanmakus danmakus;
            final Danmakus danmakus2 = new Danmakus();
            try {
                danmakus = this.mBaseParser.getDanmakus().subnew(this.stTime, this.edTime);
            } catch (Exception unused) {
                danmakus = this.mBaseParser.getDanmakus();
            }
            if (danmakus == null) {
                return danmakus2;
            }
            danmakus.forEach(new IDanmakus.Consumer<BaseDanmaku, Object>() { // from class: master.flame.danmaku.ui.widget.FakeDanmakuView.CustomParser.1
                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    long time = baseDanmaku.getTime();
                    if (time < CustomParser.this.stTime) {
                        return 0;
                    }
                    if (time > CustomParser.this.edTime) {
                        return 1;
                    }
                    BaseDanmaku createDanmaku = ((BaseDanmakuParser) CustomParser.this).mContext.mDanmakuFactory.createDanmaku(baseDanmaku.getType(), ((BaseDanmakuParser) CustomParser.this).mContext);
                    if (createDanmaku != null) {
                        createDanmaku.setTime(baseDanmaku.getTime());
                        DanmakuUtils.fillText(createDanmaku, baseDanmaku.text);
                        createDanmaku.textSize = baseDanmaku.textSize;
                        createDanmaku.textColor = baseDanmaku.textColor;
                        createDanmaku.textShadowColor = baseDanmaku.textShadowColor;
                        if (!(baseDanmaku instanceof SpecialDanmaku)) {
                            createDanmaku.setTimer(((BaseDanmakuParser) CustomParser.this).mTimer);
                            createDanmaku.mFilterParam = baseDanmaku.mFilterParam;
                            createDanmaku.filterResetFlag = baseDanmaku.filterResetFlag;
                            createDanmaku.flags = ((BaseDanmakuParser) CustomParser.this).mContext.mGlobalFlagValues;
                            synchronized (danmakus2.obtainSynchronizer()) {
                                danmakus2.addItem(createDanmaku);
                            }
                        } else {
                            SpecialDanmaku specialDanmaku = (SpecialDanmaku) baseDanmaku;
                            createDanmaku.index = baseDanmaku.index;
                            createDanmaku.duration = new Duration(specialDanmaku.getDuration());
                            createDanmaku.rotationZ = specialDanmaku.rotateZ;
                            createDanmaku.rotationY = specialDanmaku.rotationY;
                            ((SpecialDanmaku) createDanmaku).isQuadraticEaseOut = specialDanmaku.isQuadraticEaseOut;
                            ((BaseDanmakuParser) CustomParser.this).mContext.mDanmakuFactory.fillTranslationData(createDanmaku, specialDanmaku.beginX, specialDanmaku.beginY, specialDanmaku.endX, specialDanmaku.endY, specialDanmaku.translationDuration, specialDanmaku.translationStartDelay, CustomParser.this.mDispScaleX, CustomParser.this.mDispScaleY);
                            ((BaseDanmakuParser) CustomParser.this).mContext.mDanmakuFactory.fillAlphaData(createDanmaku, specialDanmaku.beginAlpha, specialDanmaku.endAlpha, createDanmaku.getDuration());
                            return 0;
                        }
                    }
                    return 0;
                }
            });
            return danmakus2;
        }

        @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
        public BaseDanmakuParser setDisplayer(IDisplayer iDisplayer) {
            super.setDisplayer(iDisplayer);
            BaseDanmakuParser baseDanmakuParser = this.mBaseParser;
            if (baseDanmakuParser != null && baseDanmakuParser.getDisplayer() != null) {
                this.mDispScaleX = this.mDispWidth / this.mBaseParser.getDisplayer().getWidth();
                this.mDispScaleY = this.mDispHeight / this.mBaseParser.getDisplayer().getHeight();
                if (this.mViewWidth <= 1) {
                    this.mViewWidth = iDisplayer.getWidth();
                }
            }
            return this;
        }

        @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser
        protected float getViewportSizeFactor() {
            return (((float) this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) * 1.1f) / (((float) (this.mViewWidth * 3800)) / 682.0f);
        }
    }

    public FakeDanmakuView(Context context) {
        super(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0097, code lost:
        if (r2 != null) goto L33;
     */
    @Override // master.flame.danmaku.p144ui.widget.DanmakuView, master.flame.danmaku.controller.IDanmakuViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long drawDanmakus() {
        Canvas canvas;
        Bitmap bitmap;
        if (!this.mIsRelease && (canvas = this.mBufferCanvas) != null && (bitmap = this.mBufferBitmap) != null && !bitmap.isRecycled()) {
            bitmap.eraseColor(0);
            if (this.mClearFlag) {
                DrawHelper.clearCanvas(canvas);
                this.mClearFlag = false;
            } else if (this.handler != null) {
                this.handler.draw(canvas);
            }
            OnFrameAvailableListener onFrameAvailableListener = this.mOnFrameAvailableListener;
            if (onFrameAvailableListener != null) {
                long j = this.mOuterTimer.currMillisecond;
                try {
                    try {
                        if (j >= this.mExpectBeginMills - this.mFrameIntervalMills) {
                            boolean z = true;
                            if (this.mScale == 1.0f) {
                                z = false;
                            } else {
                                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (this.mWidth * this.mScale), (int) (this.mHeight * this.mScale), true);
                            }
                            onFrameAvailableListener.onFrameAvailable(j, bitmap);
                            if (z) {
                                bitmap.recycle();
                            }
                        }
                    } catch (Exception e) {
                        release();
                        onFrameAvailableListener.onFailed(101, e.getMessage());
                        if (j >= this.mEndTimeMills) {
                            release();
                            DanmakuTimer danmakuTimer = this.mTimer;
                        }
                    }
                } finally {
                    if (j >= this.mEndTimeMills) {
                        release();
                        DanmakuTimer danmakuTimer2 = this.mTimer;
                        if (danmakuTimer2 != null) {
                            danmakuTimer2.update(this.mEndTimeMills);
                        }
                        onFrameAvailableListener.onFramesFinished(j);
                    }
                }
            }
            this.mRequestRender = false;
            return 2L;
        }
        return 0L;
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView
    public void release() {
        this.mIsRelease = true;
        super.release();
        this.mBufferBitmap = null;
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView, master.flame.danmaku.controller.IDanmakuViewController
    public int getViewWidth() {
        return this.mWidth;
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView, master.flame.danmaku.controller.IDanmakuViewController
    public int getViewHeight() {
        return this.mHeight;
    }

    @Override // master.flame.danmaku.p144ui.widget.DanmakuView
    public void prepare(BaseDanmakuParser baseDanmakuParser, DanmakuContext danmakuContext) {
        DanmakuContext danmakuContext2;
        CustomParser customParser = new CustomParser(this, baseDanmakuParser, this.mBeginTimeMills, this.mEndTimeMills);
        try {
            danmakuContext2 = (DanmakuContext) danmakuContext.clone();
            danmakuContext2.resetContext();
            danmakuContext2.transparency = AlphaValue.MAX;
            danmakuContext2.setDanmakuTransparency(danmakuContext.transparency / AlphaValue.MAX);
            danmakuContext2.mGlobalFlagValues.FILTER_RESET_FLAG = danmakuContext.mGlobalFlagValues.FILTER_RESET_FLAG;
            danmakuContext2.setDanmakuSync(null);
            danmakuContext2.unregisterAllConfigChangedCallbacks();
            danmakuContext2.mGlobalFlagValues.updateAll();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            danmakuContext2 = danmakuContext;
        }
        danmakuContext2.updateMethod = (byte) 1;
        OnFrameAvailableListener onFrameAvailableListener = this.mOnFrameAvailableListener;
        if (onFrameAvailableListener != null) {
            onFrameAvailableListener.onConfig(danmakuContext2);
        }
        super.prepare(customParser, danmakuContext2);
        this.handler.setIdleSleep(false);
        this.handler.enableNonBlockMode(true);
    }

    public void setTimeRange(long j, long j2) {
        this.mExpectBeginMills = j;
        this.mBeginTimeMills = Math.max(0L, j - 30000);
        this.mEndTimeMills = j2;
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener onFrameAvailableListener) {
        this.mOnFrameAvailableListener = onFrameAvailableListener;
    }

    @Override // master.flame.danmaku.controller.DrawHandler.Callback
    public void updateTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
        danmakuTimer.update(this.mOuterTimer.currMillisecond);
        this.mOuterTimer.add(this.mFrameIntervalMills);
        danmakuTimer.add(this.mFrameIntervalMills);
    }
}
