package com.one.tomato.mvp.p080ui.mine.presenter;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.impl.IMinePostPublish;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MinePostPublishPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.presenter.MinePostPublishPresenter */
/* loaded from: classes3.dex */
public final class MinePostPublishPresenter extends MvpBasePresenter<IMinePostPublish> {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public final void requestData(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().postPushPost(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostList>>() { // from class: com.one.tomato.mvp.ui.mine.presenter.MinePostPublishPresenter$requestData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostList> t) {
                IMinePostPublish mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = MinePostPublishPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerPostList(t);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IMinePostPublish mView;
                mView = MinePostPublishPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public final void requestDeletePushPost(String articleIds, int i) {
        Intrinsics.checkParameterIsNotNull(articleIds, "articleIds");
        ApiImplService.Companion.getApiImplService().deleteMyPushPost(articleIds, i).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.presenter.MinePostPublishPresenter$requestDeletePushPost$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IMinePostPublish mView;
                mView = MinePostPublishPresenter.this.getMView();
                if (mView != null) {
                    mView.showDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.presenter.MinePostPublishPresenter$requestDeletePushPost$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IMinePostPublish mView;
                IMinePostPublish mView2;
                mView = MinePostPublishPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = MinePostPublishPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.handlerRemoveItem();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IMinePostPublish mView;
                IMinePostPublish mView2;
                LogUtil.m3787d("yan", "e" + responseThrowable);
                mView = MinePostPublishPresenter.this.getMView();
                if (mView != null) {
                    mView.dismissDialog();
                }
                mView2 = MinePostPublishPresenter.this.getMView();
                if (mView2 != null) {
                    mView2.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
