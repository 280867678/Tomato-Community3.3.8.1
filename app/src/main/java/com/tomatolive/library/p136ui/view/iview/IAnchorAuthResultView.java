package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.IAnchorAuthResultView */
/* loaded from: classes3.dex */
public interface IAnchorAuthResultView extends BaseView {
    void onAnchorAuthSuccess(AnchorEntity anchorEntity);

    void onCustomerServiceFail();

    void onCustomerServiceSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity);

    void onLiveListFail();
}
