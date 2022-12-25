package com.tomatolive.library.p136ui.interfaces;

import android.view.View;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.UserEntity;

/* renamed from: com.tomatolive.library.ui.interfaces.OnLivePusherInfoCallback */
/* loaded from: classes3.dex */
public interface OnLivePusherInfoCallback {
    void onClickAdBannerListener(BannerEntity bannerEntity);

    void onClickAnchorAvatarListener(View view);

    void onClickAnchorInfoNoticeListener(View view);

    void onClickAudienceListener(View view);

    void onClickGameNoticeListener(View view);

    void onClickGiftNoticeListener(View view);

    void onClickGuardListener(GuardItemEntity guardItemEntity);

    void onClickLuckNoticeListener(View view);

    void onClickSysNoticeListener(View view);

    void onClickUserAvatarListener(UserEntity userEntity);

    void onFollowAnchorListener(View view);

    void onNobilityOpenListener();
}
