package com.tomatolive.library;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.text.TextUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.gyf.barlibrary.ImmersionBar;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.download.NobilityDownLoadManager;
import com.tomatolive.library.download.ResHotLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.StatisticsApiRetrofit;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.http.utils.RetryWithDelayUtils;
import com.tomatolive.library.model.AppLiveItemEntity;
import com.tomatolive.library.model.IndexRankEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveStatusEntity;
import com.tomatolive.library.model.SysParamsInfoEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.event.LoginEvent;
import com.tomatolive.library.model.event.LogoutEvent;
import com.tomatolive.library.model.event.UpdateBalanceEvent;
import com.tomatolive.library.p136ui.activity.home.RankingNewActivity;
import com.tomatolive.library.p136ui.activity.mylive.MyLiveActivity;
import com.tomatolive.library.p136ui.interfaces.OnAppRankingCallback;
import com.tomatolive.library.p136ui.view.faceunity.FaceConstant;
import com.tomatolive.library.p136ui.view.task.TaskBoxUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SensorsDataAPIUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.litepal.LitePal;
import com.tomatolive.library.utils.live.LiveManagerUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.youdao.sdk.app.YouDaoApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes3.dex */
public class TomatoLiveSDK {
    public String ADV_CHANNEL_ID;
    public String ADV_CHANNEL_TYPE;
    public String API_URL;
    public String APP_ID;
    public String APP_KEY;
    public String APP_NAME;
    public String CUSTOMIZE_GAME;
    public String DATA_REPORT_CONFIG_URL;
    public String DATA_REPORT_URL;
    public String ENCRYPT_API_KEY;
    public String ENCRYPT_SOCKET_KEY;
    public String GAME_CHANNEL;
    public String IMG_DOWN_URL;
    public String IMG_UP_URL;
    public String SIGN_API_KEY;
    public String SIGN_SOCKET_KEY;
    private String YOUDAO_KEY;
    private Application application;
    public TomatoLiveSDKCallbackListener sdkCallbackListener;

    /* loaded from: classes3.dex */
    public interface LiveSDKLoginCallbackListener {
        void onLoginFailListener(Context context);

        void onLoginSuccessListener(Context context);
    }

    /* loaded from: classes3.dex */
    public interface LiveSDKLogoutCallbackListener {
        void onLogoutFailListener(Context context);

        void onLogoutSuccessListener(Context context);
    }

    /* loaded from: classes3.dex */
    public interface OnAdvChannelCallbackListener {
        void onAdvDataFail(int i, String str);

        void onAdvDataSuccess(Context context, String str);
    }

    /* loaded from: classes3.dex */
    public interface OnCommonCallbackListener {
        void onDataFail(Throwable th, int i);

        void onDataSuccess(Context context, Object obj);
    }

    /* loaded from: classes3.dex */
    public interface OnLiveStatusCallbackListener {
        void onLiveStatusFail(int i, String str);

        void onLiveStatusSuccess(Context context, LiveStatusEntity liveStatusEntity);
    }

    /* loaded from: classes3.dex */
    public interface TomatoLiveSDKCallbackListener {
        void onAdClickListener(Context context, String str);

        void onAdvChannelHitsListener(Context context, String str, String str2);

        void onAdvChannelListener(Context context, String str, OnAdvChannelCallbackListener onAdvChannelCallbackListener);

        void onAdvChannelLiveNoticeListener(Context context, OnAdvChannelCallbackListener onAdvChannelCallbackListener);

        void onAppCommonCallbackListener(Context context, int i, OnCommonCallbackListener onCommonCallbackListener);

        boolean onEnterLivePermissionListener(Context context, int i);

        void onGiftRechargeListener(Context context);

        void onIncomeWithdrawalListener(Context context);

        void onLiveGameJSListener(Context context, String str);

        void onLoginListener(Context context);

        void onScoreListener(Context context);

        void onUserHomepageListener(Context context, UserEntity userEntity);

