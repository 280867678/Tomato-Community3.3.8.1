package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class PKConfigEntity {
    public String pkAccept;
    public String pkLevelLimit;

    public boolean isPkAccept() {
        return TextUtils.equals("1", this.pkAccept);
    }
}
