package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AwardHistoryEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IAwardListView */
/* loaded from: classes3.dex */
public interface IAwardListView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<AwardHistoryEntity> list, boolean z, boolean z2);
}
