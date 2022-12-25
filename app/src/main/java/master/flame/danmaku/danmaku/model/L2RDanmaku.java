package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public class L2RDanmaku extends R2LDanmaku {
    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 6;
    }

    public L2RDanmaku(Duration duration) {
        super(duration);
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f, float f2) {
        DanmakuTimer danmakuTimer = this.mTimer;
        if (danmakuTimer != null) {
            long j = danmakuTimer.currMillisecond;
            long actualTime = j - getActualTime();
            if (actualTime > 0 && actualTime < this.duration.value) {
                this.f6036x = getAccurateLeft(iDisplayer, j);
                if (isShown()) {
                    return;
                }
                this.f6037y = f2;
                setVisibility(true);
                return;
            }
        }
        setVisibility(false);
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j) {
        if (!isMeasured()) {
            return null;
        }
        float accurateLeft = getAccurateLeft(iDisplayer, j);
        if (this.RECT == null) {
            this.RECT = new float[4];
        }
        float[] fArr = this.RECT;
        fArr[0] = accurateLeft;
        float f = this.f6037y;
        fArr[1] = f;
        fArr[2] = accurateLeft + this.paintWidth;
        fArr[3] = f + this.paintHeight;
        return fArr;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku
    protected float getAccurateLeft(IDisplayer iDisplayer, long j) {
        long actualTime = j - getActualTime();
        if (actualTime >= this.duration.value) {
            return iDisplayer.getWidth();
        }
        return (this.mStepX * ((float) actualTime)) - this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.f6036x;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.f6037y;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.f6036x + this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.f6037y + this.paintHeight;
    }
}
