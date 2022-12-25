package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;

/* loaded from: classes4.dex */
public class CropTransformation extends BitmapTransformation {

    /* renamed from: ID */
    private static final String f6016ID = "jp.wasabeef.glide.transformations.CropTransformation.1";
    private static final byte[] ID_BYTES = f6016ID.getBytes(Key.CHARSET);
    private static final int VERSION = 1;
    private CropType cropType;
    private int height;
    private int width;

    /* loaded from: classes4.dex */
    public enum CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    public CropTransformation(int i, int i2) {
        this(i, i2, CropType.CENTER);
    }

    public CropTransformation(int i, int i2, CropType cropType) {
        this.cropType = CropType.CENTER;
        this.width = i;
        this.height = i2;
        this.cropType = cropType;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int i3 = this.width;
        if (i3 == 0) {
            i3 = bitmap.getWidth();
        }
        this.width = i3;
        int i4 = this.height;
        if (i4 == 0) {
            i4 = bitmap.getHeight();
        }
        this.height = i4;
        Bitmap bitmap2 = bitmapPool.get(this.width, this.height, bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        float max = Math.max(this.width / bitmap.getWidth(), this.height / bitmap.getHeight());
        float width = bitmap.getWidth() * max;
        float height = max * bitmap.getHeight();
        float f = (this.width - width) / 2.0f;
        float top2 = getTop(height);
        new Canvas(bitmap2).drawBitmap(bitmap, (Rect) null, new RectF(f, top2, width + f, height + top2), (Paint) null);
        return bitmap2;
    }

    public String toString() {
        return "CropTransformation(width=" + this.width + ", height=" + this.height + ", cropType=" + this.cropType + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof CropTransformation;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return f6016ID.hashCode();
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: jp.wasabeef.glide.transformations.CropTransformation$1 */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class C52391 {

        /* renamed from: $SwitchMap$jp$wasabeef$glide$transformations$CropTransformation$CropType */
        static final /* synthetic */ int[] f6017xcea3632 = new int[CropType.values().length];

        static {
            try {
                f6017xcea3632[CropType.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f6017xcea3632[CropType.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f6017xcea3632[CropType.BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private float getTop(float f) {
        int i = C52391.f6017xcea3632[this.cropType.ordinal()];
        if (i != 1) {
            if (i == 2) {
                return (this.height - f) / 2.0f;
            }
            if (i == 3) {
                return this.height - f;
            }
            return 0.0f;
        }
        return 0.0f;
    }
}
