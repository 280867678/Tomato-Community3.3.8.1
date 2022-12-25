package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.Serializable;
import java.util.ArrayList;

/* renamed from: com.one.tomato.entity.db.GameTypeData */
/* loaded from: classes3.dex */
public class GameTypeData implements Serializable {
    private String adArticleAvatarSec;
    private String adArticleContent;
    private String adArticleName;
    private int adBrandId;
    private String adDesc;
    private String adGameFlag;
    private String adGameId;
    private String adLink;
    private String adLinkType;
    private String adLogoType;
    private String adName;
    private String adType;
    private String appId;
    private String createTime;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int gameId;
    private String imageUrlSec;
    private String materialType;
    private int openType;
    private String spreadChannelNo;
    private String status;
    private int subChannelId;
    private ArrayList<SubGamesBean> subGames;
    private String terminal;
    private String type;
    private String updateTime;

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

    public int getAdBrandId() {
        return this.adBrandId;
    }

    public void setAdBrandId(int i) {
        this.adBrandId = i;
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

    public ArrayList<SubGamesBean> getSubGames() {
        return this.subGames;
    }

    public void setSubGames(ArrayList<SubGamesBean> arrayList) {
        this.subGames = arrayList;
    }
}
