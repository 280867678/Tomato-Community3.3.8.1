package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.support.annotation.StringRes;

/* loaded from: classes2.dex */
public final class StringUtils {
    public static String getString(@StringRes int i) {
        try {
            return Utils.getApp().getResources().getString(i);
        } catch (Resources.NotFoundException unused) {
            return "";
        }
    }
}
