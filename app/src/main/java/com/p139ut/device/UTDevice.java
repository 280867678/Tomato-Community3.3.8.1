package com.p139ut.device;

import android.content.Context;

/* renamed from: com.ut.device.UTDevice */
/* loaded from: classes4.dex */
public class UTDevice {
    @Deprecated
    public static String getAid(String str, String str2, Context context) {
        return "";
    }

    @Deprecated
    public static void getAidAsync(String str, String str2, Context context, AidCallback aidCallback) {
    }

    public static String getUtdid(Context context) {
        return com.p097ta.utdid2.device.UTDevice.getUtdid(context);
    }

    @Deprecated
    public static String getUtdidForUpdate(Context context) {
        return com.p097ta.utdid2.device.UTDevice.getUtdidForUpdate(context);
    }
}
