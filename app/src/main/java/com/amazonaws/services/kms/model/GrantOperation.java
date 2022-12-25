package com.amazonaws.services.kms.model;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public enum GrantOperation {
    Decrypt("Decrypt"),
    Encrypt("Encrypt"),
    GenerateDataKey("GenerateDataKey"),
    GenerateDataKeyWithoutPlaintext("GenerateDataKeyWithoutPlaintext"),
    ReEncryptFrom("ReEncryptFrom"),
    ReEncryptTo("ReEncryptTo"),
    CreateGrant("CreateGrant"),
    RetireGrant("RetireGrant"),
    DescribeKey("DescribeKey");
    
    private static final Map<String, GrantOperation> enumMap = new HashMap();
    private String value;

    static {
        enumMap.put("Decrypt", Decrypt);
        enumMap.put("Encrypt", Encrypt);
        enumMap.put("GenerateDataKey", GenerateDataKey);
        enumMap.put("GenerateDataKeyWithoutPlaintext", GenerateDataKeyWithoutPlaintext);
        enumMap.put("ReEncryptFrom", ReEncryptFrom);
        enumMap.put("ReEncryptTo", ReEncryptTo);
        enumMap.put("CreateGrant", CreateGrant);
        enumMap.put("RetireGrant", RetireGrant);
        enumMap.put("DescribeKey", DescribeKey);
    }

    GrantOperation(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static GrantOperation fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if (enumMap.containsKey(str)) {
            return enumMap.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}
