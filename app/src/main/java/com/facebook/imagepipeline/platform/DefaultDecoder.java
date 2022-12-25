package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.os.Build;
import android.support.p002v4.util.Pools;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.streams.TailAppendingInputStream;
import com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapPool;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@TargetApi(21)
/* loaded from: classes2.dex */
public abstract class DefaultDecoder implements PlatformDecoder {
    private final BitmapPool mBitmapPool;
    final Pools.SynchronizedPool<ByteBuffer> mDecodeBuffers;
    private static final Class<?> TAG = DefaultDecoder.class;
    private static final byte[] EOI_TAIL = {-1, -39};

    public abstract int getBitmapSize(int i, int i2, BitmapFactory.Options options);

    public DefaultDecoder(BitmapPool bitmapPool, int i, Pools.SynchronizedPool synchronizedPool) {
        this.mBitmapPool = bitmapPool;
        this.mDecodeBuffers = synchronizedPool;
        for (int i2 = 0; i2 < i; i2++) {
            this.mDecodeBuffers.release(ByteBuffer.allocate(16384));
        }
    }

    @Override // com.facebook.imagepipeline.platform.PlatformDecoder
    public CloseableReference<Bitmap> decodeFromEncodedImageWithColorSpace(EncodedImage encodedImage, Bitmap.Config config, Rect rect, boolean z) {
        BitmapFactory.Options decodeOptionsForStream = getDecodeOptionsForStream(encodedImage, config);
        boolean z2 = decodeOptionsForStream.inPreferredConfig != Bitmap.Config.ARGB_8888;
        try {
            return decodeFromStream(encodedImage.getInputStream(), decodeOptionsForStream, rect, z);
        } catch (RuntimeException e) {
            if (z2) {
                return decodeFromEncodedImageWithColorSpace(encodedImage, Bitmap.Config.ARGB_8888, rect, z);
            }
            throw e;
        }
    }

