package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes.dex */
public final class AdsMediaSource$AdLoadException extends IOException {
    public static final int TYPE_AD = 0;
    public static final int TYPE_AD_GROUP = 1;
    public static final int TYPE_ALL_ADS = 2;
    public static final int TYPE_UNEXPECTED = 3;
    public final int type;

    public static AdsMediaSource$AdLoadException createForAd(Exception exc) {
        return new AdsMediaSource$AdLoadException(0, exc);
    }

    public static AdsMediaSource$AdLoadException createForAdGroup(Exception exc, int i) {
        return new AdsMediaSource$AdLoadException(1, new IOException("Failed to load ad group " + i, exc));
    }

    public static AdsMediaSource$AdLoadException createForAllAds(Exception exc) {
        return new AdsMediaSource$AdLoadException(2, exc);
    }

    public static AdsMediaSource$AdLoadException createForUnexpected(RuntimeException runtimeException) {
        return new AdsMediaSource$AdLoadException(3, runtimeException);
    }

    private AdsMediaSource$AdLoadException(int i, Exception exc) {
        super(exc);
        this.type = i;
    }

    public RuntimeException getRuntimeExceptionForUnexpected() {
        Assertions.checkState(this.type == 3);
        return (RuntimeException) getCause();
    }
}
