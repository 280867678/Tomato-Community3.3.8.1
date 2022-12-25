package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AppealInfoEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.IAppealInfoView */
/* loaded from: classes3.dex */
public interface IAppealInfoView extends BaseView {
    void onCancelAppealFailure();

    void onCancelAppealSuccess();

    void onGetAppealInfoFailure(int i, String str);

    void onGetAppealInfoSuccess(AppealInfoEntity appealInfoEntity);
}
