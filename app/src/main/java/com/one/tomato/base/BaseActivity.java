package com.one.tomato.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.LoadingDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.event.LoginInfoEvent;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.net.LoginOutResponseObserver;
import com.one.tomato.net.LoginResponseObserver;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.utils.ConnectionChangeReceiver;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import de.greenrobot.event.EventBus;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.xutils.C5540x;

/* loaded from: classes3.dex */
public abstract class BaseActivity extends RxAppCompatActivity implements ResponseObserver, LoginResponseObserver, LoginOutResponseObserver {
    protected String TAG;
    protected ImageView backImg;
    protected LoadingDialog loadingDialog;
    protected Context mContext;
    private ConnectionChangeReceiver myReceiver = null;
    private Disposable observeLogInAndOut;
    protected TextView rightTV;
    protected LinearLayout rl_title_bg;
    protected CustomAlertDialog tipDialog;
    protected TextView titleTV;

    public void onEventMainThread(LoginInfoEvent loginInfoEvent) {
    }

    @Override // com.one.tomato.net.LoginResponseObserver
    public void onLoginCancel() {
    }

    @Override // com.one.tomato.net.LoginResponseObserver
    public void onLoginFail() {
    }

    @Override // com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutFail() {
    }

    @Override // com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutSuccess() {
    }

    @Override // com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().getDecorView();
        this.TAG = getClass().getSimpleName();
        this.mContext = this;
        C5540x.view().inject(this);
        EventBus.getDefault().register(this);
        registerLoginRxBus();
        ImmersionBarUtil.init(this);
    }

    public String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initTitleBar() {
        this.rl_title_bg = (LinearLayout) findViewById(R.id.rl_title_bg);
        ImmersionBarUtil.init(this, this.rl_title_bg);
        this.backImg = (ImageView) findViewById(R.id.back);
        this.titleTV = (TextView) findViewById(R.id.title);
        this.rightTV = (TextView) findViewById(R.id.right_txt);
        ImageView imageView = (ImageView) findViewById(R.id.right_iv);
        findViewById(R.id.view_line);
        ImageView imageView2 = this.backImg;
        if (imageView2 != null) {
            imageView2.setVisibility(0);
            this.backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.base.BaseActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BaseActivity.this.onBackPressed();
                }
            });
        }
    }

    public void showWaitingDialog() {
        initLoadingDialogHelper();
        if (!this.loadingDialog.isShowing()) {
            try {
                this.loadingDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideWaitingDialog() {
        initLoadingDialogHelper();
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        try {
            this.loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLoadingDialogHelper() {
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
    }

    public void showTipDialog(int i) {
        showTipDialog(getResources().getString(i));
    }

    public void showTipDialog(String str) {
        CustomAlertDialog customAlertDialog = this.tipDialog;
        if (customAlertDialog == null) {
            this.tipDialog = new CustomAlertDialog(this);
            this.tipDialog.bottomButtonVisiblity(2);
            this.tipDialog.setMessage(str);
        } else {
            customAlertDialog.show();
        }
        this.tipDialog.setConfirmButton(R.string.common_confirm);
        this.tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.base.BaseActivity.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                BaseActivity.this.tipDialog.dismiss();
                BaseActivity.this.onTipDialogDismiss();
            }
        });
    }

    public void onTipDialogDismiss() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isBlack(int i) {
        if (i == 1) {
            showTipDialog(R.string.manage_is_black);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLogin() {
        return DBUtil.getLoginInfo().isLogin();
    }

    public boolean startLoginActivity() {
        if (!isLogin()) {
            LoginActivity.Companion.startActivity(this.mContext);
            return true;
        }
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        int i = message.what;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleHttpRequestError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        handleResponseError(message);
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleRequestCancel(Message message) {
        hideWaitingDialog();
        return false;
    }

    private void registerLoginRxBus() {
        this.observeLogInAndOut = RxBus.getDefault().toObservable(LoginInfoEvent.class).subscribe(new Consumer<LoginInfoEvent>() { // from class: com.one.tomato.base.BaseActivity.3
            @Override // io.reactivex.functions.Consumer
            public void accept(LoginInfoEvent loginInfoEvent) throws Exception {
                int i = loginInfoEvent.loginType;
                if (i == 1) {
                    BaseActivity.this.onLoginSuccess();
                    DataUploadUtil.activateApp();
                } else if (i == 2) {
                    BaseActivity.this.onLoginFail();
                } else if (i == 3) {
                    BaseActivity.this.onLoginOutSuccess();
                } else if (i == 4) {
                    BaseActivity.this.onLoginOutFail();
                } else if (i != 5) {
                } else {
                    BaseActivity.this.onLoginCancel();
                }
            }
        });
        RxSubscriptions.add(this.observeLogInAndOut);
    }

    private void unRegisterLoginRxBus() {
        RxSubscriptions.remove(this.observeLogInAndOut);
    }

    public void hideKeyBoard(Activity activity) {
        View peekDecorView;
        if (activity == null || (peekDecorView = activity.getWindow().peekDecorView()) == null || peekDecorView.getWindowToken() == null) {
            return;
        }
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
    }

    public void showKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getApplicationContext().getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.showSoftInput(currentFocus, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideKeyBoard(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegisterLoginRxBus();
        ToastUtil.destroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.myReceiver = new ConnectionChangeReceiver();
        registerReceiver(this.myReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void unregisterReceiver() {
        unregisterReceiver(this.myReceiver);
    }

    public void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
