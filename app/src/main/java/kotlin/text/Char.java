package kotlin.text;

/* renamed from: kotlin.text.CharsKt__CharKt */
/* loaded from: classes4.dex */
class Char extends CharJVM {
    public static final boolean equals(char c, char c2, boolean z) {
        if (c == c2) {
            return true;
        }
        if (!z) {
            return false;
        }
        return Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
    }
}
