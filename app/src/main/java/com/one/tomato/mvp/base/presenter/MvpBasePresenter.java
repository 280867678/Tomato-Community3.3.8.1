package com.one.tomato.mvp.base.presenter;

import com.one.tomato.mvp.base.view.IBaseView;
import com.trello.rxlifecycle2.LifecycleProvider;

/* compiled from: MvpBasePresenter.kt */
/* loaded from: classes3.dex */
public abstract class MvpBasePresenter<V extends IBaseView> {
    private V mView;

    public abstract void onCreate();

    /* JADX INFO: Access modifiers changed from: protected */
    public final V getMView() {
        return this.mView;
    }

    public final void attachView(V v) {
        this.mView = v;
    }

    public final void detachView() {
        if (this.mView != null) {
            this.mView = null;
        }
    }

    public final LifecycleProvider<?> getLifecycleProvider() {
        V v = this.mView;
        if (v != null) {
            return v.getLifecycleProvider();
        }
        return null;
    }

    public final void showDialog() {
        V v = this.mView;
        if (v != null) {
            v.showDialog();
        }
    }

    public final void dismissDialog() {
        V v = this.mView;
        if (v != null) {
            v.dismissDialog();
        }
    }
}
