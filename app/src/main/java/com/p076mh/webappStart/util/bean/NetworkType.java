package com.p076mh.webappStart.util.bean;

import com.sensorsdata.analytics.android.sdk.AopConstants;

/* renamed from: com.mh.webappStart.util.bean.NetworkType */
/* loaded from: classes3.dex */
public enum NetworkType {
    NETWORK_WIFI(AopConstants.WIFI),
    NETWORK_4G("4g"),
    NETWORK_2G("2g"),
    NETWORK_3G("3g"),
    NETWORK_UNKNOWN("unknown"),
    NETWORK_NO("none");
    
    private String desc;

    NetworkType(String str) {
        this.desc = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.desc;
    }
}
