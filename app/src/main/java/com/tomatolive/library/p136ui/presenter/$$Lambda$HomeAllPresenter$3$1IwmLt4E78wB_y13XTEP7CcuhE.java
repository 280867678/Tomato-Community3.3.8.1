package com.tomatolive.library.p136ui.presenter;

import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.presenter.-$$Lambda$HomeAllPresenter$3$1IwmLt4E78wB_y13-XTEP7CcuhE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeAllPresenter$3$1IwmLt4E78wB_y13XTEP7CcuhE implements Function {
    public static final /* synthetic */ $$Lambda$HomeAllPresenter$3$1IwmLt4E78wB_y13XTEP7CcuhE INSTANCE = new $$Lambda$HomeAllPresenter$3$1IwmLt4E78wB_y13XTEP7CcuhE();

    private /* synthetic */ $$Lambda$HomeAllPresenter$3$1IwmLt4E78wB_y13XTEP7CcuhE() {
    }

    @Override // io.reactivex.functions.Function
    /* renamed from: apply */
    public final Object mo6755apply(Object obj) {
        ObservableSource just;
        just = Observable.just(AppUtils.formatHttpResultPageModel((HttpResultPageModel) obj));
        return just;
    }
}
