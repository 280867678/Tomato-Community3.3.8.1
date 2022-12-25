package com.one.tomato.mvp.base.bus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/* loaded from: classes3.dex */
public class RxSubscriptions {
    private static CompositeDisposable mSubscriptions = new CompositeDisposable();

    public static void add(Disposable disposable) {
        if (disposable != null) {
            mSubscriptions.add(disposable);
        }
    }

    public static void remove(Disposable disposable) {
        if (disposable != null) {
            mSubscriptions.remove(disposable);
        }
    }
}
