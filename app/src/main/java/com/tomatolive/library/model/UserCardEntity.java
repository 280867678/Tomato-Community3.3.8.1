package com.tomatolive.library.model;

import android.text.TextUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class UserCardEntity {
    public String anchorGrade;
    public UserAchievementEntity userAchievement;
    public String userGiftWallNum = "0";
    public UserGuardEntity userGuard;
    public List<ImpressionEntity> userImpression;
    public List<String> userMark;

    /* loaded from: classes3.dex */
    public static class UserAchievementEntity {
        public String achievementTotalNum = "0";
        public List<String> achievementUrl;
    }

    /* loaded from: classes3.dex */
    public static class UserGuardEntity {
        public String guardCount = "";
        public String url;
    }

    public String getGuardCount() {
        UserGuardEntity userGuardEntity = this.userGuard;
        return userGuardEntity == null ? "0" : userGuardEntity.guardCount;
    }

    public String getGuardUrl() {
        UserGuardEntity userGuardEntity = this.userGuard;
        return userGuardEntity == null ? "" : userGuardEntity.url;
    }

    public List<String> getAchievementUrls() {
        UserAchievementEntity userAchievementEntity = this.userAchievement;
        if (userAchievementEntity == null) {
            return null;
        }
        return userAchievementEntity.achievementUrl;
    }

    public String getAchievementTotalNum() {
        UserAchievementEntity userAchievementEntity = this.userAchievement;
        return userAchievementEntity == null ? "0" : userAchievementEntity.achievementTotalNum;
    }

    public List<String> getMarks() {
        return this.userMark;
    }

    public String getAnchorGrade() {
        if (TextUtils.isEmpty(this.anchorGrade)) {
            return null;
        }
        return this.anchorGrade;
    }
}
