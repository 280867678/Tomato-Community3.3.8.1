package master.flame.danmaku.danmaku.model.android;

import android.os.Build;

/* loaded from: classes4.dex */
public class CachingPolicy {
    public int bitsPerPixelOfCache;
    public float forceRecyleThreshold;
    public float maxCachePoolSizeFactorPercentage;
    public long periodOfRecycle;
    public int reusableOffsetPixel;
    public static final CachingPolicy POLICY_LAZY = new CachingPolicy(16, 0.3f, 0, 50, 0.01f);
    public static final CachingPolicy POLICY_DEFAULT = POLICY_LAZY;
    public int maxTimesOfStrictReusableFinds = 20;
    public int maxTimesOfReusableFinds = 150;

    static {
        new CachingPolicy(16, 0.5f, -1L, 50, 0.005f);
    }

    public CachingPolicy(int i, float f, long j, int i2, float f2) {
        this.bitsPerPixelOfCache = 16;
        this.maxCachePoolSizeFactorPercentage = 0.3f;
        this.periodOfRecycle = 0L;
        this.forceRecyleThreshold = 0.01f;
        this.reusableOffsetPixel = 0;
        this.bitsPerPixelOfCache = i;
        if (Build.VERSION.SDK_INT >= 19) {
            this.bitsPerPixelOfCache = 32;
        }
        this.maxCachePoolSizeFactorPercentage = f;
        this.periodOfRecycle = j;
        this.reusableOffsetPixel = i2;
        this.forceRecyleThreshold = f2;
    }
}
