package com.tomatolive.library.p136ui.presenter;

import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.presenter.-$$Lambda$HomeAllPresenter$f_Fn4XxGwl8qZzCKDjdjde1mBpM  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeAllPresenter$f_Fn4XxGwl8qZzCKDjdjde1mBpM implements Function {
    public static final /* synthetic */ $$Lambda$HomeAllPresenter$f_Fn4XxGwl8qZzCKDjdjde1mBpM INSTANCE = new $$Lambda$HomeAllPresenter$f_Fn4XxGwl8qZzCKDjdjde1mBpM();

    private /* synthetic */ $$Lambda$HomeAllPresenter$f_Fn4XxGwl8qZzCKDjdjde1mBpM() {
    }

    @Override // io.reactivex.functions.Function
    /* renamed from: apply */
    public final Object mo6755apply(Object obj) {
        ObservableSource just;
        just = Observable.just(AppUtils.formatHttpResultPageModel((HttpResultPageModel) obj));
        return just;
    }
}
