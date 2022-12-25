package com.tencent.liteav.basic.datareport;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.UUID;

/* loaded from: classes3.dex */
public class TXCDRApi {
    static final int NETWORK_TYPE_2G = 4;
    static final int NETWORK_TYPE_3G = 3;
    static final int NETWORK_TYPE_4G = 2;
    static final int NETWORK_TYPE_UNKNOWN = 255;
    static final int NETWORK_TYPE_WIFI = 1;
    private static String mAppName = "";
    private static String mDevId = "";
    private static String mDevType = "";
    private static String mDevUUID = "";
    private static String mMacAddr = "";
    private static String mNetType = "";
    private static String mSysVersion = "";
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static boolean initRpt = false;

    public static native int nativeGetStatusReportInterval();

    private static native void nativeInitDataReport();

    private static native void nativeInitEventInternal(String str, int i, int i2, TXCDRExtInfo tXCDRExtInfo);

    private static native void nativeReportDAUInterval(int i, int i2, String str);

    public static native void nativeReportEvent(String str, int i);

    public static native void nativeReportEvent40003(String str, int i, String str2, String str3);

    public static native void nativeSetCommonValue(String str, String str2);

    private static native void nativeSetEventValueInterval(String str, int i, String str2, String str3);

    private static native void nativeUninitDataReport();

    public static void InitEvent(Context context, String str, int i, int i2, TXCDRExtInfo tXCDRExtInfo) {
        setCommonInfo(context);
        if (str == null) {
            return;
        }
        nativeInitEventInternal(str, i, i2, tXCDRExtInfo);
    }

    public static void txSetEventValue(String str, int i, String str2, String str3) {
        nativeSetEventValueInterval(str, i, str2, str3);
    }

    public static void txSetEventIntValue(String str, int i, String str2, long j) {
        nativeSetEventValueInterval(str, i, str2, "" + j);
    }

    public static void txReportDAU(Context context, int i) {
        if (context != null) {
            setCommonInfo(context);
        }
        nativeReportDAUInterval(i, 0, "");
    }

    public static void txReportDAU(Context context, int i, int i2, String str) {
        if (context != null) {
            setCommonInfo(context);
        }
        nativeReportDAUInterval(i, i2, str);
    }

    public static void reportEvent40003(String str, int i, String str2, String str3) {
        nativeReportEvent40003(str, i, str2, str3);
    }

    public static int getStatusReportInterval() {
        return nativeGetStatusReportInterval();
    }

    public static void setCommonInfo(Context context) {
        mDevType = Build.MODEL;
        mNetType = Integer.toString(getNetworkType(context));
        if (mDevId.isEmpty()) {
            mDevId = getSimulateIDFA(context);
        }
        if (mDevUUID.isEmpty()) {
            mDevUUID = getDevUUID(context, mDevId);
        }
        String packageName = getPackageName(context);
        mAppName = getApplicationNameByPackageName(context, packageName) + ":" + packageName;
        mSysVersion = String.valueOf(Build.VERSION.SDK_INT);
        mMacAddr = getOrigMacAddr(context);
        txSetCommonInfo();
    }

    public static void txSetCommonInfo() {
        if (mDevType != null) {
            nativeSetCommonValue(TXCDRDef.f2512f, mDevType);
        }
        if (mNetType != null) {
            nativeSetCommonValue(TXCDRDef.f2513g, mNetType);
        }
        if (mDevId != null) {
            nativeSetCommonValue(TXCDRDef.f2514h, mDevId);
        }
        if (mDevUUID != null) {
            nativeSetCommonValue(TXCDRDef.f2515i, mDevUUID);
        }
        if (mAppName != null) {
            nativeSetCommonValue(TXCDRDef.f2516j, mAppName);
        }
        if (mSysVersion != null) {
            nativeSetCommonValue(TXCDRDef.f2518l, mSysVersion);
        }
        if (mMacAddr != null) {
            nativeSetCommonValue(TXCDRDef.f2519m, mMacAddr);
        }
    }

    public static void txSetAppVersion(String str) {
        if (str != null) {
            nativeSetCommonValue(TXCDRDef.f2517k, str);
        }
    }

