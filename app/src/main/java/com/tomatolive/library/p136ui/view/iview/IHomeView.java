package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.LabelEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IHomeView */
/* loaded from: classes3.dex */
public interface IHomeView extends BaseView {
    void onTagListFail();

    void onTagListSuccess(List<LabelEntity> list);
}
