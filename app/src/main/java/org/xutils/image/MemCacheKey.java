package org.xutils.image;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class MemCacheKey {
    public final ImageOptions options;
    public final String url;

    public MemCacheKey(String str, ImageOptions imageOptions) {
        this.url = str;
        this.options = imageOptions;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MemCacheKey.class != obj.getClass()) {
            return false;
        }
        MemCacheKey memCacheKey = (MemCacheKey) obj;
        if (this.url.equals(memCacheKey.url)) {
            return this.options.equals(memCacheKey.options);
        }
        return false;
    }

    public int hashCode() {
        return (this.url.hashCode() * 31) + this.options.hashCode();
    }

    public String toString() {
        return this.url + this.options.toString();
    }
}
