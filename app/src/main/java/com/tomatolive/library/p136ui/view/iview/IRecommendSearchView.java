package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.MyNobilityEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.IRecommendSearchView */
/* loaded from: classes3.dex */
public interface IRecommendSearchView extends BaseView {
    void onDataFail();

    void onDataSuccess(AnchorEntity anchorEntity);

    void onRecommendCount(MyNobilityEntity myNobilityEntity);

    void onRecommendFail(int i, String str);

    void onRecommendSuccess();
}
