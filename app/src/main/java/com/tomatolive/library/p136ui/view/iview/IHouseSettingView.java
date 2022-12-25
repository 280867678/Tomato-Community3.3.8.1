package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.BannedEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IHouseSettingView */
/* loaded from: classes3.dex */
public interface IHouseSettingView extends BaseView {
    void onDataListSuccess(int i, List<BannedEntity> list, boolean z, boolean z2);

    void onHouseSettingSuccess(int i, BannedEntity bannedEntity);

    void onSearchDataListSuccess(List<BannedEntity> list);
}
