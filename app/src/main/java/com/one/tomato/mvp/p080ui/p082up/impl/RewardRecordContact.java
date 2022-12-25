package com.one.tomato.mvp.p080ui.p082up.impl;

import com.one.tomato.entity.RewardRecordBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* renamed from: com.one.tomato.mvp.ui.up.impl.RewardRecordContact$IRewardRecordView */
/* loaded from: classes3.dex */
public interface RewardRecordContact extends IBaseView {
    void handleListTotalCount(int i);

    void handlerPayList(ArrayList<RewardRecordBean> arrayList);
}
