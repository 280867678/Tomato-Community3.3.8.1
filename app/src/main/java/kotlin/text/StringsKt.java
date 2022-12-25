package kotlin.text;

import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class StringsKt extends _Strings {
    public static /* bridge */ /* synthetic */ <T> void appendElement(Appendable appendable, T t, Function1<? super T, ? extends CharSequence> function1) {
        StringBuilder.appendElement(appendable, t, function1);
    }

    public static /* bridge */ /* synthetic */ int indexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        return StringsKt__StringsKt.indexOf$default(charSequence, str, i, z, i2, obj);
    }

    public static /* bridge */ /* synthetic */ int lastIndexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        return StringsKt__StringsKt.lastIndexOf$default(charSequence, str, i, z, i2, obj);
    }
}
