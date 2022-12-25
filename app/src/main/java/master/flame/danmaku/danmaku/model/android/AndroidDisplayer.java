package master.flame.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.p002v4.view.ViewCompat;
import android.text.TextPaint;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

/* loaded from: classes4.dex */
public class AndroidDisplayer extends AbsDisplayer<Canvas, Typeface> {
    public Canvas canvas;
    private int height;
    private float locationZ;
    private int width;
    private Camera camera = new Camera();
    private Matrix matrix = new Matrix();
    private final DisplayerConfig mDisplayConfig = new DisplayerConfig();
    private BaseCacheStuffer sStuffer = new SimpleTextCacheStuffer();
    private float density = 1.0f;
    private int densityDpi = 160;
    private float scaledDensity = 1.0f;
    private int mSlopPixel = 0;
    private boolean mIsHardwareAccelerated = true;
    private int mMaximumBitmapWidth = 2048;
    private int mMaximumBitmapHeight = 2048;

    /* loaded from: classes4.dex */
    public static class DisplayerConfig {
        private boolean isTranslucent;
        private float sLastScaleTextSize;
        private final Map<Float, Float> sCachedScaleSize = new HashMap(10);
        public int UNDERLINE_HEIGHT = 4;
        private float SHADOW_RADIUS = 4.0f;
        private float STROKE_WIDTH = 3.5f;
        public float sProjectionOffsetX = 1.0f;
        public float sProjectionOffsetY = 1.0f;
        private int sProjectionAlpha = 204;
        public boolean CONFIG_HAS_SHADOW = false;
        private boolean HAS_SHADOW = this.CONFIG_HAS_SHADOW;
        public boolean CONFIG_HAS_STROKE = true;
        private boolean HAS_STROKE = this.CONFIG_HAS_STROKE;
        public boolean CONFIG_HAS_PROJECTION = false;
        public boolean HAS_PROJECTION = this.CONFIG_HAS_PROJECTION;
        public boolean CONFIG_ANTI_ALIAS = true;
        private boolean ANTI_ALIAS = this.CONFIG_ANTI_ALIAS;
        private int transparency = AlphaValue.MAX;
        private float scaleTextSize = 1.0f;
        private boolean isTextScaled = false;
        private int margin = 0;
        private int allMarginTop = 0;
        public final TextPaint PAINT = new TextPaint();
        public final TextPaint PAINT_DUPLICATE = new TextPaint(this.PAINT);
        private Paint ALPHA_PAINT = new Paint();
        private Paint UNDERLINE_PAINT = new Paint();
        private Paint BORDER_PAINT = new Paint();

        public DisplayerConfig() {
            this.PAINT.setStrokeWidth(this.STROKE_WIDTH);
            this.UNDERLINE_PAINT.setStrokeWidth(this.UNDERLINE_HEIGHT);
            this.UNDERLINE_PAINT.setStyle(Paint.Style.STROKE);
            this.BORDER_PAINT.setStyle(Paint.Style.STROKE);
            this.BORDER_PAINT.setStrokeWidth(4.0f);
        }

        public void setFakeBoldText(boolean z) {
            this.PAINT.setFakeBoldText(z);
        }

        public void setTransparency(int i) {
            this.isTranslucent = i != AlphaValue.MAX;
            this.transparency = i;
        }

        public void setScaleTextSizeFactor(float f) {
            this.isTextScaled = f != 1.0f;
            this.scaleTextSize = f;
        }

        private void applyTextScaleConfig(BaseDanmaku baseDanmaku, Paint paint) {
            if (!this.isTextScaled) {
                return;
            }
            Float f = this.sCachedScaleSize.get(Float.valueOf(baseDanmaku.textSize));
            if (f == null || this.sLastScaleTextSize != this.scaleTextSize) {
                float f2 = this.scaleTextSize;
                this.sLastScaleTextSize = f2;
                f = Float.valueOf(baseDanmaku.textSize * f2);
                this.sCachedScaleSize.put(Float.valueOf(baseDanmaku.textSize), f);
            }
            paint.setTextSize(f.floatValue());
        }

