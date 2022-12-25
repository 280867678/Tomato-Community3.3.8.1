package org.xutils.http.request;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.cache.LruDiskCache;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.http.BaseParams;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestInterceptListener;
import org.xutils.http.body.ProgressBody;
import org.xutils.http.body.RequestBody;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.p149ex.HttpException;

/* loaded from: classes4.dex */
public class HttpRequest extends UriRequest {
    private static final CookieManager COOKIE_MANAGER = new CookieManager(DbCookieStore.INSTANCE, CookiePolicy.ACCEPT_ALL);
    private String cacheKey = null;
    private boolean isLoading = false;
    private InputStream inputStream = null;
    private HttpURLConnection connection = null;
    private int responseCode = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public HttpRequest(RequestParams requestParams, Type type) throws Throwable {
        super(requestParams, type);
    }

    @Override // org.xutils.http.request.UriRequest
    protected String buildQueryUrl(RequestParams requestParams) {
        String uri = requestParams.getUri();
        StringBuilder sb = new StringBuilder(uri);
        if (!uri.contains("?")) {
            sb.append("?");
        } else if (!uri.endsWith("?")) {
            sb.append("&");
        }
        List<KeyValue> queryStringParams = requestParams.getQueryStringParams();
        if (queryStringParams != null) {
            for (KeyValue keyValue : queryStringParams) {
                String str = keyValue.key;
                String valueStr = keyValue.getValueStr();
                if (!TextUtils.isEmpty(str) && valueStr != null) {
                    sb.append(Uri.encode(str, requestParams.getCharset()));
                    sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                    sb.append(Uri.encode(valueStr, requestParams.getCharset()));
                    sb.append("&");
                }
            }
        }
        if (sb.charAt(sb.length() - 1) == '&') {
            sb.deleteCharAt(sb.length() - 1);
        }
        if (sb.charAt(sb.length() - 1) == '?') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override // org.xutils.http.request.UriRequest
    public String getRequestUri() {
        URL url;
        String str = this.queryUrl;
        HttpURLConnection httpURLConnection = this.connection;
        return (httpURLConnection == null || (url = httpURLConnection.getURL()) == null) ? str : url.toString();
    }

    @Override // org.xutils.http.request.UriRequest
    @TargetApi(19)
    public void sendRequest() throws Throwable {
        RequestBody requestBody;
        SSLSocketFactory sslSocketFactory;
        this.isLoading = false;
        this.responseCode = 0;
        URL url = new URL(this.queryUrl);
        Proxy proxy = this.params.getProxy();
        if (proxy != null) {
            this.connection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            this.connection = (HttpURLConnection) url.openConnection();
        }
        if (Build.VERSION.SDK_INT < 19) {
            this.connection.setRequestProperty("Connection", MainFragment.CLOSE_EVENT);
        }
        this.connection.setReadTimeout(this.params.getReadTimeout());
        this.connection.setConnectTimeout(this.params.getConnectTimeout());
        this.connection.setInstanceFollowRedirects(this.params.getRedirectHandler() == null);
        if ((this.connection instanceof HttpsURLConnection) && (sslSocketFactory = this.params.getSslSocketFactory()) != null) {
            ((HttpsURLConnection) this.connection).setSSLSocketFactory(sslSocketFactory);
        }
        if (this.params.isUseCookie()) {
            try {
                List<String> list = COOKIE_MANAGER.get(url.toURI(), new HashMap(0)).get("Cookie");
                if (list != null) {
                    this.connection.setRequestProperty("Cookie", TextUtils.join(";", list));
                }
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
            }
        }
        List<BaseParams.Header> headers = this.params.getHeaders();
        if (headers != null) {
            for (BaseParams.Header header : headers) {
                String str = header.key;
                String valueStr = header.getValueStr();
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(valueStr)) {
                    if (header.setHeader) {
                        this.connection.setRequestProperty(str, valueStr);
                    } else {
                        this.connection.addRequestProperty(str, valueStr);
                    }
                }
            }
        }
        RequestInterceptListener requestInterceptListener = this.requestInterceptListener;
        if (requestInterceptListener != null) {
            requestInterceptListener.beforeRequest(this);
        }
        HttpMethod method = this.params.getMethod();
        try {
            this.connection.setRequestMethod(method.toString());
        } catch (ProtocolException e) {
            Field declaredField = HttpURLConnection.class.getDeclaredField("method");
            declaredField.setAccessible(true);
            declaredField.set(this.connection, method.toString());
        }
        if (HttpMethod.permitsRequestBody(method) && (requestBody = this.params.getRequestBody()) != null) {
            if (requestBody instanceof ProgressBody) {
                ((ProgressBody) requestBody).setProgressHandler(this.progressHandler);
            }
            String contentType = requestBody.getContentType();
            if (!TextUtils.isEmpty(contentType)) {
                this.connection.setRequestProperty("Content-Type", contentType);
            }
            long contentLength = requestBody.getContentLength();
            if (contentLength < 0) {
                this.connection.setChunkedStreamingMode(262144);
            } else if (contentLength < 2147483647L) {
                this.connection.setFixedLengthStreamingMode((int) contentLength);
            } else if (Build.VERSION.SDK_INT >= 19) {
                this.connection.setFixedLengthStreamingMode(contentLength);
            } else {
                this.connection.setChunkedStreamingMode(262144);
            }
            this.connection.setRequestProperty("Content-Length", String.valueOf(contentLength));
            this.connection.setDoOutput(true);
            requestBody.writeTo(this.connection.getOutputStream());
        }
        if (this.params.isUseCookie()) {
            try {
                Map<String, List<String>> headerFields = this.connection.getHeaderFields();
                if (headerFields != null) {
                    COOKIE_MANAGER.put(url.toURI(), headerFields);
                }
            } catch (Throwable th2) {
                LogUtil.m43e(th2.getMessage(), th2);
            }
        }
        this.responseCode = this.connection.getResponseCode();
        RequestInterceptListener requestInterceptListener2 = this.requestInterceptListener;
        if (requestInterceptListener2 != null) {
            requestInterceptListener2.afterRequest(this);
        }
        int i = this.responseCode;
        if (i == 204 || i == 205) {
            throw new HttpException(this.responseCode, getResponseMessage());
        }
        if (i >= 300) {
            HttpException httpException = new HttpException(i, getResponseMessage());
            try {
                httpException.setResult(IOUtil.readStr(getInputStream(), this.params.getCharset()));
            } catch (Throwable unused) {
            }
            LogUtil.m44e(httpException.toString() + ", url: " + this.queryUrl);
            throw httpException;
        }
        this.isLoading = true;
    }

