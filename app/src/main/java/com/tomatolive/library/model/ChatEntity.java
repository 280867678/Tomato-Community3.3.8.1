package com.tomatolive.library.model;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChatEntity {
    private String anchorId;
    private String appId;
    private String carIcon;
    private String date;
    private String expGrade;
    private String giftDirPath;
    private String giftName;
    private String giftNum;
    private int guardType;
    private List<String> markUrls;
    private String msgSendName;
    private String msgText;
    private int msgType;
    private String openId;
    private String propId;
    private String propName;
    private String propNum;
    private String role;
    private String sex;
    private String targetAvatar;
    private String targetId;
    private String targetName;
    private String uid;
    private String userAvatar;
    private String userRole;
    private String weekStar;
    private int danmuType = 3;
    private int nobilityType = -1;
    private boolean isSetManager = false;
    private boolean isSomeoneBanPost = false;

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String str) {
        this.userRole = str;
    }

    public List<String> getMarkUrls() {
        List<String> list = this.markUrls;
        return list == null ? new ArrayList() : list;
    }

    public void setMarkUrls(List<String> list) {
        this.markUrls = list;
    }

    public void setWeekStar(String str) {
        this.weekStar = str;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String str) {
        this.openId = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public int getDanmuType() {
        return this.danmuType;
    }

    public void setDanmuType(int i) {
        this.danmuType = i;
    }

    public String getAnchorId() {
        return this.anchorId;
    }

    public void setAnchorId(String str) {
        this.anchorId = str;
    }

    public String getGiftNum() {
        return this.giftNum;
    }

    public void setGiftNum(String str) {
        this.giftNum = str;
    }

    public String getCarIcon() {
        return this.carIcon;
    }

    public void setCarIcon(String str) {
        this.carIcon = str;
    }

    public int getGuardType() {
        return this.guardType;
    }

    public void setGuardType(int i) {
        this.guardType = i;
    }

    public String getExpGrade() {
        return this.expGrade;
    }

    public void setExpGrade(String str) {
        this.expGrade = str;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public String getTargetAvatar() {
        String str = this.targetAvatar;
        return str == null ? "" : str;
    }

    public void setTargetAvatar(String str) {
        this.targetAvatar = str;
    }

    public String getTargetId() {
        String str = this.targetId;
        return str == null ? "" : str;
    }

    public void setTargetId(String str) {
        this.targetId = str;
    }

    public String getTargetName() {
        String str = this.targetName;
        return str == null ? "" : str;
    }

    public void setTargetName(String str) {
        this.targetName = str;
    }

    public String getUid() {
        String str = this.uid;
        return str == null ? "" : str;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getDate() {
        String str = this.date;
        return str == null ? "" : str;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getMsgSendName() {
        return this.msgSendName;
    }

    public void setMsgSendName(String str) {
        this.msgSendName = str;
    }

    public String getMsgText() {
        return this.msgText;
    }

    public void setMsgText(String str) {
        this.msgText = str;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int i) {
        this.msgType = i;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String str) {
        this.giftName = str;
    }

    public String getUserAvatar() {
        return this.userAvatar;
    }

    public void setUserAvatar(String str) {
        this.userAvatar = str;
    }

    public String getGiftDirPath() {
        return this.giftDirPath;
    }

    public void setGiftDirPath(String str) {
        this.giftDirPath = str;
    }

    public String getPropId() {
        return this.propId;
    }

    public void setPropId(String str) {
        this.propId = str;
    }

    public String getPropName() {
        return this.propName;
    }

    public void setPropName(String str) {
        this.propName = str;
    }

    public String getPropNum() {
        return this.propNum;
    }

    public void setPropNum(String str) {
        this.propNum = str;
    }

    public boolean isSomeoneBanPost() {
        return this.isSomeoneBanPost;
    }

    public void setSomeoneBanPost(boolean z) {
        this.isSomeoneBanPost = z;
    }

    public boolean isSetManager() {
        return this.isSetManager;
    }

    public void setSetManager(boolean z) {
        this.isSetManager = z;
    }

    public int getNobilityType() {
        return this.nobilityType;
    }

    public void setNobilityType(int i) {
        this.nobilityType = i;
    }

    public boolean isWeekStar() {
        return TextUtils.equals(this.weekStar, "1");
    }
}
