package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.BoomStatusEntity;
import com.tomatolive.library.model.CheckTicketEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveInitInfoEntity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.OnLineUsersEntity;
import com.tomatolive.library.model.PKRecordEntity;
import com.tomatolive.library.model.PropConfigEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.TaskBoxEntity;
import com.tomatolive.library.model.TrumpetStatusEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.ITomatoLiveView */
/* loaded from: classes3.dex */
public interface ITomatoLiveView extends BaseView {
    void onAnchorInfoFail();

    void onAnchorInfoSuccess(AnchorEntity anchorEntity);

    void onAttentionSuccess();

    void onBoomStatusFail();

    void onBoomStatusSuccess(BoomStatusEntity boomStatusEntity);

    void onFragmentConfigSuccess(PropConfigEntity propConfigEntity);

    void onGiftBoxListSuccess(List<GiftBoxEntity> list);

    void onGiftListFail();

    void onGiftListSuccess(List<GiftDownloadItemEntity> list);

    void onLiveAdListFail(String str);

    void onLiveAdListSuccess(String str, List<BannerEntity> list);

    void onLiveAdNoticeSuccess(BannerEntity bannerEntity);

    void onLiveAudiencesSuccess(OnLineUsersEntity onLineUsersEntity);

    void onLiveInitInfoFail(int i, String str);

    void onLiveInitInfoSuccess(String str, String str2, LiveInitInfoEntity liveInitInfoEntity, boolean z, boolean z2);

    void onLivePopularitySuccess(long j);

    void onPKLiveRoomFPSuccess(boolean z, boolean z2, PKRecordEntity pKRecordEntity);

    void onPKLiveRoomFail();

    void onPersonalGuardInfoFail();

    void onPersonalGuardInfoSuccess(GuardItemEntity guardItemEntity);

    void onQMInteractShowTaskFail();

    void onQMInteractShowTaskSuccess(List<QMInteractTaskEntity> list);

    void onTaskChangeFail(TaskBoxEntity taskBoxEntity);

    void onTaskChangeSuccess(TaskBoxEntity taskBoxEntity);

    void onTaskListFail();

    void onTaskListSuccess(List<TaskBoxEntity> list, boolean z);

    void onTaskTakeFail();

    void onTaskTakeSuccess(TaskBoxEntity taskBoxEntity);

    void onTrumpetSendFail(int i);

    void onTrumpetSendSuccess();

    void onTrumpetStatusFail();

    void onTrumpetStatusSuccess(TrumpetStatusEntity trumpetStatusEntity);

    void onUseFragmentSuccess(PropConfigEntity propConfigEntity);

    void onUserCardInfoFail(int i, String str);

    void onUserCardInfoSuccess(UserEntity userEntity);

    void onUserCheckTicketFail(int i, String str);

    void onUserCheckTicketSuccess(CheckTicketEntity checkTicketEntity);

    void onUserOverFail();

    void onUserOverSuccess(MyAccountEntity myAccountEntity);

    void onWebSocketAddressFail();

    void onWebSocketAddressSuccess(LiveEntity liveEntity);
}
