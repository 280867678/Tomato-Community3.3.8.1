package com.one.tomato.p085ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.p085ui.lockscreen.LockScreenPwdSettingActivity;
import com.one.tomato.p085ui.mine.LanguageSwitchActivity;
import com.one.tomato.p085ui.mine.PersonInfoActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UpdateManager;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.MineCustomItemLayout;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_setting)
/* renamed from: com.one.tomato.ui.setting.SettingActivity */
/* loaded from: classes3.dex */
public class SettingActivity extends BaseActivity {
    @ViewInject(R.id.swtich_video_play)
    private Switch autoPlay;
    private CustomAlertDialog clearDilog;
    @ViewInject(R.id.ll_version)
    private LinearLayout ll_version;
    private int multipleDevice;
    @ViewInject(R.id.rl_account_safe)
    private MineCustomItemLayout rl_account_safe;
    @ViewInject(R.id.rl_cache_clear)
    private MineCustomItemLayout rl_cache_clear;
    @ViewInject(R.id.rl_luage_swith)
    private MineCustomItemLayout rl_luage_swith;
    @ViewInject(R.id.rl_message_setting)
    private MineCustomItemLayout rl_message_setting;
    @ViewInject(R.id.rl_person_info_setting)
    private MineCustomItemLayout rl_person_info_setting;
    @ViewInject(R.id.rl_shield_list)
    private MineCustomItemLayout rl_shield_list;
    @ViewInject(R.id.swtich_lock_screen_pwd)
    private Switch swtich_lock_screen_pwd;
    @ViewInject(R.id.tv_loginOut)
    private TextView tv_loginOut;
    @ViewInject(R.id.tv_version)
    private TextView tv_version;
    @ViewInject(R.id.tv_version_hint)
    private TextView tv_version_hint;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        setListener();
    }

    private void initView() {
        initTitleBar();
        this.titleTV.setText(R.string.common_setting);
        MineCustomItemLayout mineCustomItemLayout = this.rl_cache_clear;
        mineCustomItemLayout.setRightText(FormatUtil.formatTwo(Double.valueOf((FileUtil.getFolderSize(FileUtil.getCacheDir()) / 1024.0d) / 1024.0d)) + "M");
        TextView textView = this.tv_version;
        textView.setText(AppUtil.getString(R.string.about_app_version) + AppUtil.getVersionName());
        if (PreferencesUtil.getInstance().getBoolean("need_upgrade")) {
            this.tv_version_hint.setTextColor(getResources().getColor(R.color.colorAccent));
            this.tv_version_hint.setBackgroundResource(R.drawable.common_shape_stroke_corner30_coloraccent);
        } else {
            this.tv_version_hint.setTextColor(getResources().getColor(R.color.text_light));
            this.tv_version_hint.setBackgroundResource(R.drawable.common_shape_stroke_corner30_divider);
        }
        String string = PreferencesUtil.getInstance().getString("language_country");
        if (!TextUtils.isEmpty(string)) {
            char c = 65535;
            int hashCode = string.hashCode();
            if (hashCode != 2155) {
                if (hashCode != 2691) {
                    if (hashCode == 2718 && string.equals("US")) {
                        c = 2;
                    }
                } else if (string.equals("TW")) {
                    c = 0;
                }
            } else if (string.equals("CN")) {
                c = 1;
            }
            if (c == 0) {
                this.rl_luage_swith.setRightSubText("繁體中文");
            } else if (c == 1) {
                this.rl_luage_swith.setRightSubText("简体中文");
            } else if (c == 2) {
                this.rl_luage_swith.setRightSubText("English");
            }
        }
        String string2 = PreferencesUtil.getInstance().getString("video_auto");
        if (TextUtils.isEmpty(string2) || string2.equals("0")) {
            this.autoPlay.setChecked(false);
        } else {
            this.autoPlay.setChecked(true);
        }
        this.multipleDevice = DBUtil.getSystemParam().getMultipleDevice();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        switchViewByLogin();
    }

    private void switchViewByLogin() {
        if (isLogin()) {
            this.rl_person_info_setting.setVisibility(0);
            ImageLoaderUtil.loadHeadImage(this.mContext, this.rl_person_info_setting.getRightSubImg(), new ImageBean(DBUtil.getUserInfo().getAvatar()));
            this.tv_loginOut.setVisibility(0);
            if (this.multipleDevice == 1) {
                this.tv_loginOut.setText(R.string.setting_login_out);
            } else {
                this.tv_loginOut.setText(R.string.setting_login_switch);
            }
        } else {
            this.rl_person_info_setting.setVisibility(8);
            this.tv_loginOut.setVisibility(8);
        }
        if (TextUtils.isEmpty(DBUtil.getLockScreenInfo().getLockScreenPwd())) {
            this.swtich_lock_screen_pwd.setChecked(false);
        } else {
            this.swtich_lock_screen_pwd.setChecked(true);
        }
    }

    private void setListener() {
        this.rl_person_info_setting.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SettingActivity.this.startLoginActivity()) {
                    return;
                }
                PersonInfoActivity.startActivity(((BaseActivity) SettingActivity.this).mContext, "mine");
            }
        });
        this.rl_message_setting.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MessageSettingActivity.startActivity(((BaseActivity) SettingActivity.this).mContext);
            }
        });
        this.rl_account_safe.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SettingActivity.this.startLoginActivity()) {
                    return;
                }
                AccountActivity.startActivity(((BaseActivity) SettingActivity.this).mContext);
            }
        });
        this.rl_cache_clear.setOnClickListener(new View$OnClickListenerC29244());
        this.ll_version.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PreferencesUtil.getInstance().getBoolean("need_upgrade")) {
                    UpdateManager.getUpdateManager().checkAppUpdate(((BaseActivity) SettingActivity.this).mContext, true);
                }
            }
        });
        this.tv_loginOut.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SettingActivity.this.multipleDevice != 1) {
                    LoginActivity.Companion.startActivity(((BaseActivity) SettingActivity.this).mContext);
                    SettingActivity.this.finish();
                    return;
                }
                SettingActivity.this.showWaitingDialog();
                UserInfoManager.loginOut();
            }
        });
        this.rl_shield_list.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ShieldListActivity.startActivity(((BaseActivity) SettingActivity.this).mContext);
            }
        });
        this.rl_luage_swith.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, LanguageSwitchActivity.class));
                SettingActivity.this.finish();
            }
        });
        this.autoPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this) { // from class: com.one.tomato.ui.setting.SettingActivity.9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    PreferencesUtil.getInstance().putString("video_auto", "1");
                } else {
                    PreferencesUtil.getInstance().putString("video_auto", "0");
                }
            }
        });
        this.swtich_lock_screen_pwd.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SettingActivity.this.swtich_lock_screen_pwd.isChecked()) {
                    LockScreenPwdSettingActivity.startActivity(SettingActivity.this);
                } else {
                    DBUtil.deleteLockScreenInfo();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.one.tomato.ui.setting.SettingActivity$4 */
    /* loaded from: classes3.dex */
    public class View$OnClickListenerC29244 implements View.OnClickListener {
        View$OnClickListenerC29244() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (SettingActivity.this.clearDilog != null) {
                SettingActivity.this.clearDilog.show();
                return;
            }
            SettingActivity settingActivity = SettingActivity.this;
            settingActivity.clearDilog = new CustomAlertDialog(((BaseActivity) settingActivity).mContext);
            SettingActivity.this.clearDilog.setMessage(R.string.setting_clear_memory_tip);
            SettingActivity.this.clearDilog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.4.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    SettingActivity.this.clearDilog.dismiss();
                    SettingActivity.this.showWaitingDialog();
                    FileUtil.deleteFolderFile(FileUtil.getCacheDir().getPath());
                    SettingActivity.this.rl_cache_clear.postDelayed(new Runnable() { // from class: com.one.tomato.ui.setting.SettingActivity.4.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SettingActivity.this.hideWaitingDialog();
                            SettingActivity.this.rl_cache_clear.setRightText("0M");
                        }
                    }, 2000L);
                }
            });
            SettingActivity.this.clearDilog.setCancelButton(R.string.common_cancel, new View.OnClickListener() { // from class: com.one.tomato.ui.setting.SettingActivity.4.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    SettingActivity.this.clearDilog.dismiss();
                }
            });
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutSuccess() {
        super.onLoginOutSuccess();
        hideWaitingDialog();
        ToastUtil.showCenterToast((int) R.string.setting_login_out_success);
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutFail() {
        super.onLoginOutFail();
        hideWaitingDialog();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }
}
