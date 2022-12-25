package com.one.tomato.mvp.p080ui.papa.presenter;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotContact;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotContact$IPaPaHotView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;

/* compiled from: NewPaPaHotPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.presenter.NewPaPaHotPresenter */
/* loaded from: classes3.dex */
public final class NewPaPaHotPresenter extends MvpBasePresenter<IPaPaHotContact$IPaPaHotView> implements IPaPaHotContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestPostList(int i, int i2) {
        ApiImplService.Companion.getApiImplService().getNewPaPaListData(i, i2, DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaHotPresenter$requestPostList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaHotContact$IPaPaHotView mView;
                mView = NewPaPaHotPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostListData(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPaPaHotContact$IPaPaHotView mView;
                StringBuilder sb = new StringBuilder();
                sb.append("拍拍 -獲取數據失敗=");
                String str = null;
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3788d(sb.toString());
                mView = NewPaPaHotPresenter.this.getMView();
                if (mView != null) {
                    if (responseThrowable != null) {
                        str = responseThrowable.getThrowableMessage();
                    }
                    mView.onError(str);
                }
            }
        });
    }

    public void requestMostHotVideoList() {
        ApiImplService.Companion.getApiImplService().requestMostHotVideoList().compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.papa.presenter.NewPaPaHotPresenter$requestMostHotVideoList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> arrayList) {
                IPaPaHotContact$IPaPaHotView mView;
                mView = NewPaPaHotPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerMostHotVideo(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("拍拍 -最热视频");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3788d(sb.toString());
            }
        });
    }
}
