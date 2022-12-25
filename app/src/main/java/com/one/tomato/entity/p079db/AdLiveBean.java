package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.Serializable;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.AdLiveBean */
/* loaded from: classes3.dex */
public class AdLiveBean extends LitePalSupport implements Serializable {
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private String adId;
    private String allow_close;
    private String branchChannelId;
    private String componentId;
    private String content;
    private String forwardScope;
    private String img;
    private String method;
    private String name;
    private String platform;
    private int position;
    private String type;
    private String typeId;
    private String url;

    public String getAllow_close() {
        return this.allow_close;
    }

    public void setAllow_close(String str) {
        this.allow_close = str;
    }

    public String getBranchChannelId() {
        return this.branchChannelId;
    }

    public void setBranchChannelId(String str) {
        this.branchChannelId = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getForwardScope() {
        return this.forwardScope;
    }

    public void setForwardScope(String str) {
        this.forwardScope = str;
    }

    public String getId() {
        return this.adId;
    }

    public void setId(String str) {
        this.adId = str;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String str) {
        this.img = str;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String str) {
        this.typeId = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getComponentId() {
        return this.componentId;
    }

    public void setComponentId(String str) {
        this.componentId = str;
    }
}
