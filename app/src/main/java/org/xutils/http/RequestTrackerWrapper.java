package org.xutils.http;

import org.xutils.common.util.LogUtil;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
final class RequestTrackerWrapper implements RequestTracker {
    private final RequestTracker base;

    public RequestTrackerWrapper(RequestTracker requestTracker) {
        this.base = requestTracker;
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onWaiting(RequestParams requestParams) {
        try {
            this.base.onWaiting(requestParams);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onStart(RequestParams requestParams) {
        try {
            this.base.onStart(requestParams);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onRequestCreated(UriRequest uriRequest) {
        try {
            this.base.onRequestCreated(uriRequest);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onCache(UriRequest uriRequest, Object obj) {
        try {
            this.base.onCache(uriRequest, obj);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onSuccess(UriRequest uriRequest, Object obj) {
        try {
            this.base.onSuccess(uriRequest, obj);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onCancelled(UriRequest uriRequest) {
        try {
            this.base.onCancelled(uriRequest);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onError(UriRequest uriRequest, Throwable th, boolean z) {
        try {
            this.base.onError(uriRequest, th, z);
        } catch (Throwable th2) {
            LogUtil.m43e(th2.getMessage(), th2);
        }
    }

    @Override // org.xutils.http.app.RequestTracker
    public void onFinished(UriRequest uriRequest) {
        try {
            this.base.onFinished(uriRequest);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }
}
