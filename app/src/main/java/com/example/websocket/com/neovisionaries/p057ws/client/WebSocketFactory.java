package com.example.websocket.com.neovisionaries.p057ws.client;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocketFactory */
/* loaded from: classes2.dex */
public class WebSocketFactory {
    private int mConnectionTimeout;
    private String[] mServerNames;
    private boolean mVerifyHostname = true;
    private final SocketFactorySettings mSocketFactorySettings = new SocketFactorySettings();
    private final ProxySettings mProxySettings = new ProxySettings(this);

    private static int determinePort(int i, boolean z) {
        return i >= 0 ? i : z ? 443 : 80;
    }

    public SocketFactory getSocketFactory() {
        return this.mSocketFactorySettings.getSocketFactory();
    }

    public WebSocketFactory setSocketFactory(SocketFactory socketFactory) {
        this.mSocketFactorySettings.setSocketFactory(socketFactory);
        return this;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.mSocketFactorySettings.getSSLSocketFactory();
    }

    public WebSocketFactory setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.mSocketFactorySettings.setSSLSocketFactory(sSLSocketFactory);
        return this;
    }

    public SSLContext getSSLContext() {
        return this.mSocketFactorySettings.getSSLContext();
    }

    public WebSocketFactory setSSLContext(SSLContext sSLContext) {
        this.mSocketFactorySettings.setSSLContext(sSLContext);
        return this;
    }

    public ProxySettings getProxySettings() {
        return this.mProxySettings;
    }

    public int getConnectionTimeout() {
        return this.mConnectionTimeout;
    }

    public WebSocketFactory setConnectionTimeout(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("timeout value cannot be negative.");
        }
        this.mConnectionTimeout = i;
        return this;
    }

    public boolean getVerifyHostname() {
        return this.mVerifyHostname;
    }

    public WebSocketFactory setVerifyHostname(boolean z) {
        this.mVerifyHostname = z;
        return this;
    }

    public String[] getServerNames() {
        return this.mServerNames;
    }

    public WebSocketFactory setServerNames(String[] strArr) {
        this.mServerNames = strArr;
        return this;
    }

    public WebSocketFactory setServerName(String str) {
        return setServerNames(new String[]{str});
    }

    public WebSocket createSocket(String str) throws IOException {
        return createSocket(str, getConnectionTimeout());
    }

    public WebSocket createSocket(String str, int i) throws IOException {
        if (str != null) {
            if (i < 0) {
                throw new IllegalArgumentException("The given timeout value is negative.");
            }
            return createSocket(URI.create(str), i);
        }
        throw new IllegalArgumentException("The given URI is null.");
    }

    public WebSocket createSocket(URL url) throws IOException {
        return createSocket(url, getConnectionTimeout());
    }

    public WebSocket createSocket(URL url, int i) throws IOException {
        if (url != null) {
            if (i < 0) {
                throw new IllegalArgumentException("The given timeout value is negative.");
            }
            try {
                return createSocket(url.toURI(), i);
            } catch (URISyntaxException unused) {
                throw new IllegalArgumentException("Failed to convert the given URL into a URI.");
            }
        }
        throw new IllegalArgumentException("The given URL is null.");
    }

    public WebSocket createSocket(URI uri) throws IOException {
        return createSocket(uri, getConnectionTimeout());
    }

    public WebSocket createSocket(URI uri, int i) throws IOException {
        if (uri != null) {
            if (i < 0) {
                throw new IllegalArgumentException("The given timeout value is negative.");
            }
            return createSocket(uri.getScheme(), uri.getUserInfo(), Misc.extractHost(uri), uri.getPort(), uri.getRawPath(), uri.getRawQuery(), i);
        }
        throw new IllegalArgumentException("The given URI is null.");
    }

    private WebSocket createSocket(String str, String str2, String str3, int i, String str4, String str5, int i2) throws IOException {
        boolean isSecureConnectionRequired = isSecureConnectionRequired(str);
        if (str3 == null || str3.length() == 0) {
            throw new IllegalArgumentException("The host part is empty.");
        }
        return createWebSocket(isSecureConnectionRequired, str2, str3, i, determinePath(str4), str5, createRawSocket(str3, i, isSecureConnectionRequired, i2));
    }

    private static boolean isSecureConnectionRequired(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("The scheme part is empty.");
        }
        if ("wss".equalsIgnoreCase(str) || "https".equalsIgnoreCase(str)) {
            return true;
        }
        if ("ws".equalsIgnoreCase(str) || "http".equalsIgnoreCase(str)) {
            return false;
        }
        throw new IllegalArgumentException("Bad scheme: " + str);
    }

    private static String determinePath(String str) {
        if (str == null || str.length() == 0) {
            return "/";
        }
        if (str.startsWith("/")) {
            return str;
        }
        return "/" + str;
    }

    private SocketConnector createRawSocket(String str, int i, boolean z, int i2) throws IOException {
        int determinePort = determinePort(i, z);
        if (this.mProxySettings.getHost() != null) {
            return createProxiedRawSocket(str, determinePort, z, i2);
        }
        return createDirectRawSocket(str, determinePort, z, i2);
    }

    private SocketConnector createProxiedRawSocket(String str, int i, boolean z, int i2) throws IOException {
        int determinePort = determinePort(this.mProxySettings.getPort(), this.mProxySettings.isSecure());
        Socket createSocket = this.mProxySettings.selectSocketFactory().createSocket();
        SNIHelper.setServerNames(createSocket, this.mProxySettings.getServerNames());
        return new SocketConnector(createSocket, new Address(this.mProxySettings.getHost(), determinePort), i2, new ProxyHandshaker(createSocket, str, i, this.mProxySettings), z ? (SSLSocketFactory) this.mSocketFactorySettings.selectSocketFactory(z) : null, str, i).setVerifyHostname(this.mVerifyHostname);
    }

    private SocketConnector createDirectRawSocket(String str, int i, boolean z, int i2) throws IOException {
        Socket createSocket = this.mSocketFactorySettings.selectSocketFactory(z).createSocket();
        SNIHelper.setServerNames(createSocket, this.mServerNames);
        return new SocketConnector(createSocket, new Address(str, i), i2).setVerifyHostname(this.mVerifyHostname);
    }

    private WebSocket createWebSocket(boolean z, String str, String str2, int i, String str3, String str4, SocketConnector socketConnector) {
        if (i >= 0) {
            str2 = str2 + ":" + i;
        }
        String str5 = str2;
        if (str4 != null) {
            str3 = str3 + "?" + str4;
        }
        return new WebSocket(this, z, str, str5, str3, socketConnector);
    }
}
