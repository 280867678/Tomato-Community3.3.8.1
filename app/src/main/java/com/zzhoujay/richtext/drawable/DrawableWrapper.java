package com.zzhoujay.richtext.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.zzhoujay.richtext.ImageHolder;

/* loaded from: classes4.dex */
public class DrawableWrapper extends Drawable {
    private Drawable drawable;
    private DrawableSizeHolder sizeHolder;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float translateX = 0.0f;
    private float translateY = 0.0f;
    private Paint paint = new Paint();
    private boolean hasCache = false;

    public DrawableWrapper(ImageHolder imageHolder) {
        this.paint.setAntiAlias(true);
        this.sizeHolder = new DrawableSizeHolder(imageHolder);
        setUpBorderHolder(this.sizeHolder.borderHolder);
        setBounds(this.sizeHolder.border);
    }

    public DrawableWrapper(DrawableSizeHolder drawableSizeHolder) {
        this.paint.setAntiAlias(true);
        this.sizeHolder = drawableSizeHolder;
        setUpBorderHolder(drawableSizeHolder.borderHolder);
        setBounds(drawableSizeHolder.border);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap;
        canvas.save();
        if (this.drawable != null) {
            canvas.clipRect(getBounds());
            Drawable drawable = this.drawable;
            if ((drawable instanceof BitmapDrawable) && ((bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.isRecycled())) {
                return;
            }
            drawImage(canvas);
        }
        drawBorder(canvas);
        canvas.restore();
    }

    private void drawBorder(Canvas canvas) {
        DrawableBorderHolder drawableBorderHolder;
        DrawableSizeHolder drawableSizeHolder = this.sizeHolder;
        if (drawableSizeHolder == null || (drawableBorderHolder = drawableSizeHolder.borderHolder) == null || !drawableBorderHolder.isShowBorder()) {
            return;
        }
        DrawableSizeHolder drawableSizeHolder2 = this.sizeHolder;
        if (drawableSizeHolder2.border == null) {
            return;
        }
        float radius = drawableSizeHolder2.borderHolder.getRadius();
        canvas.drawRoundRect(this.sizeHolder.border, radius, radius, this.paint);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.drawable;
        if (drawable == null) {
            return -2;
        }
        return drawable.getOpacity();
    }

    public DrawableSizeHolder getSizeHolder() {
        return this.sizeHolder;
    }

    private void setUpBorderHolder(DrawableBorderHolder drawableBorderHolder) {
        if (drawableBorderHolder != null) {
            this.paint.setColor(drawableBorderHolder.getBorderColor());
            this.paint.setStrokeWidth(drawableBorderHolder.getBorderSize());
            this.paint.setStyle(Paint.Style.STROKE);
        }
    }

    public void calculate() {
        int width;
        int height;
        Drawable drawable = this.drawable;
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        } else if (drawable instanceof GifDrawable) {
            GifDrawable gifDrawable = (GifDrawable) drawable;
            width = gifDrawable.getWidth();
            height = gifDrawable.getHeight();
        } else {
            Rect bounds = drawable.getBounds();
            width = bounds.width();
            height = bounds.height();
        }
        int i = height;
        int i2 = width;
        Rect bounds2 = getBounds();
        int width2 = bounds2.width();
        int height2 = bounds2.height();
        if (width2 <= 0 || height2 <= 0) {
            return;
        }
        DrawableSizeHolder drawableSizeHolder = this.sizeHolder;
        switch (C52021.$SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[(drawableSizeHolder == null ? ImageHolder.ScaleType.none : drawableSizeHolder.scaleType).ordinal()]) {
            case 1:
                none(i2, i, width2, height2);
                return;
            case 2:
                center(i2, i, width2, height2);
                return;
            case 3:
                centerCrop(i2, i, width2, height2);
                return;
            case 4:
                centerInside(i2, i, width2, height2);
                return;
            case 5:
                fitCenter(i2, i, width2, height2, 0);
                return;
            case 6:
                fitCenter(i2, i, width2, height2, -1);
                return;
            case 7:
                fitCenter(i2, i, width2, height2, 1);
                return;
            case 8:
                fitXY(i2, i, width2, height2);
                return;
            case 9:
                fitAuto(i2, i, width2, height2);
                return;
            default:
                return;
        }
    }

