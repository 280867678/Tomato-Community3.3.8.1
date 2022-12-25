package com.tomatolive.library.p136ui.presenter;

import com.tomatolive.library.http.HttpResultPageModel;
import io.reactivex.functions.BiFunction;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.presenter.-$$Lambda$HomeAllPresenter$ArmQqLR41LSQb4pbdjvGPNslaBg  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeAllPresenter$ArmQqLR41LSQb4pbdjvGPNslaBg implements BiFunction {
    public static final /* synthetic */ $$Lambda$HomeAllPresenter$ArmQqLR41LSQb4pbdjvGPNslaBg INSTANCE = new $$Lambda$HomeAllPresenter$ArmQqLR41LSQb4pbdjvGPNslaBg();

    private /* synthetic */ $$Lambda$HomeAllPresenter$ArmQqLR41LSQb4pbdjvGPNslaBg() {
    }

    @Override // io.reactivex.functions.BiFunction
    /* renamed from: apply */
    public final Object mo6745apply(Object obj, Object obj2) {
        HttpResultPageModel httpResultPageModel = (HttpResultPageModel) obj;
        HomeAllPresenter.lambda$initObservableZip$2(httpResultPageModel, (List) obj2);
        return httpResultPageModel;
    }
}
