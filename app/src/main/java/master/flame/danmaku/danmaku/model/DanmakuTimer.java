package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public class DanmakuTimer {
    public long currMillisecond;
    private long lastInterval;

    public long update(long j) {
        this.lastInterval = j - this.currMillisecond;
        this.currMillisecond = j;
        return this.lastInterval;
    }

    public long add(long j) {
        return update(this.currMillisecond + j);
    }

    public long lastInterval() {
        return this.lastInterval;
    }
}
