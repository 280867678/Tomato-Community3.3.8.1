package com.sensorsdata.analytics.android.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.webkit.WebView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SensorsDataAPIEmptyImplementation extends SensorsDataAPI {
    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void addHeatMapActivities(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void addHeatMapActivity(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void addVisualizedAutoTrackActivities(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void addVisualizedAutoTrackActivity(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void clearGPSLocation() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void clearLastScreenUrl() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void clearReferrerWhenAppEnd() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void clearSuperProperties() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void clearTrackTimer() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void deleteAll() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void disableAutoTrack(SensorsDataAPI.AutoTrackEventType autoTrackEventType) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void disableAutoTrack(List<SensorsDataAPI.AutoTrackEventType> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableAppHeatMapConfirmDialog(boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void enableAutoTrack() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableAutoTrack(List<SensorsDataAPI.AutoTrackEventType> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void enableAutoTrackFragment(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void enableAutoTrackFragments(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableHeatMap() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableLog(boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableNetworkRequest(boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableReactNativeAutoTrack() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableTrackScreenOrientation(boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableVisualizedAutoTrack() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void enableVisualizedAutoTrackConfirmDialog(boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void flush() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void flushSync() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String getAnonymousId() {
        return null;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String getCookie(boolean z) {
        return null;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String getDistinctId() {
        return null;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public int getFlushBulkSize() {
        return 0;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public int getFlushInterval() {
        return 0;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String getLastScreenUrl() {
        return null;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String getLoginId() {
        return null;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String getMainProcessName() {
        return "";
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public long getMaxCacheSize() {
        return 0L;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public int getSessionIntervalTime() {
        return 30000;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void identify(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void ignoreAutoTrackActivities(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void ignoreAutoTrackActivity(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void ignoreAutoTrackEventType(SensorsDataAPI.AutoTrackEventType autoTrackEventType) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void ignoreAutoTrackEventType(List<SensorsDataAPI.AutoTrackEventType> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void ignoreAutoTrackFragment(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void ignoreAutoTrackFragments(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void ignoreView(View view) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void ignoreView(View view, boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void ignoreViewType(Class cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isActivityAutoTrackAppClickIgnored(Class<?> cls) {
        return true;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isActivityAutoTrackAppViewScreenIgnored(Class<?> cls) {
        return true;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI
    boolean isAppHeatMapConfirmDialogEnabled() {
        return true;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isAutoTrackEnabled() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType autoTrackEventType) {
        return true;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isDebugMode() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public boolean isFragmentAutoTrackAppViewScreen(Class<?> cls) {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isHeatMapActivity(Class<?> cls) {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isHeatMapEnabled() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isReactNativeAutoTrackEnabled() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public boolean isTrackFragmentAppViewScreenEnabled() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isVisualizedAutoTrackActivity(Class<?> cls) {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI
    boolean isVisualizedAutoTrackConfirmDialogEnabled() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public boolean isVisualizedAutoTrackEnabled() {
        return false;
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void itemDelete(String str, String str2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void itemSet(String str, String str2, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void login(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void login(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void logout() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileAppend(String str, String str2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileAppend(String str, Set<String> set) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileDelete() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileIncrement(String str, Number number) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileIncrement(Map<String, ? extends Number> map) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profilePushId(String str, String str2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileSet(String str, Object obj) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileSet(JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileSetOnce(String str, Object obj) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileSetOnce(JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileUnset(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void profileUnsetPushId(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void registerDynamicSuperProperties(SensorsDataDynamicSuperProperties sensorsDataDynamicSuperProperties) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void registerSuperProperties(JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void resetAnonymousId() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void resumeAutoTrackActivities(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void resumeAutoTrackActivity(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void resumeIgnoredAutoTrackFragment(Class<?> cls) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void resumeIgnoredAutoTrackFragments(List<Class<?>> list) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void resumeTrackScreenOrientation() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setCookie(String str, boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setFlushBulkSize(int i) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setFlushInterval(int i) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setFlushNetworkPolicy(int i) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setGPSLocation(double d, double d2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setMaxCacheSize(long j) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setServerUrl(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setSessionIntervalTime(int i) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setTrackEventCallBack(SensorsDataTrackEventCallBack sensorsDataTrackEventCallBack) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setViewActivity(View view, Activity activity) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setViewFragmentName(View view, String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setViewID(Dialog dialog, String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setViewID(View view, String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setViewID(Object obj, String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void setViewProperties(View view, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @SuppressLint({"SetJavaScriptEnabled", "addJavascriptInterface"})
    @Deprecated
    public void showUpWebView(WebView webView, JSONObject jSONObject, boolean z, boolean z2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @SuppressLint({"SetJavaScriptEnabled", "addJavascriptInterface"})
    public void showUpWebView(WebView webView, boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @SuppressLint({"SetJavaScriptEnabled", "addJavascriptInterface"})
    @Deprecated
    public void showUpWebView(WebView webView, boolean z, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @SuppressLint({"SetJavaScriptEnabled", "addJavascriptInterface"})
    public void showUpWebView(WebView webView, boolean z, boolean z2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void showUpX5WebView(Object obj) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void showUpX5WebView(Object obj, JSONObject jSONObject, boolean z, boolean z2) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void showUpX5WebView(Object obj, boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void startTrackThread() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void stopTrackScreenOrientation() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void stopTrackThread() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void track(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void track(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackAppCrash() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackChannelEvent(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackChannelEvent(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackEventFromH5(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackEventFromH5(String str, boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.internal.IFragmentAPI
    public void trackFragmentAppViewScreen() {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackInstallation(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackInstallation(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackInstallation(String str, JSONObject jSONObject, boolean z) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void trackSignUp(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void trackSignUp(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void trackTimer(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    @Deprecated
    public void trackTimer(String str, TimeUnit timeUnit) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackTimerBegin(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackTimerBegin(String str, TimeUnit timeUnit) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackTimerEnd(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackTimerEnd(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public String trackTimerStart(String str) {
        return "";
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackViewAppClick(View view) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackViewAppClick(View view, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackViewScreen(Activity activity) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackViewScreen(Object obj) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void trackViewScreen(String str, JSONObject jSONObject) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void unregisterSuperProperty(String str) {
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public JSONObject getPresetProperties() {
        return new JSONObject();
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public List<Class> getIgnoredViewTypeList() {
        return new ArrayList();
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public void removeTimer(String str) {
        super.removeTimer(str);
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public JSONObject getLastScreenTrackProperties() {
        return new JSONObject();
    }

    @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI, com.sensorsdata.analytics.android.sdk.ISensorsDataAPI
    public JSONObject getSuperProperties() {
        return new JSONObject();
    }
}
