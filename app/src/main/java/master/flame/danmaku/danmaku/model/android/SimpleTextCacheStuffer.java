package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.SpecialDanmaku;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;

/* loaded from: classes4.dex */
public class SimpleTextCacheStuffer extends BaseCacheStuffer {
    private static final Map<Float, Float> sTextHeightCache = new HashMap();

    protected void drawBackground(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2) {
    }

    protected Float getCacheHeight(BaseDanmaku baseDanmaku, Paint paint) {
        Float valueOf = Float.valueOf(paint.getTextSize());
        Float f = sTextHeightCache.get(valueOf);
        if (f == null) {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            Float valueOf2 = Float.valueOf((fontMetrics.descent - fontMetrics.ascent) + fontMetrics.leading);
            sTextHeightCache.put(valueOf, valueOf2);
            return valueOf2;
        }
        return f;
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z) {
        String[] strArr;
        float f = 0.0f;
        Float valueOf = Float.valueOf(0.0f);
        if (baseDanmaku.lines == null) {
            CharSequence charSequence = baseDanmaku.text;
            if (charSequence != null) {
                f = textPaint.measureText(charSequence.toString());
                valueOf = getCacheHeight(baseDanmaku, textPaint);
            }
            baseDanmaku.paintWidth = f;
            baseDanmaku.paintHeight = valueOf.floatValue();
            return;
        }
        Float cacheHeight = getCacheHeight(baseDanmaku, textPaint);
        for (String str : baseDanmaku.lines) {
            if (str.length() > 0) {
                f = Math.max(textPaint.measureText(str), f);
            }
        }
        baseDanmaku.paintWidth = f;
        baseDanmaku.paintHeight = baseDanmaku.lines.length * cacheHeight.floatValue();
    }

    protected void drawStroke(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f, float f2, Paint paint) {
        if (str != null) {
            canvas.drawText(str, f, f2, paint);
        } else {
            canvas.drawText(baseDanmaku.text.toString(), f, f2, paint);
        }
    }

    protected void drawText(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f, float f2, TextPaint textPaint, boolean z) {
        if (z && (baseDanmaku instanceof SpecialDanmaku)) {
            textPaint.setAlpha(255);
        }
        if (str != null) {
            canvas.drawText(str, f, f2, textPaint);
        } else {
            canvas.drawText(baseDanmaku.text.toString(), f, f2, textPaint);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void clearCaches() {
        sTextHeightCache.clear();
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2, boolean z, AndroidDisplayer.DisplayerConfig displayerConfig) {
        float f3;
        float f4;
        int i;
        float f5;
        float f6;
        float f7;
        float f8;
        int i2 = baseDanmaku.padding;
        float f9 = f + i2;
        float f10 = f2 + i2;
        if (baseDanmaku.borderColor != 0) {
            f9 += 4.0f;
            f10 += 4.0f;
        }
        float f11 = f10;
        float f12 = f9;
        displayerConfig.definePaintParams(z);
        TextPaint paint = displayerConfig.getPaint(baseDanmaku, z);
        drawBackground(baseDanmaku, canvas, f, f2);
        String[] strArr = baseDanmaku.lines;
        boolean z2 = true;
        boolean z3 = false;
        if (strArr != null) {
            if (strArr.length == 1) {
                if (displayerConfig.hasStroke(baseDanmaku)) {
                    displayerConfig.applyPaintConfig(baseDanmaku, paint, true);
                    float ascent = f11 - paint.ascent();
                    if (displayerConfig.HAS_PROJECTION) {
                        f7 = ascent + displayerConfig.sProjectionOffsetY;
                        f8 = displayerConfig.sProjectionOffsetX + f12;
                    } else {
                        f7 = ascent;
                        f8 = f12;
                    }
                    drawStroke(baseDanmaku, strArr[0], canvas, f8, f7, paint);
                }
                displayerConfig.applyPaintConfig(baseDanmaku, paint, false);
                drawText(baseDanmaku, strArr[0], canvas, f12, f11 - paint.ascent(), paint, z);
            } else {
                float length = (baseDanmaku.paintHeight - (baseDanmaku.padding * 2)) / strArr.length;
                int i3 = 0;
                while (i3 < strArr.length) {
                    if (strArr[i3] == null || strArr[i3].length() == 0) {
                        i = i3;
                    } else {
                        if (displayerConfig.hasStroke(baseDanmaku)) {
                            displayerConfig.applyPaintConfig(baseDanmaku, paint, z2);
                            float ascent2 = ((i3 * length) + f11) - paint.ascent();
                            if (displayerConfig.HAS_PROJECTION) {
                                f5 = ascent2 + displayerConfig.sProjectionOffsetY;
                                f6 = displayerConfig.sProjectionOffsetX + f12;
                            } else {
                                f5 = ascent2;
                                f6 = f12;
                            }
                            i = i3;
                            drawStroke(baseDanmaku, strArr[i3], canvas, f6, f5, paint);
                        } else {
                            i = i3;
                        }
                        displayerConfig.applyPaintConfig(baseDanmaku, paint, z3);
                        drawText(baseDanmaku, strArr[i], canvas, f12, ((i * length) + f11) - paint.ascent(), paint, z);
                    }
                    i3 = i + 1;
                    z3 = false;
                    z2 = true;
                }
            }
        } else {
            if (displayerConfig.hasStroke(baseDanmaku)) {
                displayerConfig.applyPaintConfig(baseDanmaku, paint, true);
                float ascent3 = f11 - paint.ascent();
                if (displayerConfig.HAS_PROJECTION) {
                    f3 = ascent3 + displayerConfig.sProjectionOffsetY;
                    f4 = displayerConfig.sProjectionOffsetX + f12;
                } else {
                    f3 = ascent3;
                    f4 = f12;
                }
                drawStroke(baseDanmaku, null, canvas, f4, f3, paint);
            }
            displayerConfig.applyPaintConfig(baseDanmaku, paint, false);
            drawText(baseDanmaku, null, canvas, f12, f11 - paint.ascent(), paint, z);
        }
        if (baseDanmaku.underlineColor != 0) {
            Paint underlinePaint = displayerConfig.getUnderlinePaint(baseDanmaku);
            float f13 = (f2 + baseDanmaku.paintHeight) - displayerConfig.UNDERLINE_HEIGHT;
            canvas.drawLine(f, f13, f + baseDanmaku.paintWidth, f13, underlinePaint);
        }
        if (baseDanmaku.borderColor != 0) {
            canvas.drawRect(f, f2, f + baseDanmaku.paintWidth, f2 + baseDanmaku.paintHeight, displayerConfig.getBorderPaint(baseDanmaku));
        }
    }
}
