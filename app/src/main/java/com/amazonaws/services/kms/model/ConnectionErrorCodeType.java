package com.amazonaws.services.kms.model;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public enum ConnectionErrorCodeType {
    INVALID_CREDENTIALS("INVALID_CREDENTIALS"),
    CLUSTER_NOT_FOUND("CLUSTER_NOT_FOUND"),
    NETWORK_ERRORS("NETWORK_ERRORS"),
    INSUFFICIENT_CLOUDHSM_HSMS("INSUFFICIENT_CLOUDHSM_HSMS"),
    USER_LOCKED_OUT("USER_LOCKED_OUT");
    
    private static final Map<String, ConnectionErrorCodeType> enumMap = new HashMap();
    private String value;

    static {
        enumMap.put("INVALID_CREDENTIALS", INVALID_CREDENTIALS);
        enumMap.put("CLUSTER_NOT_FOUND", CLUSTER_NOT_FOUND);
        enumMap.put("NETWORK_ERRORS", NETWORK_ERRORS);
        enumMap.put("INSUFFICIENT_CLOUDHSM_HSMS", INSUFFICIENT_CLOUDHSM_HSMS);
        enumMap.put("USER_LOCKED_OUT", USER_LOCKED_OUT);
    }

    ConnectionErrorCodeType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static ConnectionErrorCodeType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
