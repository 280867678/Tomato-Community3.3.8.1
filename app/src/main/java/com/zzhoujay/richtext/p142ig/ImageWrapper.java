package com.zzhoujay.richtext.p142ig;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.zzhoujay.richtext.callback.Recyclable;
import com.zzhoujay.richtext.drawable.GifDrawable;
import com.zzhoujay.richtext.exceptions.ImageWrapperMultiSourceException;

/* renamed from: com.zzhoujay.richtext.ig.ImageWrapper */
/* loaded from: classes4.dex */
class ImageWrapper implements Recyclable {
    private final Bitmap bitmap;
    private final GifDrawable gifDrawable;
    private final int height;
    private final int width;

    private ImageWrapper(GifDrawable gifDrawable, Bitmap bitmap) {
        this.gifDrawable = gifDrawable;
        this.bitmap = bitmap;
        if (gifDrawable == null) {
            if (bitmap == null) {
                throw new ImageWrapperMultiSourceException();
            }
            this.height = bitmap.getHeight();
            this.width = bitmap.getWidth();
        } else if (bitmap != null) {
            throw new ImageWrapperMultiSourceException();
        } else {
            this.height = gifDrawable.getHeight();
            this.width = gifDrawable.getWidth();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isGif() {
        return this.gifDrawable != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifDrawable getAsGif() {
        return this.gifDrawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bitmap getAsBitmap() {
        return this.bitmap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getHeight() {
        return this.height;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Drawable getDrawable(Resources resources) {
        GifDrawable gifDrawable = this.gifDrawable;
        if (gifDrawable == null) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, this.bitmap);
            bitmapDrawable.setBounds(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
            return bitmapDrawable;
        }
        return gifDrawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ImageWrapper createAsGif(GifDrawable gifDrawable) {
        return new ImageWrapper(gifDrawable, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ImageWrapper createAsBitmap(Bitmap bitmap) {
        return new ImageWrapper(null, bitmap);
    }
}
