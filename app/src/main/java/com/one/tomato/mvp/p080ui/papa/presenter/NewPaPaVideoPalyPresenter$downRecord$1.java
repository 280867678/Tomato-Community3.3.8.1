package com.one.tomato.mvp.p080ui.papa.presenter;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.utils.LogUtil;
import io.reactivex.functions.Consumer;

/* compiled from: NewPaPaVideoPalyPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.presenter.NewPaPaVideoPalyPresenter$downRecord$1 */
/* loaded from: classes3.dex */
final class NewPaPaVideoPalyPresenter$downRecord$1<T> implements Consumer<BaseResponse<Object>> {
    public static final NewPaPaVideoPalyPresenter$downRecord$1 INSTANCE = new NewPaPaVideoPalyPresenter$downRecord$1();

    NewPaPaVideoPalyPresenter$downRecord$1() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(BaseResponse<Object> baseResponse) {
        LogUtil.m3787d("yan", "下載紀錄上報");
    }
}
