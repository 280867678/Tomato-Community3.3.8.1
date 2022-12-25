package com.one.tomato.p085ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.p085ui.login.ModifyPSActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.widget.MineCustomItemLayout;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_account)
/* renamed from: com.one.tomato.ui.setting.AccountActivity */
/* loaded from: classes3.dex */
public class AccountActivity extends BaseActivity {
    @ViewInject(R.id.rl_account)
    private MineCustomItemLayout rl_account;
    @ViewInject(R.id.rl_account_id)
    private MineCustomItemLayout rl_account_id;
    @ViewInject(R.id.rl_email)
    private MineCustomItemLayout rl_email;
    @ViewInject(R.id.rl_modify_ps)
    private MineCustomItemLayout rl_modify_ps;
    @ViewInject(R.id.rl_phone)
    private MineCustomItemLayout rl_phone;
    private UserInfo userInfo;
    private int userInfoVersion;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AccountActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.account_title);
        initUserInfo();
        setListener();
    }

    private void initUserInfo() {
        this.userInfo = DBUtil.getUserInfo();
        this.userInfoVersion = DBUtil.getUserInfo().getLocalVersion();
        this.rl_account_id.setRightText(this.userInfo.getUserId());
        this.rl_account.setRightText(this.userInfo.getAccount());
        if (TextUtils.isEmpty(this.userInfo.getPhone())) {
            this.rl_phone.setRightSubText(AppUtil.getString(R.string.account_bind));
            this.rl_phone.setRightSubTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            this.rl_phone.setRightSubText(AppUtil.hideMiddleMobile(this.userInfo.getPhone()));
            this.rl_phone.setRightSubTextColor(getResources().getColor(R.color.text_middle));
        }
        if (TextUtils.isEmpty(this.userInfo.getEmail())) {
            this.rl_email.setRightSubText(AppUtil.getString(R.string.account_bind));
            this.rl_email.setRightSubTextColor(getResources().getColor(R.color.colorAccent));
            return;
        }
        this.rl_email.setRightSubText(AppUtil.hideMiddleMobile(this.userInfo.getEmail()));
        this.rl_email.setRightSubTextColor(getResources().getColor(R.color.text_middle));
    }

    private void setListener() {
        this.rl_modify_ps.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.AccountActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ModifyPSActivity.startActivity(((BaseActivity) AccountActivity.this).mContext);
            }
        });
        this.rl_phone.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.AccountActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TextUtils.isEmpty(AccountActivity.this.userInfo.getPhone())) {
                    PhoneBindActivity.startActivity(((BaseActivity) AccountActivity.this).mContext, "bind_phone");
                } else {
                    PhoneBindActivity.startActivity(((BaseActivity) AccountActivity.this).mContext, "bind_change_phone");
                }
            }
        });
        this.rl_email.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.AccountActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TextUtils.isEmpty(AccountActivity.this.userInfo.getEmail())) {
                    PhoneBindActivity.startActivity(((BaseActivity) AccountActivity.this).mContext, "bind_email");
                } else {
                    PhoneBindActivity.startActivity(((BaseActivity) AccountActivity.this).mContext, "bind_change_email");
                }
            }
        });
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (DBUtil.getUserInfo().getLocalVersion() > this.userInfoVersion) {
            this.userInfoVersion = DBUtil.getUserInfo().getLocalVersion();
            initUserInfo();
        }
    }
}
