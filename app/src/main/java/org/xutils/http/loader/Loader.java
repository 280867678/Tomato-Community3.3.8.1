package org.xutils.http.loader;

import android.text.TextUtils;
import java.io.InputStream;
import java.util.Date;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.cache.LruDiskCache;
import org.xutils.http.ProgressHandler;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
public abstract class Loader<T> {
    protected RequestParams params;
    protected ProgressHandler progressHandler;

    /* renamed from: load */
    public abstract T mo6877load(InputStream inputStream) throws Throwable;

    /* renamed from: load */
    public abstract T mo6878load(UriRequest uriRequest) throws Throwable;

    /* renamed from: loadFromCache */
    public abstract T mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable;

    public abstract Loader<T> newInstance();

    public abstract void save2Cache(UriRequest uriRequest);

    public void setParams(RequestParams requestParams) {
        this.params = requestParams;
    }

    public void setProgressHandler(ProgressHandler progressHandler) {
        this.progressHandler = progressHandler;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void saveStringCache(UriRequest uriRequest, String str) {
        if (!TextUtils.isEmpty(str)) {
            DiskCacheEntity diskCacheEntity = new DiskCacheEntity();
            diskCacheEntity.setKey(uriRequest.getCacheKey());
            diskCacheEntity.setLastAccess(System.currentTimeMillis());
            diskCacheEntity.setEtag(uriRequest.getETag());
            diskCacheEntity.setExpires(uriRequest.getExpiration());
            diskCacheEntity.setLastModify(new Date(uriRequest.getLastModified()));
            diskCacheEntity.setTextContent(str);
            LruDiskCache.getDiskCache(uriRequest.getParams().getCacheDirName()).put(diskCacheEntity);
        }
    }
}
