package com.one.tomato.mvp.p080ui.circle.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CircleNoticeBean;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_circle_notice_detail)
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleNoticeDetailActivity */
/* loaded from: classes3.dex */
public class CircleNoticeDetailActivity extends BaseActivity {
    private CircleNoticeBean mCircleNoticeBean = new CircleNoticeBean();
    @ViewInject(R.id.tv_circle_notice_content)
    private TextView tv_circle_notice_content;
    @ViewInject(R.id.tv_circle_notice_date)
    private TextView tv_circle_notice_date;

    public static void startActivity(Context context, CircleNoticeBean circleNoticeBean) {
        Intent intent = new Intent();
        intent.setClass(context, CircleNoticeDetailActivity.class);
        intent.putExtra("notice_info", circleNoticeBean);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        fetchIntentArg();
        updateViews();
        getNoticeInfo(this.mCircleNoticeBean.getGroupId());
    }

    private void fetchIntentArg() {
        Intent intent = getIntent();
        if (intent == null || !intent.getExtras().containsKey("notice_info")) {
            return;
        }
        this.mCircleNoticeBean = (CircleNoticeBean) intent.getExtras().getSerializable("notice_info");
        if (this.mCircleNoticeBean != null) {
            return;
        }
        this.mCircleNoticeBean = new CircleNoticeBean();
    }

    private void getNoticeInfo(int i) {
        CircleNoticeBean circleNoticeBean = this.mCircleNoticeBean;
        if (circleNoticeBean == null || TextUtils.isEmpty(circleNoticeBean.getDate())) {
            showWaitingDialog();
            TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/circle/groupNotice");
            tomatoParams.addParameter("groupId", Integer.valueOf(i));
            tomatoParams.post(new TomatoCallback(this, 600));
        }
    }

    private void updateViews() {
        CircleNoticeBean circleNoticeBean = this.mCircleNoticeBean;
        if (circleNoticeBean == null || TextUtils.isEmpty(circleNoticeBean.getData())) {
            return;
        }
        this.tv_circle_notice_content.setText(this.mCircleNoticeBean.getData());
        this.tv_circle_notice_date.setText(this.mCircleNoticeBean.getDate());
        this.titleTV.setText(this.mCircleNoticeBean.getGroupName());
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 600) {
            return;
        }
        CircleNoticeBean circleNoticeBean = (CircleNoticeBean) baseModel.obj;
        this.mCircleNoticeBean.setData(circleNoticeBean.getData());
        this.mCircleNoticeBean.setDate(circleNoticeBean.getDate());
        updateViews();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        int i = message.what;
        return super.handleResponseError(message);
    }
}
