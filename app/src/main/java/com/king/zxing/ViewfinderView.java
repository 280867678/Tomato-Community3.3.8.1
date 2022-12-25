package com.king.zxing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class ViewfinderView extends View {
    private int cornerColor;
    private int cornerRectHeight;
    private int cornerRectWidth;
    private Rect frame;
    private int frameColor;
    private int frameHeight;
    private int frameLineWidth;
    private float frameRatio;
    private int frameWidth;
    private int gridColumn;
    private int gridHeight;
    private boolean isShowResultPoint;
    private String labelText;
    private int labelTextColor;
    private TextLocation labelTextLocation;
    private float labelTextPadding;
    private float labelTextSize;
    private int laserColor;
    private LaserStyle laserStyle;
    private List<ResultPoint> lastPossibleResultPoints;
    private int maskColor;
    private Paint paint;
    private List<ResultPoint> possibleResultPoints;
    private int resultPointColor;
    private int scannerAnimationDelay;
    public int scannerEnd;
    private int scannerLineHeight;
    private int scannerLineMoveDistance;
    public int scannerStart;
    private int screenHeight;
    private int screenWidth;
    private TextPaint textPaint;

    /* loaded from: classes3.dex */
    public enum LaserStyle {
        NONE(0),
        LINE(1),
        GRID(2);
        
        private int mValue;

        LaserStyle(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static LaserStyle getFromInt(int i) {
            LaserStyle[] values;
            for (LaserStyle laserStyle : values()) {
                if (laserStyle.mValue == i) {
                    return laserStyle;
                }
            }
            return LINE;
        }
    }

    /* loaded from: classes3.dex */
    public enum TextLocation {
        TOP(0),
        BOTTOM(1);
        
        private int mValue;

        TextLocation(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TextLocation getFromInt(int i) {
            TextLocation[] values;
            for (TextLocation textLocation : values()) {
                if (textLocation.mValue == i) {
                    return textLocation;
                }
            }
            return TOP;
        }
    }

    public ViewfinderView(Context context) {
        this(context, null);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.scannerStart = 0;
        this.scannerEnd = 0;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ViewfinderView);
        this.maskColor = obtainStyledAttributes.getColor(R$styleable.ViewfinderView_maskColor, ContextCompat.getColor(context, R$color.viewfinder_mask));
        this.frameColor = obtainStyledAttributes.getColor(R$styleable.ViewfinderView_frameColor, ContextCompat.getColor(context, R$color.viewfinder_frame));
        this.cornerColor = obtainStyledAttributes.getColor(R$styleable.ViewfinderView_cornerColor, ContextCompat.getColor(context, R$color.viewfinder_corner));
        this.laserColor = obtainStyledAttributes.getColor(R$styleable.ViewfinderView_laserColor, ContextCompat.getColor(context, R$color.viewfinder_laser));
        this.resultPointColor = obtainStyledAttributes.getColor(R$styleable.ViewfinderView_resultPointColor, ContextCompat.getColor(context, R$color.viewfinder_result_point_color));
        this.labelText = obtainStyledAttributes.getString(R$styleable.ViewfinderView_labelText);
        this.labelTextColor = obtainStyledAttributes.getColor(R$styleable.ViewfinderView_labelTextColor, ContextCompat.getColor(context, R$color.viewfinder_text_color));
        this.labelTextSize = obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_labelTextSize, TypedValue.applyDimension(2, 14.0f, getResources().getDisplayMetrics()));
        this.labelTextPadding = obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_labelTextPadding, TypedValue.applyDimension(1, 24.0f, getResources().getDisplayMetrics()));
        this.labelTextLocation = TextLocation.getFromInt(obtainStyledAttributes.getInt(R$styleable.ViewfinderView_labelTextLocation, 0));
        this.isShowResultPoint = obtainStyledAttributes.getBoolean(R$styleable.ViewfinderView_showResultPoint, false);
        this.frameWidth = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ViewfinderView_frameWidth, 0);
        this.frameHeight = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ViewfinderView_frameHeight, 0);
        this.laserStyle = LaserStyle.getFromInt(obtainStyledAttributes.getInt(R$styleable.ViewfinderView_laserStyle, LaserStyle.LINE.mValue));
        this.gridColumn = obtainStyledAttributes.getInt(R$styleable.ViewfinderView_gridColumn, 20);
        this.gridHeight = (int) obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_gridHeight, TypedValue.applyDimension(1, 40.0f, getResources().getDisplayMetrics()));
        this.cornerRectWidth = (int) obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_cornerRectWidth, TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics()));
        this.cornerRectHeight = (int) obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_cornerRectHeight, TypedValue.applyDimension(1, 16.0f, getResources().getDisplayMetrics()));
        this.scannerLineMoveDistance = (int) obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_scannerLineMoveDistance, TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics()));
        this.scannerLineHeight = (int) obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_scannerLineHeight, TypedValue.applyDimension(1, 5.0f, getResources().getDisplayMetrics()));
        this.frameLineWidth = (int) obtainStyledAttributes.getDimension(R$styleable.ViewfinderView_frameLineWidth, TypedValue.applyDimension(1, 1.0f, getResources().getDisplayMetrics()));
        this.scannerAnimationDelay = obtainStyledAttributes.getInteger(R$styleable.ViewfinderView_scannerAnimationDelay, 15);
        this.frameRatio = obtainStyledAttributes.getFloat(R$styleable.ViewfinderView_frameRatio, 0.625f);
        obtainStyledAttributes.recycle();
        this.paint = new Paint(1);
        this.textPaint = new TextPaint(1);
        this.possibleResultPoints = new ArrayList(5);
        this.lastPossibleResultPoints = null;
        this.screenWidth = getDisplayMetrics().widthPixels;
        this.screenHeight = getDisplayMetrics().heightPixels;
        int min = (int) (Math.min(this.screenWidth, this.screenHeight) * this.frameRatio);
        int i = this.frameWidth;
        if (i <= 0 || i > this.screenWidth) {
            this.frameWidth = min;
        }
        int i2 = this.frameHeight;
        if (i2 <= 0 || i2 > this.screenHeight) {
            this.frameHeight = min;
        }
    }

    private DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    public void setLabelText(String str) {
        this.labelText = str;
    }

    public void setLabelTextColor(@ColorInt int i) {
        this.labelTextColor = i;
    }

    public void setLabelTextColorResource(@ColorRes int i) {
        this.labelTextColor = ContextCompat.getColor(getContext(), i);
    }

    public void setLabelTextSize(float f) {
        this.labelTextSize = f;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int paddingLeft = (((this.screenWidth - this.frameWidth) / 2) + getPaddingLeft()) - getPaddingRight();
        int paddingTop = (((this.screenHeight - this.frameHeight) / 2) + getPaddingTop()) - getPaddingBottom();
        this.frame = new Rect(paddingLeft, paddingTop, this.frameWidth + paddingLeft, this.frameHeight + paddingTop);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.frame == null) {
            return;
        }
        if (this.scannerStart == 0 || this.scannerEnd == 0) {
            Rect rect = this.frame;
            this.scannerStart = rect.top;
            this.scannerEnd = rect.bottom - this.scannerLineHeight;
        }
        drawExterior(canvas, this.frame, canvas.getWidth(), canvas.getHeight());
        drawLaserScanner(canvas, this.frame);
        drawFrame(canvas, this.frame);
        drawCorner(canvas, this.frame);
        drawTextInfo(canvas, this.frame);
        drawResultPoint(canvas, this.frame);
        long j = this.scannerAnimationDelay;
        Rect rect2 = this.frame;
        postInvalidateDelayed(j, rect2.left - 20, rect2.top - 20, rect2.right + 20, rect2.bottom + 20);
    }

    private void drawTextInfo(Canvas canvas, Rect rect) {
        if (!TextUtils.isEmpty(this.labelText)) {
            this.textPaint.setColor(this.labelTextColor);
            this.textPaint.setTextSize(this.labelTextSize);
            this.textPaint.setTextAlign(Paint.Align.CENTER);
            StaticLayout staticLayout = new StaticLayout(this.labelText, this.textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            if (this.labelTextLocation == TextLocation.BOTTOM) {
                canvas.translate(rect.left + (rect.width() / 2), rect.bottom + this.labelTextPadding);
                staticLayout.draw(canvas);
                return;
            }
            canvas.translate(rect.left + (rect.width() / 2), (rect.top - this.labelTextPadding) - staticLayout.getHeight());
            staticLayout.draw(canvas);
        }
    }

    private void drawCorner(Canvas canvas, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        this.paint.setColor(this.cornerColor);
        canvas.drawRect(rect.left, rect.top, i + this.cornerRectWidth, i2 + this.cornerRectHeight, this.paint);
        canvas.drawRect(rect.left, rect.top, i3 + this.cornerRectHeight, i4 + this.cornerRectWidth, this.paint);
        int i14 = rect.right;
        canvas.drawRect(i14 - this.cornerRectWidth, rect.top, i14, i5 + this.cornerRectHeight, this.paint);
        int i15 = rect.right;
        canvas.drawRect(i15 - this.cornerRectHeight, rect.top, i15, i6 + this.cornerRectWidth, this.paint);
        canvas.drawRect(rect.left, i8 - this.cornerRectWidth, i7 + this.cornerRectHeight, rect.bottom, this.paint);
        canvas.drawRect(rect.left, i10 - this.cornerRectHeight, i9 + this.cornerRectWidth, rect.bottom, this.paint);
        int i16 = rect.right;
        canvas.drawRect(i16 - this.cornerRectWidth, i11 - this.cornerRectHeight, i16, rect.bottom, this.paint);
        canvas.drawRect(i12 - this.cornerRectHeight, i13 - this.cornerRectWidth, rect.right, rect.bottom, this.paint);
    }

    private void drawLaserScanner(Canvas canvas, Rect rect) {
        if (this.laserStyle != null) {
            this.paint.setColor(this.laserColor);
            int i = C22001.$SwitchMap$com$king$zxing$ViewfinderView$LaserStyle[this.laserStyle.ordinal()];
            if (i == 1) {
                drawLineScanner(canvas, rect);
            } else if (i == 2) {
                drawGridScanner(canvas, rect);
            }
            this.paint.setShader(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.king.zxing.ViewfinderView$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C22001 {
        static final /* synthetic */ int[] $SwitchMap$com$king$zxing$ViewfinderView$LaserStyle = new int[LaserStyle.values().length];

        static {
            try {
                $SwitchMap$com$king$zxing$ViewfinderView$LaserStyle[LaserStyle.LINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$king$zxing$ViewfinderView$LaserStyle[LaserStyle.GRID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void drawLineScanner(Canvas canvas, Rect rect) {
        int i;
        int i2 = rect.left;
        this.paint.setShader(new LinearGradient(i2, this.scannerStart, i2, i + this.scannerLineHeight, shadeColor(this.laserColor), this.laserColor, Shader.TileMode.MIRROR));
        int i3 = this.scannerStart;
        if (i3 <= this.scannerEnd) {
            int i4 = rect.left;
            int i5 = this.scannerLineHeight;
            canvas.drawOval(new RectF(i4 + (i5 * 2), i3, rect.right - (i5 * 2), i3 + i5), this.paint);
            this.scannerStart += this.scannerLineMoveDistance;
            return;
        }
        this.scannerStart = rect.top;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0091 A[LOOP:1: B:16:0x008a->B:18:0x0091, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00aa A[EDGE_INSN: B:19:0x00aa->B:20:0x00aa ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0061 A[LOOP:0: B:7:0x005d->B:9:0x0061, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void drawGridScanner(Canvas canvas, Rect rect) {
        int i;
        int i2;
        int i3;
        float width;
        int i4;
        float f;
        int i5;
        this.paint.setStrokeWidth(2);
        int i6 = this.gridHeight;
        if (i6 > 0) {
            int i7 = this.scannerStart;
            if (i7 - rect.top > i6) {
                i = i7 - i6;
                float f2 = i;
                i2 = 0;
                this.paint.setShader(new LinearGradient(rect.left + (rect.width() / 2), f2, rect.left + (rect.width() / 2), this.scannerStart, new int[]{shadeColor(this.laserColor), this.laserColor}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
                width = (rect.width() * 1.0f) / this.gridColumn;
                for (i3 = 1; i3 < this.gridColumn; i3++) {
                    int i8 = rect.left;
                    float f3 = i3 * width;
                    canvas.drawLine(i8 + f3, f2, i8 + f3, this.scannerStart, this.paint);
                }
                i4 = this.gridHeight;
                if (i4 > 0 || this.scannerStart - rect.top <= i4) {
                    i4 = this.scannerStart - rect.top;
                }
                while (true) {
                    f = i2;
                    if (f <= i4 / width) {
                        break;
                    }
                    float f4 = rect.left;
                    int i9 = this.scannerStart;
                    float f5 = f * width;
                    canvas.drawLine(f4, i9 - f5, rect.right, i9 - f5, this.paint);
                    i2++;
                }
                i5 = this.scannerStart;
                if (i5 >= this.scannerEnd) {
                    this.scannerStart = i5 + this.scannerLineMoveDistance;
                    return;
                } else {
                    this.scannerStart = rect.top;
                    return;
                }
            }
        }
        i = rect.top;
        float f22 = i;
        i2 = 0;
        this.paint.setShader(new LinearGradient(rect.left + (rect.width() / 2), f22, rect.left + (rect.width() / 2), this.scannerStart, new int[]{shadeColor(this.laserColor), this.laserColor}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
        width = (rect.width() * 1.0f) / this.gridColumn;
        while (i3 < this.gridColumn) {
        }
        i4 = this.gridHeight;
        if (i4 > 0) {
        }
        i4 = this.scannerStart - rect.top;
        while (true) {
            f = i2;
            if (f <= i4 / width) {
            }
            float f42 = rect.left;
            int i92 = this.scannerStart;
            float f52 = f * width;
            canvas.drawLine(f42, i92 - f52, rect.right, i92 - f52, this.paint);
            i2++;
        }
        i5 = this.scannerStart;
        if (i5 >= this.scannerEnd) {
        }
    }

    public int shadeColor(int i) {
        String hexString = Integer.toHexString(i);
        return Integer.valueOf("01" + hexString.substring(2), 16).intValue();
    }

    private void drawFrame(Canvas canvas, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        this.paint.setColor(this.frameColor);
        canvas.drawRect(rect.left, rect.top, rect.right, i + this.frameLineWidth, this.paint);
        canvas.drawRect(rect.left, rect.top, i2 + this.frameLineWidth, rect.bottom, this.paint);
        canvas.drawRect(i3 - this.frameLineWidth, rect.top, rect.right, rect.bottom, this.paint);
        canvas.drawRect(rect.left, i4 - this.frameLineWidth, rect.right, rect.bottom, this.paint);
    }

    private void drawExterior(Canvas canvas, Rect rect, int i, int i2) {
        this.paint.setColor(this.maskColor);
        float f = i;
        canvas.drawRect(0.0f, 0.0f, f, rect.top, this.paint);
        canvas.drawRect(0.0f, rect.top, rect.left, rect.bottom, this.paint);
        canvas.drawRect(rect.right, rect.top, f, rect.bottom, this.paint);
        canvas.drawRect(0.0f, rect.bottom, f, i2, this.paint);
    }

    private void drawResultPoint(Canvas canvas, Rect rect) {
        if (!this.isShowResultPoint) {
            return;
        }
        List<ResultPoint> list = this.possibleResultPoints;
        List<ResultPoint> list2 = this.lastPossibleResultPoints;
        if (list.isEmpty()) {
            this.lastPossibleResultPoints = null;
        } else {
            this.possibleResultPoints = new ArrayList(5);
            this.lastPossibleResultPoints = list;
            this.paint.setAlpha(160);
            this.paint.setColor(this.resultPointColor);
            synchronized (list) {
                for (ResultPoint resultPoint : list) {
                    canvas.drawCircle(resultPoint.getX(), resultPoint.getY(), 10.0f, this.paint);
                }
            }
        }
        if (list2 == null) {
            return;
        }
        this.paint.setAlpha(80);
        this.paint.setColor(this.resultPointColor);
        synchronized (list2) {
            for (ResultPoint resultPoint2 : list2) {
                canvas.drawCircle(resultPoint2.getX(), resultPoint2.getY(), 10.0f, this.paint);
            }
        }
    }

    public void drawViewfinder() {
        invalidate();
    }

    public void setLaserStyle(LaserStyle laserStyle) {
        this.laserStyle = laserStyle;
    }

    public void setShowResultPoint(boolean z) {
        this.isShowResultPoint = z;
    }

    public void addPossibleResultPoint(ResultPoint resultPoint) {
        if (this.isShowResultPoint) {
            List<ResultPoint> list = this.possibleResultPoints;
            synchronized (list) {
                list.add(resultPoint);
                int size = list.size();
                if (size > 20) {
                    list.subList(0, size - 10).clear();
                }
            }
        }
    }
}
