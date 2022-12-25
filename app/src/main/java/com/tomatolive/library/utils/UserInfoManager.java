package com.tomatolive.library.utils;

import android.text.TextUtils;
import com.blankj.utilcode.util.SPUtils;
import com.tomatolive.library.model.UserEntity;

/* loaded from: classes4.dex */
public class UserInfoManager {
    private final String APP_ID;
    private final String APP_OPEN_ID;
    private final String CHANNEL_TOKEN;
    private final String EXP_GRADE;
    private final String IS_ENTER_HIDE;
    private final String IS_ENTER_LIVE_PERMISSION;
    private final String IS_INBANGROUP;
    private final String IS_LOGIN;
    private final String IS_RISK;
    private final String IS_SUPER_ADMIN;
    private final String IS_VISITOR_USER;
    private final String SDK_USER_ID;
    private final String SPNAME;
    private final String TOKEN;
    private final String USER_AVATAR;
    private final String USER_NICKNAME;
    private final String USER_NOBITLITY_TYPE;
    private final String USER_SEX;

    private UserInfoManager() {
        this.SPNAME = "fq_user";
        this.IS_LOGIN = "isLogin";
        this.APP_OPEN_ID = "userId";
        this.APP_ID = LogConstants.APP_ID;
        this.TOKEN = "token";
        this.CHANNEL_TOKEN = "channelToken";
        this.SDK_USER_ID = "sdkUserId";
        this.USER_NICKNAME = "userNickname";
        this.USER_AVATAR = "userAvatar";
        this.USER_SEX = "userSex";
        this.USER_NOBITLITY_TYPE = "userNobitlityType";
        this.IS_INBANGROUP = "isInBanGroup";
        this.IS_ENTER_HIDE = "isEnterHide";
        this.IS_SUPER_ADMIN = "isSuperAdmin";
        this.IS_VISITOR_USER = "isVisitorUser";
        this.IS_ENTER_LIVE_PERMISSION = "isEnterLivePermission";
        this.EXP_GRADE = ConstantUtils.EXP_GRADE_ICON_KEY;
        this.IS_RISK = "IS_RISK";
    }

    /* loaded from: classes4.dex */
    private static class LazyHolder {
        private static final UserInfoManager INSTANCE = new UserInfoManager();

        private LazyHolder() {
        }
    }

    public static UserInfoManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void loadUserInfo(UserEntity userEntity) {
        if (userEntity == null) {
            return;
        }
        setLogin(true);
        setAppOpenId(userEntity.getUserId());
        setNickname(userEntity.getName());
        setAvatar(userEntity.getAvatar());
        setSex(userEntity.getSex());
        setChannelToken(userEntity.getToken());
        setRisk(userEntity.getIsRisk());
    }

    public String getToken() {
        return SPUtils.getInstance("fq_user").getBoolean("isLogin", false) ? SPUtils.getInstance("fq_user").getString("token", "") : "";
    }

    public void setToken(String str) {
        SPUtils.getInstance("fq_user").put("token", str);
    }

    public String getChannelToken() {
        return SPUtils.getInstance("fq_user").getString("channelToken", "");
    }

    public void setExpGrade(String str) {
        SPUtils.getInstance("fq_user").put(ConstantUtils.EXP_GRADE_ICON_KEY, str);
    }

    public String getExpGrade() {
        return SPUtils.getInstance("fq_user").getString(ConstantUtils.EXP_GRADE_ICON_KEY, "");
    }

    public void setChannelToken(String str) {
        SPUtils.getInstance("fq_user").put("channelToken", str);
    }

    public String getUserId() {
        return SPUtils.getInstance("fq_user").getBoolean("isLogin", false) ? SPUtils.getInstance("fq_user").getString("sdkUserId", "") : "";
    }

    public void setUserId(String str) {
        SPUtils.getInstance("fq_user").put("sdkUserId", str);
    }

    public String getAppOpenId() {
        return SPUtils.getInstance("fq_user").getString("userId", "");
    }

    public void setAppOpenId(String str) {
        SPUtils.getInstance("fq_user").put("userId", str);
    }

