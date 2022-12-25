package com.one.tomato.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.LoadingDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.event.LoginInfoEvent;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.net.LoginOutResponseObserver;
import com.one.tomato.net.LoginResponseObserver;
import com.one.tomato.net.ResponseObserver;
import com.trello.rxlifecycle2.components.support.RxFragment;
import de.greenrobot.event.EventBus;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.xutils.C5540x;

/* loaded from: classes3.dex */
public class BaseFragment extends RxFragment implements ResponseObserver, LoginResponseObserver, LoginOutResponseObserver {
    protected String TAG;
    protected boolean isLazyLoad;
    protected boolean isViewCreated;
    protected boolean isVisibleToUser;
    private LoadingDialog loadingDialog;
    private Disposable observeLogInAndOut;
    protected CustomAlertDialog tipDialog;
    private boolean injected = false;
    protected Context mContext = null;

    public void onEventMainThread(LoginInfoEvent loginInfoEvent) {
    }

    protected void onInvisibleLoad() {
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

    protected void rePlaceLazyLoad() {
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.TAG = getClass().getSimpleName();
        this.mContext = getActivity();
        EventBus.getDefault().register(this);
        registerLoginRxBus();
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        this.injected = true;
        this.isViewCreated = true;
        return C5540x.view().inject(this, layoutInflater, viewGroup);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (!this.injected) {
            C5540x.view().inject(this, getView());
        }
        this.isViewCreated = true;
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

    public String getTAG() {
        return this.TAG;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onStop() {
        super.onStop();
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
            this.loadingDialog = new LoadingDialog(this.mContext);
        }
    }

    public void showTipDialog(int i) {
        showTipDialog(this.mContext.getResources().getString(i));
    }

    public void showTipDialog(String str) {
        CustomAlertDialog customAlertDialog = this.tipDialog;
        if (customAlertDialog == null) {
            this.tipDialog = new CustomAlertDialog(this.mContext);
            this.tipDialog.bottomButtonVisiblity(2);
            this.tipDialog.setMessage(str);
        } else {
            customAlertDialog.show();
        }
        this.tipDialog.setConfirmButton(R.string.common_confirm);
        this.tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.base.BaseFragment.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                BaseFragment.this.tipDialog.dismiss();
                BaseFragment.this.onTipDialogDismiss();
            }
        });
    }

    public boolean isBlack(int i) {
        if (i == 1) {
            showTipDialog(R.string.manage_is_black);
            return true;
        }
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
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
        this.observeLogInAndOut = RxBus.getDefault().toObservable(LoginInfoEvent.class).subscribe(new Consumer<LoginInfoEvent>() { // from class: com.one.tomato.base.BaseFragment.2
            @Override // io.reactivex.functions.Consumer
            public void accept(LoginInfoEvent loginInfoEvent) throws Exception {
                int i = loginInfoEvent.loginType;
                if (i == 1) {
                    BaseFragment.this.onLoginSuccess();
                } else if (i == 2) {
                    BaseFragment.this.onLoginFail();
                } else if (i == 3) {
                    BaseFragment.this.onLoginOutSuccess();
                } else if (i == 4) {
                    BaseFragment.this.onLoginOutFail();
                } else if (i != 5) {
                } else {
                    BaseFragment.this.onLoginCancel();
                }
            }
        });
        RxSubscriptions.add(this.observeLogInAndOut);
    }

    private void unRegisterLoginRxBus() {
        RxSubscriptions.remove(this.observeLogInAndOut);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegisterLoginRxBus();
    }

    public void setMemberFocusEvent(int i, int i2) {
        MemberFocusEvent memberFocusEvent = new MemberFocusEvent();
        memberFocusEvent.followFlag = i;
        memberFocusEvent.f1748id = i2;
        EventBus.getDefault().post(memberFocusEvent);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }
}
