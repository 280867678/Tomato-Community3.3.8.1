package com.tomatolive.library.model;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class GiftBatchItemEntity implements Serializable {
    public int active_time;
    public String animalUrl;
    public String desc;
    public int duration;
    public int num;

    public GiftBatchItemEntity() {
    }

    public GiftBatchItemEntity(String str, int i) {
        this.desc = str;
        this.num = i;
    }

    public String toString() {
        return "GiftBatchItemEntity{desc='" + this.desc + "', num=" + this.num + '}';
    }
}
