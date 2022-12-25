package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.Serializable;
import java.util.Objects;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.SubGamesBean */
/* loaded from: classes3.dex */
public class SubGamesBean extends LitePalSupport implements Serializable {
    private String adArticleAvatarSec;
    private String adArticleContent;
    private String adArticleName;
    private String adBrandId;
    private String adBrandName;
    private String adDesc;
    private int adGameBGFishType;
    private int adGameBGType;
    private int adGameDirection;
    private String adGameFlag;
    private String adGameHeight;
    private String adGameId;
    private String adGameLink;
    private String adGameWidth;
    private String adLink;
    private String adLinkType;
    private String adLogoType;
    private String adName;
    private String adType;
    private String appId;
    private String categoryType;
    private String createTime;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int gameId;
    private String imageUrlSec;
    @Column(ignore = true)
    private boolean isSelector;
    private String materialType;
    private String memberId;
    private int openType;
    private String spreadChannelNo;
    private String status;
    private int subChannelId;
    private Object subGames;
    private String terminal;
    private String type;
    private String updateTime;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && SubGamesBean.class == obj.getClass()) {
            return this.adGameId.equals(((SubGamesBean) obj).adGameId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.adGameId);
    }

    public boolean isSelector() {
        return this.isSelector;
    }

    public void setSelector(boolean z) {
        this.isSelector = z;
    }

    public String getAdGameWidth() {
        return this.adGameWidth;
    }

    public void setAdGameWidth(String str) {
        this.adGameWidth = str;
    }

    public String getAdGameHeight() {
        return this.adGameHeight;
    }

    public void setAdGameHeight(String str) {
        this.adGameHeight = str;
    }

    public String getCategoryType() {
        return this.categoryType;
    }

    public void setCategoryType(String str) {
        this.categoryType = str;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String str) {
        this.memberId = str;
    }

    public String getAdArticleAvatarSec() {
        return this.adArticleAvatarSec;
    }

    public void setAdArticleAvatarSec(String str) {
        this.adArticleAvatarSec = str;
    }

    public String getAdArticleContent() {
        return this.adArticleContent;
    }

    public void setAdArticleContent(String str) {
        this.adArticleContent = str;
    }

    public String getAdArticleName() {
        return this.adArticleName;
    }

    public void setAdArticleName(String str) {
        this.adArticleName = str;
    }

    public String getAdBrandId() {
        return this.adBrandId;
    }

    public void setAdBrandId(String str) {
        this.adBrandId = str;
    }

    public String getAdDesc() {
        return this.adDesc;
    }

    public void setAdDesc(String str) {
        this.adDesc = str;
    }

    public String getAdGameFlag() {
        return this.adGameFlag;
    }

    public void setAdGameFlag(String str) {
        this.adGameFlag = str;
    }

    public String getAdGameId() {
        return this.adGameId;
    }

    public void setAdGameId(String str) {
        this.adGameId = str;
    }

    public String getAdLink() {
        return this.adLink;
    }

    public void setAdLink(String str) {
        this.adLink = str;
    }

    public String getAdLinkType() {
        return this.adLinkType;
    }

    public void setAdLinkType(String str) {
        this.adLinkType = str;
    }

    public String getAdLogoType() {
        return this.adLogoType;
    }

    public void setAdLogoType(String str) {
        this.adLogoType = str;
    }

    public String getAdName() {
        return this.adName;
    }

    public void setAdName(String str) {
        this.adName = str;
    }

    public String getAdType() {
        return this.adType;
    }

    public void setAdType(String str) {
        this.adType = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int i) {
        this.gameId = i;
    }

    public String getImageUrlSec() {
        return this.imageUrlSec;
    }

    public void setImageUrlSec(String str) {
        this.imageUrlSec = str;
    }

    public String getMaterialType() {
        return this.materialType;
    }

    public void setMaterialType(String str) {
        this.materialType = str;
    }

    public int getOpenType() {
        return this.openType;
    }

    public void setOpenType(int i) {
        this.openType = i;
    }

    public String getSpreadChannelNo() {
        return this.spreadChannelNo;
    }

    public void setSpreadChannelNo(String str) {
        this.spreadChannelNo = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public int getSubChannelId() {
        return this.subChannelId;
    }

    public void setSubChannelId(int i) {
        this.subChannelId = i;
    }

    public Object getSubGames() {
        return this.subGames;
    }

    public void setSubGames(Object obj) {
        this.subGames = obj;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public void setTerminal(String str) {
        this.terminal = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    public String getAdBrandName() {
        return this.adBrandName;
    }

    public void setAdBrandName(String str) {
        this.adBrandName = str;
    }

    public int getAdGameDirection() {
        return this.adGameDirection;
    }

    public void setAdGameDirection(int i) {
        this.adGameDirection = i;
    }

    public String getAdGameLink() {
        return this.adGameLink;
    }

    public void setAdGameLink(String str) {
        this.adGameLink = str;
    }

    public int getAdGameBGType() {
        return this.adGameBGType;
    }

    public void setAdGameBGType(int i) {
        this.adGameBGType = i;
    }

    public int getAdGameBGFishType() {
        return this.adGameBGFishType;
    }

    public void setAdGameBGFishType(int i) {
        this.adGameBGFishType = i;
    }
}
