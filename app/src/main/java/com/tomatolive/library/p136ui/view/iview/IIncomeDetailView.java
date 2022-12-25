package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.IncomeEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IIncomeDetailView */
/* loaded from: classes3.dex */
public interface IIncomeDetailView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<? extends IncomeEntity> list, boolean z, boolean z2, String str);
}
