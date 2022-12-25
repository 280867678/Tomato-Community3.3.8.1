package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.content.SharedPreferences;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PersistentSuperProperties extends PersistentIdentity<JSONObject> {
    public PersistentSuperProperties(Future<SharedPreferences> future) {
        super(future, PersistentLoader.PersistentName.SUPER_PROPERTIES, new PersistentIdentity.PersistentSerializer<JSONObject>() { // from class: com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSuperProperties.1
            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            /* renamed from: load  reason: collision with other method in class */
            public JSONObject mo6512load(String str) {
                try {
                    return new JSONObject(str);
                } catch (JSONException e) {
                    SALog.m3676d("Persistent", "failed to load SuperProperties from SharedPreferences.", e);
                    return new JSONObject();
                }
            }

            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            public String save(JSONObject jSONObject) {
                if (jSONObject == null) {
                    jSONObject = mo6511create();
                }
                return jSONObject.toString();
            }

            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            /* renamed from: create  reason: collision with other method in class */
            public JSONObject mo6511create() {
                return new JSONObject();
            }
        });
    }
}
