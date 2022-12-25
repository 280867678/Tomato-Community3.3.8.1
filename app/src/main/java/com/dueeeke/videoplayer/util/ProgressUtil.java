package com.dueeeke.videoplayer.util;

import android.text.TextUtils;
import java.util.LinkedHashMap;

/* loaded from: classes2.dex */
public class ProgressUtil {
    private static LinkedHashMap<Integer, Long> progressMap = new LinkedHashMap<>();

    public static void saveProgress(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        progressMap.put(Integer.valueOf(str.hashCode()), Long.valueOf(j));
    }

    public static long getSavedProgress(String str) {
        if (!TextUtils.isEmpty(str) && progressMap.containsKey(Integer.valueOf(str.hashCode()))) {
            return progressMap.get(Integer.valueOf(str.hashCode())).longValue();
        }
        return 0L;
    }

    public static void clearAllSavedProgress() {
        progressMap.clear();
    }

    public static void clearSavedProgressByUrl(String str) {
        progressMap.remove(Integer.valueOf(str.hashCode()));
    }
}
