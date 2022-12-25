package com.sensorsdata.analytics.android.sdk;

import org.json.JSONObject;

/* loaded from: classes3.dex */
public interface CustomProperties {
    void dealRawProperties(String str, JSONObject jSONObject);

    JSONObject getAppPublicProperties(String str);

    String getRuleString();
}
