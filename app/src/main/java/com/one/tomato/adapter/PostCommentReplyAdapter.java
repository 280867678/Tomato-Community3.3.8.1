package com.one.tomato.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.dialog.CommentActionDialog;
import com.one.tomato.entity.CommentReplyList;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.ReplysVoListBean;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.view.PostCommentReplyActivity;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PostCommentReplyAdapter extends BaseRecyclerViewAdapter<ReplysVoListBean> {
    private PostCommentReplyActivity activity;
    private CommentActionDialog commentActionDialog;
    private CommentReplyList commentReplyList;
    private int postMemberId;

    public PostCommentReplyAdapter(Context context, RecyclerView recyclerView, int i) {
        super(context, R.layout.item_post_comment_list, recyclerView);
        this.activity = (PostCommentReplyActivity) context;
        this.postMemberId = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ReplysVoListBean replysVoListBean) {
        String str;
        String str2;
        int i;
        int i2;
        int i3;
        String str3;
        String str4;
        super.convert(baseViewHolder, (BaseViewHolder) replysVoListBean);
        int indexOf = this.mData.indexOf(replysVoListBean);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_content_data);
        linearLayout.setVisibility(0);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_content);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_head);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_name);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_comment_level_nick);
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_comment_vip);
        LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_thumb);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_comment_more);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_landlord);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_time);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_send_ing);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_send_fail);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.tv_send_retry);
        TextView textView7 = (TextView) baseViewHolder.getView(R.id.tv_send_delete);
        TextView textView8 = (TextView) baseViewHolder.getView(R.id.tv_content);
        NoScrollGridView noScrollGridView = (NoScrollGridView) baseViewHolder.getView(R.id.gridView);
        TextView textView9 = (TextView) baseViewHolder.getView(R.id.text_reply_other);
        baseViewHolder.addOnClickListener(R.id.iv_head).addOnClickListener(R.id.tv_name).addOnClickListener(R.id.iv_comment_more).addOnClickListener(R.id.tv_content).addOnClickListener(R.id.tv_send_retry).addOnClickListener(R.id.tv_send_delete);
        if (replysVoListBean.getIsVisibleSelf() == 1 && DBUtil.getMemberId() != replysVoListBean.getMemberId()) {
            linearLayout.setVisibility(8);
            return;
        }
        if (indexOf == 0) {
            linearLayout.setBackgroundColor(this.mContext.getResources().getColor(R.color.colorPrimary));
            linearLayout3.setVisibility(8);
            imageView4.setVisibility(8);
            if (this.commentReplyList.getIsLouzhu() == 1) {
                textView2.setVisibility(0);
                if ("1".equals(this.commentReplyList.getSex())) {
                    textView2.setBackgroundResource(R.drawable.shape_post_detail_landlord_man);
                } else {
                    textView2.setBackgroundResource(R.drawable.shape_post_detail_landlord_woman);
                }
            } else {
                textView2.setVisibility(8);
            }
            linearLayout2.setVisibility(0);
            textView8.setVisibility(0);
            EmotionUtil.spannableEmoticonFilter(textView8, this.commentReplyList.getContent());
            str3 = this.commentReplyList.getAvatar();
            str4 = this.commentReplyList.getMemberName();
            i2 = this.commentReplyList.getCurrentLevelIndex();
            i3 = this.commentReplyList.getVipType();
            String commentTime = this.commentReplyList.getCommentTime();
            this.commentReplyList.getImageUrl();
            textView3.setVisibility(0);
            textView3.setText(commentTime);
            textView4.setVisibility(8);
            textView5.setVisibility(8);
            textView6.setVisibility(8);
            textView7.setVisibility(8);
            noScrollGridView.setVisibility(8);
        } else {
            linearLayout2.setVisibility(0);
            linearLayout.setBackgroundColor(this.mContext.getResources().getColor(R.color.app_bg_grey));
            linearLayout3.setVisibility(8);
            imageView4.setVisibility(0);
            if (!TextUtils.isEmpty(replysVoListBean.getFromUserMsg()) && !TextUtils.isEmpty(replysVoListBean.getToMemberName())) {
                textView9.setVisibility(0);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(replysVoListBean.getToMemberName() + ": " + replysVoListBean.getFromUserMsg());
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R.color.color_5B92E1));
                spannableStringBuilder.setSpan(foregroundColorSpan, 0, (replysVoListBean.getToMemberName() + ":").length(), 18);
                textView9.setText(spannableStringBuilder);
            } else {
                textView9.setVisibility(8);
            }
            this.activity.setReply(this.commentReplyList, replysVoListBean, textView8);
            String fromMemberAvatar = replysVoListBean.getFromMemberAvatar();
            String fromMemberName = replysVoListBean.getFromMemberName();
            int currentLevelIndex = replysVoListBean.getCurrentLevelIndex();
            int vipType = replysVoListBean.getVipType();
            String createTimeStr = replysVoListBean.getCreateTimeStr();
            String secImageUrl = replysVoListBean.getSecImageUrl();
            if (!TextUtils.isEmpty(secImageUrl) && secImageUrl.split(";").length > 0) {
                noScrollGridView.setVisibility(0);
                final ArrayList arrayList = new ArrayList();
                String[] split = !TextUtils.isEmpty(replysVoListBean.getSecImageUrl()) ? replysVoListBean.getSecImageUrl().split(";") : new String[0];
                str = fromMemberAvatar;
                int i4 = 0;
                while (i4 < split.length) {
                    ImageBean imageBean = new ImageBean();
                    String str5 = fromMemberName;
                    imageBean.setImage(split[i4]);
                    imageBean.setSecret(true);
                    imageBean.setLocal(replysVoListBean.getSendStatus() > 0);
                    arrayList.add(imageBean);
                    i4++;
                    fromMemberName = str5;
                }
                str2 = fromMemberName;
                noScrollGridView.setAdapter((ListAdapter) new ImageGridAdapter(this.mContext, arrayList, R.layout.item_img_grid));
                noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.adapter.PostCommentReplyAdapter.1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i5, long j) {
                        ImageShowActivity.Companion.startActivity(((BaseQuickAdapter) PostCommentReplyAdapter.this).mContext, arrayList, i5);
                    }
                });
                i = 8;
            } else {
                str = fromMemberAvatar;
                str2 = fromMemberName;
                i = 8;
                noScrollGridView.setVisibility(8);
            }
            if (replysVoListBean.getSendStatus() == 1) {
                textView3.setVisibility(i);
                textView4.setVisibility(0);
                textView5.setVisibility(i);
                textView6.setVisibility(i);
                textView7.setVisibility(i);
            } else if (replysVoListBean.getSendStatus() == 2) {
                textView3.setVisibility(0);
                textView3.setText(AppUtil.getString(R.string.post_comment_recent));
                textView4.setVisibility(i);
                textView5.setVisibility(i);
                textView6.setVisibility(i);
                textView7.setVisibility(i);
            } else if (replysVoListBean.getSendStatus() == 3 || replysVoListBean.getSendStatus() == 4) {
                textView3.setVisibility(i);
                textView4.setVisibility(i);
                textView5.setVisibility(0);
                textView6.setVisibility(0);
                textView7.setVisibility(0);
            } else {
                textView3.setVisibility(0);
                textView3.setText(createTimeStr);
                textView4.setVisibility(i);
                textView5.setVisibility(i);
                textView6.setVisibility(i);
                textView7.setVisibility(i);
            }
            i2 = currentLevelIndex;
            i3 = vipType;
            str3 = str;
            str4 = str2;
        }
        ImageLoaderUtil.loadHeadImage(this.activity, imageView, new ImageBean(str3));
        textView.setText(str4);
        UserPermissionUtil.getInstance().userLevelNickShow(imageView2, new LevelBean(i2));
        if (i3 > 0) {
            imageView3.setVisibility(0);
        } else {
            imageView3.setVisibility(8);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        ReplysVoListBean replysVoListBean = (ReplysVoListBean) this.mData.get(i);
        switch (view.getId()) {
            case R.id.iv_comment_more /* 2131297150 */:
                if (replysVoListBean.getSendStatus() != 0 && replysVoListBean.getSendStatus() != 2) {
                    return;
                }
                showReplyActionDialog(i, replysVoListBean);
                return;
            case R.id.tv_content /* 2131298773 */:
                this.activity.onItemContentClick(i, replysVoListBean);
                return;
            case R.id.tv_name /* 2131299145 */:
                if (i == 0) {
                    NewMyHomePageActivity.Companion.startActivity(this.mContext, this.commentReplyList.getMemberId());
                    return;
                } else {
                    NewMyHomePageActivity.Companion.startActivity(this.mContext, replysVoListBean.getFromUserId());
                    return;
                }
            case R.id.tv_send_delete /* 2131299356 */:
                remove(i);
                return;
            case R.id.tv_send_retry /* 2131299361 */:
                this.activity.sendRetryCommentReply(replysVoListBean, i, null);
                return;
            default:
                return;
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildLongClick(baseQuickAdapter, view, i);
        if (i == 0) {
            return;
        }
        showReplyActionDialog(i, (ReplysVoListBean) this.mData.get(i));
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        this.activity.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.activity.loadMore();
    }

    public void setCommentReplyList(CommentReplyList commentReplyList) {
        this.commentReplyList = commentReplyList;
    }

    private void showReplyActionDialog(int i, final ReplysVoListBean replysVoListBean) {
        if (this.commentActionDialog == null) {
            this.commentActionDialog = new CommentActionDialog(this.mContext);
        }
        this.commentActionDialog.show();
        this.commentActionDialog.setCommentList(i, this.postMemberId, this.commentReplyList.getArticleId(), replysVoListBean.getId(), replysVoListBean.getFromUserId(), 3);
        this.commentActionDialog.setCommentDeleteListener(new CommentActionDialog.CommentDeleteListener() { // from class: com.one.tomato.adapter.PostCommentReplyAdapter.2
            @Override // com.one.tomato.dialog.CommentActionDialog.CommentDeleteListener
            public void showCommentDeleteDialog(int i2, int i3, int i4, int i5) {
                if (DBUtil.getMemberId() == replysVoListBean.getFromUserId()) {
                    PostCommentReplyActivity postCommentReplyActivity = PostCommentReplyAdapter.this.activity;
                    postCommentReplyActivity.requestPostDelete("3", PostCommentReplyAdapter.this.commentReplyList.getReplysVoList().get(i2).getId() + "", i2);
                    return;
                }
                PostCommentReplyAdapter.this.activity.requestAuthorCommentDelete(i2, i3, i4, i5);
            }
        });
    }
}
