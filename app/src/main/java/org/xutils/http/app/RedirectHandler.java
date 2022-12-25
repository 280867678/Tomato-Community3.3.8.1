package org.xutils.http.app;

import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
public interface RedirectHandler {
    RequestParams getRedirectParams(UriRequest uriRequest) throws Throwable;
}
