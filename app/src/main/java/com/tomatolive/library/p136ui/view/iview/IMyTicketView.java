package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.MyTicketEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IMyTicketView */
/* loaded from: classes3.dex */
public interface IMyTicketView extends BaseView {
    void onDataListFail(boolean z);

    void onDataListSuccess(List<MyTicketEntity> list, boolean z, boolean z2);
}
