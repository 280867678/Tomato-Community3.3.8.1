package org.xutils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import org.xutils.C5540x;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.cache.DiskCacheFile;
import org.xutils.cache.LruDiskCache;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;

/* loaded from: classes4.dex */
public final class ImageDecoder {
    private static final int BITMAP_DECODE_MAX_WORKER;
    private static final boolean supportWebP;
    private static final AtomicInteger bitmapDecodeWorker = new AtomicInteger(0);
    private static final Object bitmapDecodeLock = new Object();
    private static final Object gifDecodeLock = new Object();
    private static final byte[] GIF_HEADER = {71, 73, 70};
    private static final Executor THUMB_CACHE_EXECUTOR = new PriorityExecutor(1, true);
    private static final LruDiskCache THUMB_CACHE = LruDiskCache.getDiskCache("xUtils_img_thumb");

    static {
        boolean z = false;
        int i = 1;
        if (Build.VERSION.SDK_INT > 16) {
            z = true;
        }
        supportWebP = z;
        if (Runtime.getRuntime().availableProcessors() > 4) {
            i = 2;
        }
        BITMAP_DECODE_MAX_WORKER = i;
    }

    private ImageDecoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void clearCacheFiles() {
        THUMB_CACHE.clearCacheFiles();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable decodeFileWithLock(final File file, final ImageOptions imageOptions, Callback.Cancelable cancelable) throws IOException {
        Movie decodeGif;
        if (file == null || !file.exists() || file.length() < 1) {
            return null;
        }
        if (cancelable != null && cancelable.isCancelled()) {
            throw new Callback.CancelledException("cancelled during decode image");
        }
        if (!imageOptions.isIgnoreGif() && isGif(file)) {
            synchronized (gifDecodeLock) {
                decodeGif = decodeGif(file, imageOptions, cancelable);
            }
            if (decodeGif == null) {
                return null;
            }
            return new GifDrawable(decodeGif, (int) file.length());
        }
        try {
            synchronized (bitmapDecodeLock) {
                while (bitmapDecodeWorker.get() >= BITMAP_DECODE_MAX_WORKER && (cancelable == null || !cancelable.isCancelled())) {
                    try {
                        bitmapDecodeLock.wait();
                    } catch (InterruptedException unused) {
                        throw new Callback.CancelledException("cancelled during decode image");
                    } catch (Throwable unused2) {
                    }
                }
            }
            if (cancelable != null && cancelable.isCancelled()) {
                throw new Callback.CancelledException("cancelled during decode image");
            }
            bitmapDecodeWorker.incrementAndGet();
            final Bitmap thumbCache = imageOptions.isCompress() ? getThumbCache(file, imageOptions) : null;
            if (thumbCache == null && (thumbCache = decodeBitmap(file, imageOptions, cancelable)) != null && imageOptions.isCompress()) {
                THUMB_CACHE_EXECUTOR.execute(new Runnable() { // from class: org.xutils.image.ImageDecoder.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageDecoder.saveThumbCache(file, imageOptions, thumbCache);
                    }
                });
            }
            bitmapDecodeWorker.decrementAndGet();
            synchronized (bitmapDecodeLock) {
                bitmapDecodeLock.notifyAll();
            }
            if (thumbCache == null) {
                return null;
            }
            return new ReusableBitmapDrawable(C5540x.app().getResources(), thumbCache);
        } catch (Throwable th) {
            bitmapDecodeWorker.decrementAndGet();
            synchronized (bitmapDecodeLock) {
                bitmapDecodeLock.notifyAll();
                throw th;
            }
        }
    }

