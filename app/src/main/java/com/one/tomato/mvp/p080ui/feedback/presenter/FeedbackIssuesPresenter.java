package com.one.tomato.mvp.p080ui.feedback.presenter;

import com.one.tomato.entity.RechargeFeedbackIssues;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackIssuesContact;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackIssuesContact$IFeedbackIssuesView;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FeedbackIssuesPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.presenter.FeedbackIssuesPresenter */
/* loaded from: classes3.dex */
public final class FeedbackIssuesPresenter extends MvpBasePresenter<IFeedbackIssuesContact$IFeedbackIssuesView> implements IFeedbackIssuesContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestIssues(HashMap<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestIssues(map).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<RechargeFeedbackIssues>>() { // from class: com.one.tomato.mvp.ui.feedback.presenter.FeedbackIssuesPresenter$requestIssues$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<RechargeFeedbackIssues> arrayList) {
                IFeedbackIssuesContact$IFeedbackIssuesView mView;
                mView = FeedbackIssuesPresenter.this.getMView();
                if (mView != null) {
                    if (arrayList != null) {
                        mView.handleIssues(arrayList);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        });
    }
}
