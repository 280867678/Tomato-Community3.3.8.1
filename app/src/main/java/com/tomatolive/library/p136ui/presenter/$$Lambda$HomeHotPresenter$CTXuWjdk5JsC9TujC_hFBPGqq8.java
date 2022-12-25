package com.tomatolive.library.p136ui.presenter;

import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.presenter.-$$Lambda$HomeHotPresenter$CTXuWjdk-5JsC9TujC_hFBPGqq8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeHotPresenter$CTXuWjdk5JsC9TujC_hFBPGqq8 implements Function {
    public static final /* synthetic */ $$Lambda$HomeHotPresenter$CTXuWjdk5JsC9TujC_hFBPGqq8 INSTANCE = new $$Lambda$HomeHotPresenter$CTXuWjdk5JsC9TujC_hFBPGqq8();

    private /* synthetic */ $$Lambda$HomeHotPresenter$CTXuWjdk5JsC9TujC_hFBPGqq8() {
    }

    @Override // io.reactivex.functions.Function
    /* renamed from: apply */
    public final Object mo6755apply(Object obj) {
        ObservableSource just;
        just = Observable.just(AppUtils.formatHttpResultPageModel((HttpResultPageModel) obj));
        return just;
    }
}
