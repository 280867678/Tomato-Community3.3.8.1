package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class RecommendMemberList implements Parcelable {
    public static final Parcelable.Creator<RecommendMemberList> CREATOR = new Parcelable.Creator<RecommendMemberList>() { // from class: com.one.tomato.entity.RecommendMemberList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public RecommendMemberList mo6365createFromParcel(Parcel parcel) {
            return new RecommendMemberList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public RecommendMemberList[] mo6366newArray(int i) {
            return new RecommendMemberList[i];
        }
    };
    private int blackFlag;
    private String brief;
    private int followFlag;

    /* renamed from: id */
    private int f1732id;
    private String logo;
    private String name;
    private int silenceFlag;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RecommendMemberList(int i, int i2) {
        this.f1732id = i;
        this.followFlag = i2;
    }

    public RecommendMemberList(int i, String str, String str2, String str3, int i2) {
        this.f1732id = i;
        this.name = str;
        this.logo = str2;
        this.brief = str3;
        this.followFlag = i2;
    }

    public int getId() {
        return this.f1732id;
    }

    public void setId(int i) {
        this.f1732id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String str) {
        this.logo = str;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String str) {
        this.brief = str;
    }

    public int getFollowFlag() {
        return this.followFlag;
    }

    public void setFollowFlag(int i) {
        this.followFlag = i;
    }

    public int getSilenceFlag() {
        return this.silenceFlag;
    }

    public void setSilenceFlag(int i) {
        this.silenceFlag = i;
    }

    public int getBlackFlag() {
        return this.blackFlag;
    }

    public void setBlackFlag(int i) {
        this.blackFlag = i;
    }

    public boolean equals(Object obj) {
        RecommendMemberList recommendMemberList;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof RecommendMemberList) && (this == (recommendMemberList = (RecommendMemberList) obj) || this.f1732id == recommendMemberList.getId());
    }

    public int hashCode() {
        return this.f1732id;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1732id);
        parcel.writeString(this.name);
        parcel.writeString(this.logo);
        parcel.writeString(this.brief);
        parcel.writeInt(this.followFlag);
        parcel.writeInt(this.silenceFlag);
        parcel.writeInt(this.blackFlag);
    }

    protected RecommendMemberList(Parcel parcel) {
        this.f1732id = parcel.readInt();
        this.name = parcel.readString();
        this.logo = parcel.readString();
        this.brief = parcel.readString();
        this.followFlag = parcel.readInt();
        this.silenceFlag = parcel.readInt();
        this.blackFlag = parcel.readInt();
    }
}
