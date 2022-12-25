package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.iview.ISearchView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.SearchPresenter */
/* loaded from: classes3.dex */
public class SearchPresenter extends BasePresenter<ISearchView> {
    public SearchPresenter(Context context, ISearchView iSearchView) {
        super(context, iSearchView);
    }

    public void getLiveEnjoyList(StateView stateView) {
        addMapSubscription(this.mApiService.getEnjoyListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<List<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.SearchPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<LiveEntity> list) {
                if (list == null) {
                    return;
                }
                ((ISearchView) SearchPresenter.this.getView()).onLiveListSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((ISearchView) SearchPresenter.this.getView()).onResultError(i);
            }
        }, stateView, true));
    }

    public void getHotKeyList() {
        addMapSubscription(this.mApiService.getHotKeyListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<LabelEntity>>() { // from class: com.tomatolive.library.ui.presenter.SearchPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<LabelEntity> list) {
                if (list == null) {
                    return;
                }
                ((ISearchView) SearchPresenter.this.getView()).onHotKeyListSuccess(list);
            }
        }));
    }

    public void getAutoKeyList(final String str) {
        addMapSubscription(this.mApiService.getHotKeyListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<LabelEntity>>() { // from class: com.tomatolive.library.ui.presenter.SearchPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<LabelEntity> list) {
                if (list == null) {
                    return;
                }
                ((ISearchView) SearchPresenter.this.getView()).onAutoKeyListSuccess(str, list);
            }
        }));
    }
}
