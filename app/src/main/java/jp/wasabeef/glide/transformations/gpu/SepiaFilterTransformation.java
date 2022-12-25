package jp.wasabeef.glide.transformations.gpu;

import com.bumptech.glide.load.Key;
import java.security.MessageDigest;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

/* loaded from: classes4.dex */
public class SepiaFilterTransformation extends GPUFilterTransformation {

    /* renamed from: ID */
    private static final String f6028ID = "jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation.1";
    private static final byte[] ID_BYTES = f6028ID.getBytes(Key.CHARSET);
    private static final int VERSION = 1;
    private float intensity;

    public SepiaFilterTransformation() {
        this(1.0f);
    }

    public SepiaFilterTransformation(float f) {
        super(new GPUImageSepiaFilter());
        this.intensity = f;
        ((GPUImageSepiaFilter) getFilter()).setIntensity(this.intensity);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation
    public String toString() {
        return "SepiaFilterTransformation(intensity=" + this.intensity + ")";
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof SepiaFilterTransformation;
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return f6028ID.hashCode();
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
