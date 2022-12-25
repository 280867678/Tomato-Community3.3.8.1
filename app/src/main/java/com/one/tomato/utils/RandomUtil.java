package com.one.tomato.utils;

import java.util.Random;

/* loaded from: classes3.dex */
public class RandomUtil {
    private static Random random;

    public static String getRandom(int i) {
        if (random == null) {
            random = new Random();
        }
        String str = "";
        for (int i2 = 0; i2 < i; i2++) {
            str = str + String.valueOf(random.nextInt(10));
        }
        return str;
    }
}
