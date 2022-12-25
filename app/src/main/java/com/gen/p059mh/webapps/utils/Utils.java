package com.gen.p059mh.webapps.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.p002v4.graphics.drawable.DrawableCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.eclipsesource.p056v8.Platform;
import com.gen.p059mh.webapps.utils.Request;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.gen.mh.webapps.utils.Utils */
/* loaded from: classes2.dex */
public class Utils {
    public static final String DECODE_END = "#crypted";
    private static final String IO_DEFAULT_V1 = "io.default";
    private static final String IO_DEFAULT_V2 = "io.base.v2";
    private static final String IO_DEFAULT_V3 = "io.base.v3";
    private static final String IO_DEFAULT_V4 = "io.base.v4";
    private static final String IO_DEFAULT_V5 = "io.base.v5";
    private static final String IO_DEFAULT_V6 = "io.base.v6";
    private static final String IO_DEFAULT_V7 = "io.base.v7";
    private static final String IO_DEFAULT_V8 = "io.base.v8";
    public static final String SDK_VERSION = "3.1.1";
    static Map<String, String> cssColorMap = null;
    static final String cssJson = "{\n    \"aliceblue\": \"#f0f8ff\",\n    \"antiquewhite\": \"#faebd7\",\n    \"aqua\": \"#00ffff\",\n    \"aquamarine\": \"#7fffd4\",\n    \"azure\": \"#f0ffff\",\n    \"beige\": \"#f5f5dc\",\n    \"bisque\": \"#ffe4c4\",\n    \"black\": \"#000000\",\n    \"blanchedalmond\": \"#ffebcd\",\n    \"blue\": \"#0000ff\",\n    \"blueviolet\": \"#8a2be2\",\n    \"brown\": \"#a52a2a\",\n    \"burlywood\": \"#deb887\",\n    \"cadetblue\": \"#5f9ea0\",\n    \"chartreuse\": \"#7fff00\",\n    \"chocolate\": \"#d2691e\",\n    \"coral\": \"#ff7f50\",\n    \"cornflowerblue\": \"#6495ed\",\n    \"cornsilk\": \"#fff8dc\",\n    \"crimson\": \"#dc143c\",\n    \"cyan\": \"#00ffff\",\n    \"darkblue\": \"#00008b\",\n    \"darkcyan\": \"#008b8b\",\n    \"darkgoldenrod\": \"#b8860b\",\n    \"darkgray\": \"#a9a9a9\",\n    \"darkgreen\": \"#006400\",\n    \"darkgrey\": \"#a9a9a9\",\n    \"darkkhaki\": \"#bdb76b\",\n    \"darkmagenta\": \"#8b008b\",\n    \"darkolivegreen\": \"#556b2f\",\n    \"darkorange\": \"#ff8c00\",\n    \"darkorchid\": \"#9932cc\",\n    \"darkred\": \"#8b0000\",\n    \"darksalmon\": \"#e9967a\",\n    \"darkseagreen\": \"#8fbc8f\",\n    \"darkslateblue\": \"#483d8b\",\n    \"darkslategray\": \"#2f4f4f\",\n    \"darkslategrey\": \"#2f4f4f\",\n    \"darkturquoise\": \"#00ced1\",\n    \"darkviolet\": \"#9400d3\",\n    \"deeppink\": \"#ff1493\",\n    \"deepskyblue\": \"#00bfff\",\n    \"dimgray\": \"#696969\",\n    \"dimgrey\": \"#696969\",\n    \"dodgerblue\": \"#1e90ff\",\n    \"firebrick\": \"#b22222\",\n    \"floralwhite\": \"#fffaf0\",\n    \"forestgreen\": \"#228b22\",\n    \"fuchsia\": \"#ff00ff\",\n    \"gainsboro\": \"#dcdcdc\",\n    \"ghostwhite\": \"#f8f8ff\",\n    \"gold\": \"#ffd700\",\n    \"goldenrod\": \"#daa520\",\n    \"gray\": \"#808080\",\n    \"green\": \"#008000\",\n    \"greenyellow\": \"#adff2f\",\n    \"grey\": \"#808080\",\n    \"honeydew\": \"#f0fff0\",\n    \"hotpink\": \"#ff69b4\",\n    \"indianred\": \"#cd5c5c\",\n    \"indigo\": \"#4b0082\",\n    \"ivory\": \"#fffff0\",\n    \"khaki\": \"#f0e68c\",\n    \"lavender\": \"#e6e6fa\",\n    \"lavenderblush\": \"#fff0f5\",\n    \"lawngreen\": \"#7cfc00\",\n    \"lemonchiffon\": \"#fffacd\",\n    \"lightblue\": \"#add8e6\",\n    \"lightcoral\": \"#f08080\",\n    \"lightcyan\": \"#e0ffff\",\n    \"lightgoldenrodyellow\": \"#fafad2\",\n    \"lightgray\": \"#d3d3d3\",\n    \"lightgreen\": \"#90ee90\",\n    \"lightgrey\": \"#d3d3d3\",\n    \"lightpink\": \"#ffb6c1\",\n    \"lightsalmon\": \"#ffa07a\",\n    \"lightseagreen\": \"#20b2aa\",\n    \"lightskyblue\": \"#87cefa\",\n    \"lightslategray\": \"#778899\",\n    \"lightslategrey\": \"#778899\",\n    \"lightsteelblue\": \"#b0c4de\",\n    \"lightyellow\": \"#ffffe0\",\n    \"lime\": \"#00ff00\",\n    \"limegreen\": \"#32cd32\",\n    \"linen\": \"#faf0e6\",\n    \"magenta\": \"#ff00ff\",\n    \"maroon\": \"#800000\",\n    \"mediumaquamarine\": \"#66cdaa\",\n    \"mediumblue\": \"#0000cd\",\n    \"mediumorchid\": \"#ba55d3\",\n    \"mediumpurple\": \"#9370db\",\n    \"mediumseagreen\": \"#3cb371\",\n    \"mediumslateblue\": \"#7b68ee\",\n    \"mediumspringgreen\": \"#00fa9a\",\n    \"mediumturquoise\": \"#48d1cc\",\n    \"mediumvioletred\": \"#c71585\",\n    \"midnightblue\": \"#191970\",\n    \"mintcream\": \"#f5fffa\",\n    \"mistyrose\": \"#ffe4e1\",\n    \"moccasin\": \"#ffe4b5\",\n    \"navajowhite\": \"#ffdead\",\n    \"navy\": \"#000080\",\n    \"oldlace\": \"#fdf5e6\",\n    \"olive\": \"#808000\",\n    \"olivedrab\": \"#6b8e23\",\n    \"orange\": \"#ffa500\",\n    \"orangered\": \"#ff4500\",\n    \"orchid\": \"#da70d6\",\n    \"palegoldenrod\": \"#eee8aa\",\n    \"palegreen\": \"#98fb98\",\n    \"paleturquoise\": \"#afeeee\",\n    \"palevioletred\": \"#db7093\",\n    \"papayawhip\": \"#ffefd5\",\n    \"peachpuff\": \"#ffdab9\",\n    \"peru\": \"#cd853f\",\n    \"pink\": \"#ffc0cb\",\n    \"plum\": \"#dda0dd\",\n    \"powderblue\": \"#b0e0e6\",\n    \"purple\": \"#800080\",\n    \"rebeccapurple\": \"#663399\",\n    \"red\": \"#ff0000\",\n    \"rosybrown\": \"#bc8f8f\",\n    \"royalblue\": \"#4169e1\",\n    \"saddlebrown\": \"#8b4513\",\n    \"salmon\": \"#fa8072\",\n    \"sandybrown\": \"#f4a460\",\n    \"seagreen\": \"#2e8b57\",\n    \"seashell\": \"#fff5ee\",\n    \"sienna\": \"#a0522d\",\n    \"silver\": \"#c0c0c0\",\n    \"skyblue\": \"#87ceeb\",\n    \"slateblue\": \"#6a5acd\",\n    \"slategray\": \"#708090\",\n    \"slategrey\": \"#708090\",\n    \"snow\": \"#fffafa\",\n    \"springgreen\": \"#00ff7f\",\n    \"steelblue\": \"#4682b4\",\n    \"tan\": \"#d2b48c\",\n    \"teal\": \"#008080\",\n    \"thistle\": \"#d8bfd8\",\n    \"tomato\": \"#ff6347\",\n    \"turquoise\": \"#40e0d0\",\n    \"violet\": \"#ee82ee\",\n    \"wheat\": \"#f5deb3\",\n    \"white\": \"#ffffff\",\n    \"whitesmoke\": \"#f5f5f5\",\n    \"yellow\": \"#ffff00\",\n    \"yellowgreen\": \"#9acd32\"\n}";
    private static final int versionCode = 311;

