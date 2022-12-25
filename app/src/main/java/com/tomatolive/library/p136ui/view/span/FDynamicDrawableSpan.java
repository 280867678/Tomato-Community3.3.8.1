package com.tomatolive.library.p136ui.view.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import com.tomatolive.library.p136ui.view.span.ImageSpanHelper;
import java.lang.ref.WeakReference;

/* renamed from: com.tomatolive.library.ui.view.span.FDynamicDrawableSpan */
/* loaded from: classes3.dex */
public abstract class FDynamicDrawableSpan extends DynamicDrawableSpan implements ImageSpanHelper {
    private SimpleImageSpanHelper mImageSpanHelper;
    private final WeakReference<View> mView;

    protected abstract int getDefaultDrawableResId();

    protected abstract Bitmap onGetBitmap();

    protected abstract Drawable onGetDrawable();

    public FDynamicDrawableSpan(View view) {
        this.mView = new WeakReference<>(view);
    }

    public void updateCacheDrawable() {
        this.mImageSpanHelper.updateCacheDrawable();
    }

    public View getView() {
        return this.mView.get();
    }

    public Context getContext() {
        View view = getView();
        if (view == null) {
            return null;
        }
        return view.getContext();
    }

    private SimpleImageSpanHelper getImageSpanHelper() {
        if (this.mImageSpanHelper == null) {
            this.mImageSpanHelper = new SimpleImageSpanHelper(this);
        }
        return this.mImageSpanHelper;
    }

    @Override // android.text.style.DynamicDrawableSpan
    public Drawable getDrawable() {
        Drawable onGetDrawable = onGetDrawable();
        int defaultDrawableResId = getDefaultDrawableResId();
        if (onGetDrawable == null) {
            try {
                onGetDrawable = new BitmapDrawable(getContext().getResources(), getContext().getResources().openRawResource(defaultDrawableResId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onGetDrawable.setBounds(0, 0, onGetDrawable.getIntrinsicWidth(), onGetDrawable.getIntrinsicHeight());
        getImageSpanHelper().processDrawable(onGetDrawable);
        return onGetDrawable;
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        getImageSpanHelper().draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        return getImageSpanHelper().getSize(paint, charSequence, i, i2, fontMetricsInt);
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setWidth(int i) {
        getImageSpanHelper().setWidth(i);
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setHeight(int i) {
        getImageSpanHelper().setHeight(i);
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setMarginLeft(int i) {
        getImageSpanHelper().setMarginLeft(i);
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setMarginRight(int i) {
        getImageSpanHelper().setMarginRight(i);
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setMarginBottom(int i) {
        getImageSpanHelper().setMarginBottom(i);
    }

    @Override // com.tomatolive.library.p136ui.view.span.ImageSpanHelper
    public void setVerticalAlignType(ImageSpanHelper.VerticalAlignType verticalAlignType) {
        getImageSpanHelper().setVerticalAlignType(verticalAlignType);
    }
}
