package com.one.tomato.mvp.base.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class RxBus {
    private static volatile RxBus mDefaultInstance;
    private final Subject<Object> mBus = PublishSubject.create().toSerialized();

    public RxBus() {
        new ConcurrentHashMap();
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    public void post(Object obj) {
        this.mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> cls) {
        return (Observable<T>) this.mBus.ofType(cls);
    }
}
