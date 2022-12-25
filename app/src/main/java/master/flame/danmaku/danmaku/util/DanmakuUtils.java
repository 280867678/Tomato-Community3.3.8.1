package master.flame.danmaku.danmaku.util;

import android.text.TextUtils;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DrawingCache;
import master.flame.danmaku.danmaku.model.android.DrawingCacheHolder;

/* loaded from: classes4.dex */
public class DanmakuUtils {
    public static int getCacheSize(int i, int i2, int i3) {
        return i * i2 * i3;
    }

    public static boolean willHitInDuration(IDisplayer iDisplayer, BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2, long j, long j2) {
        int type = baseDanmaku.getType();
        if (type == baseDanmaku2.getType() && !baseDanmaku.isOutside()) {
            long actualTime = baseDanmaku2.getActualTime() - baseDanmaku.getActualTime();
            if (actualTime <= 0) {
                return true;
            }
            if (Math.abs(actualTime) >= j || baseDanmaku.isTimeOut() || baseDanmaku2.isTimeOut()) {
                return false;
            }
            return type == 5 || type == 4 || checkHitAtTime(iDisplayer, baseDanmaku, baseDanmaku2, j2) || checkHitAtTime(iDisplayer, baseDanmaku, baseDanmaku2, baseDanmaku.getActualTime() + baseDanmaku.getDuration());
        }
        return false;
    }

    private static boolean checkHitAtTime(IDisplayer iDisplayer, BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2, long j) {
        float[] rectAtTime = baseDanmaku.getRectAtTime(iDisplayer, j);
        float[] rectAtTime2 = baseDanmaku2.getRectAtTime(iDisplayer, j);
        if (rectAtTime == null || rectAtTime2 == null) {
            return false;
        }
        return checkHit(baseDanmaku.getType(), baseDanmaku2.getType(), rectAtTime, rectAtTime2);
    }

    private static boolean checkHit(int i, int i2, float[] fArr, float[] fArr2) {
        if (i != i2) {
            return false;
        }
        return i == 1 ? fArr2[0] < fArr[2] : i == 6 && fArr2[2] > fArr[0];
    }

    public static DrawingCache buildDanmakuDrawingCache(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, DrawingCache drawingCache, int i) {
        if (drawingCache == null) {
            drawingCache = new DrawingCache();
        }
        drawingCache.build((int) Math.ceil(baseDanmaku.paintWidth), (int) Math.ceil(baseDanmaku.paintHeight), iDisplayer.getDensityDpi(), false, i);
        DrawingCacheHolder drawingCacheHolder = drawingCache.get();
        if (drawingCacheHolder != null) {
            ((AbsDisplayer) iDisplayer).drawDanmaku(baseDanmaku, drawingCacheHolder.canvas, 0.0f, 0.0f, true);
            if (iDisplayer.isHardwareAccelerated()) {
                drawingCacheHolder.splitWith(iDisplayer.getWidth(), iDisplayer.getHeight(), iDisplayer.getMaximumCacheWidth(), iDisplayer.getMaximumCacheHeight());
            }
        }
        return drawingCache;
    }

    public static final boolean isDuplicate(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
        if (baseDanmaku == baseDanmaku2) {
            return false;
        }
        CharSequence charSequence = baseDanmaku.text;
        CharSequence charSequence2 = baseDanmaku2.text;
        if (charSequence == charSequence2) {
            return true;
        }
        return charSequence != null && charSequence.equals(charSequence2);
    }

    public static final int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
        int i;
        if (baseDanmaku == baseDanmaku2) {
            return 0;
        }
        if (baseDanmaku == null) {
            return -1;
        }
        if (baseDanmaku2 == null || baseDanmaku.getTime() - baseDanmaku2.getTime() > 0) {
            return 1;
        }
        if (i < 0) {
            return -1;
        }
        int i2 = baseDanmaku.index - baseDanmaku2.index;
        if (i2 == 0) {
            return baseDanmaku.hashCode() - baseDanmaku.hashCode();
        }
        return i2 < 0 ? -1 : 1;
    }

    public static void fillText(BaseDanmaku baseDanmaku, CharSequence charSequence) {
        baseDanmaku.text = charSequence;
        if (TextUtils.isEmpty(charSequence) || !charSequence.toString().contains("/n")) {
            return;
        }
        String[] split = String.valueOf(baseDanmaku.text).split("/n", -1);
        if (split.length <= 1) {
            return;
        }
        baseDanmaku.lines = split;
    }
}
