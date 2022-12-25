package com.p055cz.db_ormlite;

import android.util.Log;
import java.util.concurrent.Callable;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/* renamed from: com.cz.db_ormlite.RxUtil */
/* loaded from: classes2.dex */
public class RxUtil {
    public static final String TAG = "RxUtil";

    public static <T> Observable<T> getDbObservable(final Callable<T> callable) {
        return Observable.create(new Observable.OnSubscribe<T>() { // from class: com.cz.db_ormlite.RxUtil.1
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(callable.call());
                } catch (Exception e) {
                    Log.e(RxUtil.TAG, "Error reading from the database", e);
                }
            }
        });
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription compositeSubscription) {
        if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
            Log.i(TAG, "-------init rxjava");
            return new CompositeSubscription();
        }
        return compositeSubscription;
    }

    public static <T> Subscription subscribe(Callable<T> callable, Action1<T> action1) {
        return getDbObservable(callable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(action1);
    }

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
            Log.i(TAG, "-------unsubscribe");
        }
    }

    public void test() {
    }
}
