package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public class FTDanmaku extends BaseDanmaku {
    private int mLastDispWidth;
    private float mLastLeft;
    private float mLastPaintWidth;

    /* renamed from: x */
    private float f6034x = 0.0f;

    /* renamed from: y */
    protected float f6035y = -1.0f;
    private float[] RECT = null;

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 5;
    }

    public FTDanmaku(Duration duration) {
        this.duration = duration;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f, float f2) {
        DanmakuTimer danmakuTimer = this.mTimer;
        if (danmakuTimer != null) {
            long actualTime = danmakuTimer.currMillisecond - getActualTime();
            if (actualTime > 0 && actualTime < this.duration.value) {
                if (isShown()) {
                    return;
                }
                this.f6034x = getLeft(iDisplayer);
                this.f6035y = f2;
                setVisibility(true);
                return;
            }
            setVisibility(false);
            this.f6035y = -1.0f;
            this.f6034x = iDisplayer.getWidth();
        }
    }

    protected float getLeft(IDisplayer iDisplayer) {
        if (this.mLastDispWidth == iDisplayer.getWidth() && this.mLastPaintWidth == this.paintWidth) {
            return this.mLastLeft;
        }
        float width = (iDisplayer.getWidth() - this.paintWidth) / 2.0f;
        this.mLastDispWidth = iDisplayer.getWidth();
        this.mLastPaintWidth = this.paintWidth;
        this.mLastLeft = width;
        return width;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j) {
        if (!isMeasured()) {
            return null;
        }
        float left = getLeft(iDisplayer);
        if (this.RECT == null) {
            this.RECT = new float[4];
        }
        float[] fArr = this.RECT;
        fArr[0] = left;
        float f = this.f6035y;
        fArr[1] = f;
        fArr[2] = left + this.paintWidth;
        fArr[3] = f + this.paintHeight;
        return fArr;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.f6034x;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.f6035y;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.f6034x + this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.f6035y + this.paintHeight;
    }
}
