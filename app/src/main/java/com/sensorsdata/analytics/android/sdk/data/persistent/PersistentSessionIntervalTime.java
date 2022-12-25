package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.content.SharedPreferences;
import com.sensorsdata.analytics.android.sdk.data.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;
import java.util.concurrent.Future;

/* loaded from: classes3.dex */
public class PersistentSessionIntervalTime extends PersistentIdentity<Integer> {
    public PersistentSessionIntervalTime(Future<SharedPreferences> future) {
        super(future, PersistentLoader.PersistentName.APP_SESSION_TIME, new PersistentIdentity.PersistentSerializer<Integer>() { // from class: com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSessionIntervalTime.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            /* renamed from: load */
            public Integer mo6512load(String str) {
                return Integer.valueOf(str);
            }

            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            public String save(Integer num) {
                return num == null ? "" : num.toString();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            /* renamed from: create */
            public Integer mo6511create() {
                return 30000;
            }
        });
    }
}
