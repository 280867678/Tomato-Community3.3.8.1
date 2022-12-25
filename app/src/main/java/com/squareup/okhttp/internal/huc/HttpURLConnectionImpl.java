package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpDate;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.Sink;

/* loaded from: classes3.dex */
public class HttpURLConnectionImpl extends HttpURLConnection {
    final OkHttpClient client;
    Handshake handshake;
    protected HttpEngine httpEngine;
    protected IOException httpEngineFailure;
    private int redirectionCount;
    private Headers responseHeaders;
    private Route route;
    private Headers.Builder requestHeaders = new Headers.Builder();
    private long fixedContentLength = -1;

    public HttpURLConnectionImpl(URL url, OkHttpClient okHttpClient) {
        super(url);
        this.client = okHttpClient;
    }

    @Override // java.net.URLConnection
    public final void connect() throws IOException {
        initHttpEngine();
        do {
        } while (!execute(false));
    }

    @Override // java.net.HttpURLConnection
    public final void disconnect() {
        HttpEngine httpEngine = this.httpEngine;
        if (httpEngine == null) {
            return;
        }
        httpEngine.disconnect();
    }

    @Override // java.net.HttpURLConnection
    public final InputStream getErrorStream() {
        try {
            HttpEngine response = getResponse();
            if (response.hasResponseBody() && response.getResponse().code() >= 400) {
                return response.getResponseBodyBytes();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    private Headers getHeaders() throws IOException {
        if (this.responseHeaders == null) {
            Response response = getResponse().getResponse();
            Headers.Builder newBuilder = response.headers().newBuilder();
            newBuilder.add(Platform.get().getPrefix() + "-Response-Source", responseSourceHeader(response));
            this.responseHeaders = newBuilder.build();
        }
        return this.responseHeaders;
    }

    private static String responseSourceHeader(Response response) {
        if (response.networkResponse() == null) {
            if (response.cacheResponse() == null) {
                return "NONE";
            }
            return "CACHE " + response.code();
        } else if (response.cacheResponse() == null) {
            return "NETWORK " + response.code();
        } else {
            return "CONDITIONAL_CACHE " + response.networkResponse().code();
        }
    }

    @Override // java.net.HttpURLConnection, java.net.URLConnection
    public final String getHeaderField(int i) {
        try {
            return getHeaders().value(i);
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // java.net.URLConnection
    public final String getHeaderField(String str) {
        try {
            return str == null ? StatusLine.get(getResponse().getResponse()).toString() : getHeaders().get(str);
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // java.net.HttpURLConnection, java.net.URLConnection
    public final String getHeaderFieldKey(int i) {
        try {
            return getHeaders().name(i);
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // java.net.URLConnection
    public final Map<String, List<String>> getHeaderFields() {
        try {
            return OkHeaders.toMultimap(getHeaders(), StatusLine.get(getResponse().getResponse()).toString());
        } catch (IOException unused) {
            return Collections.emptyMap();
        }
    }

    @Override // java.net.URLConnection
    public final Map<String, List<String>> getRequestProperties() {
        if (((HttpURLConnection) this).connected) {
            throw new IllegalStateException("Cannot access request header fields after connection is set");
        }
        return OkHeaders.toMultimap(this.requestHeaders.build(), null);
    }

    @Override // java.net.URLConnection
    public final InputStream getInputStream() throws IOException {
        if (!((HttpURLConnection) this).doInput) {
            throw new ProtocolException("This protocol does not support input");
        }
        HttpEngine response = getResponse();
        if (getResponseCode() >= 400) {
            throw new FileNotFoundException(((HttpURLConnection) this).url.toString());
        }
        InputStream responseBodyBytes = response.getResponseBodyBytes();
        if (responseBodyBytes != null) {
            return responseBodyBytes;
        }
        throw new ProtocolException("No response body exists; responseCode=" + getResponseCode());
    }

    @Override // java.net.URLConnection
    public final OutputStream getOutputStream() throws IOException {
        connect();
        BufferedSink bufferedRequestBody = this.httpEngine.getBufferedRequestBody();
        if (bufferedRequestBody == null) {
            throw new ProtocolException("method does not support a request body: " + ((HttpURLConnection) this).method);
        } else if (this.httpEngine.hasResponse()) {
            throw new ProtocolException("cannot write request body after response has been read");
        } else {
            return bufferedRequestBody.outputStream();
        }
    }

    @Override // java.net.HttpURLConnection, java.net.URLConnection
    public final Permission getPermission() throws IOException {
        String host = getURL().getHost();
        int effectivePort = Util.getEffectivePort(getURL());
        if (usingProxy()) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) this.client.getProxy().address();
            String hostName = inetSocketAddress.getHostName();
            effectivePort = inetSocketAddress.getPort();
            host = hostName;
        }
        return new SocketPermission(host + ":" + effectivePort, "connect, resolve");
    }

    @Override // java.net.URLConnection
    public final String getRequestProperty(String str) {
        if (str == null) {
            return null;
        }
        return this.requestHeaders.get(str);
    }

    @Override // java.net.URLConnection
    public void setConnectTimeout(int i) {
        this.client.setConnectTimeout(i, TimeUnit.MILLISECONDS);
    }

    @Override // java.net.URLConnection
    public int getConnectTimeout() {
        return this.client.getConnectTimeout();
    }

    @Override // java.net.URLConnection
    public void setReadTimeout(int i) {
        this.client.setReadTimeout(i, TimeUnit.MILLISECONDS);
    }

    @Override // java.net.URLConnection
    public int getReadTimeout() {
        return this.client.getReadTimeout();
    }

    private void initHttpEngine() throws IOException {
        IOException iOException = this.httpEngineFailure;
        if (iOException != null) {
            throw iOException;
        }
        if (this.httpEngine != null) {
            return;
        }
        ((HttpURLConnection) this).connected = true;
        try {
            if (((HttpURLConnection) this).doOutput) {
                if (((HttpURLConnection) this).method.equals("GET")) {
                    ((HttpURLConnection) this).method = "POST";
                } else if (!HttpMethod.hasRequestBody(((HttpURLConnection) this).method)) {
                    throw new ProtocolException(((HttpURLConnection) this).method + " does not support writing");
                }
            }
            this.httpEngine = newHttpEngine(((HttpURLConnection) this).method, null, (!((HttpURLConnection) this).doOutput || this.fixedContentLength != 0) ? null : Util.emptySink(), null);
        } catch (IOException e) {
            this.httpEngineFailure = e;
            throw e;
        }
    }

    private HttpEngine newHttpEngine(String str, Connection connection, RetryableSink retryableSink, Response response) {
        boolean z;
        Request.Builder builder = new Request.Builder();
        builder.url(getURL());
        builder.method(str, null);
        Headers build = this.requestHeaders.build();
        boolean z2 = false;
        for (int i = 0; i < build.size(); i++) {
            builder.addHeader(build.name(i), build.value(i));
        }
        if (HttpMethod.hasRequestBody(str)) {
            long j = this.fixedContentLength;
            if (j != -1) {
                builder.header("Content-Length", Long.toString(j));
            } else if (((HttpURLConnection) this).chunkLength > 0) {
                builder.header("Transfer-Encoding", "chunked");
            } else {
                z2 = true;
            }
            if (build.get("Content-Type") == null) {
                builder.header("Content-Type", "application/x-www-form-urlencoded");
            }
            z = z2;
        } else {
            z = false;
        }
        if (build.get("User-Agent") == null) {
            builder.header("User-Agent", defaultUserAgent());
        }
        Request build2 = builder.build();
        OkHttpClient okHttpClient = this.client;
        if (Internal.instance.internalCache(okHttpClient) != null && !getUseCaches()) {
            okHttpClient = this.client.m6513clone();
            okHttpClient.setCache(null);
        }
        return new HttpEngine(okHttpClient, build2, z, connection, null, retryableSink, response);
    }

    private String defaultUserAgent() {
        String property = System.getProperty("http.agent");
        if (property != null) {
            return property;
        }
        return "Java" + System.getProperty("java.version");
    }

    private HttpEngine getResponse() throws IOException {
        initHttpEngine();
        if (this.httpEngine.hasResponse()) {
            return this.httpEngine;
        }
        while (true) {
            if (execute(true)) {
                Response response = this.httpEngine.getResponse();
                Request followUpRequest = this.httpEngine.followUpRequest();
                if (followUpRequest == null) {
                    this.httpEngine.releaseConnection();
                    return this.httpEngine;
                }
                if (response.isRedirect()) {
                    int i = this.redirectionCount + 1;
                    this.redirectionCount = i;
                    if (i > 20) {
                        throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                    }
                }
                ((HttpURLConnection) this).url = followUpRequest.url();
                this.requestHeaders = followUpRequest.headers().newBuilder();
                Sink requestBody = this.httpEngine.getRequestBody();
                if (!followUpRequest.method().equals(((HttpURLConnection) this).method)) {
                    requestBody = null;
                }
                if (requestBody != null && !(requestBody instanceof RetryableSink)) {
                    throw new HttpRetryException("Cannot retry streamed HTTP body", ((HttpURLConnection) this).responseCode);
                }
                if (!this.httpEngine.sameConnection(followUpRequest.url())) {
                    this.httpEngine.releaseConnection();
                }
                this.httpEngine = newHttpEngine(followUpRequest.method(), this.httpEngine.close(), (RetryableSink) requestBody, response);
            }
        }
    }

    private boolean execute(boolean z) throws IOException {
        try {
            this.httpEngine.sendRequest();
            this.route = this.httpEngine.getRoute();
            this.handshake = this.httpEngine.getConnection() != null ? this.httpEngine.getConnection().getHandshake() : null;
            if (!z) {
                return true;
            }
            this.httpEngine.readResponse();
            return true;
        } catch (IOException e) {
            HttpEngine recover = this.httpEngine.recover(e);
            if (recover != null) {
                this.httpEngine = recover;
                return false;
            }
            this.httpEngineFailure = e;
            throw e;
        }
    }

    @Override // java.net.HttpURLConnection
    public final boolean usingProxy() {
        Route route = this.route;
        Proxy proxy = route != null ? route.getProxy() : this.client.getProxy();
        return (proxy == null || proxy.type() == Proxy.Type.DIRECT) ? false : true;
    }

    @Override // java.net.HttpURLConnection
    public String getResponseMessage() throws IOException {
        return getResponse().getResponse().message();
    }

    @Override // java.net.HttpURLConnection
    public final int getResponseCode() throws IOException {
        return getResponse().getResponse().code();
    }

    @Override // java.net.URLConnection
    public final void setRequestProperty(String str, String str2) {
        if (!((HttpURLConnection) this).connected) {
            if (str == null) {
                throw new NullPointerException("field == null");
            }
            if (str2 == null) {
                Platform platform = Platform.get();
                platform.logW("Ignoring header " + str + " because its value was null.");
                return;
            } else if ("X-Android-Transports".equals(str) || "X-Android-Protocols".equals(str)) {
                setProtocols(str2, false);
                return;
            } else {
                this.requestHeaders.set(str, str2);
                return;
            }
        }
        throw new IllegalStateException("Cannot set request property after connection is made");
    }

    @Override // java.net.URLConnection
    public void setIfModifiedSince(long j) {
        super.setIfModifiedSince(j);
        if (((HttpURLConnection) this).ifModifiedSince != 0) {
            this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(((HttpURLConnection) this).ifModifiedSince)));
        } else {
            this.requestHeaders.removeAll("If-Modified-Since");
        }
    }

    @Override // java.net.URLConnection
    public final void addRequestProperty(String str, String str2) {
        if (!((HttpURLConnection) this).connected) {
            if (str == null) {
                throw new NullPointerException("field == null");
            }
            if (str2 == null) {
                Platform platform = Platform.get();
                platform.logW("Ignoring header " + str + " because its value was null.");
                return;
            } else if ("X-Android-Transports".equals(str) || "X-Android-Protocols".equals(str)) {
                setProtocols(str2, true);
                return;
            } else {
                this.requestHeaders.add(str, str2);
                return;
            }
        }
        throw new IllegalStateException("Cannot add request property after connection is made");
    }

    private void setProtocols(String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.addAll(this.client.getProtocols());
        }
        for (String str2 : str.split(",", -1)) {
            try {
                arrayList.add(Protocol.get(str2));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        this.client.setProtocols(arrayList);
    }

    @Override // java.net.HttpURLConnection
    public void setRequestMethod(String str) throws ProtocolException {
        if (!HttpMethod.METHODS.contains(str)) {
            throw new ProtocolException("Expected one of " + HttpMethod.METHODS + " but was " + str);
        }
        ((HttpURLConnection) this).method = str;
    }

    @Override // java.net.HttpURLConnection
    public void setFixedLengthStreamingMode(int i) {
        setFixedLengthStreamingMode(i);
    }

    @Override // java.net.HttpURLConnection
    public void setFixedLengthStreamingMode(long j) {
        if (((HttpURLConnection) this).connected) {
            throw new IllegalStateException("Already connected");
        }
        if (((HttpURLConnection) this).chunkLength > 0) {
            throw new IllegalStateException("Already in chunked mode");
        }
        if (j < 0) {
            throw new IllegalArgumentException("contentLength < 0");
        }
        this.fixedContentLength = j;
        ((HttpURLConnection) this).fixedContentLength = (int) Math.min(j, 2147483647L);
    }
}
