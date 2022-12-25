package com.one.tomato.mvp.p080ui.post.impl;

import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: IPostTabListContact.kt */
/* renamed from: com.one.tomato.mvp.ui.post.impl.IPostTabListContact$IPostTabListView */
/* loaded from: classes3.dex */
public interface IPostTabListContact$IPostTabListView extends IBaseView {
    void HandlerPostMainNotify(ArrayList<MainNotifyBean> arrayList);

    void handlerDeleteTagSuccess(int i, int i2);

    void handlerInsertAd(Map<String, Object> map);

    void handlerPostListData(ArrayList<PostList> arrayList);

    void handlerRemoveItem(int i);
}
