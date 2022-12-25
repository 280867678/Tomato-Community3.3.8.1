package com.tencent.liteav.basic.util;

/* loaded from: classes3.dex */
public class TXCCommonUtil {
    private static String mAppID = "";
    private static String mStrAppVersion = "";
    public static String pituLicencePath = "YTFaceSDK.licence";

    private static native int nativeGetSDKID();

    private static native String nativeGetSDKVersion();

    static {
        TXCSystemUtil.m2873e();
    }

    public static int[] getSDKVersion() {
        String[] split = nativeGetSDKVersion().split("\\.");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                iArr[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                iArr[i] = 0;
            }
        }
        return iArr;
    }

    public static String getSDKVersionStr() {
        return nativeGetSDKVersion();
    }

    public static int getSDKID() {
        return nativeGetSDKID();
    }

    public static String getFileExtension(String str) {
        int lastIndexOf;
        if (str == null || str.length() <= 0 || (lastIndexOf = str.lastIndexOf(46)) <= -1 || lastIndexOf >= str.length() - 1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getStreamIDByStreamUrl(String str) {
        if (str != null && str.length() != 0) {
            int indexOf = str.indexOf("?");
            if (indexOf != -1) {
                str = str.substring(0, indexOf);
            }
            if (str != null && str.length() != 0) {
                int lastIndexOf = str.lastIndexOf("/");
                if (lastIndexOf != -1) {
                    str = str.substring(lastIndexOf + 1);
                }
                if (str != null && str.length() != 0) {
                    int indexOf2 = str.indexOf(".");
                    if (indexOf2 != -1) {
                        str = str.substring(0, indexOf2);
                    }
                    if (str != null && str.length() != 0) {
                        return str;
                    }
                }
            }
        }
        return null;
    }

    public static void setAppVersion(String str) {
        mStrAppVersion = str;
    }

    public static void setPituLicencePath(String str) {
        pituLicencePath = str;
    }

    public static String getAppVersion() {
        return mStrAppVersion;
    }

    public static void setAppID(String str) {
        mAppID = str;
    }

    public static String getAppID() {
        return mAppID;
    }
}
