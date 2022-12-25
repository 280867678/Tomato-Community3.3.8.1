package org.xutils.http;

import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import org.xutils.C5540x;
import org.xutils.common.Callback;
import org.xutils.common.task.AbsTask;
import org.xutils.common.task.Priority;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.ParameterizedTypeUtil;
import org.xutils.http.app.HttpRetryHandler;
import org.xutils.http.app.RedirectHandler;
import org.xutils.http.app.RequestInterceptListener;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;
import org.xutils.http.request.UriRequestFactory;
import org.xutils.p149ex.HttpException;
import org.xutils.p149ex.HttpRedirectException;

/* loaded from: classes4.dex */
public class HttpTask<ResultType> extends AbsTask<ResultType> implements ProgressHandler {
    private Callback.CacheCallback<ResultType> cacheCallback;
    private final Callback.CommonCallback<ResultType> callback;
    private final Executor executor;
    private long lastUpdateTime;
    private Type loadType;
    private RequestParams params;
    private Callback.PrepareCallback prepareCallback;
    private Callback.ProgressCallback progressCallback;
    private UriRequest request;
    private RequestInterceptListener requestInterceptListener;
    private HttpTask<ResultType>.RequestWorker requestWorker;
    private RequestTracker tracker;
    private static final AtomicInteger sCurrFileLoadCount = new AtomicInteger(0);
    private static final HashMap<String, WeakReference<HttpTask<?>>> DOWNLOAD_TASK = new HashMap<>(1);
    private static final PriorityExecutor HTTP_EXECUTOR = new PriorityExecutor(5, true);
    private static final PriorityExecutor CACHE_EXECUTOR = new PriorityExecutor(5, true);
    private volatile boolean hasException = false;
    private Object rawResult = null;
    private volatile Boolean trustCache = null;
    private final Object cacheLock = new Object();
    private long loadingUpdateMaxTimeSpan = 300;

    public HttpTask(RequestParams requestParams, Callback.Cancelable cancelable, Callback.CommonCallback<ResultType> commonCallback) {
        super(cancelable);
        this.params = requestParams;
        this.callback = commonCallback;
        if (commonCallback instanceof Callback.CacheCallback) {
            this.cacheCallback = (Callback.CacheCallback) commonCallback;
        }
        if (commonCallback instanceof Callback.PrepareCallback) {
            this.prepareCallback = (Callback.PrepareCallback) commonCallback;
        }
        if (commonCallback instanceof Callback.ProgressCallback) {
            this.progressCallback = (Callback.ProgressCallback) commonCallback;
        }
        if (commonCallback instanceof RequestInterceptListener) {
            this.requestInterceptListener = (RequestInterceptListener) commonCallback;
        }
        RequestTracker requestTracker = requestParams.getRequestTracker();
        if (requestTracker == null) {
            if (commonCallback instanceof RequestTracker) {
                requestTracker = (RequestTracker) commonCallback;
            } else {
                requestTracker = UriRequestFactory.getDefaultTracker();
            }
        }
        if (requestTracker != null) {
            this.tracker = new RequestTrackerWrapper(requestTracker);
        }
        if (requestParams.getExecutor() != null) {
            this.executor = requestParams.getExecutor();
        } else if (this.cacheCallback != null) {
            this.executor = CACHE_EXECUTOR;
        } else {
            this.executor = HTTP_EXECUTOR;
        }
    }

