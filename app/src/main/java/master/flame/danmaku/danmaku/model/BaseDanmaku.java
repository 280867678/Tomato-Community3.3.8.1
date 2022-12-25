package master.flame.danmaku.danmaku.model;

import android.util.SparseArray;

/* loaded from: classes4.dex */
public abstract class BaseDanmaku {
    public IDrawingCache<?> cache;
    public Duration duration;
    public boolean forceBuildCacheInSameThread;
    public int index;
    public boolean isGuest;
    public boolean isLive;
    public String[] lines;
    protected DanmakuTimer mTimer;
    public float rotationY;
    public float rotationZ;
    public Object tag;
    public CharSequence text;
    public int textColor;
    public int textShadowColor;
    private long time;
    public long timeOffset;
    public String userHash;
    public int visibility;
    public int underlineColor = 0;
    public float textSize = -1.0f;
    public int borderColor = 0;
    public int padding = 0;
    public byte priority = 0;
    public float paintWidth = -1.0f;
    public float paintHeight = -1.0f;
    private int visibleResetFlag = 0;
    public int measureResetFlag = 0;
    public int syncTimeOffsetResetFlag = 0;
    public int prepareResetFlag = -1;
    public int userId = 0;
    protected int alpha = AlphaValue.MAX;
    public int mFilterParam = 0;
    public int filterResetFlag = -1;
    public GlobalFlagValues flags = null;
    public int requestFlags = 0;
    public int firstShownFlag = -1;

    public abstract float getBottom();

    public abstract float getLeft();

    public abstract float[] getRectAtTime(IDisplayer iDisplayer, long j);

    public abstract float getRight();

    public abstract float getTop();

    public abstract int getType();

    public abstract void layout(IDisplayer iDisplayer, float f, float f2);

    public BaseDanmaku() {
        new SparseArray();
    }

    public long getDuration() {
        return this.duration.value;
    }

    public int draw(IDisplayer iDisplayer) {
        return iDisplayer.draw(this);
    }

    public boolean isMeasured() {
        return this.paintWidth > -1.0f && this.paintHeight > -1.0f && this.measureResetFlag == this.flags.MEASURE_RESET_FLAG;
    }

    public void measure(IDisplayer iDisplayer, boolean z) {
        iDisplayer.measure(this, z);
        this.measureResetFlag = this.flags.MEASURE_RESET_FLAG;
    }

    public boolean isPrepared() {
        return this.prepareResetFlag == this.flags.PREPARE_RESET_FLAG;
    }

    public void prepare(IDisplayer iDisplayer, boolean z) {
        iDisplayer.prepare(this, z);
        this.prepareResetFlag = this.flags.PREPARE_RESET_FLAG;
    }

    public IDrawingCache<?> getDrawingCache() {
        return this.cache;
    }

    public boolean isShown() {
        return this.visibility == 1 && this.visibleResetFlag == this.flags.VISIBLE_RESET_FLAG;
    }

    public boolean isTimeOut() {
        DanmakuTimer danmakuTimer = this.mTimer;
        return danmakuTimer == null || isTimeOut(danmakuTimer.currMillisecond);
    }

    public boolean isTimeOut(long j) {
        return j - getActualTime() >= this.duration.value;
    }

    public boolean isOutside() {
        DanmakuTimer danmakuTimer = this.mTimer;
        return danmakuTimer == null || isOutside(danmakuTimer.currMillisecond);
    }

    public boolean isOutside(long j) {
        long actualTime = j - getActualTime();
        return actualTime <= 0 || actualTime >= this.duration.value;
    }

    public boolean isLate() {
        DanmakuTimer danmakuTimer = this.mTimer;
        return danmakuTimer == null || danmakuTimer.currMillisecond < getActualTime();
    }

    public boolean hasPassedFilter() {
        if (this.filterResetFlag != this.flags.FILTER_RESET_FLAG) {
            this.mFilterParam = 0;
            return false;
        }
        return true;
    }

    public boolean isFiltered() {
        return this.filterResetFlag == this.flags.FILTER_RESET_FLAG && this.mFilterParam != 0;
    }

    public void setVisibility(boolean z) {
        if (z) {
            this.visibleResetFlag = this.flags.VISIBLE_RESET_FLAG;
            this.visibility = 1;
            return;
        }
        this.visibility = 0;
    }

    public DanmakuTimer getTimer() {
        return this.mTimer;
    }

    public void setTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setTag(Object obj) {
        this.tag = obj;
    }

    public void setTimeOffset(long j) {
        this.timeOffset = j;
        this.syncTimeOffsetResetFlag = this.flags.SYNC_TIME_OFFSET_RESET_FLAG;
    }

    public void setTime(long j) {
        this.time = j;
        this.timeOffset = 0L;
    }

    public long getTime() {
        return this.time;
    }

    public long getActualTime() {
        GlobalFlagValues globalFlagValues = this.flags;
        if (globalFlagValues == null || globalFlagValues.SYNC_TIME_OFFSET_RESET_FLAG != this.syncTimeOffsetResetFlag) {
            this.timeOffset = 0L;
            return this.time;
        }
        return this.time + this.timeOffset;
    }

    public boolean isOffset() {
        GlobalFlagValues globalFlagValues = this.flags;
        if (globalFlagValues != null && globalFlagValues.SYNC_TIME_OFFSET_RESET_FLAG == this.syncTimeOffsetResetFlag) {
            return this.timeOffset != 0;
        }
        this.timeOffset = 0L;
        return false;
    }
}