    public int getNobilityType() {
        return SPUtils.getInstance("fq_user").getInt("userNobitlityType", -1);
    }

    public void setNobilityType(int i) {
        SPUtils.getInstance("fq_user").put("userNobitlityType", i);
    }

    public String getAppId() {
        return SPUtils.getInstance("fq_user").getString(LogConstants.APP_ID, "");
    }

    public void setAppId(String str) {
        SPUtils.getInstance("fq_user").put(LogConstants.APP_ID, str);
    }

    public void setRisk(String str) {
        SPUtils.getInstance("fq_user").put("IS_RISK", str);
    }

    public String getRisk() {
        return SPUtils.getInstance("fq_user").getString("IS_RISK", "");
    }

    public String getUserNickname() {
        return SPUtils.getInstance("fq_user").getString("userNickname", "");
    }

    public String getAvatar() {
        return SPUtils.getInstance("fq_user").getString("userAvatar");
    }

    public String getUserSex() {
        return SPUtils.getInstance("fq_user").getString("userSex");
    }

    public void setAvatar(String str) {
        SPUtils.getInstance("fq_user").put("userAvatar", str);
    }

    public void setNickname(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SPUtils.getInstance("fq_user").put("userNickname", str.replaceAll("\"", "口"));
    }

    public void setSex(String str) {
        SPUtils.getInstance("fq_user").put("userSex", str);
    }

    public void setLogin(boolean z) {
        SPUtils.getInstance("fq_user").put("isLogin", z);
    }

    public boolean isLogin() {
        return SPUtils.getInstance("fq_user").getBoolean("isLogin", false) && !TextUtils.isEmpty(getAppOpenId()) && !TextUtils.isEmpty(getUserId()) && !TextUtils.isEmpty(getToken());
    }

    public boolean isNoLogin() {
        return !SPUtils.getInstance("fq_user").getBoolean("isLogin", false);
    }

    public void setInBanGroup(boolean z) {
        SPUtils.getInstance("fq_user").put("isInBanGroup", z);
    }

    public boolean isInBanGroup() {
        return SPUtils.getInstance("fq_user").getBoolean("isInBanGroup", false);
    }

    public void setEnterHide(boolean z) {
        SPUtils.getInstance("fq_user").put("isEnterHide", z);
    }

    public boolean isEnterHide() {
        return SPUtils.getInstance("fq_user").getBoolean("isEnterHide", false);
    }

    public void setEnterLivePermission(boolean z) {
        SPUtils.getInstance("fq_user").put("isEnterLivePermission", z);
    }

    public boolean isEnterLivePermission() {
        return SPUtils.getInstance("fq_user").getBoolean("isEnterLivePermission", true);
    }

    public void setSuperAdmin(boolean z) {
        SPUtils.getInstance("fq_user").put("isSuperAdmin", z);
    }

    public boolean isSuperAdmin() {
        return SPUtils.getInstance("fq_user").getBoolean("isSuperAdmin", false);
    }

    public void setVisitorUser(boolean z) {
        SPUtils.getInstance("fq_user").put("isVisitorUser", z);
    }

    public boolean isVisitorUser() {
        return SPUtils.getInstance("fq_user").getBoolean("isVisitorUser", false);
    }

    public void clearTokenInfo() {
        setLogin(false);
        setVisitorUser(false);
        setToken(null);
        SPUtils.getInstance("fq_user").remove("token", true);
        SPUtils.getInstance("fq_user").remove("isLogin", true);
        SPUtils.getInstance("fq_user").remove("isVisitorUser", true);
    }

    public void clearLoginInfo() {
        setLogin(false);
        setVisitorUser(false);
        setToken(null);
        setAppOpenId(null);
        setNobilityType(-1);
        SPUtils.getInstance("fq_user").remove("token", true);
        SPUtils.getInstance("fq_user").remove("isLogin", true);
        SPUtils.getInstance("fq_user").remove("isVisitorUser", true);
        SPUtils.getInstance("fq_user").remove("userId", true);
        SPUtils.getInstance("fq_user").remove("userNobitlityType", true);
    }
}
