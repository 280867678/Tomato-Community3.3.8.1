package master.flame.danmaku.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.util.SystemClock;

/* loaded from: classes4.dex */
public class DanmakuFilters {
    public final Exception filterException = new Exception("not suuport this filter tag");
    private final Map<String, IDanmakuFilter<?>> filters = Collections.synchronizedSortedMap(new TreeMap());
    private final Map<String, IDanmakuFilter<?>> filtersSecondary = Collections.synchronizedSortedMap(new TreeMap());
    IDanmakuFilter<?>[] mFilterArray = new IDanmakuFilter[0];
    IDanmakuFilter<?>[] mFilterArraySecondary = new IDanmakuFilter[0];

    /* loaded from: classes4.dex */
    public static abstract class BaseDanmakuFilter<T> implements IDanmakuFilter<T> {
        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
        }
    }

    /* loaded from: classes4.dex */
    public interface IDanmakuFilter<T> {
        void clear();

        boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext);

        void setData(T t);
    }

    /* loaded from: classes4.dex */
    public static class TypeDanmakuFilter extends BaseDanmakuFilter<List<Integer>> {
        final List<Integer> mFilterTypes = Collections.synchronizedList(new ArrayList());

        public void enableType(Integer num) {
            if (!this.mFilterTypes.contains(num)) {
                this.mFilterTypes.add(num);
            }
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean z2 = baseDanmaku != null && this.mFilterTypes.contains(Integer.valueOf(baseDanmaku.getType()));
            if (z2) {
                baseDanmaku.mFilterParam = 1 | baseDanmaku.mFilterParam;
            }
            return z2;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(List<Integer> list) {
            reset();
            if (list != null) {
                for (Integer num : list) {
                    enableType(num);
                }
            }
        }

        public void reset() {
            this.mFilterTypes.clear();
        }
    }

    /* loaded from: classes4.dex */
    public static class QuantityDanmakuFilter extends BaseDanmakuFilter<Integer> {
        protected int mMaximumSize = -1;
        protected BaseDanmaku mLastSkipped = null;
        private float mFilterFactor = 1.0f;

        private boolean needFilter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            if (this.mMaximumSize > 0 && baseDanmaku.getType() == 1) {
                BaseDanmaku baseDanmaku2 = this.mLastSkipped;
                if (baseDanmaku2 == null || baseDanmaku2.isTimeOut()) {
                    this.mLastSkipped = baseDanmaku;
                } else {
                    long actualTime = baseDanmaku.getActualTime() - this.mLastSkipped.getActualTime();
                    Duration duration = danmakuContext.mDanmakuFactory.MAX_Duration_Scroll_Danmaku;
                    if ((actualTime >= 0 && duration != null && ((float) actualTime) < ((float) duration.value) * this.mFilterFactor) || i > this.mMaximumSize) {
                        return true;
                    }
                    this.mLastSkipped = baseDanmaku;
                    return false;
                }
            }
            return false;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public synchronized boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean needFilter;
            needFilter = needFilter(baseDanmaku, i, i2, danmakuTimer, z, danmakuContext);
            if (needFilter) {
                baseDanmaku.mFilterParam |= 2;
            }
            return needFilter;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Integer num) {
            reset();
            if (num == null || num.intValue() == this.mMaximumSize) {
                return;
            }
            this.mMaximumSize = num.intValue() + (num.intValue() / 5);
            this.mFilterFactor = 1.0f / this.mMaximumSize;
        }

        public synchronized void reset() {
            this.mLastSkipped = null;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.BaseDanmakuFilter, master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
            reset();
        }
    }

    /* loaded from: classes4.dex */
    public static class ElapsedTimeFilter extends BaseDanmakuFilter<Object> {
        long mMaxTime = 20;

        private synchronized boolean needFilter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z) {
            if (danmakuTimer != null) {
                if (baseDanmaku.isOutside()) {
                    return SystemClock.uptimeMillis() - danmakuTimer.currMillisecond >= this.mMaxTime;
                }
            }
            return false;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean needFilter = needFilter(baseDanmaku, i, i2, danmakuTimer, z);
            if (needFilter) {
                baseDanmaku.mFilterParam |= 4;
            }
            return needFilter;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Object obj) {
            reset();
        }

        public synchronized void reset() {
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.BaseDanmakuFilter, master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
            reset();
        }
    }

    /* loaded from: classes4.dex */
    public static class TextColorFilter extends BaseDanmakuFilter<List<Integer>> {
        public List<Integer> mWhiteList = new ArrayList();

        private void addToWhiteList(Integer num) {
            if (!this.mWhiteList.contains(num)) {
                this.mWhiteList.add(num);
            }
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean z2 = baseDanmaku != null && !this.mWhiteList.contains(Integer.valueOf(baseDanmaku.textColor));
            if (z2) {
                baseDanmaku.mFilterParam |= 8;
            }
            return z2;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(List<Integer> list) {
            reset();
            if (list != null) {
                for (Integer num : list) {
                    addToWhiteList(num);
                }
            }
        }

        public void reset() {
            this.mWhiteList.clear();
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class UserFilter<T> extends BaseDanmakuFilter<List<T>> {
        public List<T> mBlackList = new ArrayList();

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public /* bridge */ /* synthetic */ void setData(Object obj) {
            setData((List) ((List) obj));
        }

        private void addToBlackList(T t) {
            if (!this.mBlackList.contains(t)) {
                this.mBlackList.add(t);
            }
        }

        public void setData(List<T> list) {
            reset();
            if (list != null) {
                for (T t : list) {
                    addToBlackList(t);
                }
            }
        }

        public void reset() {
            this.mBlackList.clear();
        }
    }

    /* loaded from: classes4.dex */
    public static class UserIdFilter extends UserFilter<Integer> {
        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean z2 = baseDanmaku != null && this.mBlackList.contains(Integer.valueOf(baseDanmaku.userId));
            if (z2) {
                baseDanmaku.mFilterParam |= 16;
            }
            return z2;
        }
    }

    /* loaded from: classes4.dex */
    public static class UserHashFilter extends UserFilter<String> {
        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean z2 = baseDanmaku != null && this.mBlackList.contains(baseDanmaku.userHash);
            if (z2) {
                baseDanmaku.mFilterParam |= 32;
            }
            return z2;
        }
    }

    /* loaded from: classes4.dex */
    public static class GuestFilter extends BaseDanmakuFilter<Boolean> {
        private Boolean mBlock = false;

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean z2 = this.mBlock.booleanValue() && baseDanmaku.isGuest;
            if (z2) {
                baseDanmaku.mFilterParam |= 64;
            }
            return z2;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Boolean bool) {
            this.mBlock = bool;
        }
    }

    /* loaded from: classes4.dex */
    public static class DuplicateMergingFilter extends BaseDanmakuFilter<Void> {
        protected final IDanmakus blockedDanmakus = new Danmakus(4);
        protected final LinkedHashMap<String, BaseDanmaku> currentDanmakus = new LinkedHashMap<>();
        private final IDanmakus passedDanmakus = new Danmakus(4);

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Void r1) {
        }

        private final void removeTimeoutDanmakus(IDanmakus iDanmakus, final long j) {
            iDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>(this) { // from class: master.flame.danmaku.controller.DanmakuFilters.DuplicateMergingFilter.1
                long startTime = SystemClock.uptimeMillis();

                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (SystemClock.uptimeMillis() - this.startTime > j) {
                        return 1;
                    }
                    return baseDanmaku.isTimeOut() ? 2 : 1;
                }
            });
        }

        private void removeTimeoutDanmakus(LinkedHashMap<String, BaseDanmaku> linkedHashMap, int i) {
            Iterator<Map.Entry<String, BaseDanmaku>> it2 = linkedHashMap.entrySet().iterator();
            long uptimeMillis = SystemClock.uptimeMillis();
            while (it2.hasNext()) {
                try {
                    if (!it2.next().getValue().isTimeOut()) {
                        return;
                    }
                    it2.remove();
                    if (SystemClock.uptimeMillis() - uptimeMillis > i) {
                        return;
                    }
                } catch (Exception unused) {
                    return;
                }
            }
        }

        public synchronized boolean needFilter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z) {
            removeTimeoutDanmakus(this.blockedDanmakus, 2L);
            removeTimeoutDanmakus(this.passedDanmakus, 2L);
            removeTimeoutDanmakus(this.currentDanmakus, 3);
            if (!this.blockedDanmakus.contains(baseDanmaku) || baseDanmaku.isOutside()) {
                if (this.passedDanmakus.contains(baseDanmaku)) {
                    return false;
                }
                if (this.currentDanmakus.containsKey(baseDanmaku.text)) {
                    this.currentDanmakus.put(String.valueOf(baseDanmaku.text), baseDanmaku);
                    this.blockedDanmakus.removeItem(baseDanmaku);
                    this.blockedDanmakus.addItem(baseDanmaku);
                    return true;
                }
                this.currentDanmakus.put(String.valueOf(baseDanmaku.text), baseDanmaku);
                this.passedDanmakus.addItem(baseDanmaku);
                return false;
            }
            return true;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            boolean needFilter = needFilter(baseDanmaku, i, i2, danmakuTimer, z);
            if (needFilter) {
                baseDanmaku.mFilterParam |= 128;
            }
            return needFilter;
        }

        public synchronized void reset() {
            this.passedDanmakus.clear();
            this.blockedDanmakus.clear();
            this.currentDanmakus.clear();
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.BaseDanmakuFilter, master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
            reset();
        }
    }

    /* loaded from: classes4.dex */
    public static class MaximumLinesFilter extends BaseDanmakuFilter<Map<Integer, Integer>> {
        private Map<Integer, Integer> mMaximumLinesPairs;

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            Map<Integer, Integer> map = this.mMaximumLinesPairs;
            boolean z2 = false;
            if (map != null) {
                Integer num = map.get(Integer.valueOf(baseDanmaku.getType()));
                if (num != null && i >= num.intValue()) {
                    z2 = true;
                }
                if (z2) {
                    baseDanmaku.mFilterParam |= 256;
                }
            }
            return z2;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Map<Integer, Integer> map) {
            this.mMaximumLinesPairs = map;
        }
    }

    /* loaded from: classes4.dex */
    public static class OverlappingFilter extends BaseDanmakuFilter<Map<Integer, Boolean>> {
        private Map<Integer, Boolean> mEnabledPairs;

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
            Map<Integer, Boolean> map = this.mEnabledPairs;
            boolean z2 = false;
            if (map != null) {
                Boolean bool = map.get(Integer.valueOf(baseDanmaku.getType()));
                if (bool != null && bool.booleanValue() && z) {
                    z2 = true;
                }
                if (z2) {
                    baseDanmaku.mFilterParam |= 512;
                }
            }
            return z2;
        }

        @Override // master.flame.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Map<Integer, Boolean> map) {
            this.mEnabledPairs = map;
        }
    }

    public void filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
        IDanmakuFilter<?>[] iDanmakuFilterArr;
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArray) {
            if (iDanmakuFilter != null) {
                boolean filter = iDanmakuFilter.filter(baseDanmaku, i, i2, danmakuTimer, z, danmakuContext);
                baseDanmaku.filterResetFlag = danmakuContext.mGlobalFlagValues.FILTER_RESET_FLAG;
                if (filter) {
                    return;
                }
            }
        }
    }

    public boolean filterSecondary(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext) {
        IDanmakuFilter<?>[] iDanmakuFilterArr;
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArraySecondary) {
            if (iDanmakuFilter != null) {
                boolean filter = iDanmakuFilter.filter(baseDanmaku, i, i2, danmakuTimer, z, danmakuContext);
                baseDanmaku.filterResetFlag = danmakuContext.mGlobalFlagValues.FILTER_RESET_FLAG;
                if (filter) {
                    return true;
                }
            }
        }
        return false;
    }

    public IDanmakuFilter<?> get(String str, boolean z) {
        IDanmakuFilter<?> iDanmakuFilter = (z ? this.filters : this.filtersSecondary).get(str);
        return iDanmakuFilter == null ? registerFilter(str, z) : iDanmakuFilter;
    }

    public IDanmakuFilter<?> registerFilter(String str) {
        return registerFilter(str, true);
    }

    public IDanmakuFilter<?> registerFilter(String str, boolean z) {
        if (str == null) {
            throwFilterException();
            return null;
        }
        IDanmakuFilter<?> iDanmakuFilter = this.filters.get(str);
        if (iDanmakuFilter == null) {
            if ("1010_Filter".equals(str)) {
                iDanmakuFilter = new TypeDanmakuFilter();
            } else if ("1011_Filter".equals(str)) {
                iDanmakuFilter = new QuantityDanmakuFilter();
            } else if ("1012_Filter".equals(str)) {
                iDanmakuFilter = new ElapsedTimeFilter();
            } else if ("1013_Filter".equals(str)) {
                iDanmakuFilter = new TextColorFilter();
            } else if ("1014_Filter".equals(str)) {
                iDanmakuFilter = new UserIdFilter();
            } else if ("1015_Filter".equals(str)) {
                iDanmakuFilter = new UserHashFilter();
            } else if ("1016_Filter".equals(str)) {
                iDanmakuFilter = new GuestFilter();
            } else if ("1017_Filter".equals(str)) {
                iDanmakuFilter = new DuplicateMergingFilter();
            } else if ("1018_Filter".equals(str)) {
                iDanmakuFilter = new MaximumLinesFilter();
            } else if ("1019_Filter".equals(str)) {
                iDanmakuFilter = new OverlappingFilter();
            }
        }
        if (iDanmakuFilter == null) {
            throwFilterException();
            return null;
        }
        iDanmakuFilter.setData(null);
        if (z) {
            this.filters.put(str, iDanmakuFilter);
            this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
        } else {
            this.filtersSecondary.put(str, iDanmakuFilter);
            this.mFilterArraySecondary = (IDanmakuFilter[]) this.filtersSecondary.values().toArray(this.mFilterArraySecondary);
        }
        return iDanmakuFilter;
    }

    public void unregisterFilter(String str) {
        unregisterFilter(str, true);
    }

    public void unregisterFilter(String str, boolean z) {
        IDanmakuFilter<?> remove = (z ? this.filters : this.filtersSecondary).remove(str);
        if (remove != null) {
            remove.clear();
            if (z) {
                this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
            } else {
                this.mFilterArraySecondary = (IDanmakuFilter[]) this.filtersSecondary.values().toArray(this.mFilterArraySecondary);
            }
        }
    }

    public void clear() {
        IDanmakuFilter<?>[] iDanmakuFilterArr;
        IDanmakuFilter<?>[] iDanmakuFilterArr2;
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArray) {
            if (iDanmakuFilter != null) {
                iDanmakuFilter.clear();
            }
        }
        for (IDanmakuFilter<?> iDanmakuFilter2 : this.mFilterArraySecondary) {
            if (iDanmakuFilter2 != null) {
                iDanmakuFilter2.clear();
            }
        }
    }

    private void throwFilterException() {
        try {
            throw this.filterException;
        } catch (Exception unused) {
        }
    }
}
