package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.ISearchAllView */
/* loaded from: classes3.dex */
public interface ISearchAllView extends BaseView {
    void onAnchorListFail();

    void onAnchorListSuccess(List<AnchorEntity> list);

    void onLiveListSuccess(List<LiveEntity> list, boolean z, boolean z2);
}
