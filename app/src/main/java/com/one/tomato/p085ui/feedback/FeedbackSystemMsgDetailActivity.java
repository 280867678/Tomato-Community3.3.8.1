package com.one.tomato.p085ui.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.InteractiveSysBean;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_feedback_message_system_detail)
/* renamed from: com.one.tomato.ui.feedback.FeedbackSystemMsgDetailActivity */
/* loaded from: classes3.dex */
public class FeedbackSystemMsgDetailActivity extends BaseActivity {
    private static InteractiveSysBean.SystemNoticeListBean data;
    @ViewInject(R.id.tv_content)
    private TextView tv_content;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;

    public static void startActivity(Context context, InteractiveSysBean.SystemNoticeListBean systemNoticeListBean) {
        Intent intent = new Intent();
        data = systemNoticeListBean;
        intent.setClass(context, FeedbackSystemMsgDetailActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.my_message_detail_title);
        this.tv_name.setText(data.getNotice_title());
        this.tv_content.setText(data.getNotice_content());
        this.tv_time.setText(data.getCreate_time());
    }
}
