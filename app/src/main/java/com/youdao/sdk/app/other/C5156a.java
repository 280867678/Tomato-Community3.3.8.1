package com.youdao.sdk.app.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.youdao.sdk.common.YDUrlGenerator;
import java.util.Map;

/* renamed from: com.youdao.sdk.app.other.a */
/* loaded from: classes4.dex */
public class C5156a {

    /* renamed from: a */
    private Context f5893a;

    /* renamed from: a */
    public String m228a() {
        return "1.1";
    }

    public C5156a(Context context) {
        this.f5893a = context;
    }

    /* renamed from: b */
    public Map<String, String> m227b() {
        return new YDUrlGenerator(this.f5893a).generateUrlMap();
    }

    /* renamed from: c */
    public String m226c() {
        String ssid = ((WifiManager) this.f5893a.getSystemService(AopConstants.WIFI)).getConnectionInfo().getSSID();
        return ssid == null ? "" : (!ssid.startsWith("\"") || !ssid.endsWith("\"")) ? ssid : ssid.substring(1, ssid.length() - 1);
    }

    /* renamed from: d */
    public String m225d() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.f5893a.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return "unknown";
        }
        int type = activeNetworkInfo.getType();
        if (type != 0) {
            return type != 1 ? activeNetworkInfo.getTypeName() : AopConstants.WIFI;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 0:
                return "unknown";
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2g";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3g";
            case 13:
                return "4g";
            default:
                return activeNetworkInfo.getTypeName();
        }
    }
}
