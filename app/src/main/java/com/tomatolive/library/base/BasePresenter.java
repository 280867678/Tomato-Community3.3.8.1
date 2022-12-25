package com.tomatolive.library.base;

import android.content.Context;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.ApiService;
import com.tomatolive.library.http.HttpRxObservable;
import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;
import io.reactivex.Observer;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class BasePresenter<V> {
    protected ApiService mApiService = ApiRetrofit.getInstance().getApiService();
    protected WeakReference<Context> mContextRef;
    protected WeakReference<V> mViewRef;

    public BasePresenter(Context context, V v) {
        attachView(context, v);
    }

    public void attachView(Context context, V v) {
        this.mContextRef = new WeakReference<>(context);
        this.mViewRef = new WeakReference<>(v);
    }

    public void detachView() {
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            weakReference.clear();
            this.mContextRef = null;
        }
        WeakReference<V> weakReference2 = this.mViewRef;
        if (weakReference2 != null) {
            weakReference2.clear();
            this.mViewRef = null;
        }
    }

    public boolean isAttached() {
        WeakReference<V> weakReference = this.mViewRef;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Context getContext() {
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public V getView() {
        WeakReference<V> weakReference = this.mViewRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public LifecycleProvider getLifecycleProvider() {
        V view = getView();
        if (view == null || !(view instanceof LifecycleProvider)) {
            return null;
        }
        return (LifecycleProvider) view;
    }

    public boolean isApiService() {
        return this.mApiService != null;
    }

    public void addMapSubscription(Observable observable, Observer observer) {
        if (!isAttached()) {
            return;
        }
        HttpRxObservable.getObservable(observable, getLifecycleProvider()).subscribe(observer);
    }

    public void addMapSubscription(Observable observable, Observer observer, int i, int i2) {
        if (!isAttached()) {
            return;
        }
        HttpRxObservable.getObservable(observable, getLifecycleProvider(), i, i2).subscribe(observer);
    }
}
