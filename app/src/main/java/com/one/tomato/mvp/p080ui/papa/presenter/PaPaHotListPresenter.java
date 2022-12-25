package com.one.tomato.mvp.p080ui.papa.presenter;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotListContact;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotListContact$IPaPaHotListView;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;

/* compiled from: PaPaHotListPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.presenter.PaPaHotListPresenter */
/* loaded from: classes3.dex */
public final class PaPaHotListPresenter extends MvpBasePresenter<IPaPaHotListContact$IPaPaHotListView> implements IPaPaHotListContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestHoutListData(int i, int i2, int i3) {
        ApiImplService.Companion.getApiImplService().requestMostHotVideoDetailList(i, i2, i3).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.PaPaHotListPresenter$requestHoutListData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaHotListContact$IPaPaHotListView mView;
                mView = PaPaHotListPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerHoutListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaHotListContact$IPaPaHotListView mView;
                mView = PaPaHotListPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
