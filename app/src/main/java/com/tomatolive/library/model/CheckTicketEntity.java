package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* loaded from: classes3.dex */
public class CheckTicketEntity {
    public String anchorAppId;
    public String chargeType;
    public String isPrivateAnchor;
    public String needBuyTicket;
    public String privateAnchorPrice;
    public String ticketPrice;
    public String pullStreamUrl = "";
    public String liveCoverUrl = "";
    public String avatar = "";
    public String liveStatus = "";
    public String startTotalTime = "";
    public String historyLiveEvaluation = "";
    public String payLiveCount = "";

    public boolean isNeedBuyTicket() {
        return TextUtils.equals("1", this.needBuyTicket);
    }

    public String getLiveCoverUrl() {
        return TextUtils.isEmpty(this.liveCoverUrl) ? this.avatar : this.liveCoverUrl;
    }

    public String getPayLivePrice() {
        if (TextUtils.equals(this.chargeType, "1") || isTimePayLive()) {
            return this.ticketPrice;
        }
        return isPrivateAnchorByAppId() ? this.privateAnchorPrice : "0";
    }

    public boolean isTimePayLive() {
        return TextUtils.equals(this.chargeType, "2");
    }

    public boolean isPrivateAnchorByAppId() {
        return TextUtils.equals(this.isPrivateAnchor, "1") && !TextUtils.equals(this.anchorAppId, UserInfoManager.getInstance().getAppId());
    }
}
