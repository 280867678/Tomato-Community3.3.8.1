package com.p076mh.webappStart.util.sensor;

import com.gen.p059mh.webapps.utils.Logger;

/* renamed from: com.mh.webappStart.util.sensor.SensorUtil */
/* loaded from: classes3.dex */
public class SensorUtil {
    public static int getSensorDelayValue(String str) {
        Logger.m4112i("SensorUtil", "getSensorDelayValue: " + str);
        if (str == null) {
            return 3;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1039745817) {
            if (hashCode != 3732) {
                if (hashCode == 3165170 && str.equals("game")) {
                    c = 1;
                }
            } else if (str.equals("ui")) {
                c = 0;
            }
        } else if (str.equals("normal")) {
            c = 2;
        }
        if (c == 0) {
            return 2;
        }
        if (c == 1) {
            return 1;
        }
        if (c != 2) {
        }
        return 3;
    }

    public static String getAccuracyEnumString(int i) {
        if (i != -1) {
            if (i == 0) {
                return "unreliable";
            }
            if (i == 1) {
                return "low";
            }
            if (i == 2) {
                return "medium";
            }
            if (i == 3) {
                return "high";
            }
            return "unknow ${" + i + "}";
        }
        return "no-contact";
    }
}
