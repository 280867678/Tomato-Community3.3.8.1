package com.tomatolive.library.p136ui.presenter;

import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.presenter.-$$Lambda$HomeAllPresenter$wfhwJusWh_z5Whdk2pl_dAbe06c  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeAllPresenter$wfhwJusWh_z5Whdk2pl_dAbe06c implements Function {
    public static final /* synthetic */ $$Lambda$HomeAllPresenter$wfhwJusWh_z5Whdk2pl_dAbe06c INSTANCE = new $$Lambda$HomeAllPresenter$wfhwJusWh_z5Whdk2pl_dAbe06c();

    private /* synthetic */ $$Lambda$HomeAllPresenter$wfhwJusWh_z5Whdk2pl_dAbe06c() {
    }

    @Override // io.reactivex.functions.Function
    /* renamed from: apply */
    public final Object mo6755apply(Object obj) {
        ObservableSource just;
        just = Observable.just(AppUtils.formatHttpResultPageModel((HttpResultPageModel) obj));
        return just;
    }
}