        public boolean hasStroke(BaseDanmaku baseDanmaku) {
            return (this.HAS_STROKE || this.HAS_PROJECTION) && this.STROKE_WIDTH > 0.0f && baseDanmaku.textShadowColor != 0;
        }

        public Paint getBorderPaint(BaseDanmaku baseDanmaku) {
            this.BORDER_PAINT.setColor(baseDanmaku.borderColor);
            return this.BORDER_PAINT;
        }

        public Paint getUnderlinePaint(BaseDanmaku baseDanmaku) {
            this.UNDERLINE_PAINT.setColor(baseDanmaku.underlineColor);
            return this.UNDERLINE_PAINT;
        }

        public TextPaint getPaint(BaseDanmaku baseDanmaku, boolean z) {
            TextPaint textPaint;
            int i;
            if (z) {
                textPaint = this.PAINT;
            } else {
                textPaint = this.PAINT_DUPLICATE;
                textPaint.set(this.PAINT);
            }
            textPaint.setTextSize(baseDanmaku.textSize);
            applyTextScaleConfig(baseDanmaku, textPaint);
            if (this.HAS_SHADOW) {
                float f = this.SHADOW_RADIUS;
                if (f > 0.0f && (i = baseDanmaku.textShadowColor) != 0) {
                    textPaint.setShadowLayer(f, 0.0f, 0.0f, i);
                    textPaint.setAntiAlias(this.ANTI_ALIAS);
                    return textPaint;
                }
            }
            textPaint.clearShadowLayer();
            textPaint.setAntiAlias(this.ANTI_ALIAS);
            return textPaint;
        }

        public void applyPaintConfig(BaseDanmaku baseDanmaku, Paint paint, boolean z) {
            if (this.isTranslucent) {
                if (z) {
                    paint.setStyle(this.HAS_PROJECTION ? Paint.Style.FILL : Paint.Style.FILL_AND_STROKE);
                    paint.setColor(baseDanmaku.textShadowColor & ViewCompat.MEASURED_SIZE_MASK);
                    paint.setAlpha(this.HAS_PROJECTION ? (int) (this.sProjectionAlpha * (this.transparency / AlphaValue.MAX)) : this.transparency);
                } else {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(baseDanmaku.textColor & ViewCompat.MEASURED_SIZE_MASK);
                    paint.setAlpha(this.transparency);
                }
            } else if (z) {
                paint.setStyle(this.HAS_PROJECTION ? Paint.Style.FILL : Paint.Style.FILL_AND_STROKE);
                paint.setColor(baseDanmaku.textShadowColor & ViewCompat.MEASURED_SIZE_MASK);
                paint.setAlpha(this.HAS_PROJECTION ? this.sProjectionAlpha : AlphaValue.MAX);
            } else {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(baseDanmaku.textColor & ViewCompat.MEASURED_SIZE_MASK);
                paint.setAlpha(AlphaValue.MAX);
            }
            if (baseDanmaku.getType() == 7) {
                paint.setAlpha(baseDanmaku.getAlpha());
            }
        }

        public void clearTextHeightCache() {
            this.sCachedScaleSize.clear();
        }

        public float getStrokeWidth() {
            if (this.HAS_SHADOW && this.HAS_STROKE) {
                return Math.max(this.SHADOW_RADIUS, this.STROKE_WIDTH);
            }
            if (this.HAS_SHADOW) {
                return this.SHADOW_RADIUS;
            }
            if (!this.HAS_STROKE) {
                return 0.0f;
            }
            return this.STROKE_WIDTH;
        }

        public void definePaintParams(boolean z) {
            this.HAS_STROKE = this.CONFIG_HAS_STROKE;
            this.HAS_SHADOW = this.CONFIG_HAS_SHADOW;
            this.HAS_PROJECTION = this.CONFIG_HAS_PROJECTION;
            this.ANTI_ALIAS = this.CONFIG_ANTI_ALIAS;
        }
    }

