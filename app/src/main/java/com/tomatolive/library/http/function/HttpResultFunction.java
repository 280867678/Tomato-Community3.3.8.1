package com.tomatolive.library.http.function;

import com.tomatolive.library.http.exception.ExceptionEngine;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/* loaded from: classes3.dex */
public class HttpResultFunction<T> implements Function<Throwable, Observable<T>> {
    @Override // io.reactivex.functions.Function
    /* renamed from: apply  reason: avoid collision after fix types in other method */
    public Observable<T> mo6755apply(Throwable th) {
        return Observable.error(ExceptionEngine.handleException(th));
    }
}
