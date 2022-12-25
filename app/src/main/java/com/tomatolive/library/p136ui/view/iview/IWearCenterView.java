package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.ChatPreviewEntity;
import com.tomatolive.library.model.MedalEntity;
import java.util.List;
import java.util.Map;

/* renamed from: com.tomatolive.library.ui.view.iview.IWearCenterView */
/* loaded from: classes3.dex */
public interface IWearCenterView extends BaseView {
    void onAllDataFail();

    void onAllDataSuccess(Map<String, Object> map);

    void onCancelWearCenterFail();

    void onCancelWearCenterSuccess(String str);

    void onChatPreviewSuccess(ChatPreviewEntity chatPreviewEntity);

    void onEquipFail(boolean z);

    void onEquipSuccess(String str, boolean z, MedalEntity medalEntity, int i);

    void onMedalDataFail(int i);

    void onMedalDataSuccess(int i, List<MedalEntity> list);

    void onPrefixDataFail(int i);

    void onPrefixDataSuccess(int i, List<MedalEntity> list);
}
