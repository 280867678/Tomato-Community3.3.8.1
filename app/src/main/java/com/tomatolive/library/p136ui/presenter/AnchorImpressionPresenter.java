package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.ImpressionEntity;
import com.tomatolive.library.p136ui.view.iview.IAnchorImpressionView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.DBUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.AnchorImpressionPresenter */
/* loaded from: classes3.dex */
public class AnchorImpressionPresenter extends BasePresenter<IAnchorImpressionView> {
    public AnchorImpressionPresenter(Context context, IAnchorImpressionView iAnchorImpressionView) {
        super(context, iAnchorImpressionView);
    }

    public void getImpressionList(String str, String str2, StateView stateView) {
        addMapSubscription(this.mApiService.getImpressionListService(new RequestParams().getImpressionParams(str2, str)), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<List<ImpressionEntity>>() { // from class: com.tomatolive.library.ui.presenter.AnchorImpressionPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<ImpressionEntity> list) {
                ((IAnchorImpressionView) AnchorImpressionPresenter.this.getView()).onImpressionListSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((IAnchorImpressionView) AnchorImpressionPresenter.this.getView()).onImpressionListFail();
            }
        }, stateView, true));
    }

    public void updateImpressionList(String str, String str2, final String str3, String str4) {
        addMapSubscription(this.mApiService.updateImpressionListService(new RequestParams().getUpdateImpressionParams(str2, str, str3, str4)), new HttpRxObserver(getContext(), new ResultCallBack<List<ImpressionEntity>>() { // from class: com.tomatolive.library.ui.presenter.AnchorImpressionPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str5) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<ImpressionEntity> list) {
                ((IAnchorImpressionView) AnchorImpressionPresenter.this.getView()).onImpressionListUpdateSuccess(list, str3);
            }
        }));
    }

    public List<String> getLocalLightImpressionList(String str, String str2) {
        return DBUtils.getLightImpressionList(str, str2);
    }

    private List<ImpressionEntity> getList() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] strArr2 = {"软妹子", "潮人", "性感", "清纯", "闷骚", "女汉子", "女神", "身材", "可爱"};
        for (int i = 0; i < strArr.length; i++) {
            arrayList.add(new ImpressionEntity(strArr[i], strArr2[i]));
        }
        return arrayList;
    }
}
