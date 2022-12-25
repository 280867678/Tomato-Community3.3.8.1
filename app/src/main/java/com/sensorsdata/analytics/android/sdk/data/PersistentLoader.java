package com.sensorsdata.analytics.android.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppEndData;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppPaused;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppStartTime;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentDistinctId;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstDay;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstStart;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstTrackInstallation;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstTrackInstallationWithCallback;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoginId;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentRemoteSDKConfig;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSessionIntervalTime;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSuperProperties;
import java.util.concurrent.Future;

/* loaded from: classes3.dex */
public class PersistentLoader {
    private static Context context;
    private static volatile PersistentLoader instance;
    private static Future<SharedPreferences> storedPreferences;

    /* loaded from: classes3.dex */
    public interface PersistentName {
        public static final String APP_END_DATA = "app_end_data";
        public static final String APP_PAUSED_TIME = "app_end_time";
        public static final String APP_SESSION_TIME = "session_interval_time";
        public static final String APP_START_TIME = "app_start_time";
        public static final String DISTINCT_ID = "events_distinct_id";
        public static final String FIRST_DAY = "first_day";
        public static final String FIRST_INSTALL = "first_track_installation";
        public static final String FIRST_INSTALL_CALLBACK = "first_track_installation_with_callback";
        public static final String FIRST_START = "first_start";
        public static final String LOGIN_ID = "events_login_id";
        public static final String REMOTE_CONFIG = "sensorsdata_sdk_configuration";
        public static final String SUPER_PROPERTIES = "super_properties";
    }

    private PersistentLoader(Context context2) {
        context = context2.getApplicationContext();
        storedPreferences = new SharedPreferencesLoader().loadPreferences(context2, "com.sensorsdata.analytics.android.sdk.SensorsDataAPI");
    }

    public static PersistentLoader initLoader(Context context2) {
        if (instance == null) {
            instance = new PersistentLoader(context2);
        }
        return instance;
    }

    public static PersistentIdentity loadPersistent(String str) {
        if (instance == null) {
            throw new RuntimeException("you should call 'PersistentLoader.initLoader(Context)' first");
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1436067305:
                if (str.equals(PersistentName.LOGIN_ID)) {
                    c = '\t';
                    break;
                }
                break;
            case -1173524450:
                if (str.equals(PersistentName.APP_SESSION_TIME)) {
                    c = 2;
                    break;
                }
                break;
            case -951089033:
                if (str.equals(PersistentName.SUPER_PROPERTIES)) {
                    c = 11;
                    break;
                }
                break;
            case -854148740:
                if (str.equals(PersistentName.FIRST_INSTALL_CALLBACK)) {
                    c = 7;
                    break;
                }
                break;
            case -690407917:
                if (str.equals(PersistentName.FIRST_START)) {
                    c = '\b';
                    break;
                }
                break;
            case 133344653:
                if (str.equals(PersistentName.FIRST_DAY)) {
                    c = 5;
                    break;
                }
                break;
            case 721318680:
                if (str.equals(PersistentName.DISTINCT_ID)) {
                    c = 4;
                    break;
                }
                break;
            case 791585128:
                if (str.equals(PersistentName.APP_START_TIME)) {
                    c = 3;
                    break;
                }
                break;
            case 947194773:
                if (str.equals(PersistentName.REMOTE_CONFIG)) {
                    c = '\n';
                    break;
                }
                break;
            case 1214783133:
                if (str.equals(PersistentName.FIRST_INSTALL)) {
                    c = 6;
                    break;
                }
                break;
            case 1521941740:
                if (str.equals(PersistentName.APP_END_DATA)) {
                    c = 0;
                    break;
                }
                break;
            case 1522425871:
                if (str.equals(PersistentName.APP_PAUSED_TIME)) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return new PersistentAppEndData(storedPreferences);
            case 1:
                return new PersistentAppPaused(storedPreferences);
            case 2:
                return new PersistentSessionIntervalTime(storedPreferences);
            case 3:
                return new PersistentAppStartTime(storedPreferences);
            case 4:
                return new PersistentDistinctId(storedPreferences, context);
            case 5:
                return new PersistentFirstDay(storedPreferences);
            case 6:
                return new PersistentFirstTrackInstallation(storedPreferences);
            case 7:
                return new PersistentFirstTrackInstallationWithCallback(storedPreferences);
            case '\b':
                return new PersistentFirstStart(storedPreferences);
            case '\t':
                return new PersistentLoginId(storedPreferences);
            case '\n':
                return new PersistentRemoteSDKConfig(storedPreferences);
            case 11:
                return new PersistentSuperProperties(storedPreferences);
            default:
                return null;
        }
    }
}
