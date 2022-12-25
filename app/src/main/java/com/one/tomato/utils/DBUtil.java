package com.one.tomato.utils;

import com.one.tomato.entity.AdLive;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.p079db.AdLiveBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.ChannelInfo;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.entity.p079db.GameRegisterUserBean;
import com.one.tomato.entity.p079db.InitAppInfo;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.entity.p079db.LockScreenInfo;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.LookTimeBean;
import com.one.tomato.entity.p079db.OpenInstallChannelBean;
import com.one.tomato.entity.p079db.PostDownPayBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.entity.p079db.PostHotSearch;
import com.one.tomato.entity.p079db.PostRewardId;
import com.one.tomato.entity.p079db.ShareParamsBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UpRecommentBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.entity.p079db.VerNumBean;
import com.one.tomato.entity.p079db.WebAppChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.litepal.LitePal;

/* loaded from: classes3.dex */
public class DBUtil {
    public static LevelBean tempLevelBean;
    private static LoginInfo tempLoginInfo;
    private static UserInfo tempUserInfo;
    private static int userInfoVersion;

    public static void saveSystemParam(SystemParam systemParam) {
        if (systemParam != null) {
            systemParam.saveOrUpdate(new String[0]);
        }
    }

    public static SystemParam getSystemParam() {
        SystemParam systemParam = (SystemParam) LitePal.findFirst(SystemParam.class);
        return systemParam == null ? new SystemParam() : systemParam;
    }

    public static void saveInitAppInfo(InitAppInfo initAppInfo) {
        if (initAppInfo != null) {
            initAppInfo.saveOrUpdate(new String[0]);
        }
    }

    public static InitAppInfo getInitAppInfo() {
        InitAppInfo initAppInfo = (InitAppInfo) LitePal.findFirst(InitAppInfo.class);
        return initAppInfo == null ? new InitAppInfo() : initAppInfo;
    }

    public static synchronized void saveLoginInfo(LoginInfo loginInfo) {
        synchronized (DBUtil.class) {
            if (loginInfo != null) {
                if (loginInfo.saveOrUpdate(new String[0])) {
                    tempLoginInfo = loginInfo;
                }
            }
        }
    }

    public static LoginInfo getLoginInfo() {
        if (tempLoginInfo == null) {
            tempLoginInfo = (LoginInfo) LitePal.findFirst(LoginInfo.class);
            if (tempLoginInfo == null) {
                tempLoginInfo = new LoginInfo();
            }
        }
        return tempLoginInfo;
    }

    public static String getToken() {
        return getLoginInfo().getToken();
    }

    public static int getMemberId() {
        return getLoginInfo().getMemberId();
    }

