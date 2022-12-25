package com.facebook.cache.common;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;

/* loaded from: classes2.dex */
public class SimpleCacheKey implements CacheKey {
    final String mKey;

    public SimpleCacheKey(String str) {
        Preconditions.checkNotNull(str);
        this.mKey = str;
    }

    public String toString() {
        return this.mKey;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SimpleCacheKey)) {
            return false;
        }
        return this.mKey.equals(((SimpleCacheKey) obj).mKey);
    }

    public int hashCode() {
        return this.mKey.hashCode();
    }

    @Override // com.facebook.cache.common.CacheKey
    public boolean containsUri(Uri uri) {
        return this.mKey.contains(uri.toString());
    }

    @Override // com.facebook.cache.common.CacheKey
    public String getUriString() {
        return this.mKey;
    }
}
