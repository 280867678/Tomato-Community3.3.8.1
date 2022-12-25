package com.example.websocket.com.neovisionaries.p057ws.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.ProxySettings */
/* loaded from: classes2.dex */
public class ProxySettings {
    private String mHost;
    private String mId;
    private String mPassword;
    private int mPort;
    private boolean mSecure;
    private String[] mServerNames;
    private final WebSocketFactory mWebSocketFactory;
    private final Map<String, List<String>> mHeaders = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    private final SocketFactorySettings mSocketFactorySettings = new SocketFactorySettings();

    /* JADX INFO: Access modifiers changed from: package-private */
    public ProxySettings(WebSocketFactory webSocketFactory) {
        this.mWebSocketFactory = webSocketFactory;
        reset();
    }

    public WebSocketFactory getWebSocketFactory() {
        return this.mWebSocketFactory;
    }

    public ProxySettings reset() {
        this.mSecure = false;
        this.mHost = null;
        this.mPort = -1;
        this.mId = null;
        this.mPassword = null;
        this.mHeaders.clear();
        this.mServerNames = null;
        return this;
    }

    public boolean isSecure() {
        return this.mSecure;
    }

    public ProxySettings setSecure(boolean z) {
        this.mSecure = z;
        return this;
    }

    public String getHost() {
        return this.mHost;
    }

    public ProxySettings setHost(String str) {
        this.mHost = str;
        return this;
    }

    public int getPort() {
        return this.mPort;
    }

    public ProxySettings setPort(int i) {
        this.mPort = i;
        return this;
    }

    public String getId() {
        return this.mId;
    }

    public ProxySettings setId(String str) {
        this.mId = str;
        return this;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public ProxySettings setPassword(String str) {
        this.mPassword = str;
        return this;
    }

    public ProxySettings setCredentials(String str, String str2) {
        return setId(str).setPassword(str2);
    }

    public ProxySettings setServer(String str) {
        return str == null ? this : setServer(URI.create(str));
    }

    public ProxySettings setServer(URL url) {
        if (url == null) {
            return this;
        }
        try {
            return setServer(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ProxySettings setServer(URI uri) {
        return uri == null ? this : setServer(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort());
    }

    private ProxySettings setServer(String str, String str2, String str3, int i) {
        setByScheme(str);
        setByUserInfo(str2);
        this.mHost = str3;
        this.mPort = i;
        return this;
    }

    private void setByScheme(String str) {
        if ("http".equalsIgnoreCase(str)) {
            this.mSecure = false;
        } else if (!"https".equalsIgnoreCase(str)) {
        } else {
            this.mSecure = true;
        }
    }

    private void setByUserInfo(String str) {
        String str2;
        String str3;
        if (str == null) {
            return;
        }
        String[] split = str.split(":", 2);
        int length = split.length;
        if (length == 1) {
            str2 = split[0];
            str3 = null;
        } else if (length != 2) {
            return;
        } else {
            str2 = split[0];
            str3 = split[1];
        }
        if (str2.length() == 0) {
            return;
        }
        this.mId = str2;
        this.mPassword = str3;
    }

    public Map<String, List<String>> getHeaders() {
        return this.mHeaders;
    }

    public ProxySettings addHeader(String str, String str2) {
        if (str != null && str.length() != 0) {
            List<String> list = this.mHeaders.get(str);
            if (list == null) {
                list = new ArrayList<>();
                this.mHeaders.put(str, list);
            }
            list.add(str2);
        }
        return this;
    }

    public SocketFactory getSocketFactory() {
        return this.mSocketFactorySettings.getSocketFactory();
    }

    public ProxySettings setSocketFactory(SocketFactory socketFactory) {
        this.mSocketFactorySettings.setSocketFactory(socketFactory);
        return this;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.mSocketFactorySettings.getSSLSocketFactory();
    }

    public ProxySettings setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.mSocketFactorySettings.setSSLSocketFactory(sSLSocketFactory);
        return this;
    }

    public SSLContext getSSLContext() {
        return this.mSocketFactorySettings.getSSLContext();
    }

    public ProxySettings setSSLContext(SSLContext sSLContext) {
        this.mSocketFactorySettings.setSSLContext(sSLContext);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SocketFactory selectSocketFactory() {
        return this.mSocketFactorySettings.selectSocketFactory(this.mSecure);
    }

    public String[] getServerNames() {
        return this.mServerNames;
    }

    public ProxySettings setServerNames(String[] strArr) {
        this.mServerNames = strArr;
        return this;
    }

    public ProxySettings setServerName(String str) {
        return setServerNames(new String[]{str});
    }
}
