package com.tomatolive.library.p136ui.interfaces;

import com.tomatolive.library.model.GiftDownloadItemEntity;

/* renamed from: com.tomatolive.library.ui.interfaces.OnHdLotteryCallback */
/* loaded from: classes3.dex */
public interface OnHdLotteryCallback {
    void onBackLotteryVerifyListener();

    void onFloatingWindowClickListener();

    void onFloatingWindowCloseListener();

    void onJoinLotteryListener(GiftDownloadItemEntity giftDownloadItemEntity, String str);

    void onLotteryEndListener();

    void onOpenLotteryListener();

    void onOpenLotterySuccess(String str, String str2, String str3);

    void onStartLotteryVerifySuccess(String str);
}
