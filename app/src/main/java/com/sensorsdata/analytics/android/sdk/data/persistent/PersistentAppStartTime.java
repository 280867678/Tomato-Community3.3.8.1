package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.content.SharedPreferences;
import com.sensorsdata.analytics.android.sdk.data.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;
import java.util.concurrent.Future;

/* loaded from: classes3.dex */
public class PersistentAppStartTime extends PersistentIdentity<Long> {
    public PersistentAppStartTime(Future<SharedPreferences> future) {
        super(future, PersistentLoader.PersistentName.APP_START_TIME, new PersistentIdentity.PersistentSerializer<Long>() { // from class: com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppStartTime.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            /* renamed from: load */
            public Long mo6512load(String str) {
                return Long.valueOf(str);
            }

            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            public String save(Long l) {
                return l == null ? mo6511create().toString() : String.valueOf(l);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity.PersistentSerializer
            /* renamed from: create */
            public Long mo6511create() {
                return 0L;
            }
        });
    }
}
