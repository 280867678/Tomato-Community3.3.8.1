package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public interface IDisplayer {
    int draw(BaseDanmaku baseDanmaku);

    int getAllMarginTop();

    float getDensity();

    int getDensityDpi();

    int getHeight();

    int getMargin();

    int getMaximumCacheHeight();

    int getMaximumCacheWidth();

    float getScaledDensity();

    int getSlopPixel();

    int getWidth();

    boolean isHardwareAccelerated();

    void measure(BaseDanmaku baseDanmaku, boolean z);

    void prepare(BaseDanmaku baseDanmaku, boolean z);

    void recycle(BaseDanmaku baseDanmaku);

    void resetSlopPixel(float f);

    void setDensities(float f, int i, float f2);

    void setHardwareAccelerated(boolean z);

    void setSize(int i, int i2);
}
