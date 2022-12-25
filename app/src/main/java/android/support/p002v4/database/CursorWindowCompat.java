package android.support.p002v4.database;

import android.database.CursorWindow;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* renamed from: android.support.v4.database.CursorWindowCompat */
/* loaded from: classes2.dex */
public final class CursorWindowCompat {
    private CursorWindowCompat() {
    }

    @NonNull
    public static CursorWindow create(@Nullable String str, long j) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            return new CursorWindow(str, j);
        }
        if (i >= 15) {
            return new CursorWindow(str);
        }
        return new CursorWindow(false);
    }
}
