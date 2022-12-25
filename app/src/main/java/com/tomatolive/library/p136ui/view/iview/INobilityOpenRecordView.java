package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.NobilityOpenRecordEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.INobilityOpenRecordView */
/* loaded from: classes3.dex */
public interface INobilityOpenRecordView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<NobilityOpenRecordEntity> list, boolean z, boolean z2);
}
