package com.one.tomato.mvp.p080ui.feedback.presenter;

import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackRechargeContact;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackRechargeContact$IFeedbackRechargeView;
import com.one.tomato.utils.RxUtils;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FeedbackRechargePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.presenter.FeedbackRechargePresenter */
/* loaded from: classes3.dex */
public final class FeedbackRechargePresenter extends MvpBasePresenter<IFeedbackRechargeContact$IFeedbackRechargeView> implements IFeedbackRechargeContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void submit(HashMap<String, String> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().feedbackRecharge(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.feedback.presenter.FeedbackRechargePresenter$submit$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IFeedbackRechargeContact$IFeedbackRechargeView mView;
                mView = FeedbackRechargePresenter.this.getMView();
                if (mView != null) {
                    mView.handleSubmit();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                FeedbackRechargePresenter.this.dismissDialog();
            }
        });
    }
}
