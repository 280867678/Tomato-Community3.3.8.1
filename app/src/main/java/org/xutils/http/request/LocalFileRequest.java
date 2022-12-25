package org.xutils.http.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.xutils.common.util.IOUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.loader.FileLoader;
import org.xutils.http.loader.Loader;

/* loaded from: classes4.dex */
public class LocalFileRequest extends UriRequest {
    private InputStream inputStream;

    @Override // org.xutils.http.request.UriRequest
    public void clearCacheHeader() {
    }

    @Override // org.xutils.http.request.UriRequest
    public String getCacheKey() {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public String getETag() {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getExpiration() {
        return -1L;
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
    public Object loadResultFromCache() throws Throwable {
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public void save2Cache() {
    }

    @Override // org.xutils.http.request.UriRequest
    public void sendRequest() throws Throwable {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalFileRequest(RequestParams requestParams, Type type) throws Throwable {
        super(requestParams, type);
    }

    @Override // org.xutils.http.request.UriRequest
    public Object loadResult() throws Throwable {
        Loader<?> loader = this.loader;
        if (loader instanceof FileLoader) {
            return getFile();
        }
        return loader.mo6878load(this);
    }

    private File getFile() {
        String str;
        if (this.queryUrl.startsWith("file:")) {
            str = this.queryUrl.substring(5);
        } else {
            str = this.queryUrl;
        }
        return new File(str);
    }

    @Override // org.xutils.http.request.UriRequest
    public InputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            this.inputStream = new FileInputStream(getFile());
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
        return getFile().length();
    }

    @Override // org.xutils.http.request.UriRequest
    public int getResponseCode() throws IOException {
        return getFile().exists() ? 200 : 404;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getLastModified() {
        return getFile().lastModified();
    }
}