    public static void saveUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            userInfo.setLocalVersion(userInfoVersion + 1);
            if (!userInfo.saveOrUpdate(new String[0])) {
                return;
            }
            tempUserInfo = userInfo;
        }
    }

    public static UserInfo getUserInfo() {
        if (tempUserInfo == null) {
            tempUserInfo = (UserInfo) LitePal.findFirst(UserInfo.class);
            if (tempUserInfo == null) {
                tempUserInfo = new UserInfo();
            }
        }
        userInfoVersion = tempUserInfo.getLocalVersion();
        return tempUserInfo;
    }

    public static void saveLevelBean(LevelBean levelBean) {
        if (levelBean != null) {
            levelBean.saveOrUpdate(new String[0]);
            tempLevelBean = levelBean;
        }
    }

    public static void saveLevelBean(int i) {
        if (tempLevelBean == null) {
            tempLevelBean = getLevelBean();
        }
        if (i == 1) {
            if (tempLevelBean.getPubCount() <= 0 || tempLevelBean.getPubCount() == tempLevelBean.getReplyCount_times()) {
                return;
            }
            LevelBean levelBean = tempLevelBean;
            levelBean.setPubCount_times(levelBean.getPubCount_times() + 1);
        } else if (i == 2) {
            if (tempLevelBean.getCommentCount() <= 0 || tempLevelBean.getCommentCount() == tempLevelBean.getCommentCount_times()) {
                return;
            }
            LevelBean levelBean2 = tempLevelBean;
            levelBean2.setCommentCount_times(levelBean2.getCommentCount_times() + 1);
        } else if (i != 3 || tempLevelBean.getReplyCount() <= 0 || tempLevelBean.getReplyCount() == tempLevelBean.getReplyCount_times()) {
        } else {
            LevelBean levelBean3 = tempLevelBean;
            levelBean3.setReplyCount_times(levelBean3.getReplyCount_times() + 1);
        }
    }

    public static LevelBean getLevelBean() {
        if (tempLevelBean == null) {
            tempLevelBean = (LevelBean) LitePal.findFirst(LevelBean.class);
            if (tempLevelBean == null) {
                tempLevelBean = new LevelBean();
            }
        }
        return tempLevelBean;
    }

    public static void saveLockScreenInfo(LockScreenInfo lockScreenInfo) {
        if (lockScreenInfo != null) {
            lockScreenInfo.saveOrUpdate(new String[0]);
        }
    }

    public static void deleteLockScreenInfo() {
        LitePal.deleteAll(LockScreenInfo.class, new String[0]);
    }

    public static LockScreenInfo getLockScreenInfo() {
        LockScreenInfo lockScreenInfo = (LockScreenInfo) LitePal.findFirst(LockScreenInfo.class);
        return lockScreenInfo == null ? new LockScreenInfo() : lockScreenInfo;
    }

    public static void saveAdPage(C2516Ad c2516Ad) {
        if (c2516Ad != null) {
            ArrayList<AdPage> papaPage = c2516Ad.getPapaPage();
            ArrayList<AdPage> startupPage = c2516Ad.getStartupPage();
            ArrayList<AdPage> listPage = c2516Ad.getListPage();
            ArrayList<AdPage> circlePage = c2516Ad.getCirclePage();
            ArrayList<AdPage> papaBannerPage = c2516Ad.getPapaBannerPage();
            ArrayList<AdPage> forceAlertPage = c2516Ad.getForceAlertPage();
            ArrayList<AdPage> yuleBannerPage = c2516Ad.getYuleBannerPage();
            ArrayList<AdPage> bgGameBannerPage = c2516Ad.getBgGameBannerPage();
            ArrayList<SubGamesBean> papaRec = c2516Ad.getPapaRec();
            ArrayList<SubGamesBean> articleRec = c2516Ad.getArticleRec();
            ArrayList<SubGamesBean> gameInLive = c2516Ad.getGameInLive();
            saveAdPage(papaPage, C2516Ad.TYPE_PAPA);
            saveAdPage(startupPage, C2516Ad.TYPE_START);
            saveAdPage(listPage, C2516Ad.TYPE_LIST);
            saveAdPage(circlePage, C2516Ad.TYPE_CIRCLE);
            saveAdPage(papaBannerPage, C2516Ad.TYPE_PAPA_BANNER);
            saveAdPage(forceAlertPage, C2516Ad.TYPE_FORCE);
            saveAdPage(yuleBannerPage, C2516Ad.TYPE_CHESS_BANNER);
            saveAdPage(bgGameBannerPage, C2516Ad.TYPE_GAME_CENTER_BANNER);
            savePostGame(papaRec, C2516Ad.TYPE_PAPA_REC);
            savePostGame(articleRec, C2516Ad.TYPE_ARTICLE_REC);
            savePostGame(gameInLive, C2516Ad.TYPE_LIVE_GAME);
        }
    }

    public static void savePostGame(ArrayList<SubGamesBean> arrayList, String str) {
        deleteAdPage(str);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setCategoryType(str);
        }
        LitePal.saveAll(arrayList);
    }

    public static void saveAdPage(ArrayList<AdPage> arrayList, String str) {
        deleteAdPage(str);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (arrayList.get(0).getWeight() > 0) {
            if (C2516Ad.TYPE_START.equals(str)) {
                PreferencesUtil.getInstance().putInt("start_ad_index", 0);
            }
        } else {
            try {
                Collections.sort(arrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setCategoryType(str);
        }
        LitePal.saveAll(arrayList);
    }

    private static void deleteAdPage(String str) {
        if (str.equals(C2516Ad.TYPE_ARTICLE_REC) || str.equals(C2516Ad.TYPE_PAPA_REC) || str.equals(C2516Ad.TYPE_LIVE_GAME)) {
            LitePal.deleteAll(SubGamesBean.class, "categoryType = ?", str);
        } else {
            LitePal.deleteAll(AdPage.class, "categoryType = ?", str);
        }
    }

    public static ArrayList<AdPage> getAdPage(String str) {
        ArrayList<AdPage> arrayList = new ArrayList<>();
        arrayList.addAll(LitePal.where("categoryType = ?", str).find(AdPage.class));
        return arrayList;
    }

    public static ArrayList<SubGamesBean> getPostGameBean(String str) {
        ArrayList<SubGamesBean> arrayList = new ArrayList<>();
        arrayList.addAll(LitePal.where("categoryType = ?", str).find(SubGamesBean.class));
        return arrayList;
    }

    public static void saveLiveAd(ArrayList<AdLive> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        LitePal.deleteAll(AdLiveBean.class, new String[0]);
        ArrayList arrayList2 = new ArrayList();
        Iterator<AdLive> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList2.addAll(it2.next().getAdvList());
        }
        LitePal.saveAll(arrayList2);
    }

    public static ArrayList<AdLiveBean> getLiveAdList(String str) {
        ArrayList<AdLiveBean> arrayList = new ArrayList<>();
        arrayList.addAll(LitePal.where("typeId = ?", str).find(AdLiveBean.class));
        return arrayList;
    }

    public static void saveCountryList(ArrayList<CountryDB> arrayList) {
        LitePal.deleteAll(CountryDB.class, new String[0]);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        LitePal.saveAll(arrayList);
    }

    public static ArrayList<CountryDB> getCountryList() {
        ArrayList<CountryDB> arrayList = new ArrayList<>();
        List findAll = LitePal.findAll(CountryDB.class, new long[0]);
        if (findAll != null && !findAll.isEmpty()) {
            arrayList.addAll(findAll);
        }
        return arrayList;
    }

    public static ArrayList<CountryDB> getCountryList(String str) {
        ArrayList<CountryDB> arrayList = new ArrayList<>();
        List find = LitePal.where("countryName like ? or countryCode like ?", "%" + str + "%", "%" + str + "%").find(CountryDB.class);
        if (find != null && !find.isEmpty()) {
            arrayList.addAll(find);
        }
        return arrayList;
    }

    public static CountryDB queryCountry(String str) {
        return (CountryDB) LitePal.where("countryCode = ?", str).findFirst(CountryDB.class);
    }

    public static void saveLookTimeBean(LookTimeBean lookTimeBean) {
        if (lookTimeBean != null) {
            lookTimeBean.saveOrUpdate("memberId = ?", String.valueOf(getMemberId()));
        }
    }

    public static LookTimeBean getLookTimeBean() {
        LookTimeBean lookTimeBean = (LookTimeBean) LitePal.where("memberId = ?", String.valueOf(getMemberId())).findFirst(LookTimeBean.class);
        if (lookTimeBean == null) {
            lookTimeBean = new LookTimeBean();
            if (getUserInfo().getVipType() > 0) {
                lookTimeBean.setHasPay(true);
            } else {
                lookTimeBean.setFreeTimes(3);
                lookTimeBean.setRemainTimes(3);
            }
        }
        return lookTimeBean;
    }

    public static void saveChannelInfo(ChannelInfo channelInfo) {
        if (channelInfo != null) {
            channelInfo.saveOrUpdate(new String[0]);
        }
    }

    public static ChannelInfo getChannelInfo() {
        ChannelInfo channelInfo = (ChannelInfo) LitePal.findFirst(ChannelInfo.class);
        return channelInfo == null ? new ChannelInfo() : channelInfo;
    }

    public static OpenInstallChannelBean getOpenChannelBean() {
        OpenInstallChannelBean openInstallChannelBean = (OpenInstallChannelBean) LitePal.findFirst(OpenInstallChannelBean.class);
        return openInstallChannelBean == null ? new OpenInstallChannelBean() : openInstallChannelBean;
    }

    public static void saveOpenInstallChannelBean(OpenInstallChannelBean openInstallChannelBean) {
        if (openInstallChannelBean != null) {
            openInstallChannelBean.saveOrUpdate(new String[0]);
        }
    }

    public static synchronized ShareParamsBean getShareParams() {
        ShareParamsBean shareParamsBean;
        synchronized (DBUtil.class) {
            shareParamsBean = (ShareParamsBean) LitePal.findFirst(ShareParamsBean.class);
            if (shareParamsBean == null) {
                shareParamsBean = new ShareParamsBean();
            }
        }
        return shareParamsBean;
    }

    public static synchronized void saveShareParams(ShareParamsBean shareParamsBean) {
        synchronized (DBUtil.class) {
            if (shareParamsBean == null) {
                return;
            }
            ShareParamsBean shareParamsBean2 = (ShareParamsBean) LitePal.findFirst(ShareParamsBean.class);
            if (shareParamsBean2 != null) {
                if (shareParamsBean.getPostShare() != null) {
                    shareParamsBean2.setPostShare(shareParamsBean.getPostShare());
                }
                if (shareParamsBean.getVideoShare() != null) {
                    shareParamsBean2.setVideoShare(shareParamsBean.getVideoShare());
                }
                if (shareParamsBean.getShareAddress() != null) {
                    shareParamsBean2.setShareAddress(shareParamsBean.getShareAddress());
                }
                shareParamsBean2.saveOrUpdate(new String[0]);
                return;
            }
            shareParamsBean.saveOrUpdate(new String[0]);
        }
    }

    public static void saveHotSearchList(ArrayList<PostHotSearch> arrayList) {
        LitePal.deleteAll(PostHotSearch.class, new String[0]);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        LitePal.saveAll(arrayList);
    }

    public static ArrayList<PostHotSearch> getHotSearchList() {
        ArrayList<PostHotSearch> arrayList = new ArrayList<>();
        List findAll = LitePal.findAll(PostHotSearch.class, new long[0]);
        if (findAll != null && !findAll.isEmpty()) {
            arrayList.addAll(findAll);
        }
        return arrayList;
    }

    public static void saveWebAppChanelList(ArrayList<WebAppChannel> arrayList) {
        LitePal.deleteAll(WebAppChannel.class, new String[0]);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        LitePal.saveAll(arrayList);
    }

    public static WebAppChannel getWebAppChannel(String str) {
        return (WebAppChannel) LitePal.where("appAlias = ?", str).findFirst(WebAppChannel.class);
    }

    public static List<CircleAllBean> getCircleAllBean() {
        return LitePal.findAll(CircleAllBean.class, new long[0]);
    }

    public static void saveCiecleAllBean(List<CircleAllBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        LitePal.deleteAll(CircleAllBean.class, new String[0]);
        LitePal.saveAll(list);
    }

    public static void saveRecommentUpBean(List<UpRecommentBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        LitePal.deleteAll(UpRecommentBean.class, new String[0]);
        LitePal.saveAll(list);
    }

    public static List<UpRecommentBean> getRecommentUpBean() {
        return LitePal.findAll(UpRecommentBean.class, new long[0]);
    }

    public static void saveRecommentHotMessageBean(List<PostHotMessageBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        LitePal.deleteAll(PostHotMessageBean.class, new String[0]);
        LitePal.saveAll(list);
    }

    public static List<PostRewardId> getPayPostId() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(LitePal.where("membeId = ?", String.valueOf(getMemberId())).find(PostRewardId.class));
        return arrayList;
    }

    public static void setPayPostId(ArrayList<PostRewardId> arrayList) {
        LitePal.saveAll(arrayList);
    }

    public static List<PostDownPayBean> getDownPayPostId() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(LitePal.where("membeId = ?", String.valueOf(getMemberId())).find(PostDownPayBean.class));
        return arrayList;
    }

    public static void setDownPayPostId(ArrayList<PostDownPayBean> arrayList) {
        LitePal.saveAll(arrayList);
    }

    public static void setVerNumBean(VerNumBean verNumBean) {
        verNumBean.saveOrUpdate(new String[0]);
    }

    public static VerNumBean getVerNumBean() {
        VerNumBean verNumBean = (VerNumBean) LitePal.where("memberId = ?", String.valueOf(getMemberId())).findFirst(VerNumBean.class);
        return verNumBean != null ? verNumBean : new VerNumBean(getMemberId());
    }

    public static void setTagList(ArrayList<Tag> arrayList) {
        LitePal.saveAll(arrayList);
    }

    public static ArrayList<Tag> getTagList() {
        ArrayList<Tag> arrayList = new ArrayList<>();
        arrayList.addAll(LitePal.where(new String[0]).find(Tag.class));
        return arrayList;
    }

    public static void deleteTag() {
        LitePal.deleteAll(Tag.class, new String[0]);
    }

    public static void deleteTag(String str, int i) {
        LitePal.deleteAll(Tag.class, "tagId = ? and tagName = ?", i + "", str);
    }

    public static ArrayList<SubGamesBean> getGameBean() {
        ArrayList<SubGamesBean> arrayList = new ArrayList<>();
        arrayList.addAll(LitePal.where("memberId = ?", String.valueOf(getMemberId())).find(SubGamesBean.class));
        return arrayList;
    }

    public static void saveGameBean(ArrayList<SubGamesBean> arrayList) {
        LitePal.saveAll(arrayList);
    }

    public static void deleteGameBean() {
        LitePal.deleteAll(SubGamesBean.class, "memberId = ?", String.valueOf(getMemberId()));
    }

    public static void saveGameRegisterBean(GameRegisterUserBean gameRegisterUserBean) {
        if (gameRegisterUserBean.getMemberNames() == null || gameRegisterUserBean.getMemberNames().size() <= 0) {
            return;
        }
        gameRegisterUserBean.saveOrUpdate(new String[0]);
    }

    public static GameRegisterUserBean getGameRegisterBean() {
        return (GameRegisterUserBean) LitePal.findFirst(GameRegisterUserBean.class);
    }
}
