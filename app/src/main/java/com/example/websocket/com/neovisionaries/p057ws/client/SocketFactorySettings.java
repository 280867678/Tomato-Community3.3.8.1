package com.example.websocket.com.neovisionaries.p057ws.client;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.SocketFactorySettings */
/* loaded from: classes2.dex */
class SocketFactorySettings {
    private SSLContext mSSLContext;
    private SSLSocketFactory mSSLSocketFactory;
    private SocketFactory mSocketFactory;

    public SocketFactory getSocketFactory() {
        return this.mSocketFactory;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.mSocketFactory = socketFactory;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.mSSLSocketFactory;
    }

    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.mSSLSocketFactory = sSLSocketFactory;
    }

    public SSLContext getSSLContext() {
        return this.mSSLContext;
    }

    public void setSSLContext(SSLContext sSLContext) {
        this.mSSLContext = sSLContext;
    }

    public SocketFactory selectSocketFactory(boolean z) {
        if (z) {
            SSLContext sSLContext = this.mSSLContext;
            if (sSLContext != null) {
                return sSLContext.getSocketFactory();
            }
            SSLSocketFactory sSLSocketFactory = this.mSSLSocketFactory;
            return sSLSocketFactory != null ? sSLSocketFactory : SSLSocketFactory.getDefault();
        }
        SocketFactory socketFactory = this.mSocketFactory;
        return socketFactory != null ? socketFactory : SocketFactory.getDefault();
    }
}
