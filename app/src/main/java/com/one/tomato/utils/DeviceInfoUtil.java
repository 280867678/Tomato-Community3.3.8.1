package com.one.tomato.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.one.tomato.entity.AppInfoBean;
import com.one.tomato.mvp.base.BaseApplication;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public class DeviceInfoUtil {
    private static String devCodeFileName = "devIf";
    private static Context mContext = BaseApplication.getApplication();
    private static String uniqueID = "";

    public static String getUniqueDeviceID() {
        try {
            if (TextUtils.isEmpty(uniqueID)) {
                uniqueID = PreferencesUtil.getInstance().getString("deviceNo");
            }
            if (TextUtils.isEmpty(uniqueID)) {
                uniqueID = FileUtil.readSDCardData(new File(FileUtil.getTextCacheDir(), devCodeFileName).getPath());
                if (!TextUtils.isEmpty(uniqueID)) {
                    PreferencesUtil.getInstance().putString("deviceNo", uniqueID);
                }
            }
            if (TextUtils.isEmpty(uniqueID)) {
                String imei = getIMEI();
                String androidId = getAndroidId();
                uniqueID = imei + androidId + "";
                LogUtil.m3783i("----->device_id ：", imei);
                LogUtil.m3783i("----->android_id ：", androidId);
                LogUtil.m3783i("----->mac ：", "");
                if ("".equals(uniqueID)) {
                    uniqueID = getMd5(UUID.randomUUID().toString());
                    LogUtil.m3783i("----->uniqueID 随机字符串MD5加密：", uniqueID);
                } else {
                    uniqueID = getMd5(uniqueID);
                    LogUtil.m3783i("----->uniqueID 三个值合并为一个字符串MD5加密：", uniqueID);
                }
                PreferencesUtil.getInstance().putString("deviceNo", uniqueID);
                FileUtil.writeSDCardData(new File(FileUtil.getTextCacheDir(), devCodeFileName).getPath(), uniqueID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            uniqueID = "";
        }
        return uniqueID;
    }

    private static String getMd5(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] bArr = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                bArr[i] = (byte) charArray[i];
            }
            byte[] digest = messageDigest.digest(bArr);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i2 = b & 255;
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private static String getAndroidId() {
        String string = Settings.Secure.getString(mContext.getContentResolver(), "android_id");
        if (string == null) {
            string = "";
        }
        String lowerCase = string.toLowerCase();
        return "9774d56d682e549c".equals(lowerCase) ? "" : lowerCase;
    }

    private static boolean checkPermission(Context context, String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0) {
                    return true;
                }
            } catch (Exception unused) {
            }
        } else if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
            return true;
        }
        return false;
    }

    private static String getIMEI() {
        String deviceId;
        try {
            if (!checkPermission(mContext, "android.permission.READ_PHONE_STATE") || (deviceId = ((TelephonyManager) mContext.getSystemService("phone")).getDeviceId()) == null) {
                return "";
            }
            Log.e("----->IMEI", deviceId);
            return deviceId;
        } catch (Exception e) {
            Log.e("----->TelephonyManager", "getDeviceId:" + e.toString());
            e.printStackTrace();
        }
        return "";
    }

    public static List<AppInfoBean> getPaKageAll(PackageManager packageManager) {
        ArrayList arrayList = new ArrayList();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            int i2 = installedPackages.get(i).applicationInfo.flags;
            ApplicationInfo applicationInfo = installedPackages.get(i).applicationInfo;
            if ((i2 & 1) <= 0) {
                AppInfoBean appInfoBean = new AppInfoBean();
                appInfoBean.applicationName = installedPackages.get(i).applicationInfo.loadLabel(packageManager).toString();
                appInfoBean.packageName = installedPackages.get(i).packageName;
                appInfoBean.appVersionCode = String.valueOf(AppUtil.getVersionCode());
                appInfoBean.appVersionName = AppUtil.getVersionName();
                arrayList.add(appInfoBean);
            }
        }
        return arrayList;
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean isOverNougat() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean isOverO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static String getDeviceTypeName() {
        return Build.MODEL;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getPhoneOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceNick() {
        return BluetoothAdapter.getDefaultAdapter().getName();
    }
}
