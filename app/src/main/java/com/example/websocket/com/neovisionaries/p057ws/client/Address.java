package com.example.websocket.com.neovisionaries.p057ws.client;

import java.net.InetSocketAddress;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.Address */
/* loaded from: classes2.dex */
class Address {
    private final String mHost;
    private final int mPort;
    private transient String mString;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Address(String str, int i) {
        this.mHost = str;
        this.mPort = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public InetSocketAddress toInetSocketAddress() {
        return new InetSocketAddress(this.mHost, this.mPort);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getHostname() {
        return this.mHost;
    }

    public String toString() {
        if (this.mString == null) {
            this.mString = String.format("%s:%d", this.mHost, Integer.valueOf(this.mPort));
        }
        return this.mString;
    }
}
