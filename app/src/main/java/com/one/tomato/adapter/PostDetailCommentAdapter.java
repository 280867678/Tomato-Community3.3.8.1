package com.one.tomato.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.dialog.CommentActionDialog;
import com.one.tomato.entity.CommentList;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.PostCommentReplyActivity;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PostDetailCommentAdapter extends BaseRecyclerViewAdapter<CommentList> {
    private CommentActionDialog commentActionDialog;
    private int curPosition = -1;
    private PostList intentPostList;
    private IPostCommentContact itemToViewCallBack;

    public void setItemToViewCallBack(IPostCommentContact iPostCommentContact) {
        this.itemToViewCallBack = iPostCommentContact;
    }

    public PostDetailCommentAdapter(Context context, RecyclerView recyclerView, PostList postList) {
        super(context, R.layout.item_post_comment_list, recyclerView);
        this.intentPostList = postList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, CommentList commentList) {
        TextView textView;
        int i;
        super.convert(baseViewHolder, (BaseViewHolder) commentList);
        int indexOf = this.mData.indexOf(commentList);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_content_data);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_comment_loading);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) baseViewHolder.getView(R.id.loading_progress);
        LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_comment_nodata);
        LinearLayout linearLayout4 = (LinearLayout) baseViewHolder.getView(R.id.ll_comment_fail);
        LinearLayout linearLayout5 = (LinearLayout) baseViewHolder.getView(R.id.ll_content);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_head);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_name);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_comment_level_nick);
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_comment_vip);
        LinearLayout linearLayout6 = (LinearLayout) baseViewHolder.getView(R.id.ll_thumb);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_thumb);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_thumb_num);
        ImageView imageView5 = (ImageView) baseViewHolder.getView(R.id.iv_un_thumb);
        ImageView imageView6 = (ImageView) baseViewHolder.getView(R.id.iv_comment_more);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_landlord);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_time);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.tv_send_ing);
        TextView textView7 = (TextView) baseViewHolder.getView(R.id.tv_send_fail);
        TextView textView8 = (TextView) baseViewHolder.getView(R.id.tv_send_retry);
        TextView textView9 = (TextView) baseViewHolder.getView(R.id.tv_send_delete);
        TextView textView10 = (TextView) baseViewHolder.getView(R.id.tv_content);
        NoScrollGridView noScrollGridView = (NoScrollGridView) baseViewHolder.getView(R.id.gridView);
        TextView textView11 = (TextView) baseViewHolder.getView(R.id.tv_reply_more);
        linearLayout3.setVisibility(8);
        linearLayout2.setVisibility(8);
        if (lottieAnimationView.getAnimation() != null && lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }
        linearLayout4.setVisibility(8);
        if (indexOf == 0 && commentList.getId() == 0) {
            linearLayout.setVisibility(8);
            if (commentList.getLoadStatus() == 1) {
                linearLayout2.setVisibility(0);
                lottieAnimationView.setAnimation("loading_more.json");
                return;
            } else if (commentList.getLoadStatus() == 2) {
                linearLayout3.setVisibility(0);
                return;
            } else if (commentList.getLoadStatus() != 3) {
                return;
            } else {
                linearLayout4.setVisibility(0);
                return;
            }
        }
        baseViewHolder.addOnClickListener(R.id.iv_head).addOnClickListener(R.id.tv_name).addOnClickListener(R.id.iv_thumb).addOnClickListener(R.id.iv_un_thumb).addOnClickListener(R.id.iv_comment_more).addOnClickListener(R.id.tv_reply_more).addOnClickListener(R.id.tv_send_retry).addOnClickListener(R.id.tv_send_delete).addOnClickListener(R.id.ll_comment_fail).addOnClickListener(R.id.ll_content_data);
        baseViewHolder.addOnLongClickListener(R.id.ll_content_data);
        LogUtil.m3787d("yan6", "position" + indexOf);
        if (DBUtil.getMemberId() != commentList.getMemberId() && commentList.getIsVisibleSelf() == 1) {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(8);
            linearLayout4.setVisibility(8);
            return;
        }
        linearLayout.setVisibility(0);
        linearLayout5.setVisibility(0);
        if (commentList.getVipType() > 0) {
            imageView3.setVisibility(0);
        } else {
            imageView3.setVisibility(8);
        }
        ImageLoaderUtil.loadHeadImage(this.mContext, imageView, new ImageBean(commentList.getAvatar()));
        textView2.setText(commentList.getMemberName());
        UserPermissionUtil.getInstance().userLevelNickShow(imageView2, new LevelBean(commentList.getCurrentLevelIndex()));
        if (commentList.getIsThumbUp() == 1) {
            imageView4.setSelected(true);
            imageView5.setSelected(false);
            textView = textView3;
            textView.setTextColor(this.mContext.getResources().getColor(R.color.colorAccent));
        } else {
            textView = textView3;
            if (commentList.getIsThumbDown() == 1) {
                imageView4.setSelected(false);
                imageView5.setSelected(true);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.tip_color));
            } else {
                imageView4.setSelected(false);
                imageView5.setSelected(false);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.text_middle));
            }
        }
        textView.setText(FormatUtil.formatNumOverTenThousand((commentList.getGoodNum() - commentList.getPointOnNum()) + ""));
        if (commentList.getIsLouzhu() == 1) {
            textView4.setVisibility(0);
            if ("1".equals(commentList.getSex())) {
                textView4.setBackgroundResource(R.drawable.shape_post_detail_landlord_man);
            } else {
                textView4.setBackgroundResource(R.drawable.shape_post_detail_landlord_woman);
            }
            i = 8;
        } else {
            i = 8;
            textView4.setVisibility(8);
        }
        if (commentList.getSendStatus() == 1) {
            textView5.setVisibility(i);
            textView6.setVisibility(0);
            textView7.setVisibility(i);
            textView8.setVisibility(i);
            textView9.setVisibility(i);
        } else if (commentList.getSendStatus() == 2) {
            textView5.setVisibility(0);
            textView5.setText(R.string.post_comment_recent);
            textView6.setVisibility(i);
            textView7.setVisibility(i);
            textView8.setVisibility(i);
            textView9.setVisibility(i);
        } else if (commentList.getSendStatus() == 3 || commentList.getSendStatus() == 4) {
            textView5.setVisibility(i);
            textView6.setVisibility(i);
            textView7.setVisibility(0);
            textView8.setVisibility(0);
            textView9.setVisibility(0);
        } else {
            textView5.setVisibility(0);
            textView5.setText(commentList.getCommentTime());
            textView6.setVisibility(i);
            textView7.setVisibility(i);
            textView8.setVisibility(i);
            textView9.setVisibility(i);
        }
        if (!TextUtils.isEmpty(commentList.getContent())) {
            EmotionUtil.spannableEmoticonFilter(textView10, commentList.getContent());
        }
        if (!TextUtils.isEmpty(commentList.getSecImageUrl()) && commentList.getSecImageUrl().split(";").length > 0) {
            noScrollGridView.setVisibility(0);
            final ArrayList arrayList = new ArrayList();
            for (String str : !TextUtils.isEmpty(commentList.getSecImageUrl()) ? commentList.getSecImageUrl().split(";") : new String[0]) {
                ImageBean imageBean = new ImageBean();
                imageBean.setImage(str);
                imageBean.setSecret(true);
                imageBean.setLocal(commentList.getSendStatus() > 0);
                arrayList.add(imageBean);
            }
            noScrollGridView.setAdapter((ListAdapter) new ImageGridAdapter(this.mContext, arrayList, R.layout.item_img_grid));
            noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.adapter.PostDetailCommentAdapter.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                    ImageShowActivity.Companion.startActivity(((BaseQuickAdapter) PostDetailCommentAdapter.this).mContext, arrayList, i2);
                }
            });
        } else {
            noScrollGridView.setVisibility(8);
        }
        textView11.setVisibility(0);
        if (commentList.getReplysVoList() == null || commentList.getReplysVoList().size() <= 0) {
            textView11.setText(R.string.video_comment_reply);
        } else {
            textView11.setText(AppUtil.getString(R.string.post_comment_reply_num, Integer.valueOf(commentList.getReplysVoList().size())));
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        this.curPosition = i;
        CommentList commentList = (CommentList) this.mData.get(i);
        switch (view.getId()) {
            case R.id.iv_comment_more /* 2131297150 */:
                if (commentList.getSendStatus() != 0 && commentList.getSendStatus() != 2) {
                    return;
                }
                showCommentActionDialog(i, commentList);
                return;
            case R.id.iv_head /* 2131297220 */:
            case R.id.tv_name /* 2131299145 */:
                NewMyHomePageActivity.Companion.startActivity(this.mContext, commentList.getMemberId());
                return;
            case R.id.iv_thumb /* 2131297381 */:
                int id = view.getId();
                PostUtils postUtils = PostUtils.INSTANCE;
                if (AppUtil.isFastClick(id, ConstantUtils.MAX_ITEM_NUM)) {
                    ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                    return;
                }
                IPostCommentContact iPostCommentContact = this.itemToViewCallBack;
                if (iPostCommentContact == null) {
                    return;
                }
                iPostCommentContact.postCommentTumb(true, commentList);
                return;
            case R.id.iv_un_thumb /* 2131297392 */:
                int id2 = view.getId();
                PostUtils postUtils2 = PostUtils.INSTANCE;
                if (AppUtil.isFastClick(id2, ConstantUtils.MAX_ITEM_NUM)) {
                    ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                    return;
                }
                IPostCommentContact iPostCommentContact2 = this.itemToViewCallBack;
                if (iPostCommentContact2 == null) {
                    return;
                }
                iPostCommentContact2.postCommentTumb(true, commentList);
                return;
            case R.id.ll_comment_fail /* 2131297523 */:
                IPostCommentContact iPostCommentContact3 = this.itemToViewCallBack;
                if (iPostCommentContact3 == null) {
                    return;
                }
                iPostCommentContact3.postRefresh();
                return;
            case R.id.ll_content_data /* 2131297532 */:
            case R.id.tv_reply_more /* 2131299318 */:
                if (commentList.getId() <= 0) {
                    return;
                }
                PostCommentReplyActivity.startActivity(this.mContext, commentList.getId(), this.intentPostList.getMemberId());
                return;
            case R.id.tv_send_delete /* 2131299356 */:
                IPostCommentContact iPostCommentContact4 = this.itemToViewCallBack;
                if (iPostCommentContact4 == null) {
                    return;
                }
                iPostCommentContact4.callDeleteComment(i);
                return;
            case R.id.tv_send_retry /* 2131299361 */:
                IPostCommentContact iPostCommentContact5 = this.itemToViewCallBack;
                if (iPostCommentContact5 == null) {
                    return;
                }
                iPostCommentContact5.sendRetryComment(commentList, i, null);
                return;
            default:
                return;
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildLongClick(baseQuickAdapter, view, i);
        CommentList commentList = (CommentList) this.mData.get(i);
        if (view.getId() != R.id.ll_content_data) {
            return;
        }
        if (commentList.getSendStatus() != 0 && commentList.getSendStatus() != 2) {
            return;
        }
        showCommentActionDialog(i, commentList);
    }

    private void showCommentActionDialog(int i, final CommentList commentList) {
        if (this.commentActionDialog == null) {
            this.commentActionDialog = new CommentActionDialog(this.mContext);
        }
        this.commentActionDialog.show();
        this.commentActionDialog.setCommentList(i, this.intentPostList.getMemberId(), commentList.getArticleId(), commentList.getId(), commentList.getMemberId(), 2);
        this.commentActionDialog.setCommentDeleteListener(new CommentActionDialog.CommentDeleteListener() { // from class: com.one.tomato.adapter.PostDetailCommentAdapter.2
            @Override // com.one.tomato.dialog.CommentActionDialog.CommentDeleteListener
            public void showCommentDeleteDialog(int i2, int i3, int i4, int i5) {
                if (DBUtil.getMemberId() == commentList.getMemberId()) {
                    if (PostDetailCommentAdapter.this.itemToViewCallBack != null) {
                        IPostCommentContact iPostCommentContact = PostDetailCommentAdapter.this.itemToViewCallBack;
                        iPostCommentContact.requestCommentDelete("2", commentList.getId() + "", i2);
                    }
                } else if (PostDetailCommentAdapter.this.itemToViewCallBack != null) {
                    PostDetailCommentAdapter.this.itemToViewCallBack.requestAuthorCommentDelete(i2, PostDetailCommentAdapter.this.intentPostList.getId(), i4, i5);
                }
                PostDetailCommentAdapter.this.commentActionDialog.dismiss();
            }
        });
    }

    public int getCurPosition() {
        return this.curPosition;
    }
}
