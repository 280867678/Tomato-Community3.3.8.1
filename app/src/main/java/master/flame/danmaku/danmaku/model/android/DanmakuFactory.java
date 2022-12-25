package master.flame.danmaku.danmaku.model.android;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.FBDanmaku;
import master.flame.danmaku.danmaku.model.FTDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.L2RDanmaku;
import master.flame.danmaku.danmaku.model.R2LDanmaku;
import master.flame.danmaku.danmaku.model.SpecialDanmaku;

/* loaded from: classes4.dex */
public class DanmakuFactory {
    public Duration MAX_Duration_Fix_Danmaku;
    public Duration MAX_Duration_Scroll_Danmaku;
    public Duration MAX_Duration_Special_Danmaku;
    private DanmakuContext sLastConfig;
    public IDisplayer sLastDisp;
    public int CURRENT_DISP_WIDTH = 0;
    public int CURRENT_DISP_HEIGHT = 0;
    private SpecialDanmaku.ScaleFactor mScaleFactor = null;
    private float CURRENT_DISP_SIZE_FACTOR = 1.0f;
    public long REAL_DANMAKU_DURATION = 3800;
    public long MAX_DANMAKU_DURATION = 4000;

    public static DanmakuFactory create() {
        return new DanmakuFactory();
    }

    protected DanmakuFactory() {
    }

    public void resetDurationsData() {
        this.sLastDisp = null;
        this.CURRENT_DISP_HEIGHT = 0;
        this.CURRENT_DISP_WIDTH = 0;
        this.MAX_Duration_Scroll_Danmaku = null;
        this.MAX_Duration_Fix_Danmaku = null;
        this.MAX_Duration_Special_Danmaku = null;
        this.MAX_DANMAKU_DURATION = 4000L;
    }

    public void notifyDispSizeChanged(DanmakuContext danmakuContext) {
        this.sLastConfig = danmakuContext;
        this.sLastDisp = danmakuContext.getDisplayer();
        createDanmaku(1, danmakuContext);
    }

    public BaseDanmaku createDanmaku(int i) {
        return createDanmaku(i, this.sLastConfig);
    }

    public BaseDanmaku createDanmaku(int i, DanmakuContext danmakuContext) {
        if (danmakuContext == null) {
            return null;
        }
        this.sLastConfig = danmakuContext;
        this.sLastDisp = danmakuContext.getDisplayer();
        return createDanmaku(i, this.sLastDisp.getWidth(), this.sLastDisp.getHeight(), this.CURRENT_DISP_SIZE_FACTOR, danmakuContext.scrollSpeedFactor);
    }

    public BaseDanmaku createDanmaku(int i, int i2, int i3, float f, float f2) {
        return createDanmaku(i, i2, i3, f, f2);
    }

    public BaseDanmaku createDanmaku(int i, float f, float f2, float f3, float f4) {
        float f5;
        int i2 = this.CURRENT_DISP_WIDTH;
        int i3 = this.CURRENT_DISP_HEIGHT;
        boolean updateViewportState = updateViewportState(f, f2, f3);
        Duration duration = this.MAX_Duration_Scroll_Danmaku;
        if (duration == null) {
            this.MAX_Duration_Scroll_Danmaku = new Duration(this.REAL_DANMAKU_DURATION);
            this.MAX_Duration_Scroll_Danmaku.setFactor(f4);
        } else if (updateViewportState) {
            duration.setValue(this.REAL_DANMAKU_DURATION);
        }
        if (this.MAX_Duration_Fix_Danmaku == null) {
            this.MAX_Duration_Fix_Danmaku = new Duration(3800L);
        }
        float f6 = 1.0f;
        if (!updateViewportState || f <= 0.0f) {
            f5 = 1.0f;
        } else {
            updateMaxDanmakuDuration();
            if (i2 <= 0 || i3 <= 0) {
                f5 = 1.0f;
            } else {
                f6 = f / i2;
                f5 = f2 / i3;
            }
            int i4 = (int) f;
            int i5 = (int) f2;
            updateScaleFactor(i4, i5, f6, f5);
            if (f2 > 0.0f) {
                updateSpecialDanmakusDate(i4, i5, f6, f5);
            }
        }
        if (i != 1) {
            if (i == 4) {
                return new FBDanmaku(this.MAX_Duration_Fix_Danmaku);
            }
            if (i == 5) {
                return new FTDanmaku(this.MAX_Duration_Fix_Danmaku);
            }
            if (i == 6) {
                return new L2RDanmaku(this.MAX_Duration_Scroll_Danmaku);
            }
            if (i != 7) {
                return null;
            }
            SpecialDanmaku specialDanmaku = new SpecialDanmaku();
            updateScaleFactor((int) f, (int) f2, f6, f5);
            specialDanmaku.setScaleFactor(this.mScaleFactor);
            return specialDanmaku;
        }
        return new R2LDanmaku(this.MAX_Duration_Scroll_Danmaku);
    }

