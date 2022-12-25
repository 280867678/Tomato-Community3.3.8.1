package com.sensorsdata.analytics.android.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class AppWebViewInterface {
    private static final String TAG = "SA.AppWebViewInterface";
    private boolean enableVerify;
    private Context mContext;
    private JSONObject properties;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppWebViewInterface(Context context, JSONObject jSONObject, boolean z) {
        this.mContext = context;
        this.properties = jSONObject;
        this.enableVerify = z;
    }

    @JavascriptInterface
    public String sensorsdata_call_app() {
        try {
            if (this.properties == null) {
                this.properties = new JSONObject();
            }
            this.properties.put("type", "Android");
            String loginId = SensorsDataAPI.sharedInstance(this.mContext).getLoginId();
            if (!TextUtils.isEmpty(loginId)) {
                this.properties.put("distinct_id", loginId);
                this.properties.put("is_login", true);
            } else {
                this.properties.put("distinct_id", SensorsDataAPI.sharedInstance(this.mContext).getAnonymousId());
                this.properties.put("is_login", false);
            }
            return this.properties.toString();
        } catch (JSONException e) {
            SALog.m3674i(TAG, e.getMessage());
            return null;
        }
    }

    @JavascriptInterface
    public void sensorsdata_track(String str) {
        try {
            SensorsDataAPI.sharedInstance(this.mContext).trackEventFromH5(str, this.enableVerify);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @JavascriptInterface
    public boolean sensorsdata_verify(String str) {
        try {
            if (!this.enableVerify) {
                sensorsdata_track(str);
                return true;
            }
            return SensorsDataAPI.sharedInstance(this.mContext)._trackEventFromH5(str);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }
}
