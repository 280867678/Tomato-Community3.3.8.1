package com.one.tomato.entity;

import java.util.Objects;

/* loaded from: classes3.dex */
public class LocationCity {
    private String cityCode;
    private String cityName;
    private String provinceCode;

    public LocationCity(String str) {
        this.cityName = str;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String str) {
        this.cityName = str;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String str) {
        this.cityCode = str;
    }

    public String getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(String str) {
        this.provinceCode = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocationCity) {
            return Objects.equals(getCityName(), ((LocationCity) obj).getCityName());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getCityName());
    }
}
