package com.one.tomato.entity;

import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public class LocationProvince {
    private ArrayList<LocationCity> cityList;
    private String provinceCode;
    private String provinceName;

    public LocationProvince(String str) {
        this.provinceName = str;
    }

    public String getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(String str) {
        this.provinceCode = str;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String str) {
        this.provinceName = str;
    }

    public ArrayList<LocationCity> getCityList() {
        return this.cityList;
    }

    public void setCityList(ArrayList<LocationCity> arrayList) {
        this.cityList = arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocationProvince) {
            return Objects.equals(getProvinceName(), ((LocationProvince) obj).getProvinceName());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getProvinceName());
    }
}
