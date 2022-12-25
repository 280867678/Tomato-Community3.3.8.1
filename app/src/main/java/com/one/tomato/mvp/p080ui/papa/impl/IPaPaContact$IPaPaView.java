package com.one.tomato.mvp.p080ui.papa.impl;

import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: IPaPaContact.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.impl.IPaPaContact$IPaPaView */
/* loaded from: classes3.dex */
public interface IPaPaContact$IPaPaView extends IBaseView {
    void handlerHotMessageData(ArrayList<PostHotMessageBean> arrayList);

    void handlerPostListData(ArrayList<PostList> arrayList);
}
