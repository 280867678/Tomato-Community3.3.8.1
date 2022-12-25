package org.xutils.common;

import java.lang.reflect.Type;

/* loaded from: classes4.dex */
public interface Callback {

    /* loaded from: classes4.dex */
    public interface CacheCallback<ResultType> extends CommonCallback<ResultType> {
        boolean onCache(ResultType resulttype);
    }

    /* loaded from: classes4.dex */
    public interface Callable<ResultType> {
        void call(ResultType resulttype);
    }

    /* loaded from: classes4.dex */
    public interface Cancelable {
        void cancel();

        boolean isCancelled();
    }

    /* loaded from: classes4.dex */
    public interface CommonCallback<ResultType> extends Callback {
        void onCancelled(CancelledException cancelledException);

        void onError(Throwable th, boolean z);

        void onFinished();

        void onSuccess(ResultType resulttype);
    }

    /* loaded from: classes4.dex */
    public interface GroupCallback<ItemType> extends Callback {
        void onAllFinished();

        void onCancelled(ItemType itemtype, CancelledException cancelledException);

        void onError(ItemType itemtype, Throwable th, boolean z);

        void onFinished(ItemType itemtype);

        void onSuccess(ItemType itemtype);
    }

    /* loaded from: classes4.dex */
    public interface PrepareCallback<PrepareType, ResultType> extends CommonCallback<ResultType> {
        ResultType prepare(PrepareType preparetype);
    }

    /* loaded from: classes4.dex */
    public interface ProgressCallback<ResultType> extends CommonCallback<ResultType> {
        void onLoading(long j, long j2, boolean z);

        void onStarted();

        void onWaiting();
    }

    /* loaded from: classes4.dex */
    public interface ProxyCacheCallback<ResultType> extends CacheCallback<ResultType> {
        boolean onlyCache();
    }

    /* loaded from: classes4.dex */
    public interface TypedCallback<ResultType> extends CommonCallback<ResultType> {
        Type getLoadType();
    }

    /* loaded from: classes4.dex */
    public static class CancelledException extends RuntimeException {
        public CancelledException(String str) {
            super(str);
        }
    }
}
