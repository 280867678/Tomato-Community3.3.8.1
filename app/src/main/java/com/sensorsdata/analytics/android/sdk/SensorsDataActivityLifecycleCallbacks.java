package com.sensorsdata.analytics.android.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.data.DbAdapter;
import com.sensorsdata.analytics.android.sdk.data.DbParams;
import com.sensorsdata.analytics.android.sdk.data.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.SASystemUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataTimer;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.tomatolive.library.utils.DateUtils;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
@TargetApi(14)
/* loaded from: classes3.dex */
public class SensorsDataActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String EVENT_TIMER = "event_timer";
    private static final String TAG = "SA.LifecycleCallbacks";
    private static final int TIME_INTERVAL = 10000;
    private Handler handler;
    private boolean isMultiProcess;
    private Context mContext;
    private CustomProperties mCustomProperties;
    private final SensorsDataAPI mSensorsDataInstance;
    private int startActivityCount;
    private int startTimerCount;
    private boolean resumeFromBackground = false;
    private JSONObject activityProperty = new JSONObject();
    private JSONObject endDataProperty = new JSONObject();
    private final int MESSAGE_END = 0;
    private final String APP_START_TIME = PersistentLoader.PersistentName.APP_START_TIME;
    private final String APP_END_TIME = PersistentLoader.PersistentName.APP_PAUSED_TIME;
    private final String APP_END_DATA = PersistentLoader.PersistentName.APP_END_DATA;
    private final String APP_RESET_STATE = "app_reset_state";
    private long messageReceiveTime = 0;
    private Runnable timer = new Runnable() { // from class: com.sensorsdata.analytics.android.sdk.SensorsDataActivityLifecycleCallbacks.1
        @Override // java.lang.Runnable
        public void run() {
            if (!SensorsDataActivityLifecycleCallbacks.this.mSensorsDataInstance.isAutoTrackEnabled() || !SensorsDataActivityLifecycleCallbacks.this.isAutoTrackAppEnd()) {
                return;
            }
            SensorsDataActivityLifecycleCallbacks.this.generateAppEndData();
        }
    };
    private DbAdapter mDbAdapter = DbAdapter.getInstance();
    private int sessionTime = this.mDbAdapter.getSessionIntervalTime();

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SensorsDataActivityLifecycleCallbacks(SensorsDataAPI sensorsDataAPI, Context context) {
        this.mSensorsDataInstance = sensorsDataAPI;
        this.mContext = context;
        this.isMultiProcess = this.mSensorsDataInstance.isMultiProcess();
        initHandler();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        try {
            this.activityProperty = AopUtil.buildTitleAndScreenName(activity);
            if (this.mCustomProperties != null) {
                String ruleString = this.mCustomProperties.getRuleString();
                if (!TextUtils.isEmpty(ruleString) && !TextUtils.isEmpty(this.activityProperty.optString(AopConstants.SCREEN_NAME)) && !this.activityProperty.optString(AopConstants.SCREEN_NAME).startsWith(ruleString)) {
                    this.activityProperty.put(AopConstants.SCREEN_NAME, "unknown");
                    this.activityProperty.put("title", "unknown");
                }
            }
            SensorsDataUtils.mergeJSONObject(this.activityProperty, this.endDataProperty);
            if (this.isMultiProcess) {
                this.startActivityCount = this.mDbAdapter.getActivityCount();
                DbAdapter dbAdapter = this.mDbAdapter;
                int i = this.startActivityCount + 1;
                this.startActivityCount = i;
                dbAdapter.commitActivityCount(i);
            } else {
                this.startActivityCount++;
            }
            if (this.startActivityCount == 1) {
                this.handler.removeMessages(0);
                if (isSessionTimeOut()) {
                    this.handler.sendMessage(generateMessage(false));
                    try {
                        this.mSensorsDataInstance.appBecomeActive();
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                    if (this.resumeFromBackground) {
                        this.mSensorsDataInstance.applySDKConfigFromCache();
                        this.mSensorsDataInstance.resumeTrackScreenOrientation();
                    }
                    try {
                        if (this.mSensorsDataInstance.isAutoTrackEnabled() && !this.mSensorsDataInstance.isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_START)) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put(AopConstants.RESUME_FROM_BACKGROUND, SASystemUtils.booleanToString(this.resumeFromBackground));
                            SensorsDataUtils.mergeJSONObject(this.activityProperty, jSONObject);
                            this.mSensorsDataInstance.track(AopConstants.APP_START_EVENT_NAME, jSONObject);
                        }
                        try {
                            this.mDbAdapter.commitAppStartTime(SystemClock.elapsedRealtime());
                        } catch (Exception unused) {
                            this.mDbAdapter.commitAppStartTime(SystemClock.elapsedRealtime());
                        }
                    } catch (Exception e2) {
                        SALog.m3672i(TAG, e2);
                    }
                    if (this.resumeFromBackground) {
                        try {
                            HeatMapService.getInstance().resume();
                            VisualizedAutoTrackService.getInstance().resume();
                        } catch (Exception e3) {
                            SALog.printStackTrace(e3);
                        }
                    }
                    this.resumeFromBackground = true;
                }
            }
            int i2 = this.startTimerCount;
            this.startTimerCount = i2 + 1;
            if (i2 != 0) {
                return;
            }
            SensorsDataTimer.getInstance().timer(this.timer, 0L, 10000L);
        } catch (Exception e4) {
            SALog.printStackTrace(e4);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        JSONObject trackProperties;
        try {
            if (!this.mSensorsDataInstance.isAutoTrackEnabled() || this.mSensorsDataInstance.isActivityAutoTrackAppViewScreenIgnored(activity.getClass()) || this.mSensorsDataInstance.isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN)) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            SensorsDataUtils.mergeJSONObject(this.activityProperty, jSONObject);
            jSONObject.put(AopConstants.RESUME_FROM_BACKGROUND, SASystemUtils.booleanToString(this.resumeFromBackground));
            if ((activity instanceof ScreenAutoTracker) && (trackProperties = ((ScreenAutoTracker) activity).getTrackProperties()) != null) {
                SensorsDataUtils.mergeJSONObject(trackProperties, jSONObject);
            }
            String screenUrl = SensorsDataUtils.getScreenUrl(activity);
            if (this.mCustomProperties != null) {
                String ruleString = this.mCustomProperties.getRuleString();
                if (!TextUtils.isEmpty(ruleString) && !TextUtils.isEmpty(screenUrl) && !screenUrl.startsWith(ruleString)) {
                    return;
                }
            }
            this.mSensorsDataInstance.trackViewScreen(screenUrl, jSONObject);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i;
        try {
            this.startTimerCount--;
            if (this.startTimerCount == 0) {
                SensorsDataTimer.getInstance().shutdownTimerTask();
            }
            if (this.mSensorsDataInstance.isMultiProcess()) {
                this.startActivityCount = this.mDbAdapter.getActivityCount();
                if (this.startActivityCount > 0) {
                    i = this.startActivityCount - 1;
                    this.startActivityCount = i;
                } else {
                    i = 0;
                }
                this.startActivityCount = i;
                this.mDbAdapter.commitActivityCount(this.startActivityCount);
            } else {
                this.startActivityCount--;
            }
            if (this.startActivityCount > 0) {
                return;
            }
            generateAppEndData();
            this.handler.sendMessageDelayed(generateMessage(true), this.sessionTime);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trackAppEnd(long j, long j2, String str) {
        try {
            if (!this.mSensorsDataInstance.isAutoTrackEnabled() || !isAutoTrackAppEnd() || TextUtils.isEmpty(str)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(str);
            long optLong = jSONObject.optLong(EVENT_TIMER);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AopConstants.SCREEN_NAME, jSONObject.optString(AopConstants.SCREEN_NAME));
            jSONObject2.put("title", jSONObject.optString("title"));
            jSONObject2.put("duration", duration(j, optLong));
            this.mSensorsDataInstance.track(AopConstants.APP_END_EVENT_NAME, jSONObject2);
            this.mDbAdapter.commitAppEndData("");
            this.mSensorsDataInstance.flushSync();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private String duration(long j, long j2) {
        long j3 = j2 - j;
        try {
            if (j3 < 0 || j3 > DateUtils.ONE_DAY_MILLIONS) {
                return String.valueOf(0);
            }
            float f = ((float) j3) / 1000.0f;
            return f < 0.0f ? String.valueOf(0) : String.format(Locale.CHINA, "%.3f", Float.valueOf(f));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return String.valueOf(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generateAppEndData() {
        try {
            this.endDataProperty.put(EVENT_TIMER, SystemClock.elapsedRealtime());
            this.mDbAdapter.commitAppEndData(this.endDataProperty.toString());
            this.mDbAdapter.commitAppEndTime(System.currentTimeMillis());
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private boolean isSessionTimeOut() {
        long j = 946656000000L;
        if (System.currentTimeMillis() > 946656000000L) {
            j = System.currentTimeMillis();
        }
        return Math.abs(j - this.mDbAdapter.getAppEndTime()) > ((long) this.sessionTime);
    }

    private Message generateMessage(boolean z) {
        Message obtain = Message.obtain(this.handler);
        obtain.what = 0;
        Bundle bundle = new Bundle();
        bundle.putLong(PersistentLoader.PersistentName.APP_START_TIME, DbAdapter.getInstance().getAppStartTime());
        bundle.putLong(PersistentLoader.PersistentName.APP_PAUSED_TIME, DbAdapter.getInstance().getAppEndTime());
        bundle.putString(PersistentLoader.PersistentName.APP_END_DATA, DbAdapter.getInstance().getAppEndData());
        bundle.putBoolean("app_reset_state", z);
        obtain.setData(bundle);
        return obtain;
    }

    private void initHandler() {
        try {
            HandlerThread handlerThread = new HandlerThread("app_end_timer");
            handlerThread.start();
            this.handler = new Handler(handlerThread.getLooper()) { // from class: com.sensorsdata.analytics.android.sdk.SensorsDataActivityLifecycleCallbacks.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (SensorsDataActivityLifecycleCallbacks.this.messageReceiveTime == 0 || SystemClock.elapsedRealtime() - SensorsDataActivityLifecycleCallbacks.this.messageReceiveTime >= SensorsDataActivityLifecycleCallbacks.this.sessionTime) {
                        SensorsDataActivityLifecycleCallbacks.this.messageReceiveTime = SystemClock.elapsedRealtime();
                        if (message == null) {
                            return;
                        }
                        Bundle data = message.getData();
                        long j = data.getLong(PersistentLoader.PersistentName.APP_START_TIME);
                        long j2 = data.getLong(PersistentLoader.PersistentName.APP_PAUSED_TIME);
                        String string = data.getString(PersistentLoader.PersistentName.APP_END_DATA);
                        if (data.getBoolean("app_reset_state")) {
                            SensorsDataActivityLifecycleCallbacks.this.resetState();
                        } else {
                            j2 += 10000;
                        }
                        SensorsDataActivityLifecycleCallbacks.this.trackAppEnd(j, j2, string);
                    }
                }
            };
            this.mContext.getContentResolver().registerContentObserver(DbParams.getInstance().getSessionTimeUri(), false, new SensorsActivityStateObserver(this.handler));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetState() {
        try {
            this.mSensorsDataInstance.stopTrackScreenOrientation();
            HeatMapService.getInstance().stop();
            VisualizedAutoTrackService.getInstance().stop();
            this.mSensorsDataInstance.appEnterBackground();
            this.resumeFromBackground = true;
            this.mSensorsDataInstance.clearLastScreenUrl();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void setCustomProperties(CustomProperties customProperties) {
        this.mCustomProperties = customProperties;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class SensorsActivityStateObserver extends ContentObserver {
        SensorsActivityStateObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            try {
                if (!DbParams.getInstance().getSessionTimeUri().equals(uri)) {
                    return;
                }
                SensorsDataActivityLifecycleCallbacks.this.sessionTime = SensorsDataActivityLifecycleCallbacks.this.mDbAdapter.getSessionIntervalTime();
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAutoTrackAppEnd() {
        return !this.mSensorsDataInstance.isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_END);
    }
}
