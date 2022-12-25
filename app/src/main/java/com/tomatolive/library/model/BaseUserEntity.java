package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class BaseUserEntity implements Parcelable {
    public static final Parcelable.Creator<BaseUserEntity> CREATOR = new Parcelable.Creator<BaseUserEntity>() { // from class: com.tomatolive.library.model.BaseUserEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public BaseUserEntity mo6546createFromParcel(Parcel parcel) {
            return new BaseUserEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public BaseUserEntity[] mo6547newArray(int i) {
            return new BaseUserEntity[i];
        }
    };
    public String appId;
    public String avatar;
    public String enterLivePermission;
    public String exp;
    public String expGrade;
    public String guardType;
    public String isEnterHide;
    public String isLiving;
    public String isRankHide;
    public String liveId;
    public String liveStatus;
    @SerializedName("userName")
    public String name;
    public String nextGradeExp;
    public String nickname;
    public int nobilityType;
    public String openId;
    public String role;
    public String sex;
    public String signature;
    public String userId;
    public String userRole;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BaseUserEntity() {
        this.userId = "";
        this.avatar = "";
        this.isRankHide = "";
        this.isEnterHide = "";
        this.enterLivePermission = "";
        this.exp = "";
        this.expGrade = "";
        this.nextGradeExp = "";
        this.guardType = "";
        this.nobilityType = 0;
        this.isLiving = "0";
        this.liveStatus = "0";
        this.openId = "";
        this.appId = "";
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public boolean isRankHideBoolean() {
        return TextUtils.equals(this.isRankHide, String.valueOf(1));
    }

    public boolean isLiveEnterHideBoolean() {
        return TextUtils.equals(this.isEnterHide, String.valueOf(1));
    }

    public boolean isEnterLivePermissionBoolean() {
        if (TextUtils.isEmpty(this.enterLivePermission)) {
            return true;
        }
        return TextUtils.equals(this.enterLivePermission, "1");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.nickname);
        parcel.writeString(this.name);
        parcel.writeString(this.avatar);
        parcel.writeString(this.sex);
        parcel.writeString(this.role);
        parcel.writeString(this.signature);
        parcel.writeString(this.userRole);
        parcel.writeString(this.isRankHide);
        parcel.writeString(this.isEnterHide);
        parcel.writeString(this.enterLivePermission);
        parcel.writeString(this.exp);
        parcel.writeString(this.expGrade);
        parcel.writeString(this.nextGradeExp);
        parcel.writeString(this.guardType);
        parcel.writeInt(this.nobilityType);
        parcel.writeString(this.liveId);
        parcel.writeString(this.isLiving);
        parcel.writeString(this.liveStatus);
        parcel.writeString(this.openId);
        parcel.writeString(this.appId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseUserEntity(Parcel parcel) {
        this.userId = "";
        this.avatar = "";
        this.isRankHide = "";
        this.isEnterHide = "";
        this.enterLivePermission = "";
        this.exp = "";
        this.expGrade = "";
        this.nextGradeExp = "";
        this.guardType = "";
        this.nobilityType = 0;
        this.isLiving = "0";
        this.liveStatus = "0";
        this.openId = "";
        this.appId = "";
        this.userId = parcel.readString();
        this.nickname = parcel.readString();
        this.name = parcel.readString();
        this.avatar = parcel.readString();
        this.sex = parcel.readString();
        this.role = parcel.readString();
        this.signature = parcel.readString();
        this.userRole = parcel.readString();
        this.isRankHide = parcel.readString();
        this.isEnterHide = parcel.readString();
        this.enterLivePermission = parcel.readString();
        this.exp = parcel.readString();
        this.expGrade = parcel.readString();
        this.nextGradeExp = parcel.readString();
        this.guardType = parcel.readString();
        this.nobilityType = parcel.readInt();
        this.liveId = parcel.readString();
        this.isLiving = parcel.readString();
        this.liveStatus = parcel.readString();
        this.openId = parcel.readString();
        this.appId = parcel.readString();
    }
}