    /* renamed from: com.gen.mh.webapps.utils.Utils$ENCODE_TYPE */
    /* loaded from: classes2.dex */
    public enum ENCODE_TYPE {
        NORMAL,
        DEFAULT,
        WORK
    }

    public static native byte[] decodeByCrypto(String str, long j);

    public static native long decodeImage(byte[] bArr);

    public static native void deleteCrypto(long j);

    public static String getIoDefaultName() {
        return IO_DEFAULT_V8;
    }

    public static native long initCrypto(String str);

    public static native String processWithFilePath(String str, String str2, long j, String str3, String str4);

    public static boolean hasSuffix(String str, String str2) {
        return str.length() - str.lastIndexOf(str2) == str2.length();
    }

    public static String getExtension(String str) {
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf >= 0) {
            str = str.substring(lastIndexOf + 1);
        }
        int lastIndexOf2 = str.lastIndexOf(46);
        return lastIndexOf2 > 0 ? str.substring(lastIndexOf2 + 1) : "";
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static HashMap launchSettings(Activity activity, String str) {
        if (activity == null) {
            return new HashMap();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        float f = activity.getResources().getDisplayMetrics().density;
        hashMap2.put("brand", Build.BRAND);
        hashMap2.put("model", Build.MODEL);
        hashMap2.put("pixelRatio", Float.valueOf(f));
        int navHeight = getNavHeight(activity);
        int statusBarHeight = getStatusBarHeight(activity);
        hashMap2.put("screenWidth", Float.valueOf(displayMetrics.widthPixels / f));
        hashMap2.put("screenHeight", Float.valueOf((displayMetrics.heightPixels + statusBarHeight) / f));
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        hashMap2.put("windowWidth", Float.valueOf(rect.width() / f));
        hashMap2.put("windowHeight", Float.valueOf((rect.height() + statusBarHeight) / f));
        hashMap2.put("statusBarHeight", Float.valueOf(statusBarHeight / f));
        hashMap2.put("language", "中文".equals(Locale.getDefault().getLanguage()) ? "CN" : Locale.getDefault().getLanguage());
        hashMap2.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, Build.VERSION.RELEASE);
        String str2 = Build.VERSION.SDK_INT >= 23 ? Build.VERSION.BASE_OS : Platform.ANDROID;
        if (str2 == null || "".equals(str2)) {
            str2 = Platform.ANDROID;
        }
        hashMap2.put("system", str2);
        hashMap2.put("safeArea", 0);
        hashMap2.put("safeArea2", createSafeArea(activity, f, navHeight));
        hashMap2.put("platform", Platform.ANDROID);
        hashMap2.put("fontSizeSetting", 14);
        hashMap2.put("SDKVersion", "3.1.1");
        hashMap2.put("benchmarkLevel", 1);
        String devicesId = getDevicesId(activity);
        hashMap2.put("udid", devicesId);
        hashMap2.put("udid2", devicesId);
        hashMap2.put("idfa", devicesId);
        hashMap2.put("packageName", activity.getPackageName());
        hashMap.put("systemInfo", hashMap2);
        HashMap hashMap3 = new HashMap();
        hashMap3.put("path", str);
        hashMap3.put(AopConstants.SCENE, 1001);
        hashMap3.put("query", new HashMap());
        hashMap3.put("shareTicket", "");
        hashMap3.put("referrerInfo", new HashMap());
        hashMap.put("launchOptions", hashMap3);
        return hashMap;
    }

