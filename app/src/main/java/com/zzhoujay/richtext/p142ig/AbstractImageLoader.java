package com.zzhoujay.richtext.p142ig;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.cache.BitmapPool;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.drawable.DrawableWrapper;
import com.zzhoujay.richtext.exceptions.ImageDecodeException;
import com.zzhoujay.richtext.ext.ContextKit;
import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.zzhoujay.richtext.ig.AbstractImageLoader */
/* loaded from: classes4.dex */
public abstract class AbstractImageLoader<T> implements ImageLoader {
    private final RichTextConfig config;
    private final WeakReference<DrawableWrapper> drawableWrapperWeakReference;
    final ImageHolder holder;
    private final WeakReference<ImageLoadNotify> notifyWeakReference;
    private final SourceDecode<T> sourceDecode;
    private final WeakReference<TextView> textViewWeakReference;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AbstractImageLoader(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify imageLoadNotify, SourceDecode<T> sourceDecode) {
        this.holder = imageHolder;
        this.config = richTextConfig;
        this.sourceDecode = sourceDecode;
        this.textViewWeakReference = new WeakReference<>(textView);
        this.drawableWrapperWeakReference = new WeakReference<>(drawableWrapper);
        this.notifyWeakReference = new WeakReference<>(imageLoadNotify);
        onLoading();
    }

    public void onLoading() {
        DrawableWrapper drawableWrapper;
        if (activityIsAlive() && (drawableWrapper = this.drawableWrapperWeakReference.get()) != null) {
            this.holder.setImageState(1);
            Drawable placeHolder = this.holder.getPlaceHolder();
            Rect bounds = placeHolder.getBounds();
            drawableWrapper.setDrawable(placeHolder);
            ImageFixCallback imageFixCallback = this.config.imageFixCallback;
            if (imageFixCallback != null) {
                imageFixCallback.onLoading(this.holder);
            }
            if (drawableWrapper.isHasCache()) {
                placeHolder.setBounds(drawableWrapper.getBounds());
            } else {
                drawableWrapper.setScaleType(this.holder.getScaleType());
                drawableWrapper.setBorderHolder(this.holder.getBorderHolder());
                drawableWrapper.setBounds(0, 0, getHolderWidth(bounds.width()), getHolderHeight(bounds.height()));
                drawableWrapper.calculate();
            }
            resetText();
        }
    }

    public int onSizeReady(int i, int i2) {
        int sampleSize;
        this.holder.setImageState(4);
        ImageHolder.SizeHolder sizeHolder = new ImageHolder.SizeHolder(i, i2);
        ImageFixCallback imageFixCallback = this.config.imageFixCallback;
        if (imageFixCallback != null) {
            imageFixCallback.onSizeReady(this.holder, i, i2, sizeHolder);
        }
        if (sizeHolder.isInvalidateSize()) {
            sampleSize = getSampleSize(i, i2, sizeHolder.getWidth(), sizeHolder.getHeight());
        } else {
            sampleSize = getSampleSize(i, i2, getRealWidth(), Integer.MAX_VALUE);
        }
        return Math.max(1, sampleSize == 0 ? 0 : Integer.highestOneBit(sampleSize));
    }

    public void onFailure(Exception exc) {
        DrawableWrapper drawableWrapper;
        if (activityIsAlive() && (drawableWrapper = this.drawableWrapperWeakReference.get()) != null) {
            this.holder.setImageState(3);
            Drawable errorImage = this.holder.getErrorImage();
            Rect bounds = errorImage.getBounds();
            drawableWrapper.setDrawable(errorImage);
            ImageFixCallback imageFixCallback = this.config.imageFixCallback;
            if (imageFixCallback != null) {
                imageFixCallback.onFailure(this.holder, exc);
            }
            if (drawableWrapper.isHasCache()) {
                errorImage.setBounds(drawableWrapper.getBounds());
            } else {
                drawableWrapper.setScaleType(this.holder.getScaleType());
                drawableWrapper.setBounds(0, 0, getHolderWidth(bounds.width()), getHolderHeight(bounds.height()));
                drawableWrapper.setBorderHolder(this.holder.getBorderHolder());
                drawableWrapper.calculate();
            }
            resetText();
            done();
        }
    }

