package com.bumptech.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import java.io.File;
import java.net.URL;

/* loaded from: classes2.dex */
interface ModelTypes<T> {
    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6723load(@Nullable Bitmap bitmap);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6724load(@Nullable Drawable drawable);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6725load(@Nullable Uri uri);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6726load(@Nullable File file);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6727load(@RawRes @DrawableRes @Nullable Integer num);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6728load(@Nullable Object obj);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6729load(@Nullable String str);

    @CheckResult
    @Deprecated
    /* renamed from: load */
    T mo6730load(@Nullable URL url);

    @CheckResult
    @NonNull
    /* renamed from: load */
    T mo6731load(@Nullable byte[] bArr);
}
