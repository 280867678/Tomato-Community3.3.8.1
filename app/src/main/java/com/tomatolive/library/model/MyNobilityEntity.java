package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class MyNobilityEntity {
    public String isEnterHide;
    public String isRankHide;
    public String nickname;
    public String nobilityType;
    public String endTime = "";
    public String recommendNumber = "0";

    public boolean isEnterHideBoolean() {
        return TextUtils.equals(String.valueOf(1), this.isEnterHide);
    }

    public boolean isRankHideBoolean() {
        return TextUtils.equals(String.valueOf(1), this.isRankHide);
    }
}
