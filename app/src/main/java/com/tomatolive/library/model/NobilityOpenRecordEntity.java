package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class NobilityOpenRecordEntity {
    public String createTime;
    public String nobilityName;
    public String nobilityStatus;
    public String openPrice;
    public String openType;
    public String rebatePrice;
    public String userName;

    public boolean isOpen() {
        return TextUtils.equals(this.openType, "1");
    }

    public boolean isRenew() {
        return TextUtils.equals(this.openType, "2");
    }
}
