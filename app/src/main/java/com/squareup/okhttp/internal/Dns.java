package com.squareup.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes3.dex */
public interface Dns {
    public static final Dns DEFAULT = new Dns() { // from class: com.squareup.okhttp.internal.Dns.1
        @Override // com.squareup.okhttp.internal.Dns
        public InetAddress[] getAllByName(String str) throws UnknownHostException {
            if (str == null) {
                throw new UnknownHostException("host == null");
            }
            return InetAddress.getAllByName(str);
        }
    };

    InetAddress[] getAllByName(String str) throws UnknownHostException;
}
