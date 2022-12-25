package com.tomatolive.library.model;

import android.text.TextUtils;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class BannedEntity implements Serializable {
    public String avatar;
    public String clearTime;
    public String name;
    public String userId;
    public String managerStatus = "1";
    public String banPostStatus = "1";
    public String exp = "";
    public String expGrade = "";
    public String duration = "";
    public String lastEnterTime = "";
    public String createTime = "";
    public int count = 0;

    public boolean isBanned() {
        return TextUtils.equals("1", this.banPostStatus);
    }

    public boolean isHouseManager() {
        return TextUtils.equals("1", this.managerStatus);
    }
}