    public void onResourceReady(ImageWrapper imageWrapper) {
        TextView textView;
        if (imageWrapper == null) {
            onFailure(new ImageDecodeException());
            return;
        }
        DrawableWrapper drawableWrapper = this.drawableWrapperWeakReference.get();
        if (drawableWrapper == null || (textView = this.textViewWeakReference.get()) == null) {
            return;
        }
        new WeakReference(imageWrapper);
        this.holder.setImageState(2);
        Drawable drawable = imageWrapper.getDrawable(textView.getResources());
        drawableWrapper.setDrawable(drawable);
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        ImageFixCallback imageFixCallback = this.config.imageFixCallback;
        if (imageFixCallback != null) {
            imageFixCallback.onImageReady(this.holder, width, height);
        }
        if (drawableWrapper.isHasCache()) {
            drawable.setBounds(drawableWrapper.getBounds());
        } else {
            drawableWrapper.setScaleType(this.holder.getScaleType());
            drawableWrapper.setBounds(0, 0, getHolderWidth(width), getHolderHeight(height));
            drawableWrapper.setBorderHolder(this.holder.getBorderHolder());
            drawableWrapper.calculate();
        }
        if (imageWrapper.isGif() && this.holder.isAutoPlay()) {
            imageWrapper.getAsGif().start(textView);
        }
        BitmapPool pool = BitmapPool.getPool();
        String key = this.holder.getKey();
        if (this.config.cacheType.intValue() > CacheType.none.intValue() && !drawableWrapper.isHasCache()) {
            pool.cacheSize(key, drawableWrapper.getSizeHolder());
        }
        if (this.config.cacheType.intValue() > CacheType.layout.intValue() && !imageWrapper.isGif()) {
            pool.cacheBitmap(key, imageWrapper.getAsBitmap());
        }
        resetText();
        done();
    }

    private int[] getDimensions(T t, BitmapFactory.Options options) {
        options.inJustDecodeBounds = true;
        this.sourceDecode.decodeSize(t, options);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static int getSampleSize(int i, int i2, int i3, int i4) {
        int ceil = (int) Math.ceil(Math.max(i2 / i4, i / i3));
        int i5 = 1;
        int max = Math.max(1, Integer.highestOneBit(ceil));
        if (max >= ceil) {
            i5 = 0;
        }
        return max << i5;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void doLoadImage(T t) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int[] dimensions = getDimensions(t, options);
        options.inSampleSize = onSizeReady(dimensions[0], dimensions[1]);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        onResourceReady(this.sourceDecode.decode(this.holder, t, options));
    }

    private boolean activityIsAlive() {
        TextView textView = this.textViewWeakReference.get();
        if (textView == null) {
            return false;
        }
        return ContextKit.activityIsAlive(textView.getContext());
    }

    private void resetText() {
        final TextView textView = this.textViewWeakReference.get();
        if (textView != null) {
            textView.post(new Runnable(this) { // from class: com.zzhoujay.richtext.ig.AbstractImageLoader.1
                @Override // java.lang.Runnable
                public void run() {
                    textView.setText(textView.getText());
                }
            });
        }
    }

    private void done() {
        ImageLoadNotify imageLoadNotify = this.notifyWeakReference.get();
        if (imageLoadNotify != null) {
            imageLoadNotify.done(this);
        }
    }

    private int getRealWidth() {
        TextView textView = this.textViewWeakReference.get();
        if (textView == null) {
            return 0;
        }
        return (textView.getWidth() - textView.getPaddingRight()) - textView.getPaddingLeft();
    }

    private int getRealHeight() {
        TextView textView = this.textViewWeakReference.get();
        if (textView == null) {
            return 0;
        }
        return (textView.getHeight() - textView.getPaddingTop()) - textView.getPaddingBottom();
    }

    private int getHolderWidth(int i) {
        int width = this.holder.getWidth();
        if (width == Integer.MAX_VALUE) {
            return getRealWidth();
        }
        return width == Integer.MIN_VALUE ? i : width;
    }

    private int getHolderHeight(int i) {
        int height = this.holder.getHeight();
        if (height == Integer.MAX_VALUE) {
            return getRealHeight();
        }
        return height == Integer.MIN_VALUE ? i : height;
    }
}
