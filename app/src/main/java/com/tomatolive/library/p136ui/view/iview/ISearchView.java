package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.LiveEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.ISearchView */
/* loaded from: classes3.dex */
public interface ISearchView extends BaseView {
    void onAutoKeyListSuccess(String str, List<LabelEntity> list);

    void onHotKeyListSuccess(List<LabelEntity> list);

    void onLiveListSuccess(List<LiveEntity> list);
}
