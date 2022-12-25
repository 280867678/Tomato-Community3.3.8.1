package com.one.tomato.p085ui.messge.adapter;

import android.graphics.drawable.Drawable;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import com.one.tomato.p085ui.messge.p086ui.PostReviewMessageActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;

/* renamed from: com.one.tomato.ui.messge.adapter.PostReviewMessageAdapter */
/* loaded from: classes3.dex */
public class PostReviewMessageAdapter extends BaseRecyclerViewAdapter<PostReviewBean.DataBean> {
    private PostReviewMessageActivity activity;
    private int type;
    private int[] drawable = {R.drawable.icon_post_review_sucess, R.drawable.icon_post_review_faile};
    private ArrayList<Integer> msgTypePass = new ArrayList<>();
    private ArrayList<Integer> msgTypeNO = new ArrayList<>();

    public PostReviewMessageAdapter(PostReviewMessageActivity postReviewMessageActivity, int i, RecyclerView recyclerView, RefreshLayout refreshLayout) {
        super(postReviewMessageActivity, i, recyclerView);
        this.activity = postReviewMessageActivity;
        addMsgType();
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    private void addMsgType() {
        this.msgTypePass.add(101);
        this.msgTypePass.add(103);
        this.msgTypePass.add(204);
        this.msgTypePass.add(203);
        this.msgTypePass.add(901);
        this.msgTypePass.add(903);
        this.msgTypePass.add(906);
        this.msgTypePass.add(907);
        this.msgTypePass.add(1001);
        this.msgTypePass.add(1002);
        this.msgTypePass.add(1005);
        this.msgTypePass.add(304);
        this.msgTypeNO.add(102);
        this.msgTypeNO.add(104);
        this.msgTypeNO.add(201);
        this.msgTypeNO.add(202);
        this.msgTypeNO.add(205);
        this.msgTypeNO.add(206);
        this.msgTypeNO.add(207);
        this.msgTypeNO.add(208);
        this.msgTypeNO.add(902);
        this.msgTypeNO.add(904);
        this.msgTypeNO.add(905);
        this.msgTypeNO.add(1003);
        this.msgTypeNO.add(1005);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:16:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x02c5  */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void convert(BaseViewHolder baseViewHolder, PostReviewBean.DataBean dataBean) {
        LinearLayout linearLayout;
        TextView textView;
        SpannableString spannableString;
        TextView textView2;
        LinearLayout linearLayout2;
        int i;
        super.convert(baseViewHolder, (BaseViewHolder) dataBean);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.text_title);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.text_reason);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.relate);
        RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.getView(R.id.image_head);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.image_show_video);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.text_weigui_tishi);
        RelativeLayout relativeLayout2 = (RelativeLayout) baseViewHolder.getView(R.id.relate_content);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.text_content_duanwen);
        LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.liner_weigui);
        textView4.setVisibility(8);
        linearLayout3.setVisibility(8);
        relativeLayout2.setVisibility(8);
        relativeLayout.setVisibility(8);
        roundedImageView.setVisibility(8);
        imageView.setVisibility(8);
        textView6.setVisibility(8);
        int msgType = dataBean.getMsgType();
        int articleType = dataBean.getArticleType();
        ((TextView) baseViewHolder.getView(R.id.text_create_time)).setText(dataBean.getCreateTime());
        String msgTitle = dataBean.getMsgTitle();
        String replaceMsgTitle = dataBean.getReplaceMsgTitle();
        String str = "";
        if (this.msgTypePass.contains(Integer.valueOf(msgType))) {
            textView = textView5;
            Drawable drawable = this.mContext.getResources().getDrawable(this.drawable[0]);
            linearLayout = linearLayout3;
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView3.setCompoundDrawables(drawable, null, null, null);
            if (!TextUtils.isEmpty(replaceMsgTitle) && msgTitle.contains("{{replace}}")) {
                msgTitle = msgTitle.replace("{{replace}}", str);
                int length = msgTitle.length();
                String str2 = msgTitle + ConstantUtils.PLACEHOLDER_STR_ONE + replaceMsgTitle;
                SpannableString spannableString2 = new SpannableString(str2);
                spannableString2.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.color_4DB84E)), length, str2.length(), 34);
                spannableString = spannableString2;
            }
            spannableString = null;
        } else {
            linearLayout = linearLayout3;
            textView = textView5;
            if (this.msgTypeNO.contains(Integer.valueOf(msgType))) {
                Drawable drawable2 = this.mContext.getResources().getDrawable(this.drawable[1]);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                spannableString = null;
                textView3.setCompoundDrawables(drawable2, null, null, null);
                if (!TextUtils.isEmpty(replaceMsgTitle) && msgTitle.contains("{{replace}}")) {
                    msgTitle = msgTitle.replace("{{replace}}", str);
                    int length2 = msgTitle.length();
                    String str3 = msgTitle + ConstantUtils.PLACEHOLDER_STR_ONE + replaceMsgTitle;
                    SpannableString spannableString3 = new SpannableString(str3);
                    spannableString3.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.red_ff5252)), length2, str3.length(), 34);
                    spannableString = spannableString3;
                }
            }
            spannableString = null;
        }
        if (spannableString != null) {
            textView3.setText(spannableString);
        } else {
            textView3.setText(msgTitle);
        }
        if (getType() == 1) {
            relativeLayout2.setVisibility(0);
            relativeLayout.setVisibility(0);
            roundedImageView.setVisibility(0);
            textView6.setVisibility(0);
            textView6.setText(dataBean.getArticleDes());
        } else if (getType() == 2) {
            if (msgType == 203) {
                linearLayout2 = linearLayout;
                linearLayout2.setVisibility(0);
                relativeLayout2.setVisibility(0);
                relativeLayout.setVisibility(0);
                roundedImageView.setVisibility(0);
                textView6.setVisibility(0);
                textView2 = textView;
                textView2.setText(TextUtils.isEmpty(dataBean.getMsgContent()) ? str : dataBean.getMsgContent());
                textView6.setText(TextUtils.isEmpty(dataBean.getArticleDes()) ? str : dataBean.getArticleDes());
            } else {
                textView2 = textView;
                linearLayout2 = linearLayout;
            }
            if (msgType == 204) {
                linearLayout2.setVisibility(0);
                String msgContent = dataBean.getMsgContent();
                String replaceMsgContent = dataBean.getReplaceMsgContent();
                if (!TextUtils.isEmpty(msgContent) && !TextUtils.isEmpty(replaceMsgContent) && msgContent.contains("{{replace}}")) {
                    int indexOf = msgContent.indexOf("{");
                    int length3 = replaceMsgContent.length();
                    SpannableString spannableString4 = new SpannableString(msgContent.replace("{{replace}}", replaceMsgContent));
                    spannableString4.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.red_ff5252)), indexOf, length3 + indexOf, 34);
                    textView2.setText(spannableString4);
                } else {
                    textView2.setText(msgContent);
                }
            }
            if (msgType == 206) {
                linearLayout2.setVisibility(0);
                if (!TextUtils.isEmpty(dataBean.getMsgContent())) {
                    str = dataBean.getMsgContent();
                }
                textView2.setText(str);
            }
            if (msgType == 205 || msgType == 207 || msgType == 208) {
                linearLayout2.setVisibility(8);
                i = 0;
                relativeLayout2.setVisibility(0);
                relativeLayout.setVisibility(0);
                roundedImageView.setVisibility(0);
                textView6.setVisibility(0);
                textView6.setText(dataBean.getArticleDes());
                if (!TextUtils.isEmpty(dataBean.getMsgContent())) {
                    linearLayout2.setVisibility(0);
                    textView2.setText(dataBean.getMsgContent());
                }
                if (msgType != 304) {
                    if (!TextUtils.isEmpty(dataBean.getMsgContent())) {
                        textView4.setVisibility(i);
                        textView4.setText(dataBean.getMsgContent());
                    }
                } else if (!TextUtils.isEmpty(dataBean.getMsgReason())) {
                    textView4.setVisibility(i);
                    textView4.setText(dataBean.getMsgReason());
                }
                if (articleType != 1) {
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(dataBean.getArticleCover()));
                    return;
                }
                if (articleType != 2) {
                    if (articleType == 3) {
                        roundedImageView.setImageResource(R.drawable.icon_post_review_duanwe);
                        return;
                    } else if (articleType != 4) {
                        return;
                    }
                }
                imageView.setVisibility(0);
                ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(dataBean.getArticleCover()));
                return;
            }
        }
        i = 0;
        if (msgType != 304) {
        }
        if (articleType != 1) {
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        PostReviewBean.DataBean dataBean = getData().get(i);
        boolean z = dataBean.getMsgType() == 102 || dataBean.getMsgType() == 104;
        if (dataBean.getArticleId() != 0) {
            NewPostDetailViewPagerActivity.Companion.startActivity(this.mContext, dataBean.getArticleId(), false, z, true);
        }
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
