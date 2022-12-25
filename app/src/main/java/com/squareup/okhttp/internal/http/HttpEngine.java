package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Dns;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheRequest;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* loaded from: classes3.dex */
public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody() { // from class: com.squareup.okhttp.internal.http.HttpEngine.1
        @Override // com.squareup.okhttp.ResponseBody
        public BufferedSource source() {
            return new Buffer();
        }
    };
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    final OkHttpClient client;
    private Connection connection;
    private Request networkRequest;
    private Response networkResponse;
    private final Response priorResponse;
    private Sink requestBodyOut;
    private BufferedSource responseBody;
    private InputStream responseBodyBytes;
    private Source responseTransferSource;
    private Route route;
    private RouteSelector routeSelector;
    long sentRequestMillis = -1;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean z, Connection connection, RouteSelector routeSelector, RetryableSink retryableSink, Response response) {
        this.client = okHttpClient;
        this.userRequest = request;
        this.bufferRequestBody = z;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = retryableSink;
        this.priorResponse = response;
        if (connection != null) {
            Internal.instance.setOwner(connection, this);
            this.route = connection.getRoute();
            return;
        }
        this.route = null;
    }

    public void sendRequest() throws IOException {
        if (this.cacheStrategy != null) {
            return;
        }
        if (this.transport != null) {
            throw new IllegalStateException();
        }
        Request networkRequest = networkRequest(this.userRequest);
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        Response response = internalCache != null ? internalCache.get(networkRequest) : null;
        this.cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), networkRequest, response).get();
        CacheStrategy cacheStrategy = this.cacheStrategy;
        this.networkRequest = cacheStrategy.networkRequest;
        this.cacheResponse = cacheStrategy.cacheResponse;
        if (internalCache != null) {
            internalCache.trackResponse(cacheStrategy);
        }
        if (response != null && this.cacheResponse == null) {
            Util.closeQuietly(response.body());
        }
        Request request = this.networkRequest;
        if (request != null) {
            if (this.connection == null) {
                connect(request);
            }
            if (Internal.instance.getOwner(this.connection) != this && !Internal.instance.isSpdy(this.connection)) {
                throw new AssertionError();
            }
            this.transport = Internal.instance.newTransport(this.connection, this);
            if (!hasRequestBody() || this.requestBodyOut != null) {
                return;
            }
            this.requestBodyOut = this.transport.createRequestBody(networkRequest);
            return;
        }
        if (this.connection != null) {
            Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
            this.connection = null;
        }
        Response response2 = this.cacheResponse;
        if (response2 != null) {
            Response.Builder newBuilder = response2.newBuilder();
            newBuilder.request(this.userRequest);
            newBuilder.priorResponse(stripBody(this.priorResponse));
            newBuilder.cacheResponse(stripBody(this.cacheResponse));
            this.userResponse = newBuilder.build();
        } else {
            Response.Builder builder = new Response.Builder();
            builder.request(this.userRequest);
            builder.priorResponse(stripBody(this.priorResponse));
            builder.protocol(Protocol.HTTP_1_1);
            builder.code(504);
            builder.message("Unsatisfiable Request (only-if-cached)");
            builder.body(EMPTY_BODY);
            this.userResponse = builder.build();
        }
        if (this.userResponse.body() == null) {
            return;
        }
        initContentStream(this.userResponse.body().source());
    }

    private static Response stripBody(Response response) {
        if (response == null || response.body() == null) {
            return response;
        }
        Response.Builder newBuilder = response.newBuilder();
        newBuilder.body(null);
        return newBuilder.build();
    }

    private void connect(Request request) throws IOException {
        SSLSocketFactory sSLSocketFactory;
        HostnameVerifier hostnameVerifier;
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        if (this.routeSelector == null) {
            String host = request.url().getHost();
            if (host == null || host.length() == 0) {
                throw new UnknownHostException(request.url().toString());
            }
            if (request.isHttps()) {
                sSLSocketFactory = this.client.getSslSocketFactory();
                hostnameVerifier = this.client.getHostnameVerifier();
            } else {
                sSLSocketFactory = null;
                hostnameVerifier = null;
            }
            this.routeSelector = new RouteSelector(new Address(host, Util.getEffectivePort(request.url()), this.client.getSocketFactory(), sSLSocketFactory, hostnameVerifier, this.client.getAuthenticator(), this.client.getProxy(), this.client.getProtocols()), request.uri(), this.client.getProxySelector(), this.client.getConnectionPool(), Dns.DEFAULT, Internal.instance.routeDatabase(this.client));
        }
        this.connection = this.routeSelector.next(request.method());
        Internal.instance.setOwner(this.connection, this);
        if (!Internal.instance.isConnected(this.connection)) {
            Internal.instance.connect(this.connection, this.client.getConnectTimeout(), this.client.getReadTimeout(), this.client.getWriteTimeout(), tunnelRequest(this.connection, request));
            if (Internal.instance.isSpdy(this.connection)) {
                Internal.instance.share(this.client.getConnectionPool(), this.connection);
            }
            Internal.instance.routeDatabase(this.client).connected(this.connection.getRoute());
        }
        Internal.instance.setTimeouts(this.connection, this.client.getReadTimeout(), this.client.getWriteTimeout());
        this.route = this.connection.getRoute();
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasRequestBody() {
        return HttpMethod.hasRequestBody(this.userRequest.method()) && !Util.emptySink().equals(this.requestBodyOut);
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy == null) {
            throw new IllegalStateException();
        }
        return this.requestBodyOut;
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink bufferedSink = this.bufferedRequestBody;
        if (bufferedSink != null) {
            return bufferedSink;
        }
        Sink requestBody = getRequestBody();
        if (requestBody == null) {
            return null;
        }
        BufferedSink buffer = Okio.buffer(requestBody);
        this.bufferedRequestBody = buffer;
        return buffer;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Response getResponse() {
        Response response = this.userResponse;
        if (response != null) {
            return response;
        }
        throw new IllegalStateException();
    }

    public BufferedSource getResponseBody() {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        return this.responseBody;
    }

    public InputStream getResponseBodyBytes() {
        InputStream inputStream = this.responseBodyBytes;
        if (inputStream != null) {
            return inputStream;
        }
        InputStream inputStream2 = Okio.buffer(getResponseBody()).inputStream();
        this.responseBodyBytes = inputStream2;
        return inputStream2;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public HttpEngine recover(IOException iOException, Sink sink) {
        Connection connection;
        RouteSelector routeSelector = this.routeSelector;
        if (routeSelector != null && (connection = this.connection) != null) {
            routeSelector.connectFailed(connection, iOException);
        }
        boolean z = sink == null || (sink instanceof RetryableSink);
        if (this.routeSelector == null && this.connection == null) {
            return null;
        }
        RouteSelector routeSelector2 = this.routeSelector;
        if ((routeSelector2 != null && !routeSelector2.hasNext()) || !isRecoverable(iOException) || !z) {
            return null;
        }
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, close(), this.routeSelector, (RetryableSink) sink, this.priorResponse);
    }

    public HttpEngine recover(IOException iOException) {
        return recover(iOException, this.requestBodyOut);
    }

    private boolean isRecoverable(IOException iOException) {
        return !((iOException instanceof SSLHandshakeException) && (iOException.getCause() instanceof CertificateException)) && !(iOException instanceof ProtocolException);
    }

    public Route getRoute() {
        return this.route;
    }

    private void maybeCache() throws IOException {
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        if (internalCache == null) {
            return;
        }
        if (!CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
            if (!HttpMethod.invalidatesCache(this.networkRequest.method())) {
                return;
            }
            try {
                internalCache.remove(this.networkRequest);
                return;
            } catch (IOException unused) {
                return;
            }
        }
        this.storeRequest = internalCache.put(stripBody(this.userResponse));
    }

    public void releaseConnection() throws IOException {
        Transport transport = this.transport;
        if (transport != null && this.connection != null) {
            transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public void disconnect() {
        Transport transport = this.transport;
        if (transport != null) {
            try {
                transport.disconnect(this);
            } catch (IOException unused) {
            }
        }
    }

    public Connection close() {
        BufferedSink bufferedSink = this.bufferedRequestBody;
        if (bufferedSink != null) {
            Util.closeQuietly(bufferedSink);
        } else {
            Sink sink = this.requestBodyOut;
            if (sink != null) {
                Util.closeQuietly(sink);
            }
        }
        BufferedSource bufferedSource = this.responseBody;
        if (bufferedSource == null) {
            Connection connection = this.connection;
            if (connection != null) {
                Util.closeQuietly(connection.getSocket());
            }
            this.connection = null;
            return null;
        }
        Util.closeQuietly(bufferedSource);
        Util.closeQuietly(this.responseBodyBytes);
        Transport transport = this.transport;
        if (transport != null && this.connection != null && !transport.canReuseConnection()) {
            Util.closeQuietly(this.connection.getSocket());
            this.connection = null;
            return null;
        }
        Connection connection2 = this.connection;
        if (connection2 != null && !Internal.instance.clearOwner(connection2)) {
            this.connection = null;
        }
        Connection connection3 = this.connection;
        this.connection = null;
        return connection3;
    }

    private void initContentStream(Source source) throws IOException {
        this.responseTransferSource = source;
        if (this.transparentGzip && "gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
            Response.Builder newBuilder = this.userResponse.newBuilder();
            newBuilder.removeHeader("Content-Encoding");
            newBuilder.removeHeader("Content-Length");
            this.userResponse = newBuilder.build();
            this.responseBody = Okio.buffer(new GzipSource(source));
            return;
        }
        this.responseBody = Okio.buffer(source);
    }

    public boolean hasResponseBody() {
        if (this.userRequest.method().equals("HEAD")) {
            return false;
        }
        int code = this.userResponse.code();
        return (((code >= 100 && code < 200) || code == 204 || code == 304) && OkHeaders.contentLength(this.networkResponse) == -1 && !"chunked".equalsIgnoreCase(this.networkResponse.header("Transfer-Encoding"))) ? false : true;
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder newBuilder = request.newBuilder();
        if (request.header("Host") == null) {
            newBuilder.header("Host", hostHeader(request.url()));
        }
        Connection connection = this.connection;
        if ((connection == null || connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
            newBuilder.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            newBuilder.header("Accept-Encoding", "gzip");
        }
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(newBuilder, cookieHandler.get(request.uri(), OkHeaders.toMultimap(newBuilder.build().headers(), null)));
        }
        return newBuilder.build();
    }

    public static String hostHeader(URL url) {
        if (Util.getEffectivePort(url) != Util.getDefaultPort(url.getProtocol())) {
            return url.getHost() + ":" + url.getPort();
        }
        return url.getHost();
    }

    public void readResponse() throws IOException {
        if (this.userResponse != null) {
            return;
        }
        if (this.networkRequest == null && this.cacheResponse == null) {
            throw new IllegalStateException("call sendRequest() first!");
        }
        if (this.networkRequest == null) {
            return;
        }
        BufferedSink bufferedSink = this.bufferedRequestBody;
        if (bufferedSink != null && bufferedSink.buffer().size() > 0) {
            this.bufferedRequestBody.flush();
        }
        if (this.sentRequestMillis == -1) {
            if (OkHeaders.contentLength(this.networkRequest) == -1) {
                Sink sink = this.requestBodyOut;
                if (sink instanceof RetryableSink) {
                    long contentLength = ((RetryableSink) sink).contentLength();
                    Request.Builder newBuilder = this.networkRequest.newBuilder();
                    newBuilder.header("Content-Length", Long.toString(contentLength));
                    this.networkRequest = newBuilder.build();
                }
            }
            this.transport.writeRequestHeaders(this.networkRequest);
        }
        Sink sink2 = this.requestBodyOut;
        if (sink2 != null) {
            BufferedSink bufferedSink2 = this.bufferedRequestBody;
            if (bufferedSink2 != null) {
                bufferedSink2.close();
            } else {
                sink2.close();
            }
            if ((this.requestBodyOut instanceof RetryableSink) && !Util.emptySink().equals(this.requestBodyOut)) {
                this.transport.writeRequestBody((RetryableSink) this.requestBodyOut);
            }
        }
        this.transport.flushRequest();
        Response.Builder readResponseHeaders = this.transport.readResponseHeaders();
        readResponseHeaders.request(this.networkRequest);
        readResponseHeaders.handshake(this.connection.getHandshake());
        readResponseHeaders.header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis));
        readResponseHeaders.header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis()));
        this.networkResponse = readResponseHeaders.build();
        Internal.instance.setProtocol(this.connection, this.networkResponse.protocol());
        receiveHeaders(this.networkResponse.headers());
        Response response = this.cacheResponse;
        if (response != null) {
            if (validate(response, this.networkResponse)) {
                Response.Builder newBuilder2 = this.cacheResponse.newBuilder();
                newBuilder2.request(this.userRequest);
                newBuilder2.priorResponse(stripBody(this.priorResponse));
                newBuilder2.headers(combine(this.cacheResponse.headers(), this.networkResponse.headers()));
                newBuilder2.cacheResponse(stripBody(this.cacheResponse));
                newBuilder2.networkResponse(stripBody(this.networkResponse));
                this.userResponse = newBuilder2.build();
                this.transport.emptyTransferStream();
                releaseConnection();
                InternalCache internalCache = Internal.instance.internalCache(this.client);
                internalCache.trackConditionalCacheHit();
                internalCache.update(this.cacheResponse, stripBody(this.userResponse));
                if (this.cacheResponse.body() == null) {
                    return;
                }
                initContentStream(this.cacheResponse.body().source());
                return;
            }
            Util.closeQuietly(this.cacheResponse.body());
        }
        Response.Builder newBuilder3 = this.networkResponse.newBuilder();
        newBuilder3.request(this.userRequest);
        newBuilder3.priorResponse(stripBody(this.priorResponse));
        newBuilder3.cacheResponse(stripBody(this.cacheResponse));
        newBuilder3.networkResponse(stripBody(this.networkResponse));
        this.userResponse = newBuilder3.build();
        if (!hasResponseBody()) {
            this.responseTransferSource = this.transport.getTransferStream(this.storeRequest);
            this.responseBody = Okio.buffer(this.responseTransferSource);
            return;
        }
        maybeCache();
        initContentStream(this.transport.getTransferStream(this.storeRequest));
    }

    private static boolean validate(Response response, Response response2) {
        Date date;
        if (response2.code() == 304) {
            return true;
        }
        Date date2 = response.headers().getDate("Last-Modified");
        return (date2 == null || (date = response2.headers().getDate("Last-Modified")) == null || date.getTime() >= date2.getTime()) ? false : true;
    }

    private static Headers combine(Headers headers, Headers headers2) throws IOException {
        Headers.Builder builder = new Headers.Builder();
        for (int i = 0; i < headers.size(); i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            if ((!"Warning".equals(name) || !value.startsWith("1")) && (!OkHeaders.isEndToEnd(name) || headers2.get(name) == null)) {
                builder.add(name, value);
            }
        }
        for (int i2 = 0; i2 < headers2.size(); i2++) {
            String name2 = headers2.name(i2);
            if (OkHeaders.isEndToEnd(name2)) {
                builder.add(name2, headers2.value(i2));
            }
        }
        return builder.build();
    }

    private Request tunnelRequest(Connection connection, Request request) throws IOException {
        String str;
        if (!connection.getRoute().requiresTunnel()) {
            return null;
        }
        String host = request.url().getHost();
        int effectivePort = Util.getEffectivePort(request.url());
        if (effectivePort == Util.getDefaultPort("https")) {
            str = host;
        } else {
            str = host + ":" + effectivePort;
        }
        Request.Builder builder = new Request.Builder();
        builder.url(new URL("https", host, effectivePort, "/"));
        builder.header("Host", str);
        builder.header("Proxy-Connection", "Keep-Alive");
        String header = request.header("User-Agent");
        if (header != null) {
            builder.header("User-Agent", header);
        }
        String header2 = request.header("Proxy-Authorization");
        if (header2 != null) {
            builder.header("Proxy-Authorization", header2);
        }
        return builder.build();
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public Request followUpRequest() throws IOException {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        Proxy proxy = getRoute() != null ? getRoute().getProxy() : this.client.getProxy();
        int code = this.userResponse.code();
        if (code != 307) {
            if (code != 401) {
                if (code != 407) {
                    switch (code) {
                        case 300:
                        case 301:
                        case 302:
                        case 303:
                            break;
                        default:
                            return null;
                    }
                } else if (proxy.type() != Proxy.Type.HTTP) {
                    throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                }
            }
            return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, proxy);
        } else if (!this.userRequest.method().equals("GET") && !this.userRequest.method().equals("HEAD")) {
            return null;
        }
        String header = this.userResponse.header("Location");
        if (header == null) {
            return null;
        }
        URL url = new URL(this.userRequest.url(), header);
        if (!url.getProtocol().equals("https") && !url.getProtocol().equals("http")) {
            return null;
        }
        if (!url.getProtocol().equals(this.userRequest.url().getProtocol()) && !this.client.getFollowSslRedirects()) {
            return null;
        }
        Request.Builder newBuilder = this.userRequest.newBuilder();
        if (HttpMethod.hasRequestBody(this.userRequest.method())) {
            newBuilder.method("GET", null);
            newBuilder.removeHeader("Transfer-Encoding");
            newBuilder.removeHeader("Content-Length");
            newBuilder.removeHeader("Content-Type");
        }
        if (!sameConnection(url)) {
            newBuilder.removeHeader("Authorization");
        }
        newBuilder.url(url);
        return newBuilder.build();
    }

    public boolean sameConnection(URL url) {
        URL url2 = this.userRequest.url();
        return url2.getHost().equals(url.getHost()) && Util.getEffectivePort(url2) == Util.getEffectivePort(url) && url2.getProtocol().equals(url.getProtocol());
    }
}
