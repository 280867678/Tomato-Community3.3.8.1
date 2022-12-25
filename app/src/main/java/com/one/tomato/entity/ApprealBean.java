package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ApprealBean implements Parcelable {
    public static final Parcelable.Creator<ApprealBean> CREATOR = new Parcelable.Creator<ApprealBean>() { // from class: com.one.tomato.entity.ApprealBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public ApprealBean mo6353createFromParcel(Parcel parcel) {
            return new ApprealBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public ApprealBean[] mo6354newArray(int i) {
            return new ApprealBean[i];
        }
    };
    private String account;
    private int complaintType;
    private int countryCode;
    private String createTime;
    private String expireTime;
    private String giftToAnchor;

    /* renamed from: id */
    private int f1693id;
    private String identityInfo;
    private String invited;
    private int memberId;
    private String phone;
    private String phoneType;
    private String published;
    private String reason;
    private String rechargeInfo;
    private String registTime;
    private String rejectReason;
    private String remark;
    private int status;
    private String updateTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ApprealBean(Parcel parcel) {
        this.f1693id = parcel.readInt();
        this.memberId = parcel.readInt();
        this.account = parcel.readString();
        this.complaintType = parcel.readInt();
        this.countryCode = parcel.readInt();
        this.phone = parcel.readString();
        this.status = parcel.readInt();
        this.reason = parcel.readString();
        this.registTime = parcel.readString();
        this.phoneType = parcel.readString();
        this.rechargeInfo = parcel.readString();
        this.identityInfo = parcel.readString();
        this.giftToAnchor = parcel.readString();
        this.published = parcel.readString();
        this.invited = parcel.readString();
        this.rejectReason = parcel.readString();
        this.remark = parcel.readString();
        this.createTime = parcel.readString();
        this.expireTime = parcel.readString();
        this.updateTime = parcel.readString();
    }

    public int getId() {
        return this.f1693id;
    }

    public void setId(int i) {
        this.f1693id = i;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public int getComplaintType() {
        return this.complaintType;
    }

    public void setComplaintType(int i) {
        this.complaintType = i;
    }

    public int getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(int i) {
        this.countryCode = i;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public String getRegistTime() {
        return this.registTime;
    }

    public void setRegistTime(String str) {
        this.registTime = str;
    }

    public String getPhoneType() {
        return this.phoneType;
    }

    public void setPhoneType(String str) {
        this.phoneType = str;
    }

    public String getRechargeInfo() {
        return this.rechargeInfo;
    }

    public void setRechargeInfo(String str) {
        this.rechargeInfo = str;
    }

    public String getIdentityInfo() {
        return this.identityInfo;
    }

    public void setIdentityInfo(String str) {
        this.identityInfo = str;
    }

    public String getGiftToAnchor() {
        return this.giftToAnchor;
    }

    public void setGiftToAnchor(String str) {
        this.giftToAnchor = str;
    }

    public String getPublished() {
        return this.published;
    }

    public void setPublished(String str) {
        this.published = str;
    }

    public String getInvited() {
        return this.invited;
    }

    public void setInvited(String str) {
        this.invited = str;
    }

    public String getRejectReason() {
        return this.rejectReason;
    }

    public void setRejectReason(String str) {
        this.rejectReason = str;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String str) {
        this.remark = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(String str) {
        this.expireTime = str;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1693id);
        parcel.writeInt(this.memberId);
        parcel.writeString(this.account);
        parcel.writeInt(this.complaintType);
        parcel.writeInt(this.countryCode);
        parcel.writeString(this.phone);
        parcel.writeInt(this.status);
        parcel.writeString(this.reason);
        parcel.writeString(this.registTime);
        parcel.writeString(this.phoneType);
        parcel.writeString(this.rechargeInfo);
        parcel.writeString(this.identityInfo);
        parcel.writeString(this.giftToAnchor);
        parcel.writeString(this.published);
        parcel.writeString(this.invited);
        parcel.writeString(this.rejectReason);
        parcel.writeString(this.remark);
        parcel.writeString(this.createTime);
        parcel.writeString(this.expireTime);
        parcel.writeString(this.updateTime);
    }
}
