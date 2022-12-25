package com.amazonaws.services.kms.model;

import com.amazonaws.services.p054s3.model.BucketLifecycleConfiguration;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public enum KeyState {
    Enabled("Enabled"),
    Disabled(BucketLifecycleConfiguration.DISABLED),
    PendingDeletion("PendingDeletion"),
    PendingImport("PendingImport"),
    Unavailable("Unavailable");
    
    private static final Map<String, KeyState> enumMap = new HashMap();
    private String value;

    static {
        enumMap.put("Enabled", Enabled);
        enumMap.put(BucketLifecycleConfiguration.DISABLED, Disabled);
        enumMap.put("PendingDeletion", PendingDeletion);
        enumMap.put("PendingImport", PendingImport);
        enumMap.put("Unavailable", Unavailable);
    }

    KeyState(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static KeyState fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
