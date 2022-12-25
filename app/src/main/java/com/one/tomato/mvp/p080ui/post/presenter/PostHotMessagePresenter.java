package com.one.tomato.mvp.p080ui.post.presenter;

import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.post.impl.IPostHotMessageContact;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotMessagePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.presenter.PostHotMessagePresenter */
/* loaded from: classes3.dex */
public final class PostHotMessagePresenter extends MvpBasePresenter<IPostHotMessageContact> {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public final void requestList(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestHotEventList(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PostHotMessageBean>>() { // from class: com.one.tomato.mvp.ui.post.presenter.PostHotMessagePresenter$requestList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostHotMessageBean> arrayList) {
                IPostHotMessageContact mView;
                mView = PostHotMessagePresenter.this.getMView();
                if (mView != null) {
                    mView.handleHotMessage(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IPostHotMessageContact mView;
                mView = PostHotMessagePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
