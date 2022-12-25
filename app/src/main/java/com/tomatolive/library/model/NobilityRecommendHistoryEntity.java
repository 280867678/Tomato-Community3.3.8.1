package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class NobilityRecommendHistoryEntity {
    public String anchorName;
    public String anonymous;
    public String createTime;
    public String endTime;
    public String startTime;
    public String status = "1";

    public boolean isAnonymous() {
        return TextUtils.equals(this.anonymous, "1");
    }
}