    private void updateScaleFactor(int i, int i2, float f, float f2) {
        if (this.mScaleFactor == null) {
            this.mScaleFactor = new SpecialDanmaku.ScaleFactor(i, i2, f, f2);
        }
        this.mScaleFactor.update(i, i2, f, f2);
    }

    public boolean updateViewportState(float f, float f2, float f3) {
        int i = (int) f;
        if (this.CURRENT_DISP_WIDTH == i && this.CURRENT_DISP_HEIGHT == ((int) f2) && this.CURRENT_DISP_SIZE_FACTOR == f3) {
            return false;
        }
        this.REAL_DANMAKU_DURATION = ((f * f3) / 682.0f) * 3800.0f;
        this.REAL_DANMAKU_DURATION = Math.min(9000L, this.REAL_DANMAKU_DURATION);
        this.REAL_DANMAKU_DURATION = Math.max(4000L, this.REAL_DANMAKU_DURATION);
        this.CURRENT_DISP_WIDTH = i;
        this.CURRENT_DISP_HEIGHT = (int) f2;
        this.CURRENT_DISP_SIZE_FACTOR = f3;
        return true;
    }

    private synchronized void updateSpecialDanmakusDate(int i, int i2, float f, float f2) {
        if (this.mScaleFactor != null) {
            this.mScaleFactor.update(i, i2, f, f2);
        }
    }

    public void updateMaxDanmakuDuration() {
        Duration duration = this.MAX_Duration_Scroll_Danmaku;
        long j = 0;
        long j2 = duration == null ? 0L : duration.value;
        Duration duration2 = this.MAX_Duration_Fix_Danmaku;
        long j3 = duration2 == null ? 0L : duration2.value;
        Duration duration3 = this.MAX_Duration_Special_Danmaku;
        if (duration3 != null) {
            j = duration3.value;
        }
        this.MAX_DANMAKU_DURATION = Math.max(j2, j3);
        this.MAX_DANMAKU_DURATION = Math.max(this.MAX_DANMAKU_DURATION, j);
        this.MAX_DANMAKU_DURATION = Math.max(3800L, this.MAX_DANMAKU_DURATION);
        this.MAX_DANMAKU_DURATION = Math.max(this.REAL_DANMAKU_DURATION, this.MAX_DANMAKU_DURATION);
    }

    public void updateDurationFactor(float f) {
        Duration duration = this.MAX_Duration_Scroll_Danmaku;
        if (duration == null || this.MAX_Duration_Fix_Danmaku == null) {
            return;
        }
        duration.setFactor(f);
        updateMaxDanmakuDuration();
    }

    public void fillTranslationData(BaseDanmaku baseDanmaku, float f, float f2, float f3, float f4, long j, long j2, float f5, float f6) {
        if (baseDanmaku.getType() != 7) {
            return;
        }
        ((SpecialDanmaku) baseDanmaku).setTranslationData(f * f5, f2 * f6, f3 * f5, f4 * f6, j, j2);
        updateSpecicalDanmakuDuration(baseDanmaku);
    }

    public void fillAlphaData(BaseDanmaku baseDanmaku, int i, int i2, long j) {
        if (baseDanmaku.getType() != 7) {
            return;
        }
        ((SpecialDanmaku) baseDanmaku).setAlphaData(i, i2, j);
        updateSpecicalDanmakuDuration(baseDanmaku);
    }

    private void updateSpecicalDanmakuDuration(BaseDanmaku baseDanmaku) {
        Duration duration;
        Duration duration2 = this.MAX_Duration_Special_Danmaku;
        if (duration2 == null || ((duration = baseDanmaku.duration) != null && duration.value > duration2.value)) {
            this.MAX_Duration_Special_Danmaku = baseDanmaku.duration;
            updateMaxDanmakuDuration();
        }
    }
}
