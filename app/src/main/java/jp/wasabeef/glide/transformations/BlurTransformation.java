package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;
import jp.wasabeef.glide.transformations.internal.FastBlur;

/* loaded from: classes4.dex */
public class BlurTransformation extends BitmapTransformation {
    private static final int VERSION = 1;
    private int radius;
    private int sampling;

    /* renamed from: ID */
    private static final String f6012ID = "jp.wasabeef.glide.transformations.BlurTransformation.1";
    private static final byte[] ID_BYTES = f6012ID.getBytes(Key.CHARSET);
    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    public BlurTransformation() {
        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(int i) {
        this(i, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(int i, int i2) {
        this.radius = i;
        this.sampling = i2;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i3 = this.sampling;
        Bitmap bitmap2 = bitmapPool.get(width / i3, height / i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        int i4 = this.sampling;
        canvas.scale(1.0f / i4, 1.0f / i4);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return FastBlur.blur(bitmap2, this.radius, true);
    }

    public String toString() {
        return "BlurTransformation(radius=" + this.radius + ", sampling=" + this.sampling + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof BlurTransformation;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return f6012ID.hashCode();
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
