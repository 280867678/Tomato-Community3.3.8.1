package com.one.tomato.mvp.p080ui.p082up.presenter;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.RewardRecordBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.p082up.impl.RewardRecordContact;
import com.one.tomato.mvp.p080ui.p082up.impl.RewardRecordContact$RewardRecordPresenter;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RewardRecordPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.presenter.RewardRecordPresenter */
/* loaded from: classes3.dex */
public final class RewardRecordPresenter extends MvpBasePresenter<RewardRecordContact> implements RewardRecordContact$RewardRecordPresenter {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestUpPayRecordList(long j, int i, int i2) {
        ApiImplService.Companion.getApiImplService().requestUpPayRecordList(j, i, i2).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<RewardRecordBean>>() { // from class: com.one.tomato.mvp.ui.up.presenter.RewardRecordPresenter$requestUpPayRecordList$1
            /* JADX WARN: Code restructure failed: missing block: B:3:0x000d, code lost:
                r0 = r1.this$0.getMView();
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver, io.reactivex.Observer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onNext(BaseResponse<ArrayList<RewardRecordBean>> baseResponse) {
                RewardRecordContact mView;
                Intrinsics.checkParameterIsNotNull(baseResponse, "baseResponse");
                super.onNext((BaseResponse) baseResponse);
                BaseResponse.Page page = baseResponse.page;
                if (page == null || mView == null) {
                    return;
                }
                mView.handleListTotalCount(page.getTotalCount());
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<RewardRecordBean> arrayList) {
                RewardRecordContact mView;
                RewardRecordContact mView2;
                if (arrayList != null) {
                    if (arrayList == null || arrayList.isEmpty()) {
                        mView2 = RewardRecordPresenter.this.getMView();
                        if (mView2 == null) {
                            return;
                        }
                        mView2.onEmpty("");
                        return;
                    }
                }
                mView = RewardRecordPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPayList(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RewardRecordContact mView;
                mView = RewardRecordPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
