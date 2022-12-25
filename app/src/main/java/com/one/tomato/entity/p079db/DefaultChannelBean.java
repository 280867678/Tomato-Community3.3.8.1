package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.Serializable;
import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.DefaultChannelBean */
/* loaded from: classes3.dex */
public class DefaultChannelBean extends LitePalSupport implements Serializable {
    private String categoryType;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int channelId;
    private String contentSource;
    private String contentType;
    private String createTime;
    private String englishName;
    private int isDefault;
    private String isSystemChannel;
    private boolean isTabSelect;
    private int memberId;
    private String name;
    private String refGroup;
    private String refTag;
    private String remark;
    private String sortNum;
    private String status;
    private String traditionalName;
    private String type;
    private String updateTime;
    private String versionNo;

    public DefaultChannelBean(int i) {
        this.channelId = i;
    }

    public DefaultChannelBean(int i, String str, String str2, String str3) {
        this.channelId = i;
        this.name = str;
        this.englishName = str2;
        this.traditionalName = str3;
    }

    public String getIsSystemChannel() {
        return this.isSystemChannel;
    }

    public void setIsSystemChannel(String str) {
        this.isSystemChannel = str;
    }

    public String getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(String str) {
        this.versionNo = str;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getCategoryType() {
        return this.categoryType;
    }

    public void setCategoryType(String str) {
        this.categoryType = str;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int i) {
        this.channelId = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(String str) {
        this.sortNum = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public void setEnglishName(String str) {
        this.englishName = str;
    }

    public String getTraditionalName() {
        return this.traditionalName;
    }

    public void setTraditionalName(String str) {
        this.traditionalName = str;
    }

    public int getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(int i) {
        this.isDefault = i;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public String getContentSource() {
        return this.contentSource;
    }

    public void setContentSource(String str) {
        this.contentSource = str;
    }

    public String getRefGroup() {
        return this.refGroup;
    }

    public void setRefGroup(String str) {
        this.refGroup = str;
    }

    public String getRefTag() {
        return this.refTag;
    }

    public void setRefTag(String str) {
        this.refTag = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String str) {
        this.remark = str;
    }

    public String getCreateTime() {
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

    public boolean isTabSelect() {
        return this.isTabSelect;
    }

    public void setTabSelect(boolean z) {
        this.isTabSelect = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && DefaultChannelBean.class == obj.getClass() && this.channelId == ((DefaultChannelBean) obj).channelId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.channelId));
    }
}
