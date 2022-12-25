package com.one.tomato.utils;

import android.text.TextUtils;
import com.one.tomato.entity.AdLive;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.p079db.ChannelInfo;
import com.one.tomato.entity.p079db.InitAppInfo;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.OpenInstallChannelBean;
import com.one.tomato.entity.p079db.ShareParamsBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.WebAppChannel;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.circle.utils.ChannelManger;
import com.one.tomato.mvp.p080ui.circle.utils.CircleAllUtils;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.p089pm.liquidlink.LiquidLink;
import com.p089pm.liquidlink.listener.AppInstallAdapter;
import com.p089pm.liquidlink.model.AppData;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class AppInitUtil {
    public static void initChannelID() {
        int memberId = DBUtil.getMemberId();
        final OpenInstallChannelBean openChannelBean = DBUtil.getOpenChannelBean();
        ChannelInfo channelInfo = DBUtil.getChannelInfo();
        if (memberId > 0 && !TextUtils.isEmpty(channelInfo.getChannelId())) {
            LogUtil.m3784i("initChannelID ====>>> 本地保存的channelInfo");
            BaseApplication.getApplication().setChannelInfo(channelInfo.getChannelId(), channelInfo.getSubChannelId());
            initAppInfoFromServer(true);
        } else if (!TextUtils.isEmpty(openChannelBean.getOpenInstallChannelId())) {
            LogUtil.m3784i("initChannelID ====>>> 本地保存的openInstall");
            BaseApplication.getApplication().setChannelInfo(openChannelBean.getOpenInstallChannelId(), openChannelBean.getOpenInstallSubChannelId());
            initAppInfoFromServer(true);
        } else if (TextUtils.isEmpty(openChannelBean.getOpenInstallChannelId()) || !PreferencesUtil.getInstance().getBoolean("get_openinstall_channel_id")) {
            LiquidLink.getInstall(new AppInstallAdapter() { // from class: com.one.tomato.utils.AppInitUtil.1
                @Override // com.p089pm.liquidlink.listener.AppInstallAdapter
                public void onInstall(AppData appData) {
                    if (appData != null && !TextUtils.isEmpty(appData.data)) {
                        String str = appData.data;
                        LogUtil.m3784i("获取LiquidLink安装数据 ====>>> " + appData.data);
                        try {
                            JSONObject jSONObject = new JSONObject(str);
                            String string = jSONObject.getString("channelId");
                            String string2 = jSONObject.getString("subChannelId");
                            String string3 = jSONObject.getString("inviteCode");
                            if (!TextUtils.isEmpty(string)) {
                                OpenInstallChannelBean.this.setOpenInstallChannelId(string);
                            }
                            if (!TextUtils.isEmpty(string2)) {
                                OpenInstallChannelBean.this.setOpenInstallSubChannelId(string2);
                            }
                            if (!TextUtils.isEmpty(string3)) {
                                OpenInstallChannelBean.this.setOpenInstallVcode(string3);
                            }
                            DBUtil.saveOpenInstallChannelBean(OpenInstallChannelBean.this);
                            BaseApplication.getApplication().setChannelInfo(OpenInstallChannelBean.this.getOpenInstallChannelId(), OpenInstallChannelBean.this.getOpenInstallSubChannelId());
                            PreferencesUtil.getInstance().putBoolean("get_openinstall_channel_id", true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    AppInitUtil.initAppInfoFromServer(true);
                }
            });
        } else {
            initAppInfoFromServer(true);
        }
    }

    public static void initAppInfoFromServer(final boolean z) {
        String uniqueDeviceID = DeviceInfoUtil.getUniqueDeviceID();
        String openInstallVcode = DBUtil.getOpenChannelBean().getOpenInstallVcode();
        if (TextUtils.isEmpty(openInstallVcode)) {
            openInstallVcode = AppUtil.getClipData();
        }
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        String token = DBUtil.getToken();
        String versionCodeStr = AppUtil.getVersionCodeStr();
        if (openInstallVcode.length() > 8) {
            openInstallVcode = "";
        }
        apiImplService.getAppInfoFormService(uniqueDeviceID, token, versionCodeStr, openInstallVcode).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<InitAppInfo>() { // from class: com.one.tomato.utils.AppInitUtil.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(InitAppInfo initAppInfo) {
                if (!TextUtils.isEmpty(initAppInfo.getKeyApp())) {
                    SaltUtils.INSTANCE.getKeyApp(initAppInfo.getKeyApp());
                }
                ChannelInfo channelInfo = DBUtil.getChannelInfo();
                String channelId = channelInfo.getChannelId();
                if (!TextUtils.isEmpty(channelId) && !TextUtils.isEmpty(initAppInfo.getChannelId()) && !channelId.equals(initAppInfo.getChannelId())) {
                    PreferencesUtil.getInstance().putString("domainVersionCode", "");
                }
                if (!TextUtils.isEmpty(initAppInfo.getChannelId()) && !initAppInfo.getChannelId().equals("null")) {
                    channelInfo.setChannelId(initAppInfo.getChannelId());
                    channelInfo.setSubChannelId(initAppInfo.getSubChannelId());
                    DBUtil.saveChannelInfo(channelInfo);
                    BaseApplication.getApplication().setChannelInfo(channelInfo.getChannelId(), channelInfo.getSubChannelId());
                    TomatoLiveSDKUtils.getSingleton().updateAdChannelField();
                }
                DBUtil.saveInitAppInfo(initAppInfo);
                LoginInfo loginInfo = DBUtil.getLoginInfo();
                boolean z2 = true;
                if (initAppInfo.getUserType() == 1) {
                    loginInfo.setMemberId(initAppInfo.getMemberId());
                    loginInfo.setToken(initAppInfo.getToken());
                    loginInfo.setLiveToken(initAppInfo.getLiveToken());
                    loginInfo.setUserType(initAppInfo.getUserType());
                    DBUtil.saveLoginInfo(loginInfo);
                }
                if (initAppInfo.getUserType() != 1) {
                    z2 = false;
                }
                UserInfoManager.requestUserInfo(z2);
                AppInitUtil.initRequest(z);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                AppInitUtil.initRequest(z);
            }
        });
    }

    public static void initAppInfoFromServerForSalt() {
        String uniqueDeviceID = DeviceInfoUtil.getUniqueDeviceID();
        String openInstallVcode = DBUtil.getOpenChannelBean().getOpenInstallVcode();
        if (TextUtils.isEmpty(openInstallVcode)) {
            openInstallVcode = AppUtil.getClipData();
        }
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        String token = DBUtil.getToken();
        String versionCodeStr = AppUtil.getVersionCodeStr();
        if (openInstallVcode.length() > 8) {
            openInstallVcode = "";
        }
        apiImplService.getAppInfoFormService(uniqueDeviceID, token, versionCodeStr, openInstallVcode).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<InitAppInfo>() { // from class: com.one.tomato.utils.AppInitUtil.3
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(InitAppInfo initAppInfo) {
                if (initAppInfo == null || TextUtils.isEmpty(initAppInfo.getKeyApp())) {
                    return;
                }
                SaltUtils.INSTANCE.getKeyApp(initAppInfo.getKeyApp());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initRequest(boolean z) {
        if (z) {
            requestAd();
            requestSystemParam();
            requestPostShare();
            requestAppShare();
            requestLiveAd();
            requestWebappChanel();
            DataUploadUtil.activateApp();
            DataUploadUtil.uploadAppListInfo();
            ChannelManger.INSTANCE.requestChannelList();
            CircleAllUtils.INSTANCE.initCircle();
            AppIdHistoryManger.INSTANCE.getAppidHistory();
            CircleAllUtils.INSTANCE.initChessHomeData();
        }
    }

    private static void requestSystemParam() {
        ApiImplService.Companion.getApiImplService().requestSystemParams().subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<SystemParam>() { // from class: com.one.tomato.utils.AppInitUtil.4
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(SystemParam systemParam) {
                SaltUtils.INSTANCE.saltOne(systemParam.getKeyApp(), 2);
                DBUtil.saveSystemParam(systemParam);
            }
        });
    }

    private static void requestAd() {
        ApiImplService.Companion.getApiImplService().requestAd().subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<C2516Ad>() { // from class: com.one.tomato.utils.AppInitUtil.5
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(C2516Ad c2516Ad) {
                SaltUtils.INSTANCE.saltOne(c2516Ad.getKeyApp(), 1);
                DBUtil.saveAdPage(c2516Ad);
            }
        });
    }

    private static void requestLiveAd() {
        ApiImplService.Companion.getApiImplService().requestLiveAd().subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<ArrayList<AdLive>>() { // from class: com.one.tomato.utils.AppInitUtil.6
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<AdLive> arrayList) {
                DBUtil.saveLiveAd(arrayList);
            }
        });
    }

    private static void requestPostShare() {
        ApiImplService.Companion.getApiImplService().sharePost(DBUtil.getMemberId(), BaseApplication.getApplication().isChess() ? 2 : 1).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<ShareParamsBean>() { // from class: com.one.tomato.utils.AppInitUtil.7
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ShareParamsBean shareParamsBean) {
                SaltUtils.INSTANCE.saltOne(shareParamsBean.getKeyApp(), 3);
                DBUtil.saveShareParams(shareParamsBean);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3787d("yan1", "获取帖子分享报错------" + responseThrowable.getThrowableMessage());
            }
        });
    }

    private static void requestAppShare() {
        ApiImplService.Companion.getApiImplService().shareApp(BaseApplication.getApplication().isChess() ? 2 : 1).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<ShareParamsBean>() { // from class: com.one.tomato.utils.AppInitUtil.8
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ShareParamsBean shareParamsBean) {
                SaltUtils.INSTANCE.saltOne(shareParamsBean.getKeyApp(), 4);
                DBUtil.saveShareParams(shareParamsBean);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3787d("yan1", "获取帖子分享报错------" + responseThrowable.getThrowableMessage());
            }
        });
    }

    private static void requestWebappChanel() {
        ApiImplService.Companion.getApiImplService().requestWebappChannel().subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<ArrayList<WebAppChannel>>() { // from class: com.one.tomato.utils.AppInitUtil.9
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<WebAppChannel> arrayList) {
                DBUtil.saveWebAppChanelList(arrayList);
            }
        });
    }
}
