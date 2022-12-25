package com.one.tomato.entity;

import java.util.Objects;

/* loaded from: classes3.dex */
public class LocationCountry {
    private String countryCode;
    private String countryName;
    private String shortPinyin;

    public LocationCountry(String str) {
        this.countryName = str;
    }

    public String getShortPinyin() {
        return this.shortPinyin;
    }

    public void setShortPinyin(String str) {
        this.shortPinyin = str;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocationCountry) {
            return Objects.equals(getCountryName(), ((LocationCountry) obj).getCountryName());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getCountryName());
    }
}
