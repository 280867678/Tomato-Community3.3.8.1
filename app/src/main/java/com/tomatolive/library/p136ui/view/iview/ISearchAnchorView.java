package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.ISearchAnchorView */
/* loaded from: classes3.dex */
public interface ISearchAnchorView extends BaseView {
    void onAttentionSuccess();

    void onDataListFail(boolean z);

    void onDataListSuccess(List<AnchorEntity> list, boolean z, boolean z2);
}
