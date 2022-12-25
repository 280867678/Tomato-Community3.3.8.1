package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.MyCarEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IMyCarView */
/* loaded from: classes3.dex */
public interface IMyCarView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<MyCarEntity> list, boolean z, boolean z2);

    void onUseCarFail();

    void onUseCarSuccess(MyCarEntity myCarEntity);
}
