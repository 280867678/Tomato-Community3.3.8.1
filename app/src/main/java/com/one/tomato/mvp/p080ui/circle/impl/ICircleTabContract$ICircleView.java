package com.one.tomato.mvp.p080ui.circle.impl;

import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: ICircleTabContract.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.impl.ICircleTabContract$ICircleView */
/* loaded from: classes3.dex */
public interface ICircleTabContract$ICircleView extends IBaseView {
    void handlerCircleDiscover(ArrayList<CircleDiscoverListBean> arrayList);

    void handlerCircleFllowSuccess(int i);
}
