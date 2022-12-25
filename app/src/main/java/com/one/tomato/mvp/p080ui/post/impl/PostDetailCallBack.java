package com.one.tomato.mvp.p080ui.post.impl;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.view.NewDetailViewPagerRecyclerFragment;
import com.one.tomato.widget.image.MNGestureView;

/* compiled from: PostDetailCallBack.kt */
/* renamed from: com.one.tomato.mvp.ui.post.impl.PostDetailCallBack */
/* loaded from: classes3.dex */
public interface PostDetailCallBack {
    void callCollect(PostList postList);

    boolean callCurrentIndex(NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment);

    void callFragmentToActVideoMenu(PostList postList);

    void callJumpAd(PostList postList);

    void callPostItemPayCom(PostList postList);

    PostEvenOneTabVideoListManger callPostVideoManger();

    long callPostVideoPostion();

    void callTumb(PostList postList);

    MNGestureView getMNView();

    void postAttetion(PostList postList, int i);
}
