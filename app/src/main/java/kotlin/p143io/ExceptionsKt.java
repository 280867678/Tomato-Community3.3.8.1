package kotlin.p143io;

import java.io.File;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Exceptions.kt */
/* renamed from: kotlin.io.ExceptionsKt */
/* loaded from: classes4.dex */
public final class ExceptionsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String constructMessage(File file, File file2, String str) {
        StringBuilder sb = new StringBuilder(file.toString());
        if (file2 != null) {
            sb.append(" -> " + file2);
        }
        if (str != null) {
            sb.append(": " + str);
        }
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "sb.toString()");
        return sb2;
    }
}
