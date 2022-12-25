package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.CircleAllBean */
/* loaded from: classes3.dex */
public class CircleAllBean extends LitePalSupport {
    private int appId;
    private String beginTime;
    private int blackFlag;
    private String brief;
    private int categoryId;
    private String categoryName;
    private Object createTime;
    private String endTime;
    private long followCount;
    private int followFlag;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int groupId;
    private String logo;
    private int memberId;
    private String name;
    private int official;
    private String postType;

    public long getFollowCount() {
        return this.followCount;
    }

    public void setFollowCount(long j) {
        this.followCount = j;
    }

    public int getAppId() {
        return this.appId;
    }

    public void setAppId(int i) {
        this.appId = i;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String str) {
        this.beginTime = str;
    }

    public int getBlackFlag() {
        return this.blackFlag;
    }

    public void setBlackFlag(int i) {
        this.blackFlag = i;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String str) {
        this.brief = str;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int i) {
        this.categoryId = i;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public Object getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Object obj) {
        this.createTime = obj;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public int getFollowFlag() {
        return this.followFlag;
    }

    public void setFollowFlag(int i) {
        this.followFlag = i;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String str) {
        this.logo = str;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getOfficial() {
        return this.official;
    }

    public void setOfficial(int i) {
        this.official = i;
    }

    public String getPostType() {
        return this.postType;
    }

    public void setPostType(String str) {
        this.postType = str;
    }
}
