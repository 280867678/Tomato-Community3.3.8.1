package com.sensorsdata.analytics.android.sdk;

/* loaded from: classes3.dex */
public abstract class AbstractSAConfigOptions {
    String mAnonymousId;
    int mAutoTrackEventType;
    boolean mEnableTrackAppCrash;
    int mFlushBulkSize;
    int mFlushInterval;
    boolean mRNAutoTrackEnabled;
    boolean mTrackScreenOrientationEnabled;
    long mMaxCacheSize = 33554432;
    int mNetworkTypePolicy = 30;
    boolean enableMultiProcess = true;
}
