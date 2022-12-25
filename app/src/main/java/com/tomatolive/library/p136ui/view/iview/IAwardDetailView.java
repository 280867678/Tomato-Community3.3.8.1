package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AwardDetailEntity;
import com.tomatolive.library.model.MessageDetailEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IAwardDetailView */
/* loaded from: classes3.dex */
public interface IAwardDetailView extends BaseView {
    void onAddAddressFailure(int i, String str);

    void onAddAddressSuccess(MessageDetailEntity messageDetailEntity);

    void onAddMessageFailure(int i, String str);

    void onAddMessageSuccess(MessageDetailEntity messageDetailEntity);

    void onGetAwardDetailFailure(int i, String str);

    void onGetAwardDetailSuccess(AwardDetailEntity awardDetailEntity);

    void onGetMessageDetailFailure();

    void onGetMessageDetailSuccess(List<MessageDetailEntity> list, boolean z, boolean z2);
}
