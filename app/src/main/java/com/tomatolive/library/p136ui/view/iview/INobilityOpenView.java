package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.NobilityEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.INobilityOpenView */
/* loaded from: classes3.dex */
public interface INobilityOpenView extends BaseView {
    void onInitDataFail();

    void onInitDataSuccess(List<NobilityEntity> list);
}
