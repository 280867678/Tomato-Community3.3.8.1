package com.taobao.gcanvas;

import android.graphics.Bitmap;
import com.taobao.gcanvas.util.GLog;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class GCanvasJNI {
    public static boolean GCanvaslibEnable;
    static Map<String, Integer> contextTypeMap = new HashMap();
    static Map<String, Double> devicePixelRatioMap = new HashMap();
    static Map<String, Boolean> qualityMap = new HashMap();

    public static native void addFallbackFontFamily(String[] strArr);

    public static native void addFontFamily(String[] strArr, String[] strArr2);

    public static native void bindTexture(String str, Bitmap bitmap, int i, int i2, int i3, int i4, int i5, int i6);

    public static native String getImageData(String str, int i, int i2, int i3, int i4);

    public static native int getNativeFps(String str);

    public static native boolean isFboSupport(String str);

    public static native boolean isNeonSupport();

    public static native void putImageData(String str, String str2, int i, float f, float f2, float f3, float f4, float f5, float f6);

    public static native void registerCallback(String str, int i);

    public static native void release();

    public static native void render(String str, String str2);

    public static native boolean sendEvent(String str);

    public static native void setBackgroundColor(String str, int i, int i2, int i3);

    public static native void setClearColor(String str, String str2);

    public static native void setConfig(String str, int i);

    public static native void setContextType(String str, int i);

    public static native void setDevicePixelRatio(String str, double d);

    public static native void setFallbackFont(String str, String str2);

    public static native void setHiQuality(String str, boolean z);

    public static native void setLogLevel(String str);

    public static native void setOrtho(String str, int i, int i2);

    public static native void setPreCompilePath(String str);

    public static native void setTyOffsetFlag(String str, boolean z);

    public static native void texSubImage2D(String str, Bitmap bitmap, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static void load() {
        if (!GCanvaslibEnable) {
            try {
                System.loadLibrary("freetype2");
                System.loadLibrary("gcanvas");
                GCanvaslibEnable = true;
            } catch (Exception unused) {
                GLog.m3563e("CANVAS", "fail to load gcanvas.");
            } catch (UnsatisfiedLinkError unused2) {
                GLog.m3563e("CANVAS", "gcanvas is not found.");
            }
        }
    }

    static {
        load();
    }

    public static void setWrapperDevicePixelRatio(String str, double d) {
        setDevicePixelRatio(str, d);
        devicePixelRatioMap.put(str, Double.valueOf(d));
    }

    public static void setWrapperContextType(String str, int i) {
        setContextType(str, i);
        contextTypeMap.put(str, Integer.valueOf(i));
    }

    public static void setWrapperHiQuality(String str, boolean z) {
        setHiQuality(str, z);
        qualityMap.put(str, Boolean.valueOf(z));
    }

    public static void refreshArguments(String str) {
        Integer num = contextTypeMap.get(str);
        if (num != null) {
            setContextType(str, num.intValue());
        }
        Double d = devicePixelRatioMap.get(str);
        if (d != null) {
            setDevicePixelRatio(str, d.doubleValue());
        }
        Boolean bool = qualityMap.get(str);
        if (bool != null) {
            setHiQuality(str, bool.booleanValue());
        }
    }

    public static void setFontFamilies() {
        GFontConfigParser gFontConfigParser = new GFontConfigParser();
        setFallbackFont(gFontConfigParser.getFallbackFont(), gFontConfigParser.getSystemFontLocation());
        HashMap<List<String>, List<String>> fontFamilies = gFontConfigParser.getFontFamilies();
        if (fontFamilies != null) {
            GLog.m3564d("CANVAS", "setFontFamilies() fontFamilies.size() " + fontFamilies.size());
        } else {
            GLog.m3564d("CANVAS", "setFontFamilies() error! fontFamilies is empty");
        }
        if (fontFamilies != null) {
            for (List<String> list : fontFamilies.keySet()) {
                int size = list.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = list.get(i);
                }
                List<String> list2 = fontFamilies.get(list);
                int size2 = list2.size();
                String[] strArr2 = new String[size2];
                for (int i2 = 0; i2 < size2; i2++) {
                    strArr2[i2] = list2.get(i2);
                }
                addFontFamily(strArr, strArr2);
            }
        }
        List<String> fallbackFontsList = gFontConfigParser.getFallbackFontsList();
        if (fallbackFontsList == null) {
            return;
        }
        int size3 = fallbackFontsList.size();
        String[] strArr3 = new String[size3];
        for (int i3 = 0; i3 < size3; i3++) {
            strArr3[i3] = fallbackFontsList.get(i3);
        }
        addFallbackFontFamily(strArr3);
    }
}
