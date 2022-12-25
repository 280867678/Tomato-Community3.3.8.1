package com.one.tomato.mvp.p080ui.feedback.impl;

import com.one.tomato.entity.FeedbackOrderCheck;
import com.one.tomato.entity.JCOrderRecord;
import com.one.tomato.entity.RechargeOnlineUnCallback;
import com.one.tomato.entity.RechargeProblemOrder;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: IFeedbackOrderContact.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.impl.IFeedbackOrderContact$IFeedbackOrderView */
/* loaded from: classes3.dex */
public interface IFeedbackOrderContact$IFeedbackOrderView extends IBaseView {
    void handleCheckOrder(FeedbackOrderCheck feedbackOrderCheck, RechargeProblemOrder rechargeProblemOrder);

    void handleJC(JCOrderRecord jCOrderRecord);

    void handleOnline(ArrayList<RechargeOnlineUnCallback> arrayList);
}
