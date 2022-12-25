package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.LivePreNoticeEntity;
import com.tomatolive.library.p136ui.view.iview.ILivePreNoticeView;

/* renamed from: com.tomatolive.library.ui.presenter.LivePreNoticePresenter */
/* loaded from: classes3.dex */
public class LivePreNoticePresenter extends BasePresenter<ILivePreNoticeView> {
    public LivePreNoticePresenter(Context context, ILivePreNoticeView iLivePreNoticeView) {
        super(context, iLivePreNoticeView);
    }

    public void getDataList() {
        addMapSubscription(this.mApiService.getLivePreNoticeService(new RequestParams().getUserIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<LivePreNoticeEntity>() { // from class: com.tomatolive.library.ui.presenter.LivePreNoticePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LivePreNoticeEntity livePreNoticeEntity) {
                if (livePreNoticeEntity == null) {
                    return;
                }
                ((ILivePreNoticeView) LivePreNoticePresenter.this.getView()).onDataSuccess(livePreNoticeEntity);
            }
        }));
    }

    public void onSaveContent(String str) {
        addMapSubscription(this.mApiService.getAddLivePreNoticeService(new RequestParams().getLivePreNoticeParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.LivePreNoticePresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((ILivePreNoticeView) LivePreNoticePresenter.this.getView()).onSaveSuccess();
            }
        }, true));
    }
}
