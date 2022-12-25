package com.one.tomato.mvp.p080ui.post.impl;

import com.one.tomato.entity.CommentList;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: IPostCommentContact.kt */
/* renamed from: com.one.tomato.mvp.ui.post.impl.IPostCommentContact$IPostCommentView */
/* loaded from: classes3.dex */
public interface IPostCommentContact$IPostCommentView extends IBaseView {
    void handlerAddTagError();

    void handlerAddTagSuccess(Tag tag);

    void handlerDelete(int i);

    void handlerDeleteTagSuccess(int i);

    void handlerPostCommentList(ArrayList<CommentList> arrayList);

    void handlerPostSingleDetail(PostList postList);

    void handlerReplyComment(CommentList commentList, int i);

    void handlerReplyCommentError(ResponseThrowable responseThrowable, int i);

    void handlerSendCommentError(int i);

    void handlerSendCommentSucss(CommentList commentList);

    void handlerSendFisrtComment(String str, String str2);
}
