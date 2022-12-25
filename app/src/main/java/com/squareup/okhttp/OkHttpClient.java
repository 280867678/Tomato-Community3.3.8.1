package com.squareup.okhttp;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes3.dex */
public final class OkHttpClient implements Cloneable {
    private static SSLSocketFactory defaultSslSocketFactory;
    private Authenticator authenticator;
    private int connectTimeout;
    private ConnectionPool connectionPool;
    private CookieHandler cookieHandler;
    private HostnameVerifier hostnameVerifier;
    private InternalCache internalCache;
    private List<Protocol> protocols;
    private Proxy proxy;
    private ProxySelector proxySelector;
    private int readTimeout;
    private SocketFactory socketFactory;
    private SSLSocketFactory sslSocketFactory;
    private int writeTimeout;
    private boolean followSslRedirects = true;
    private final RouteDatabase routeDatabase = new RouteDatabase();

    static {
        Internal.instance = new Internal() { // from class: com.squareup.okhttp.OkHttpClient.1
            @Override // com.squareup.okhttp.internal.Internal
            public Transport newTransport(Connection connection, HttpEngine httpEngine) throws IOException {
                return connection.newTransport(httpEngine);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public boolean clearOwner(Connection connection) {
                return connection.clearOwner();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void closeIfOwnedBy(Connection connection, Object obj) throws IOException {
                connection.closeIfOwnedBy(obj);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public int recycleCount(Connection connection) {
                return connection.recycleCount();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public Object getOwner(Connection connection) {
                return connection.getOwner();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void setProtocol(Connection connection, Protocol protocol) {
                connection.setProtocol(protocol);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void setOwner(Connection connection, HttpEngine httpEngine) {
                connection.setOwner(httpEngine);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void connect(Connection connection, int i, int i2, int i3, Request request) throws IOException {
                connection.connect(i, i2, i3, request);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public boolean isConnected(Connection connection) {
                return connection.isConnected();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public boolean isSpdy(Connection connection) {
                return connection.isSpdy();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void setTimeouts(Connection connection, int i, int i2) throws IOException {
                connection.setTimeouts(i, i2);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public boolean isReadable(Connection connection) {
                return connection.isReadable();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void addLine(Headers.Builder builder, String str) {
                builder.addLine(str);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public InternalCache internalCache(OkHttpClient okHttpClient) {
                return okHttpClient.internalCache();
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void recycle(ConnectionPool connectionPool, Connection connection) {
                connectionPool.recycle(connection);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public void share(ConnectionPool connectionPool, Connection connection) {
                connectionPool.share(connection);
            }

            @Override // com.squareup.okhttp.internal.Internal
            public RouteDatabase routeDatabase(OkHttpClient okHttpClient) {
                return okHttpClient.routeDatabase;
            }
        };
    }

    public OkHttpClient() {
        new Dispatcher();
    }

    public void setConnectTimeout(long j, TimeUnit timeUnit) {
        if (j >= 0) {
            if (timeUnit == null) {
                throw new IllegalArgumentException("unit == null");
            }
            long millis = timeUnit.toMillis(j);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.connectTimeout = (int) millis;
            return;
        }
        throw new IllegalArgumentException("timeout < 0");
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setReadTimeout(long j, TimeUnit timeUnit) {
        if (j >= 0) {
            if (timeUnit == null) {
                throw new IllegalArgumentException("unit == null");
            }
            long millis = timeUnit.toMillis(j);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.readTimeout = (int) millis;
            return;
        }
        throw new IllegalArgumentException("timeout < 0");
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public OkHttpClient setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public CookieHandler getCookieHandler() {
        return this.cookieHandler;
    }

    InternalCache internalCache() {
        return this.internalCache;
    }

    public OkHttpClient setCache(Cache cache) {
        this.internalCache = cache != null ? cache.internalCache : null;
        return this;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public OkHttpClient setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
        return this;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public boolean getFollowSslRedirects() {
        return this.followSslRedirects;
    }

    public OkHttpClient setProtocols(List<Protocol> list) {
        List immutableList = Util.immutableList(list);
        if (!immutableList.contains(Protocol.HTTP_1_1)) {
            throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + immutableList);
        } else if (immutableList.contains(null)) {
            throw new IllegalArgumentException("protocols must not contain null");
        } else {
            this.protocols = Util.immutableList(immutableList);
            return this;
        }
    }

    public List<Protocol> getProtocols() {
        return this.protocols;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OkHttpClient copyWithDefaults() {
        OkHttpClient m6513clone = m6513clone();
        if (m6513clone.proxySelector == null) {
            m6513clone.proxySelector = ProxySelector.getDefault();
        }
        if (m6513clone.cookieHandler == null) {
            m6513clone.cookieHandler = CookieHandler.getDefault();
        }
        if (m6513clone.socketFactory == null) {
            m6513clone.socketFactory = SocketFactory.getDefault();
        }
        if (m6513clone.sslSocketFactory == null) {
            m6513clone.sslSocketFactory = getDefaultSSLSocketFactory();
        }
        if (m6513clone.hostnameVerifier == null) {
            m6513clone.hostnameVerifier = OkHostnameVerifier.INSTANCE;
        }
        if (m6513clone.authenticator == null) {
            m6513clone.authenticator = AuthenticatorAdapter.INSTANCE;
        }
        if (m6513clone.connectionPool == null) {
            m6513clone.connectionPool = ConnectionPool.getDefault();
        }
        if (m6513clone.protocols == null) {
            m6513clone.protocols = Util.immutableList(Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1);
        }
        return m6513clone;
    }

    private synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
        if (defaultSslSocketFactory == null) {
            try {
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                sSLContext.init(null, null, null);
                defaultSslSocketFactory = sSLContext.getSocketFactory();
            } catch (GeneralSecurityException unused) {
                throw new AssertionError();
            }
        }
        return defaultSslSocketFactory;
    }

    /* renamed from: clone */
    public OkHttpClient m6513clone() {
        try {
            return (OkHttpClient) super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }
}
