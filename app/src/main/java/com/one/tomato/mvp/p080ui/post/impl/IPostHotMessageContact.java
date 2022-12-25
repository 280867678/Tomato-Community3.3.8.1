package com.one.tomato.mvp.p080ui.post.impl;

import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* renamed from: com.one.tomato.mvp.ui.post.impl.IPostHotMessageContact$IPostHotMessageView */
/* loaded from: classes3.dex */
public interface IPostHotMessageContact extends IBaseView {
    void handleHotMessage(ArrayList<PostHotMessageBean> arrayList);
}
