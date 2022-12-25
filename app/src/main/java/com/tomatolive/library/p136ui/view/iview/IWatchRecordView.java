package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.LiveEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IWatchRecordView */
/* loaded from: classes3.dex */
public interface IWatchRecordView extends BaseView {
    void onDataListFail();

    void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2);

    void onDeleteAllSuccess();

    void onDeleteSuccess(int i);
}
