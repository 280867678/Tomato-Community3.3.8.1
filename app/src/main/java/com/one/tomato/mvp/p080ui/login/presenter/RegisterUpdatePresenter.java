package com.one.tomato.mvp.p080ui.login.presenter;

import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.impl.IRegisterUpdateContact;
import com.one.tomato.mvp.p080ui.login.impl.IRegisterUpdateContact$IRegisterUpdateView;
import com.one.tomato.utils.RxUtils;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RegisterUpdatePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.login.presenter.RegisterUpdatePresenter */
/* loaded from: classes3.dex */
public final class RegisterUpdatePresenter extends MvpBasePresenter<IRegisterUpdateContact$IRegisterUpdateView> implements IRegisterUpdateContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestUpdateUserInfo(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestUpdateUserInfo(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.presenter.RegisterUpdatePresenter$requestUpdateUserInfo$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IRegisterUpdateContact$IRegisterUpdateView mView;
                mView = RegisterUpdatePresenter.this.getMView();
                if (mView != null) {
                    mView.handleUpdateUserInfo();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IRegisterUpdateContact$IRegisterUpdateView mView;
                mView = RegisterUpdatePresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
            }
        });
    }
}