    @Override // org.xutils.http.request.UriRequest
    public boolean isLoading() {
        return this.isLoading;
    }

    @Override // org.xutils.http.request.UriRequest
    public String getCacheKey() {
        if (this.cacheKey == null) {
            this.cacheKey = this.params.getCacheKey();
            if (TextUtils.isEmpty(this.cacheKey)) {
                this.cacheKey = this.params.toString();
            }
        }
        return this.cacheKey;
    }

    @Override // org.xutils.http.request.UriRequest
    public Object loadResult() throws Throwable {
        this.isLoading = true;
        return super.loadResult();
    }

    @Override // org.xutils.http.request.UriRequest
    public Object loadResultFromCache() throws Throwable {
        this.isLoading = true;
        DiskCacheEntity diskCacheEntity = LruDiskCache.getDiskCache(this.params.getCacheDirName()).setMaxSize(this.params.getCacheSize()).get(getCacheKey());
        if (diskCacheEntity != null) {
            if (HttpMethod.permitsCache(this.params.getMethod())) {
                Date lastModify = diskCacheEntity.getLastModify();
                if (lastModify.getTime() > 0) {
                    this.params.setHeader("If-Modified-Since", toGMTString(lastModify));
                }
                String etag = diskCacheEntity.getEtag();
                if (!TextUtils.isEmpty(etag)) {
                    this.params.setHeader("If-None-Match", etag);
                }
            }
            return this.loader.mo6879loadFromCache(diskCacheEntity);
        }
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public void clearCacheHeader() {
        this.params.setHeader("If-Modified-Since", null);
        this.params.setHeader("If-None-Match", null);
    }

    @Override // org.xutils.http.request.UriRequest
    public InputStream getInputStream() throws IOException {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null && this.inputStream == null) {
            this.inputStream = httpURLConnection.getResponseCode() >= 400 ? this.connection.getErrorStream() : this.connection.getInputStream();
        }
        return this.inputStream;
    }

