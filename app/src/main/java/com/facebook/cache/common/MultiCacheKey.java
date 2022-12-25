package com.facebook.cache.common;

import android.net.Uri;
import java.util.List;

/* loaded from: classes2.dex */
public class MultiCacheKey implements CacheKey {
    final List<CacheKey> mCacheKeys;

    public List<CacheKey> getCacheKeys() {
        return this.mCacheKeys;
    }

    public String toString() {
        return "MultiCacheKey:" + this.mCacheKeys.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MultiCacheKey)) {
            return false;
        }
        return this.mCacheKeys.equals(((MultiCacheKey) obj).mCacheKeys);
    }

    public int hashCode() {
        return this.mCacheKeys.hashCode();
    }

    @Override // com.facebook.cache.common.CacheKey
    public boolean containsUri(Uri uri) {
        for (int i = 0; i < this.mCacheKeys.size(); i++) {
            if (this.mCacheKeys.get(i).containsUri(uri)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.facebook.cache.common.CacheKey
    public String getUriString() {
        return this.mCacheKeys.get(0).getUriString();
    }
}
