package com.tomatolive.library.p136ui.view.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import com.tomatolive.library.p136ui.view.span.ImageSpanHelper;
import java.lang.ref.WeakReference;

/* renamed from: com.tomatolive.library.ui.view.span.SimpleImageSpanHelper */
/* loaded from: classes3.dex */
class SimpleImageSpanHelper implements ImageSpanHelper {
    private WeakReference<Drawable> mDrawableRef;
    private int mHeight;
    private int mMarginBottom;
    private int mMarginLeft;
    private int mMarginRight;
    private final DynamicDrawableSpan mSpan;
    private ImageSpanHelper.VerticalAlignType mVerticalAlignType = ImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM;
    private int mWidth;

    public SimpleImageSpanHelper(DynamicDrawableSpan dynamicDrawableSpan) {
        this.mSpan = dynamicDrawableSpan;
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setWidth(int i) {
        this.mWidth = i;
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setHeight(int i) {
        this.mHeight = i;
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setMarginLeft(int i) {
        this.mMarginLeft = i;
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setMarginRight(int i) {
        this.mMarginRight = i;
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setMarginBottom(int i) {
        this.mMarginBottom = i;
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setVerticalAlignType(ImageSpanHelper.VerticalAlignType verticalAlignType) {
        if (verticalAlignType != null) {
            this.mVerticalAlignType = verticalAlignType;
        }
    }

    public void processDrawable(Drawable drawable) {
        if (drawable != null && this.mHeight > 0) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i = this.mHeight;
            drawable.setBounds(0, 0, (intrinsicWidth * i) / intrinsicHeight, i);
        }
    }

    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        try {
            Drawable cachedDrawable = getCachedDrawable();
            canvas.save();
            canvas.translate(f, (((i5 - i3) - cachedDrawable.getBounds().bottom) / 2) + i3);
            cachedDrawable.draw(canvas);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Rect bounds = getCachedDrawable().getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fontMetricsInt2 = paint.getFontMetricsInt();
            int i3 = fontMetricsInt2.bottom - fontMetricsInt2.top;
            int i4 = (bounds.bottom - bounds.top) / 2;
            int i5 = i3 / 4;
            int i6 = i4 - i5;
            int i7 = -(i4 + i5);
            fontMetricsInt.ascent = i7;
            fontMetricsInt.top = i7;
            fontMetricsInt.bottom = i6;
            fontMetricsInt.descent = i6;
        }
        return bounds.right;
    }

    public void updateCacheDrawable() {
        WeakReference<Drawable> weakReference = this.mDrawableRef;
        if (weakReference != null) {
            weakReference.clear();
            this.mDrawableRef = null;
        }
        this.mDrawableRef = new WeakReference<>(this.mSpan.getDrawable());
    }

    private Drawable getCachedDrawable() {
        WeakReference<Drawable> weakReference = this.mDrawableRef;
        Drawable drawable = weakReference != null ? weakReference.get() : null;
        if (drawable == null) {
            Drawable drawable2 = this.mSpan.getDrawable();
            this.mDrawableRef = new WeakReference<>(drawable2);
            return drawable2;
        }
        return drawable;
    }
}
