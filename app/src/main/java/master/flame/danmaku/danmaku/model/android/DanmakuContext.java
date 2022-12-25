package master.flame.danmaku.danmaku.model.android;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import master.flame.danmaku.controller.DanmakuFilters;
import master.flame.danmaku.danmaku.model.AbsDanmakuSync;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.GlobalFlagValues;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;

/* loaded from: classes4.dex */
public class DanmakuContext implements Cloneable {
    public AbsDanmakuSync danmakuSync;
    private BaseCacheStuffer mCacheStuffer;
    private List<WeakReference<ConfigChangedCallback>> mCallbackList;
    private boolean mIsMaxLinesLimited;
    private boolean mIsPreventOverlappingEnabled;
    public int transparency = AlphaValue.MAX;
    public float scaleTextSize = 1.0f;
    public float scrollSpeedFactor = 1.0f;
    private boolean mDuplicateMergingEnable = false;
    private boolean mIsAlignBottom = false;
    public AbsDisplayer mDisplayer = new AndroidDisplayer();
    public GlobalFlagValues mGlobalFlagValues = new GlobalFlagValues();
    public DanmakuFilters mDanmakuFilters = new DanmakuFilters();
    public DanmakuFactory mDanmakuFactory = DanmakuFactory.create();
    public CachingPolicy cachingPolicy = CachingPolicy.POLICY_DEFAULT;
    public byte updateMethod = 0;

