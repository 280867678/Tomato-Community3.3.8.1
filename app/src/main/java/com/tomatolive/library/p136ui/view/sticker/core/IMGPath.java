package com.tomatolive.library.p136ui.view.sticker.core;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.p002v4.internal.view.SupportMenu;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.IMGPath */
/* loaded from: classes3.dex */
public class IMGPath {
    public static final float BASE_DOODLE_WIDTH = 20.0f;
    public static final float BASE_MOSAIC_WIDTH = 72.0f;
    private int color;
    private IMGMode mode;
    protected Path path;
    private float width;

    public IMGPath() {
        this(new Path());
    }

    public IMGPath(Path path) {
        this(path, IMGMode.DOODLE);
    }

    public IMGPath(Path path, IMGMode iMGMode) {
        this(path, iMGMode, SupportMenu.CATEGORY_MASK);
    }

    public IMGPath(Path path, IMGMode iMGMode, int i) {
        this(path, iMGMode, i, 72.0f);
    }

    public IMGPath(Path path, IMGMode iMGMode, int i, float f) {
        this.color = SupportMenu.CATEGORY_MASK;
        this.width = 72.0f;
        this.mode = IMGMode.DOODLE;
        this.path = path;
        this.mode = iMGMode;
        this.color = i;
        this.width = f;
        if (iMGMode == IMGMode.MOSAIC) {
            path.setFillType(Path.FillType.EVEN_ODD);
        }
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public IMGMode getMode() {
        return this.mode;
    }

    public void setMode(IMGMode iMGMode) {
        this.mode = iMGMode;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public float getWidth() {
        return this.width;
    }

    public void onDrawDoodle(Canvas canvas, Paint paint) {
        if (this.mode == IMGMode.DOODLE) {
            paint.setColor(this.color);
            paint.setStrokeWidth(20.0f);
            canvas.drawPath(this.path, paint);
        }
    }

    public void onDrawMosaic(Canvas canvas, Paint paint) {
        if (this.mode == IMGMode.MOSAIC) {
            paint.setStrokeWidth(this.width);
            canvas.drawPath(this.path, paint);
        }
    }

    public void transform(Matrix matrix) {
        this.path.transform(matrix);
    }
}