        void onUserOfflineListener(Context context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$0(Throwable th) throws Exception {
    }

    private TomatoLiveSDK() {
        this.YOUDAO_KEY = "1fdd2bb053fdb5bf";
        this.sdkCallbackListener = null;
        this.API_URL = "";
        this.IMG_UP_URL = "";
        this.IMG_DOWN_URL = "";
        this.APP_KEY = "";
        this.APP_ID = "";
        this.APP_NAME = "";
        this.SIGN_API_KEY = "8zy8nbs9lyddx02slcz8ypmwcr2tlu72";
        this.SIGN_SOCKET_KEY = "8zy8nbs9lyddx02slcz8ypmwcr2tlu72";
        this.ENCRYPT_API_KEY = ConstantUtils.ENCRYPT_FILE_KEY;
        this.ENCRYPT_SOCKET_KEY = "246887c3-ee20-4fe8-a320-1fde4a8d10b6";
        this.ADV_CHANNEL_TYPE = "";
        this.ADV_CHANNEL_ID = "";
        this.GAME_CHANNEL = "";
        this.CUSTOMIZE_GAME = "";
        this.DATA_REPORT_URL = "";
        this.DATA_REPORT_CONFIG_URL = "";
        RxJavaPlugins.setErrorHandler($$Lambda$TomatoLiveSDK$LhJubGeHOFCrBWAnakfgkIqVdLQ.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final TomatoLiveSDK INSTANCE = new TomatoLiveSDK();
    }

    public static TomatoLiveSDK getSingleton() {
        return SingletonHolder.INSTANCE;
    }

    public void initSDK(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, String str9, String str10, String str11, TomatoLiveSDKCallbackListener tomatoLiveSDKCallbackListener) {
        initSDK(application, str, str2, str3, str4, str5, str6, str7, str8, false, null, z, str9, str10, str11, tomatoLiveSDKCallbackListener);
    }

    public void initSDK(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, byte[] bArr, boolean z2, String str9, String str10, String str11, TomatoLiveSDKCallbackListener tomatoLiveSDKCallbackListener) {
        initSDK(application, str, str2, str3, str4, str5, str6, str7, str8, z, bArr, z2, false, true, str9, str10, str11, tomatoLiveSDKCallbackListener);
    }

    public void initSDK(Application application, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, byte[] bArr, boolean z2, boolean z3, boolean z4, String str9, String str10, String str11, TomatoLiveSDKCallbackListener tomatoLiveSDKCallbackListener) {
        this.application = application;
        this.sdkCallbackListener = tomatoLiveSDKCallbackListener;
        this.APP_ID = str;
        this.APP_KEY = str2;
        this.APP_NAME = str3;
        this.API_URL = str4;
        this.IMG_UP_URL = str5;
        this.IMG_DOWN_URL = str6;
        this.ADV_CHANNEL_ID = str7;
        this.ADV_CHANNEL_TYPE = str8;
        this.GAME_CHANNEL = str9;
        this.CUSTOMIZE_GAME = str10;
        formatSignEncryptKey(z2);
        updateDataReportUrl(str11);
        Utils.init(application);
        YouDaoApplication.init(application, this.YOUDAO_KEY);
        LitePal.initialize(application);
        initHttpCacheDir(application);
        UserInfoManager.getInstance().setAppId(str);
        SysConfigInfoManager.getInstance().setEnableVisitorPermission(z3);
        SysConfigInfoManager.getInstance().setEnableExchangeProportion(z4);
        SPUtils.getInstance().put(FaceConstant.IS_OPEN_BEAUTY, z);
        SPUtils.getInstance().put(ConstantUtils.SHOW_MOBIE_TIP, false);
        if (z) {
            SPUtils.getInstance().put(FaceConstant.AUTH_ENCRYPT_STR, getAuthStr(bArr));
        }
        CacheUtils.cleanCacheDisk(ConstantUtils.GENERAL_TURNTABLE_KEY, ConstantUtils.LUXURY_TURNTABLE_KEY);
    }

    public void updateServerUrl(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str)) {
            this.API_URL = str;
        }
        if (!TextUtils.isEmpty(str2)) {
            this.IMG_UP_URL = str2;
        }
        if (!TextUtils.isEmpty(str3)) {
            this.IMG_DOWN_URL = str3;
        }
    }

    public void updateDataReportUrl(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.DATA_REPORT_URL = str;
            this.DATA_REPORT_CONFIG_URL = str;
        }
    }

