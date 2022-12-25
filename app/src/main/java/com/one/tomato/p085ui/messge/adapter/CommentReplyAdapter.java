package com.one.tomato.p085ui.messge.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import com.one.tomato.p085ui.messge.p086ui.CommentResponseActivity;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.DateUtils;
import java.util.Date;

/* renamed from: com.one.tomato.ui.messge.adapter.CommentReplyAdapter */
/* loaded from: classes3.dex */
public class CommentReplyAdapter extends BaseRecyclerViewAdapter<PostReviewBean.DataBean> {
    private CommentResponseActivity activity;

    public CommentReplyAdapter(Context context, int i, RecyclerView recyclerView, CommentResponseActivity commentResponseActivity) {
        super(context, R.layout.item_comment_reply, recyclerView);
        this.activity = commentResponseActivity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostReviewBean.DataBean dataBean) {
        super.convert(baseViewHolder, (BaseViewHolder) dataBean);
        TextView textView = (TextView) baseViewHolder.getView(R.id.text_response);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.text_response_content);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.text_my_response);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.relate_content);
        RelativeLayout relativeLayout2 = (RelativeLayout) baseViewHolder.getView(R.id.relate);
        RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.getView(R.id.image_head);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.image_show_video);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.text_my_response_head);
        textView3.setVisibility(0);
        textView4.setVisibility(0);
        imageView.setVisibility(8);
        ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) baseViewHolder.getView(R.id.iv_head), new ImageBean(dataBean.getFromMemberAvatar()));
        ((TextView) baseViewHolder.getView(R.id.text_time)).setText(FormatUtil.formatTime(FormatUtil.formatTimeToDate(DateUtils.C_TIME_PATTON_DEFAULT, dataBean.getCreateTime()), new Date()));
        ((TextView) baseViewHolder.getView(R.id.text_name)).setText(dataBean.getFromMemberName());
        ((TextView) baseViewHolder.getView(R.id.text_content_duanwen)).setText(dataBean.getArticleDes());
        switch (dataBean.getMsgType()) {
            case 601:
                textView.setText(AppUtil.getString(R.string.string_other_comment));
                EmotionUtil.spannableEmoticonFilter(textView2, dataBean.getOtherSideComment());
                textView3.setVisibility(8);
                textView4.setVisibility(8);
                break;
            case 602:
                textView4.setText(this.mContext.getResources().getString(R.string.string_my_comment));
                EmotionUtil.spannableEmoticonFilter(textView3, dataBean.getMyComment());
                textView.setText(AppUtil.getString(R.string.string_other_reply_comment));
                EmotionUtil.spannableEmoticonFilter(textView2, dataBean.getOtherSideReply());
                break;
            case 603:
                textView4.setText(this.mContext.getResources().getString(R.string.string_my_reply));
                EmotionUtil.spannableEmoticonFilter(textView3, dataBean.getMyReply());
                textView.setText(AppUtil.getString(R.string.string_other_reply_my_reply));
                EmotionUtil.spannableEmoticonFilter(textView2, dataBean.getOtherSideReply());
                break;
        }
        int articleType = dataBean.getArticleType();
        if (articleType == 1) {
            ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(dataBean.getArticleCover()));
        } else if (articleType == 2) {
            imageView.setVisibility(0);
            ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(dataBean.getArticleCover()));
        } else if (articleType != 3) {
        } else {
            roundedImageView.setImageResource(R.drawable.icon_post_review_duanwe);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int articleId;
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        PostReviewBean.DataBean dataBean = getData().get(i);
        if (dataBean == null || (articleId = dataBean.getArticleId()) == 0) {
            return;
        }
        NewPostDetailViewPagerActivity.Companion.startActivity(this.mContext, articleId, true, false, false);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        this.activity.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.activity.loadMore();
    }
}
