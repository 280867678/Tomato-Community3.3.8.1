package com.one.tomato.mvp.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.one.tomato.utils.DBUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MvpBaseFragment.kt */
/* loaded from: classes3.dex */
public abstract class MvpBaseFragment<V extends IBaseView, P extends MvpBasePresenter<V>> extends RxFragment implements IBaseView, LoginResponseObserver, LoginOutResponseObserver {
    protected String TAG;
    private HashMap _$_findViewCache;
    private boolean isLazyLoad;
    private boolean isViewCreated;
    private boolean isVisibleToUser;
    private View layoutView;
    private LoadingDialog loadingDialog;
    private Context mContext;
    private P mPresenter;
    private Disposable observeLogInAndOut;
    private CustomAlertDialog tipDialog;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public abstract int createLayoutID();

    /* renamed from: createPresenter */
    public abstract P mo6441createPresenter();

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public LifecycleProvider<?> getLifecycleProvider() {
        return this;
    }

    public abstract void inintData();

    public void initListener() {
    }

    public abstract void initView();

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInvisibleLoad() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLazyLoad() {
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

    public void onTipDialogDismiss() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVisibleLoad() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void rePlaceLazyLoad() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String getTAG() {
        String str = this.TAG;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("TAG");
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Context getMContext() {
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final CustomAlertDialog getTipDialog() {
        return this.tipDialog;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean isVisibleToUser() {
        return this.isVisibleToUser;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean isViewCreated() {
        return this.isViewCreated;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setLazyLoad(boolean z) {
        this.isLazyLoad = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final P getMPresenter() {
        return this.mPresenter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final View getLayoutView() {
        return this.layoutView;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkExpressionValueIsNotNull(simpleName, "this.javaClass.simpleName");
        this.TAG = simpleName;
        this.mContext = getActivity();
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        this.isViewCreated = true;
        this.layoutView = inflater.inflate(createLayoutID(), viewGroup, false);
        return this.layoutView;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        registerLoginRxBus();
        this.isViewCreated = true;
        this.mPresenter = mo6441createPresenter();
        P p = this.mPresenter;
        if (p != null) {
            p.attachView(this);
        }
        initView();
        inintData();
        initListener();
        P p2 = this.mPresenter;
        if (p2 != null) {
            p2.onCreate();
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        this.isVisibleToUser = z;
        if (z && this.isViewCreated && !this.isLazyLoad) {
            onLazyLoad();
            this.isLazyLoad = true;
            rePlaceLazyLoad();
        } else if (z && this.isViewCreated) {
            onVisibleLoad();
        } else if (!this.isViewCreated || z) {
        } else {
            onInvisibleLoad();
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onStop() {
        super.onStop();
    }

    public final boolean isBlack(int i) {
        if (i == 1) {
            showTipDialog((int) R.string.manage_is_black);
            return true;
        }
        return false;
    }

    private final void registerLoginRxBus() {
        this.observeLogInAndOut = RxBus.getDefault().toObservable(LoginInfoEvent.class).subscribe(new Consumer<LoginInfoEvent>() { // from class: com.one.tomato.mvp.base.view.MvpBaseFragment$registerLoginRxBus$1
            @Override // io.reactivex.functions.Consumer
            public void accept(LoginInfoEvent loginInfoEvent) {
                Integer valueOf = loginInfoEvent != null ? Integer.valueOf(loginInfoEvent.loginType) : null;
                if (valueOf != null && valueOf.intValue() == 1) {
                    MvpBaseFragment.this.onLoginSuccess();
                } else if (valueOf != null && valueOf.intValue() == 2) {
                    MvpBaseFragment.this.onLoginFail();
                } else if (valueOf != null && valueOf.intValue() == 3) {
                    MvpBaseFragment.this.onLoginOutSuccess();
                } else if (valueOf != null && valueOf.intValue() == 4) {
                    MvpBaseFragment.this.onLoginOutFail();
                } else if (valueOf == null || valueOf.intValue() != 5) {
                } else {
                    MvpBaseFragment.this.onLoginCancel();
                }
            }
        });
        RxSubscriptions.add(this.observeLogInAndOut);
    }

    private final void unRegisterLoginRxBus() {
        RxSubscriptions.remove(this.observeLogInAndOut);
    }

    public void showWaitingDialog() {
        initLoadingDialogHelper();
        LoadingDialog loadingDialog = this.loadingDialog;
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
            if (loadingDialog == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (!loadingDialog.isShowing()) {
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
            this.loadingDialog = new LoadingDialog(this.mContext);
        }
    }

    public void showTipDialog(int i) {
        Resources resources;
        Context context = this.mContext;
        showTipDialog((context == null || (resources = context.getResources()) == null) ? null : resources.getString(i));
    }

    public void showTipDialog(String str) {
        CustomAlertDialog customAlertDialog = this.tipDialog;
        if (customAlertDialog == null) {
            this.tipDialog = new CustomAlertDialog(this.mContext);
            CustomAlertDialog customAlertDialog2 = this.tipDialog;
            if (customAlertDialog2 != null) {
                customAlertDialog2.bottomButtonVisiblity(2);
            }
            CustomAlertDialog customAlertDialog3 = this.tipDialog;
            if (customAlertDialog3 != null) {
                customAlertDialog3.setMessage(str);
            }
        } else if (customAlertDialog != null) {
            customAlertDialog.show();
        }
        CustomAlertDialog customAlertDialog4 = this.tipDialog;
        if (customAlertDialog4 != null) {
            customAlertDialog4.setConfirmButton(R.string.common_confirm);
        }
        CustomAlertDialog customAlertDialog5 = this.tipDialog;
        if (customAlertDialog5 != null) {
            customAlertDialog5.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseFragment$showTipDialog$1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    CustomAlertDialog tipDialog = MvpBaseFragment.this.getTipDialog();
                    if (tipDialog != null) {
                        tipDialog.dismiss();
                    }
                    MvpBaseFragment.this.onTipDialogDismiss();
                }
            });
        }
    }

    public void backgroundAlpha(float f) {
        Context context = this.mContext;
        if (context == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.app.Activity");
        }
        Window window = ((Activity) context).getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "window");
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = f;
        window.setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLogin() {
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

    public void closeKeyBoard(Activity context, View view) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object systemService = context.getSystemService("input_method");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).hideSoftInputFromWindow(view.getWindowToken(), 2);
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void showDialog() {
        showWaitingDialog();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void dismissDialog() {
        hideWaitingDialog();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.isLazyLoad = false;
        unRegisterLoginRxBus();
        P p = this.mPresenter;
        if (p != null) {
            p.detachView();
        }
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public boolean isStartLogin() {
        return startLoginActivity();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.support.p002v4.app.Fragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public Context getContext() {
        return this.mContext;
    }
}
