package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.LiveEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.ISearchLiveView */
/* loaded from: classes3.dex */
public interface ISearchLiveView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2);
}
