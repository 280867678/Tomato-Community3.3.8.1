package com.soundcloud.android.crop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.p002v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class HighlightView {
    RectF cropRect;
    Rect drawRect;
    private float handleRadius;
    private int highlightColor;
    private RectF imageRect;
    private float initialAspectRatio;
    private boolean isFocused;
    private boolean maintainAspectRatio;
    Matrix matrix;
    private float outlineWidth;
    private boolean showCircle;
    private boolean showThirds;
    private View viewContext;
    private final Paint outsidePaint = new Paint();
    private final Paint outlinePaint = new Paint();
    private final Paint handlePaint = new Paint();
    private ModifyMode modifyMode = ModifyMode.None;
    private HandleMode handleMode = HandleMode.Changing;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum HandleMode {
        Changing,
        Always,
        Never
    }

    /* loaded from: classes3.dex */
    enum ModifyMode {
        None,
        Move,
        Grow
    }

    public HighlightView(View view) {
        this.viewContext = view;
        initStyles(view.getContext());
    }

    private void initStyles(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R$attr.cropImageStyle, typedValue, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(typedValue.resourceId, R$styleable.CropImageView);
        try {
            this.showThirds = obtainStyledAttributes.getBoolean(R$styleable.CropImageView_showThirds, false);
            this.showCircle = obtainStyledAttributes.getBoolean(R$styleable.CropImageView_showCircle, false);
            this.highlightColor = obtainStyledAttributes.getColor(R$styleable.CropImageView_highlightColor, -13388315);
            this.handleMode = HandleMode.values()[obtainStyledAttributes.getInt(R$styleable.CropImageView_showHandles, 0)];
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setup(Matrix matrix, Rect rect, RectF rectF, boolean z) {
        this.matrix = new Matrix(matrix);
        this.cropRect = rectF;
        this.imageRect = new RectF(rect);
        this.maintainAspectRatio = z;
        this.initialAspectRatio = this.cropRect.width() / this.cropRect.height();
        this.drawRect = computeLayout();
        this.outsidePaint.setARGB(125, 50, 50, 50);
        this.outlinePaint.setStyle(Paint.Style.STROKE);
        this.outlinePaint.setAntiAlias(true);
        this.outlineWidth = dpToPx(2.0f);
        this.handlePaint.setColor(this.highlightColor);
        this.handlePaint.setStyle(Paint.Style.FILL);
        this.handlePaint.setAntiAlias(true);
        this.handleRadius = dpToPx(12.0f);
        this.modifyMode = ModifyMode.None;
    }

    private float dpToPx(float f) {
        return f * this.viewContext.getResources().getDisplayMetrics().density;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void draw(Canvas canvas) {
        canvas.save();
        Path path = new Path();
        this.outlinePaint.setStrokeWidth(this.outlineWidth);
        if (!hasFocus()) {
            this.outlinePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawRect(this.drawRect, this.outlinePaint);
            return;
        }
        Rect rect = new Rect();
        this.viewContext.getDrawingRect(rect);
        path.addRect(new RectF(this.drawRect), Path.Direction.CW);
        this.outlinePaint.setColor(this.highlightColor);
        if (isClipPathSupported(canvas)) {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            canvas.drawRect(rect, this.outsidePaint);
        } else {
            drawOutsideFallback(canvas);
        }
        canvas.restore();
        canvas.drawPath(path, this.outlinePaint);
        if (this.showThirds) {
            drawThirds(canvas);
        }
        if (this.showCircle) {
            drawCircle(canvas);
        }
        HandleMode handleMode = this.handleMode;
        if (handleMode != HandleMode.Always && (handleMode != HandleMode.Changing || this.modifyMode != ModifyMode.Grow)) {
            return;
        }
        drawHandles(canvas);
    }

    private void drawOutsideFallback(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), this.drawRect.top, this.outsidePaint);
        canvas.drawRect(0.0f, this.drawRect.bottom, canvas.getWidth(), canvas.getHeight(), this.outsidePaint);
        Rect rect = this.drawRect;
        canvas.drawRect(0.0f, rect.top, rect.left, rect.bottom, this.outsidePaint);
        Rect rect2 = this.drawRect;
        canvas.drawRect(rect2.right, rect2.top, canvas.getWidth(), this.drawRect.bottom, this.outsidePaint);
    }

    @SuppressLint({"NewApi"})
    private boolean isClipPathSupported(Canvas canvas) {
        int i = Build.VERSION.SDK_INT;
        if (i == 17) {
            return false;
        }
        if (i >= 14 && i <= 15) {
            return !canvas.isHardwareAccelerated();
        }
        return true;
    }

    private void drawHandles(Canvas canvas) {
        Rect rect = this.drawRect;
        int i = rect.left;
        int i2 = ((rect.right - i) / 2) + i;
        int i3 = rect.top;
        int i4 = i3 + ((rect.bottom - i3) / 2);
        float f = i;
        float f2 = i4;
        canvas.drawCircle(f, f2, this.handleRadius, this.handlePaint);
        float f3 = i2;
        canvas.drawCircle(f3, this.drawRect.top, this.handleRadius, this.handlePaint);
        canvas.drawCircle(this.drawRect.right, f2, this.handleRadius, this.handlePaint);
        canvas.drawCircle(f3, this.drawRect.bottom, this.handleRadius, this.handlePaint);
    }

    private void drawThirds(Canvas canvas) {
        Rect rect;
        this.outlinePaint.setStrokeWidth(1.0f);
        Rect rect2 = this.drawRect;
        int i = rect2.right;
        int i2 = rect2.left;
        float f = (i - i2) / 3;
        int i3 = rect2.bottom;
        int i4 = rect2.top;
        float f2 = (i3 - i4) / 3;
        canvas.drawLine(i2 + f, i4, i2 + f, i3, this.outlinePaint);
        int i5 = this.drawRect.left;
        float f3 = f * 2.0f;
        canvas.drawLine(i5 + f3, rect.top, i5 + f3, rect.bottom, this.outlinePaint);
        Rect rect3 = this.drawRect;
        float f4 = rect3.left;
        int i6 = rect3.top;
        canvas.drawLine(f4, i6 + f2, rect3.right, i6 + f2, this.outlinePaint);
        Rect rect4 = this.drawRect;
        float f5 = rect4.left;
        int i7 = rect4.top;
        float f6 = f2 * 2.0f;
        canvas.drawLine(f5, i7 + f6, rect4.right, i7 + f6, this.outlinePaint);
    }

    private void drawCircle(Canvas canvas) {
        this.outlinePaint.setStrokeWidth(1.0f);
        canvas.drawOval(new RectF(this.drawRect), this.outlinePaint);
    }

    public void setMode(ModifyMode modifyMode) {
        if (modifyMode != this.modifyMode) {
            this.modifyMode = modifyMode;
            this.viewContext.invalidate();
        }
    }

    public int getHit(float f, float f2) {
        Rect computeLayout = computeLayout();
        boolean z = false;
        boolean z2 = f2 >= ((float) computeLayout.top) - 20.0f && f2 < ((float) computeLayout.bottom) + 20.0f;
        if (f >= computeLayout.left - 20.0f && f < computeLayout.right + 20.0f) {
            z = true;
        }
        int i = (Math.abs(((float) computeLayout.left) - f) >= 20.0f || !z2) ? 1 : 3;
        if (Math.abs(computeLayout.right - f) < 20.0f && z2) {
            i |= 4;
        }
        if (Math.abs(computeLayout.top - f2) < 20.0f && z) {
            i |= 8;
        }
        if (Math.abs(computeLayout.bottom - f2) < 20.0f && z) {
            i |= 16;
        }
        if (i != 1 || !computeLayout.contains((int) f, (int) f2)) {
            return i;
        }
        return 32;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleMotion(int i, float f, float f2) {
        Rect computeLayout = computeLayout();
        if (i == 32) {
            moveBy(f * (this.cropRect.width() / computeLayout.width()), f2 * (this.cropRect.height() / computeLayout.height()));
            return;
        }
        if ((i & 6) == 0) {
            f = 0.0f;
        }
        if ((i & 24) == 0) {
            f2 = 0.0f;
        }
        float width = f * (this.cropRect.width() / computeLayout.width());
        float height = f2 * (this.cropRect.height() / computeLayout.height());
        int i2 = -1;
        float f3 = ((i & 2) != 0 ? -1 : 1) * width;
        if ((i & 8) == 0) {
            i2 = 1;
        }
        growBy(f3, i2 * height);
    }

    void moveBy(float f, float f2) {
        Rect rect = new Rect(this.drawRect);
        this.cropRect.offset(f, f2);
        RectF rectF = this.cropRect;
        rectF.offset(Math.max(0.0f, this.imageRect.left - rectF.left), Math.max(0.0f, this.imageRect.top - this.cropRect.top));
        RectF rectF2 = this.cropRect;
        rectF2.offset(Math.min(0.0f, this.imageRect.right - rectF2.right), Math.min(0.0f, this.imageRect.bottom - this.cropRect.bottom));
        this.drawRect = computeLayout();
        rect.union(this.drawRect);
        float f3 = this.handleRadius;
        rect.inset(-((int) f3), -((int) f3));
        this.viewContext.invalidate(rect);
    }

    void growBy(float f, float f2) {
        if (this.maintainAspectRatio) {
            if (f != 0.0f) {
                f2 = f / this.initialAspectRatio;
            } else if (f2 != 0.0f) {
                f = this.initialAspectRatio * f2;
            }
        }
        RectF rectF = new RectF(this.cropRect);
        if (f > 0.0f && rectF.width() + (f * 2.0f) > this.imageRect.width()) {
            f = (this.imageRect.width() - rectF.width()) / 2.0f;
            if (this.maintainAspectRatio) {
                f2 = f / this.initialAspectRatio;
            }
        }
        if (f2 > 0.0f && rectF.height() + (f2 * 2.0f) > this.imageRect.height()) {
            f2 = (this.imageRect.height() - rectF.height()) / 2.0f;
            if (this.maintainAspectRatio) {
                f = this.initialAspectRatio * f2;
            }
        }
        rectF.inset(-f, -f2);
        float f3 = 25.0f;
        if (rectF.width() < 25.0f) {
            rectF.inset((-(25.0f - rectF.width())) / 2.0f, 0.0f);
        }
        if (this.maintainAspectRatio) {
            f3 = 25.0f / this.initialAspectRatio;
        }
        if (rectF.height() < f3) {
            rectF.inset(0.0f, (-(f3 - rectF.height())) / 2.0f);
        }
        float f4 = rectF.left;
        RectF rectF2 = this.imageRect;
        float f5 = rectF2.left;
        if (f4 < f5) {
            rectF.offset(f5 - f4, 0.0f);
        } else {
            float f6 = rectF.right;
            float f7 = rectF2.right;
            if (f6 > f7) {
                rectF.offset(-(f6 - f7), 0.0f);
            }
        }
        float f8 = rectF.top;
        RectF rectF3 = this.imageRect;
        float f9 = rectF3.top;
        if (f8 < f9) {
            rectF.offset(0.0f, f9 - f8);
        } else {
            float f10 = rectF.bottom;
            float f11 = rectF3.bottom;
            if (f10 > f11) {
                rectF.offset(0.0f, -(f10 - f11));
            }
        }
        this.cropRect.set(rectF);
        this.drawRect = computeLayout();
        this.viewContext.invalidate();
    }

    public Rect getScaledCropRect(float f) {
        RectF rectF = this.cropRect;
        return new Rect((int) (rectF.left * f), (int) (rectF.top * f), (int) (rectF.right * f), (int) (rectF.bottom * f));
    }

    private Rect computeLayout() {
        RectF rectF = this.cropRect;
        RectF rectF2 = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        this.matrix.mapRect(rectF2);
        return new Rect(Math.round(rectF2.left), Math.round(rectF2.top), Math.round(rectF2.right), Math.round(rectF2.bottom));
    }

    public void invalidate() {
        this.drawRect = computeLayout();
    }

    public boolean hasFocus() {
        return this.isFocused;
    }

    public void setFocus(boolean z) {
        this.isFocused = z;
    }
}
