package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.WeekStarRankingEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IWeekStarRankingView */
/* loaded from: classes3.dex */
public interface IWeekStarRankingView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<WeekStarRankingEntity> list, boolean z);
}
