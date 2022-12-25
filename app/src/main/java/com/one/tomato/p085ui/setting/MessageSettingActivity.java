package com.one.tomato.p085ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p002v4.app.NotificationManagerCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.MessagePermission;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_message_setting)
/* renamed from: com.one.tomato.ui.setting.MessageSettingActivity */
/* loaded from: classes3.dex */
public class MessageSettingActivity extends BaseActivity {
    @ViewInject(R.id.btn_comment_reply)
    private Switch btn_comment_reply;
    @ViewInject(R.id.btn_huitie_reply)
    private Switch btn_huitie_reply;
    @ViewInject(R.id.btn_notify_status)
    private Switch btn_notify_status;
    @ViewInject(R.id.btn_post_reply)
    private Switch btn_post_reply;
    @ViewInject(R.id.ll_notify)
    private RelativeLayout ll_notify;
    private int setType;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MessageSettingActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.message_setting);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            this.btn_notify_status.setChecked(true);
            this.btn_post_reply.setEnabled(true);
            this.btn_huitie_reply.setEnabled(true);
            this.btn_comment_reply.setEnabled(true);
            if (PreferencesUtil.getInstance().getInt("message_post_reply") == 1) {
                this.btn_post_reply.setChecked(true);
            } else {
                this.btn_post_reply.setChecked(false);
            }
            if (PreferencesUtil.getInstance().getInt("message_reply_reply") == 1) {
                this.btn_huitie_reply.setChecked(true);
            } else {
                this.btn_huitie_reply.setChecked(false);
            }
            if (PreferencesUtil.getInstance().getInt("message_comment_reply") == 1) {
                this.btn_comment_reply.setChecked(true);
            } else {
                this.btn_comment_reply.setChecked(false);
            }
            getPermission();
            setListener();
            return;
        }
        this.btn_notify_status.setChecked(false);
        this.btn_post_reply.setChecked(false);
        this.btn_post_reply.setEnabled(false);
        this.btn_huitie_reply.setChecked(false);
        this.btn_huitie_reply.setEnabled(false);
        this.btn_comment_reply.setChecked(false);
        this.btn_comment_reply.setEnabled(false);
    }

    private void getPermission() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/messageCenter/switch/findByMemberId");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, MessagePermission.class));
    }

    private void setListener() {
        this.ll_notify.setOnClickListener(new View.OnClickListener(this) { // from class: com.one.tomato.ui.setting.MessageSettingActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AppUtil.startAppSetting();
            }
        });
        this.btn_notify_status.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.one.tomato.ui.setting.MessageSettingActivity.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.btn_post_reply.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.MessageSettingActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MessageSettingActivity.this.setType = 1;
                MessageSettingActivity messageSettingActivity = MessageSettingActivity.this;
                messageSettingActivity.setPermission(messageSettingActivity.btn_post_reply.isChecked() ? 1 : 0, MessageSettingActivity.this.btn_huitie_reply.isChecked() ? 1 : 0, MessageSettingActivity.this.btn_comment_reply.isChecked() ? 1 : 0);
            }
        });
        this.btn_huitie_reply.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.MessageSettingActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MessageSettingActivity.this.setType = 2;
                MessageSettingActivity messageSettingActivity = MessageSettingActivity.this;
                messageSettingActivity.setPermission(messageSettingActivity.btn_post_reply.isChecked() ? 1 : 0, MessageSettingActivity.this.btn_huitie_reply.isChecked() ? 1 : 0, MessageSettingActivity.this.btn_comment_reply.isChecked() ? 1 : 0);
            }
        });
        this.btn_comment_reply.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.MessageSettingActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MessageSettingActivity.this.setType = 3;
                MessageSettingActivity messageSettingActivity = MessageSettingActivity.this;
                messageSettingActivity.setPermission(messageSettingActivity.btn_post_reply.isChecked() ? 1 : 0, MessageSettingActivity.this.btn_huitie_reply.isChecked() ? 1 : 0, MessageSettingActivity.this.btn_comment_reply.isChecked() ? 1 : 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPermission(int i, int i2, int i3) {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/messageCenter/switch/save");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("systemMsg", Integer.valueOf(i));
        tomatoParams.addParameter("interactiveMsg", Integer.valueOf(i2));
        tomatoParams.addParameter("noticeMsg", Integer.valueOf(i3));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            MessagePermission messagePermission = (MessagePermission) baseModel.obj;
            if (messagePermission.getSystemMsg() == 1) {
                this.btn_post_reply.setChecked(true);
            } else {
                this.btn_post_reply.setChecked(false);
            }
            PreferencesUtil.getInstance().putInt("message_post_reply", this.btn_post_reply.isChecked() ? 1 : 0);
            if (messagePermission.getInteractiveMsg() == 1) {
                this.btn_huitie_reply.setChecked(true);
            } else {
                this.btn_huitie_reply.setChecked(false);
            }
            PreferencesUtil.getInstance().putInt("message_reply_reply", this.btn_huitie_reply.isChecked() ? 1 : 0);
            if (messagePermission.getNoticeMsg() == 1) {
                this.btn_comment_reply.setChecked(true);
            } else {
                this.btn_comment_reply.setChecked(false);
            }
            PreferencesUtil.getInstance().putInt("message_comment_reply", this.btn_comment_reply.isChecked() ? 1 : 0);
        } else if (i != 2) {
        } else {
            int i2 = this.setType;
            if (i2 == 1) {
                Switch r8 = this.btn_post_reply;
                r8.setChecked(r8.isChecked());
                PreferencesUtil.getInstance().putInt("message_post_reply", this.btn_post_reply.isChecked() ? 1 : 0);
            } else if (i2 == 2) {
                Switch r82 = this.btn_huitie_reply;
                r82.setChecked(r82.isChecked());
                PreferencesUtil.getInstance().putInt("message_reply_reply", this.btn_huitie_reply.isChecked() ? 1 : 0);
            } else if (i2 != 3) {
            } else {
                Switch r83 = this.btn_comment_reply;
                r83.setChecked(r83.isChecked());
                PreferencesUtil.getInstance().putInt("message_comment_reply", this.btn_comment_reply.isChecked() ? 1 : 0);
            }
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i != 1 && i == 2) {
            int i2 = this.setType;
            if (i2 == 1) {
                Switch r0 = this.btn_post_reply;
                r0.setChecked(true ^ r0.isChecked());
            } else if (i2 == 2) {
                Switch r02 = this.btn_huitie_reply;
                r02.setChecked(true ^ r02.isChecked());
            } else if (i2 == 3) {
                Switch r03 = this.btn_comment_reply;
                r03.setChecked(true ^ r03.isChecked());
            }
        }
        return super.handleResponseError(message);
    }
}
