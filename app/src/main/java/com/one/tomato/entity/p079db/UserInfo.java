package com.one.tomato.entity.p079db;

import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.UpSubscriberVo;
import com.one.tomato.utils.AppUtil;
import java.util.ArrayList;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.UserInfo */
/* loaded from: classes3.dex */
public class UserInfo extends LitePalSupport {
    private String account;
    private ArrayList<String> accountStatus;
    private int adultLookFlag;
    private int alreadyPublishCnt;
    private int autoSwitch;
    private String avatar;
    private String backgroundPicUrl;
    private String birth;
    private String city;
    private int countOfSubscriber;
    private String country;
    private String countryCodeStr;
    private String createTime;
    private int currentLevelIndex;
    private String email;
    private String expireTime;
    private int favorCount;
    private int followFlag;
    private String friendshipIntention;
    private String gameChannel;
    private int goldPorterFlag;
    private String inviteCode;
    private String isRisk;
    private ArrayList<UpSubscriberVo> listTop3Subscriber;
    private int localVersion;
    private String marriage;
    private int maxPrice;
    private int maxPriceMonth;
    private int maxPriceSeason;
    private int maxPriceYear;
    private int maxPublishCnt;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int memberId;
    private boolean memberIsAnchor;
    private int messageCount;
    private String name;
    private String notice;
    private boolean official;
    private String officialTime;
    private String password;
    private String phone;
    private int priceMonth;
    private int priceSeason;
    private int priceYear;
    private String province;
    private int publishCount;
    private String reviewTime;
    private int reviewType = -1;
    private int roleType;
    private String sex;
    private String sexualOrientation;
    private int shieldFlag;
    private String signature;
    private int status;
    private int subscribeCount;
    private int subscribeFlag;
    private int subscribeSwitch;
    private String upHostType;
    private int upLevel;
    private String updateTime;
    private int userFansCount;
    private int userFollowCount;
    private String userId;
    private int userType;
    private int vipType;

    public int getGoldPorterFlag() {
        return this.goldPorterFlag;
    }

    public void setGoldPorterFlag(int i) {
        this.goldPorterFlag = i;
    }

    public int getAlreadyPublishCnt() {
        return this.alreadyPublishCnt;
    }

    public void setAlreadyPublishCnt(int i) {
        this.alreadyPublishCnt = i;
    }

    public int getMaxPublishCnt() {
        return this.maxPublishCnt;
    }

    public void setMaxPublishCnt(int i) {
        this.maxPublishCnt = i;
    }

    public int getRoleType() {
        return this.roleType;
    }

    public void setRoleType(int i) {
        this.roleType = i;
    }

    public String getReviewTime() {
        return this.reviewTime;
    }

    public void setReviewTime(String str) {
        this.reviewTime = str;
    }

    public int getReviewType() {
        return this.reviewType;
    }

    public void setReviewType(int i) {
        this.reviewType = i;
    }

    public String getOfficialTime() {
        return this.officialTime;
    }

    public void setOfficialTime(String str) {
        this.officialTime = str;
    }

    public boolean isOfficial() {
        return this.official;
    }

    public void setOfficial(boolean z) {
        this.official = z;
    }

    public String getCountryCodeStr() {
        if (TextUtils.isEmpty(this.countryCodeStr)) {
            this.countryCodeStr = "+86";
        }
        return this.countryCodeStr;
    }

    public boolean isMemberIsAnchor() {
        return this.memberIsAnchor;
    }

    public void setMemberIsAnchor(boolean z) {
        this.memberIsAnchor = z;
    }

    public void setCountryCodeStr(String str) {
        this.countryCodeStr = str;
    }

    public String getInviteCode() {
        return this.inviteCode;
    }

    public void setInviteCode(String str) {
        this.inviteCode = str;
    }

    public int getCurrentLevelIndex() {
        if (this.currentLevelIndex == 0) {
            this.currentLevelIndex = 1;
        }
        return this.currentLevelIndex;
    }

