package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IWeekStarRankingAnchorView */
/* loaded from: classes3.dex */
public interface IWeekStarRankingAnchorView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<WeekStarAnchorEntity> list, boolean z);

    void onUserRankingFail();

    void onUserRankingSuccess(WeekStarAnchorEntity weekStarAnchorEntity);
}
