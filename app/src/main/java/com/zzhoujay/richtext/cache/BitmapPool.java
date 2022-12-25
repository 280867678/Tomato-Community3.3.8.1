package com.zzhoujay.richtext.cache;

import android.graphics.Bitmap;
import android.support.p002v4.media.session.PlaybackStateCompat;
import android.support.p002v4.util.LruCache;
import com.jakewharton.disklrucache.DiskLruCache;
import com.zzhoujay.richtext.drawable.DrawableSizeHolder;
import com.zzhoujay.richtext.ext.Debug;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class BitmapPool {
    private static final int bitmapCacheSize = (int) (Runtime.getRuntime().maxMemory() / 4);
    private static File cacheDir;
    private static File sizeDir;
    private static DiskLruCache sizeDiskLruCache;
    private static File tempDir;
    private static DiskLruCache tempDiskLruCache;
    private LruCache<String, Bitmap> bitmapCache;
    private LruCache<String, DrawableSizeHolder> sizeHolderCache;

    private BitmapPool() {
        this.bitmapCache = new LruCache<String, Bitmap>(this, bitmapCacheSize) { // from class: com.zzhoujay.richtext.cache.BitmapPool.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.support.p002v4.util.LruCache
            public int sizeOf(String str, Bitmap bitmap) {
                if (bitmap == null) {
                    return 0;
                }
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
        this.sizeHolderCache = new LruCache<>(100);
    }

    public void cacheBitmap(String str, Bitmap bitmap) {
        this.bitmapCache.put(str, bitmap);
    }

    public void cacheSize(String str, DrawableSizeHolder drawableSizeHolder) {
        this.sizeHolderCache.put(str, drawableSizeHolder);
        CacheIOHelper.SIZE_CACHE_IO_HELPER.writeToCache(str, drawableSizeHolder, getSizeDiskLruCache());
    }

    public Bitmap getBitmap(String str) {
        return this.bitmapCache.get(str);
    }

    public DrawableSizeHolder getSizeHolder(String str) {
        DrawableSizeHolder drawableSizeHolder = this.sizeHolderCache.get(str);
        return drawableSizeHolder == null ? CacheIOHelper.SIZE_CACHE_IO_HELPER.mo6749readFromCache(str, getSizeDiskLruCache()) : drawableSizeHolder;
    }

    public void writeBitmapToTemp(String str, InputStream inputStream) {
        CacheIOHelper.REMOTE_IMAGE_CACHE_IO_HELPER.writeToCache(str, inputStream, getTempDiskLruCache());
    }

    public InputStream readBitmapFromTemp(String str) {
        return CacheIOHelper.REMOTE_IMAGE_CACHE_IO_HELPER.mo6749readFromCache(str, getTempDiskLruCache());
    }

    public boolean hasBitmapLocalCache(String str) {
        return CacheIOHelper.REMOTE_IMAGE_CACHE_IO_HELPER.hasCache(str, getTempDiskLruCache());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class BitmapPoolHolder {
        private static final BitmapPool BITMAP_POOL = new BitmapPool();
    }

    public static BitmapPool getPool() {
        return BitmapPoolHolder.BITMAP_POOL;
    }

    public void clear() {
        this.bitmapCache.evictAll();
        this.sizeHolderCache.evictAll();
    }

    private static DiskLruCache getSizeDiskLruCache() {
        if (sizeDiskLruCache == null && cacheDir != null) {
            try {
                sizeDiskLruCache = DiskLruCache.open(sizeDir, 1, 1, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
            } catch (IOException e) {
                Debug.m122e(e);
            }
        }
        return sizeDiskLruCache;
    }

    private static DiskLruCache getTempDiskLruCache() {
        if (tempDiskLruCache == null && cacheDir != null) {
            try {
                tempDiskLruCache = DiskLruCache.open(tempDir, 1, 1, 104857600L);
            } catch (IOException e) {
                Debug.m122e(e);
            }
        }
        return tempDiskLruCache;
    }
}
