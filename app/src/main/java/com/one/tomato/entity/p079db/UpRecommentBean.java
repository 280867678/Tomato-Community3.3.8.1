package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.UpRecommentBean */
/* loaded from: classes3.dex */
public class UpRecommentBean extends LitePalSupport {
    private String avatar;
    private String name;
    private String signature;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int upId;

    public int getUpId() {
        return this.upId;
    }

    public void setUpId(int i) {
        this.upId = i;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