    public void setCurrentLevelIndex(int i) {
        this.currentLevelIndex = i;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public int getUserType() {
        if (this.userType == 0) {
            this.userType = 1;
        }
        return this.userType;
    }

    public void setUserType(int i) {
        this.userType = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getSignatureHint() {
        if (TextUtils.isEmpty(this.signature)) {
            this.signature = AppUtil.getString(R.string.user_no_signature);
        }
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String getBackgroundPicUrl() {
        return this.backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String str) {
        this.backgroundPicUrl = str;
    }

    public int getUserFollowCount() {
        if (this.userFollowCount <= 0) {
            this.userFollowCount = 0;
        }
        return this.userFollowCount;
    }

    public void setUserFollowCount(int i) {
        this.userFollowCount = i;
    }

    public int getPublishCount() {
        if (this.publishCount <= 0) {
            this.publishCount = 0;
        }
        return this.publishCount;
    }

    public void setPublishCount(int i) {
        this.publishCount = i;
    }

    public int getFavorCount() {
        if (this.favorCount <= 0) {
            this.favorCount = 0;
        }
        return this.favorCount;
    }

    public void setFavorCount(int i) {
        this.favorCount = i;
    }

    public int getMessageCount() {
        if (this.messageCount <= 0) {
            this.messageCount = 0;
        }
        return this.messageCount;
    }

    public void setMessageCount(int i) {
        this.messageCount = i;
    }

    public int getFollowFlag() {
        return this.followFlag;
    }

    public void setFollowFlag(int i) {
        this.followFlag = i;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public String getSex() {
        if (TextUtils.isEmpty(this.sex)) {
            this.sex = "2";
        }
        return this.sex;
    }

    public String getSexDes() {
        return "1".equals(getSex()) ? "男" : "女";
    }

    public int getShieldFlag() {
        return this.shieldFlag;
    }

    public void setShieldFlag(int i) {
        this.shieldFlag = i;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getCreateTime() {
        if (TextUtils.isEmpty(this.createTime)) {
            this.createTime = "2018.05.01";
        }
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public int getAdultLookFlag() {
        return this.adultLookFlag;
    }

    public void setAdultLookFlag(int i) {
        this.adultLookFlag = i;
    }

    public int getUserFansCount() {
        if (this.userFansCount <= 0) {
            this.userFansCount = 0;
        }
        return this.userFansCount;
    }

    public void setUserFansCount(int i) {
        this.userFansCount = i;
    }

    public String getBirth() {
        return this.birth;
    }

    public String getBirthDesc() {
        return this.birth;
    }

    public void setBirth(String str) {
        this.birth = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String str) {
        this.province = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getLocation() {
        String str = "";
        if (!TextUtils.isEmpty(this.country)) {
            String str2 = "中國";
            if (str2.equals(this.country)) {
                String str3 = this.province;
                if (!TextUtils.isEmpty(str3) && !isCityProvince(str3)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str3);
                    if (!TextUtils.isEmpty(this.city)) {
                        str = this.city;
                    }
                    sb.append(str);
                    return sb.toString();
                }
                if (!TextUtils.isEmpty(str3)) {
                    str2 = str + str3;
                }
                return str2;
            }
            return this.country;
        }
        return str;
    }

    private boolean isCityProvince(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : new String[]{"北京市", "天津市", "上海市", "重慶市"}) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public String getSexualOrientation() {
        return this.sexualOrientation;
    }

    public String getSexualOrientationDesc() {
        if (TextUtils.isEmpty(this.sex)) {
            this.sex = "2";
        }
        if (TextUtils.isEmpty(this.sexualOrientation)) {
            this.sexualOrientation = "1".equals(this.sex) ? "2" : "1";
        }
        if (!"1".equals(this.sexualOrientation)) {
            if ("2".equals(this.sexualOrientation)) {
                return "女";
            }
            if ("3".equals(this.sexualOrientation)) {
                return "双性恋";
            }
            if ("1".equals(this.sex)) {
                return "女";
            }
        }
        return "男";
    }

    public void setSexualOrientation(String str) {
        this.sexualOrientation = str;
    }

    public String getMarriage() {
        if ("0".equals(this.marriage)) {
            this.marriage = "";
        }
        return this.marriage;
    }

    public String getMarriageDesc() {
        return "1".equals(this.marriage) ? "单身" : "2".equals(this.marriage) ? "恋爱中" : "3".equals(this.marriage) ? "已婚" : "4".equals(this.marriage) ? "离异" : "5".equals(this.marriage) ? "丧偶" : "单身";
    }

    public void setMarriage(String str) {
        this.marriage = str;
    }

    public String getFriendshipIntention() {
        return this.friendshipIntention;
    }

    public String getFriendshipIntentionDesc() {
        return "1".equals(this.friendshipIntention) ? "勿扰" : "2".equals(this.friendshipIntention) ? "求撩" : "3".equals(this.friendshipIntention) ? "走心" : "4".equals(this.friendshipIntention) ? "走肾" : "求撩";
    }

    public void setFriendshipIntention(String str) {
        this.friendshipIntention = str;
    }

    public ArrayList<String> getAccountStatus() {
        return this.accountStatus;
    }

    public void setAccountStatus(ArrayList<String> arrayList) {
        this.accountStatus = arrayList;
    }

    public int getLocalVersion() {
        return this.localVersion;
    }

    public void setLocalVersion(int i) {
        this.localVersion = i;
    }

    public String getUpHostType() {
        return this.upHostType;
    }

    public void setUpHostType(String str) {
        this.upHostType = str;
    }

    public int getVipType() {
        return this.vipType;
    }

    public void setVipType(int i) {
        this.vipType = i;
    }

    public String getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(String str) {
        this.expireTime = str;
    }

    public String getGameChannel() {
        if (TextUtils.isEmpty(this.gameChannel)) {
            this.gameChannel = "1_1.000_0";
        }
        return this.gameChannel;
    }

    public void setGameChannel(String str) {
        this.gameChannel = str;
    }

    public int getAutoSwitch() {
        return this.autoSwitch;
    }

    public void setAutoSwitch(int i) {
        this.autoSwitch = i;
    }

    public String getIsRisk() {
        return this.isRisk;
    }

    public void setIsRisk(String str) {
        this.isRisk = str;
    }

    public int getUpLevel() {
        return this.upLevel;
    }

    public void setUpLevel(int i) {
        this.upLevel = i;
    }

    public int getSubscribeCount() {
        return this.subscribeCount;
    }

    public void setSubscribeCount(int i) {
        this.subscribeCount = i;
    }

    public int getMaxPriceMonth() {
        return this.maxPriceMonth;
    }

    public void setMaxPriceMonth(int i) {
        this.maxPriceMonth = i;
    }

    public int getMaxPriceSeason() {
        return this.maxPriceSeason;
    }

    public void setMaxPriceSeason(int i) {
        this.maxPriceSeason = i;
    }

    public int getMaxPriceYear() {
        return this.maxPriceYear;
    }

    public void setMaxPriceYear(int i) {
        this.maxPriceYear = i;
    }

    public int getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(int i) {
        this.maxPrice = i;
    }

    public int getSubscribeFlag() {
        return this.subscribeFlag;
    }

    public void setSubscribeFlag(int i) {
        this.subscribeFlag = i;
    }

    public int getSubscribeSwitch() {
        return this.subscribeSwitch;
    }

    public void setSubscribeSwitch(int i) {
        this.subscribeSwitch = i;
    }

    public ArrayList<UpSubscriberVo> getListTop3Subscriber() {
        return this.listTop3Subscriber;
    }

    public void setListTop3Subscriber(ArrayList<UpSubscriberVo> arrayList) {
        this.listTop3Subscriber = arrayList;
    }

    public String getNotice() {
        return this.notice;
    }

    public void setNotice(String str) {
        this.notice = str;
    }

    public int getPriceMonth() {
        return this.priceMonth;
    }

    public void setPriceMonth(int i) {
        this.priceMonth = i;
    }

    public int getPriceSeason() {
        return this.priceSeason;
    }

    public void setPriceSeason(int i) {
        this.priceSeason = i;
    }

    public int getPriceYear() {
        return this.priceYear;
    }

    public void setPriceYear(int i) {
        this.priceYear = i;
    }

    public int getCountOfSubscriber() {
        return this.countOfSubscriber;
    }

    public void setCountOfSubscriber(int i) {
        this.countOfSubscriber = i;
    }
}
