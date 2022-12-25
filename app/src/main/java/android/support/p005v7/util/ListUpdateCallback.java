package android.support.p005v7.util;

import android.support.annotation.Nullable;

/* renamed from: android.support.v7.util.ListUpdateCallback */
/* loaded from: classes2.dex */
public interface ListUpdateCallback {
    void onChanged(int i, int i2, @Nullable Object obj);

    void onInserted(int i, int i2);

    void onMoved(int i, int i2);

    void onRemoved(int i, int i2);
}
