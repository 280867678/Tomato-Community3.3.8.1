package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.NobilityRecommendHistoryEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.INobilityRecommendHistoryView */
/* loaded from: classes3.dex */
public interface INobilityRecommendHistoryView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<NobilityRecommendHistoryEntity> list, boolean z, boolean z2);
}
