package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.LiveEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IHomeSortView */
/* loaded from: classes3.dex */
public interface IHomeSortView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2);
}
