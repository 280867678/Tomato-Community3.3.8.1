package com.one.tomato.adapter;

import android.app.Activity;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.mvp.p080ui.post.view.PostCommentReplyActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* loaded from: classes3.dex */
public class PostDetailImgUploadAdapter extends BaseRecyclerViewAdapter<String> {
    private Activity activity;

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
    }

    public PostDetailImgUploadAdapter(Activity activity, int i, RecyclerView recyclerView) {
        super(activity, i, recyclerView);
        this.activity = activity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, String str) {
        super.convert(baseViewHolder, (BaseViewHolder) str);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_pic);
        baseViewHolder.addOnClickListener(R.id.iv_delete);
        ImageLoaderUtil.loadNormalLocalImage(this.activity, imageView, str, ImageLoaderUtil.getCenterInsideImageOption(imageView));
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Activity activity = this.activity;
        if (activity instanceof PostCommentReplyActivity) {
            ((PostCommentReplyActivity) activity).startImgShowActivity(i);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        if (view.getId() != R.id.iv_delete) {
            return;
        }
        Activity activity = this.activity;
        if (!(activity instanceof PostCommentReplyActivity)) {
            return;
        }
        ((PostCommentReplyActivity) activity).removeLocalImg(i);
    }
}
