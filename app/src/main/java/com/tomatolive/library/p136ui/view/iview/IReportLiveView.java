package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.ReportTypeEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IReportLiveView */
/* loaded from: classes3.dex */
public interface IReportLiveView extends BaseView {
    void onReportSuccess();

    void onReportTypeListSuccess(List<ReportTypeEntity> list);
}
