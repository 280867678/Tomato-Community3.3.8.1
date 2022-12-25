package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;

/* loaded from: classes3.dex */
public final class Route {
    final Address address;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;
    final String tlsVersion;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, String str) {
        if (address != null) {
            if (proxy == null) {
                throw new NullPointerException("proxy == null");
            }
            if (inetSocketAddress == null) {
                throw new NullPointerException("inetSocketAddress == null");
            }
            if (str == null) {
                throw new NullPointerException("tlsVersion == null");
            }
            this.address = address;
            this.proxy = proxy;
            this.inetSocketAddress = inetSocketAddress;
            this.tlsVersion = str;
            return;
        }
        throw new NullPointerException("address == null");
    }

    public Address getAddress() {
        return this.address;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean supportsNpn() {
        return !this.tlsVersion.equals("SSLv3");
    }

    public boolean requiresTunnel() {
        return this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Route) {
            Route route = (Route) obj;
            return this.address.equals(route.address) && this.proxy.equals(route.proxy) && this.inetSocketAddress.equals(route.inetSocketAddress) && this.tlsVersion.equals(route.tlsVersion);
        }
        return false;
    }

    public int hashCode() {
        return ((((((527 + this.address.hashCode()) * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode()) * 31) + this.tlsVersion.hashCode();
    }
}
