package com.amazonaws.util;

/* loaded from: classes2.dex */
public class ValidationUtils {
    public static void assertParameterNotNull(Object obj, String str) {
        if (obj != null) {
            return;
        }
        throw new IllegalArgumentException(str);
    }
}
