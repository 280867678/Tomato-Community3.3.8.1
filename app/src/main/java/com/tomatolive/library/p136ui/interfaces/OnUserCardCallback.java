package com.tomatolive.library.p136ui.interfaces;

import android.view.View;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.UserEntity;

/* renamed from: com.tomatolive.library.ui.interfaces.OnUserCardCallback */
/* loaded from: classes3.dex */
public interface OnUserCardCallback {
    void onAnchorItemClickListener(AnchorEntity anchorEntity);

    void onAttentionAnchorListener(View view, AnchorEntity anchorEntity);

    void onClickAttentionListener(View view);

    void onClickGuardListener();

    void onClickHomepageListener(View view);

    void onClickManageListener(View view);

    void onClickNobilityListener(View view);

    void onGiftWallClickListener(AnchorEntity anchorEntity);

    void onUserAchieveListener(UserEntity userEntity, String str);

    void onUserItemClickListener(UserEntity userEntity);
}
