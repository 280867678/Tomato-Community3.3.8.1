package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class MyCarEntity extends CarEntity {
    public String isUsed;
    public String remainDay;
    public String uniqueId;
    public String userId;

    public boolean isEquipage() {
        return TextUtils.equals(this.isUsed, "1");
    }
}
