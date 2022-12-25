package com.trello.rxlifecycle2;

import io.reactivex.Observable;

/* loaded from: classes4.dex */
public interface LifecycleProvider<E> {
    <T> LifecycleTransformer<T> bindToLifecycle();

    <T> LifecycleTransformer<T> bindUntilEvent(E e);

    Observable<E> lifecycle();
}
