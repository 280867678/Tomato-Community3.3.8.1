package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

/* loaded from: classes3.dex */
public class VisualizedAutoTrackService {
    private static VisualizedAutoTrackService instance;
    private static VisualizedAutoTrackViewCrawler mVTrack;

    private VisualizedAutoTrackService() {
    }

    public static VisualizedAutoTrackService getInstance() {
        if (instance == null) {
            instance = new VisualizedAutoTrackService();
        }
        return instance;
    }

    public void stop() {
        try {
            if (mVTrack == null) {
                return;
            }
            mVTrack.stopUpdates(false);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void resume() {
        try {
            if (mVTrack == null) {
                return;
            }
            mVTrack.startUpdates();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void start(Activity activity, String str, String str2) {
        try {
            Bundle bundle = activity.getApplicationContext().getPackageManager().getApplicationInfo(activity.getApplicationContext().getPackageName(), 128).metaData;
            if (bundle == null) {
                bundle = new Bundle();
            }
            if (Build.VERSION.SDK_INT < 16) {
                return;
            }
            String string = bundle.getString("com.sensorsdata.analytics.android.ResourcePackageName");
            if (string == null) {
                string = activity.getPackageName();
            }
            mVTrack = new VisualizedAutoTrackViewCrawler(activity, string, str, str2);
            mVTrack.startUpdates();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public boolean isVisualizedAutoTrackRunning() {
        VisualizedAutoTrackViewCrawler visualizedAutoTrackViewCrawler = mVTrack;
        if (visualizedAutoTrackViewCrawler != null) {
            return visualizedAutoTrackViewCrawler.isVisualizedAutoTrackRunning();
        }
        return false;
    }
}
