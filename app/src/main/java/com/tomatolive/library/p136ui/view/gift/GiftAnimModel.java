package com.tomatolive.library.p136ui.view.gift;

import android.text.TextUtils;
import com.tomatolive.library.p136ui.view.gift.GiftFrameLayout;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.view.gift.GiftAnimModel */
/* loaded from: classes3.dex */
public class GiftAnimModel {
    private GiftFrameLayout.BarrageEndAnimationListener animationListener;
    public String appId;
    private boolean currentStart;
    private String effectType;
    private int giftCount;
    private String giftId;
    private String giftName;
    public String giftNum;
    private String giftPic;
    private String giftPrice;
    private int hitCombo;
    public boolean isProp;
    private int jumpCombo;
    public String onLineUrl;
    public String onlineDefaultUrl;
    public String openId;
    private Long sendGiftTime;
    private int sendIndex;
    public String sendRole;
    private String sendUserAvatar;
    public String sendUserExpGrade;
    public int sendUserGuardType;
    private String sendUserId;
    private String sendUserName;
    public int sendUserNobilityType;
    public String sendUserRole;
    public String sendUserSex;
    private String giftDirPath = "";
    private int giftShowTime = 3000;
    private int giftTotalCount = 0;

    public String toString() {
        return "GiftAnimModel{giftId='" + this.giftId + "', giftName='" + this.giftName + "', onLineUrl='" + this.onLineUrl + "', onlineDefaultUrl='" + this.onlineDefaultUrl + "', giftDirPath='" + this.giftDirPath + "', effectType='" + this.effectType + "', isProp='" + this.isProp + "'}";
    }

    public GiftAnimModel setOpenId(String str) {
        this.openId = str;
        return this;
    }

    public GiftAnimModel setAppId(String str) {
        this.appId = str;
        return this;
    }

    public int getSendUserNobilityType() {
        return this.sendUserNobilityType;
    }

    public GiftAnimModel setSendUserNobilityType(int i) {
        this.sendUserNobilityType = i;
        return this;
    }

    public String getSendUserRole() {
        return this.sendUserRole;
    }

    public GiftAnimModel setSendUserRole(String str) {
        this.sendUserRole = str;
        return this;
    }

    public String getSendRole() {
        return this.sendRole;
    }

    public GiftAnimModel setSendRole(String str) {
        this.sendRole = str;
        return this;
    }

    public GiftAnimModel setGiftNum(String str) {
        this.giftNum = str;
        return this;
    }

    public String getGiftNum() {
        return this.giftNum;
    }

    public int getSendUserGuardType() {
        return this.sendUserGuardType;
    }

    public GiftAnimModel setSendUserGuardType(int i) {
        this.sendUserGuardType = i;
        return this;
    }

    public String getSendUserExpGrade() {
        return this.sendUserExpGrade;
    }

    public GiftAnimModel setSendUserExpGrade(String str) {
        this.sendUserExpGrade = str;
        return this;
    }

    public String getSendUserSex() {
        return this.sendUserSex;
    }

    public GiftAnimModel setSendUserSex(String str) {
        this.sendUserSex = str;
        return this;
    }

    public boolean isPropPlay() {
        return !TextUtils.equals("2", this.effectType);
    }

    public boolean isFreeGift() {
        return NumberUtils.string2long(this.giftPrice, 0L) <= 0;
    }

    public GiftAnimModel setOnLineUrl(String str) {
        this.onLineUrl = str;
        return this;
    }

    public GiftAnimModel setOnlineDefaultUrl(String str) {
        this.onlineDefaultUrl = str;
        return this;
    }

    public String getEffectType() {
        String str = this.effectType;
        return str == null ? "" : str;
    }

    public GiftAnimModel setEffectType(String str) {
        this.effectType = str;
        return this;
    }

    public int getSendIndex() {
        return this.sendIndex;
    }

    public GiftAnimModel setSendIndex(int i) {
        this.sendIndex = i;
        return this;
    }

    public GiftAnimModel setProp(boolean z) {
        this.isProp = z;
        return this;
    }

    public String getGiftId() {
        return this.giftId;
    }

    public GiftAnimModel setGiftId(String str) {
        this.giftId = str;
        return this;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public GiftAnimModel setGiftName(String str) {
        this.giftName = str;
        return this;
    }

    public int getGiftCount() {
        return this.giftCount;
    }

    public GiftAnimModel setGiftCount(int i) {
        this.giftCount = i;
        return this;
    }

    public String getSendUserId() {
        return this.sendUserId;
    }

    public GiftAnimModel setSendUserId(String str) {
        this.sendUserId = str;
        return this;
    }

    public String getSendUserName() {
        return this.sendUserName;
    }

    public GiftAnimModel setSendUserName(String str) {
        this.sendUserName = str;
        return this;
    }

    public String getSendUserAvatar() {
        return this.sendUserAvatar;
    }

    public GiftAnimModel setSendUserAvatar(String str) {
        this.sendUserAvatar = str;
        return this;
    }

    public String getGiftPic() {
        return this.giftPic;
    }

    public GiftAnimModel setGiftPic(String str) {
        this.giftPic = str;
        return this;
    }

    public String getGiftPrice() {
        return this.giftPrice;
    }

    public GiftAnimModel setGiftPrice(String str) {
        this.giftPrice = str;
        return this;
    }

    public int getHitCombo() {
        return this.hitCombo;
    }

    public GiftAnimModel setHitCombo(int i) {
        this.hitCombo = i;
        return this;
    }

    public Long getSendGiftTime() {
        return this.sendGiftTime;
    }

    public GiftAnimModel setSendGiftTime(Long l) {
        this.sendGiftTime = l;
        return this;
    }

    public boolean isCurrentStart() {
        return this.currentStart;
    }

    public GiftAnimModel setCurrentStart(boolean z) {
        this.currentStart = z;
        return this;
    }

    public int getJumpCombo() {
        return this.jumpCombo;
    }

    public void setJumpCombo(int i) {
        this.jumpCombo = i;
    }

    public String getGiftDirPath() {
        return this.giftDirPath;
    }

    public GiftAnimModel setGiftDirPath(String str) {
        this.giftDirPath = str;
        return this;
    }

    public int getGiftShowTime() {
        return this.giftShowTime;
    }

    public GiftAnimModel setGiftShowTime(int i) {
        this.giftShowTime = i;
        return this;
    }

    public GiftFrameLayout.BarrageEndAnimationListener getAnimationListener() {
        return this.animationListener;
    }

    public GiftAnimModel setAnimationListener(GiftFrameLayout.BarrageEndAnimationListener barrageEndAnimationListener) {
        this.animationListener = barrageEndAnimationListener;
        return this;
    }

    public int getGiftTotalCount() {
        return this.giftTotalCount;
    }

    public void setGiftTotalCount(int i) {
        this.giftTotalCount = i;
    }

    public boolean isStartLottieAnim() {
        return !TextUtils.isEmpty(this.giftDirPath);
    }

    public boolean isSendSingleGift() {
        return TextUtils.equals(this.giftNum, "1");
    }
}
