package com.one.tomato.entity;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/* loaded from: classes3.dex */
public class Country extends BaseIndexPinyinBean {
    private String countryCode;
    private String countryName;
    private boolean isTop;

    public Country(String str) {
        this.countryCode = str;
    }

    public Country(String str, String str2) {
        this.countryCode = str;
        this.countryName = str2;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean
    public String getTarget() {
        return this.countryName;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean
    public boolean isNeedToPinyin() {
        return !this.isTop;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexBean, com.mcxtzhang.indexlib.suspension.ISuspensionInterface
    public boolean isShowSuspension() {
        return !this.isTop;
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

    public boolean isTop() {
        return this.isTop;
    }

    public Country setTop(boolean z) {
        this.isTop = z;
        return this;
    }

    public int hashCode() {
        return this.countryCode.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Country)) {
            return false;
        }
        Country country = (Country) obj;
        return this == country || this.countryCode.equals(country.getCountryCode());
    }
}
