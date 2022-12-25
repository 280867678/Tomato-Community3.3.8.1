package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.IndexRankEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IHomeHotView */
/* loaded from: classes3.dex */
public interface IHomeHotView extends BaseView {
    void onAnchorAuthSuccess(AnchorEntity anchorEntity);

    void onBannerListSuccess(List<BannerEntity> list);

    void onDataListFail(boolean z);

    void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2);

    void onLiveHelperAppConfigFail();

    void onLiveHelperAppConfigSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity);

    void onTopListSuccess(List<IndexRankEntity> list);
}
