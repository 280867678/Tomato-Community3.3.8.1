package com.one.tomato.p085ui.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.p079db.LockScreenInfo;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.SeparatedEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_lock_screen_entry_pwd)
/* renamed from: com.one.tomato.ui.lockscreen.LockScreenPwdSettingActivity */
/* loaded from: classes3.dex */
public class LockScreenPwdSettingActivity extends BaseActivity {
    @ViewInject(R.id.et_lock_screen_pwd)
    private SeparatedEditText et_lock_screen_pwd;
    private String firstPwd;
    @ViewInject(R.id.right_txt)
    private TextView right_txt;
    @ViewInject(R.id.tv_lock_screen_tip)
    private TextView tv_lock_screen_tip;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LockScreenPwdSettingActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        init();
        setListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.et_lock_screen_pwd.setFocusable(true);
        this.et_lock_screen_pwd.setFocusableInTouchMode(true);
        this.et_lock_screen_pwd.requestFocusFromTouch();
        this.et_lock_screen_pwd.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    private void init() {
        this.titleTV.setText(R.string.lock_screen_setting_pwd_title);
        this.right_txt.setText(R.string.lock_screen_setting_reset);
        this.right_txt.setTextColor(getResources().getColor(R.color.text_969696));
        this.right_txt.setVisibility(8);
    }

    private void setListener() {
        this.et_lock_screen_pwd.setTextChangedListener(new SeparatedEditText.TextChangedListener() { // from class: com.one.tomato.ui.lockscreen.LockScreenPwdSettingActivity.1
            @Override // com.one.tomato.widget.SeparatedEditText.TextChangedListener
            public void textChanged(CharSequence charSequence) {
            }

            @Override // com.one.tomato.widget.SeparatedEditText.TextChangedListener
            public void textCompleted(CharSequence charSequence) {
                LockScreenPwdSettingActivity.this.lockPwdHandle(charSequence.toString());
                LockScreenPwdSettingActivity.this.et_lock_screen_pwd.clearText();
            }
        });
    }

    @Event({R.id.right_txt})
    private void onClick(View view) {
        if (view.getId() != R.id.right_txt) {
            return;
        }
        this.right_txt.setVisibility(8);
        this.tv_lock_screen_tip.setText(R.string.lock_screen_entry_pwd_tip);
        this.firstPwd = null;
        this.et_lock_screen_pwd.clearText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockPwdHandle(String str) {
        if (TextUtils.isEmpty(this.firstPwd)) {
            if (!veitifyLockPwd(str)) {
                return;
            }
            this.tv_lock_screen_tip.setText(R.string.lock_screen_confirm_pwd_tip);
            this.firstPwd = str;
        } else if (veitifyConfirmLockPwd(str)) {
            if (saveLockScreenPwd(this.firstPwd)) {
                ToastUtil.showCenterToast((int) R.string.lock_screen_setting_success);
                finish();
                return;
            }
            ToastUtil.showCenterToast((int) R.string.lock_screen_setting_save_fail);
        } else {
            this.right_txt.setVisibility(0);
            ToastUtil.showCenterToast((int) R.string.lock_screen_setting_verity_pwd_error);
        }
    }

    private boolean veitifyLockPwd(String str) {
        try {
            if (TextUtils.isEmpty(str) || str.length() != 6) {
                return false;
            }
            Integer.parseInt(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean veitifyConfirmLockPwd(String str) {
        try {
            if (TextUtils.isEmpty(str) || str.length() != 6 || !str.equals(this.firstPwd)) {
                return false;
            }
            Integer.parseInt(this.firstPwd);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean saveLockScreenPwd(String str) {
        try {
            LockScreenInfo lockScreenInfo = new LockScreenInfo();
            int i = PreferencesUtil.getInstance().getInt("login_type", -2);
            if (i == 3) {
                String string = PreferencesUtil.getInstance().getString("country_code");
                String string2 = PreferencesUtil.getInstance().getString("country_name");
                String string3 = PreferencesUtil.getInstance().getString("login_user_phone");
                lockScreenInfo.setCountryCode(string);
                lockScreenInfo.setCountryName(string2);
                lockScreenInfo.setPhone(string3);
            } else if (i == 2) {
                lockScreenInfo.setAccount(PreferencesUtil.getInstance().getString("login_user_account"));
            }
            lockScreenInfo.setLoginType(i);
            lockScreenInfo.setLockScreenPwd(str);
            DBUtil.saveLockScreenInfo(lockScreenInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
