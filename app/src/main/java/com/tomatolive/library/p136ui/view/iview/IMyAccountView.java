package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.MyAccountEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.IMyAccountView */
/* loaded from: classes3.dex */
public interface IMyAccountView extends BaseView {
    void onUserOverFail();

    void onUserOverSuccess(MyAccountEntity myAccountEntity);
}
