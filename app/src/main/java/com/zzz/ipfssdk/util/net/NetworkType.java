package com.zzz.ipfssdk.util.net;

import com.sensorsdata.analytics.android.sdk.AopConstants;

/* loaded from: classes4.dex */
public enum NetworkType {
    NETWORK_WIFI(AopConstants.WIFI),
    NETWORK_4G("4g"),
    NETWORK_2G("2g"),
    NETWORK_3G("3g"),
    NETWORK_UNKNOWN("unknown"),
    NETWORK_NO("none");
    

    /* renamed from: h */
    public String f5986h;

    NetworkType(String str) {
        this.f5986h = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.f5986h;
    }
}
