package com.luck.picture.lib.compress;

import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
class Checker {
    private static List<String> format = new ArrayList();

    static {
        format.add("jpg");
        format.add("jpeg");
        format.add("png");
        format.add("webp");
        format.add("gif");
        format.add("bmp");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isImage(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return format.contains(str.substring(str.lastIndexOf(".") + 1, str.length()).toLowerCase());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isJPG(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String lowerCase = str.substring(str.lastIndexOf("."), str.length()).toLowerCase();
        return lowerCase.contains("jpg") || lowerCase.contains("jpeg");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String checkSuffix(String str) {
        return TextUtils.isEmpty(str) ? ".jpg" : str.substring(str.lastIndexOf("."), str.length());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isNeedCompress(int i, String str) {
        if (i > 0) {
            File file = new File(str);
            return file.exists() && file.length() > ((long) (i << 10));
        }
        return true;
    }
}
