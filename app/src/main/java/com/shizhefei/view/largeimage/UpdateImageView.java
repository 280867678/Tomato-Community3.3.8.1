package com.shizhefei.view.largeimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.graphics.drawable.DrawableCompat;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import com.shizhefei.view.largeimage.BlockImageLoader;
import com.shizhefei.view.largeimage.factory.BitmapDecoderFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class UpdateImageView extends UpdateView implements BlockImageLoader.OnImageLoadListener, ILargeImageView {
    private Drawable defaultDrawable;
    private List<BlockImageLoader.DrawData> drawDataList;
    private BlockImageLoader imageBlockLoader;
    private boolean isAttachedWindow;
    private Drawable mDrawable;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private BitmapDecoderFactory mFactory;
    private int mLevel;
    private float mOffsetX;
    private float mOffsetY;
    private float mScale;
    private BlockImageLoader.OnImageLoadListener onImageLoadListener;
    private Rect tempImageRect;
    private Rect tempVisibilityRect;

    public UpdateImageView(Context context) {
        this(context, null);
    }

    public UpdateImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UpdateImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAttachedWindow = false;
        this.tempVisibilityRect = new Rect();
        this.tempImageRect = new Rect();
        this.drawDataList = new ArrayList();
        this.imageBlockLoader = new BlockImageLoader(context);
        this.imageBlockLoader.setOnImageLoadListener(this);
    }

    public void setScale(float f) {
        this.mScale = f;
        notifyInvalidate();
    }

    public float getScale() {
        return this.mScale;
    }

    public BlockImageLoader.OnImageLoadListener getOnImageLoadListener() {
        return this.onImageLoadListener;
    }

    public void setScale(float f, float f2, float f3) {
        this.mScale = f;
        this.mOffsetX = f2;
        this.mOffsetY = f3;
        notifyInvalidate();
    }

    public int getImageWidth() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return this.imageBlockLoader.getWidth();
    }

    public int getImageHeight() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return this.imageBlockLoader.getHeight();
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        resizeFromDrawable();
    }

    private void resizeFromDrawable() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (intrinsicWidth < 0) {
                intrinsicWidth = this.mDrawableWidth;
            }
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicHeight < 0) {
                intrinsicHeight = this.mDrawableHeight;
            }
            if (intrinsicWidth == this.mDrawableWidth && intrinsicHeight == this.mDrawableHeight) {
                return;
            }
            this.mDrawableWidth = intrinsicWidth;
            this.mDrawableHeight = intrinsicHeight;
            requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.shizhefei.view.largeimage.UpdateView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean z = true;
        this.isAttachedWindow = true;
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            if (getVisibility() != 0) {
                z = false;
            }
            drawable.setVisible(z, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.shizhefei.view.largeimage.UpdateView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedWindow = false;
        this.imageBlockLoader.stopLoad();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setVisible(false, false);
        }
    }

    public boolean hasLoad() {
        if (this.mDrawable != null) {
            return true;
        }
        if (this.mFactory == null) {
            return false;
        }
        if (this.defaultDrawable == null) {
            return this.imageBlockLoader.hasLoad();
        }
        return true;
    }

    @Override // com.shizhefei.view.largeimage.ILargeImageView
    public void setOnImageLoadListener(BlockImageLoader.OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public void setOnLoadStateChangeListener(BlockImageLoader.OnLoadStateChangeListener onLoadStateChangeListener) {
        BlockImageLoader blockImageLoader = this.imageBlockLoader;
        if (blockImageLoader != null) {
            blockImageLoader.setOnLoadStateChangeListener(onLoadStateChangeListener);
        }
    }

    public void setImage(BitmapDecoderFactory bitmapDecoderFactory) {
        setImage(bitmapDecoderFactory, null);
    }

    public void setImage(BitmapDecoderFactory bitmapDecoderFactory, Drawable drawable) {
        this.mScale = 1.0f;
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        this.mDrawable = null;
        this.mFactory = bitmapDecoderFactory;
        this.defaultDrawable = drawable;
        if (drawable != null) {
            onLoadImageSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        this.imageBlockLoader.setBitmapDecoderFactory(bitmapDecoderFactory);
        invalidate();
    }

    public void setImage(Bitmap bitmap) {
        setImageDrawable(new BitmapDrawable(getResources(), bitmap));
    }

    public void setImage(Drawable drawable) {
        setImageDrawable(drawable);
    }

    public void setImage(@DrawableRes int i) {
        setImageDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public void setImageDrawable(Drawable drawable) {
        this.mFactory = null;
        this.mScale = 1.0f;
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        if (this.mDrawable != drawable) {
            int i = this.mDrawableWidth;
            int i2 = this.mDrawableHeight;
            updateDrawable(drawable);
            onLoadImageSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            if (i != this.mDrawableWidth || i2 != this.mDrawableHeight) {
                requestLayout();
            }
            invalidate();
        }
    }

    private void updateDrawable(Drawable drawable) {
        boolean z;
        Drawable drawable2 = this.mDrawable;
        boolean z2 = false;
        if (drawable2 != null) {
            z = drawable2 == drawable;
            this.mDrawable.setCallback(null);
            unscheduleDrawable(this.mDrawable);
            if (!z && this.isAttachedWindow) {
                this.mDrawable.setVisible(false, false);
            }
        } else {
            z = false;
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (Build.VERSION.SDK_INT >= 23) {
                drawable.setLayoutDirection(getLayoutDirection());
            }
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (!z) {
                if (this.isAttachedWindow && getWindowVisibility() == 0 && isShown()) {
                    z2 = true;
                }
                drawable.setVisible(z2, true);
            }
            drawable.setLevel(this.mLevel);
            this.mDrawableWidth = drawable.getIntrinsicWidth();
            this.mDrawableHeight = drawable.getIntrinsicHeight();
            return;
        }
        this.mDrawableHeight = -1;
        this.mDrawableWidth = -1;
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mDrawable;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, f, f2);
        }
    }

    @Override // com.shizhefei.view.largeimage.UpdateView, android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setVisible(i == 0, false);
        }
    }

    @Override // com.shizhefei.view.largeimage.UpdateView
    protected void onUpdateWindow(Rect rect) {
        if (this.mFactory == null || !hasLoad()) {
            return;
        }
        notifyInvalidate();
    }

    private void notifyInvalidate() {
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override // android.view.View
    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        Rect rect;
        Drawable drawable;
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width == 0) {
            return;
        }
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            float f = width;
            float f2 = this.mScale;
            drawable2.setBounds((int) this.mOffsetX, (int) this.mOffsetY, (int) (f * f2), (int) (height * f2));
            this.mDrawable.draw(canvas);
        } else if (this.mFactory != null) {
            getVisibilityRect(this.tempVisibilityRect);
            float f3 = width;
            float width2 = this.imageBlockLoader.getWidth() / (this.mScale * f3);
            Rect rect2 = this.tempImageRect;
            rect2.left = (int) Math.ceil((rect.left - this.mOffsetX) * width2);
            rect2.top = (int) Math.ceil((rect.top - this.mOffsetY) * width2);
            rect2.right = (int) Math.ceil((rect.right - this.mOffsetX) * width2);
            rect2.bottom = (int) Math.ceil((rect.bottom - this.mOffsetY) * width2);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (this.defaultDrawable == null || (this.imageBlockLoader.hasLoad() && this.imageBlockLoader.getWidth() * this.imageBlockLoader.getHeight() > displayMetrics.widthPixels * displayMetrics.heightPixels)) {
                this.imageBlockLoader.loadImageBlocks(this.drawDataList, width2, rect2, width, height);
            }
            if (this.drawDataList.isEmpty()) {
                if (this.defaultDrawable == null) {
                    return;
                }
                int intrinsicHeight = (int) (((drawable.getIntrinsicHeight() * 1.0f) / this.defaultDrawable.getIntrinsicWidth()) * f3);
                int i = (int) this.mOffsetY;
                float f4 = this.mScale;
                this.defaultDrawable.setBounds((int) this.mOffsetX, i + ((height - intrinsicHeight) / 2), (int) (f3 * f4), (int) (intrinsicHeight * f4));
                this.defaultDrawable.draw(canvas);
                return;
            }
            int save = canvas.save();
            for (BlockImageLoader.DrawData drawData : this.drawDataList) {
                Rect rect3 = drawData.imageRect;
                rect3.left = (int) (Math.ceil(rect3.left / width2) + this.mOffsetX);
                rect3.top = (int) (Math.ceil(rect3.top / width2) + this.mOffsetY);
                rect3.right = (int) (Math.ceil(rect3.right / width2) + this.mOffsetX);
                rect3.bottom = (int) (Math.ceil(rect3.bottom / width2) + this.mOffsetY);
                canvas.drawBitmap(drawData.bitmap, drawData.srcRect, rect3, (Paint) null);
            }
            canvas.restoreToCount(save);
        }
    }

    @Override // com.shizhefei.view.largeimage.BlockImageLoader.OnImageLoadListener
    public void onBlockImageLoadFinished() {
        notifyInvalidate();
        BlockImageLoader.OnImageLoadListener onImageLoadListener = this.onImageLoadListener;
        if (onImageLoadListener != null) {
            onImageLoadListener.onBlockImageLoadFinished();
        }
    }

    @Override // com.shizhefei.view.largeimage.BlockImageLoader.OnImageLoadListener
    public void onLoadImageSize(int i, int i2) {
        this.mDrawableWidth = i;
        this.mDrawableHeight = i2;
        notifyInvalidate();
        BlockImageLoader.OnImageLoadListener onImageLoadListener = this.onImageLoadListener;
        if (onImageLoadListener != null) {
            onImageLoadListener.onLoadImageSize(i, i2);
        }
    }

    @Override // com.shizhefei.view.largeimage.BlockImageLoader.OnImageLoadListener
    public void onLoadFail(Exception exc) {
        BlockImageLoader.OnImageLoadListener onImageLoadListener = this.onImageLoadListener;
        if (onImageLoadListener != null) {
            onImageLoadListener.onLoadFail(exc);
        }
    }
}
