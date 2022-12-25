package org.dom4j.util;

/* loaded from: classes4.dex */
public final class StringUtils {
    private StringUtils() {
    }

    public static boolean startsWithWhitespace(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            return false;
        }
        return Character.isWhitespace(charSequence.charAt(0));
    }

    public static boolean endsWithWhitespace(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            return false;
        }
        return Character.isWhitespace(charSequence.charAt(charSequence.length() - 1));
    }
}
