package com.one.tomato.mvp.p080ui.post.impl;

import com.one.tomato.entity.CommentList;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.Tag;

/* renamed from: com.one.tomato.mvp.ui.post.impl.IPostCommentContact$IAdapterItemToViewCallBack */
/* loaded from: classes3.dex */
public interface IPostCommentContact {
    void callDeleteComment(int i);

    void callFoucs();

    void callLouZhu(int i);

    void callRewardFinsh(PostList postList);

    void callSortType(String str);

    void itemClickRefreshPost(PostList postList);

    void itemTagDelete(Tag tag, int i, int i2);

    void postCommentTumb(boolean z, CommentList commentList);

    void postRefresh();

    void requestAuthorCommentDelete(int i, int i2, int i3, int i4);

    void requestCommentDelete(String str, String str2, int i);

    void sendRetryComment(CommentList commentList, int i, String str);
}
