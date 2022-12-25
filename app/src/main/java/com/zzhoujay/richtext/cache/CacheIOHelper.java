package com.zzhoujay.richtext.cache;

import com.jakewharton.disklrucache.DiskLruCache;
import com.zzhoujay.richtext.drawable.DrawableSizeHolder;
import com.zzhoujay.richtext.ext.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public interface CacheIOHelper<INPUT, OUTPUT> {
    public static final CacheIOHelper<DrawableSizeHolder, DrawableSizeHolder> SIZE_CACHE_IO_HELPER = new CacheIOHelper<DrawableSizeHolder, DrawableSizeHolder>() { // from class: com.zzhoujay.richtext.cache.CacheIOHelper.1
        @Override // com.zzhoujay.richtext.cache.CacheIOHelper
        public void writeToCache(String str, DrawableSizeHolder drawableSizeHolder, DiskLruCache diskLruCache) {
            if (diskLruCache != null) {
                try {
                    DiskLruCache.Editor edit = diskLruCache.edit(str);
                    if (edit == null) {
                        return;
                    }
                    OutputStream newOutputStream = edit.newOutputStream(0);
                    drawableSizeHolder.save(newOutputStream);
                    newOutputStream.flush();
                    newOutputStream.close();
                    edit.commit();
                } catch (IOException e) {
                    Debug.m122e(e);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.zzhoujay.richtext.cache.CacheIOHelper
        /* renamed from: readFromCache */
        public DrawableSizeHolder mo6749readFromCache(String str, DiskLruCache diskLruCache) {
            if (diskLruCache != null) {
                try {
                    DiskLruCache.Snapshot snapshot = diskLruCache.get(str);
                    if (snapshot == null) {
                        return null;
                    }
                    InputStream inputStream = snapshot.getInputStream(0);
                    DrawableSizeHolder read = DrawableSizeHolder.read(inputStream, str);
                    inputStream.close();
                    return read;
                } catch (IOException e) {
                    Debug.m122e(e);
                }
            }
            return null;
        }

        @Override // com.zzhoujay.richtext.cache.CacheIOHelper
        public boolean hasCache(String str, DiskLruCache diskLruCache) {
            if (diskLruCache != null) {
                try {
                    return diskLruCache.get(str) != null;
                } catch (IOException e) {
                    Debug.m122e(e);
                }
            }
            return false;
        }
    };
    public static final CacheIOHelper<InputStream, InputStream> REMOTE_IMAGE_CACHE_IO_HELPER = new CacheIOHelper<InputStream, InputStream>() { // from class: com.zzhoujay.richtext.cache.CacheIOHelper.2
        @Override // com.zzhoujay.richtext.cache.CacheIOHelper
        public void writeToCache(String str, InputStream inputStream, DiskLruCache diskLruCache) {
            if (diskLruCache != null) {
                try {
                    DiskLruCache.Editor edit = diskLruCache.edit(str);
                    if (edit == null) {
                        return;
                    }
                    OutputStream newOutputStream = edit.newOutputStream(0);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read != -1) {
                            newOutputStream.write(bArr, 0, read);
                        } else {
                            newOutputStream.flush();
                            newOutputStream.close();
                            inputStream.close();
                            edit.commit();
                            return;
                        }
                    }
                } catch (IOException e) {
                    Debug.m122e(e);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.zzhoujay.richtext.cache.CacheIOHelper
        /* renamed from: readFromCache */
        public InputStream mo6749readFromCache(String str, DiskLruCache diskLruCache) {
            DiskLruCache.Snapshot snapshot;
            if (diskLruCache != null) {
                try {
                    snapshot = diskLruCache.get(str);
                } catch (IOException e) {
                    Debug.m122e(e);
                    snapshot = null;
                }
                if (snapshot != null) {
                    return snapshot.getInputStream(0);
                }
                return null;
            }
            return null;
        }

        @Override // com.zzhoujay.richtext.cache.CacheIOHelper
        public boolean hasCache(String str, DiskLruCache diskLruCache) {
            if (diskLruCache != null) {
                try {
                    return diskLruCache.get(str) != null;
                } catch (IOException e) {
                    Debug.m122e(e);
                }
            }
            return false;
        }
    };

    boolean hasCache(String str, DiskLruCache diskLruCache);

    /* renamed from: readFromCache */
    OUTPUT mo6749readFromCache(String str, DiskLruCache diskLruCache);

    void writeToCache(String str, INPUT input, DiskLruCache diskLruCache);
}
