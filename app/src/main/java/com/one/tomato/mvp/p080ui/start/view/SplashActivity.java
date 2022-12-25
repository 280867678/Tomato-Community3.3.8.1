package com.one.tomato.mvp.p080ui.start.view;

import android.content.Intent;
import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.LockScreenInfo;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.start.impl.ISplashContact$ISplashView;
import com.one.tomato.mvp.p080ui.start.presenter.SplashPresenterMvp;
import com.one.tomato.p085ui.lockscreen.LockScreenPwdEnterActivity;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.VirtualDeviceUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SplashActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.SplashActivity */
/* loaded from: classes3.dex */
public final class SplashActivity extends MvpBaseActivity<ISplashContact$ISplashView, SplashPresenterMvp> implements ISplashContact$ISplashView {
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_splash;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public SplashPresenterMvp mo6439createPresenter() {
        return new SplashPresenterMvp();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        if (VirtualDeviceUtil.checkDevice()) {
            AppManager.getAppManager().exitApp();
            return;
        }
        SplashPresenterMvp mPresenter = getMPresenter();
        if (mPresenter == null) {
            return;
        }
        mPresenter.requestPermission();
    }

    @Override // com.one.tomato.mvp.p080ui.start.impl.ISplashContact$ISplashView
    public void handlerPermission() {
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isLiquidLink()) {
            AppInitUtil.initChannelID();
        } else {
            AppInitUtil.initAppInfoFromServer(true);
        }
        startActivity();
    }

    public final void startActivity() {
        if (PreferencesUtil.getInstance().getBoolean("Launcher_333", true)) {
            BaseApplication baseApplication = BaseApplication.instance;
            Intrinsics.checkExpressionValueIsNotNull(baseApplication, "BaseApplication.instance");
            if (!baseApplication.isChess()) {
                startLauncherActivity();
                return;
            }
        }
        if (!startLockActivity()) {
            startWarnActivity();
        }
    }

    public final void startLauncherActivity() {
        LauncherActivity.Companion.startActivity(this);
        finish();
    }

    public final boolean startLockActivity() {
        LockScreenInfo lockScreenInfo = DBUtil.getLockScreenInfo();
        Intrinsics.checkExpressionValueIsNotNull(lockScreenInfo, "DBUtil.getLockScreenInfo()");
        boolean z = !TextUtils.isEmpty(lockScreenInfo.getLockScreenPwd());
        if (z) {
            LockScreenPwdEnterActivity.startActivity(this);
        }
        return z;
    }

    public final void startWarnActivity() {
        WarnActivity.Companion.startActivity(this);
        finish();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().exitApp();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (800 == i2) {
            startWarnActivity();
        }
    }
}
