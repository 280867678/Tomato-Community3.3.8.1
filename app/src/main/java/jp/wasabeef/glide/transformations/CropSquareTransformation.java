package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import java.security.MessageDigest;

/* loaded from: classes4.dex */
public class CropSquareTransformation extends BitmapTransformation {

    /* renamed from: ID */
    private static final String f6015ID = "jp.wasabeef.glide.transformations.CropSquareTransformation.1";
    private static final byte[] ID_BYTES = f6015ID.getBytes(Key.CHARSET);
    private static final int VERSION = 1;
    private int size;

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        this.size = Math.max(i, i2);
        int i3 = this.size;
        return TransformationUtils.centerCrop(bitmapPool, bitmap, i3, i3);
    }

    public String toString() {
        return "CropSquareTransformation(size=" + this.size + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof CropSquareTransformation;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return f6015ID.hashCode();
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
