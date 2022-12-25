package org.xutils.http;

import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.Executor;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import org.xutils.common.task.Priority;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParamsHelper;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;
import org.xutils.http.app.HttpRetryHandler;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.http.app.RedirectHandler;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.body.RequestBody;

/* loaded from: classes4.dex */
public class RequestParams extends BaseParams {
    private boolean autoRename;
    private boolean autoResume;
    private String buildCacheKey;
    private String buildUri;
    private ParamsBuilder builder;
    private String cacheDirName;
    private final String[] cacheKeys;
    private long cacheMaxAge;
    private long cacheSize;
    private boolean cancelFast;
    private int connectTimeout;
    private Executor executor;
    private HostnameVerifier hostnameVerifier;
    private HttpRequest httpRequest;
    private HttpRetryHandler httpRetryHandler;
    private boolean invokedGetHttpRequest;
    private int loadingUpdateMaxTimeSpan;
    private int maxRetryCount;
    private Priority priority;
    private Proxy proxy;
    private int readTimeout;
    private RedirectHandler redirectHandler;
    private RequestTracker requestTracker;
    private String saveFilePath;
    private final String[] signs;
    private SSLSocketFactory sslSocketFactory;
    private String uri;
    private boolean useCookie;

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addBodyParameter(String str, File file) {
        super.addBodyParameter(str, file);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addBodyParameter(String str, Object obj, String str2) {
        super.addBodyParameter(str, obj, str2);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addBodyParameter(String str, Object obj, String str2, String str3) {
        super.addBodyParameter(str, obj, str2, str3);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addBodyParameter(String str, String str2) {
        super.addBodyParameter(str, str2);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addHeader(String str, String str2) {
        super.addHeader(str, str2);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addParameter(String str, Object obj) {
        super.addParameter(str, obj);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void addQueryStringParameter(String str, String str2) {
        super.addQueryStringParameter(str, str2);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void clearParams() {
        super.clearParams();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ String getBodyContent() {
        return super.getBodyContent();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ List getBodyParams() {
        return super.getBodyParams();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ String getCharset() {
        return super.getCharset();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ List getFileParams() {
        return super.getFileParams();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ List getHeaders() {
        return super.getHeaders();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ HttpMethod getMethod() {
        return super.getMethod();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ List getParams(String str) {
        return super.getParams(str);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ List getQueryStringParams() {
        return super.getQueryStringParams();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ RequestBody getRequestBody() throws IOException {
        return super.getRequestBody();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ String getStringParameter(String str) {
        return super.getStringParameter(str);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ List getStringParams() {
        return super.getStringParams();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ boolean isAsJsonContent() {
        return super.isAsJsonContent();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ boolean isMultipart() {
        return super.isMultipart();
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void removeParameter(String str) {
        super.removeParameter(str);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setAsJsonContent(boolean z) {
        super.setAsJsonContent(z);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setBodyContent(String str) {
        super.setBodyContent(str);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setCharset(String str) {
        super.setCharset(str);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setHeader(String str, String str2) {
        super.setHeader(str, str2);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setMethod(HttpMethod httpMethod) {
        super.setMethod(httpMethod);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setMultipart(boolean z) {
        super.setMultipart(z);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ void setRequestBody(RequestBody requestBody) {
        super.setRequestBody(requestBody);
    }

    @Override // org.xutils.http.BaseParams
    public /* bridge */ /* synthetic */ String toJSONString() {
        return super.toJSONString();
    }

    public RequestParams() {
        this(null, null, null, null);
    }

    public RequestParams(String str) {
        this(str, null, null, null);
    }

    public RequestParams(String str, ParamsBuilder paramsBuilder, String[] strArr, String[] strArr2) {
        this.useCookie = true;
        this.priority = Priority.DEFAULT;
        this.connectTimeout = 15000;
        this.readTimeout = 15000;
        this.autoResume = true;
        this.autoRename = false;
        this.maxRetryCount = 2;
        this.cancelFast = false;
        this.loadingUpdateMaxTimeSpan = 300;
        this.invokedGetHttpRequest = false;
        if (str != null && paramsBuilder == null) {
            paramsBuilder = new DefaultParamsBuilder();
        }
        this.uri = str;
        this.signs = strArr;
        this.cacheKeys = strArr2;
        this.builder = paramsBuilder;
    }

    public void init() throws Throwable {
        if (!TextUtils.isEmpty(this.buildUri)) {
            return;
        }
        if (TextUtils.isEmpty(this.uri) && getHttpRequest() == null) {
            throw new IllegalStateException("uri is empty && @HttpRequest == null");
        }
        initEntityParams();
        this.buildUri = this.uri;
        HttpRequest httpRequest = getHttpRequest();
        if (httpRequest != null) {
            this.builder = httpRequest.builder().newInstance();
            this.buildUri = this.builder.buildUri(this, httpRequest);
            this.builder.buildParams(this);
            this.builder.buildSign(this, httpRequest.signs());
            if (this.sslSocketFactory != null) {
                return;
            }
            this.sslSocketFactory = this.builder.getSSLSocketFactory();
            return;
        }
        ParamsBuilder paramsBuilder = this.builder;
        if (paramsBuilder == null) {
            return;
        }
        paramsBuilder.buildParams(this);
        this.builder.buildSign(this, this.signs);
        if (this.sslSocketFactory != null) {
            return;
        }
        this.sslSocketFactory = this.builder.getSSLSocketFactory();
    }

    public String getUri() {
        return TextUtils.isEmpty(this.buildUri) ? this.uri : this.buildUri;
    }

    public void setUri(String str) {
        if (TextUtils.isEmpty(this.buildUri)) {
            this.uri = str;
        } else {
            this.buildUri = str;
        }
    }

    public String getCacheKey() {
        if (TextUtils.isEmpty(this.buildCacheKey) && this.builder != null) {
            HttpRequest httpRequest = getHttpRequest();
            if (httpRequest != null) {
                this.buildCacheKey = this.builder.buildCacheKey(this, httpRequest.cacheKeys());
            } else {
                this.buildCacheKey = this.builder.buildCacheKey(this, this.cacheKeys);
            }
        }
        return this.buildCacheKey;
    }

    public void setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public boolean isUseCookie() {
        return this.useCookie;
    }

    public void setUseCookie(boolean z) {
        this.useCookie = z;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int i) {
        if (i > 0) {
            this.connectTimeout = i;
        }
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(int i) {
        if (i > 0) {
            this.readTimeout = i;
        }
    }

    public String getCacheDirName() {
        return this.cacheDirName;
    }

    public void setCacheDirName(String str) {
        this.cacheDirName = str;
    }

    public long getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(long j) {
        this.cacheSize = j;
    }

    public long getCacheMaxAge() {
        return this.cacheMaxAge;
    }

    public void setCacheMaxAge(long j) {
        this.cacheMaxAge = j;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public boolean isAutoResume() {
        return this.autoResume;
    }

    public void setAutoResume(boolean z) {
        this.autoResume = z;
    }

    public boolean isAutoRename() {
        return this.autoRename;
    }

    public void setAutoRename(boolean z) {
        this.autoRename = z;
    }

    public String getSaveFilePath() {
        return this.saveFilePath;
    }

    public void setSaveFilePath(String str) {
        this.saveFilePath = str;
    }

    public int getMaxRetryCount() {
        return this.maxRetryCount;
    }

    public void setMaxRetryCount(int i) {
        this.maxRetryCount = i;
    }

    public boolean isCancelFast() {
        return this.cancelFast;
    }

    public void setCancelFast(boolean z) {
        this.cancelFast = z;
    }

    public int getLoadingUpdateMaxTimeSpan() {
        return this.loadingUpdateMaxTimeSpan;
    }

    public void setLoadingUpdateMaxTimeSpan(int i) {
        this.loadingUpdateMaxTimeSpan = i;
    }

    public HttpRetryHandler getHttpRetryHandler() {
        return this.httpRetryHandler;
    }

    public void setHttpRetryHandler(HttpRetryHandler httpRetryHandler) {
        this.httpRetryHandler = httpRetryHandler;
    }

    public RedirectHandler getRedirectHandler() {
        return this.redirectHandler;
    }

    public void setRedirectHandler(RedirectHandler redirectHandler) {
        this.redirectHandler = redirectHandler;
    }

    public RequestTracker getRequestTracker() {
        return this.requestTracker;
    }

    public void setRequestTracker(RequestTracker requestTracker) {
        this.requestTracker = requestTracker;
    }

    private void initEntityParams() {
        RequestParamsHelper.parseKV(this, getClass(), new RequestParamsHelper.ParseKVListener() { // from class: org.xutils.http.RequestParams.1
            @Override // org.xutils.http.RequestParamsHelper.ParseKVListener
            public void onParseKV(String str, Object obj) {
                RequestParams.this.addParameter(str, obj);
            }
        });
    }

    private HttpRequest getHttpRequest() {
        if (this.httpRequest == null && !this.invokedGetHttpRequest) {
            this.invokedGetHttpRequest = true;
            Class<?> cls = getClass();
            if (cls != RequestParams.class) {
                this.httpRequest = (HttpRequest) cls.getAnnotation(HttpRequest.class);
            }
        }
        return this.httpRequest;
    }

    @Override // org.xutils.http.BaseParams
    public String toString() {
        try {
            init();
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        String uri = getUri();
        if (TextUtils.isEmpty(uri)) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(uri);
        String str = "?";
        if (uri.contains(str)) {
            str = "&";
        }
        sb.append(str);
        sb.append(super.toString());
        return sb.toString();
    }
}
