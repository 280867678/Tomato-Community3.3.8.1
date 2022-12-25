package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IRankingView */
/* loaded from: classes3.dex */
public interface IRankingView extends BaseView {
    void onAttentionSuccess();

    void onCharmTopListSuccess(List<AnchorEntity> list, int i, boolean z);

    void onRankConfigFail();

    void onRankConfigSuccess(List<String> list);

    void onStrengthTopListSuccess(List<AnchorEntity> list, int i, boolean z);
}
