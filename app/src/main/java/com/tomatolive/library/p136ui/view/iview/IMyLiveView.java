package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.model.MyLiveEntity;
import com.tomatolive.library.model.UserEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.IMyLiveView */
/* loaded from: classes3.dex */
public interface IMyLiveView extends BaseView {
    void onAnchorAuthSuccess(AnchorEntity anchorEntity);

    void onAnchorGradeSuccess(AnchorEntity anchorEntity);

    void onDataSuccess(MyLiveEntity myLiveEntity);

    void onLiveHelperAppConfigFail();

    void onLiveHelperAppConfigSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity);

    void onUserGradeSuccess(UserEntity userEntity);
}
