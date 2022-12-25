package com.one.tomato.mvp.p080ui.p082up.impl;

import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: UpContarct.kt */
/* renamed from: com.one.tomato.mvp.ui.up.impl.UpContarct$UpIView */
/* loaded from: classes3.dex */
public interface UpContarct$UpIView extends IBaseView {
    void handlerApplyError();

    void handlerApplyUpSuccess();

    void handlerQueryAchiSucess(UpStatusBean upStatusBean);

    void handlerQueryUpStatusInfo(UpStatusBean upStatusBean);

    void handlerRankList(ArrayList<UpRankListBean> arrayList);
}
