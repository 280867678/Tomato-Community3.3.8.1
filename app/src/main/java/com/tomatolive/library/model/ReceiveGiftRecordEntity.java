package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class ReceiveGiftRecordEntity extends AnchorEntity {
    public String createTime;
    public String description;
    public String liveAdminStatus = "";

    public String getRole() {
        return TextUtils.equals(this.liveAdminStatus, "1") ? "5" : this.role;
    }
}