    /* loaded from: classes4.dex */
    public interface ConfigChangedCallback {
        boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuConfigTag danmakuConfigTag, Object... objArr);
    }

    public DanmakuContext() {
        new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
    }

    public static DanmakuContext create() {
        return new DanmakuContext();
    }

    /* loaded from: classes4.dex */
    public enum DanmakuConfigTag {
        FT_DANMAKU_VISIBILITY,
        FB_DANMAKU_VISIBILITY,
        L2R_DANMAKU_VISIBILITY,
        R2L_DANMAKU_VISIBILIY,
        SPECIAL_DANMAKU_VISIBILITY,
        TYPEFACE,
        TRANSPARENCY,
        SCALE_TEXTSIZE,
        MAXIMUM_NUMS_IN_SCREEN,
        DANMAKU_STYLE,
        DANMAKU_BOLD,
        COLOR_VALUE_WHITE_LIST,
        USER_ID_BLACK_LIST,
        USER_HASH_BLACK_LIST,
        SCROLL_SPEED_FACTOR,
        BLOCK_GUEST_DANMAKU,
        DUPLICATE_MERGING_ENABLED,
        MAXIMUN_LINES,
        OVERLAPPING_ENABLE,
        ALIGN_BOTTOM,
        DANMAKU_MARGIN,
        DANMAKU_SYNC;

        public boolean isVisibilityRelatedTag() {
            return equals(FT_DANMAKU_VISIBILITY) || equals(FB_DANMAKU_VISIBILITY) || equals(L2R_DANMAKU_VISIBILITY) || equals(R2L_DANMAKU_VISIBILIY) || equals(SPECIAL_DANMAKU_VISIBILITY) || equals(COLOR_VALUE_WHITE_LIST) || equals(USER_ID_BLACK_LIST);
        }
    }

    public AbsDisplayer getDisplayer() {
        return this.mDisplayer;
    }

    public DanmakuContext setDanmakuTransparency(float f) {
        int i = (int) (AlphaValue.MAX * f);
        if (i != this.transparency) {
            this.transparency = i;
            this.mDisplayer.setTransparency(i);
            notifyConfigureChanged(DanmakuConfigTag.TRANSPARENCY, Float.valueOf(f));
        }
        return this;
    }

    public DanmakuContext setScaleTextSize(float f) {
        if (this.scaleTextSize != f) {
            this.scaleTextSize = f;
            this.mDisplayer.clearTextHeightCache();
            this.mDisplayer.setScaleTextSizeFactor(f);
            this.mGlobalFlagValues.updateMeasureFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.SCALE_TEXTSIZE, Float.valueOf(f));
        }
        return this;
    }

    private <T> void setFilterData(String str, T t, boolean z) {
        this.mDanmakuFilters.get(str, z).setData(t);
    }

    public DanmakuContext setDanmakuBold(boolean z) {
        this.mDisplayer.setFakeBoldText(z);
        notifyConfigureChanged(DanmakuConfigTag.DANMAKU_BOLD, Boolean.valueOf(z));
        return this;
    }

    public DanmakuContext setScrollSpeedFactor(float f) {
        if (this.scrollSpeedFactor != f) {
            this.scrollSpeedFactor = f;
            this.mDanmakuFactory.updateDurationFactor(f);
            this.mGlobalFlagValues.updateMeasureFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.SCROLL_SPEED_FACTOR, Float.valueOf(f));
        }
        return this;
    }

    public DanmakuContext setDuplicateMergingEnabled(boolean z) {
        if (this.mDuplicateMergingEnable != z) {
            this.mDuplicateMergingEnable = z;
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.DUPLICATE_MERGING_ENABLED, Boolean.valueOf(z));
        }
        return this;
    }

    public boolean isDuplicateMergingEnabled() {
        return this.mDuplicateMergingEnable;
    }

    public boolean isAlignBottom() {
        return this.mIsAlignBottom;
    }

    public DanmakuContext setMaximumLines(Map<Integer, Integer> map) {
        this.mIsMaxLinesLimited = map != null;
        if (map == null) {
            this.mDanmakuFilters.unregisterFilter("1018_Filter", false);
        } else {
            setFilterData("1018_Filter", map, false);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.MAXIMUN_LINES, map);
        return this;
    }

    public DanmakuContext preventOverlapping(Map<Integer, Boolean> map) {
        this.mIsPreventOverlappingEnabled = map != null;
        if (map == null) {
            this.mDanmakuFilters.unregisterFilter("1019_Filter", false);
        } else {
            setFilterData("1019_Filter", map, false);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.OVERLAPPING_ENABLE, map);
        return this;
    }

    public boolean isMaxLinesLimited() {
        return this.mIsMaxLinesLimited;
    }

    public boolean isPreventOverlappingEnabled() {
        return this.mIsPreventOverlappingEnabled;
    }

    public DanmakuContext setCacheStuffer(BaseCacheStuffer baseCacheStuffer, BaseCacheStuffer.Proxy proxy) {
        this.mCacheStuffer = baseCacheStuffer;
        BaseCacheStuffer baseCacheStuffer2 = this.mCacheStuffer;
        if (baseCacheStuffer2 != null) {
            baseCacheStuffer2.setProxy(proxy);
            this.mDisplayer.setCacheStuffer(this.mCacheStuffer);
        }
        return this;
    }

    public DanmakuContext setDanmakuSync(AbsDanmakuSync absDanmakuSync) {
        this.danmakuSync = absDanmakuSync;
        return this;
    }

    public void registerConfigChangedCallback(ConfigChangedCallback configChangedCallback) {
        if (configChangedCallback == null || this.mCallbackList == null) {
            this.mCallbackList = Collections.synchronizedList(new ArrayList());
        }
        for (WeakReference<ConfigChangedCallback> weakReference : this.mCallbackList) {
            if (configChangedCallback.equals(weakReference.get())) {
                return;
            }
        }
        this.mCallbackList.add(new WeakReference<>(configChangedCallback));
    }

    public void unregisterAllConfigChangedCallbacks() {
        List<WeakReference<ConfigChangedCallback>> list = this.mCallbackList;
        if (list != null) {
            list.clear();
            this.mCallbackList = null;
        }
    }

    private void notifyConfigureChanged(DanmakuConfigTag danmakuConfigTag, Object... objArr) {
        List<WeakReference<ConfigChangedCallback>> list = this.mCallbackList;
        if (list != null) {
            for (WeakReference<ConfigChangedCallback> weakReference : list) {
                ConfigChangedCallback configChangedCallback = weakReference.get();
                if (configChangedCallback != null) {
                    configChangedCallback.onDanmakuConfigChanged(this, danmakuConfigTag, objArr);
                }
            }
        }
    }

    public DanmakuContext resetContext() {
        this.mDisplayer = new AndroidDisplayer();
        this.mGlobalFlagValues = new GlobalFlagValues();
        this.mDanmakuFilters.clear();
        this.mDanmakuFactory = DanmakuFactory.create();
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
