package com.one.tomato.mvp.p080ui.p082up.impl;

import com.one.tomato.entity.RechargeList;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: RewardContact.kt */
/* renamed from: com.one.tomato.mvp.ui.up.impl.RewardContact$IRewardView */
/* loaded from: classes3.dex */
public interface RewardContact$IRewardView extends IBaseView {
    void handlerRechargeList(ArrayList<RechargeList> arrayList);

    void handlerRewardPayError();

    void handlerRewardPayOk(String str);
}
