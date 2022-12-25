package com.one.tomato.p085ui.lockscreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.p079db.LockScreenInfo;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.tomatolive.library.utils.ConstantUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_lock_screen_login_verity)
/* renamed from: com.one.tomato.ui.lockscreen.LoginAccountVerityActivity */
/* loaded from: classes3.dex */
public class LoginAccountVerityActivity extends BaseActivity {
    @ViewInject(R.id.et_login_pwd)
    private EditText et_login_pwd;
    private boolean isCloseBack;
    private boolean isContentInput;
    private LockScreenInfo lockScreenInfo;
    @ViewInject(R.id.sv_lock_screen)
    private ScrollView sv_lock_screen;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.tv_login_account)
    private TextView tv_login_account;

    public static void startActivity(Context context, boolean z) {
        Intent intent = new Intent();
        intent.setClass(context, LoginAccountVerityActivity.class);
        intent.putExtra("is_close_back", z);
        ((Activity) context).startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        getIntentArgs();
        init();
        setListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.et_login_pwd.setFocusable(true);
        this.et_login_pwd.setFocusableInTouchMode(true);
        this.et_login_pwd.requestFocusFromTouch();
        this.et_login_pwd.requestFocus();
    }

    private void getIntentArgs() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("is_close_back")) {
            this.isCloseBack = intent.getExtras().getBoolean("is_close_back", false);
        } else {
            this.isCloseBack = false;
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (!this.isCloseBack) {
            setResult(802);
            super.onBackPressed();
        }
    }

    @TargetApi(21)
    private void init() {
        this.lockScreenInfo = DBUtil.getLockScreenInfo();
        this.titleTV.setText(R.string.lock_screen_verity_login_title);
        this.et_login_pwd.setShowSoftInputOnFocus(true);
        this.et_login_pwd.setSelection(0);
        if (3 == this.lockScreenInfo.getLoginType()) {
            TextView textView = this.tv_login_account;
            textView.setText(this.lockScreenInfo.getCountryCode() + ConstantUtils.PLACEHOLDER_STR_ONE + this.lockScreenInfo.getPhone());
        } else if (2 == this.lockScreenInfo.getLoginType()) {
            this.tv_login_account.setText(this.lockScreenInfo.getAccount());
        }
        if (this.isCloseBack) {
            this.backImg.setVisibility(8);
        }
    }

    private void setListener() {
        this.et_login_pwd.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.lockscreen.LoginAccountVerityActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    LoginAccountVerityActivity.this.isContentInput = true;
                } else {
                    LoginAccountVerityActivity.this.isContentInput = false;
                }
                if (LoginAccountVerityActivity.this.isContentInput) {
                    LoginAccountVerityActivity.this.tv_confirm.setEnabled(true);
                } else {
                    LoginAccountVerityActivity.this.tv_confirm.setEnabled(false);
                }
            }
        });
        this.sv_lock_screen.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.one.tomato.ui.lockscreen.LoginAccountVerityActivity.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                LoginAccountVerityActivity.this.sv_lock_screen.post(new Runnable() { // from class: com.one.tomato.ui.lockscreen.LoginAccountVerityActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LoginAccountVerityActivity.this.sv_lock_screen.fullScroll(130);
                    }
                });
            }
        });
    }

    private void loginVerity() {
        if (this.lockScreenInfo == null) {
            return;
        }
        String trim = this.et_login_pwd.getText().toString().trim();
        if (trim.length() < 6) {
            ToastUtil.showCenterToast((int) R.string.ps_length_tip1);
            return;
        }
        showWaitingDialog();
        hideKeyBoard(this);
        String phone = this.lockScreenInfo.getPhone();
        String account = this.lockScreenInfo.getAccount();
        String countryCode = this.lockScreenInfo.getCountryCode();
        int loginType = this.lockScreenInfo.getLoginType();
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        if (3 != loginType) {
            phone = account;
        }
        apiImplService.getAccountInfoFromService(phone, countryCode, trim).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new Observer<BaseResponse<LoginInfo>>() { // from class: com.one.tomato.ui.lockscreen.LoginAccountVerityActivity.3
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(BaseResponse<LoginInfo> baseResponse) {
                DBUtil.saveLoginInfo(baseResponse.getData());
                if (3 == LoginAccountVerityActivity.this.lockScreenInfo.getLoginType()) {
                    PreferencesUtil.getInstance().putString("country_code", LoginAccountVerityActivity.this.lockScreenInfo.getCountryCode());
                    PreferencesUtil.getInstance().putString("login_user_phone", LoginAccountVerityActivity.this.lockScreenInfo.getPhone());
                } else {
                    PreferencesUtil.getInstance().putString("login_user_account", LoginAccountVerityActivity.this.lockScreenInfo.getAccount());
                }
                PreferencesUtil.getInstance().putInt("login_type", LoginAccountVerityActivity.this.lockScreenInfo.getLoginType());
                DBUtil.deleteLockScreenInfo();
                UserInfoManager.requestUserInfo(true);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                LoginAccountVerityActivity.this.hideWaitingDialog();
            }
        });
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        hideWaitingDialog();
        setResult(800);
        finish();
    }

    @Event({R.id.tv_confirm})
    private void onClick(View view) {
        if (view.getId() != R.id.tv_confirm) {
            return;
        }
        loginVerity();
    }
}
