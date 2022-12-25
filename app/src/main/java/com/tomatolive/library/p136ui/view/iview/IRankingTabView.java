package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.LabelEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IRankingTabView */
/* loaded from: classes3.dex */
public interface IRankingTabView extends BaseView {
    void onRankConfigFail();

    void onRankConfigSuccess(List<LabelEntity> list);
}
