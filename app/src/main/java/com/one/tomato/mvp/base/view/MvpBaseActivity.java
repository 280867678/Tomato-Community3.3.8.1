package com.one.tomato.mvp.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.LoadingDialog;
import com.one.tomato.entity.event.LoginInfoEvent;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.net.LoginOutResponseObserver;
import com.one.tomato.net.LoginResponseObserver;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.EditTextUtils;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.ToastUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MvpBaseActivity.kt */
/* loaded from: classes3.dex */
public abstract class MvpBaseActivity<V extends IBaseView, P extends MvpBasePresenter<V>> extends RxAppCompatActivity implements IBaseView, LoginResponseObserver, LoginOutResponseObserver {
    private String TAG;
    private ImageView backImg;
    private LoadingDialog loadingDialog;
    private Context mContext;
    private P mPresenter;
    private Disposable observeLogInAndOut;
    private ImageView rightIV;
    private TextView rightTV;
    private LinearLayout rl_title_bg;
    private TextView titleTV;

    public abstract int createLayoutView();

    /* renamed from: createPresenter */
    public abstract P mo6439createPresenter();

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public LifecycleProvider<?> getLifecycleProvider() {
        return this;
    }

    public abstract void initData();

    public abstract void initView();

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
    public final String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Context getMContext() {
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ImageView getBackImg() {
        return this.backImg;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final TextView getTitleTV() {
        return this.titleTV;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final TextView getRightTV() {
        return this.rightTV;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ImageView getRightIV() {
        return this.rightIV;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final P getMPresenter() {
        return this.mPresenter;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null && motionEvent.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if (EditTextUtils.isShouldHideSoftKeyBoard(currentFocus, motionEvent)) {
                EditTextUtils.hideSoftKeyBoard(currentFocus.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(createLayoutView());
        Window window = getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "window");
        window.getDecorView();
        this.TAG = getClass().getSimpleName();
        this.mContext = this;
        ImmersionBarUtil.init(this);
        registerLoginRxBus();
        this.mPresenter = mo6439createPresenter();
        P p = this.mPresenter;
        if (p != null) {
            p.attachView(this);
        }
        initView();
        initData();
        P p2 = this.mPresenter;
        if (p2 != null) {
            p2.onCreate();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initTitleBar() {
        View findViewById = findViewById(R.id.rl_title_bg);
        if (findViewById == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.LinearLayout");
        }
        this.rl_title_bg = (LinearLayout) findViewById;
        ImmersionBarUtil.init(this, this.rl_title_bg);
        View findViewById2 = findViewById(R.id.back);
        if (findViewById2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.ImageView");
        }
        this.backImg = (ImageView) findViewById2;
        View findViewById3 = findViewById(R.id.title);
        if (findViewById3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
        this.titleTV = (TextView) findViewById3;
        View findViewById4 = findViewById(R.id.right_txt);
        if (findViewById4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
        this.rightTV = (TextView) findViewById4;
        View findViewById5 = findViewById(R.id.right_iv);
        if (findViewById5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.ImageView");
        }
        this.rightIV = (ImageView) findViewById5;
        if (findViewById(R.id.view_line) == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.View");
        }
        ImageView imageView = this.backImg;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        ImageView imageView2 = this.backImg;
        if (imageView2 == null) {
            return;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseActivity$initTitleBar$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MvpBaseActivity.this.onBackPressed();
            }
        });
    }

    public void showWaitingDialog() {
        initLoadingDialogHelper();
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog == null || this.mContext == null) {
            return;
        }
        Boolean valueOf = loadingDialog != null ? Boolean.valueOf(loadingDialog.isShowing()) : null;
        if (valueOf == null) {
            Intrinsics.throwNpe();
            throw null;
        } else if (valueOf.booleanValue()) {
        } else {
            try {
                LoadingDialog loadingDialog2 = this.loadingDialog;
                if (loadingDialog2 == null) {
                    return;
                }
                loadingDialog2.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideWaitingDialog() {
        initLoadingDialogHelper();
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog != null) {
            Boolean valueOf = loadingDialog != null ? Boolean.valueOf(loadingDialog.isShowing()) : null;
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (!valueOf.booleanValue()) {
            } else {
                try {
                    LoadingDialog loadingDialog2 = this.loadingDialog;
                    if (loadingDialog2 == null) {
                        return;
                    }
                    loadingDialog2.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final void initLoadingDialogHelper() {
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
    }

    public final void showMissingPermissionDialog(String str, final boolean z) {
        if (str == null || !(!Intrinsics.areEqual(str, ""))) {
            str = getResources().getString(R.string.common_permission_notify);
        }
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this.mContext);
        customAlertDialog.bottomButtonVisiblity(0);
        customAlertDialog.setMessage(str);
        customAlertDialog.setConfirmButton(R.string.common_setting, new View.OnClickListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseActivity$showMissingPermissionDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomAlertDialog.this.dismiss();
                AppUtil.startAppSetting();
            }
        });
        customAlertDialog.setCancelButton(R.string.common_cancel, new View.OnClickListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseActivity$showMissingPermissionDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customAlertDialog.dismiss();
                if (z) {
                    MvpBaseActivity.this.finish();
                }
            }
        });
        customAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseActivity$showMissingPermissionDialog$3
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (z) {
                    MvpBaseActivity.this.finish();
                    return false;
                }
                return false;
            }
        });
    }

    protected boolean isLogin() {
        LoginInfo loginInfo = DBUtil.getLoginInfo();
        Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
        return loginInfo.isLogin();
    }

    public boolean startLoginActivity() {
        if (!isLogin()) {
            LoginActivity.Companion companion = LoginActivity.Companion;
            Context context = this.mContext;
            if (context != null) {
                companion.startActivity(context);
                return true;
            }
            Intrinsics.throwNpe();
            throw null;
        }
        return false;
    }

    public final void hideKeyBoard(Activity activity) {
        View peekDecorView;
        if (activity == null || (peekDecorView = activity.getWindow().peekDecorView()) == null || peekDecorView.getWindowToken() == null) {
            return;
        }
        Object systemService = activity.getSystemService("input_method");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
    }

    private final void registerLoginRxBus() {
        Disposable subscribe = RxBus.getDefault().toObservable(LoginInfoEvent.class).subscribe(new Consumer<LoginInfoEvent>() { // from class: com.one.tomato.mvp.base.view.MvpBaseActivity$registerLoginRxBus$1
            @Override // io.reactivex.functions.Consumer
            public void accept(LoginInfoEvent loginInfoEvent) {
                Integer valueOf = loginInfoEvent != null ? Integer.valueOf(loginInfoEvent.loginType) : null;
                if (valueOf != null && valueOf.intValue() == 1) {
                    MvpBaseActivity.this.onLoginSuccess();
                    DataUploadUtil.activateApp();
                } else if (valueOf != null && valueOf.intValue() == 2) {
                    MvpBaseActivity.this.onLoginFail();
                } else if (valueOf != null && valueOf.intValue() == 3) {
                    MvpBaseActivity.this.onLoginOutSuccess();
                } else if (valueOf != null && valueOf.intValue() == 4) {
                    MvpBaseActivity.this.onLoginOutFail();
                } else if (valueOf == null || valueOf.intValue() != 5) {
                } else {
                    MvpBaseActivity.this.onLoginCancel();
                }
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(subscribe, "RxBus.getDefault().toObs…     }\n                })");
        this.observeLogInAndOut = subscribe;
        Disposable disposable = this.observeLogInAndOut;
        if (disposable != null) {
            RxSubscriptions.add(disposable);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("observeLogInAndOut");
            throw null;
        }
    }

    private final void unRegisterLoginRxBus() {
        Disposable disposable = this.observeLogInAndOut;
        if (disposable != null) {
            RxSubscriptions.remove(disposable);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("observeLogInAndOut");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unRegisterLoginRxBus();
        P p = this.mPresenter;
        if (p != null) {
            p.detachView();
        }
        ToastUtil.destroy();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void showDialog() {
        showWaitingDialog();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void dismissDialog() {
        hideWaitingDialog();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public Context getContext() {
        return this.mContext;
    }
}
