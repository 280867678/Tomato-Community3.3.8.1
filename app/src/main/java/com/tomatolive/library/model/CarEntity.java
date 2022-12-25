package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class CarEntity implements Serializable {
    public String animalUrl;
    public String brief;

    /* renamed from: id */
    public String f5836id;
    public String imgUrl;
    public String isPermission;
    public String monthPrice;
    public String name;
    public String permissionUserList;
    public String weekPrice;
    public String versionCode = "0";
    public String description = "";

    public boolean isPublicCar() {
        if (!isPrivatePermission()) {
            return true;
        }
        if (TextUtils.isEmpty(this.permissionUserList)) {
            return false;
        }
        if (!this.permissionUserList.contains(",")) {
            return TextUtils.equals(this.permissionUserList, UserInfoManager.getInstance().getAppOpenId());
        }
        boolean z = false;
        for (String str : TextUtils.split(this.permissionUserList, ",")) {
            if (TextUtils.equals(str, UserInfoManager.getInstance().getAppOpenId())) {
                z = true;
            }
        }
        return z;
    }

    public boolean isPrivatePermission() {
        return TextUtils.equals(this.isPermission, "1");
    }

    public boolean isWeekStarCar() {
        return TextUtils.equals(this.isPermission, "2");
    }

    public String getWeekPrice() {
        return this.weekPrice;
    }

    public String getMonthPrice() {
        return this.monthPrice;
    }
}
