package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IHomeAttentionView */
/* loaded from: classes3.dex */
public interface IHomeAttentionView extends BaseView {
    void onAttentionListFail(boolean z);

    void onAttentionListSuccess(List<LiveEntity> list, boolean z, boolean z2);

    void onAttentionSuccess();

    void onRecommendListFail();

    void onRecommendListSuccess(List<AnchorEntity> list);
}
