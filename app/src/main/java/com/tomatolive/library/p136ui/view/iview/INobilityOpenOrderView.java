package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.NobilityEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.INobilityOpenOrderView */
/* loaded from: classes3.dex */
public interface INobilityOpenOrderView extends BaseView {
    void onBuyFail(int i);

    void onBuySuccess(String str);

    void onNobilityListFail();

    void onNobilityListSuccess(List<NobilityEntity> list);

    void onUserOverFail();

    void onUserOverSuccess(MyAccountEntity myAccountEntity);
}
