package com.tomatolive.library.p136ui.interfaces;

import com.tomatolive.library.model.GiftDownloadItemEntity;

/* renamed from: com.tomatolive.library.ui.interfaces.OnQMInteractCallback */
/* loaded from: classes3.dex */
public interface OnQMInteractCallback {
    void onBackQMInteractConfigListener();

    void onNoticeAnimViewDismissListener();

    void onSendGiftListener(GiftDownloadItemEntity giftDownloadItemEntity);

    void onSendInvitationListener(String str, String str2, String str3, String str4);

    void onTaskStatusUpdateListener(String str, String str2);

    void onUserCardListener(String str);
}
