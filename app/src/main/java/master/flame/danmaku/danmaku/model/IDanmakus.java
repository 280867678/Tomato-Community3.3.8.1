package master.flame.danmaku.danmaku.model;

import java.util.Comparator;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes4.dex */
public interface IDanmakus {

    /* loaded from: classes4.dex */
    public static abstract class Consumer<Progress, Result> {
        public abstract int accept(Progress progress);

        public void after() {
        }

        public void before() {
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class DefaultConsumer<Progress> extends Consumer<Progress, Void> {
    }

    boolean addItem(BaseDanmaku baseDanmaku);

    void clear();

    boolean contains(BaseDanmaku baseDanmaku);

    BaseDanmaku first();

    void forEach(Consumer<? super BaseDanmaku, ?> consumer);

    void forEachSync(Consumer<? super BaseDanmaku, ?> consumer);

    boolean isEmpty();

    BaseDanmaku last();

    Object obtainSynchronizer();

    boolean removeItem(BaseDanmaku baseDanmaku);

    int size();

    IDanmakus sub(long j, long j2);

    IDanmakus subnew(long j, long j2);

    /* loaded from: classes4.dex */
    public static class BaseComparator implements Comparator<BaseDanmaku> {
        protected boolean mDuplicateMergingEnable;

        public BaseComparator(boolean z) {
            setDuplicateMergingEnabled(z);
        }

        public void setDuplicateMergingEnabled(boolean z) {
            this.mDuplicateMergingEnable = z;
        }

        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            if (!this.mDuplicateMergingEnable || !DanmakuUtils.isDuplicate(baseDanmaku, baseDanmaku2)) {
                return DanmakuUtils.compare(baseDanmaku, baseDanmaku2);
            }
            return 0;
        }
    }

    /* loaded from: classes4.dex */
    public static class TimeComparator extends BaseComparator {
        public TimeComparator(boolean z) {
            super(z);
        }

        @Override // java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            return super.compare(baseDanmaku, baseDanmaku2);
        }
    }

    /* loaded from: classes4.dex */
    public static class YPosComparator extends BaseComparator {
        public YPosComparator(boolean z) {
            super(z);
        }

        @Override // java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            if (!this.mDuplicateMergingEnable || !DanmakuUtils.isDuplicate(baseDanmaku, baseDanmaku2)) {
                return Float.compare(baseDanmaku.getTop(), baseDanmaku2.getTop());
            }
            return 0;
        }
    }

    /* loaded from: classes4.dex */
    public static class YPosDescComparator extends BaseComparator {
        public YPosDescComparator(boolean z) {
            super(z);
        }

        @Override // java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            if (!this.mDuplicateMergingEnable || !DanmakuUtils.isDuplicate(baseDanmaku, baseDanmaku2)) {
                return Float.compare(baseDanmaku2.getTop(), baseDanmaku.getTop());
            }
            return 0;
        }
    }
}
