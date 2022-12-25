package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* loaded from: classes3.dex */
public class LeftAnimEntity {
    public String avatar;
    public int leftAnimType;
    public String tips;
    public String userId;
    public String userName = "";
    public String guardType = "0";
    public int nobilityType = -1;

    public boolean isLocalAnim() {
        return TextUtils.equals(this.userId, UserInfoManager.getInstance().getUserId());
    }
}
