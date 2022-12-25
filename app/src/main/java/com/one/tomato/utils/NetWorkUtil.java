package com.one.tomato.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.one.tomato.mvp.base.BaseApplication;

/* loaded from: classes3.dex */
public class NetWorkUtil {
    private static Context mContext = BaseApplication.getApplication();

    public static boolean isNetworkConnected() {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null && allNetworkInfo.length > 0) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWiFi() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004e, code lost:
        if (r0.equalsIgnoreCase("CDMA2000") == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getNetWorkType() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        char c = 5;
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (activeNetworkInfo.getType() != 1) {
                if (activeNetworkInfo.getType() == 0) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                        case 16:
                            c = 2;
                            break;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                        case 17:
                            c = 3;
                            break;
                        case 13:
                        case 18:
                            c = 4;
                            break;
                        default:
                            String subtypeName = activeNetworkInfo.getSubtypeName();
                            if (!subtypeName.equalsIgnoreCase("TD-SCDMA")) {
                                if (!subtypeName.equalsIgnoreCase("WCDMA")) {
                                    break;
                                }
                            }
                            c = 3;
                            break;
                    }
                }
            } else {
                c = 1;
            }
        } else {
            c = 65535;
        }
        return c != 1 ? c != 2 ? c != 3 ? c != 4 ? "UNKNOWN" : "4G" : "3G" : "2G" : "WIFI";
    }
}
