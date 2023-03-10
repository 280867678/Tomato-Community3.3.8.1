package com.amazonaws.services.p054s3.model;

/* renamed from: com.amazonaws.services.s3.model.StorageClass */
/* loaded from: classes2.dex */
public enum StorageClass {
    Standard("STANDARD"),
    ReducedRedundancy("REDUCED_REDUNDANCY"),
    Glacier("GLACIER"),
    StandardInfrequentAccess("STANDARD_IA"),
    OneZoneInfrequentAccess("ONEZONE_IA"),
    IntelligentTiering("INTELLIGENT_TIERING");
    
    private final String storageClassId;

    public static StorageClass fromValue(String str) throws IllegalArgumentException {
        StorageClass[] values;
        for (StorageClass storageClass : values()) {
            if (storageClass.toString().equals(str)) {
                return storageClass;
            }
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }

    StorageClass(String str) {
        this.storageClassId = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.storageClassId;
    }
}
