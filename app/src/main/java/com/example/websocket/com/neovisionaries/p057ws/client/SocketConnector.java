package com.example.websocket.com.neovisionaries.p057ws.client;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.SocketConnector */
/* loaded from: classes2.dex */
public class SocketConnector {
    private final Address mAddress;
    private final int mConnectionTimeout;
    private final String mHost;
    private final int mPort;
    private final ProxyHandshaker mProxyHandshaker;
    private final SSLSocketFactory mSSLSocketFactory;
    private Socket mSocket;
    private boolean mVerifyHostname;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SocketConnector(Socket socket, Address address, int i) {
        this(socket, address, i, null, null, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SocketConnector(Socket socket, Address address, int i, ProxyHandshaker proxyHandshaker, SSLSocketFactory sSLSocketFactory, String str, int i2) {
        this.mSocket = socket;
        this.mAddress = address;
        this.mConnectionTimeout = i;
        this.mProxyHandshaker = proxyHandshaker;
        this.mSSLSocketFactory = sSLSocketFactory;
        this.mHost = str;
        this.mPort = i2;
    }

    public Socket getSocket() {
        return this.mSocket;
    }

    public int getConnectionTimeout() {
        return this.mConnectionTimeout;
    }

    public void connect() throws WebSocketException {
        try {
            doConnect();
        } catch (WebSocketException e) {
            try {
                this.mSocket.close();
            } catch (IOException unused) {
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SocketConnector setVerifyHostname(boolean z) {
        this.mVerifyHostname = z;
        return this;
    }

    private void doConnect() throws WebSocketException {
        boolean z = this.mProxyHandshaker != null;
        try {
            this.mSocket.connect(this.mAddress.toInetSocketAddress(), this.mConnectionTimeout);
            if (this.mSocket instanceof SSLSocket) {
                verifyHostname((SSLSocket) this.mSocket, this.mAddress.getHostname());
            }
            if (!z) {
                return;
            }
            handshake();
        } catch (IOException e) {
            Object[] objArr = new Object[3];
            objArr[0] = z ? "the proxy " : "";
            objArr[1] = this.mAddress;
            objArr[2] = e.getMessage();
            throw new WebSocketException(WebSocketError.SOCKET_CONNECT_ERROR, String.format("Failed to connect to %s'%s': %s", objArr), e);
        }
    }

    private void verifyHostname(SSLSocket sSLSocket, String str) throws HostnameUnverifiedException {
        if (this.mVerifyHostname && !OkHostnameVerifier.INSTANCE.verify(str, sSLSocket.getSession())) {
            throw new HostnameUnverifiedException(sSLSocket, str);
        }
    }

    private void handshake() throws WebSocketException {
        try {
            this.mProxyHandshaker.perform();
            SSLSocketFactory sSLSocketFactory = this.mSSLSocketFactory;
            if (sSLSocketFactory == null) {
                return;
            }
            try {
                this.mSocket = sSLSocketFactory.createSocket(this.mSocket, this.mHost, this.mPort, true);
                try {
                    ((SSLSocket) this.mSocket).startHandshake();
                    if (!(this.mSocket instanceof SSLSocket)) {
                        return;
                    }
                    verifyHostname((SSLSocket) this.mSocket, this.mProxyHandshaker.getProxiedHostname());
                } catch (IOException e) {
                    throw new WebSocketException(WebSocketError.SSL_HANDSHAKE_ERROR, String.format("SSL handshake with the WebSocket endpoint (%s) failed: %s", this.mAddress, e.getMessage()), e);
                }
            } catch (IOException e2) {
                throw new WebSocketException(WebSocketError.SOCKET_OVERLAY_ERROR, "Failed to overlay an existing socket: " + e2.getMessage(), e2);
            }
        } catch (IOException e3) {
            throw new WebSocketException(WebSocketError.PROXY_HANDSHAKE_ERROR, String.format("Handshake with the proxy server (%s) failed: %s", this.mAddress, e3.getMessage()), e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void closeSilently() {
        try {
            this.mSocket.close();
        } catch (Throwable unused) {
        }
    }
}
