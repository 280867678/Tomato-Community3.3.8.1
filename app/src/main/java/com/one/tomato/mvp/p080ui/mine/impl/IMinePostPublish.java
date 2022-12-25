package com.one.tomato.mvp.p080ui.mine.impl;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* renamed from: com.one.tomato.mvp.ui.mine.impl.IMinePostPublish$IMinePublishView */
/* loaded from: classes3.dex */
public interface IMinePostPublish extends IBaseView {
    void handlerPostList(ArrayList<PostList> arrayList);

    void handlerRemoveItem();
}
