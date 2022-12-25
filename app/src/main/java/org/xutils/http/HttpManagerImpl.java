package org.xutils.http;

import java.lang.reflect.Type;
import org.xutils.C5540x;
import org.xutils.HttpManager;
import org.xutils.common.Callback;

/* loaded from: classes4.dex */
public final class HttpManagerImpl implements HttpManager {
    private static volatile HttpManagerImpl instance;
    private static final Object lock = new Object();

    private HttpManagerImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new HttpManagerImpl();
                }
            }
        }
        C5540x.Ext.setHttpManager(instance);
    }

    @Override // org.xutils.HttpManager
    public <T> Callback.Cancelable get(RequestParams requestParams, Callback.CommonCallback<T> commonCallback) {
        return request(HttpMethod.GET, requestParams, commonCallback);
    }

    @Override // org.xutils.HttpManager
    public <T> Callback.Cancelable post(RequestParams requestParams, Callback.CommonCallback<T> commonCallback) {
        return request(HttpMethod.POST, requestParams, commonCallback);
    }

    @Override // org.xutils.HttpManager
    public <T> Callback.Cancelable request(HttpMethod httpMethod, RequestParams requestParams, Callback.CommonCallback<T> commonCallback) {
        requestParams.setMethod(httpMethod);
        return C5540x.task().start(new HttpTask(requestParams, commonCallback instanceof Callback.Cancelable ? (Callback.Cancelable) commonCallback : null, commonCallback));
    }

    @Override // org.xutils.HttpManager
    public <T> T getSync(RequestParams requestParams, Class<T> cls) throws Throwable {
        return (T) requestSync(HttpMethod.GET, requestParams, cls);
    }

    @Override // org.xutils.HttpManager
    public <T> T postSync(RequestParams requestParams, Class<T> cls) throws Throwable {
        return (T) requestSync(HttpMethod.POST, requestParams, cls);
    }

    @Override // org.xutils.HttpManager
    public <T> T requestSync(HttpMethod httpMethod, RequestParams requestParams, Class<T> cls) throws Throwable {
        return (T) requestSync(httpMethod, requestParams, new DefaultSyncCallback(this, cls));
    }

    @Override // org.xutils.HttpManager
    public <T> T requestSync(HttpMethod httpMethod, RequestParams requestParams, Callback.TypedCallback<T> typedCallback) throws Throwable {
        requestParams.setMethod(httpMethod);
        return (T) C5540x.task().startSync(new HttpTask(requestParams, null, typedCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class DefaultSyncCallback<T> implements Callback.TypedCallback<T> {
        private final Class<T> resultType;

        @Override // org.xutils.common.Callback.CommonCallback
        public void onCancelled(Callback.CancelledException cancelledException) {
        }

        @Override // org.xutils.common.Callback.CommonCallback
        public void onError(Throwable th, boolean z) {
        }

        @Override // org.xutils.common.Callback.CommonCallback
        public void onFinished() {
        }

        @Override // org.xutils.common.Callback.CommonCallback
        public void onSuccess(T t) {
        }

        public DefaultSyncCallback(HttpManagerImpl httpManagerImpl, Class<T> cls) {
            this.resultType = cls;
        }

        @Override // org.xutils.common.Callback.TypedCallback
        public Type getLoadType() {
            return this.resultType;
        }
    }
}
