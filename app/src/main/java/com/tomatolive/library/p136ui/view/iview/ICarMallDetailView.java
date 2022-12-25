package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;

/* renamed from: com.tomatolive.library.ui.view.iview.ICarMallDetailView */
/* loaded from: classes3.dex */
public interface ICarMallDetailView extends BaseView {
    void onBuyCarFail(int i);

    void onBuyCarSuccess(String str, String str2);

    void onUserOverFail();

    void onUserOverSuccess(String str);
}
