package com.one.tomato.mvp.p080ui.circle.impl;

import com.one.tomato.entity.CircleCategory;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: ICircleAllContact.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.impl.ICircleAllContact$ICircleAllView */
/* loaded from: classes3.dex */
public interface ICircleAllContact$ICircleAllView extends IBaseView {
    void handlerCategoryCircleAllError();

    void handlerCategoryCircleAllSucess(ArrayList<CircleCategory> arrayList);

    void handlerCircleAll(ArrayList<CircleAllBean> arrayList);

    void handlerCircleFllowSuccess(int i);
}
