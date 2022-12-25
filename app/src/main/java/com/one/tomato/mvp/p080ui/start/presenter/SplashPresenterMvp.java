package com.one.tomato.mvp.p080ui.start.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.p080ui.start.impl.ISplashContact;
import com.one.tomato.mvp.p080ui.start.impl.ISplashContact$ISplashView;
import com.one.tomato.mvp.p080ui.start.view.SplashActivity;
import io.reactivex.functions.Consumer;
import kotlin.TypeCastException;

/* compiled from: SplashPresenterMvp.kt */
/* renamed from: com.one.tomato.mvp.ui.start.presenter.SplashPresenterMvp */
/* loaded from: classes3.dex */
public final class SplashPresenterMvp extends MvpBasePresenter<ISplashContact$ISplashView> implements ISplashContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    @SuppressLint({"CheckResult"})
    public void requestPermission() {
        ISplashContact$ISplashView mView = getMView();
        if (mView == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.app.Activity");
        }
        new RxPermissions((Activity) mView).request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE").subscribe(new Consumer<Boolean>() { // from class: com.one.tomato.mvp.ui.start.presenter.SplashPresenterMvp$requestPermission$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Boolean bool) {
                ISplashContact$ISplashView mView2;
                ISplashContact$ISplashView mView3;
                if (bool instanceof Boolean) {
                    if (bool.booleanValue()) {
                        mView3 = SplashPresenterMvp.this.getMView();
                        if (mView3 == null) {
                            return;
                        }
                        mView3.handlerPermission();
                        return;
                    }
                    mView2 = SplashPresenterMvp.this.getMView();
                    if (mView2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.start.view.SplashActivity");
                    }
                    SplashActivity splashActivity = (SplashActivity) mView2;
                    if (splashActivity == null) {
                        return;
                    }
                    splashActivity.showMissingPermissionDialog(null, true);
                }
            }
        });
    }
}
