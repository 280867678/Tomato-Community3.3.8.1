package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.BannedEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IBannedView */
/* loaded from: classes3.dex */
public interface IBannedView extends BaseView {
    void onBannedSettingSuccess(int i, BannedEntity bannedEntity);

    void onDataListSuccess(int i, List<BannedEntity> list, boolean z, boolean z2);

    void onSearchDataListSuccess(List<BannedEntity> list);
}
