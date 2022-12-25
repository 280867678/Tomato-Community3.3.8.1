package com.p076mh.webappStart.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.p076mh.webappStart.util.bean.NetworkType;
import java.text.DecimalFormat;

/* renamed from: com.mh.webappStart.util.NetWorkUtil */
/* loaded from: classes3.dex */
public class NetWorkUtil {
    private static int getNetworkClassByType(int i) {
        int i2 = -101;
        if (i != -101) {
            i2 = -1;
            if (i != -1) {
                switch (i) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 1;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return 2;
                    case 13:
                        return 3;
                    default:
                        return 0;
                }
            }
        }
        return i2;
    }

    static {
        new DecimalFormat("#.##");
    }

    public static NetworkType getCurrentNetworkType() {
        int networkClass = getNetworkClass();
        NetworkType networkType = NetworkType.NETWORK_UNKNOWN;
        if (networkClass != -101) {
            if (networkClass == -1) {
                return NetworkType.NETWORK_NO;
            }
            if (networkClass == 0) {
                return networkType;
            }
            if (networkClass == 1) {
                return NetworkType.NETWORK_2G;
            }
            if (networkClass == 2) {
                return NetworkType.NETWORK_3G;
            }
            return networkClass != 3 ? networkType : NetworkType.NETWORK_4G;
        }
        return NetworkType.NETWORK_WIFI;
    }

    private static int getNetworkClass() {
        int i = 0;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) WebApplication.getInstance().getApplication().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                i = -1;
            } else {
                int type = activeNetworkInfo.getType();
                if (type == 1) {
                    i = -101;
                } else if (type == 0) {
                    i = ((TelephonyManager) WebApplication.getInstance().getApplication().getSystemService("phone")).getNetworkType();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getNetworkClassByType(i);
    }
}
