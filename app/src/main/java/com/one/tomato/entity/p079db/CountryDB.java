package com.one.tomato.entity.p079db;

import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.CountryDB */
/* loaded from: classes3.dex */
public class CountryDB extends LitePalSupport {
    private String countryCode;
    private String countryName;

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

    public int hashCode() {
        return this.countryCode.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CountryDB)) {
            return false;
        }
        CountryDB countryDB = (CountryDB) obj;
        return this == countryDB || this.countryCode.equals(countryDB.getCountryCode());
    }
}
