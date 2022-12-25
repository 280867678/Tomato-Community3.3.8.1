package com.one.tomato.mvp.p080ui.circle.presenter;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ICircleView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CircleTabPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.presenter.CircleTabPresenter */
/* loaded from: classes3.dex */
public final class CircleTabPresenter extends MvpBasePresenter<ICircleTabContract$ICircleView> implements ICircleTabContract {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestCircleFllow(int i, final int i2, String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        ApiImplService.Companion.getApiImplService().circleFllow(url, DBUtil.getMemberId(), i).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleTabPresenter$requestCircleFllow$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                CircleTabPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleTabPresenter$requestCircleFllow$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                ICircleTabContract$ICircleView mView;
                mView = CircleTabPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerCircleFllowSuccess(i2);
                }
                CircleTabPresenter.this.dismissDialog();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CircleTabPresenter.this.dismissDialog();
                StringBuilder sb = new StringBuilder();
                sb.append("圈子關注失敗 +++");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    public void requestCircleDiscover() {
        ApiImplService.Companion.getApiImplService().requestCircleDiscoverHome(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new Consumer<BaseResponse<ArrayList<CircleDiscoverListBean>>>() { // from class: com.one.tomato.mvp.ui.circle.presenter.CircleTabPresenter$requestCircleDiscover$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(BaseResponse<ArrayList<CircleDiscoverListBean>> baseResponse) {
                ICircleTabContract$ICircleView mView;
                ICircleTabContract$ICircleView mView2;
                if (baseResponse instanceof BaseResponse) {
                    mView2 = CircleTabPresenter.this.getMView();
                    if (mView2 == null) {
                        return;
                    }
                    mView2.handlerCircleDiscover(baseResponse.getData());
                } else if (!(baseResponse instanceof Throwable)) {
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("推荐的圈子错误 ++");
                    Throwable th = (Throwable) baseResponse;
                    sb.append(th.getMessage());
                    LogUtil.m3787d("yan", sb.toString());
                    mView = CircleTabPresenter.this.getMView();
                    if (mView == null) {
                        return;
                    }
                    mView.onError(th.getMessage());
                }
            }
        });
    }
}
