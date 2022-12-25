package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public abstract class AbsDanmakuSync {
    public abstract int getSyncState();

    public abstract long getThresholdTimeMills();

    public abstract long getUptimeMillis();

    public abstract boolean isSyncPlayingState();
}
