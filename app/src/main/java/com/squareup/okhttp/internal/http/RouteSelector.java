package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Dns;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;

/* loaded from: classes3.dex */
public final class RouteSelector {
    private final Address address;
    private final Dns dns;
    private boolean hasNextProxy;
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private int nextSocketAddressIndex;
    private String nextTlsVersion;
    private final ConnectionPool pool;
    private final List<Route> postponedRoutes = new LinkedList();
    private final ProxySelector proxySelector;
    private Iterator<Proxy> proxySelectorProxies;
    private final RouteDatabase routeDatabase;
    private InetAddress[] socketAddresses;
    private int socketPort;
    private final URI uri;
    private Proxy userSpecifiedProxy;

    public RouteSelector(Address address, URI uri, ProxySelector proxySelector, ConnectionPool connectionPool, Dns dns, RouteDatabase routeDatabase) {
        this.address = address;
        this.uri = uri;
        this.proxySelector = proxySelector;
        this.pool = connectionPool;
        this.dns = dns;
        this.routeDatabase = routeDatabase;
        resetNextProxy(uri, address.getProxy());
    }

    public boolean hasNext() {
        return hasNextTlsVersion() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
    }

    public Connection next(String str) throws IOException {
        Connection connection;
        while (true) {
            connection = this.pool.get(this.address);
            if (connection != null) {
                if (str.equals("GET") || Internal.instance.isReadable(connection)) {
                    break;
                }
                connection.getSocket().close();
            } else {
                if (!hasNextTlsVersion()) {
                    if (!hasNextInetSocketAddress()) {
                        if (!hasNextProxy()) {
                            if (!hasNextPostponed()) {
                                throw new NoSuchElementException();
                            }
                            return new Connection(this.pool, nextPostponed());
                        }
                        this.lastProxy = nextProxy();
                        resetNextInetSocketAddress(this.lastProxy);
                    }
                    this.lastInetSocketAddress = nextInetSocketAddress();
                    resetNextTlsVersion();
                }
                Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, nextTlsVersion());
                if (this.routeDatabase.shouldPostpone(route)) {
                    this.postponedRoutes.add(route);
                    return next(str);
                }
                return new Connection(this.pool, route);
            }
        }
        return connection;
    }

    public void connectFailed(Connection connection, IOException iOException) {
        ProxySelector proxySelector;
        if (Internal.instance.recycleCount(connection) > 0) {
            return;
        }
        Route route = connection.getRoute();
        if (route.getProxy().type() != Proxy.Type.DIRECT && (proxySelector = this.proxySelector) != null) {
            proxySelector.connectFailed(this.uri, route.getProxy().address(), iOException);
        }
        this.routeDatabase.failed(route);
        if ((iOException instanceof SSLHandshakeException) || (iOException instanceof SSLProtocolException)) {
            return;
        }
        while (hasNextTlsVersion()) {
            this.routeDatabase.failed(new Route(this.address, this.lastProxy, this.lastInetSocketAddress, nextTlsVersion()));
        }
    }

    private void resetNextProxy(URI uri, Proxy proxy) {
        this.hasNextProxy = true;
        if (proxy != null) {
            this.userSpecifiedProxy = proxy;
            return;
        }
        List<Proxy> select = this.proxySelector.select(uri);
        if (select == null) {
            return;
        }
        this.proxySelectorProxies = select.iterator();
    }

    private boolean hasNextProxy() {
        return this.hasNextProxy;
    }

    private Proxy nextProxy() {
        Proxy proxy = this.userSpecifiedProxy;
        if (proxy != null) {
            this.hasNextProxy = false;
            return proxy;
        }
        if (this.proxySelectorProxies != null) {
            while (this.proxySelectorProxies.hasNext()) {
                Proxy next = this.proxySelectorProxies.next();
                if (next.type() != Proxy.Type.DIRECT) {
                    return next;
                }
            }
        }
        this.hasNextProxy = false;
        return Proxy.NO_PROXY;
    }

    private void resetNextInetSocketAddress(Proxy proxy) throws UnknownHostException {
        String str;
        this.socketAddresses = null;
        if (proxy.type() == Proxy.Type.DIRECT) {
            str = this.uri.getHost();
            this.socketPort = Util.getEffectivePort(this.uri);
        } else {
            SocketAddress address = proxy.address();
            if (!(address instanceof InetSocketAddress)) {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + address.getClass());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
            String hostName = inetSocketAddress.getHostName();
            this.socketPort = inetSocketAddress.getPort();
            str = hostName;
        }
        this.socketAddresses = this.dns.getAllByName(str);
        this.nextSocketAddressIndex = 0;
    }

    private boolean hasNextInetSocketAddress() {
        return this.socketAddresses != null;
    }

    private InetSocketAddress nextInetSocketAddress() throws UnknownHostException {
        InetAddress[] inetAddressArr = this.socketAddresses;
        int i = this.nextSocketAddressIndex;
        this.nextSocketAddressIndex = i + 1;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddressArr[i], this.socketPort);
        if (this.nextSocketAddressIndex == this.socketAddresses.length) {
            this.socketAddresses = null;
            this.nextSocketAddressIndex = 0;
        }
        return inetSocketAddress;
    }

    private void resetNextTlsVersion() {
        this.nextTlsVersion = this.address.getSslSocketFactory() != null ? "TLSv1" : "SSLv3";
    }

    private boolean hasNextTlsVersion() {
        return this.nextTlsVersion != null;
    }

    private String nextTlsVersion() {
        String str = this.nextTlsVersion;
        if (str == null) {
            throw new IllegalStateException("No next TLS version");
        }
        if (str.equals("TLSv1")) {
            this.nextTlsVersion = "SSLv3";
            return "TLSv1";
        } else if (this.nextTlsVersion.equals("SSLv3")) {
            this.nextTlsVersion = null;
            return "SSLv3";
        } else {
            throw new AssertionError();
        }
    }

    private boolean hasNextPostponed() {
        return !this.postponedRoutes.isEmpty();
    }

    private Route nextPostponed() {
        return this.postponedRoutes.remove(0);
    }
}
