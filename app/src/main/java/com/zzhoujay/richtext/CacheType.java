package com.zzhoujay.richtext;

/* loaded from: classes4.dex */
public enum CacheType {
    none(0),
    layout(1),
    all(2);
    
    int value;

    CacheType(int i) {
        this.value = i;
    }

    public int intValue() {
        return this.value;
    }
}
