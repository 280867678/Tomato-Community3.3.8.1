package com.tomatolive.library.utils;

import com.blankj.utilcode.util.SPUtils;
import com.tomatolive.library.model.WatermarkConfigEntity;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SysConfigInfoManager {
    private final String ENABLE_TRANSLATION_LEVEL;
    private final String ENTRY_NOTICE_LEVEL_THRESHOLD;
    private final String GIFT_TRUMPET_PLAY_PERIOD;
    private final String GRADE_CHARACTER_LIMIT;
    public String IS_ENABLE_ACHIEVEMENT;
    private final String IS_ENABLE_ANCHOR_HOMEPAGE;
    private final String IS_ENABLE_CAR;
    public String IS_ENABLE_COMPONENTS;
    public String IS_ENABLE_EXCHANGE_PROPORTION;
    public String IS_ENABLE_FEE_TAG;
    public String IS_ENABLE_GIFT_WALL;
    private final String IS_ENABLE_GUARD;
    private final String IS_ENABLE_LIVE_GUIDE;
    public String IS_ENABLE_LIVE_HELPER_JUMP;
    public String IS_ENABLE_LOG_EVENT_REPORT;
    private final String IS_ENABLE_NOBILITY;
    private final String IS_ENABLE_NOBILITY_GUIDE;
    private String IS_ENABLE_OFFLINE_PRIVATE_MSG;
    private final String IS_ENABLE_PAID_LIVE_GUIDE;
    public String IS_ENABLE_PRIVATE_MSG;
    private String IS_ENABLE_QM_INTERACT;
    private String IS_ENABLE_QM_INTERACT_RED_DOT;
    private final String IS_ENABLE_RATING_GUIDE;
    private final String IS_ENABLE_REPORT;
    public String IS_ENABLE_SCORE;
    public String IS_ENABLE_SHOW_CURRENT_TOP;
    private final String IS_ENABLE_STICKER;
    public String IS_ENABLE_TRANSLATION;
    private final String IS_ENABLE_TURNTABLE;
    public String IS_ENABLE_TURNTABLE_UPDATE_TIP;
    private final String IS_ENABLE_USER_HOMEPAGE;
    private final String IS_ENABLE_VIDEO_STREAM_ENCODE;
    public String IS_ENABLE_VISITOR_LIVE;
    public String IS_ENABLE_VISITOR_PERMISSION;
    public String IS_ENABLE_WEEK_STAR;
    public String LOCAL_RESOURCE_URL_KEY;
    public String LOCAL_RESOURCE_VERSION_KEY;
    private final String ONLINE_COUNT_INTERVAL;
    private final String ONLINE_USER_LEVEL_FILTER;
    private final String ONLINE_USER_LIST_SIZE;
    private final String SOCKET_HEART_BEAT_INTERVAL;
    private final String SPNAME;
    private final String USER_GRADE_MAX;
    private ArrayList<String> liveRankConfig;
    private final String nobilityTypeThresholdForHasPreventBanned;
    private WatermarkConfigEntity watermarkConfig;

    private SysConfigInfoManager() {
        this.SPNAME = "fq_config";
        this.USER_GRADE_MAX = "userGradeMax";
        this.IS_ENABLE_REPORT = "isEnableReport";
        this.IS_ENABLE_STICKER = "isEnableSticker";
        this.IS_ENABLE_LIVE_GUIDE = "isEnableLiveGuide";
        this.IS_ENABLE_PAID_LIVE_GUIDE = "isEnablePaidLiveGuide";
        this.IS_ENABLE_RATING_GUIDE = "isEnableRatingGuide";
        this.IS_ENABLE_NOBILITY_GUIDE = "isEnableNobilityGuide";
        this.IS_ENABLE_NOBILITY = "isEnableNobility";
        this.IS_ENABLE_VIDEO_STREAM_ENCODE = "isEnableVideoStreamEncode";
        this.IS_ENABLE_TURNTABLE = "isEnableTurntable";
        this.IS_ENABLE_CAR = "isEnableCar";
        this.IS_ENABLE_GUARD = "isEnableGuard";
        this.ENABLE_TRANSLATION_LEVEL = "enableTranslationLevel";
        this.ENTRY_NOTICE_LEVEL_THRESHOLD = "entryNoticeLevelThreshold";
        this.GRADE_CHARACTER_LIMIT = "gradeSet10CharacterLimit";
        this.GIFT_TRUMPET_PLAY_PERIOD = "giftTrumpetPlayPeriod";
        this.ONLINE_COUNT_INTERVAL = "onlineCountSynInterval";
        this.nobilityTypeThresholdForHasPreventBanned = "nobilityTypeThresholdForHasPreventBanned";
        this.SOCKET_HEART_BEAT_INTERVAL = "socketHeartBeatInterval";
        this.ONLINE_USER_LEVEL_FILTER = "liveOnlineUserListLevelFilter";
        this.ONLINE_USER_LIST_SIZE = "liveInitOnlineUserListSize";
        this.IS_ENABLE_ANCHOR_HOMEPAGE = "isEnableAnchorHomepage";
        this.IS_ENABLE_USER_HOMEPAGE = "isEnableUserHomepage";
        this.IS_ENABLE_VISITOR_LIVE = "enableVisitorLive";
        this.IS_ENABLE_SCORE = "enableScore";
        this.IS_ENABLE_TURNTABLE_UPDATE_TIP = "enableTurntableUpdateTip";
        this.IS_ENABLE_WEEK_STAR = "enableWeekStar";
        this.IS_ENABLE_GIFT_WALL = "enableGiftWall";
        this.IS_ENABLE_ACHIEVEMENT = "enableAchievement";
        this.IS_ENABLE_COMPONENTS = "enableComponents";
        this.IS_ENABLE_TRANSLATION = "enableTranslation";
        this.IS_ENABLE_PRIVATE_MSG = "enablePrivateMsg";
        this.IS_ENABLE_VISITOR_PERMISSION = "enableVisitorPermission";
        this.IS_ENABLE_EXCHANGE_PROPORTION = "enableExchangeProportion";
        this.IS_ENABLE_LOG_EVENT_REPORT = "enableLogEventReport";
        this.IS_ENABLE_FEE_TAG = "enableFeeTag";
        this.IS_ENABLE_LIVE_HELPER_JUMP = "enableLiveHelperJump";
        this.IS_ENABLE_SHOW_CURRENT_TOP = "enableShowCurrentTop10";
        this.LOCAL_RESOURCE_VERSION_KEY = "LOCAL_RESOURCE_VERSION_KEY";
        this.LOCAL_RESOURCE_URL_KEY = "LOCAL_RESOURCE_URL_KEY";
        this.IS_ENABLE_OFFLINE_PRIVATE_MSG = "IS_ENABLE_OFFLINE_PRIVATE_MSG";
        this.IS_ENABLE_QM_INTERACT = "IS_ENABLE_QM_INTERACT";
        this.IS_ENABLE_QM_INTERACT_RED_DOT = "isQMInteractRedDot";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class LazyHolder {
        private static final SysConfigInfoManager INSTANCE = new SysConfigInfoManager();

        private LazyHolder() {
        }
    }

    public static SysConfigInfoManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setLocalResUrl(String str) {
        SPUtils.getInstance("fq_config").put(this.LOCAL_RESOURCE_URL_KEY, str);
    }

    public String getLocalResUrl() {
        return SPUtils.getInstance("fq_config").getString(this.LOCAL_RESOURCE_URL_KEY, "");
    }

    public int getUserGradeMax() {
        return SPUtils.getInstance("fq_config").getInt("userGradeMax", 60);
    }

    public void setUserGradeMax(int i) {
        SPUtils.getInstance("fq_config").put("userGradeMax", i);
    }

    public void setEnableReport(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableReport", z);
    }

    public boolean isEnableReport() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableReport", true);
    }

    public void setEnableSticker(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableSticker", z);
    }

    public boolean isEnableSticker() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableSticker", true);
    }

    public void setEnableLiveGuide(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableLiveGuide", z);
    }

    public boolean isEnableLiveGuide() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableLiveGuide", true);
    }

    public void setEnableRatingGuide(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableRatingGuide", z);
    }

    public boolean isEnableRatingGuide() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableRatingGuide", true);
    }

    public void setEnablePaidLiveGuide(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnablePaidLiveGuide", z);
    }

    public boolean isEnablePaidLiveGuide() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnablePaidLiveGuide", true);
    }

    public void setEnableNobilityGuide(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableNobilityGuide", z);
    }

    public boolean isEnableNobilityGuide() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableNobilityGuide", true);
    }

    public void setEnableNobility(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableNobility", z);
    }

    public boolean isEnableNobility() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableNobility", true);
    }

    public void setEnableVideoStreamEncode(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableVideoStreamEncode", z);
    }

    public boolean isEnableVideoStreamEncode() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableVideoStreamEncode", false);
    }

    public void setEnableTurntable(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableTurntable", z);
    }

    public boolean isEnableTurntable() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableTurntable", false);
    }

    public void setEnableCar(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableCar", z);
    }

    public boolean isEnableCar() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableCar", true);
    }

    public String getEnableTranslationLevel() {
        return SPUtils.getInstance("fq_config").getString("enableTranslationLevel", "5");
    }

    public void setEnableTranslationLevel(String str) {
        SPUtils.getInstance("fq_config").put("enableTranslationLevel", str);
    }

    public String getEntryNoticeLevelThreshold() {
        return SPUtils.getInstance("fq_config").getString("entryNoticeLevelThreshold", "10");
    }

    public void setEntryNoticeLevelThreshold(String str) {
        SPUtils.getInstance("fq_config").put("entryNoticeLevelThreshold", str);
    }

    public String getGradeSet10CharacterLimit() {
        return SPUtils.getInstance("fq_config").getString("gradeSet10CharacterLimit", "10");
    }

    public void setGradeSet10CharacterLimit(String str) {
        SPUtils.getInstance("fq_config").put("gradeSet10CharacterLimit", str);
    }

    public String getGiftTrumpetPlayPeriod() {
        return SPUtils.getInstance("fq_config").getString("giftTrumpetPlayPeriod", "10");
    }

    public void setGiftTrumpetPlayPeriod(String str) {
        SPUtils.getInstance("fq_config").put("giftTrumpetPlayPeriod", str);
    }

    public String getOnlineCountSynInterval() {
        return SPUtils.getInstance("fq_config").getString("onlineCountSynInterval", "10");
    }

    public void setOnlineCountSynInterval(String str) {
        SPUtils.getInstance("fq_config").put("onlineCountSynInterval", str);
    }

    public int getNobilityTypeThresholdForHasPreventBanned() {
        return SPUtils.getInstance("fq_config").getInt("nobilityTypeThresholdForHasPreventBanned", 7);
    }

    public void setNobilityTypeThresholdForHasPreventBanned(int i) {
        SPUtils.getInstance("fq_config").put("nobilityTypeThresholdForHasPreventBanned", i);
    }

    public long getSocketHeartBeatInterval() {
        return SPUtils.getInstance("fq_config").getLong("socketHeartBeatInterval", 30L);
    }

    public void setSocketHeartBeatInterval(long j) {
        SPUtils.getInstance("fq_config").put("socketHeartBeatInterval", j);
    }

    public void setEnableAnchorHomepage(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableAnchorHomepage", z);
    }

    public boolean isEnableAnchorHomepage() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableAnchorHomepage", false);
    }

    public void setEnableUserHomepage(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableUserHomepage", z);
    }

    public boolean isEnableUserHomepage() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableUserHomepage", true);
    }

    public void setEnableScore(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_SCORE, z);
    }

    public boolean isEnableScore() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_SCORE, true);
    }

    public void setEnableGuard(boolean z) {
        SPUtils.getInstance("fq_config").put("isEnableGuard", z);
    }

    public boolean isEnableGuard() {
        return SPUtils.getInstance("fq_config").getBoolean("isEnableGuard", true);
    }

    public void setEnableVisitorLive(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_VISITOR_LIVE, z);
    }

    public boolean isEnableVisitorLive() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_VISITOR_LIVE, true);
    }

    public void setEnableTurntableUpdateTip(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_TURNTABLE_UPDATE_TIP, z);
    }

    public boolean isEnableTurntableUpdateTip() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_TURNTABLE_UPDATE_TIP, true);
    }

    public void setEnableWeekStar(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_WEEK_STAR, z);
    }

    public boolean isEnableWeekStar() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_WEEK_STAR, true);
    }

    public void setEnableShowCurrentTop10(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_SHOW_CURRENT_TOP, z);
    }

    public boolean isEnableShowCurrentTop10() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_SHOW_CURRENT_TOP, false);
    }

    public void setEnableGiftWall(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_GIFT_WALL, z);
    }

    public boolean isEnableGiftWall() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_GIFT_WALL, true);
    }

    public void setEnableAchievement(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_ACHIEVEMENT, z);
    }

    public boolean isEnableAchievement() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_ACHIEVEMENT, true);
    }

    public void setEnableComponents(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_COMPONENTS, z);
    }

    public boolean isEnableComponents() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_COMPONENTS, true);
    }

    public void setEnableTranslation(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_TRANSLATION, z);
    }

    public boolean isEnableTranslation() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_TRANSLATION, true);
    }

    public void setEnablePrivateMsg(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_PRIVATE_MSG, z);
    }

    public boolean isEnablePrivateMsg() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_PRIVATE_MSG, true);
    }

    public void setEnableVisitorPermission(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_VISITOR_PERMISSION, z);
    }

    public boolean isEnableVisitorPermission() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_VISITOR_PERMISSION, false);
    }

    public void setEnableExchangeProportion(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_EXCHANGE_PROPORTION, z);
    }

    public boolean isEnableExchangeProportion() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_EXCHANGE_PROPORTION, true);
    }

    public void setEnableFeeTag(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_FEE_TAG, z);
    }

    public boolean isEnableFeeTag() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_FEE_TAG, false);
    }

    public void setEnableLiveHelperJump(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_LIVE_HELPER_JUMP, z);
    }

    public boolean isEnableLiveHelperJump() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_LIVE_HELPER_JUMP, false);
    }

    public void setEnableLogEventReport(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_LOG_EVENT_REPORT, z);
    }

    public boolean isEnableLogEventReport() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_LOG_EVENT_REPORT, false);
    }

    public void setEnableOfflinePrivateMsg(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_OFFLINE_PRIVATE_MSG, z);
    }

    public boolean isEnableOfflinePrivateMsg() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_OFFLINE_PRIVATE_MSG, true);
    }

    public String getOnlineUserLevelFilter() {
        return SPUtils.getInstance("fq_config").getString("liveOnlineUserListLevelFilter", "1");
    }

    public void setOnlineUserLevelFilter(String str) {
        SPUtils.getInstance("fq_config").put("liveOnlineUserListLevelFilter", str);
    }

    public String getOnlineUserListSize() {
        return SPUtils.getInstance("fq_config").getString("liveInitOnlineUserListSize", "10");
    }

    public void setOnlineUserListSize(String str) {
        SPUtils.getInstance("fq_config").put("liveInitOnlineUserListSize", str);
    }

    public ArrayList<String> getLiveRankConfig() {
        return this.liveRankConfig;
    }

    public void setLiveRankConfig(ArrayList<String> arrayList) {
        this.liveRankConfig = arrayList;
    }

    public void setLocalResourceVersion(String str) {
        SPUtils.getInstance("fq_config").put(this.LOCAL_RESOURCE_VERSION_KEY, str);
    }

    public String getLocalResourceVersion() {
        return SPUtils.getInstance("fq_config").getString(this.LOCAL_RESOURCE_VERSION_KEY, null);
    }

    public WatermarkConfigEntity getWatermarkConfig() {
        return this.watermarkConfig;
    }

    public void setWatermarkConfig(WatermarkConfigEntity watermarkConfigEntity) {
        this.watermarkConfig = watermarkConfigEntity;
    }

    public void setEnableQMInteract(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_QM_INTERACT, z);
    }

    public boolean isEnableQMInteract() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_QM_INTERACT, true);
    }

    public void setEnableQMInteractRedDot(boolean z) {
        SPUtils.getInstance("fq_config").put(this.IS_ENABLE_QM_INTERACT_RED_DOT, z);
    }

    public boolean isEnableQMInteractRedDot() {
        return SPUtils.getInstance("fq_config").getBoolean(this.IS_ENABLE_QM_INTERACT_RED_DOT, true);
    }
}
