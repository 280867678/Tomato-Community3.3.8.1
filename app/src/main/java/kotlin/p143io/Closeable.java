package kotlin.p143io;

import kotlin.ExceptionsKt__ExceptionsKt;

/* renamed from: kotlin.io.CloseableKt */
/* loaded from: classes4.dex */
public final class Closeable {
    public static final void closeFinally(java.io.Closeable closeable, Throwable th) {
        if (closeable == null) {
            return;
        }
        if (th == null) {
            closeable.close();
            return;
        }
        try {
            closeable.close();
        } catch (Throwable th2) {
            ExceptionsKt__ExceptionsKt.addSuppressed(th, th2);
        }
    }
}
