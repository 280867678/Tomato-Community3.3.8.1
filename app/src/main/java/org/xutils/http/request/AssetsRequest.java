package org.xutils.http.request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.xutils.C5540x;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.cache.LruDiskCache;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/* loaded from: classes4.dex */
public class AssetsRequest extends UriRequest {
    private long contentLength = 0;
    private InputStream inputStream;

    @Override // org.xutils.http.request.UriRequest
    public void clearCacheHeader() {
    }

    @Override // org.xutils.http.request.UriRequest
    public String getETag() {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getExpiration() {
        return Long.MAX_VALUE;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getHeaderFieldDate(String str, long j) {
        return j;
    }

    @Override // org.xutils.http.request.UriRequest
    public String getResponseHeader(String str) {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public Map<String, List<String>> getResponseHeaders() {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public String getResponseMessage() throws IOException {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public boolean isLoading() {
        return true;
    }

    @Override // org.xutils.http.request.UriRequest
    public void sendRequest() throws Throwable {
    }

    public AssetsRequest(RequestParams requestParams, Type type) throws Throwable {
        super(requestParams, type);
    }

    @Override // org.xutils.http.request.UriRequest
    public String getCacheKey() {
        return this.queryUrl;
    }

    @Override // org.xutils.http.request.UriRequest
    public Object loadResult() throws Throwable {
        return this.loader.mo6878load(this);
    }

    @Override // org.xutils.http.request.UriRequest
    public Object loadResultFromCache() throws Throwable {
        Date lastModify;
        DiskCacheEntity diskCacheEntity = LruDiskCache.getDiskCache(this.params.getCacheDirName()).setMaxSize(this.params.getCacheSize()).get(getCacheKey());
        if (diskCacheEntity == null || (lastModify = diskCacheEntity.getLastModify()) == null || lastModify.getTime() < getAssetsLastModified()) {
            return null;
        }
        return this.loader.mo6879loadFromCache(diskCacheEntity);
    }

    @Override // org.xutils.http.request.UriRequest
    public InputStream getInputStream() throws IOException {
        if (this.inputStream == null && this.callingClassLoader != null) {
            this.inputStream = this.callingClassLoader.getResourceAsStream("assets/" + this.queryUrl.substring(9));
            this.contentLength = (long) this.inputStream.available();
        }
        return this.inputStream;
    }

    @Override // org.xutils.http.request.UriRequest, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtil.closeQuietly(this.inputStream);
        this.inputStream = null;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getContentLength() {
        try {
            getInputStream();
            return this.contentLength;
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
            return 0L;
        }
    }

    @Override // org.xutils.http.request.UriRequest
    public int getResponseCode() throws IOException {
        return getInputStream() != null ? 200 : 404;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getLastModified() {
        return getAssetsLastModified();
    }

    protected long getAssetsLastModified() {
        return new File(C5540x.app().getApplicationInfo().sourceDir).lastModified();
    }
}
