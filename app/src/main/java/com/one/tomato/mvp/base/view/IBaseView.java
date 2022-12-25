package com.one.tomato.mvp.base.view;

import android.content.Context;
import com.trello.rxlifecycle2.LifecycleProvider;

/* compiled from: IBaseView.kt */
/* loaded from: classes3.dex */
public interface IBaseView {
    void dismissDialog();

    Context getContext();

    LifecycleProvider<?> getLifecycleProvider();

    boolean isStartLogin();

    void onEmpty(Object obj);

    void onError(String str);

    void showDialog();
}