    public static DisplayMetrics displaymetrics(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private static Map createSafeArea(Activity activity, float f, int i) {
        DisplayMetrics displaymetrics = displaymetrics(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("bottom", Float.valueOf(i / f));
        hashMap.put("height", Float.valueOf(displaymetrics.heightPixels / f));
        hashMap.put("left", 0);
        hashMap.put("right", 0);
        hashMap.put("top", Float.valueOf(getStatusBarHeight(activity) / f));
        hashMap.put("width", Float.valueOf(displaymetrics.widthPixels / f));
        Logger.m4112i("createSafeArea", hashMap.toString());
        return hashMap;
    }

    private static boolean checkDeviceHasNavigationBar(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", Platform.ANDROID);
        boolean z = identifier > 0 ? resources.getBoolean(identifier) : false;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str = (String) cls.getMethod("get", String.class).invoke(cls, "qemu.hw.mainkeys");
            if ("1".equals(str)) {
                return false;
            }
            if (!"0".equals(str)) {
                return z;
            }
            return true;
        } catch (Exception unused) {
            return z;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
        if (r0.equalsIgnoreCase("OPPO") != false) goto L8;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isNavigationBarShowing(Context context) {
        if (checkDeviceHasNavigationBar(context) && Build.VERSION.SDK_INT >= 17) {
            String str = Build.BRAND;
            String str2 = "navigation_gesture_on";
            if (!str.equalsIgnoreCase("HUAWEI")) {
                if (str.equalsIgnoreCase("XIAOMI")) {
                    str2 = "force_fsg_nav_bar";
                } else if (!str.equalsIgnoreCase("VIVO")) {
                }
                if (Settings.Global.getInt(context.getContentResolver(), str2, 0) != 0) {
                    return true;
                }
            }
            str2 = "navigationbar_is_min";
            if (Settings.Global.getInt(context.getContentResolver(), str2, 0) != 0) {
            }
        }
        return false;
    }

    public static int getNavHeight(Activity activity) {
        if (ImmersionBar.hasNavigationBar(activity)) {
            return ImmersionBar.getNavigationBarHeight(activity);
        }
        return 0;
    }

    public static int getNavHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", Platform.ANDROID);
        if (identifier <= 0 || !isNavigationBarShowing(context)) {
            return 0;
        }
        return resources.getDimensionPixelSize(identifier);
    }

    public static float d2p(Context context, int i) {
        return i * context.getResources().getDisplayMetrics().density;
    }

    /* renamed from: t */
    public static <T> T m4107t(T t, Class<T> cls) {
        if (t.getClass().isInstance(cls)) {
            return t;
        }
        return null;
    }

    public static int colorFromCSS(String str) {
        if (cssColorMap == null) {
            cssColorMap = (Map) new Gson().fromJson(cssJson, (Class<Object>) Map.class);
        }
        if (cssColorMap.containsKey(str)) {
            str = cssColorMap.get(str);
        }
        if (str.charAt(0) == '#') {
            str = str.substring(1, str.length());
        }
        if (str.length() == 6) {
            return Color.rgb(Integer.parseInt(str.substring(0, 2), 16), Integer.parseInt(str.substring(2, 4), 16), Integer.parseInt(str.substring(4, 6), 16));
        }
        if (str.length() == 8) {
            return Color.argb(Integer.parseInt(str.substring(0, 2), 16), Integer.parseInt(str.substring(2, 4), 16), Integer.parseInt(str.substring(4, 6), 16), Integer.parseInt(str.substring(6, 8), 16));
        }
        if (str.length() == 4) {
            int parseInt = Integer.parseInt(str.substring(0, 1), 16);
            int parseInt2 = Integer.parseInt(str.substring(1, 2), 16);
            int parseInt3 = Integer.parseInt(str.substring(2, 3), 16);
            int parseInt4 = Integer.parseInt(str.substring(3, 4), 16);
            return Color.argb((parseInt * 16) + parseInt, (parseInt2 * 16) + parseInt2, (parseInt3 * 16) + parseInt3, (parseInt4 * 16) + parseInt4);
        } else if (str.length() != 3) {
            return -1;
        } else {
            int parseInt5 = Integer.parseInt(str.substring(0, 1), 16);
            int parseInt6 = Integer.parseInt(str.substring(1, 2), 16);
            int parseInt7 = Integer.parseInt(str.substring(2, 3), 16);
            return Color.rgb((parseInt5 * 16) + parseInt5, (parseInt6 * 16) + parseInt6, (parseInt7 * 16) + parseInt7);
        }
    }

    public static byte[] dataFrom(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            String str = (String) map.get(AopConstants.APP_PROPERTIES_KEY);
            if (((String) map.get("type")).equals("buffer")) {
                return Base64.decode(str, 0);
            }
            return str.getBytes();
        }
        return obj.toString().getBytes();
    }

    public static void pipe(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    return;
                }
                outputStream.write(bArr, 0, read);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static String getDevicesId(Context context) {
        String readDeviceID = UdidUtils.readDeviceID(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP_devices", 0);
        String string = sharedPreferences.getString("sp_devices_id", readDeviceID);
        if (string != null && readDeviceID != null && !string.equals(readDeviceID) && readDeviceID != null && string != 0) {
            UdidUtils.saveDeviceID(string, context);
            readDeviceID = string;
        }
        if (readDeviceID != null) {
            readDeviceID = UdidUtils.getDeviceId(context);
        }
        sharedPreferences.edit().putString("sp_devices_id", readDeviceID).commit();
        return readDeviceID;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable wrap = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrap, colorStateList);
        return wrap;
    }

    static {
        System.loadLibrary("wapp");
    }

    public static String getRealPath(String str, String str2) {
        String str3;
        boolean startsWith = str.startsWith("http");
        String replace = str.replace("http://", "");
        if (str2.startsWith("/")) {
            str2 = str2.substring(1);
        }
        String[] split = str2.split(File.separator);
        String substring = replace.substring(0, replace.lastIndexOf(File.separator));
        if (split.length > 0 && !split[0].equals("")) {
            ArrayList arrayList = new ArrayList(Arrays.asList(substring.split("/")));
            for (int i = 0; i < split.length; i++) {
                if (!split[i].equals(".")) {
                    if (split[i].equals("..")) {
                        arrayList.remove(arrayList.size() - 1);
                    } else {
                        arrayList.add(split[i]);
                    }
                }
            }
            str3 = join(arrayList, File.separator);
        } else {
            str3 = substring + str2;
        }
        if (!startsWith || str3.startsWith("http")) {
            return str3;
        }
        return "http://" + str3;
    }

    public static String join(List<String> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static boolean deleteDirWithFile(File file) {
        File[] listFiles;
        if (file == null || !file.exists() || !file.isDirectory()) {
            return false;
        }
        for (File file2 : file.listFiles()) {
            if (file2.isFile()) {
                file2.delete();
            } else if (file2.isDirectory()) {
                deleteDirWithFile(file2);
            }
        }
        return file.delete();
    }

    public static String join(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i == list.size()) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static boolean isAppOnForeground(Context context) {
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(packageName) && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public static String inject(String str, String str2, String str3) {
        int indexOf;
        String str4;
        int indexOf2;
        if (str.indexOf("</head>") > 0) {
            str4 = str.substring(0, indexOf) + str2 + str.substring(indexOf);
        } else {
            str4 = "<head>" + str2 + "</head>" + str;
        }
        if (str4.indexOf("</head>") > 0) {
            return str4.substring(0, indexOf2) + str3 + str4.substring(indexOf2);
        }
        return str4;
    }

    public static byte[] loadNoEncodeData(String str) {
        if (!new File(str).exists()) {
            return new byte[0];
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pipe(fileInputStream, byteArrayOutputStream);
            fileInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new byte[0];
        } catch (IOException e2) {
            e2.printStackTrace();
            return new byte[0];
        }
    }

    public static byte[] loadData(String str, ENCODE_TYPE encode_type, WACrypto wACrypto) {
        if (encode_type == ENCODE_TYPE.NORMAL) {
            return loadNoEncodeData(str);
        }
        if (encode_type == ENCODE_TYPE.DEFAULT) {
            return decodeByCrypto(str, wACrypto.getDefaultCrypto());
        }
        return encode_type == ENCODE_TYPE.WORK ? decodeByCrypto(str, wACrypto.getWorkCrypto()) : new byte[0];
    }

    public static byte[] downloadImage(Context context, String str, boolean z) {
        return downloadImage(context, str, z, false);
    }

    public static byte[] downloadImage(Context context, String str, boolean z, boolean z2) {
        File checkCache = z2 ? checkCache(context, str) : null;
        if (checkCache != null && checkCache.exists()) {
            byte[] byteArray = toByteArray(checkCache);
            if (z) {
                decodeImage(byteArray);
            }
            return byteArray;
        }
        return doDownloadImage(checkCache, str, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.FileInputStream, java.nio.channels.FileChannel] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.nio.channels.FileChannel] */
    public static byte[] toByteArray(File file) {
        FileInputStream fileInputStream;
        if (!file.exists()) {
            throw new RuntimeException(file.getName());
        }
        FileChannel fileChannel = 0;
        fileChannel = 0;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    fileChannel = fileInputStream.getChannel();
                    ByteBuffer allocate = ByteBuffer.allocate((int) fileChannel.size());
                    while (fileChannel.read(allocate) > 0) {
                    }
                    byte[] array = allocate.array();
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return array;
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    try {
                        fileChannel.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    return new byte[0];
                }
            } catch (Throwable th) {
                th = th;
                try {
                    fileChannel.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
                try {
                    fileChannel.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
                throw th;
            }
        } catch (IOException e8) {
            e = e8;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileChannel.close();
            fileChannel.close();
            throw th;
        }
    }

    public static byte[] doDownloadImage(final File file, final String str, final boolean z) {
        URL url;
        final Hashtable hashtable = new Hashtable();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Request request = new Request();
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        request.setUrl(url);
        request.setMethod("GET");
        request.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapps.utils.Utils.1
            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onProgress(long j, long j2) {
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public boolean onReceiveResponse(Request.Response response) {
                return true;
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onFail(int i, String str2) {
                hashtable.put(AopConstants.APP_PROPERTIES_KEY, new byte[0]);
                countDownLatch.countDown();
                Logger.m4115e("load " + str + " fail" + str2);
            }

            /* JADX WARN: Removed duplicated region for block: B:35:0x003f  */
            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onComplete(int i, byte[] bArr) {
                FileOutputStream fileOutputStream;
                File file2 = file;
                if (file2 != null) {
                    FileOutputStream fileOutputStream2 = null;
                    try {
                        try {
                            try {
                                fileOutputStream = new FileOutputStream(file2);
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        } catch (Exception e3) {
                            e = e3;
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        fileOutputStream.getChannel().write(ByteBuffer.wrap(bArr));
                        fileOutputStream.close();
                    } catch (Exception e4) {
                        e = e4;
                        fileOutputStream2 = fileOutputStream;
                        e.printStackTrace();
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        if (z) {
                        }
                        hashtable.put(AopConstants.APP_PROPERTIES_KEY, bArr);
                        countDownLatch.countDown();
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                if (z) {
                    Utils.decodeImage(bArr);
                }
                hashtable.put(AopConstants.APP_PROPERTIES_KEY, bArr);
                countDownLatch.countDown();
            }
        });
        request.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return (byte[]) hashtable.get(AopConstants.APP_PROPERTIES_KEY);
    }

    public static File checkCache(Context context, String str) {
        try {
            return new File(context.getCacheDir(), MD5Utils.to32Str(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
