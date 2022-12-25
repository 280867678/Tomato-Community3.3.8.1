package com.one.tomato.p085ui.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.CommonBaseAdapter;
import com.one.tomato.adapter.ViewHolder;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.FeedbackType;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackRechargeIssuesActivity;
import com.one.tomato.thirdpart.p084jc.JCRechargeUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.widget.NoScrollGridView;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_feedback)
/* renamed from: com.one.tomato.ui.feedback.FeedbackEnterActivity */
/* loaded from: classes3.dex */
public class FeedbackEnterActivity extends BaseActivity {
    @ViewInject(R.id.gridView)
    private NoScrollGridView gridView;
    private List<FeedbackType> list = new ArrayList();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FeedbackEnterActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.feedback_type_title);
        this.rightTV.setText(R.string.feedback_history);
        this.rightTV.setVisibility(0);
        initGridView();
    }

    private void initGridView() {
        String[] strArr = {"suggest", "report", "recharge", "online_service"};
        int[] iArr = {R.drawable.feedback_suggest, R.drawable.feedback_report, R.drawable.feedback_recharge, R.drawable.feedback_online_service};
        String[] strArr2 = {AppUtil.getString(R.string.feedback_type_suggest), AppUtil.getString(R.string.feedback_type_report), AppUtil.getString(R.string.feedback_type_recharge), AppUtil.getString(R.string.recharge_feedback_online_service)};
        for (int i = 0; i < strArr.length; i++) {
            FeedbackType feedbackType = new FeedbackType();
            feedbackType.setType(strArr[i]);
            feedbackType.setImgResId(iArr[i]);
            feedbackType.setTitle(strArr2[i]);
            this.list.add(feedbackType);
        }
        this.gridView.setAdapter((ListAdapter) new CommonBaseAdapter<FeedbackType>(this, this, this.list, R.layout.item_feedback_type) { // from class: com.one.tomato.ui.feedback.FeedbackEnterActivity.1
            @Override // com.one.tomato.adapter.CommonBaseAdapter
            public void convert(ViewHolder viewHolder, FeedbackType feedbackType2, int i2) {
                ((ImageView) viewHolder.getView(R.id.iv_img)).setImageResource(feedbackType2.getImgResId());
                ((TextView) viewHolder.getView(R.id.tv_title)).setText(feedbackType2.getTitle());
            }
        });
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackEnterActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                if (i2 == 0) {
                    FeedbackType feedbackType2 = (FeedbackType) FeedbackEnterActivity.this.list.get(0);
                    FeedbackDetailActivity.startActivity(((BaseActivity) FeedbackEnterActivity.this).mContext, feedbackType2.getType(), feedbackType2.getTitle());
                } else if (i2 == 1) {
                    FeedbackType feedbackType3 = (FeedbackType) FeedbackEnterActivity.this.list.get(1);
                    FeedbackDetailActivity.startActivity(((BaseActivity) FeedbackEnterActivity.this).mContext, feedbackType3.getType(), feedbackType3.getTitle());
                } else if (i2 == 2) {
                    FeedbackRechargeIssuesActivity.Companion.startActivity(((BaseActivity) FeedbackEnterActivity.this).mContext, 1);
                } else if (i2 != 3) {
                } else {
                    JCRechargeUtil.startHelp(((BaseActivity) FeedbackEnterActivity.this).mContext);
                }
            }
        });
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackEnterActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FeedbackHistoryActivity.startActivity(FeedbackEnterActivity.this);
            }
        });
    }
}
