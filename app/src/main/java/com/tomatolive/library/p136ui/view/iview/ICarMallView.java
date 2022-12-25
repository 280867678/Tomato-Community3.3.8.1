package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.CarEntity;
import com.tomatolive.library.model.CarHistoryRecordEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.ICarMallView */
/* loaded from: classes3.dex */
public interface ICarMallView extends BaseView {
    void onBuyCarFail(int i);

    void onBuyCarSuccess(CarEntity carEntity, String str, String str2);

    void onDataListFail(boolean z);

    void onDataListSuccess(List<CarEntity> list, boolean z, boolean z2);

    void onGetCarPurchaseCarouselRecordFail();

    void onGetCarPurchaseCarouselRecordSuccess(List<CarHistoryRecordEntity> list);
}
