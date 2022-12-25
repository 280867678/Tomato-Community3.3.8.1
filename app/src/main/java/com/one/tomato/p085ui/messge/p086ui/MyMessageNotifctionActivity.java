package com.one.tomato.p085ui.messge.p086ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.p085ui.setting.MessageSettingActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_message_notifction)
/* renamed from: com.one.tomato.ui.messge.ui.MyMessageNotifctionActivity */
/* loaded from: classes3.dex */
public class MyMessageNotifctionActivity extends BaseActivity {
    @ViewInject(R.id.tv_comment_message_count)
    private TextView tvCommentMessageCount;
    @ViewInject(R.id.tv_post_message_count)
    private TextView tvPostMessageCount;
    @ViewInject(R.id.tv_suggest_message_count)
    private TextView tvSuggestMessageCount;
    @ViewInject(R.id.tv_system_message_count)
    private TextView tvSystemMessageCount;
    @ViewInject(R.id.tv_weigui_message_count)
    private TextView tvWeiguiMessageCount;
    @ViewInject(R.id.tv_review_message_count)
    private TextView tv_review_message_count;
    @ViewInject(R.id.tv_reward_message_count)
    private TextView tv_reward_message_count;
    @ViewInject(R.id.tv_up_message_count)
    private TextView tv_up_message_count;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyMessageNotifctionActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        BaseApplication.setIsMyMessageHave(0);
    }

    private void initView() {
        initTitleBar();
        this.titleTV.setText(getResources().getString(R.string.string_my_message_ceter));
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.common_setting);
        this.rightTV.setTextColor(this.mContext.getResources().getColor(R.color.text_999ead));
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.messge.ui.MyMessageNotifctionActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MessageSettingActivity.startActivity(((BaseActivity) MyMessageNotifctionActivity.this).mContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        postMessage();
    }

    @Event({R.id.text_post_review, R.id.text_weigui_chufa, R.id.text_suggest, R.id.text_comment, R.id.text_system, R.id.text_up, R.id.text_review, R.id.text_reward})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_comment /* 2131298403 */:
                openActivity(CommentResponseActivity.class);
                this.tvCommentMessageCount.setText("");
                this.tvCommentMessageCount.setVisibility(8);
                return;
            case R.id.text_post_review /* 2131298471 */:
                Intent intent = new Intent(this, PostReviewMessageActivity.class);
                intent.putExtra("post_or_weigui", 1);
                startActivity(intent);
                this.tvPostMessageCount.setText("");
                this.tvPostMessageCount.setVisibility(8);
                return;
            case R.id.text_review /* 2131298491 */:
                this.tv_review_message_count.setText("");
                this.tv_review_message_count.setVisibility(8);
                Intent intent2 = new Intent(this, PostReviewMessageActivity.class);
                intent2.putExtra("post_or_weigui", 10);
                startActivity(intent2);
                return;
            case R.id.text_reward /* 2131298492 */:
                Intent intent3 = new Intent(this, PostReviewMessageActivity.class);
                intent3.putExtra("post_or_weigui", 3);
                startActivity(intent3);
                this.tv_reward_message_count.setText("");
                this.tv_reward_message_count.setVisibility(8);
                return;
            case R.id.text_suggest /* 2131298508 */:
                openActivity(FeedBackResponseActivity.class);
                this.tvSuggestMessageCount.setText("");
                this.tvSuggestMessageCount.setVisibility(8);
                return;
            case R.id.text_system /* 2131298509 */:
                openActivity(SystemNoticeActivity.class);
                this.tvSystemMessageCount.setText("");
                this.tvSystemMessageCount.setVisibility(8);
                return;
            case R.id.text_up /* 2131298530 */:
                Intent intent4 = new Intent(this, PostReviewMessageActivity.class);
                intent4.putExtra("post_or_weigui", 9);
                startActivity(intent4);
                this.tv_up_message_count.setText("");
                this.tv_up_message_count.setVisibility(8);
                return;
            case R.id.text_weigui_chufa /* 2131298542 */:
                Intent intent5 = new Intent(this, PostReviewMessageActivity.class);
                intent5.putExtra("post_or_weigui", 2);
                startActivity(intent5);
                this.tvWeiguiMessageCount.setText("");
                this.tvWeiguiMessageCount.setVisibility(8);
                return;
            default:
                return;
        }
    }

    private void postMessage() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/messageCenter/queryUnReadMessageCount");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 1));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        String str = ((BaseModel) message.obj).data;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.has("1") ? jSONObject.getInt("1") : 0;
                int i2 = jSONObject.has("2") ? jSONObject.getInt("2") : 0;
                int i3 = jSONObject.has("4") ? jSONObject.getInt("4") : 0;
                int i4 = jSONObject.has("6") ? jSONObject.getInt("6") : 0;
                int i5 = jSONObject.has("8") ? jSONObject.getInt("8") : 0;
                int i6 = jSONObject.has("9") ? jSONObject.getInt("9") : 0;
                int i7 = jSONObject.has("10") ? jSONObject.getInt("10") : 0;
                int i8 = jSONObject.has("3") ? jSONObject.getInt("3") : 0;
                if (i != 0) {
                    this.tvPostMessageCount.setVisibility(0);
                    TextView textView = this.tvPostMessageCount;
                    textView.setText(i + "");
                }
                if (i2 != 0) {
                    this.tvWeiguiMessageCount.setVisibility(0);
                    TextView textView2 = this.tvWeiguiMessageCount;
                    textView2.setText(i2 + "");
                }
                if (i3 != 0) {
                    this.tvSuggestMessageCount.setVisibility(0);
                    TextView textView3 = this.tvSuggestMessageCount;
                    textView3.setText(i3 + "");
                }
                if (i4 != 0) {
                    this.tvCommentMessageCount.setVisibility(0);
                    TextView textView4 = this.tvCommentMessageCount;
                    textView4.setText(i4 + "");
                }
                if (i5 != 0) {
                    this.tvSystemMessageCount.setVisibility(0);
                    TextView textView5 = this.tvSystemMessageCount;
                    textView5.setText(i5 + "");
                }
                if (i6 != 0) {
                    this.tv_up_message_count.setVisibility(0);
                    this.tv_up_message_count.setText(String.valueOf(i6));
                }
                if (i7 != 0) {
                    this.tv_review_message_count.setText(String.valueOf(i6));
                    this.tv_review_message_count.setVisibility(0);
                }
                if (i8 == 0) {
                    return;
                }
                this.tv_reward_message_count.setText(String.valueOf(i8));
                this.tv_reward_message_count.setVisibility(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        ToastUtil.showCenterToast(AppUtil.getString(R.string.Inquire_meesage_count_fail));
        return super.handleResponseError(message);
    }
}
