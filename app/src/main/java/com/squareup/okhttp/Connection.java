package com.squareup.okhttp;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import java.io.IOException;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import javax.net.ssl.SSLSocket;

/* loaded from: classes3.dex */
public final class Connection {
    private Handshake handshake;
    private HttpConnection httpConnection;
    private long idleStartTimeNs;
    private Object owner;
    private final ConnectionPool pool;
    private int recycleCount;
    private final Route route;
    private Socket socket;
    private SpdyConnection spdyConnection;
    private boolean connected = false;
    private Protocol protocol = Protocol.HTTP_1_1;

    public Connection(ConnectionPool connectionPool, Route route) {
        this.pool = connectionPool;
        this.route = route;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object getOwner() {
        Object obj;
        synchronized (this.pool) {
            obj = this.owner;
        }
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOwner(Object obj) {
        if (isSpdy()) {
            return;
        }
        synchronized (this.pool) {
            if (this.owner != null) {
                throw new IllegalStateException("Connection already has an owner!");
            }
            this.owner = obj;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean clearOwner() {
        synchronized (this.pool) {
            if (this.owner == null) {
                return false;
            }
            this.owner = null;
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void closeIfOwnedBy(Object obj) throws IOException {
        if (isSpdy()) {
            throw new IllegalStateException();
        }
        synchronized (this.pool) {
            if (this.owner != obj) {
                return;
            }
            this.owner = null;
            this.socket.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void connect(int i, int i2, int i3, Request request) throws IOException {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        if (this.route.proxy.type() != Proxy.Type.HTTP) {
            this.socket = new Socket(this.route.proxy);
        } else {
            this.socket = this.route.address.socketFactory.createSocket();
        }
        this.socket.setSoTimeout(i2);
        Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, i);
        if (this.route.address.sslSocketFactory != null) {
            upgradeToTls(request, i2, i3);
        } else {
            this.httpConnection = new HttpConnection(this.pool, this, this.socket);
        }
        this.connected = true;
    }

    private void upgradeToTls(Request request, int i, int i2) throws IOException {
        String selectedProtocol;
        Platform platform = Platform.get();
        if (request != null) {
            makeTunnel(request, i, i2);
        }
        Address address = this.route.address;
        this.socket = address.sslSocketFactory.createSocket(this.socket, address.uriHost, address.uriPort, true);
        SSLSocket sSLSocket = (SSLSocket) this.socket;
        Route route = this.route;
        platform.configureTls(sSLSocket, route.address.uriHost, route.tlsVersion);
        boolean supportsNpn = this.route.supportsNpn();
        if (supportsNpn) {
            platform.setProtocols(sSLSocket, this.route.address.protocols);
        }
        sSLSocket.startHandshake();
        Address address2 = this.route.address;
        if (!address2.hostnameVerifier.verify(address2.uriHost, sSLSocket.getSession())) {
            throw new IOException("Hostname '" + this.route.address.uriHost + "' was not verified");
        }
        this.handshake = Handshake.get(sSLSocket.getSession());
        if (supportsNpn && (selectedProtocol = platform.getSelectedProtocol(sSLSocket)) != null) {
            this.protocol = Protocol.get(selectedProtocol);
        }
        Protocol protocol = this.protocol;
        if (protocol == Protocol.SPDY_3 || protocol == Protocol.HTTP_2) {
            sSLSocket.setSoTimeout(0);
            SpdyConnection.Builder builder = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket);
            builder.protocol(this.protocol);
            this.spdyConnection = builder.build();
            this.spdyConnection.sendConnectionPreface();
            return;
        }
        this.httpConnection = new HttpConnection(this.pool, this, this.socket);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isConnected() {
        return this.connected;
    }

    public Route getRoute() {
        return this.route;
    }

    public Socket getSocket() {
        return this.socket;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAlive() {
        return !this.socket.isClosed() && !this.socket.isInputShutdown() && !this.socket.isOutputShutdown();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isReadable() {
        HttpConnection httpConnection = this.httpConnection;
        if (httpConnection != null) {
            return httpConnection.isReadable();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void resetIdleStartTime() {
        if (this.spdyConnection != null) {
            throw new IllegalStateException("spdyConnection != null");
        }
        this.idleStartTimeNs = System.nanoTime();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isIdle() {
        SpdyConnection spdyConnection = this.spdyConnection;
        return spdyConnection == null || spdyConnection.isIdle();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isExpired(long j) {
        return getIdleStartTimeNs() < System.nanoTime() - j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getIdleStartTimeNs() {
        SpdyConnection spdyConnection = this.spdyConnection;
        return spdyConnection == null ? this.idleStartTimeNs : spdyConnection.getIdleStartTimeNs();
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Transport newTransport(HttpEngine httpEngine) throws IOException {
        SpdyConnection spdyConnection = this.spdyConnection;
        return spdyConnection != null ? new SpdyTransport(httpEngine, spdyConnection) : new HttpTransport(httpEngine, this.httpConnection);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isSpdy() {
        return this.spdyConnection != null;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setProtocol(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.protocol = protocol;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setTimeouts(int i, int i2) throws IOException {
        if (!this.connected) {
            throw new IllegalStateException("setTimeouts - not connected");
        }
        if (this.httpConnection == null) {
            return;
        }
        this.socket.setSoTimeout(i);
        this.httpConnection.setTimeouts(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void incrementRecycleCount() {
        this.recycleCount++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int recycleCount() {
        return this.recycleCount;
    }

    private void makeTunnel(Request request, int i, int i2) throws IOException {
        HttpConnection httpConnection = new HttpConnection(this.pool, this, this.socket);
        httpConnection.setTimeouts(i, i2);
        URL url = request.url();
        String str = "CONNECT " + url.getHost() + ":" + url.getPort() + " HTTP/1.1";
        do {
            httpConnection.writeRequest(request.headers(), str);
            httpConnection.flush();
            Response.Builder readResponse = httpConnection.readResponse();
            readResponse.request(request);
            Response build = readResponse.build();
            httpConnection.emptyResponseBody();
            int code = build.code();
            if (code == 200) {
                if (httpConnection.bufferSize() > 0) {
                    throw new IOException("TLS tunnel buffered too many bytes!");
                }
                return;
            } else if (code == 407) {
                Route route = this.route;
                request = OkHeaders.processAuthHeader(route.address.authenticator, build, route.proxy);
            } else {
                throw new IOException("Unexpected response code for CONNECT: " + build.code());
            }
        } while (request != null);
        throw new IOException("Failed to authenticate with proxy");
    }
}
