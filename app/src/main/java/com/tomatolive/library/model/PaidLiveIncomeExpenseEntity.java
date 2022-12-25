package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import com.tomatolive.library.R$drawable;

/* loaded from: classes3.dex */
public class PaidLiveIncomeExpenseEntity extends IncomeEntity {
    private String ticketPracticalPrice = "0";
    private String avatar = "";
    private String topic = "";
    private String chargeType = "";
    private String createTime = "";
    private String beginTime = "";
    private String liveCount = "";
    private String watchMemberCount = "";
    private String liveTime = "";
    private String maxPopularity = "";
    private String endTime = "";

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getAnchorIncomePrice() {
        return this.anchorIncomePrice;
    }

    public String getTicketPracticalPrice() {
        return this.ticketPracticalPrice;
    }

    public void setTicketPracticalPrice(String str) {
        this.ticketPracticalPrice = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String str) {
        this.topic = str;
    }

    public String getChargeType() {
        return this.chargeType;
    }

    public void setChargeType(String str) {
        this.chargeType = str;
    }

    public String getLiveCount() {
        return this.liveCount;
    }

    public void setLiveCount(String str) {
        this.liveCount = str;
    }

    public String getWatchMemberCount() {
        return this.watchMemberCount;
    }

    public void setWatchMemberCount(String str) {
        this.watchMemberCount = str;
    }

    public String getLiveTime() {
        return this.liveTime;
    }

    public void setLiveTime(String str) {
        this.liveTime = str;
    }

    public String getMaxPopularity() {
        return this.maxPopularity;
    }

    public void setMaxPopularity(String str) {
        this.maxPopularity = str;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String str) {
        this.beginTime = str;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return TextUtils.equals(getChargeType(), "1") ? R$drawable.fq_ic_pay_live_room_tickets : R$drawable.fq_ic_pay_live_room_pay;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return this.avatar;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        return getHtmlSpanned(this.topic);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? this.price : this.ticketPracticalPrice;
    }
}