    @Override // org.xutils.http.request.UriRequest, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            IOUtil.closeQuietly(inputStream);
            this.inputStream = null;
        }
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    @Override // org.xutils.http.request.UriRequest
    public long getContentLength() {
        int available;
        HttpURLConnection httpURLConnection = this.connection;
        long j = 0;
        try {
            if (httpURLConnection != null) {
                try {
                    j = httpURLConnection.getContentLength();
                } catch (Throwable th) {
                    LogUtil.m43e(th.getMessage(), th);
                }
                if (j >= 1) {
                    return j;
                }
                available = getInputStream().available();
            } else {
                available = getInputStream().available();
            }
            j = available;
            return j;
        } catch (Throwable unused) {
            return j;
        }
    }

    @Override // org.xutils.http.request.UriRequest
    public int getResponseCode() throws IOException {
        if (this.connection != null) {
            return this.responseCode;
        }
        return getInputStream() != null ? 200 : 404;
    }

    @Override // org.xutils.http.request.UriRequest
    public String getResponseMessage() throws IOException {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            return URLDecoder.decode(httpURLConnection.getResponseMessage(), this.params.getCharset());
        }
        return null;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getExpiration() {
        HttpURLConnection httpURLConnection = this.connection;
        long j = -1;
        if (httpURLConnection == null) {
            return -1L;
        }
        String headerField = httpURLConnection.getHeaderField("Cache-Control");
        if (!TextUtils.isEmpty(headerField)) {
            StringTokenizer stringTokenizer = new StringTokenizer(headerField, ",");
            while (true) {
                if (!stringTokenizer.hasMoreTokens()) {
                    break;
                }
                String lowerCase = stringTokenizer.nextToken().trim().toLowerCase();
                if (lowerCase.startsWith("max-age")) {
                    int indexOf = lowerCase.indexOf(61);
                    if (indexOf > 0) {
                        try {
                            long parseLong = Long.parseLong(lowerCase.substring(indexOf + 1).trim());
                            if (parseLong > 0) {
                                j = System.currentTimeMillis() + (parseLong * 1000);
                            }
                        } catch (Throwable th) {
                            LogUtil.m43e(th.getMessage(), th);
                        }
                    }
                }
            }
        }
        if (j <= 0) {
            j = this.connection.getExpiration();
        }
        if (j <= 0 && this.params.getCacheMaxAge() > 0) {
            j = System.currentTimeMillis() + this.params.getCacheMaxAge();
        }
        if (j > 0) {
            return j;
        }
        return Long.MAX_VALUE;
    }

    @Override // org.xutils.http.request.UriRequest
    public long getLastModified() {
        return getHeaderFieldDate("Last-Modified", System.currentTimeMillis());
    }

    @Override // org.xutils.http.request.UriRequest
    public String getETag() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection == null) {
            return null;
        }
        return httpURLConnection.getHeaderField("ETag");
    }

    @Override // org.xutils.http.request.UriRequest
    public String getResponseHeader(String str) {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection == null) {
            return null;
        }
        return httpURLConnection.getHeaderField(str);
    }

    @Override // org.xutils.http.request.UriRequest
    public Map<String, List<String>> getResponseHeaders() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection == null) {
            return null;
        }
        return httpURLConnection.getHeaderFields();
    }

    @Override // org.xutils.http.request.UriRequest
    public long getHeaderFieldDate(String str, long j) {
        HttpURLConnection httpURLConnection = this.connection;
        return httpURLConnection == null ? j : httpURLConnection.getHeaderFieldDate(str, j);
    }

    private static String toGMTString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT'", Locale.US);
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(timeZone);
        new GregorianCalendar(timeZone).setTimeInMillis(date.getTime());
        return simpleDateFormat.format(date);
    }
}
