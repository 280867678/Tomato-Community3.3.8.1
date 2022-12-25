package com.tomatolive.library.p136ui.interfaces;

import android.view.View;
import com.tomatolive.library.model.RelationLastLiveEntity;

/* renamed from: com.tomatolive.library.ui.interfaces.OnPayLiveCallback */
/* loaded from: classes3.dex */
public interface OnPayLiveCallback {
    void onPayCancelListener();

    void onPayEnterClickListener(View view);

    void onPayExitClickListener();

    void onPayLiveInfoSubmit(String str, String str2, String str3, String str4, RelationLastLiveEntity relationLastLiveEntity);
}
