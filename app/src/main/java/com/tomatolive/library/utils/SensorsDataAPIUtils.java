package com.tomatolive.library.utils;

import android.app.Application;
import android.text.TextUtils;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.CustomProperties;
import com.sensorsdata.analytics.android.sdk.GetAnalyticsDataListener;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.data.DbAdapter;
import com.sensorsdata.analytics.android.sdk.util.JSONUtils;
import com.sensorsdata.analytics.android.sdk.util.NetworkUtils;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.EventConfigRetrofit;
import com.tomatolive.library.http.EventReportRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.LogEventConfigCacheEntity;
import com.tomatolive.library.model.LogEventConfigEntity;
import com.tomatolive.library.model.p135db.AnchorLiveDataEntity;
import com.tomatolive.library.model.p135db.LiveDataEntity;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class SensorsDataAPIUtils {
    public static String TAG = "SensorsData";
    private static AtomicInteger errorCount = new AtomicInteger(0);

    private SensorsDataAPIUtils() {
    }

    public static void initSensorsDataAPI(final Application application) {
        if (application != null && AppUtils.isEnableLogEventReport() && AppUtils.isEventStatisticsService()) {
            SAConfigOptions sAConfigOptions = new SAConfigOptions();
            sAConfigOptions.setAutoTrackEventType(11).enableTrackAppCrash();
            SensorsDataAPI.startWithConfigOptions(application, sAConfigOptions);
            SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();
            initLogEventConfig();
            LogEventConfigCacheEntity logEventConfigCacheEntity = (LogEventConfigCacheEntity) CacheDiskUtils.getInstance().getParcelable(ConstantUtils.LOG_EVENT_KEY, LogEventConfigCacheEntity.CREATOR);
            if (logEventConfigCacheEntity != null) {
                setLogMinFlushInterval(logEventConfigCacheEntity.getEventList());
            }
            SensorsDataAPI.sharedInstance().setCustomExtraProperties(new CustomProperties() { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.1
                @Override // com.sensorsdata.analytics.android.sdk.CustomProperties
                public String getRuleString() {
                    return "com.tomatolive.library";
                }

                @Override // com.sensorsdata.analytics.android.sdk.CustomProperties
                public JSONObject getAppPublicProperties(String str) {
                    JSONObject jSONObject = new JSONObject();
                    String networkType = NetworkUtils.networkType(application);
                    try {
                        String str2 = "1";
                        jSONObject.put(AopConstants.WIFI, "WIFI".equals(networkType) ? str2 : "0");
                        jSONObject.put(AopConstants.NETWORK_TYPE, networkType);
                        jSONObject.put(LogConstants.OPEN_ID, UserInfoManager.getInstance().getAppOpenId());
                        jSONObject.put(AopConstants.TIME_KEY, String.valueOf(System.currentTimeMillis()));
                        jSONObject.put(AopConstants.IS_FIRST_DAY, String.valueOf(SASystemUtils.isFirstDay()));
                        if (!TextUtils.equals(str, LogConstants.APP_INSTALL_EVENT_NAME)) {
                            str2 = "0";
                        }
                        jSONObject.put("isFirstTime", str2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return jSONObject;
                }

                @Override // com.sensorsdata.analytics.android.sdk.CustomProperties
                public void dealRawProperties(String str, JSONObject jSONObject) {
                    char c;
                    int hashCode = str.hashCode();
                    if (hashCode == 401582162) {
                        if (str.equals(AopConstants.APP_VIEW_SCREEN_EVENT_NAME)) {
                            c = 2;
                        }
                        c = 65535;
                    } else if (hashCode != 1221389025) {
                        if (hashCode == 1967735578 && str.equals(AopConstants.APP_END_EVENT_NAME)) {
                            c = 1;
                        }
                        c = 65535;
                    } else {
                        if (str.equals(AopConstants.APP_START_EVENT_NAME)) {
                            c = 0;
                        }
                        c = 65535;
                    }
                    if (c == 0 || c == 1) {
                        jSONObject.remove(AopConstants.SCREEN_NAME);
                        jSONObject.remove("title");
                    } else if (c != 2) {
                    } else {
                        jSONObject.remove(AopConstants.SCREEN_NAME);
                    }
                }
            });
            SensorsDataAPI.sharedInstance().setAnalyticsDataListener(new GetAnalyticsDataListener() { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.2
                @Override // com.sensorsdata.analytics.android.sdk.GetAnalyticsDataListener
                public void getAnalyticsDataList(List<JSONObject> list) {
                    String str = SensorsDataAPIUtils.TAG;
                    LogUtils.iTag(str, "rawList = " + list);
                    LogEventConfigCacheEntity logEventConfigCacheEntity2 = (LogEventConfigCacheEntity) CacheDiskUtils.getInstance().getParcelable(ConstantUtils.LOG_EVENT_KEY, LogEventConfigCacheEntity.CREATOR);
                    if (logEventConfigCacheEntity2 == null) {
                        LogUtils.iTag(SensorsDataAPIUtils.TAG, "没有上报配置，就都不上报");
                        SensorsDataAPIUtils.deleteRedundantData(list);
                        return;
                    }
                    List<LogEventConfigEntity> eventList = logEventConfigCacheEntity2.getEventList();
                    for (int i = 0; i < eventList.size(); i++) {
                        LogEventConfigEntity logEventConfigEntity = eventList.get(i);
                        ArrayList arrayList = new ArrayList();
                        String events = logEventConfigEntity.getEvents();
                        int timeLimit = logEventConfigEntity.getTimeLimit();
                        int quantityLimit = logEventConfigEntity.getQuantityLimit();
                        if (quantityLimit <= 0) {
                            quantityLimit = 100;
                        }
                        String id = logEventConfigEntity.getId();
                        List asList = Arrays.asList(events.split(","));
                        for (JSONObject jSONObject : list) {
                            if (asList.contains(jSONObject.optString(AopConstants.APP_EVENT_KEY))) {
                                arrayList.add(jSONObject);
                            }
                        }
                        if (arrayList.size() != 0) {
                            list.removeAll(arrayList);
                            SensorsDataAPIUtils.assertUploadData(arrayList, timeLimit, quantityLimit, id, application);
                        }
                    }
                    SensorsDataAPIUtils.deleteRedundantData(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void deleteRedundantData(List<JSONObject> list) {
        if (list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (JSONObject jSONObject : list) {
                arrayList.add((String) jSONObject.remove(AopConstants.DB_ID_KEY));
                String str = TAG;
                LogUtils.iTag(str, "删除不在配置内的数据：" + jSONObject.optString(AopConstants.APP_EVENT_KEY));
            }
            DbAdapter.getInstance().cleanupEvents(arrayList);
        }
    }

    public static void initLogEventConfig() {
        if (!AppUtils.isEventStatisticsService()) {
            return;
        }
        EventConfigRetrofit.getInstance().getApiService().getLogEventConfigService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<LogEventConfigEntity>>() { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.5
        }).onErrorResumeNext(new HttpResultFunction<List<LogEventConfigEntity>>() { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.4
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<List<LogEventConfigEntity>>(false) { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<LogEventConfigEntity> list) {
                LogUtils.json(SensorsDataAPIUtils.TAG, list);
                LogEventUtils.eventName = SensorsDataAPIUtils.getEventNames(list);
                LogEventConfigCacheEntity logEventConfigCacheEntity = new LogEventConfigCacheEntity();
                logEventConfigCacheEntity.setEventList(list);
                CacheDiskUtils.getInstance().put(ConstantUtils.LOG_EVENT_KEY, logEventConfigCacheEntity);
                SensorsDataAPIUtils.setLogMinFlushInterval(list);
                SensorsDataAPIUtils.initErrorReportData();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                LogUtils.iTag(SensorsDataAPIUtils.TAG, th.getMessage());
                SensorsDataAPIUtils.initErrorReportData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<String> getEventNames(List<LogEventConfigEntity> list) {
        ArrayList arrayList = new ArrayList();
        for (LogEventConfigEntity logEventConfigEntity : list) {
            arrayList.addAll(Arrays.asList(logEventConfigEntity.getEvents().split(",")));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initErrorReportData() {
        uploadAppInstall();
        LiveDataEntity liveData = DBUtils.getLiveData();
        if (liveData != null) {
            LogEventUtils.uploadLeaveRoom(liveData);
            DBUtils.deleteAll(LiveDataEntity.class);
        }
        AnchorLiveDataEntity anchorLiveData = DBUtils.getAnchorLiveData();
        if (anchorLiveData != null) {
            LogEventUtils.uploadEndLive(anchorLiveData);
            DBUtils.deleteAll(AnchorLiveDataEntity.class);
        }
    }

    private static void uploadAppInstall() {
        boolean z = SPUtils.getInstance().getBoolean(LogConstants.IS_APP_INSTALL, true);
        if (!AppUtils.isEnableLogEventReport() || !z) {
            return;
        }
        LogEventUtils.uploadAppInstall();
        SPUtils.getInstance().put(LogConstants.IS_APP_INSTALL, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setLogMinFlushInterval(List<LogEventConfigEntity> list) {
        if (list == null) {
            return;
        }
        int i = 0;
        for (LogEventConfigEntity logEventConfigEntity : list) {
            int timeLimit = logEventConfigEntity.getTimeLimit();
            if (i == 0) {
                i = timeLimit;
            }
            i = Math.min(i, timeLimit);
        }
        if (i <= 0) {
            return;
        }
        SensorsDataAPI.sharedInstance().setFlushInterval(i * 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void assertUploadData(List<JSONObject> list, int i, int i2, String str, Application application) {
        long j = SPUtils.getInstance(ConstantUtils.UPLOAD_TIME_KEY).getLong(str);
        if (list.size() >= i2 || System.currentTimeMillis() - j >= i * 1000) {
            uploadLogEvent(list, application);
            SPUtils.getInstance(ConstantUtils.UPLOAD_TIME_KEY).put(str, System.currentTimeMillis());
        }
    }

    private static void uploadLogEvent(List<JSONObject> list, Application application) {
        final ArrayList arrayList = new ArrayList();
        for (JSONObject jSONObject : list) {
            arrayList.add((String) jSONObject.remove(AopConstants.DB_ID_KEY));
        }
        Map<String, Object> deviceInfo = SASystemUtils.getDeviceInfo(application);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(AopConstants.DEVICE_KEY, new JSONObject(deviceInfo));
            jSONObject2.put("events", new JSONArray((Collection) list));
            jSONObject2.put(LogConstants.APP_ID, UserInfoManager.getInstance().getAppId());
            jSONObject2.put(LogConstants.APP_VERSION, SASystemUtils.getAppVersionName(application));
            jSONObject2.put("lib", application.getString(R$string.fq_live_lib));
            jSONObject2.put("libVersion", SASystemUtils.getLibVersion());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.json(TAG, JSONUtils.formatJson(jSONObject2.toString()));
        Map<String, Object> map = (Map) GsonUtils.fromJson(jSONObject2.toString(), (Class<Object>) Map.class);
        if (!AppUtils.isEventStatisticsService()) {
            return;
        }
        EventReportRetrofit.getInstance().getApiService().uploadLogEventService(map).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.8
        }).onErrorResumeNext(new HttpResultFunction<LogEventConfigEntity>() { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.7
        }).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Object>(false) { // from class: com.tomatolive.library.utils.SensorsDataAPIUtils.6
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                LogUtils.iTag(SensorsDataAPIUtils.TAG, "数据统计开始上报：");
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                String str = SensorsDataAPIUtils.TAG;
                LogUtils.iTag(str, "数据统计上报成功：" + arrayList);
                SensorsDataAPIUtils.errorCount.set(0);
                DbAdapter.getInstance().cleanupEvents(arrayList);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                LogUtils.iTag(SensorsDataAPIUtils.TAG, "数据统计上报失败");
                SensorsDataAPIUtils.errorCount.incrementAndGet();
                if (SensorsDataAPIUtils.errorCount.get() >= 3) {
                    SensorsDataAPI.sharedInstance().deleteAll();
                }
            }
        });
    }
}
