package com.tomatolive.library.utils.emoji;

/* loaded from: classes4.dex */
public enum Fitzpatrick {
    TYPE_1_2("\u1f3fb"),
    TYPE_3("\u1f3fc"),
    TYPE_4("\u1f3fd"),
    TYPE_5("\u1f3fe"),
    TYPE_6("\u1f3ff");
    
    public final String unicode;

    Fitzpatrick(String str) {
        this.unicode = str;
    }

    public static Fitzpatrick fitzpatrickFromUnicode(String str) {
        Fitzpatrick[] values;
        for (Fitzpatrick fitzpatrick : values()) {
            if (fitzpatrick.unicode.equals(str)) {
                return fitzpatrick;
            }
        }
        return null;
    }

    public static Fitzpatrick fitzpatrickFromType(String str) {
        try {
            return valueOf(str.toUpperCase());
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
