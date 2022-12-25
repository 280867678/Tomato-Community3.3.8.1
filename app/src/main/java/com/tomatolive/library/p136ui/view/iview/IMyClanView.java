package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.MyClanEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IMyClanView */
/* loaded from: classes3.dex */
public interface IMyClanView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<MyClanEntity> list, boolean z, boolean z2);
}
