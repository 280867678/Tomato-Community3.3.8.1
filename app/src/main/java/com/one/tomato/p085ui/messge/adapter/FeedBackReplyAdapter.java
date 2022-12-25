package com.one.tomato.p085ui.messge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import com.one.tomato.p085ui.messge.p086ui.FeedBackDetailActivity;
import com.one.tomato.p085ui.messge.p086ui.FeedBackResponseActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ViewUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.one.tomato.ui.messge.adapter.FeedBackReplyAdapter */
/* loaded from: classes3.dex */
public class FeedBackReplyAdapter extends BaseRecyclerViewAdapter<PostReviewBean.DataBean> {
    FeedBackResponseActivity activity;

    public FeedBackReplyAdapter(Context context, int i, RecyclerView recyclerView, FeedBackResponseActivity feedBackResponseActivity) {
        super(context, R.layout.item_feedback_reply, recyclerView);
        this.activity = feedBackResponseActivity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostReviewBean.DataBean dataBean) {
        super.convert(baseViewHolder, (BaseViewHolder) dataBean);
        TextView textView = (TextView) baseViewHolder.getView(R.id.text_title);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.text_reason);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.text_weigui_tishi);
        ((TextView) baseViewHolder.getView(R.id.text_create_time)).setText(dataBean.getCreateTime());
        textView.setText(dataBean.getMsgTitle());
        if (!TextUtils.isEmpty(dataBean.getExt3())) {
            if ("1".equals(dataBean.getExt2())) {
                ViewUtil.initTextViewWithSpannableString(textView, new String[]{dataBean.getMsgTitle(), ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.feedback_recharge_status_success)}, new String[]{String.valueOf(this.activity.getResources().getColor(R.color.text_dark)), String.valueOf(this.activity.getResources().getColor(R.color.blue_149eff))}, new String[]{"16", "16"});
            } else if ("2".equals(dataBean.getExt2())) {
                ViewUtil.initTextViewWithSpannableString(textView, new String[]{dataBean.getMsgTitle(), ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.feedback_recharge_status_fail)}, new String[]{String.valueOf(this.activity.getResources().getColor(R.color.text_dark)), String.valueOf(this.activity.getResources().getColor(R.color.red_ff5252))}, new String[]{"16", "16"});
            }
        }
        textView2.setText(dataBean.getFeedbackMsg());
        String feedbackMsgReply = dataBean.getFeedbackMsgReply();
        String string = this.mContext.getResources().getString(R.string.string_feedback_reply_me);
        int length = string.length();
        SpannableString spannableString = new SpannableString(string + feedbackMsgReply);
        spannableString.setSpan(new StyleSpan(1), 0, length, 17);
        textView3.setText(spannableString);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Intent intent = new Intent(this.mContext, FeedBackDetailActivity.class);
        intent.putExtra(AopConstants.APP_PROPERTIES_KEY, getData().get(i));
        this.mContext.startActivity(intent);
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
