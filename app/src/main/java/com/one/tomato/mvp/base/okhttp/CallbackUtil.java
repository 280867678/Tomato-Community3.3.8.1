package com.one.tomato.mvp.base.okhttp;

import android.app.Activity;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.mvp.p080ui.start.view.AdActivity;
import com.one.tomato.mvp.p080ui.start.view.SplashActivity;
import com.one.tomato.mvp.p080ui.start.view.WarnActivity;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.DBUtil;

/* loaded from: classes3.dex */
public class CallbackUtil {
    public static synchronized void loginOut(boolean z) {
        synchronized (CallbackUtil.class) {
            LoginInfo loginInfo = DBUtil.getLoginInfo();
            loginInfo.setMemberId(0);
            loginInfo.setToken("");
            loginInfo.setLiveToken("");
            loginInfo.setUserType(1);
            DBUtil.saveLoginInfo(loginInfo);
            TomatoLiveSDKUtils.getSingleton().logoutLiveSDK(AppManager.getAppManager().currentActivity());
            AppInitUtil.initAppInfoFromServer(false);
            if (filterActivity()) {
                LoginActivity.Companion.startActivity(AppManager.getAppManager().currentActivity());
            }
            if (z) {
                AppManager.getAppManager().currentActivity().runOnUiThread(new Runnable() { // from class: com.one.tomato.mvp.base.okhttp.CallbackUtil.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Activity currentActivity = AppManager.getAppManager().currentActivity();
                        if (currentActivity instanceof BaseActivity) {
                            ((BaseActivity) currentActivity).hideWaitingDialog();
                        } else if (currentActivity instanceof MvpBaseActivity) {
                            ((MvpBaseActivity) currentActivity).hideWaitingDialog();
                        }
                        CustomAlertDialog customAlertDialog = new CustomAlertDialog(AppManager.getAppManager().currentActivity());
                        customAlertDialog.bottomButtonVisiblity(2);
                        customAlertDialog.setMessage(R.string.common_account_black);
                        customAlertDialog.setConfirmButton(R.string.common_confirm);
                    }
                });
            }
        }
    }

    public static boolean filterActivity() {
        Class<?> cls = AppManager.getAppManager().currentActivity().getClass();
        return (cls == LoginActivity.class || cls == SplashActivity.class || cls == WarnActivity.class || cls == AdActivity.class) ? false : true;
    }
}