    @SuppressLint({"NewApi"})
    private static final int getMaximumBitmapWidth(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 14) {
            return canvas.getMaximumBitmapWidth();
        }
        return canvas.getWidth();
    }

    @SuppressLint({"NewApi"})
    private static final int getMaximumBitmapHeight(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 14) {
            return canvas.getMaximumBitmapHeight();
        }
        return canvas.getHeight();
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setFakeBoldText(boolean z) {
        this.mDisplayConfig.setFakeBoldText(z);
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setTransparency(int i) {
        this.mDisplayConfig.setTransparency(i);
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setScaleTextSizeFactor(float f) {
        this.mDisplayConfig.setScaleTextSizeFactor(f);
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setCacheStuffer(BaseCacheStuffer baseCacheStuffer) {
        if (baseCacheStuffer != this.sStuffer) {
            this.sStuffer = baseCacheStuffer;
        }
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public BaseCacheStuffer getCacheStuffer() {
        return this.sStuffer;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getMargin() {
        return this.mDisplayConfig.margin;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getAllMarginTop() {
        return this.mDisplayConfig.allMarginTop;
    }

    private void update(Canvas canvas) {
        this.canvas = canvas;
        if (canvas != null) {
            this.width = canvas.getWidth();
            this.height = canvas.getHeight();
            if (!this.mIsHardwareAccelerated) {
                return;
            }
            this.mMaximumBitmapWidth = getMaximumBitmapWidth(canvas);
            this.mMaximumBitmapHeight = getMaximumBitmapHeight(canvas);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getWidth() {
        return this.width;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getHeight() {
        return this.height;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public float getDensity() {
        return this.density;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getDensityDpi() {
        return this.densityDpi;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int draw(BaseDanmaku baseDanmaku) {
        Paint paint;
        boolean z;
        boolean z2;
        float top2 = baseDanmaku.getTop();
        float left = baseDanmaku.getLeft();
        if (this.canvas != null) {
            Paint paint2 = null;
            int i = 1;
            if (baseDanmaku.getType() != 7) {
                paint = null;
                z = false;
            } else if (baseDanmaku.getAlpha() == AlphaValue.TRANSPARENT) {
                return 0;
            } else {
                if (baseDanmaku.rotationZ == 0.0f && baseDanmaku.rotationY == 0.0f) {
                    z2 = false;
                } else {
                    saveCanvas(baseDanmaku, this.canvas, left, top2);
                    z2 = true;
                }
                if (baseDanmaku.getAlpha() != AlphaValue.MAX) {
                    paint2 = this.mDisplayConfig.ALPHA_PAINT;
                    paint2.setAlpha(baseDanmaku.getAlpha());
                }
                paint = paint2;
                z = z2;
            }
            if (paint != null && paint.getAlpha() == AlphaValue.TRANSPARENT) {
                return 0;
            }
            if (!this.sStuffer.drawCache(baseDanmaku, this.canvas, left, top2, paint, this.mDisplayConfig.PAINT)) {
                if (paint != null) {
                    this.mDisplayConfig.PAINT.setAlpha(paint.getAlpha());
                    this.mDisplayConfig.PAINT_DUPLICATE.setAlpha(paint.getAlpha());
                } else {
                    resetPaintAlpha(this.mDisplayConfig.PAINT);
                }
                drawDanmaku(baseDanmaku, this.canvas, left, top2, false);
                i = 2;
            }
            if (z) {
                restoreCanvas(this.canvas);
            }
            return i;
        }
        return 0;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void recycle(BaseDanmaku baseDanmaku) {
        BaseCacheStuffer baseCacheStuffer = this.sStuffer;
        if (baseCacheStuffer != null) {
            baseCacheStuffer.releaseResource(baseDanmaku);
        }
    }

    private void resetPaintAlpha(Paint paint) {
        int alpha = paint.getAlpha();
        int i = AlphaValue.MAX;
        if (alpha != i) {
            paint.setAlpha(i);
        }
    }

    private void restoreCanvas(Canvas canvas) {
        canvas.restore();
    }

    private int saveCanvas(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2) {
        this.camera.save();
        float f3 = this.locationZ;
        if (f3 != 0.0f && Build.VERSION.SDK_INT >= 12) {
            this.camera.setLocation(0.0f, 0.0f, f3);
        }
        this.camera.rotateY(-baseDanmaku.rotationY);
        this.camera.rotateZ(-baseDanmaku.rotationZ);
        this.camera.getMatrix(this.matrix);
        this.matrix.preTranslate(-f, -f2);
        this.matrix.postTranslate(f, f2);
        this.camera.restore();
        int save = canvas.save();
        canvas.concat(this.matrix);
        return save;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public synchronized void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2, boolean z) {
        if (this.sStuffer != null) {
            this.sStuffer.drawDanmaku(baseDanmaku, canvas, f, f2, z, this.mDisplayConfig);
        }
    }

    private synchronized TextPaint getPaint(BaseDanmaku baseDanmaku, boolean z) {
        return this.mDisplayConfig.getPaint(baseDanmaku, z);
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void prepare(BaseDanmaku baseDanmaku, boolean z) {
        BaseCacheStuffer baseCacheStuffer = this.sStuffer;
        if (baseCacheStuffer != null) {
            baseCacheStuffer.prepare(baseDanmaku, z);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void measure(BaseDanmaku baseDanmaku, boolean z) {
        TextPaint paint = getPaint(baseDanmaku, z);
        if (this.mDisplayConfig.HAS_STROKE) {
            this.mDisplayConfig.applyPaintConfig(baseDanmaku, paint, true);
        }
        calcPaintWH(baseDanmaku, paint, z);
        if (this.mDisplayConfig.HAS_STROKE) {
            this.mDisplayConfig.applyPaintConfig(baseDanmaku, paint, false);
        }
    }

    private void calcPaintWH(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z) {
        this.sStuffer.measure(baseDanmaku, textPaint, z);
        setDanmakuPaintWidthAndHeight(baseDanmaku, baseDanmaku.paintWidth, baseDanmaku.paintHeight);
    }

    private void setDanmakuPaintWidthAndHeight(BaseDanmaku baseDanmaku, float f, float f2) {
        int i = baseDanmaku.padding;
        float f3 = f + (i * 2);
        float f4 = f2 + (i * 2);
        if (baseDanmaku.borderColor != 0) {
            float f5 = 8;
            f3 += f5;
            f4 += f5;
        }
        baseDanmaku.paintWidth = f3 + getStrokeWidth();
        baseDanmaku.paintHeight = f4;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void clearTextHeightCache() {
        this.sStuffer.clearCaches();
        this.mDisplayConfig.clearTextHeightCache();
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public float getScaledDensity() {
        return this.scaledDensity;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void resetSlopPixel(float f) {
        float max = Math.max(f, getWidth() / 682.0f) * 25.0f;
        this.mSlopPixel = (int) max;
        if (f > 1.0f) {
            this.mSlopPixel = (int) (max * f);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getSlopPixel() {
        return this.mSlopPixel;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setDensities(float f, int i, float f2) {
        this.density = f;
        this.densityDpi = i;
        this.scaledDensity = f2;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
        this.locationZ = (float) ((i / 2.0f) / Math.tan(0.4799655442984406d));
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setExtraData(Canvas canvas) {
        update(canvas);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    /* renamed from: getExtraData */
    public Canvas mo6799getExtraData() {
        return this.canvas;
    }

    public float getStrokeWidth() {
        return this.mDisplayConfig.getStrokeWidth();
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setHardwareAccelerated(boolean z) {
        this.mIsHardwareAccelerated = z;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer, master.flame.danmaku.danmaku.model.IDisplayer
    public boolean isHardwareAccelerated() {
        return this.mIsHardwareAccelerated;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getMaximumCacheWidth() {
        return this.mMaximumBitmapWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getMaximumCacheHeight() {
        return this.mMaximumBitmapHeight;
    }
}
