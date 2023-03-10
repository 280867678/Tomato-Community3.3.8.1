package com.google.android.exoplayer2.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class Assertions {
    public static void checkArgument(boolean z) {
        if (z) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public static void checkArgument(boolean z, Object obj) {
        if (z) {
            return;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    public static int checkIndex(int i, int i2, int i3) {
        if (i < i2 || i >= i3) {
            throw new IndexOutOfBoundsException();
        }
        return i;
    }

    public static void checkState(boolean z) {
        if (z) {
            return;
        }
        throw new IllegalStateException();
    }

    public static void checkState(boolean z, Object obj) {
        if (z) {
            return;
        }
        throw new IllegalStateException(String.valueOf(obj));
    }

    public static <T> T checkNotNull(@Nullable T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static String checkNotEmpty(@Nullable String str) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        throw new IllegalArgumentException();
    }
}
