package com.tomatolive.library.p136ui.view.widget.guideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/* renamed from: com.tomatolive.library.ui.view.widget.guideview.MaskView */
/* loaded from: classes4.dex */
public class MaskView extends ViewGroup {
    private boolean ignoreRepadding;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    private int mChangedHeight;
    private final RectF mChildTmpRect;
    private int mCorner;
    private Paint mEraser;
    private Bitmap mEraserBitmap;
    private Canvas mEraserCanvas;
    private boolean mFirstFlag;
    private final Paint mFullingPaint;
    private int mInitHeight;
    private final RectF mOverlayRect;
    private boolean mOverlayTarget;
    private int mPadding;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mStyle;
    private final RectF mTargetRect;
    private PorterDuffXfermode mXfermode;

    public MaskView(Context context) {
        this(context, null, 0);
    }

    public MaskView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTargetRect = new RectF();
        this.mOverlayRect = new RectF();
        this.mChildTmpRect = new RectF();
        this.mPadding = 0;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mOverlayTarget = false;
        this.mCorner = 0;
        this.mStyle = 0;
        this.mChangedHeight = 0;
        this.mFirstFlag = true;
        setWillNotDraw(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        this.mOverlayRect.set(0.0f, 0.0f, i2, i3);
        this.mEraserBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        this.mEraserCanvas = new Canvas(this.mEraserBitmap);
        this.mFullingPaint = new Paint();
        this.mEraser = new Paint();
        this.mEraser.setColor(-1);
        this.mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mEraser.setFlags(1);
        this.mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.mBitmapPaint = new Paint();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            clearFocus();
            this.mEraserCanvas.setBitmap(null);
            this.mEraserBitmap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (this.mFirstFlag) {
            this.mInitHeight = size2;
            this.mFirstFlag = false;
        }
        int i3 = this.mInitHeight;
        if (i3 > size2) {
            this.mChangedHeight = size2 - i3;
        } else if (i3 < size2) {
            this.mChangedHeight = size2 - i3;
        } else {
            this.mChangedHeight = 0;
        }
        setMeasuredDimension(size, size2);
        this.mOverlayRect.set(0.0f, 0.0f, size, size2);
        resetOutPath();
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt != null) {
                measureChild(childAt, i, i2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        LayoutParams layoutParams;
        int childCount = getChildCount();
        float f = getResources().getDisplayMetrics().density;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt != null && (layoutParams = (LayoutParams) childAt.getLayoutParams()) != null) {
                int i6 = layoutParams.targetAnchor;
                if (i6 == 1) {
                    RectF rectF = this.mChildTmpRect;
                    rectF.right = this.mTargetRect.left;
                    rectF.left = rectF.right - childAt.getMeasuredWidth();
                    verticalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.targetParentPosition);
                } else if (i6 == 2) {
                    RectF rectF2 = this.mChildTmpRect;
                    rectF2.bottom = this.mTargetRect.top;
                    rectF2.top = rectF2.bottom - childAt.getMeasuredHeight();
                    horizontalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.targetParentPosition);
                } else if (i6 == 3) {
                    RectF rectF3 = this.mChildTmpRect;
                    rectF3.left = this.mTargetRect.right;
                    rectF3.right = rectF3.left + childAt.getMeasuredWidth();
                    verticalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.targetParentPosition);
                } else if (i6 == 4) {
                    RectF rectF4 = this.mChildTmpRect;
                    rectF4.top = this.mTargetRect.bottom;
                    rectF4.bottom = rectF4.top + childAt.getMeasuredHeight();
                    horizontalChildPositionLayout(childAt, this.mChildTmpRect, layoutParams.targetParentPosition);
                } else if (i6 == 5) {
                    this.mChildTmpRect.left = (((int) this.mTargetRect.width()) - childAt.getMeasuredWidth()) >> 1;
                    this.mChildTmpRect.top = (((int) this.mTargetRect.height()) - childAt.getMeasuredHeight()) >> 1;
                    this.mChildTmpRect.right = (((int) this.mTargetRect.width()) + childAt.getMeasuredWidth()) >> 1;
                    this.mChildTmpRect.bottom = (((int) this.mTargetRect.height()) + childAt.getMeasuredHeight()) >> 1;
                    RectF rectF5 = this.mChildTmpRect;
                    RectF rectF6 = this.mTargetRect;
                    rectF5.offset(rectF6.left, rectF6.top);
                }
                this.mChildTmpRect.offset((int) ((layoutParams.offsetX * f) + 0.5f), (int) ((layoutParams.offsetY * f) + 0.5f));
                RectF rectF7 = this.mChildTmpRect;
                childAt.layout((int) rectF7.left, (int) rectF7.top, (int) rectF7.right, (int) rectF7.bottom);
            }
        }
    }

    private void horizontalChildPositionLayout(View view, RectF rectF, int i) {
        if (i == 16) {
            rectF.left = this.mTargetRect.left;
            rectF.right = rectF.left + view.getMeasuredWidth();
        } else if (i == 32) {
            rectF.left = (this.mTargetRect.width() - view.getMeasuredWidth()) / 2.0f;
            rectF.right = (this.mTargetRect.width() + view.getMeasuredWidth()) / 2.0f;
            rectF.offset(this.mTargetRect.left, 0.0f);
        } else if (i != 48) {
        } else {
            rectF.right = this.mTargetRect.right;
            rectF.left = rectF.right - view.getMeasuredWidth();
        }
    }

    private void verticalChildPositionLayout(View view, RectF rectF, int i) {
        if (i == 16) {
            rectF.top = this.mTargetRect.top;
            rectF.bottom = rectF.top + view.getMeasuredHeight();
        } else if (i == 32) {
            rectF.top = (this.mTargetRect.width() - view.getMeasuredHeight()) / 2.0f;
            rectF.bottom = (this.mTargetRect.width() + view.getMeasuredHeight()) / 2.0f;
            rectF.offset(0.0f, this.mTargetRect.top);
        } else if (i != 48) {
        } else {
            RectF rectF2 = this.mTargetRect;
            rectF.bottom = rectF2.bottom;
            rectF.top = rectF2.bottom - view.getMeasuredHeight();
        }
    }

    private void resetOutPath() {
        resetPadding();
    }

    private void resetPadding() {
        if (!this.ignoreRepadding) {
            int i = this.mPadding;
            if (i != 0 && this.mPaddingLeft == 0) {
                this.mTargetRect.left -= i;
            }
            int i2 = this.mPadding;
            if (i2 != 0 && this.mPaddingTop == 0) {
                this.mTargetRect.top -= i2;
            }
            int i3 = this.mPadding;
            if (i3 != 0 && this.mPaddingRight == 0) {
                this.mTargetRect.right += i3;
            }
            int i4 = this.mPadding;
            if (i4 != 0 && this.mPaddingBottom == 0) {
                this.mTargetRect.bottom += i4;
            }
            int i5 = this.mPaddingLeft;
            if (i5 != 0) {
                this.mTargetRect.left -= i5;
            }
            int i6 = this.mPaddingTop;
            if (i6 != 0) {
                this.mTargetRect.top -= i6;
            }
            int i7 = this.mPaddingRight;
            if (i7 != 0) {
                this.mTargetRect.right += i7;
            }
            int i8 = this.mPaddingBottom;
            if (i8 != 0) {
                this.mTargetRect.bottom += i8;
            }
            this.ignoreRepadding = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        long drawingTime = getDrawingTime();
        for (int i = 0; i < getChildCount(); i++) {
            try {
                drawChild(canvas, getChildAt(i), drawingTime);
            } catch (NullPointerException unused) {
                return;
            }
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.mChangedHeight;
        if (i != 0) {
            this.mTargetRect.offset(0.0f, i);
            this.mInitHeight += this.mChangedHeight;
            this.mChangedHeight = 0;
        }
        this.mEraserBitmap.eraseColor(0);
        this.mEraserCanvas.drawColor(this.mFullingPaint.getColor());
        if (!this.mOverlayTarget) {
            int i2 = this.mStyle;
            if (i2 == 0) {
                Canvas canvas2 = this.mEraserCanvas;
                RectF rectF = this.mTargetRect;
                int i3 = this.mCorner;
                canvas2.drawRoundRect(rectF, i3, i3, this.mEraser);
            } else if (i2 == 1) {
                this.mEraserCanvas.drawCircle(this.mTargetRect.centerX(), this.mTargetRect.centerY(), this.mTargetRect.width() / 2.0f, this.mEraser);
            } else if (i2 == 2) {
                this.mTargetRect.inset(-30.0f, -30.0f);
                this.mBitmapPaint.setXfermode(this.mXfermode);
                this.mEraserCanvas.drawOval(this.mTargetRect, this.mBitmapPaint);
                this.mBitmapPaint.setXfermode(null);
                this.mTargetRect.inset(-1.0f, -1.0f);
                this.mEraserCanvas.drawBitmap(this.mBitmap, (Rect) null, this.mTargetRect, this.mBitmapPaint);
            } else {
                Canvas canvas3 = this.mEraserCanvas;
                RectF rectF2 = this.mTargetRect;
                int i4 = this.mCorner;
                canvas3.drawRoundRect(rectF2, i4, i4, this.mEraser);
            }
        }
        Bitmap bitmap = this.mEraserBitmap;
        RectF rectF3 = this.mOverlayRect;
        canvas.drawBitmap(bitmap, rectF3.left, rectF3.top, (Paint) null);
    }

    public void setTargetRect(Rect rect) {
        this.mTargetRect.set(rect);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setFullingAlpha(int i) {
        this.mFullingPaint.setAlpha(i);
    }

    public void setFullingColor(int i) {
        this.mFullingPaint.setColor(i);
    }

    public void setHighTargetCorner(int i) {
        this.mCorner = i;
    }

    public void setHighTargetGraphStyle(int i) {
        this.mStyle = i;
    }

    public void setOverlayTarget(boolean z) {
        this.mOverlayTarget = z;
    }

    public void setPadding(int i) {
        this.mPadding = i;
    }

    public void setPaddingLeft(int i) {
        this.mPaddingLeft = i;
    }

    public void setPaddingTop(int i) {
        this.mPaddingTop = i;
    }

    public void setPaddingRight(int i) {
        this.mPaddingRight = i;
    }

    public void setPaddingBottom(int i) {
        this.mPaddingBottom = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.widget.guideview.MaskView$LayoutParams */
    /* loaded from: classes4.dex */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        public static final int ANCHOR_BOTTOM = 4;
        public static final int ANCHOR_LEFT = 1;
        public static final int ANCHOR_OVER = 5;
        public static final int ANCHOR_RIGHT = 3;
        public static final int ANCHOR_TOP = 2;
        public static final int PARENT_CENTER = 32;
        public static final int PARENT_END = 48;
        public static final int PARENT_START = 16;
        public int targetAnchor = 4;
        public int targetParentPosition = 32;
        public int offsetX = 0;
        public int offsetY = 0;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }
}
