package com.zzhoujay.richtext.ext;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

/* loaded from: classes4.dex */
public class ContextKit {
    public static Activity getActivity(Context context) {
        boolean z;
        if (context == null) {
            return null;
        }
        while (true) {
            z = context instanceof Activity;
            if (z || !(context instanceof ContextWrapper)) {
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (!z) {
            return null;
        }
        return (Activity) context;
    }

    public static boolean activityIsAlive(Context context) {
        Activity activity = getActivity(context);
        if (activity != null && !activity.isFinishing()) {
            return Build.VERSION.SDK_INT < 17 || !activity.isDestroyed();
        }
        return false;
    }
}
