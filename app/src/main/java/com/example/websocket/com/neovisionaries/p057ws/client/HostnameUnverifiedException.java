package com.example.websocket.com.neovisionaries.p057ws.client;

import javax.net.ssl.SSLSocket;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.HostnameUnverifiedException */
/* loaded from: classes2.dex */
public class HostnameUnverifiedException extends WebSocketException {
    private static final long serialVersionUID = 1;
    private final String mHostname;
    private final SSLSocket mSSLSocket;

    public HostnameUnverifiedException(SSLSocket sSLSocket, String str) {
        super(WebSocketError.HOSTNAME_UNVERIFIED, String.format("The certificate of the peer%s does not match the expected hostname (%s)", stringifyPrincipal(sSLSocket), str));
        this.mSSLSocket = sSLSocket;
        this.mHostname = str;
    }

    private static String stringifyPrincipal(SSLSocket sSLSocket) {
        try {
            return String.format(" (%s)", sSLSocket.getSession().getPeerPrincipal().toString());
        } catch (Exception unused) {
            return "";
        }
    }

    public SSLSocket getSSLSocket() {
        return this.mSSLSocket;
    }

    public String getHostname() {
        return this.mHostname;
    }
}
