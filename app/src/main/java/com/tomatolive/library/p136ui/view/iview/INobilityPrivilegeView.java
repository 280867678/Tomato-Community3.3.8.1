package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.MyNobilityEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.INobilityPrivilegeView */
/* loaded from: classes3.dex */
public interface INobilityPrivilegeView extends BaseView {
    void onDataFail();

    void onDataSuccess(MyNobilityEntity myNobilityEntity);

    void onEnterHideFail(String str);

    void onEnterHideSuccess(boolean z);
}
