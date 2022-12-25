package com.one.tomato.p085ui.messge.p086ui;

import android.os.Bundle;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_system_notice_detail)
/* renamed from: com.one.tomato.ui.messge.ui.SystemNoticeDetailAct */
/* loaded from: classes3.dex */
public class SystemNoticeDetailAct extends BaseActivity {
    private TextView textContent;
    private TextView textTime;
    private TextView textTitle;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.circle_notice_subtitle);
        this.textTitle = (TextView) findViewById(R.id.text_title);
        this.textTime = (TextView) findViewById(R.id.text_time);
        this.textContent = (TextView) findViewById(R.id.text_content);
        PostReviewBean.DataBean dataBean = (PostReviewBean.DataBean) getIntent().getSerializableExtra("system_notice");
        if (dataBean != null) {
            this.textTitle.setText(dataBean.getMsgTitle());
            this.textTime.setText(dataBean.getCreateTime());
            this.textContent.setText(dataBean.getMsgContent());
        }
    }
}
