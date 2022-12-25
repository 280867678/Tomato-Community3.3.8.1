package com.one.tomato.mvp.p080ui.feedback.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RechargeFeedbackIssues;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;

/* compiled from: FeedbackIssuesAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.adapter.FeedbackIssuesAdapter */
/* loaded from: classes3.dex */
public final class FeedbackIssuesAdapter extends BaseRecyclerViewAdapter<RechargeFeedbackIssues> {
    public FeedbackIssuesAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_feedback_issues, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RechargeFeedbackIssues rechargeFeedbackIssues) {
        super.convert(baseViewHolder, (BaseViewHolder) rechargeFeedbackIssues);
        int indexOf = this.mData.indexOf(rechargeFeedbackIssues);
        String str = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_question) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_answer) : null;
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(indexOf + 1));
            sb.append(".");
            sb.append(rechargeFeedbackIssues != null ? rechargeFeedbackIssues.getQuestion() : null);
            textView.setText(sb.toString());
        }
        if (textView2 != null) {
            if (rechargeFeedbackIssues != null) {
                str = rechargeFeedbackIssues.getAnswer();
            }
            textView2.setText(str);
        }
    }
}
