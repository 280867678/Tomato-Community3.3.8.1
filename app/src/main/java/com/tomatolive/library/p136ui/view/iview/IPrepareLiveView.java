package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.AnchorStartLiveEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.CoverEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.LiveEndEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.OnLineUsersEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.RelationLastLiveEntity;
import com.tomatolive.library.model.StartLiveVerifyEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IPrepareLiveView */
/* loaded from: classes3.dex */
public interface IPrepareLiveView extends BaseView {
    void onAnchorInfoFail(int i, String str);

    void onAnchorInfoSuccess(AnchorEntity anchorEntity);

    void onFeedbackSuccess();

    void onGiftBoxListSuccess(List<GiftBoxEntity> list);

    void onGiftListFail();

    void onGiftListSuccess(List<GiftDownloadItemEntity> list);

    void onLiveAdNoticeSuccess(BannerEntity bannerEntity);

    void onLiveAudiencesSuccess(OnLineUsersEntity onLineUsersEntity);

    void onLiveEndFail();

    void onLiveEndSuccess(int i, LiveEndEntity liveEndEntity);

    void onLivePopularitySuccess(long j);

    void onPoplarCardRemaining(long j);

    void onPreStartLiveInfoFail();

    void onPreStartLiveInfoSuccess(CoverEntity coverEntity, boolean z);

    void onQMInteractShowTaskFail();

    void onQMInteractShowTaskSuccess(List<QMInteractTaskEntity> list);

    void onRemainCountFail();

    void onRemainCountSuccess(String str);

    void onShowUserManageMenu(int i, boolean z, boolean z2, boolean z3, String str, String str2);

    void onStartLiveFail();

    void onStartLiveSubmitSuccess(boolean z, String str, String str2, String str3, String str4, String str5, long j, RelationLastLiveEntity relationLastLiveEntity);

    void onStartLiveSuccess(AnchorStartLiveEntity anchorStartLiveEntity);

    void onStartPayLiveVerifyFail();

    void onStartPayLiveVerifySuccess(boolean z, StartLiveVerifyEntity startLiveVerifyEntity);

    void onTagListFail();

    void onTagListSuccess(List<LabelEntity> list);

    void onUsePopularFail();

    void onUsePopularSuccess(PopularCardEntity popularCardEntity);

    void onUserCardInfoFail(int i, String str);

    void onUserCardInfoSuccess(UserEntity userEntity);

    void onWebSocketAddressFail();

    void onWebSocketAddressSuccess(LiveEntity liveEntity);
}
