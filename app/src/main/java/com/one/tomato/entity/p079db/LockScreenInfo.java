package com.one.tomato.entity.p079db;

import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.LockScreenInfo */
/* loaded from: classes3.dex */
public class LockScreenInfo extends LitePalSupport {
    private String account;
    private String countryCode;
    private String countryName;
    private int errorCount = 5;
    private String lockScreenPwd;
    private int loginType;
    private String phone;

    public int getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(int i) {
        this.errorCount = i;
    }

    public String getLockScreenPwd() {
        return this.lockScreenPwd;
    }

    public void setLockScreenPwd(String str) {
        this.lockScreenPwd = str;
    }

    public int getLoginType() {
        return this.loginType;
    }

    public void setLoginType(int i) {
        this.loginType = i;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String str) {
        this.countryCode = str;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String str) {
        this.countryName = str;
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
}
