package com.one.tomato.utils;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class RxUtils {
    public static <T> LifecycleTransformer<T> bindToLifecycler(RxAppCompatActivity rxAppCompatActivity) {
        return rxAppCompatActivity.bindToLifecycle();
    }

    public static <T> LifecycleTransformer<T> bindToLifecycler(RxFragment rxFragment) {
        return rxFragment.bindToLifecycle();
    }

    public static <T> LifecycleTransformer<T> bindToLifecycler(LifecycleProvider lifecycleProvider) {
        return lifecycleProvider.bindToLifecycle();
    }

    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return new ObservableTransformer<T, T>() { // from class: com.one.tomato.utils.RxUtils.1
            @Override // io.reactivex.ObservableTransformer
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
