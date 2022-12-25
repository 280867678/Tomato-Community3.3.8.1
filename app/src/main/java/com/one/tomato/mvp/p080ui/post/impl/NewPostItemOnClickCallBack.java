package com.one.tomato.mvp.p080ui.post.impl;

import android.view.View;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.Tag;

/* compiled from: NewPostItemOnClickCallBack.kt */
/* renamed from: com.one.tomato.mvp.ui.post.impl.NewPostItemOnClickCallBack */
/* loaded from: classes3.dex */
public interface NewPostItemOnClickCallBack {
    void callVideoDialog(PostList postList);

    void itemADClick(PostList postList);

    void itemCircle(PostList postList);

    void itemClick(PostList postList, View view, int i);

    boolean itemClickZan(PostList postList, int i);

    void itemConmment(PostList postList, int i, View view);

    void itemDelete(PostList postList, int i, View view);

    void itemHead(PostList postList);

    void itemNeedPay(PostList postList);

    void itemPostCollect(PostList postList);

    void itemPostFoucs(PostList postList);

    void itemPostPayComplete(PostList postList);

    void itemShare(PostList postList);

    void itemShowAuthInfo(PostList postList, int i);

    void itemTagDelete(Tag tag, int i, int i2, int i3);

    void jumpPostDetailActivity(int i);
}
