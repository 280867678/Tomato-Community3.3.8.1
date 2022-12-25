package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.LoginInfo */
/* loaded from: classes3.dex */
public class LoginInfo extends LitePalSupport {
    private String countryCode;
    private String email;
    private String liveToken = "";
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int memberId;
    private String phone;
    private String token;
    private int type;
    private int userType;

    public boolean isLogin() {
        return 2 == this.userType;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getToken() {
        return this.token;
    }

    public void setLiveToken(String str) {
        this.liveToken = str;
    }

    public String getLiveToken() {
        return this.liveToken;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String str) {
        this.countryCode = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int i) {
        this.userType = i;
    }
}
