package com.tomatolive.library.http;

import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.http.utils.RetryWithDelayUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class HttpRxObservable {
    public static Observable getObservable(Observable observable) {
        return observable.map(new ServerResultFunction()).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable getObservable(Observable observable, int i, int i2) {
        return observable.map(new ServerResultFunction()).onErrorResumeNext(new HttpResultFunction()).retryWhen(new RetryWithDelayUtils(i, i2)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable getObservable(Observable observable, LifecycleProvider lifecycleProvider) {
        if (lifecycleProvider != null) {
            return observable.map(new ServerResultFunction()).compose(lifecycleProvider.bindToLifecycle()).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
        }
        return getObservable(observable);
    }

    public static Observable getObservable(Observable observable, LifecycleProvider lifecycleProvider, int i, int i2) {
        if (lifecycleProvider != null) {
            return observable.map(new ServerResultFunction()).compose(lifecycleProvider.bindToLifecycle()).onErrorResumeNext(new HttpResultFunction()).retryWhen(new RetryWithDelayUtils(i, i2)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
        }
        return getObservable(observable, i, i2);
    }

    public static Observable getObservable(Observable observable, LifecycleProvider<ActivityEvent> lifecycleProvider, ActivityEvent activityEvent) {
        if (lifecycleProvider != null) {
            return observable.map(new ServerResultFunction()).compose(lifecycleProvider.bindUntilEvent(activityEvent)).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
        }
        return getObservable(observable);
    }

    public static Observable getObservable(Observable observable, LifecycleProvider<FragmentEvent> lifecycleProvider, FragmentEvent fragmentEvent) {
        if (lifecycleProvider != null) {
            return observable.map(new ServerResultFunction()).compose(lifecycleProvider.bindUntilEvent(fragmentEvent)).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
        }
        return getObservable(observable);
    }
}
