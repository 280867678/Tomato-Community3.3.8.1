package com.squareup.okhttp;

import java.io.IOException;
import java.net.Proxy;

/* loaded from: classes3.dex */
public interface Authenticator {
    Request authenticate(Proxy proxy, Response response) throws IOException;

    Request authenticateProxy(Proxy proxy, Response response) throws IOException;
}
