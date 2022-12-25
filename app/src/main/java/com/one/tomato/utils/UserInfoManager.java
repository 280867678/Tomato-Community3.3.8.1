package com.one.tomato.utils;

import android.text.TextUtils;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.entity.event.LevelBeanEvent;
import com.one.tomato.entity.event.LoginInfoEvent;
import com.one.tomato.entity.event.LookTimeBeanEvent;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.LookTimeBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.post.VideoDownloadCountUtils;
import com.one.tomato.utils.post.VideoPlayCountUtils;

/* loaded from: classes3.dex */
public class UserInfoManager {
    public static void requestUserInfo(final boolean z) {
        if (DBUtil.getMemberId() == 0) {
            return;
        }
        ApiImplService.Companion.getApiImplService().getUserInfoFormService(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<UserInfo>() { // from class: com.one.tomato.utils.UserInfoManager.1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(UserInfo userInfo) {
                if (userInfo != null) {
                    String avatar = userInfo.getAvatar();
                    if (!TextUtils.isEmpty(avatar)) {
                        PreferencesUtil.getInstance().putString("login_member_head", avatar);
                    }
                }
                DBUtil.saveUserInfo(userInfo);
                TomatoLiveSDKUtils.getSingleton().loginLiveSDK(AppManager.getAppManager().currentActivity(), z);
                LoginInfoEvent loginInfoEvent = new LoginInfoEvent();
                loginInfoEvent.loginType = 1;
                RxBus.getDefault().post(loginInfoEvent);
                UserInfoManager.initLookTime();
                UserInfoManager.initUserLevel();
                UserInfoManager.initVideoDownloadInfo();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LoginInfoEvent loginInfoEvent = new LoginInfoEvent();
                loginInfoEvent.loginType = 2;
                RxBus.getDefault().post(loginInfoEvent);
                ((MvpBaseActivity) AppManager.getAppManager().currentActivity()).hideWaitingDialog();
                UserInfoManager.initLookTime();
                UserInfoManager.initUserLevel();
                UserInfoManager.initVideoDownloadInfo();
            }
        });
    }

    public static void initLookTime() {
        ApiImplService.Companion.getApiImplService().getLookTime(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<LookTimeBean>() { // from class: com.one.tomato.utils.UserInfoManager.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(LookTimeBean lookTimeBean) {
                DBUtil.saveLookTimeBean(lookTimeBean);
                VideoPlayCountUtils.getInstance().updateLookTimeBean(lookTimeBean);
                RxBus.getDefault().post(new LookTimeBeanEvent());
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                VideoPlayCountUtils.getInstance().updateLookTimeBean(null);
            }
        });
    }

    public static void initUserLevel() {
        ApiImplService.Companion.getApiImplService().requestLevelInfo(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<LevelBean>() { // from class: com.one.tomato.utils.UserInfoManager.3
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(LevelBean levelBean) {
                DBUtil.saveLevelBean(levelBean);
                RxBus.getDefault().post(new LevelBeanEvent());
            }
        });
    }

    public static void initVideoDownloadInfo() {
        ApiImplService.Companion.getApiImplService().downLoadVideoCheck(DBUtil.getMemberId(), 1).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<VideoPay>() { // from class: com.one.tomato.utils.UserInfoManager.4
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(VideoPay videoPay) {
                VideoDownloadCountUtils.getInstance().setInfo(videoPay);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                VideoDownloadCountUtils.getInstance().setInfo(null);
            }
        });
    }

    public static void loginOut() {
        ApiImplService.Companion.getApiImplService().loginOut(DeviceInfoUtil.getUniqueDeviceID(), DBUtil.getMemberId(), DBUtil.getToken()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<LoginInfo>() { // from class: com.one.tomato.utils.UserInfoManager.5
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(LoginInfo loginInfo) {
                LoginInfoEvent loginInfoEvent = new LoginInfoEvent();
                loginInfoEvent.loginType = 3;
                RxBus.getDefault().post(loginInfoEvent);
                LoginInfo loginInfo2 = DBUtil.getLoginInfo();
                loginInfo2.setMemberId(0);
                loginInfo2.setToken("");
                loginInfo2.setLiveToken("");
                loginInfo2.setUserType(1);
                DBUtil.saveLoginInfo(loginInfo2);
                TomatoLiveSDKUtils.getSingleton().logoutLiveSDK(AppManager.getAppManager().currentActivity());
                AppInitUtil.initAppInfoFromServer(false);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LoginInfoEvent loginInfoEvent = new LoginInfoEvent();
                loginInfoEvent.loginType = 4;
                RxBus.getDefault().post(loginInfoEvent);
            }
        });
    }

    public static void setUserFollowCount(boolean z) {
        UserInfo userInfo = DBUtil.getUserInfo();
        int userFollowCount = userInfo.getUserFollowCount();
        userInfo.setUserFollowCount(z ? userFollowCount + 1 : userFollowCount - 1);
        DBUtil.saveUserInfo(userInfo);
    }

    public static void setPublishCount(boolean z) {
        UserInfo userInfo = DBUtil.getUserInfo();
        int publishCount = userInfo.getPublishCount();
        userInfo.setPublishCount(z ? publishCount + 1 : publishCount - 1);
        DBUtil.saveUserInfo(userInfo);
    }

    public static void setFavorCount(boolean z) {
        UserInfo userInfo = DBUtil.getUserInfo();
        int favorCount = userInfo.getFavorCount();
        userInfo.setFavorCount(z ? favorCount + 1 : favorCount - 1);
        DBUtil.saveUserInfo(userInfo);
    }
}