    @Override // com.facebook.imagepipeline.platform.PlatformDecoder
    public CloseableReference<Bitmap> decodeJPEGFromEncodedImageWithColorSpace(EncodedImage encodedImage, Bitmap.Config config, Rect rect, int i, boolean z) {
        boolean isCompleteAt = encodedImage.isCompleteAt(i);
        BitmapFactory.Options decodeOptionsForStream = getDecodeOptionsForStream(encodedImage, config);
        InputStream inputStream = encodedImage.getInputStream();
        Preconditions.checkNotNull(inputStream);
        if (encodedImage.getSize() > i) {
            inputStream = new LimitedInputStream(inputStream, i);
        }
        InputStream tailAppendingInputStream = !isCompleteAt ? new TailAppendingInputStream(inputStream, EOI_TAIL) : inputStream;
        boolean z2 = decodeOptionsForStream.inPreferredConfig != Bitmap.Config.ARGB_8888;
        try {
            return decodeFromStream(tailAppendingInputStream, decodeOptionsForStream, rect, z);
        } catch (RuntimeException e) {
            if (z2) {
                return decodeJPEGFromEncodedImageWithColorSpace(encodedImage, Bitmap.Config.ARGB_8888, rect, i, z);
            }
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0085 A[Catch: all -> 0x00a5, RuntimeException -> 0x00a7, IllegalArgumentException -> 0x00ae, TRY_LEAVE, TryCatch #7 {IllegalArgumentException -> 0x00ae, RuntimeException -> 0x00a7, blocks: (B:14:0x0047, B:23:0x0060, B:25:0x0085, B:37:0x0077, B:41:0x007e, B:42:0x0081), top: B:13:0x0047, outer: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x007e A[Catch: all -> 0x00a5, RuntimeException -> 0x00a7, IllegalArgumentException -> 0x00ae, TryCatch #7 {IllegalArgumentException -> 0x00ae, RuntimeException -> 0x00a7, blocks: (B:14:0x0047, B:23:0x0060, B:25:0x0085, B:37:0x0077, B:41:0x007e, B:42:0x0081), top: B:13:0x0047, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private CloseableReference<Bitmap> decodeFromStream(InputStream inputStream, BitmapFactory.Options options, Rect rect, boolean z) {
        Bitmap bitmap;
        BitmapRegionDecoder bitmapRegionDecoder;
        Preconditions.checkNotNull(inputStream);
        int i = options.outWidth;
        int i2 = options.outHeight;
        if (rect != null) {
            i = rect.width() / options.inSampleSize;
            i2 = rect.height() / options.inSampleSize;
        }
        Bitmap mo5947get = this.mBitmapPool.mo5947get(getBitmapSize(i, i2, options));
        if (mo5947get == null) {
            throw new NullPointerException("BitmapPool.get returned null");
        }
        options.inBitmap = mo5947get;
        if (Build.VERSION.SDK_INT >= 26 && z) {
            options.inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        ByteBuffer acquire = this.mDecodeBuffers.acquire();
        if (acquire == null) {
            acquire = ByteBuffer.allocate(16384);
        }
        try {
            try {
                options.inTempStorage = acquire.array();
                if (rect != null) {
                    try {
                        mo5947get.reconfigure(i, i2, options.inPreferredConfig);
                        bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, true);
                        try {
                            try {
                                bitmap = bitmapRegionDecoder.decodeRegion(rect, options);
                                if (bitmapRegionDecoder != null) {
                                    bitmapRegionDecoder.recycle();
                                }
                            } catch (IOException unused) {
                                FLog.m4154e(TAG, "Could not decode region %s, decoding full bitmap instead.", rect);
                                if (bitmapRegionDecoder != null) {
                                    bitmapRegionDecoder.recycle();
                                }
                                bitmap = null;
                                if (bitmap == null) {
                                }
                                this.mDecodeBuffers.release(acquire);
                                if (mo5947get != bitmap) {
                                }
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (bitmapRegionDecoder != null) {
                                bitmapRegionDecoder.recycle();
                            }
                            throw th;
                        }
                    } catch (IOException unused2) {
                        bitmapRegionDecoder = null;
                    } catch (Throwable th2) {
                        th = th2;
                        bitmapRegionDecoder = null;
                        if (bitmapRegionDecoder != null) {
                        }
                        throw th;
                    }
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    }
                    this.mDecodeBuffers.release(acquire);
                    if (mo5947get != bitmap) {
                        this.mBitmapPool.release(mo5947get);
                        bitmap.recycle();
                        throw new IllegalStateException();
                    }
                    return CloseableReference.m4129of(bitmap, this.mBitmapPool);
                }
                bitmap = null;
                if (bitmap == null) {
                }
                this.mDecodeBuffers.release(acquire);
                if (mo5947get != bitmap) {
                }
            } catch (Throwable th3) {
                this.mDecodeBuffers.release(acquire);
                throw th3;
            }
        } catch (IllegalArgumentException e) {
            this.mBitmapPool.release(mo5947get);
            try {
                inputStream.reset();
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                if (decodeStream == null) {
                    throw e;
                }
                CloseableReference<Bitmap> m4129of = CloseableReference.m4129of(decodeStream, SimpleBitmapReleaser.getInstance());
                this.mDecodeBuffers.release(acquire);
                return m4129of;
            } catch (IOException unused3) {
                throw e;
            }
        } catch (RuntimeException e2) {
            this.mBitmapPool.release(mo5947get);
            throw e2;
        }
    }

    private static BitmapFactory.Options getDecodeOptionsForStream(EncodedImage encodedImage, Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = encodedImage.getSampleSize();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(encodedImage.getInputStream(), null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            throw new IllegalArgumentException();
        }
        options.inJustDecodeBounds = false;
        options.inDither = true;
        options.inPreferredConfig = config;
        options.inMutable = true;
        return options;
    }
}
