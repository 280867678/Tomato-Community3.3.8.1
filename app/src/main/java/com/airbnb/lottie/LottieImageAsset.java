package com.airbnb.lottie;

import android.support.annotation.RestrictTo;

/* loaded from: classes2.dex */
public class LottieImageAsset {
    private final String fileName;

    /* renamed from: id */
    private final String f737id;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public LottieImageAsset(int i, int i2, String str, String str2, String str3) {
        this.f737id = str;
        this.fileName = str2;
    }

    public String getId() {
        return this.f737id;
    }

    public String getFileName() {
        return this.fileName;
    }
}