    public static String txCreateToken() {
        return UUID.randomUUID().toString();
    }

    private static String byteArrayToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length << 1];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i + 1;
            char[] cArr2 = DIGITS_LOWER;
            cArr[i] = cArr2[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr[i3] = cArr2[bArr[i2] & 15];
        }
        return new String(cArr);
    }

    static {
        TXCSystemUtil.m2873e();
        nativeInitDataReport();
    }

    public static String string2Md5(String str) {
        String str2;
        if (str == null) {
            return "";
        }
        try {
            str2 = byteArrayToHexString(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            str2 = "";
        }
        return str2 == null ? "" : str2;
    }

    public static String doRead(Context context) {
        String str = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            String deviceId = telephonyManager != null ? telephonyManager.getDeviceId() : str;
            if (deviceId != null) {
                str = deviceId;
            }
        } catch (Exception unused) {
        }
        TXCLog.m2915d("rtmpsdk", "deviceinfo:" + str);
        return string2Md5(str);
    }

    public static String getOrigAndroidID(Context context) {
        String str;
        try {
            str = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            str = "";
        }
        return string2Md5(str);
    }

    public static String getOrigMacAddr(Context context) {
        String str;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(AopConstants.WIFI);
            WifiInfo connectionInfo = wifiManager != null ? wifiManager.getConnectionInfo() : null;
            str = connectionInfo != null ? connectionInfo.getMacAddress() : null;
            if (str != null) {
                try {
                    str = string2Md5(str.replaceAll(":", "").toUpperCase());
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
            str = "";
        }
        return str == null ? "" : str;
    }

    public static String getSimulateIDFA(Context context) {
        return doRead(context) + ";" + getOrigMacAddr(context) + ";" + getOrigAndroidID(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0095 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getDevUUID(Context context, String str) {
        String str2;
        String str3;
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.tencent.liteav.dev_uuid", 0);
        String str4 = "";
        String string = sharedPreferences.getString("com.tencent.liteav.key_dev_uuid", str4);
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/txrtmp/spuid");
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                int available = fileInputStream.available();
                if (available > 0) {
                    byte[] bArr = new byte[available];
                    fileInputStream.read(bArr);
                    str2 = new String(bArr, "UTF-8");
                } else {
                    str2 = str4;
                }
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    if (!string.isEmpty()) {
                    }
                    if (!str2.isEmpty()) {
                    }
                    if (!str4.isEmpty()) {
                    }
                    if (str2.isEmpty()) {
                    }
                    if (!string.equals(str2)) {
                    }
                    return str3;
                }
            } else {
                str2 = str4;
            }
        } catch (Exception e2) {
            e = e2;
            str2 = str4;
        }
        if (!string.isEmpty()) {
            str4 = string;
        }
        if (!str2.isEmpty()) {
            str4 = str2;
        }
        if (!str4.isEmpty()) {
            str3 = string2Md5(str + UUID.randomUUID().toString());
        } else {
            str3 = str4;
        }
        if (str2.isEmpty()) {
            try {
                File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/txrtmp");
                if (!file2.exists()) {
                    file2.mkdir();
                }
                File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/txrtmp/spuid");
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file3);
                fileOutputStream.write(str3.getBytes());
                fileOutputStream.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            str2 = str3;
        }
        if (!string.equals(str2)) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("key_user_id", str3);
            edit.commit();
        }
        return str3;
    }

    public static int getNetworkType(Context context) {
        if (context == null) {
            return 255;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return 255;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 255;
        }
        switch (telephonyManager.getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 4;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
            default:
                return 2;
        }
    }

    private static String getPackageName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getApplicationNameByPackageName(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 128)).toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static void initCrashReport(Context context) {
        String sDKVersionStr;
        try {
            synchronized (TXCDRApi.class) {
                if (!initRpt && context != null && (sDKVersionStr = TXCCommonUtil.getSDKVersionStr()) != null) {
                    SharedPreferences.Editor edit = context.getSharedPreferences("BuglySdkInfos", 0).edit();
                    edit.putString("8e50744bf0", sDKVersionStr);
                    edit.commit();
                    initRpt = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
