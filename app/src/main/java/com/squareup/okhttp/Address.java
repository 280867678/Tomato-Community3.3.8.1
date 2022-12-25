package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes3.dex */
public final class Address {
    final Authenticator authenticator;
    final HostnameVerifier hostnameVerifier;
    final List<Protocol> protocols;
    final Proxy proxy;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final String uriHost;
    final int uriPort;

    public Address(String str, int i, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, Authenticator authenticator, Proxy proxy, List<Protocol> list) throws UnknownHostException {
        if (str != null) {
            if (i <= 0) {
                throw new IllegalArgumentException("uriPort <= 0: " + i);
            } else if (authenticator == null) {
                throw new IllegalArgumentException("authenticator == null");
            } else {
                if (list == null) {
                    throw new IllegalArgumentException("protocols == null");
                }
                this.proxy = proxy;
                this.uriHost = str;
                this.uriPort = i;
                this.socketFactory = socketFactory;
                this.sslSocketFactory = sSLSocketFactory;
                this.hostnameVerifier = hostnameVerifier;
                this.authenticator = authenticator;
                this.protocols = Util.immutableList(list);
                return;
            }
        }
        throw new NullPointerException("uriHost == null");
    }

    public String getUriHost() {
        return this.uriHost;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Address) {
            Address address = (Address) obj;
            return Util.equal(this.proxy, address.proxy) && this.uriHost.equals(address.uriHost) && this.uriPort == address.uriPort && Util.equal(this.sslSocketFactory, address.sslSocketFactory) && Util.equal(this.hostnameVerifier, address.hostnameVerifier) && Util.equal(this.authenticator, address.authenticator) && Util.equal(this.protocols, address.protocols);
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (((527 + this.uriHost.hashCode()) * 31) + this.uriPort) * 31;
        SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
        int i = 0;
        int hashCode2 = (hashCode + (sSLSocketFactory != null ? sSLSocketFactory.hashCode() : 0)) * 31;
        HostnameVerifier hostnameVerifier = this.hostnameVerifier;
        int hashCode3 = (((hashCode2 + (hostnameVerifier != null ? hostnameVerifier.hashCode() : 0)) * 31) + this.authenticator.hashCode()) * 31;
        Proxy proxy = this.proxy;
        if (proxy != null) {
            i = proxy.hashCode();
        }
        return ((hashCode3 + i) * 31) + this.protocols.hashCode();
    }
}