    public static boolean isGif(File file) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                return Arrays.equals(GIF_HEADER, IOUtil.readBytes(fileInputStream, 0L, 3));
            } catch (Throwable th) {
                th = th;
                try {
                    LogUtil.m43e(th.getMessage(), th);
                    IOUtil.closeQuietly(fileInputStream);
                    return false;
                } finally {
                    IOUtil.closeQuietly(fileInputStream);
                }
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00cc, code lost:
        r11 = rotate(r11, r4, true);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap decodeBitmap(File file, ImageOptions imageOptions, Callback.Cancelable cancelable) throws IOException {
        if (file == null || !file.exists() || file.length() < 1) {
            return null;
        }
        if (imageOptions == null) {
            imageOptions = ImageOptions.DEFAULT;
        }
        if (imageOptions.getMaxWidth() <= 0 || imageOptions.getMaxHeight() <= 0) {
            imageOptions.optimizeMaxSize(null);
        }
        if (cancelable != null) {
            try {
                if (cancelable.isCancelled()) {
                    throw new Callback.CancelledException("cancelled during decode image");
                }
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
                return null;
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int i = 0;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = imageOptions.getConfig();
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        int width = imageOptions.getWidth();
        int height = imageOptions.getHeight();
        if (imageOptions.isAutoRotate()) {
            i = getRotateAngle(file.getAbsolutePath());
            if ((i / 90) % 2 == 1) {
                i2 = options.outHeight;
                i3 = options.outWidth;
            }
        }
        if (!imageOptions.isCrop() && width > 0 && height > 0) {
            if ((i / 90) % 2 == 1) {
                options.outWidth = height;
                options.outHeight = width;
            } else {
                options.outWidth = width;
                options.outHeight = height;
            }
        }
        options.inSampleSize = calculateSampleSize(i2, i3, imageOptions.getMaxWidth(), imageOptions.getMaxHeight());
        if (cancelable != null && cancelable.isCancelled()) {
            throw new Callback.CancelledException("cancelled during decode image");
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if (decodeFile == null) {
            throw new IOException("decode image error");
        }
        if (cancelable != null && cancelable.isCancelled()) {
            throw new Callback.CancelledException("cancelled during decode image");
        }
        if (cancelable != null && cancelable.isCancelled()) {
            throw new Callback.CancelledException("cancelled during decode image");
        }
        if (imageOptions.isCrop() && width > 0 && height > 0) {
            decodeFile = cut2ScaleSize(decodeFile, width, height, true);
        }
        if (decodeFile == null) {
            throw new IOException("decode image error");
        }
        if (cancelable != null && cancelable.isCancelled()) {
            throw new Callback.CancelledException("cancelled during decode image");
        }
        if (imageOptions.isCircular()) {
            decodeFile = cut2Circular(decodeFile, true);
        } else if (imageOptions.getRadius() > 0) {
            decodeFile = cut2RoundCorner(decodeFile, imageOptions.getRadius(), imageOptions.isSquare(), true);
        } else if (imageOptions.isSquare()) {
            decodeFile = cut2Square(decodeFile, true);
        }
        if (decodeFile == null) {
            throw new IOException("decode image error");
        }
        return decodeFile;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [long, java.io.Closeable] */
    public static Movie decodeGif(File file, ImageOptions imageOptions, Callback.Cancelable cancelable) throws IOException {
        if (file != null && file.exists()) {
            BufferedInputStream length = file.length();
            if (length >= 1) {
                try {
                    if (cancelable != null) {
                        try {
                            if (cancelable.isCancelled()) {
                                throw new Callback.CancelledException("cancelled during decode image");
                            }
                        } catch (IOException e) {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            BufferedInputStream bufferedInputStream = null;
                            LogUtil.m43e(th.getMessage(), th);
                            return null;
                        }
                    }
                    length = new BufferedInputStream(new FileInputStream(file), 16384);
                    try {
                        length.mark(16384);
                        Movie decodeStream = Movie.decodeStream(length);
                        if (decodeStream != null) {
                            return decodeStream;
                        }
                        throw new IOException("decode image error");
                    } catch (IOException e2) {
                        throw e2;
                    } catch (Throwable th2) {
                        th = th2;
                        LogUtil.m43e(th.getMessage(), th);
                        return null;
                    }
                } finally {
                    IOUtil.closeQuietly((Closeable) length);
                }
            }
        }
        return null;
    }

    public static int calculateSampleSize(int i, int i2, int i3, int i4) {
        int round;
        int i5 = 1;
        if (i > i3 || i2 > i4) {
            if (i > i2) {
                round = Math.round(i2 / i4);
            } else {
                round = Math.round(i / i3);
            }
            if (round >= 1) {
                i5 = round;
            }
            while ((i * i2) / (i5 * i5) > i3 * i4 * 2) {
                i5++;
            }
        }
        return i5;
    }

    public static Bitmap cut2Square(Bitmap bitmap, boolean z) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == height) {
            return bitmap;
        }
        int min = Math.min(width, height);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, (width - min) / 2, (height - min) / 2, min, min);
        if (createBitmap == null) {
            return bitmap;
        }
        if (z && createBitmap != bitmap) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap cut2Circular(Bitmap bitmap, boolean z) {
        int width;
        int height;
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap createBitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        if (createBitmap != null) {
            Canvas canvas = new Canvas(createBitmap);
            float f = min / 2;
            canvas.drawCircle(f, f, f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, (min - width) / 2, (min - height) / 2, paint);
            if (z) {
                bitmap.recycle();
            }
            return createBitmap;
        }
        return bitmap;
    }

    public static Bitmap cut2RoundCorner(Bitmap bitmap, int i, boolean z, boolean z2) {
        int i2;
        int i3;
        if (i <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (z) {
            i2 = Math.min(width, height);
            i3 = i2;
        } else {
            i2 = width;
            i3 = height;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        if (createBitmap == null) {
            return bitmap;
        }
        Canvas canvas = new Canvas(createBitmap);
        float f = i;
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, i2, i3), f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (i2 - width) / 2, (i3 - height) / 2, paint);
        if (z2) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap cut2ScaleSize(Bitmap bitmap, int i, int i2, boolean z) {
        int i3;
        int i4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == i && height == i2) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        float f = i;
        float f2 = width;
        float f3 = f / f2;
        float f4 = i2;
        float f5 = height;
        float f6 = f4 / f5;
        if (f3 > f6) {
            float f7 = f4 / f3;
            height = (int) ((f5 + f7) / 2.0f);
            i4 = (int) ((f5 - f7) / 2.0f);
            i3 = 0;
        } else {
            float f8 = f / f6;
            i3 = (int) ((f2 - f8) / 2.0f);
            width = (int) ((f2 + f8) / 2.0f);
            f3 = f6;
            i4 = 0;
        }
        matrix.setScale(f3, f3);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, i3, i4, width - i3, height - i4, matrix, true);
        if (createBitmap == null) {
            return bitmap;
        }
        if (z && createBitmap != bitmap) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap rotate(Bitmap bitmap, int i, boolean z) {
        Bitmap bitmap2;
        if (i != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(i);
            try {
                bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
            }
            if (bitmap2 != null) {
                return bitmap;
            }
            if (z && bitmap2 != bitmap) {
                bitmap.recycle();
            }
            return bitmap2;
        }
        bitmap2 = null;
        if (bitmap2 != null) {
        }
    }

    public static int getRotateAngle(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(android.support.media.ExifInterface.TAG_ORIENTATION, 0);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            return attributeInt != 8 ? 0 : 270;
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveThumbCache(File file, ImageOptions imageOptions, Bitmap bitmap) {
        FileOutputStream fileOutputStream;
        Throwable th;
        DiskCacheFile diskCacheFile;
        DiskCacheEntity diskCacheEntity = new DiskCacheEntity();
        diskCacheEntity.setKey(file.getAbsolutePath() + "@" + file.lastModified() + imageOptions.toString());
        FileOutputStream fileOutputStream2 = null;
        try {
            diskCacheFile = THUMB_CACHE.createDiskCacheFile(diskCacheEntity);
            if (diskCacheFile != null) {
                try {
                    fileOutputStream = new FileOutputStream(diskCacheFile);
                    try {
                        bitmap.compress(supportWebP ? Bitmap.CompressFormat.WEBP : Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                        fileOutputStream.flush();
                        diskCacheFile = diskCacheFile.commit();
                        fileOutputStream2 = fileOutputStream;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            IOUtil.deleteFileOrDir(diskCacheFile);
                            LogUtil.m37w(th.getMessage(), th);
                        } finally {
                            IOUtil.closeQuietly(diskCacheFile);
                            IOUtil.closeQuietly(fileOutputStream);
                        }
                    }
                } catch (Throwable th3) {
                    fileOutputStream = null;
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            fileOutputStream = null;
            th = th4;
            diskCacheFile = null;
        }
    }

    private static Bitmap getThumbCache(File file, ImageOptions imageOptions) {
        DiskCacheFile diskCacheFile;
        try {
            diskCacheFile = THUMB_CACHE.getDiskCacheFile(file.getAbsolutePath() + "@" + file.lastModified() + imageOptions.toString());
            if (diskCacheFile != null) {
                try {
                    if (diskCacheFile.exists()) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        return BitmapFactory.decodeFile(diskCacheFile.getAbsolutePath(), options);
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        LogUtil.m37w(th.getMessage(), th);
                        return null;
                    } finally {
                        IOUtil.closeQuietly(diskCacheFile);
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            diskCacheFile = null;
        }
        return null;
    }
}
