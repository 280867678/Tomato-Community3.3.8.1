package com.p076mh.webappStart.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

/* renamed from: com.mh.webappStart.util.TransferUtil */
/* loaded from: classes3.dex */
public class TransferUtil {
    public static String getRealPathFromURI(Activity activity, Uri uri) {
        Cursor managedQuery = activity.managedQuery(uri, new String[]{"_data"}, null, null, null);
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public static int getIntFromDouble(Object obj) {
        return Integer.parseInt(String.valueOf(Math.round(((Double) obj).doubleValue())));
    }

    public static float getFloatFromDouble(Object obj) {
        return Float.parseFloat(String.valueOf(obj));
    }
}
