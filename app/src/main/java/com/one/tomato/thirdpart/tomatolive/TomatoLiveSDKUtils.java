package com.one.tomato.thirdpart.tomatolive;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.eclipsesource.p056v8.Platform;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.GameBingBingToken;
import com.one.tomato.entity.GameDaShengH5;
import com.one.tomato.entity.GameDaShengWebapp;
import com.one.tomato.entity.LiveRecommendData;
import com.one.tomato.entity.p079db.AdLiveBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.entity.p079db.WebAppChannel;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.p085ui.task.TaskCenterActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.LiveStatusEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.interfaces.OnAppRankingCallback;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.language.MultiLanguageUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class TomatoLiveSDKUtils {
    private static final TomatoLiveSDKUtils sInstance = new TomatoLiveSDKUtils();
    private AdUtil adUtil;
    TomatoLiveSDK.TomatoLiveSDKCallbackListener tomatoLiveCallback = new TomatoLiveSDK.TomatoLiveSDKCallbackListener() { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.1
        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public boolean onEnterLivePermissionListener(Context context, int i) {
            return true;
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onGiftRechargeListener(Context context) {
            RechargeActivity.startActivity(context);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onLoginListener(Context context) {
            LoginActivity.Companion.startActivity(context);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onIncomeWithdrawalListener(Context context) {
            IncomeActivity.Companion.startActivity(context, 1);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onUserOfflineListener(Context context) {
            AppInitUtil.initAppInfoFromServer(false);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onAdClickListener(Context context, String str) {
            TomatoLiveSDKUtils.this.onWebAppClick(context, str);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onAdvChannelListener(Context context, String str, TomatoLiveSDK.OnAdvChannelCallbackListener onAdvChannelCallbackListener) {
            TomatoLiveSDKUtils.this.onAdvChannel(context, str, onAdvChannelCallbackListener);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onAdvChannelLiveNoticeListener(Context context, TomatoLiveSDK.OnAdvChannelCallbackListener onAdvChannelCallbackListener) {
            TomatoLiveSDKUtils.this.onAdvChannel(context, "16", onAdvChannelCallbackListener);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onAdvChannelHitsListener(Context context, String str, String str2) {
            TomatoLiveSDKUtils.this.onAdvChannelHits(context, str, str2);
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onUserHomepageListener(Context context, UserEntity userEntity) {
            NewMyHomePageActivity.Companion.startActivity(context, Integer.parseInt(userEntity.getUserId()));
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onScoreListener(Context context) {
            TaskCenterActivity.startEarnActivity(context);
            TomatoLiveSDK.getSingleton().onUpdateBalance();
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onAppCommonCallbackListener(Context context, int i, TomatoLiveSDK.OnCommonCallbackListener onCommonCallbackListener) {
            if (i == 273) {
                TomatoLiveSDKUtils.this.updateBingBingToken(context, i, onCommonCallbackListener);
            } else if (i != 274) {
            } else {
                TomatoLiveSDKUtils.this.updateBingBingData(context, i, onCommonCallbackListener);
            }
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.TomatoLiveSDKCallbackListener
        public void onLiveGameJSListener(Context context, String str) {
            char c;
            int hashCode = str.hashCode();
            if (hashCode != -1795460375) {
                if (hashCode == -273599897 && str.equals("live_open_login")) {
                    c = 1;
                }
                c = 65535;
            } else {
                if (str.equals("live_open_recharge")) {
                    c = 0;
                }
                c = 65535;
            }
            if (c == 0) {
                RechargeActivity.startActivity(context);
            } else if (c != 1) {
            } else {
                LoginActivity.Companion.startActivity(context);
            }
        }
    };
    private TomatoLiveSDK.LiveSDKLoginCallbackListener liveSDKLoginListener = new TomatoLiveSDK.LiveSDKLoginCallbackListener() { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.2
        @Override // com.tomatolive.library.TomatoLiveSDK.LiveSDKLoginCallbackListener
        public void onLoginSuccessListener(Context context) {
            LogUtil.m3784i("直播SDK登录成功");
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.LiveSDKLoginCallbackListener
        public void onLoginFailListener(Context context) {
            LogUtil.m3786e("直播SDK登录失败");
            TomatoLiveSDKUtils.this.clearUserTokenInfo();
        }
    };
    private TomatoLiveSDK.LiveSDKLogoutCallbackListener liveSDKLogoutCallbackListener = new TomatoLiveSDK.LiveSDKLogoutCallbackListener() { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.3
        @Override // com.tomatolive.library.TomatoLiveSDK.LiveSDKLogoutCallbackListener
        public void onLogoutSuccessListener(Context context) {
            LogUtil.m3784i("直播SDK登出成功");
            TomatoLiveSDKUtils.this.clearUserTokenInfo();
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.LiveSDKLogoutCallbackListener
        public void onLogoutFailListener(Context context) {
            LogUtil.m3786e("直播SDK登出失败");
            TomatoLiveSDKUtils.this.clearUserTokenInfo();
        }
    };

    /* loaded from: classes3.dex */
    public interface LiveRankDataListener {
        void onFail();

        void onSuccess(List<String> list, List<String> list2);
    }

    /* loaded from: classes3.dex */
    public interface LiveRecommendDataListener {
        void onFail();

        void onSuccess(List<LiveRecommendData> list);
    }

    /* loaded from: classes3.dex */
    public interface LiveStatusEntityListener {
        void callbackLiveStatus(String str, String str2);
    }

    private TomatoLiveSDKUtils() {
    }

    public static TomatoLiveSDKUtils getSingleton() {
        return sInstance;
    }

    public void initSDK(Application application) {
        String str;
        try {
            str = URLEncoder.encode(URLEncoder.encode(AppUtil.getString(R.string.app_name), EncryptUtil.CHARSET), EncryptUtil.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str = "tomato";
        }
        TomatoLiveSDK.getSingleton().initSDK(application, "1", "016c6dd0-0531-4c7f-a531-52b76f0d366c", str, DomainServer.getInstance().getLiveServerUrl(), DomainServer.getInstance().getLiveUploadUrl(), DomainServer.getInstance().getLivePictureUrl(), getAdChannelId(), "2", true, authpack.m3789A(), true, true, true, DBUtil.getUserInfo().getGameChannel(), "2", DomainServer.getInstance().getServerUrl(), this.tomatoLiveCallback);
    }

    public void loginLiveSDK(Context context, boolean z) {
        updateServerUrl();
        UserInfoManager.getInstance().loadUserInfo(getUserEntity());
        updateGameChannel();
        if (z) {
            TomatoLiveSDK.getSingleton().loginSDK(context, this.liveSDKLoginListener);
        }
    }

    private UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(String.valueOf(DBUtil.getMemberId()));
        userEntity.setToken(DBUtil.getLoginInfo().getLiveToken());
        UserInfo userInfo = DBUtil.getUserInfo();
        userEntity.setName(AppUtil.filterString(userInfo.getName(), "口"));
        userEntity.setSex(userInfo.getSex());
        userEntity.setAvatar(DomainServer.getInstance().getTtViewPicture() + userInfo.getAvatar());
        userEntity.setIsRisk(userInfo.getIsRisk());
        LogUtil.m3784i("登录直播传参：\nuserId = " + userEntity.getUserId() + "\nliveToken = " + userEntity.getToken() + "\nname = " + userEntity.getName() + "\nsex = " + userEntity.getSex() + "\navatar = " + userEntity.getAvatar() + "\nisRisk = " + userEntity.getIsRisk());
        return userEntity;
    }

    public void logoutLiveSDK(Context context) {
        TomatoLiveSDK.getSingleton().exitSDK(context, this.liveSDKLogoutCallbackListener);
    }

    public void updateServerUrl() {
        String liveServerUrl = DomainServer.getInstance().getLiveServerUrl();
        LogUtil.m3784i("serverUrl ：" + liveServerUrl);
        String liveUploadUrl = DomainServer.getInstance().getLiveUploadUrl();
        LogUtil.m3784i("uploadUrl ：" + liveUploadUrl);
        String livePictureUrl = DomainServer.getInstance().getLivePictureUrl();
        LogUtil.m3784i("pictureUrl ：" + livePictureUrl);
        TomatoLiveSDK.getSingleton().updateServerUrl(liveServerUrl, liveUploadUrl, livePictureUrl);
        String serverUrl = DomainServer.getInstance().getServerUrl();
        LogUtil.m3784i("reportUrl ：" + serverUrl);
        updateDataReportUrl(serverUrl);
    }

    public void initAnim() {
        TomatoLiveSDK.getSingleton().initAnim();
    }

    public void clearUserTokenInfo() {
        TomatoLiveSDK.getSingleton().clearUserTokenInfo();
    }

    public void onUpdateUserAvatar(Context context, String str) {
        TomatoLiveSDK.getSingleton().onUpdateUserAvatar(context, str);
    }

    public void onUpdateUserNickName(Context context, String str) {
        TomatoLiveSDK.getSingleton().onUpdateUserNickName(context, AppUtil.filterString(str, "口"));
    }

    public void onUpdateUserSex(Context context, String str) {
        TomatoLiveSDK.getSingleton().onUpdateUserSex(context, str);
    }

    public void updateLanguage(Context context, int i) {
        MultiLanguageUtil.getInstance().updateLanguage(context, i);
    }

    private String getAdChannelId() {
        String channelId = BaseApplication.getApplication().getChannelId();
        String channelSubId = BaseApplication.getApplication().getChannelSubId();
        if (TextUtils.isEmpty(channelSubId)) {
            channelSubId = "null";
        }
        return channelId + "_" + channelSubId;
    }

    public void updateAdChannelField() {
        TomatoLiveSDK.getSingleton().updateAdvChannelField(getAdChannelId(), "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWebAppClick(Context context, String str) {
        LogUtil.m3784i("小程序json：" + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String str2 = "";
            if (jSONObject.has(LogConstants.APP_ID)) {
                str2 = jSONObject.getString(LogConstants.APP_ID);
                sb.append(str2);
                sb.append(";");
            }
            if (jSONObject.has(DatabaseFieldConfigLoader.FIELD_NAME_ID)) {
                sb.append(jSONObject.getString(DatabaseFieldConfigLoader.FIELD_NAME_ID));
                sb.append(";");
            }
            if (jSONObject.has("host")) {
                sb.append(jSONObject.getString("host"));
                sb.append(";");
            }
            if (jSONObject.has("key")) {
                sb.append(jSONObject.getString("key"));
            }
            WebAppChannel webAppChannel = DBUtil.getWebAppChannel(str2);
            if (webAppChannel != null) {
                sb.append(";");
                sb.append(webAppChannel.getAppChannelCode());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.adUtil == null) {
            this.adUtil = new AdUtil((Activity) context);
        }
        this.adUtil.clickAd(0, 3, "", "", 0, sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdvChannel(Context context, String str, TomatoLiveSDK.OnAdvChannelCallbackListener onAdvChannelCallbackListener) {
        String json;
        ArrayList<AdLiveBean> liveAdList = DBUtil.getLiveAdList(str);
        if (liveAdList == null || liveAdList.isEmpty()) {
            onAdvChannelCallbackListener.onAdvDataFail(-1, "no data");
            return;
        }
        if ("16".equals(str)) {
            AdLiveBean adLiveBean = liveAdList.get(0);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("name", adLiveBean.getName());
                jSONObject.put("content", adLiveBean.getContent());
                json = jSONObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                json = "";
            }
            onAdvChannelCallbackListener.onAdvDataSuccess(context, json);
        } else {
            ArrayList arrayList = new ArrayList();
            for (AdLiveBean adLiveBean2 : liveAdList) {
                BannerEntity bannerEntity = new BannerEntity();
                bannerEntity.f5830id = adLiveBean2.getId();
                bannerEntity.name = adLiveBean2.getName();
                bannerEntity.img = adLiveBean2.getImg();
                bannerEntity.url = adLiveBean2.getUrl();
                bannerEntity.method = adLiveBean2.getMethod();
                bannerEntity.content = adLiveBean2.getContent();
                bannerEntity.allow_close = adLiveBean2.getAllow_close();
                bannerEntity.terminal = Platform.ANDROID;
                bannerEntity.forwardScope = adLiveBean2.getForwardScope();
                bannerEntity.type = adLiveBean2.getType();
                bannerEntity.componentId = adLiveBean2.getComponentId();
                arrayList.add(bannerEntity);
            }
            json = BaseApplication.getGson().toJson(arrayList);
            onAdvChannelCallbackListener.onAdvDataSuccess(context, json);
        }
        LogUtil.m3784i("传递到直播的广告类型 type = " + str + "， json = " + json);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdvChannelHits(Context context, String str, String str2) {
        int i;
        int i2 = 0;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            i = 0;
        }
        try {
            i2 = Integer.parseInt(str2);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
        DataUploadUtil.uploadAD(i, i2);
    }

    public void startLiveActivity(Context context, String str) {
        TomatoLiveSDK.getSingleton().toLiveActivity(context, str);
    }

    public void startMyLiveActivity(Context context) {
        TomatoLiveSDK.getSingleton().toMyLiveActivity(context);
    }

    public void getLiveStatus(Context context, String str, final LiveStatusEntityListener liveStatusEntityListener) {
        TomatoLiveSDK.getSingleton().onAnchorLiveStatus(context, str, new TomatoLiveSDK.OnLiveStatusCallbackListener(this) { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.4
            @Override // com.tomatolive.library.TomatoLiveSDK.OnLiveStatusCallbackListener
            public void onLiveStatusSuccess(Context context2, LiveStatusEntity liveStatusEntity) {
                String str2 = liveStatusEntity.liveStatus;
                String str3 = liveStatusEntity.liveId;
                liveStatusEntityListener.callbackLiveStatus(str2, str3);
                LogUtil.m3784i("主播当前是否开播：liveStatus(1是0否) = " + str2 + ",liveId = " + str3);
            }

            @Override // com.tomatolive.library.TomatoLiveSDK.OnLiveStatusCallbackListener
            public void onLiveStatusFail(int i, String str2) {
                LogUtil.m3784i("主播当前是否开播：接口失败，errorCode = " + i + "，errorMsg = " + str2);
                liveStatusEntityListener.callbackLiveStatus("0", "");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBingBingToken(final Context context, int i, final TomatoLiveSDK.OnCommonCallbackListener onCommonCallbackListener) {
        GameUtils gameUtils = GameUtils.INSTANCE;
        gameUtils.requestGameResponse(gameUtils.getGAME_BINGBING_TOKEN(), "1", "", "", new GameUtils.RequestGameResponseListener() { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.5
            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameDaShengH5(GameDaShengH5 gameDaShengH5) {
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameDaShengWebapp(GameDaShengWebapp gameDaShengWebapp) {
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameBingBingToken(GameBingBingToken gameBingBingToken) {
                TomatoLiveSDKUtils.this.updateGameChannel();
                if (gameBingBingToken == null || TextUtils.isEmpty(gameBingBingToken.token)) {
                    onCommonCallbackListener.onDataFail(new Throwable("liveToken is null"), -1);
                    return;
                }
                LogUtil.m3784i("传递给直播的游戏Token = " + gameBingBingToken.token);
                onCommonCallbackListener.onDataSuccess(context, gameBingBingToken.token);
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestFail() {
                onCommonCallbackListener.onDataFail(new Throwable("liveToken request fail"), -1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBingBingData(Context context, int i, TomatoLiveSDK.OnCommonCallbackListener onCommonCallbackListener) {
        ArrayList<SubGamesBean> postGameBean = DBUtil.getPostGameBean(C2516Ad.TYPE_LIVE_GAME);
        if (postGameBean != null && !postGameBean.isEmpty()) {
            try {
                JSONArray jSONArray = new JSONArray();
                Iterator<SubGamesBean> it2 = postGameBean.iterator();
                while (it2.hasNext()) {
                    SubGamesBean next = it2.next();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, next.getGameId());
                    jSONObject.put("name", next.getAdName());
                    jSONObject.put("imgUrl", next.getAdArticleAvatarSec());
                    jSONObject.put("targetUrl", next.getAdLink());
                    jSONObject.put("callType", next.getOpenType());
                    jSONObject.put("gameId", next.getAdGameId());
                    jSONObject.put("width", next.getAdGameWidth());
                    jSONObject.put("height", next.getAdGameHeight());
                    jSONArray.put(jSONObject);
                }
                LogUtil.m3784i("传递给直播的游戏数据：" + jSONArray.toString());
                onCommonCallbackListener.onDataSuccess(context, jSONArray.toString());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        onCommonCallbackListener.onDataFail(new Throwable("liveGameList is null"), -1);
        LogUtil.m3784i("传递给直播的游戏数据：null");
    }

    public void updateGameChannel() {
        TomatoLiveSDK.getSingleton().updateGameChannelField(DBUtil.getUserInfo().getGameChannel(), "2");
    }

    public void updateIsRisk(Context context, String str) {
        LogUtil.m3784i("是否是风险用户 ：" + str);
        TomatoLiveSDK.getSingleton().onUpdateUserRisk(context, str);
    }

    public void updateDataReportUrl(String str) {
        TomatoLiveSDK.getSingleton().updateDataReportUrl(str);
    }

    public void onLiveRecommendDataCallback(final LiveRecommendDataListener liveRecommendDataListener) {
        TomatoLiveSDK.getSingleton().onAppLiveListCallback(1, 6, new ResultCallBack<String>(this) { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.6
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(String str) {
                LogUtil.m3784i("直播推荐列表数据获取成功，json = \n" + str);
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                liveRecommendDataListener.onSuccess((List) GsonUtils.fromJson(str, new TypeToken<List<LiveRecommendData>>(this) { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.6.1
                }.getType()));
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                LogUtil.m3786e("直播推荐列表数据获取失败，code = " + i + ", errorMsg = " + str);
                liveRecommendDataListener.onFail();
            }
        });
    }

    public void loadRoundCornersImage(Context context, ImageView imageView, String str) {
        GlideUtils.loadRoundCornersImage(context, imageView, str, 6, (int) R.drawable.fq_ic_placeholder_corners);
    }

    public void onLiveRankDataCallback(final LiveRankDataListener liveRankDataListener) {
        TomatoLiveSDK.getSingleton().onAppRankingCallback(new OnAppRankingCallback(this) { // from class: com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.7
            @Override // com.tomatolive.library.p136ui.interfaces.OnAppRankingCallback
            public void onAppRankingSuccess(List<String> list, List<String> list2) {
                LogUtil.m3784i("获取魅力富豪排行榜成功，\ncharmAvatarList魅力榜 = " + list + "\nstrengthAvatarList富豪榜 = " + list2);
                liveRankDataListener.onSuccess(list, list2);
            }

            @Override // com.tomatolive.library.p136ui.interfaces.OnAppRankingCallback
            public void onAppRankingFail() {
                LogUtil.m3786e("获取魅力富豪排行榜失败");
                liveRankDataListener.onFail();
            }
        });
    }

    public void startRankType(Context context, int i) {
        TomatoLiveSDK.getSingleton().toRankingActivity(context, i);
    }

    public void loadAvatar(Context context, ImageView imageView, String str) {
        GlideUtils.loadAvatar(context, imageView, str, 1, ContextCompat.getColor(context, R.color.fq_colorWhite));
    }
}
