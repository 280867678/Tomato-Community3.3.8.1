package org.xutils.http.app;

import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
public interface RequestInterceptListener {
    void afterRequest(UriRequest uriRequest) throws Throwable;

    void beforeRequest(UriRequest uriRequest) throws Throwable;
}