    /* renamed from: com.zzhoujay.richtext.drawable.DrawableWrapper$1 */
    /* loaded from: classes4.dex */
    static /* synthetic */ class C52021 {
        static final /* synthetic */ int[] $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType = new int[ImageHolder.ScaleType.values().length];

        static {
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.none.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.center.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.center_crop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.center_inside.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.fit_center.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.fit_start.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.fit_end.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.fit_xy.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$zzhoujay$richtext$ImageHolder$ScaleType[ImageHolder.ScaleType.fit_auto.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private void drawImage(Canvas canvas) {
        canvas.save();
        canvas.scale(this.scaleX, this.scaleY);
        canvas.translate(this.translateX, this.translateY);
        this.drawable.draw(canvas);
        canvas.restore();
    }

    private void none(int i, int i2, int i3, int i4) {
        this.translateX = 0.0f;
        this.translateY = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
    }

    private void center(int i, int i2, int i3, int i4) {
        this.translateX = (i3 - i) / 2.0f;
        this.translateY = (i4 - i2) / 2.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
    }

    private void fitAuto(int i, int i2, int i3, int i4) {
        DrawableSizeHolder drawableSizeHolder;
        float f = i3;
        float f2 = f / i;
        int i5 = (int) (i2 * f2);
        this.scaleX = f2;
        this.scaleY = f2;
        this.translateX = 0.0f;
        this.translateY = 0.0f;
        setBounds(0, 0, i3, i5);
        if (!this.hasCache || (drawableSizeHolder = this.sizeHolder) == null) {
            return;
        }
        drawableSizeHolder.border.set(0.0f, 0.0f, f, i5);
    }

    private void fitXY(int i, int i2, int i3, int i4) {
        this.scaleX = i3 / i;
        this.scaleY = i4 / i2;
        this.translateX = 0.0f;
        this.translateY = 0.0f;
    }

    private void fitCenter(int i, int i2, int i3, int i4, int i5) {
        float f = i3;
        float f2 = i;
        float f3 = f / f2;
        float f4 = i4;
        float f5 = i2;
        float f6 = f4 / f5;
        float min = Math.min(f3, f6);
        boolean z = f3 > f6;
        float f7 = f - (f2 * min);
        float f8 = f7 / 2.0f;
        float f9 = f4 - (f5 * min);
        float f10 = f9 / 2.0f;
        if (i5 < 0) {
            if (z) {
                f8 = 0.0f;
            } else {
                f10 = 0.0f;
            }
        } else if (i5 > 0) {
            if (z) {
                f8 = f7;
            } else {
                f10 = f9;
            }
        }
        this.scaleX = min;
        this.scaleY = min;
        this.translateX = f8 / min;
        this.translateY = f10 / min;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (r0 < 1.0f) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void centerInside(int i, int i2, int i3, int i4) {
        float f = i3;
        float f2 = i;
        float f3 = f / f2;
        float f4 = i4;
        float f5 = i2;
        float f6 = f4 / f5;
        if (f3 >= f6) {
            if (f6 < 1.0f) {
                f3 = f6;
            }
            f3 = 1.0f;
        }
        float f7 = f - (f2 * f3);
        float f8 = 2.0f * f3;
        this.translateX = f7 / f8;
        this.translateY = (f4 - (f5 * f3)) / f8;
        this.scaleX = f3;
        this.scaleY = f3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (r0 > 1.0f) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void centerCrop(int i, int i2, int i3, int i4) {
        float f = i3;
        float f2 = i;
        float f3 = f / f2;
        float f4 = i4;
        float f5 = i2;
        float f6 = f4 / f5;
        if (f3 <= f6) {
            if (f6 > 1.0f) {
                f3 = f6;
            }
            f3 = 1.0f;
        }
        float f7 = f - (f2 * f3);
        float f8 = 2.0f * f3;
        this.translateX = f7 / f8;
        this.translateY = (f4 - (f5 * f3)) / f8;
        this.scaleX = f3;
        this.scaleY = f3;
    }

    private void setBounds(RectF rectF) {
        setBounds(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    private void setBounds(float f, float f2, float f3, float f4) {
        setBounds((int) f, (int) f2, (int) f3, (int) f4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        DrawableSizeHolder drawableSizeHolder;
        super.setBounds(i, i2, i3, i4);
        if (this.hasCache || (drawableSizeHolder = this.sizeHolder) == null) {
            return;
        }
        drawableSizeHolder.border.set(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(@NonNull Rect rect) {
        DrawableSizeHolder drawableSizeHolder;
        super.setBounds(rect);
        if (this.hasCache || (drawableSizeHolder = this.sizeHolder) == null) {
            return;
        }
        drawableSizeHolder.border.set(rect);
    }

    public void setScaleType(ImageHolder.ScaleType scaleType) {
        DrawableSizeHolder drawableSizeHolder;
        if (this.hasCache || (drawableSizeHolder = this.sizeHolder) == null) {
            return;
        }
        drawableSizeHolder.scaleType = scaleType;
    }

    public void setBorderHolder(DrawableBorderHolder drawableBorderHolder) {
        DrawableSizeHolder drawableSizeHolder;
        if (this.hasCache || (drawableSizeHolder = this.sizeHolder) == null) {
            return;
        }
        drawableSizeHolder.borderHolder.set(drawableBorderHolder);
    }

    public boolean isHasCache() {
        return this.hasCache;
    }
}
