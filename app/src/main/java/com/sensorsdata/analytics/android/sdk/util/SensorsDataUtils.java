package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.C3089R;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackAppViewScreenUrl;
import com.sensorsdata.analytics.android.sdk.SensorsDataSDKRemoteConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class SensorsDataUtils {
    private static final String SHARED_PREF_CHANNEL_EVENT = "sensorsdata.channel.event";
    private static final String SHARED_PREF_DEVICE_ID_KEY = "sensorsdata.device.id";
    private static final String SHARED_PREF_EDITS_FILE = "sensorsdata";
    private static final String SHARED_PREF_REQUEST_TIME = "sensorsdata.request.time";
    private static final String SHARED_PREF_REQUEST_TIME_RANDOM = "sensorsdata.request.time.random";
    private static final String SHARED_PREF_USER_AGENT_KEY = "sensorsdata.user.agent";
    private static final String TAG = "SA.SensorsDataUtils";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";
    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final Map<String, String> sCarrierMap = new HashMap<String, String>() { // from class: com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.1
        {
            put("46000", "中国移动");
            put("46002", "中国移动");
            put("46007", "中国移动");
            put("46008", "中国移动");
            put("46001", "中国联通");
            put("46006", "中国联通");
            put("46009", "中国联通");
            put("46003", "中国电信");
            put("46005", "中国电信");
            put("46011", "中国电信");
            put("46004", "中国卫通");
            put("46020", "中国铁通");
        }
    };
    private static final List<String> sManufacturer = new ArrayList<String>() { // from class: com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.2
        {
            add("HUAWEI");
            add("OPPO");
            add("vivo");
        }
    };
    private static Set<String> channelEvents = new HashSet();
    private static final List<String> mInvalidAndroidId = new ArrayList<String>() { // from class: com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.3
        {
            add("9774d56d682e549c");
            add("0123456789abcdef");
        }
    };

    public static int getNaturalHeight(int i, int i2, int i3) {
        return (i == 0 || i == 2) ? i3 : i2;
    }

    public static int getNaturalWidth(int i, int i2, int i3) {
        return (i == 0 || i == 2) ? i2 : i3;
    }

    public static SensorsDataSDKRemoteConfig toSDKRemoteConfig(String str) {
        SensorsDataSDKRemoteConfig sensorsDataSDKRemoteConfig = new SensorsDataSDKRemoteConfig();
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                sensorsDataSDKRemoteConfig.setV(jSONObject.optString("v"));
                if (!TextUtils.isEmpty(jSONObject.optString("configs"))) {
                    JSONObject jSONObject2 = new JSONObject(jSONObject.optString("configs"));
                    sensorsDataSDKRemoteConfig.setDisableDebugMode(jSONObject2.optBoolean("disableDebugMode", false));
                    sensorsDataSDKRemoteConfig.setDisableSDK(jSONObject2.optBoolean("disableSDK", false));
                    sensorsDataSDKRemoteConfig.setAutoTrackMode(jSONObject2.optInt("autoTrackMode", -1));
                } else {
                    sensorsDataSDKRemoteConfig.setDisableDebugMode(false);
                    sensorsDataSDKRemoteConfig.setDisableSDK(false);
                    sensorsDataSDKRemoteConfig.setAutoTrackMode(-1);
                }
                return sensorsDataSDKRemoteConfig;
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return sensorsDataSDKRemoteConfig;
    }

    public static String getManufacturer() {
        String str = Build.MANUFACTURER;
        String trim = str == null ? "UNKNOWN" : str.trim();
        try {
            if (!TextUtils.isEmpty(trim)) {
                for (String str2 : sManufacturer) {
                    if (str2.equalsIgnoreCase(trim)) {
                        return str2;
                    }
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return trim;
    }

    public static ArrayList<String> getAutoTrackFragments(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(context.getAssets().open("sa_autotrack_fragment.config")));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            } else if (!TextUtils.isEmpty(readLine) && !readLine.startsWith("#")) {
                                arrayList.add(readLine);
                            }
                        } catch (IOException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            if (e.toString().contains("FileNotFoundException")) {
                                SALog.m3677d(TAG, "SensorsDataAutoTrackFragment file not exists.");
                            } else {
                                SALog.printStackTrace(e);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return arrayList;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                    SALog.printStackTrace(e2);
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                } catch (IOException e3) {
                    SALog.printStackTrace(e3);
                }
            } catch (IOException e4) {
                e = e4;
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String getJsonFromAssets(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                        } catch (IOException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            SALog.printStackTrace(e);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return sb.toString();
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                    SALog.printStackTrace(e2);
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                } catch (IOException e3) {
                    SALog.printStackTrace(e3);
                }
            } catch (IOException e4) {
                e = e4;
            }
            return sb.toString();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0035 A[Catch: Exception -> 0x004f, TryCatch #0 {Exception -> 0x004f, blocks: (B:8:0x000a, B:10:0x0015, B:12:0x001f, B:14:0x0029, B:15:0x002f, B:17:0x0035, B:19:0x003c, B:21:0x0044, B:23:0x004a), top: B:7:0x000a, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004a A[Catch: Exception -> 0x004f, TRY_LEAVE, TryCatch #0 {Exception -> 0x004f, blocks: (B:8:0x000a, B:10:0x0015, B:12:0x001f, B:14:0x0029, B:15:0x002f, B:17:0x0035, B:19:0x003c, B:21:0x0044, B:23:0x004a), top: B:7:0x000a, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getCarrier(Context context) {
        String str;
        try {
            if (checkHasPermission(context, "android.permission.READ_PHONE_STATE")) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    if (telephonyManager != null) {
                        String simOperator = telephonyManager.getSimOperator();
                        if (Build.VERSION.SDK_INT >= 28) {
                            CharSequence simCarrierIdName = telephonyManager.getSimCarrierIdName();
                            if (!TextUtils.isEmpty(simCarrierIdName)) {
                                str = simCarrierIdName.toString();
                                if (TextUtils.isEmpty(str)) {
                                    str = telephonyManager.getSimState() == 5 ? telephonyManager.getSimOperatorName() : "未知";
                                }
                                if (!TextUtils.isEmpty(simOperator)) {
                                    return operatorToCarrier(context, simOperator, str);
                                }
                            }
                        }
                        str = null;
                        if (TextUtils.isEmpty(str)) {
                        }
                        if (!TextUtils.isEmpty(simOperator)) {
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001b A[Catch: Exception -> 0x004b, TryCatch #0 {Exception -> 0x004b, blocks: (B:6:0x0003, B:8:0x0009, B:11:0x0015, B:13:0x001b, B:14:0x0023, B:16:0x0029, B:18:0x002f, B:20:0x0042), top: B:5:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getActivityTitle(Activity activity) {
        String str;
        PackageManager packageManager;
        if (activity != null) {
            try {
                if (Build.VERSION.SDK_INT >= 11) {
                    str = getToolbarTitle(activity);
                    if (!TextUtils.isEmpty(str)) {
                        if (TextUtils.isEmpty(str)) {
                            str = activity.getTitle().toString();
                        }
                        if (!TextUtils.isEmpty(str) && (packageManager = activity.getPackageManager()) != null) {
                            ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 0);
                            return !TextUtils.isEmpty(activityInfo.loadLabel(packageManager)) ? activityInfo.loadLabel(packageManager).toString() : str;
                        }
                    }
                }
                str = null;
                if (TextUtils.isEmpty(str)) {
                }
                return !TextUtils.isEmpty(str) ? str : str;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static String getMainProcessName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getApplicationContext().getApplicationInfo().processName;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    private static String getCurrentProcessName(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        try {
            int myPid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo != null && runningAppProcessInfo.pid == myPid) {
                        return runningAppProcessInfo.processName;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public static boolean isMainProcess(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        String currentProcessName = getCurrentProcessName(context.getApplicationContext());
        return TextUtils.isEmpty(currentProcessName) || str.equals(currentProcessName);
    }

    private static String operatorToCarrier(Context context, String str, String str2) {
        try {
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if (sCarrierMap.containsKey(str)) {
            return sCarrierMap.get(str);
        }
        String jsonFromAssets = getJsonFromAssets("sa_mcc_mnc_mini.json", context);
        if (TextUtils.isEmpty(jsonFromAssets)) {
            sCarrierMap.put(str, str2);
            return str2;
        }
        String carrierFromJsonObject = getCarrierFromJsonObject(new JSONObject(jsonFromAssets), str);
        if (!TextUtils.isEmpty(carrierFromJsonObject)) {
            sCarrierMap.put(str, carrierFromJsonObject);
            return carrierFromJsonObject;
        }
        return str2;
    }

    private static String getCarrierFromJsonObject(JSONObject jSONObject, String str) {
        if (jSONObject == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return jSONObject.optString(str);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_EDITS_FILE, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @TargetApi(11)
    public static String getToolbarTitle(Activity activity) {
        Object invoke;
        CharSequence charSequence;
        try {
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if ("com.tencent.connect.common.AssistActivity".equals(activity.getClass().getCanonicalName())) {
            if (TextUtils.isEmpty(activity.getTitle())) {
                return null;
            }
            return activity.getTitle().toString();
        }
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            if (!TextUtils.isEmpty(actionBar.getTitle())) {
                return actionBar.getTitle().toString();
            }
        } else {
            try {
                Class<?> compatActivity = compatActivity();
                if (compatActivity != null && compatActivity.isInstance(activity) && (invoke = activity.getClass().getMethod("getSupportActionBar", new Class[0]).invoke(activity, new Object[0])) != null && (charSequence = (CharSequence) invoke.getClass().getMethod("getTitle", new Class[0]).invoke(invoke, new Object[0])) != null) {
                    return charSequence.toString();
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static Class<?> compatActivity() {
        Class<?> cls;
        try {
            cls = Class.forName("android.support.v7.app.AppCompatActivity");
        } catch (Exception unused) {
            cls = null;
        }
        if (cls == null) {
            try {
                return Class.forName("androidx.appcompat.app.AppCompatActivity");
            } catch (Exception unused2) {
                return cls;
            }
        }
        return cls;
    }

    public static void getScreenNameAndTitleFromActivity(JSONObject jSONObject, Activity activity) {
        PackageManager packageManager;
        if (activity == null || jSONObject == null) {
            return;
        }
        try {
            jSONObject.put(AopConstants.SCREEN_NAME, activity.getClass().getCanonicalName());
            String str = null;
            if (!TextUtils.isEmpty(activity.getTitle())) {
                str = activity.getTitle().toString();
            }
            if (Build.VERSION.SDK_INT >= 11) {
                String toolbarTitle = getToolbarTitle(activity);
                if (!TextUtils.isEmpty(toolbarTitle)) {
                    str = toolbarTitle;
                }
            }
            if (TextUtils.isEmpty(str) && (packageManager = activity.getPackageManager()) != null) {
                str = packageManager.getActivityInfo(activity.getComponentName(), 0).loadLabel(packageManager).toString();
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            jSONObject.put("title", str);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void cleanUserAgent(Context context) {
        try {
            SharedPreferences.Editor edit = getSharedPreferences(context).edit();
            edit.putString(SHARED_PREF_USER_AGENT_KEY, null);
            edit.apply();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void mergeJSONObject(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = jSONObject.get(next);
                if ((obj instanceof Date) && !"$time".equals(next)) {
                    jSONObject2.put(next, DateFormatUtils.formatDate((Date) obj, Locale.CHINA));
                } else {
                    jSONObject2.put(next, obj);
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void mergeSuperJSONObject(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Iterator<String> keys2 = jSONObject2.keys();
                while (true) {
                    if (!keys2.hasNext()) {
                        break;
                    }
                    String next2 = keys2.next();
                    if (!TextUtils.isEmpty(next) && next.toLowerCase(Locale.getDefault()).equals(next2.toLowerCase(Locale.getDefault()))) {
                        jSONObject2.remove(next2);
                        break;
                    }
                }
                Object obj = jSONObject.get(next);
                if ((obj instanceof Date) && !"$time".equals(next)) {
                    jSONObject2.put(next, DateFormatUtils.formatDate((Date) obj, Locale.CHINA));
                } else {
                    jSONObject2.put(next, obj);
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @Deprecated
    public static String getUserAgent(Context context) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            String string = sharedPreferences.getString(SHARED_PREF_USER_AGENT_KEY, null);
            if (TextUtils.isEmpty(string)) {
                if (Build.VERSION.SDK_INT >= 17) {
                    try {
                        if (Class.forName("android.webkit.WebSettings").getMethod("getDefaultUserAgent", Context.class) != null) {
                            string = WebSettings.getDefaultUserAgent(context);
                        }
                    } catch (Exception unused) {
                        SALog.m3674i(TAG, "WebSettings NoSuchMethod: getDefaultUserAgent");
                    }
                }
                if (TextUtils.isEmpty(string)) {
                    string = System.getProperty("http.agent");
                }
                if (!TextUtils.isEmpty(string)) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(SHARED_PREF_USER_AGENT_KEY, string);
                    edit.apply();
                }
            }
            return string;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public static String getDeviceID(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String string = sharedPreferences.getString(SHARED_PREF_DEVICE_ID_KEY, null);
        if (string == null) {
            String uuid = UUID.randomUUID().toString();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(SHARED_PREF_DEVICE_ID_KEY, uuid);
            edit.apply();
            return uuid;
        }
        return string;
    }

    public static boolean checkHasPermission(Context context, String str) {
        Class<?> cls;
        try {
            cls = Class.forName("android.support.v4.content.ContextCompat");
        } catch (Exception unused) {
            cls = null;
        }
        if (cls == null) {
            try {
                cls = Class.forName("androidx.core.content.ContextCompat");
            } catch (Exception unused2) {
            }
        }
        if (cls == null) {
            return true;
        }
        try {
            if (((Integer) cls.getMethod("checkSelfPermission", Context.class, String.class).invoke(null, context, str)).intValue() == 0) {
                return true;
            }
            SALog.m3674i(TAG, "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"" + str + "\" />");
            return false;
        } catch (Exception e) {
            SALog.m3674i(TAG, e.toString());
            return true;
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager;
        String deviceId;
        try {
            if (!checkHasPermission(context, "android.permission.READ_PHONE_STATE") || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
                return "";
            }
            if (Build.VERSION.SDK_INT > 28) {
                if (telephonyManager.hasCarrierPrivileges()) {
                    deviceId = telephonyManager.getImei();
                } else {
                    SALog.m3677d(TAG, "Can not get IMEI info.");
                    return "";
                }
            } else if (Build.VERSION.SDK_INT >= 26) {
                deviceId = telephonyManager.getImei();
            } else {
                deviceId = telephonyManager.getDeviceId();
            }
            return deviceId;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static String getIMEIOld(Context context) {
        return getDeviceID(context, -1);
    }

    public static String getSlot(Context context, int i) {
        return getDeviceID(context, i);
    }

    public static String getMEID(Context context) {
        return getDeviceID(context, -2);
    }

    private static String getDeviceID(Context context, int i) {
        TelephonyManager telephonyManager;
        String deviceId;
        try {
            if (!checkHasPermission(context, "android.permission.READ_PHONE_STATE") || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
                return "";
            }
            if (i == -1) {
                deviceId = telephonyManager.getDeviceId();
            } else if (i == -2 && Build.VERSION.SDK_INT >= 26) {
                deviceId = telephonyManager.getMeid();
            } else if (Build.VERSION.SDK_INT < 23) {
                return "";
            } else {
                deviceId = telephonyManager.getDeviceId(i);
            }
            return deviceId;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static Integer getZoneOffset() {
        try {
            return Integer.valueOf(Calendar.getInstance(Locale.getDefault()).get(15) / 1000);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public static String getApplicationMetaData(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getApplicationContext().getPackageName(), 128);
            String string = applicationInfo.metaData.getString(str);
            int i = string == null ? applicationInfo.metaData.getInt(str, -1) : -1;
            return i != -1 ? String.valueOf(i) : string;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getMacAddressByInterface() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if ("wlan0".equalsIgnoreCase(networkInterface.getName())) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02X:", Byte.valueOf(hardwareAddress[i])));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String getMacAddress(Context context) {
        WifiManager wifiManager;
        String macAddressByInterface;
        if (checkHasPermission(context, "android.permission.ACCESS_WIFI_STATE") && (wifiManager = (WifiManager) context.getApplicationContext().getSystemService(AopConstants.WIFI)) != null) {
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && marshmallowMacAddress.equals(connectionInfo.getMacAddress())) {
                try {
                    macAddressByInterface = getMacAddressByInterface();
                } catch (Exception unused) {
                }
                return macAddressByInterface != null ? macAddressByInterface : marshmallowMacAddress;
            }
            if (connectionInfo != null && connectionInfo.getMacAddress() != null) {
                return connectionInfo.getMacAddress();
            }
            return "";
        }
        return "";
    }

    public static boolean isValidAndroidId(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return !mInvalidAndroidId.contains(str.toLowerCase(Locale.getDefault()));
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0045 A[Catch: Exception -> 0x005f, TRY_LEAVE, TryCatch #0 {Exception -> 0x005f, blocks: (B:4:0x0009, B:7:0x0013, B:10:0x002c, B:12:0x0037, B:16:0x0045), top: B:2:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isRequestValid(Context context, int i, int i2) {
        try {
            if (i > i2) {
                SALog.m3677d(TAG, "最小时间间隔（minRequestHourInterval）大于最大时间间隔（maxRequestHourInterval），时间间隔设置无效。");
                return true;
            }
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            long j = sharedPreferences.getLong(SHARED_PREF_REQUEST_TIME, 0L);
            boolean z = false;
            int i3 = sharedPreferences.getInt(SHARED_PREF_REQUEST_TIME_RANDOM, 0);
            if (j != 0 && i3 != 0) {
                float elapsedRealtime = (float) (SystemClock.elapsedRealtime() - j);
                if (elapsedRealtime > 0.0f && elapsedRealtime / 1000.0f < i3 * 3600) {
                    if (z) {
                        edit.putLong(SHARED_PREF_REQUEST_TIME, SystemClock.elapsedRealtime());
                        edit.putInt(SHARED_PREF_REQUEST_TIME_RANDOM, new Random().nextInt((i2 - i) + 1) + i);
                        edit.apply();
                    }
                    return z;
                }
            }
            z = true;
            if (z) {
            }
            return z;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return true;
        }
    }

    public static boolean hasUtmProperties(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        return jSONObject.has("$utm_source") || jSONObject.has("$utm_medium") || jSONObject.has("$utm_term") || jSONObject.has("$utm_content") || jSONObject.has("$utm_campaign");
    }

    public static boolean isFirstChannelEvent(Context context, String str) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            if (channelEvents.isEmpty()) {
                String string = sharedPreferences.getString(SHARED_PREF_CHANNEL_EVENT, "");
                if (!TextUtils.isEmpty(string)) {
                    JSONArray jSONArray = new JSONArray(string);
                    if (jSONArray.length() > 0) {
                        for (int i = 0; i < jSONArray.length(); i++) {
                            channelEvents.add(jSONArray.getString(i));
                        }
                    }
                }
            }
            if (!channelEvents.isEmpty() && channelEvents.contains(str)) {
                return false;
            }
            channelEvents.add(str);
            sharedPreferences.edit().putString(SHARED_PREF_CHANNEL_EVENT, channelEvents.toString()).apply();
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static CharSequence getAppName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager == null ? "" : packageManager.getApplicationInfo(context.getPackageName(), 128).loadLabel(packageManager);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static boolean isDoubleClick(View view) {
        long currentTimeMillis;
        String str;
        if (view == null) {
            return false;
        }
        try {
            currentTimeMillis = System.currentTimeMillis();
            str = (String) view.getTag(C3089R.C3090id.sensors_analytics_tag_view_onclick_timestamp);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (!TextUtils.isEmpty(str) && currentTimeMillis - Long.parseLong(str) < 500) {
            return true;
        }
        view.setTag(C3089R.C3090id.sensors_analytics_tag_view_onclick_timestamp, String.valueOf(currentTimeMillis));
        return false;
    }

    public static String getScreenUrl(Object obj) {
        String str = null;
        if (obj == null) {
            return null;
        }
        try {
            if (obj instanceof ScreenAutoTracker) {
                str = ((ScreenAutoTracker) obj).getScreenUrl();
            } else {
                SensorsDataAutoTrackAppViewScreenUrl sensorsDataAutoTrackAppViewScreenUrl = (SensorsDataAutoTrackAppViewScreenUrl) obj.getClass().getAnnotation(SensorsDataAutoTrackAppViewScreenUrl.class);
                if (sensorsDataAutoTrackAppViewScreenUrl != null) {
                    str = sensorsDataAutoTrackAppViewScreenUrl.url();
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return str == null ? obj.getClass().getCanonicalName() : str;
    }
}