    private void resolveLoadType() {
        Class<?> cls = this.callback.getClass();
        Callback.CommonCallback<ResultType> commonCallback = this.callback;
        if (commonCallback instanceof Callback.TypedCallback) {
            this.loadType = ((Callback.TypedCallback) commonCallback).getLoadType();
        } else if (commonCallback instanceof Callback.PrepareCallback) {
            this.loadType = ParameterizedTypeUtil.getParameterizedType(cls, Callback.PrepareCallback.class, 0);
        } else {
            this.loadType = ParameterizedTypeUtil.getParameterizedType(cls, Callback.CommonCallback.class, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UriRequest createNewRequest() throws Throwable {
        this.params.init();
        UriRequest uriRequest = UriRequestFactory.getUriRequest(this.params, this.loadType);
        uriRequest.setCallingClassLoader(this.callback.getClass().getClassLoader());
        uriRequest.setProgressHandler(this);
        this.loadingUpdateMaxTimeSpan = this.params.getLoadingUpdateMaxTimeSpan();
        update(1, uriRequest);
        return uriRequest;
    }

    private void checkDownloadTask() {
        if (File.class == this.loadType) {
            synchronized (DOWNLOAD_TASK) {
                String saveFilePath = this.params.getSaveFilePath();
                if (!TextUtils.isEmpty(saveFilePath)) {
                    WeakReference<HttpTask<?>> weakReference = DOWNLOAD_TASK.get(saveFilePath);
                    if (weakReference != null) {
                        HttpTask<?> httpTask = weakReference.get();
                        if (httpTask != null) {
                            httpTask.cancel();
                            httpTask.closeRequestSync();
                        }
                        DOWNLOAD_TASK.remove(saveFilePath);
                    }
                    DOWNLOAD_TASK.put(saveFilePath, new WeakReference<>(this));
                }
                if (DOWNLOAD_TASK.size() > 3) {
                    Iterator<Map.Entry<String, WeakReference<HttpTask<?>>>> it2 = DOWNLOAD_TASK.entrySet().iterator();
                    while (it2.hasNext()) {
                        WeakReference<HttpTask<?>> value = it2.next().getValue();
                        if (value == null || value.get() == null) {
                            it2.remove();
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v36, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v37 */
    /* JADX WARN: Type inference failed for: r1v49, types: [java.lang.Object] */
    @Override // org.xutils.common.task.AbsTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ResultType doBackground() throws Throwable {
        Object obj;
        Callback.CommonCallback<ResultType> commonCallback;
        Callback.CancelledException th;
        ResultType resulttype;
        boolean z;
        ?? r1;
        if (isCancelled()) {
            throw new Callback.CancelledException("cancelled before request");
        }
        resolveLoadType();
        this.request = createNewRequest();
        checkDownloadTask();
        HttpRetryHandler httpRetryHandler = this.params.getHttpRetryHandler();
        if (httpRetryHandler == null) {
            httpRetryHandler = new HttpRetryHandler();
        }
        httpRetryHandler.setMaxRetryCount(this.params.getMaxRetryCount());
        if (isCancelled()) {
            throw new Callback.CancelledException("cancelled before request");
        }
        if (this.cacheCallback != null && HttpMethod.permitsCache(this.params.getMethod())) {
            try {
                clearRawResult();
                LogUtil.m46d("load cache: " + this.request.getRequestUri());
                this.rawResult = this.request.loadResultFromCache();
            } catch (Throwable th2) {
                LogUtil.m37w("load disk cache error", th2);
            }
            if (isCancelled()) {
                clearRawResult();
                throw new Callback.CancelledException("cancelled before request");
            }
            obj = this.rawResult;
            if (obj != null) {
                Callback.PrepareCallback prepareCallback = this.prepareCallback;
                if (prepareCallback != null) {
                    try {
                        obj = prepareCallback.prepare(obj);
                    } catch (Throwable th3) {
                        try {
                            LogUtil.m37w("prepare disk cache error", th3);
                            clearRawResult();
                            obj = null;
                        } finally {
                            clearRawResult();
                        }
                    }
                }
                if (isCancelled()) {
                    throw new Callback.CancelledException("cancelled before request");
                }
                if (obj != null) {
                    update(2, obj);
                    synchronized (this.cacheLock) {
                        while (this.trustCache == null) {
                            try {
                                this.cacheLock.wait();
                            } catch (InterruptedException unused) {
                                throw new Callback.CancelledException("cancelled before request");
                            } catch (Throwable unused2) {
                            }
                        }
                    }
                    if (this.trustCache.booleanValue()) {
                        return null;
                    }
                }
                if (this.trustCache == null) {
                    this.trustCache = false;
                }
                if (obj == null) {
                    this.request.clearCacheHeader();
                }
                commonCallback = this.callback;
                if (!(commonCallback instanceof Callback.ProxyCacheCallback) && ((Callback.ProxyCacheCallback) commonCallback).onlyCache()) {
                    return null;
                }
                th = null;
                resulttype = null;
                z = true;
                int i = 0;
                while (z) {
                    try {
                    } catch (HttpRedirectException unused3) {
                    } catch (Throwable th4) {
                        th = th4;
                    }
                    if (isCancelled()) {
                        throw new Callback.CancelledException("cancelled before request");
                    }
                    this.request.close();
                    clearRawResult();
                    LogUtil.m46d("load: " + this.request.getRequestUri());
                    this.requestWorker = new RequestWorker();
                    this.requestWorker.request();
                    if (this.requestWorker.f6068ex != null) {
                        throw this.requestWorker.f6068ex;
                    }
                    this.rawResult = this.requestWorker.result;
                    if (this.prepareCallback != null) {
                        if (isCancelled()) {
                            throw new Callback.CancelledException("cancelled before request");
                        }
                        r1 = this.prepareCallback.prepare(this.rawResult);
                        try {
                        } catch (HttpRedirectException unused4) {
                            resulttype = r1;
                            LogUtil.m38w("Http Redirect:" + this.params.getUri());
                            z = true;
                        } catch (Throwable th5) {
                            th = th5;
                            resulttype = r1;
                            int responseCode = this.request.getResponseCode();
                            if (responseCode == 204 || responseCode == 205 || responseCode == 304) {
                                return null;
                            }
                            if (isCancelled() && !(th instanceof Callback.CancelledException)) {
                                th = new Callback.CancelledException("canceled by user");
                            }
                            i++;
                            z = httpRetryHandler.canRetry(this.request, th, i);
                        }
                    } else {
                        r1 = this.rawResult;
                    }
                    resulttype = r1;
                    if (this.cacheCallback != null && HttpMethod.permitsCache(this.params.getMethod())) {
                        this.request.save2Cache();
                    }
                    if (isCancelled()) {
                        throw new Callback.CancelledException("cancelled after request");
                    }
                    z = false;
                }
                if (th != null || resulttype != null || this.trustCache.booleanValue()) {
                    return resulttype;
                }
                this.hasException = true;
                throw th;
            }
        }
        obj = null;
        if (this.trustCache == null) {
        }
        if (obj == null) {
        }
        commonCallback = this.callback;
        if (!(commonCallback instanceof Callback.ProxyCacheCallback)) {
        }
        th = null;
        resulttype = null;
        z = true;
        int i2 = 0;
        while (z) {
        }
        if (th != null) {
        }
        return resulttype;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.xutils.common.task.AbsTask
    public void onUpdate(int i, Object... objArr) {
        Callback.ProgressCallback progressCallback;
        if (i == 1) {
            RequestTracker requestTracker = this.tracker;
            if (requestTracker == null) {
                return;
            }
            requestTracker.onRequestCreated((UriRequest) objArr[0]);
        } else if (i == 2) {
            synchronized (this.cacheLock) {
                Object obj = objArr[0];
                if (this.tracker != null) {
                    this.tracker.onCache(this.request, obj);
                }
                this.trustCache = Boolean.valueOf(this.cacheCallback.onCache(obj));
                this.cacheLock.notifyAll();
            }
        } else if (i != 3 || (progressCallback = this.progressCallback) == null || objArr.length != 3) {
        } else {
            try {
                progressCallback.onLoading(((Number) objArr[0]).longValue(), ((Number) objArr[1]).longValue(), ((Boolean) objArr[2]).booleanValue());
            } catch (Throwable th) {
                this.callback.onError(th, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onWaiting() {
        RequestTracker requestTracker = this.tracker;
        if (requestTracker != null) {
            requestTracker.onWaiting(this.params);
        }
        Callback.ProgressCallback progressCallback = this.progressCallback;
        if (progressCallback != null) {
            progressCallback.onWaiting();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onStarted() {
        RequestTracker requestTracker = this.tracker;
        if (requestTracker != null) {
            requestTracker.onStart(this.params);
        }
        Callback.ProgressCallback progressCallback = this.progressCallback;
        if (progressCallback != null) {
            progressCallback.onStarted();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onSuccess(ResultType resulttype) {
        if (this.hasException) {
            return;
        }
        RequestTracker requestTracker = this.tracker;
        if (requestTracker != null) {
            requestTracker.onSuccess(this.request, resulttype);
        }
        this.callback.onSuccess(resulttype);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onError(Throwable th, boolean z) {
        RequestTracker requestTracker = this.tracker;
        if (requestTracker != null) {
            requestTracker.onError(this.request, th, z);
        }
        this.callback.onError(th, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onCancelled(Callback.CancelledException cancelledException) {
        RequestTracker requestTracker = this.tracker;
        if (requestTracker != null) {
            requestTracker.onCancelled(this.request);
        }
        this.callback.onCancelled(cancelledException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onFinished() {
        RequestTracker requestTracker = this.tracker;
        if (requestTracker != null) {
            requestTracker.onFinished(this.request);
        }
        C5540x.task().run(new Runnable() { // from class: org.xutils.http.HttpTask.1
            @Override // java.lang.Runnable
            public void run() {
                HttpTask.this.closeRequestSync();
            }
        });
        this.callback.onFinished();
    }

    private void clearRawResult() {
        Object obj = this.rawResult;
        if (obj instanceof Closeable) {
            IOUtil.closeQuietly((Closeable) obj);
        }
        this.rawResult = null;
    }

    @Override // org.xutils.common.task.AbsTask
    protected void cancelWorks() {
        C5540x.task().run(new Runnable() { // from class: org.xutils.http.HttpTask.2
            @Override // java.lang.Runnable
            public void run() {
                HttpTask.this.closeRequestSync();
            }
        });
    }

    @Override // org.xutils.common.task.AbsTask
    protected boolean isCancelFast() {
        return this.params.isCancelFast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRequestSync() {
        clearRawResult();
        IOUtil.closeQuietly(this.request);
    }

    @Override // org.xutils.common.task.AbsTask
    public Executor getExecutor() {
        return this.executor;
    }

    @Override // org.xutils.common.task.AbsTask
    public Priority getPriority() {
        return this.params.getPriority();
    }

    @Override // org.xutils.http.ProgressHandler
    public boolean updateProgress(long j, long j2, boolean z) {
        if (isCancelled() || isFinished()) {
            return false;
        }
        if (this.progressCallback != null && this.request != null && j > 0) {
            if (j < j2) {
                j = j2;
            }
            if (z) {
                this.lastUpdateTime = System.currentTimeMillis();
                update(3, Long.valueOf(j), Long.valueOf(j2), Boolean.valueOf(this.request.isLoading()));
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.lastUpdateTime >= this.loadingUpdateMaxTimeSpan) {
                    this.lastUpdateTime = currentTimeMillis;
                    update(3, Long.valueOf(j), Long.valueOf(j2), Boolean.valueOf(this.request.isLoading()));
                }
            }
        }
        return !isCancelled() && !isFinished();
    }

    public String toString() {
        return this.params.toString();
    }

    /* loaded from: classes4.dex */
    private final class RequestWorker {

        /* renamed from: ex */
        Throwable f6068ex;
        Object result;

        private RequestWorker() {
        }

        public void request() {
            HttpException httpException;
            int code;
            RedirectHandler redirectHandler;
            RequestParams redirectParams;
            boolean z = false;
            try {
                if (File.class == HttpTask.this.loadType) {
                    synchronized (HttpTask.sCurrFileLoadCount) {
                        while (HttpTask.sCurrFileLoadCount.get() >= 3 && !HttpTask.this.isCancelled()) {
                            try {
                                HttpTask.sCurrFileLoadCount.wait(10L);
                            } catch (InterruptedException unused) {
                                z = true;
                            } catch (Throwable unused2) {
                            }
                        }
                    }
                    HttpTask.sCurrFileLoadCount.incrementAndGet();
                }
                if (!z && !HttpTask.this.isCancelled()) {
                    HttpTask.this.request.setRequestInterceptListener(HttpTask.this.requestInterceptListener);
                    this.result = HttpTask.this.request.loadResult();
                    if (this.f6068ex != null) {
                        throw this.f6068ex;
                    }
                    if (File.class != HttpTask.this.loadType) {
                        return;
                    }
                    synchronized (HttpTask.sCurrFileLoadCount) {
                        HttpTask.sCurrFileLoadCount.decrementAndGet();
                        HttpTask.sCurrFileLoadCount.notifyAll();
                    }
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("cancelled before request");
                sb.append(z ? "(interrupted)" : "");
                throw new Callback.CancelledException(sb.toString());
            } catch (Throwable th) {
                try {
                    this.f6068ex = th;
                    if ((th instanceof HttpException) && (((code = (httpException = th).getCode()) == 301 || code == 302) && (redirectHandler = HttpTask.this.params.getRedirectHandler()) != null && (redirectParams = redirectHandler.getRedirectParams(HttpTask.this.request)) != null)) {
                        if (redirectParams.getMethod() == null) {
                            redirectParams.setMethod(HttpTask.this.params.getMethod());
                        }
                        HttpTask.this.params = redirectParams;
                        HttpTask.this.request = HttpTask.this.createNewRequest();
                        this.f6068ex = new HttpRedirectException(code, httpException.getMessage(), httpException.getResult());
                    }
                    if (File.class != HttpTask.this.loadType) {
                        return;
                    }
                    synchronized (HttpTask.sCurrFileLoadCount) {
                        HttpTask.sCurrFileLoadCount.decrementAndGet();
                        HttpTask.sCurrFileLoadCount.notifyAll();
                    }
                } catch (Throwable th2) {
                    if (File.class == HttpTask.this.loadType) {
                        synchronized (HttpTask.sCurrFileLoadCount) {
                            HttpTask.sCurrFileLoadCount.decrementAndGet();
                            HttpTask.sCurrFileLoadCount.notifyAll();
                        }
                    }
                    throw th2;
                }
            }
        }
    }
}
