package com.one.tomato.mvp.p080ui.papa.impl;

import com.one.tomato.entity.PostList;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* renamed from: com.one.tomato.mvp.ui.papa.impl.IPaPaPlayContact$IPaPaPlayView */
/* loaded from: classes3.dex */
public interface IPaPaPlayContact extends IBaseView {
    void handlerAddTagError(int i);

    void handlerAddTagSuccess(Tag tag, int i);

    void handlerCollect(int i);

    void handlerDownCheck(VideoPay videoPay, boolean z);

    void handlerFoucs(int i);

    void handlerPostListData(ArrayList<PostList> arrayList);
}
