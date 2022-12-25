package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AppealHistoryEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IAppealListView */
/* loaded from: classes3.dex */
public interface IAppealListView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<AppealHistoryEntity> list, boolean z, boolean z2);
}
