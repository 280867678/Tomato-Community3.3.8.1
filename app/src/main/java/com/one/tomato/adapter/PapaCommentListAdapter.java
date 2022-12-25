package com.one.tomato.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.dialog.PapaCommentDialog;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.VideoCommentList;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes3.dex */
public class PapaCommentListAdapter extends BaseRecyclerViewAdapter<VideoCommentList> {
    private Context context;
    private PapaCommentDialog papaCommentDialog;
    private PostList postList;

    public void setPostList(PostList postList) {
        this.postList = postList;
    }

    public PapaCommentListAdapter(PapaCommentDialog papaCommentDialog, int i, RecyclerView recyclerView) {
        super(papaCommentDialog.getContext(), i, recyclerView);
        this.context = papaCommentDialog.getContext();
        this.papaCommentDialog = papaCommentDialog;
        setPreLoadNumber(5);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, VideoCommentList videoCommentList) {
        TextView textView;
        TextView textView2;
        int i;
        TextView textView3;
        super.convert(baseViewHolder, (BaseViewHolder) videoCommentList);
        int indexOf = this.mData.indexOf(videoCommentList);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_root);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_thumb);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_thumb);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_thumb_num);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_head);
        LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_line);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_name);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.tv_content);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.rl_change_layout);
        TextView textView7 = (TextView) baseViewHolder.getView(R.id.tv_time);
        TextView textView8 = (TextView) baseViewHolder.getView(R.id.tv_more_reply);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) baseViewHolder.getView(R.id.iv_loading);
        if (videoCommentList.getIsVisibleSelf() == 1 && DBUtil.getMemberId() != videoCommentList.getMemberId()) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
        }
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_post_member_level_nick);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.image_vip);
        if (this.postList != null) {
            textView2 = textView5;
            textView = textView8;
            UserPermissionUtil.getInstance().userLevelNickShow(imageView3, new LevelBean(this.postList.getCurrentLevelIndex()));
        } else {
            textView = textView8;
            textView2 = textView5;
        }
        if (videoCommentList.getVipType() > 0) {
            imageView4.setVisibility(0);
        } else {
            imageView4.setVisibility(8);
        }
        baseViewHolder.addOnClickListener(R.id.iv_head).addOnClickListener(R.id.ll_thumb).addOnClickListener(R.id.tv_more_reply);
        if (1 == videoCommentList.getIsThumbsUp()) {
            imageView.setImageResource(R.drawable.video_comment_favor_s);
        } else {
            imageView.setImageResource(R.drawable.video_comment_favor_n);
        }
        textView4.setText(FormatUtil.formatNumOverTenThousand(videoCommentList.getGoodNum() + ""));
        linearLayout.setPadding(0, 0, 0, 0);
        if (videoCommentList.isReply()) {
            textView6.setPadding(0, 0, 0, 30);
            linearLayout2.setVisibility(4);
            imageView2.setVisibility(4);
            linearLayout3.setVisibility(0);
            relativeLayout.setVisibility(8);
            cancelAnimation(lottieAnimationView);
            int replyStatus = videoCommentList.getReplyStatus();
            if (replyStatus == 1) {
                TextView textView9 = textView;
                relativeLayout.setVisibility(0);
                textView9.setVisibility(0);
                textView9.setText(AppUtil.getString(R.string.video_comment_reply_more) + "(" + videoCommentList.getReplyNum() + ")");
                lottieAnimationView.setVisibility(8);
            } else if (replyStatus == 2) {
                TextView textView10 = textView;
                relativeLayout.setVisibility(0);
                textView10.setVisibility(0);
                textView10.setText(AppUtil.getString(R.string.video_comment_reply_more_check));
                lottieAnimationView.setVisibility(8);
            } else if (replyStatus == 3) {
                relativeLayout.setVisibility(0);
                textView.setVisibility(8);
                lottieAnimationView.setVisibility(0);
                showAnimation(lottieAnimationView);
            } else if (replyStatus == 4) {
                relativeLayout.setVisibility(8);
                textView.setVisibility(8);
                lottieAnimationView.setVisibility(8);
            } else {
                relativeLayout.setVisibility(8);
                textView.setVisibility(8);
                lottieAnimationView.setVisibility(8);
            }
            if (videoCommentList.getToMemberId() == videoCommentList.getParentMemberId()) {
                textView2.setText(videoCommentList.getName());
            } else {
                ViewUtil.initTextViewWithSpannableString(textView2, new String[]{videoCommentList.getName(), AppUtil.getString(R.string.video_comment_reply) + ConstantUtils.PLACEHOLDER_STR_ONE, videoCommentList.getToMemberName()}, new String[]{String.valueOf(this.context.getResources().getColor(R.color.text_light)), String.valueOf(this.context.getResources().getColor(R.color.withdraw_default)), String.valueOf(this.context.getResources().getColor(R.color.text_light))}, new String[]{"12", "12", "12"});
            }
            textView6.setTextSize(2, 12.0f);
            textView3 = textView7;
        } else {
            TextView textView11 = textView2;
            TextView textView12 = textView;
            if (indexOf > 0) {
                i = 0;
                linearLayout.setPadding(0, 80, 0, 0);
            } else {
                i = 0;
            }
            textView6.setPadding(i, i, i, 15);
            linearLayout2.setVisibility(i);
            imageView2.setVisibility(i);
            linearLayout3.setVisibility(8);
            relativeLayout.setVisibility(i);
            textView3 = textView7;
            textView3.setVisibility(i);
            textView3.setPadding(i, i, i, 30);
            textView12.setVisibility(8);
            lottieAnimationView.setVisibility(8);
            cancelAnimation(lottieAnimationView);
            textView11.setText(videoCommentList.getName());
            textView6.setTextSize(2, 14.0f);
        }
        ImageLoaderUtil.loadHeadImage(this.mContext, imageView2, new ImageBean(videoCommentList.getAvatar()));
        EmotionUtil.spannableEmoticonFilter(textView6, videoCommentList.getContent());
        textView3.setText(videoCommentList.getCommentTime());
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        VideoCommentList videoCommentList = (VideoCommentList) this.mData.get(i);
        int id = view.getId();
        if (id == R.id.iv_head) {
            NewMyHomePageActivity.Companion.startActivity(this.context, videoCommentList.getMemberId());
        } else if (id == R.id.ll_thumb) {
            this.papaCommentDialog.thumb(i, videoCommentList);
        } else if (id != R.id.tv_more_reply) {
        } else {
            VideoCommentList videoCommentList2 = new VideoCommentList(videoCommentList.getCommentId());
            this.papaCommentDialog.getReplyListFromServer(videoCommentList.getCommentId(), videoCommentList.getParentMemberId(), i, (i - this.mData.indexOf(videoCommentList2)) - 1);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        VideoCommentList videoCommentList = (VideoCommentList) this.mData.get(i);
        if (videoCommentList.getMemberId() == DBUtil.getMemberId()) {
            ToastUtil.showCenterToast((int) R.string.video_comment_for_self);
        } else {
            this.papaCommentDialog.setClickCommentList(videoCommentList);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemLongClick(baseQuickAdapter, view, i);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        this.papaCommentDialog.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.papaCommentDialog.loadMore();
    }

    public void cancelAnimation(LottieAnimationView lottieAnimationView) {
        if (lottieAnimationView.getAnimation() == null || !lottieAnimationView.isAnimating()) {
            return;
        }
        lottieAnimationView.cancelAnimation();
    }

    public void showAnimation(LottieAnimationView lottieAnimationView) {
        if (lottieAnimationView.getAnimation() != null && lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }
        lottieAnimationView.setAnimation("loading_more.json");
    }
}
