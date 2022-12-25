package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.util.LruCache;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.integration.webp.WebpFrame;
import com.bumptech.glide.integration.webp.WebpFrameInfo;
import com.bumptech.glide.integration.webp.WebpImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class WebpDecoder implements GifDecoder {
    private static final int MAX_FRAME_BITMAP_CACHE_SIZE = 5;
    private static final String TAG = "WebpDecoder";
    private int downsampledHeight;
    private int downsampledWidth;
    private Bitmap.Config mBitmapConfig;
    private final GifDecoder.BitmapProvider mBitmapProvider;
    private WebpFrameCacheStrategy mCacheStrategy;
    private final LruCache<Integer, Bitmap> mFrameBitmapCache;
    private final int[] mFrameDurations;
    private final WebpFrameInfo[] mFrameInfos;
    private int mFramePointer;
    private final Paint mTransparentFillPaint;
    private WebpImage mWebPImage;
    private ByteBuffer rawData;
    private int sampleSize;

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getStatus() {
        return 0;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int read(InputStream inputStream, int i) {
        return 0;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int read(byte[] bArr) {
        return 0;
    }

    public WebpDecoder(GifDecoder.BitmapProvider bitmapProvider, WebpImage webpImage, ByteBuffer byteBuffer, int i) {
        this(bitmapProvider, webpImage, byteBuffer, i, WebpFrameCacheStrategy.NONE);
    }

    public WebpDecoder(GifDecoder.BitmapProvider bitmapProvider, WebpImage webpImage, ByteBuffer byteBuffer, int i, WebpFrameCacheStrategy webpFrameCacheStrategy) {
        int max;
        this.mFramePointer = -1;
        this.mBitmapConfig = Bitmap.Config.ARGB_8888;
        this.mBitmapProvider = bitmapProvider;
        this.mWebPImage = webpImage;
        this.mFrameDurations = webpImage.getFrameDurations();
        this.mFrameInfos = new WebpFrameInfo[webpImage.getFrameCount()];
        for (int i2 = 0; i2 < this.mWebPImage.getFrameCount(); i2++) {
            this.mFrameInfos[i2] = this.mWebPImage.getFrameInfo(i2);
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "mFrameInfos: " + this.mFrameInfos[i2].toString());
            }
        }
        this.mCacheStrategy = webpFrameCacheStrategy;
        this.mTransparentFillPaint = new Paint();
        this.mTransparentFillPaint.setColor(0);
        this.mTransparentFillPaint.setStyle(Paint.Style.FILL);
        this.mTransparentFillPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        if (this.mCacheStrategy.cacheAll()) {
            max = webpImage.getFrameCount();
        } else {
            max = Math.max(5, this.mCacheStrategy.getCacheSize());
        }
        this.mFrameBitmapCache = new LruCache<Integer, Bitmap>(max) { // from class: com.bumptech.glide.integration.webp.decoder.WebpDecoder.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public void entryRemoved(boolean z, Integer num, Bitmap bitmap, Bitmap bitmap2) {
                if (bitmap != null) {
                    WebpDecoder.this.mBitmapProvider.release(bitmap);
                }
            }
        };
        setData(new GifHeader(), byteBuffer, i);
    }

    public WebpFrameCacheStrategy getCacheStrategy() {
        return this.mCacheStrategy;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getWidth() {
        return this.mWebPImage.getWidth();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getHeight() {
        return this.mWebPImage.getHeight();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public ByteBuffer getData() {
        return this.rawData;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void advance() {
        this.mFramePointer = (this.mFramePointer + 1) % this.mWebPImage.getFrameCount();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getDelay(int i) {
        if (i >= 0) {
            int[] iArr = this.mFrameDurations;
            if (i < iArr.length) {
                return iArr[i];
            }
        }
        return -1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNextDelay() {
        int i;
        if (this.mFrameDurations.length == 0 || (i = this.mFramePointer) < 0) {
            return 0;
        }
        return getDelay(i);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getFrameCount() {
        return this.mWebPImage.getFrameCount();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getCurrentFrameIndex() {
        return this.mFramePointer;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void resetFrameIndex() {
        this.mFramePointer = -1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getLoopCount() {
        return this.mWebPImage.getLoopCount();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNetscapeLoopCount() {
        return this.mWebPImage.getLoopCount();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getTotalIterationCount() {
        if (this.mWebPImage.getLoopCount() == 0) {
            return 0;
        }
        return this.mWebPImage.getFrameCount() + 1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getByteSize() {
        return this.mWebPImage.getSizeInBytes();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setDefaultBitmapConfig(Bitmap.Config config) {
        if (config != Bitmap.Config.ARGB_8888) {
            throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888);
        }
        this.mBitmapConfig = config;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public Bitmap getNextFrame() {
        Bitmap bitmap;
        int currentFrameIndex = getCurrentFrameIndex();
        Bitmap obtain = this.mBitmapProvider.obtain(this.downsampledWidth, this.downsampledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(obtain);
        canvas.drawColor(0, PorterDuff.Mode.SRC);
        if (!this.mCacheStrategy.noCache() && (bitmap = this.mFrameBitmapCache.get(Integer.valueOf(currentFrameIndex))) != null) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "hit frame bitmap from memory cache, frameNumber=" + currentFrameIndex);
            }
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            return obtain;
        }
        int prepareCanvasWithBlending = !isKeyFrame(currentFrameIndex) ? prepareCanvasWithBlending(currentFrameIndex - 1, canvas) : currentFrameIndex;
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "frameNumber=" + currentFrameIndex + ", nextIndex=" + prepareCanvasWithBlending);
        }
        while (prepareCanvasWithBlending < currentFrameIndex) {
            WebpFrameInfo webpFrameInfo = this.mFrameInfos[prepareCanvasWithBlending];
            if (!webpFrameInfo.blendPreviousFrame) {
                disposeToBackground(canvas, webpFrameInfo);
            }
            renderFrame(prepareCanvasWithBlending, canvas);
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "renderFrame, index=" + prepareCanvasWithBlending + ", blend=" + webpFrameInfo.blendPreviousFrame + ", dispose=" + webpFrameInfo.disposeBackgroundColor);
            }
            if (webpFrameInfo.disposeBackgroundColor) {
                disposeToBackground(canvas, webpFrameInfo);
            }
            prepareCanvasWithBlending++;
        }
        WebpFrameInfo webpFrameInfo2 = this.mFrameInfos[currentFrameIndex];
        if (!webpFrameInfo2.blendPreviousFrame) {
            disposeToBackground(canvas, webpFrameInfo2);
        }
        renderFrame(currentFrameIndex, canvas);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "renderFrame, index=" + currentFrameIndex + ", blend=" + webpFrameInfo2.blendPreviousFrame + ", dispose=" + webpFrameInfo2.disposeBackgroundColor);
        }
        cacheFrameBitmap(currentFrameIndex, obtain);
        return obtain;
    }

    private void renderFrame(int i, Canvas canvas) {
        WebpFrameInfo webpFrameInfo = this.mFrameInfos[i];
        int i2 = webpFrameInfo.width;
        int i3 = this.sampleSize;
        int i4 = i2 / i3;
        int i5 = webpFrameInfo.height / i3;
        int i6 = webpFrameInfo.xOffset / i3;
        int i7 = webpFrameInfo.yOffset / i3;
        WebpFrame frame = this.mWebPImage.getFrame(i);
        try {
            Bitmap obtain = this.mBitmapProvider.obtain(i4, i5, this.mBitmapConfig);
            obtain.eraseColor(0);
            frame.renderFrame(i4, i5, obtain);
            canvas.drawBitmap(obtain, i6, i7, (Paint) null);
            this.mBitmapProvider.release(obtain);
        } finally {
            frame.dispose();
        }
    }

    private void cacheFrameBitmap(int i, Bitmap bitmap) {
        this.mFrameBitmapCache.remove(Integer.valueOf(i));
        Bitmap obtain = this.mBitmapProvider.obtain(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        obtain.eraseColor(0);
        new Canvas(obtain).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        this.mFrameBitmapCache.put(Integer.valueOf(i), obtain);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void clear() {
        this.mWebPImage.dispose();
        this.mWebPImage = null;
        this.mFrameBitmapCache.evictAll();
        this.rawData = null;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setData(GifHeader gifHeader, byte[] bArr) {
        setData(gifHeader, ByteBuffer.wrap(bArr));
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setData(GifHeader gifHeader, ByteBuffer byteBuffer) {
        setData(gifHeader, byteBuffer, 1);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setData(GifHeader gifHeader, ByteBuffer byteBuffer, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Sample size must be >=0, not: " + i);
        }
        int highestOneBit = Integer.highestOneBit(i);
        this.rawData = byteBuffer.asReadOnlyBuffer();
        this.rawData.position(0);
        this.sampleSize = highestOneBit;
        this.downsampledWidth = this.mWebPImage.getWidth() / highestOneBit;
        this.downsampledHeight = this.mWebPImage.getHeight() / highestOneBit;
    }

    private int prepareCanvasWithBlending(int i, Canvas canvas) {
        while (i >= 0) {
            WebpFrameInfo webpFrameInfo = this.mFrameInfos[i];
            if (webpFrameInfo.disposeBackgroundColor && isFullFrame(webpFrameInfo)) {
                return i + 1;
            }
            Bitmap bitmap = this.mFrameBitmapCache.get(Integer.valueOf(i));
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                if (webpFrameInfo.disposeBackgroundColor) {
                    disposeToBackground(canvas, webpFrameInfo);
                }
                return i + 1;
            } else if (isKeyFrame(i)) {
                return i;
            } else {
                i--;
            }
        }
        return 0;
    }

    private void disposeToBackground(Canvas canvas, WebpFrameInfo webpFrameInfo) {
        int i = webpFrameInfo.xOffset;
        int i2 = this.sampleSize;
        int i3 = webpFrameInfo.yOffset;
        canvas.drawRect(i / i2, i3 / i2, (i + webpFrameInfo.width) / i2, (i3 + webpFrameInfo.height) / i2, this.mTransparentFillPaint);
    }

    private boolean isKeyFrame(int i) {
        if (i == 0) {
            return true;
        }
        WebpFrameInfo[] webpFrameInfoArr = this.mFrameInfos;
        WebpFrameInfo webpFrameInfo = webpFrameInfoArr[i];
        WebpFrameInfo webpFrameInfo2 = webpFrameInfoArr[i - 1];
        if (!webpFrameInfo.blendPreviousFrame && isFullFrame(webpFrameInfo)) {
            return true;
        }
        return webpFrameInfo2.disposeBackgroundColor && isFullFrame(webpFrameInfo2);
    }

    private boolean isFullFrame(WebpFrameInfo webpFrameInfo) {
        return webpFrameInfo.xOffset == 0 && webpFrameInfo.yOffset == 0 && webpFrameInfo.width == this.mWebPImage.getWidth() && webpFrameInfo.height == this.mWebPImage.getHeight();
    }
}