    public void updateAdvChannelField(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.ADV_CHANNEL_ID = str;
        }
        if (!TextUtils.isEmpty(str2)) {
            this.ADV_CHANNEL_TYPE = str2;
        }
    }

    public void updateGameChannelField(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.GAME_CHANNEL = str;
        }
        if (!TextUtils.isEmpty(str2)) {
            this.CUSTOMIZE_GAME = str2;
        }
    }

    public void updateAppId(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.APP_ID = str;
            UserInfoManager.getInstance().setAppId(str);
        }
    }

    public void setApiKey(String str, String str2, String str3, String str4) {
        this.SIGN_API_KEY = str;
        this.ENCRYPT_API_KEY = str2;
        this.SIGN_SOCKET_KEY = str3;
        this.ENCRYPT_SOCKET_KEY = str4;
    }

    public void setHighBeauty(boolean z) {
        SPUtils.getInstance().put(FaceConstant.IS_OPEN_BEAUTY, z);
    }

    public void setHighBeauty(boolean z, byte[] bArr) {
        SPUtils.getInstance().put(FaceConstant.IS_OPEN_BEAUTY, z);
        if (z) {
            SPUtils.getInstance().put(FaceConstant.AUTH_ENCRYPT_STR, getAuthStr(bArr));
        }
    }

    public void initAnim() {
        GiftDownLoadManager.getInstance().updateAnimOnlineRes();
        NobilityDownLoadManager.getInstance().updateAnimOnlineAllRes();
    }

    public Application getApplication() {
        return this.application;
    }

    public boolean isLiveAdvChannel() {
        return TextUtils.isEmpty(this.ADV_CHANNEL_TYPE) || TextUtils.equals(this.ADV_CHANNEL_TYPE, "1");
    }

    public boolean isLiveGameChannel() {
        return TextUtils.isEmpty(this.CUSTOMIZE_GAME) || TextUtils.equals(this.CUSTOMIZE_GAME, "1");
    }

    public void toLiveActivity(Context context, String str) {
        LiveEntity liveEntity = new LiveEntity();
        liveEntity.liveId = str;
        AppUtils.startTomatoLiveActivity(context, liveEntity, "2", context.getString(R$string.fq_live_enter_source_home_page));
    }

    public void toMyLiveActivity(Context context) {
        if (!AppUtils.isConsumptionPermissionUser(context)) {
            return;
        }
        context.startActivity(new Intent(context, MyLiveActivity.class));
    }

    public void initSysConfig() {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getSysParamsInfoService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<SysParamsInfoEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.3
        }).onErrorResumeNext(new HttpResultFunction<SysParamsInfoEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.2
        }).retryWhen(new RetryWithDelayUtils(3, 3)).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<SysParamsInfoEntity>(false) { // from class: com.tomatolive.library.TomatoLiveSDK.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(SysParamsInfoEntity sysParamsInfoEntity) {
                int i = 60;
                if (sysParamsInfoEntity == null) {
                    SysConfigInfoManager.getInstance().setUserGradeMax(60);
                    return;
                }
                SysConfigInfoManager.getInstance().setEnableReport(sysParamsInfoEntity.isEnableReport());
                SysConfigInfoManager.getInstance().setEnableSticker(sysParamsInfoEntity.isEnableSticker());
                SysConfigInfoManager.getInstance().setEnableNobility(sysParamsInfoEntity.isEnableNobility());
                SysConfigInfoManager.getInstance().setEnableTurntable(sysParamsInfoEntity.isEnableTurntable());
                SysConfigInfoManager.getInstance().setEnableAnchorHomepage(sysParamsInfoEntity.isEnableAnchorHomepage());
                SysConfigInfoManager.getInstance().setEnableUserHomepage(sysParamsInfoEntity.isEnableUserHomepage());
                SysConfigInfoManager.getInstance().setEnableGuard(sysParamsInfoEntity.isEnableGuard());
                SysConfigInfoManager.getInstance().setEnableVisitorLive(sysParamsInfoEntity.isEnableVisitorLive());
                SysConfigInfoManager.getInstance().setEnableScore(sysParamsInfoEntity.isEnableScore());
                SysConfigInfoManager.getInstance().setEnableWeekStar(sysParamsInfoEntity.isEnableWeekStar());
                SysConfigInfoManager.getInstance().setEnableVideoStreamEncode(sysParamsInfoEntity.isEnableVideoStreamEncode());
                SysConfigInfoManager.getInstance().setEnableGiftWall(sysParamsInfoEntity.isEnableGiftWall());
                SysConfigInfoManager.getInstance().setEnableAchievement(sysParamsInfoEntity.isEnableAchievement());
                SysConfigInfoManager.getInstance().setEnableComponents(sysParamsInfoEntity.isEnableComponents());
                SysConfigInfoManager.getInstance().setEnableTranslation(sysParamsInfoEntity.isEnableTranslation());
                SysConfigInfoManager.getInstance().setEnablePrivateMsg(sysParamsInfoEntity.isEnablePrivateMsg());
                SysConfigInfoManager.getInstance().setEnableFeeTag(sysParamsInfoEntity.isEnableFeeTag());
                SysConfigInfoManager.getInstance().setEnableLiveHelperJump(sysParamsInfoEntity.isEnableLiveHelperJump());
                SysConfigInfoManager.getInstance().setWatermarkConfig(sysParamsInfoEntity.watermarkConfig);
                SysConfigInfoManager.getInstance().setEnableLogEventReport(sysParamsInfoEntity.isEnableLogEventReport());
                SysConfigInfoManager.getInstance().setEnableQMInteract(sysParamsInfoEntity.isEnableIntimate());
                SysConfigInfoManager.getInstance().setEnableTranslationLevel(sysParamsInfoEntity.enableTranslationLevel);
                SysConfigInfoManager.getInstance().setEntryNoticeLevelThreshold(sysParamsInfoEntity.entryNoticeLevelThreshold);
                SysConfigInfoManager.getInstance().setGradeSet10CharacterLimit(sysParamsInfoEntity.gradeSet10CharacterLimit);
                SysConfigInfoManager.getInstance().setGiftTrumpetPlayPeriod(sysParamsInfoEntity.giftTrumpetPlayPeriod);
                SysConfigInfoManager.getInstance().setOnlineCountSynInterval(sysParamsInfoEntity.onlineCountSynInterval);
                SysConfigInfoManager.getInstance().setNobilityTypeThresholdForHasPreventBanned(sysParamsInfoEntity.nobilityTypeThresholdForHasPreventBanned);
                SysConfigInfoManager.getInstance().setLiveRankConfig(sysParamsInfoEntity.liveRankConfig);
                SysConfigInfoManager.getInstance().setSocketHeartBeatInterval(NumberUtils.string2long(sysParamsInfoEntity.socketHeartBeatInterval));
                SysConfigInfoManager.getInstance().setOnlineUserLevelFilter(sysParamsInfoEntity.liveOnlineUserListLevelFilter);
                SysConfigInfoManager.getInstance().setOnlineUserListSize(sysParamsInfoEntity.liveInitOnlineUserListSize);
                SysConfigInfoManager.getInstance().setEnableTurntableUpdateTip(sysParamsInfoEntity.isEnableTurntableUpdateTip());
                SensorsDataAPIUtils.initSensorsDataAPI(TomatoLiveSDK.this.application);
                ResHotLoadManager.getInstance().dealResLoad(sysParamsInfoEntity);
                if (!sysParamsInfoEntity.isEnableGrade120()) {
                    SysConfigInfoManager.getInstance().setUserGradeMax(60);
                } else if (AppUtils.isUserGradeMax120()) {
                } else {
                    SysConfigInfoManager sysConfigInfoManager = SysConfigInfoManager.getInstance();
                    if (sysParamsInfoEntity.isEnableGrade120()) {
                        i = 120;
                    }
                    sysConfigInfoManager.setUserGradeMax(i);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                if (AppUtils.isUserGradeMax120()) {
                    return;
                }
                SysConfigInfoManager.getInstance().setUserGradeMax(60);
            }
        });
    }

    public void loginSDK(Context context) {
        loginSDK(context, null);
    }

    public void loginSDK(final Context context, final LiveSDKLoginCallbackListener liveSDKLoginCallbackListener) {
        if (context != null && AppUtils.isApiService()) {
            LogEventUtils.uploadLoginButtonClick();
            ApiRetrofit.getInstance().getApiService().getSdkLoginService(new RequestParams().getSDKLoginParams()).map(new ServerResultFunction<UserEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.6
            }).onErrorResumeNext(new HttpResultFunction<UserEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.5
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(context, new ResultCallBack<UserEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.4
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(UserEntity userEntity) {
                    if (userEntity == null) {
                        return;
                    }
                    UserInfoManager.getInstance().setToken(userEntity.getToken());
                    UserInfoManager.getInstance().setUserId(userEntity.getUserId());
                    UserInfoManager.getInstance().setNobilityType(userEntity.getNobilityType());
                    UserInfoManager.getInstance().setEnterHide(userEntity.isLiveEnterHideBoolean());
                    UserInfoManager.getInstance().setInBanGroup(userEntity.isInBanGroup());
                    UserInfoManager.getInstance().setSuperAdmin(userEntity.isSuperAdmin());
                    UserInfoManager.getInstance().setVisitorUser(userEntity.isVisitorUser());
                    UserInfoManager.getInstance().setEnterLivePermission(userEntity.isEnterLivePermissionBoolean());
                    LogEventUtils.uploadLoginSuccess(userEntity.getUserId(), true);
                    if (userEntity.isNewUserBoolean()) {
                        LogEventUtils.uploadRegisterSuccess(userEntity.getUserId(), AppUtils.isVisitor() ? "2" : "1", 1);
                    }
                    DBUtils.saveAllAttentionAnchor(userEntity.getFollowTargetIds());
                    DBUtils.saveAllShieldUser(userEntity.getShieldTargetIds());
                    if (!UserInfoManager.getInstance().isVisitorUser()) {
                        EventBus.getDefault().post(new LoginEvent());
                    }
                    LiveSDKLoginCallbackListener liveSDKLoginCallbackListener2 = liveSDKLoginCallbackListener;
                    if (liveSDKLoginCallbackListener2 == null) {
                        return;
                    }
                    liveSDKLoginCallbackListener2.onLoginSuccessListener(context);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str) {
                    UserInfoManager.getInstance().setToken(null);
                    UserInfoManager.getInstance().setUserId(null);
                    UserInfoManager.getInstance().setNobilityType(-1);
                    LiveSDKLoginCallbackListener liveSDKLoginCallbackListener2 = liveSDKLoginCallbackListener;
                    if (liveSDKLoginCallbackListener2 != null) {
                        liveSDKLoginCallbackListener2.onLoginFailListener(context);
                    }
                }
            }));
        }
    }

    public void exitSDK(Context context) {
        exitSDK(context, null);
    }

    public void exitSDK(final Context context, final LiveSDKLogoutCallbackListener liveSDKLogoutCallbackListener) {
        if (context != null && AppUtils.isApiService()) {
            ApiRetrofit.getInstance().getApiService().getExitSDKService(new RequestParams().getExitSDKParams()).map(new ServerResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.9
            }).onErrorResumeNext(new HttpResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.8
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(context, new ResultCallBack<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.7
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    EventBus.getDefault().post(new LogoutEvent());
                    UserInfoManager.getInstance().clearLoginInfo();
                    TaskBoxUtils.getInstance().clear();
                    LiveSDKLogoutCallbackListener liveSDKLogoutCallbackListener2 = liveSDKLogoutCallbackListener;
                    if (liveSDKLogoutCallbackListener2 != null) {
                        liveSDKLogoutCallbackListener2.onLogoutSuccessListener(context);
                    }
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str) {
                    LiveSDKLogoutCallbackListener liveSDKLogoutCallbackListener2 = liveSDKLogoutCallbackListener;
                    if (liveSDKLogoutCallbackListener2 != null) {
                        liveSDKLogoutCallbackListener2.onLogoutFailListener(context);
                    }
                }
            }));
        }
    }

    public void onUpdateUserAvatar(Context context, final String str) {
        if (context != null && AppUtils.isApiService()) {
            ApiRetrofit.getInstance().getApiService().getUpdateUserInfoService(new RequestParams().getUpdateAvatarParams(str)).map(new ServerResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.12
            }).onErrorResumeNext(new HttpResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.11
            }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new HttpRxObserver(context, new ResultCallBack<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.10
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str2) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    UserInfoManager.getInstance().setAvatar(str);
                }
            }));
        }
    }

    public void onUpdateUserNickName(Context context, final String str) {
        if (context != null && AppUtils.isApiService()) {
            ApiRetrofit.getInstance().getApiService().getUpdateUserInfoService(new RequestParams().getUpdateNicknameParams(str)).map(new ServerResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.15
            }).onErrorResumeNext(new HttpResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.14
            }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new HttpRxObserver(context, new ResultCallBack<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.13
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str2) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    UserInfoManager.getInstance().setNickname(str);
                }
            }));
        }
    }

    public void onUpdateUserSex(Context context, final String str) {
        if (context != null && AppUtils.isApiService()) {
            ApiRetrofit.getInstance().getApiService().getUpdateUserInfoService(new RequestParams().getUpdateSexParams(str)).map(new ServerResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.18
            }).onErrorResumeNext(new HttpResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.17
            }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new HttpRxObserver(context, new ResultCallBack<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.16
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str2) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    UserInfoManager.getInstance().setSex(str);
                }
            }));
        }
    }

    public void onUpdateUserRisk(Context context, final String str) {
        if (context != null && AppUtils.isApiService()) {
            ApiRetrofit.getInstance().getApiService().getUpdateUserInfoService(new RequestParams().getUpdateIsRiskParams(str)).map(new ServerResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.21
            }).onErrorResumeNext(new HttpResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.20
            }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new HttpRxObserver(context, new ResultCallBack<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.19
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str2) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    UserInfoManager.getInstance().setRisk(str);
                }
            }));
        }
    }

    public void onAnchorLiveStatus(final Context context, String str, final OnLiveStatusCallbackListener onLiveStatusCallbackListener) {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getLiveStatusService(new RequestParams().getLiveStatusParams(UserInfoManager.getInstance().getAppId(), str)).map(new ServerResultFunction<LiveStatusEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.24
        }).onErrorResumeNext(new HttpResultFunction<LiveStatusEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.23
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(context, new ResultCallBack<LiveStatusEntity>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.22
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LiveStatusEntity liveStatusEntity) {
                OnLiveStatusCallbackListener onLiveStatusCallbackListener2 = onLiveStatusCallbackListener;
                if (onLiveStatusCallbackListener2 != null) {
                    onLiveStatusCallbackListener2.onLiveStatusSuccess(context, liveStatusEntity);
                }
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                OnLiveStatusCallbackListener onLiveStatusCallbackListener2 = onLiveStatusCallbackListener;
                if (onLiveStatusCallbackListener2 != null) {
                    onLiveStatusCallbackListener2.onLiveStatusFail(i, str2);
                }
            }
        }));
    }

    public void onUpdateBalance() {
        EventBus.getDefault().post(new UpdateBalanceEvent());
    }

    public void clearUserTokenInfo() {
        UserInfoManager.getInstance().clearTokenInfo();
    }

    public void onAppLiveListCallback(int i, int i2, final ResultCallBack<String> resultCallBack) {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getLiveOpenListService(new RequestParams().getLiveOpenListParams(i, 1, i2)).map(new ServerResultFunction<HttpResultPageModel<LiveEntity>>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.27
        }).flatMap(new Function<HttpResultPageModel<LiveEntity>, ObservableSource<String>>() { // from class: com.tomatolive.library.TomatoLiveSDK.26
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<String> mo6755apply(HttpResultPageModel<LiveEntity> httpResultPageModel) throws Exception {
                return Observable.just(TomatoLiveSDK.this.formatAppLiveListJson(httpResultPageModel));
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<String>(false) { // from class: com.tomatolive.library.TomatoLiveSDK.25
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(String str) {
                if (resultCallBack != null) {
                    TomatoLiveSDK.this.onAllLiveListUpdate(null);
                    resultCallBack.onSuccess(str);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i3, String str) {
                super.onError(i3, str);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(i3, str);
                }
            }
        });
    }

    public void onAppRankingCallback(final OnAppRankingCallback onAppRankingCallback) {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getIndexRankService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<IndexRankEntity>>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.30
        }).onErrorResumeNext(new HttpResultFunction<List<IndexRankEntity>>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.29
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<IndexRankEntity>>(this, false) { // from class: com.tomatolive.library.TomatoLiveSDK.28
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<IndexRankEntity> list) {
                if (list == null) {
                    return;
                }
                List<String> arrayList = new ArrayList<>();
                List<String> arrayList2 = new ArrayList<>();
                if (list.isEmpty()) {
                    OnAppRankingCallback onAppRankingCallback2 = onAppRankingCallback;
                    if (onAppRankingCallback2 == null) {
                        return;
                    }
                    onAppRankingCallback2.onAppRankingSuccess(arrayList, arrayList2);
                    return;
                }
                for (IndexRankEntity indexRankEntity : list) {
                    if (TextUtils.equals(indexRankEntity.getType(), ConstantUtils.RANK_TYPE_INCOME)) {
                        arrayList = indexRankEntity.getAvatars();
                    }
                    if (TextUtils.equals(indexRankEntity.getType(), ConstantUtils.RANK_TYPE_EXPENSE)) {
                        arrayList2 = indexRankEntity.getAvatars();
                    }
                }
                OnAppRankingCallback onAppRankingCallback3 = onAppRankingCallback;
                if (onAppRankingCallback3 == null) {
                    return;
                }
                onAppRankingCallback3.onAppRankingSuccess(arrayList, arrayList2);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                OnAppRankingCallback onAppRankingCallback2 = onAppRankingCallback;
                if (onAppRankingCallback2 != null) {
                    onAppRankingCallback2.onAppRankingFail();
                }
            }
        });
    }

    public void toRankingActivity(Context context, int i) {
        if (i > 1 || i < 0) {
            return;
        }
        Intent intent = new Intent(context, RankingNewActivity.class);
        intent.putExtra(ConstantUtils.RESULT_FLAG, i);
        if (AppUtils.isEnableWeekStar()) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(ConstantUtils.RANK_TYPE_WEEKSTAR);
            intent.putStringArrayListExtra(ConstantUtils.RESULT_ITEM, arrayList);
        }
        context.startActivity(intent);
    }

    public void statisticsReport(String str) {
        if (!StatisticsApiRetrofit.getInstance().isApiService()) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(AopConstants.APP_EVENT_KEY, str);
        StatisticsApiRetrofit.getInstance().getApiService().statisticsReportService(hashMap).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<ResponseBody>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.31
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(ResponseBody responseBody) {
            }
        });
    }

    public void clickBannerReport(Context context, String str) {
        if (!AppUtils.isApiService()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getClickAdService(new RequestParams().getClickAdParams(str)).map(new ServerResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.34
        }).onErrorResumeNext(new HttpResultFunction<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.33
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new HttpRxObserver(context, new ResultCallBack<Object>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.32
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
            }
        }));
    }

    public void onAllLiveListUpdate(LifecycleTransformer lifecycleTransformer) {
        onAllLiveListUpdate(lifecycleTransformer, null);
    }

    public void onAllLiveListUpdate(LifecycleTransformer lifecycleTransformer, final ResultCallBack<List<LiveEntity>> resultCallBack) {
        if (!AppUtils.isApiService()) {
            return;
        }
        Observable observeOn = ApiRetrofit.getInstance().getApiService().getAllListService(new RequestParams().getPageListParams(1, 40)).map(new ServerResultFunction<HttpResultPageModel<LiveEntity>>(this) { // from class: com.tomatolive.library.TomatoLiveSDK.35
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io());
        if (lifecycleTransformer != null) {
            observeOn.compose(lifecycleTransformer);
        }
        observeOn.subscribe(new Consumer() { // from class: com.tomatolive.library.-$$Lambda$TomatoLiveSDK$DatQA3QcolUJn_L_jJkT0gUuuug
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                TomatoLiveSDK.lambda$onAllLiveListUpdate$1(ResultCallBack.this, (HttpResultPageModel) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onAllLiveListUpdate$1(ResultCallBack resultCallBack, HttpResultPageModel httpResultPageModel) throws Exception {
        if (httpResultPageModel == null) {
            return;
        }
        LiveManagerUtils.getInstance().setLiveList(httpResultPageModel.dataList);
        if (resultCallBack == null) {
            return;
        }
        resultCallBack.onSuccess(httpResultPageModel.dataList);
    }

    private String getAuthStr(byte[] bArr) {
        if (bArr != null && bArr.length >= 1) {
            try {
                return StringUtils.compress(EncryptUtil.DESEncrypt("246887c3-ee20-4fe8-a320-1fde4a8d10b6", StringUtils.toHexString(bArr)));
            } catch (Exception unused) {
            }
        }
        return "";
    }

    private void formatSignEncryptKey(boolean z) {
        if (z) {
            this.SIGN_API_KEY = "8zy8nbs9lyddx02slcz8ypmwcr2tlu72";
            this.SIGN_SOCKET_KEY = "8zy8nbs9lyddx02slcz8ypmwcr2tlu72";
            this.ENCRYPT_API_KEY = ConstantUtils.ENCRYPT_FILE_KEY;
            this.ENCRYPT_SOCKET_KEY = "246887c3-ee20-4fe8-a320-1fde4a8d10b6";
            return;
        }
        this.SIGN_API_KEY = "789";
        this.SIGN_SOCKET_KEY = "456";
        this.ENCRYPT_API_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIQVlM6t9IBT6OxfQPhAhTAm/lCnzjy5K9mtrlYmlEXc/uNlhIqs0bCUvBPCWZ6UqWGK23LjPxwiC2jy9+i9SdkopsenLKhZV+yf+y4m02gV/wROQ2QsLng1/0IePiSyl0iGgmXsjR+LYcdAGSBBweZ/iNyiyEsnV5FYHS0cLb0XAgMBAAECgYADpmEFURNHlIoENiGieo3zpbAzZF+zl95ZVo5RvSEtyQyWFhESj/H/ciy8UwuM8Ui49FBaHWN6EIrGLKijGs/2kRcmx4mbnK9eQmkQBuRaTfgqc03XTK7LNU+pz3PVTRlfn7GkDfsSWaeDWNtbR1zK1mgoR+JnMfqbM8C0FqOaEQJBAMEzfzqEgkpmEtx9cUfyLPIw0pGviepZtp+lFO6PHQlPszwM/Xof9ZVhIR8oIR+mCJfqqCGoeoWQbAnoiQizoD8CQQCvBHnxnsxBITaq2Wrjod/rDeM3YHRDg6HET9cVKKIIvhSlLFx8KYw+ZbhPxdz219hdFmdjM3PYy1xibucsQi0pAkBDgKypU3b6a6OXajTUQGc3z5siz8ROHz5RlSo1F8e7Yx9qkddWfigeIyuhaTH5jtddzN0ltWnplMZKx/ZpFemdAkEAot86kHWkRZQgKLyucWpKVJeW9QjpCY9tMqDOWx12NUaXNeNjqhSMM+E7tdk/uePCsVZRHotaas1NizkEHzbyiQJAfC0aRuF5AdJ81o8GJ4j0FwnRUiqWS2DPT9n2x16cmhP2v2ik14nQzp2ihML2kE1I7WUtHzFkZv6NnxBthM4Xwg==";
        this.ENCRYPT_SOCKET_KEY = "c21d31be-4300-4881-a553-156ebb5df087";
    }

    private void initHttpCacheDir(Application application) {
        try {
            HttpResponseCache.install(new File(application.getCacheDir(), "http"), 134217728L);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String formatAppLiveListJson(HttpResultPageModel<LiveEntity> httpResultPageModel) {
        List<LiveEntity> list;
        if (httpResultPageModel == null || (list = httpResultPageModel.dataList) == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (LiveEntity liveEntity : list) {
            AppLiveItemEntity appLiveItemEntity = new AppLiveItemEntity();
            appLiveItemEntity.appId = liveEntity.appId;
            appLiveItemEntity.openId = liveEntity.openId;
            appLiveItemEntity.userId = liveEntity.userId;
            appLiveItemEntity.liveId = liveEntity.liveId;
            appLiveItemEntity.nickname = liveEntity.nickname;
            appLiveItemEntity.avatar = liveEntity.avatar;
            appLiveItemEntity.sex = liveEntity.sex;
            appLiveItemEntity.liveCoverUrl = liveEntity.liveCoverUrl;
            appLiveItemEntity.tag = liveEntity.tag;
            appLiveItemEntity.topic = liveEntity.topic;
            appLiveItemEntity.popularity = liveEntity.popularity;
            appLiveItemEntity.liveStatus = liveEntity.liveStatus;
            appLiveItemEntity.markerUrl = liveEntity.markerUrl;
            appLiveItemEntity.leftPendantUrl = liveEntity.pendantUrl;
            appLiveItemEntity.rightPendantUrl = liveEntity.getCoverIdentityUrl();
            arrayList.add(appLiveItemEntity);
        }
        return GsonUtils.toJson(arrayList);
    }

    public void initImmersionBar(Activity activity) {
        ImmersionBar.with(activity).init();
    }

    public void onDestroyImmersionBar(Activity activity) {
        ImmersionBar.with(activity).destroy();
    }
}
