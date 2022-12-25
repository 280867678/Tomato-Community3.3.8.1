package com.sensorsdata.analytics.android.sdk;

/* loaded from: classes3.dex */
public final class SAConfigOptions extends AbstractSAConfigOptions {
    public SAConfigOptions setAutoTrackEventType(int i) {
        this.mAutoTrackEventType = i;
        return this;
    }

    public SAConfigOptions enableTrackAppCrash() {
        this.mEnableTrackAppCrash = true;
        return this;
    }

    public SAConfigOptions setFlushInterval(int i) {
        this.mFlushInterval = i;
        return this;
    }

    public SAConfigOptions setFlushBulkSize(int i) {
        this.mFlushBulkSize = i;
        return this;
    }

    public SAConfigOptions setMaxCacheSize(long j) {
        this.mMaxCacheSize = Math.max(16777216L, j);
        return this;
    }

    public SAConfigOptions enableReactNativeAutoTrack(boolean z) {
        this.mRNAutoTrackEnabled = z;
        return this;
    }

    public SAConfigOptions setNetworkTypePolicy(int i) {
        this.mNetworkTypePolicy = i;
        return this;
    }
}
