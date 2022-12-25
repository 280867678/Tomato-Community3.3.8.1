package com.amazonaws.services.kms.model;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public enum WrappingKeySpec {
    RSA_2048("RSA_2048");
    
    private static final Map<String, WrappingKeySpec> enumMap = new HashMap();
    private String value;

    static {
        enumMap.put("RSA_2048", RSA_2048);
    }

    WrappingKeySpec(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static WrappingKeySpec fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
