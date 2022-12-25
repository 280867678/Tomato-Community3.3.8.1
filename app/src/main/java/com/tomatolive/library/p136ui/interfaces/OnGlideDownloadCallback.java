package com.tomatolive.library.p136ui.interfaces;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

/* renamed from: com.tomatolive.library.ui.interfaces.OnGlideDownloadCallback */
/* loaded from: classes3.dex */
public interface OnGlideDownloadCallback<T> {
    void onLoadFailed(@Nullable Drawable drawable);

    void onLoadStarted(@Nullable Drawable drawable);

    void onLoadSuccess(T t);
}
