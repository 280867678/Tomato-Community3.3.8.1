package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class CircleList implements Parcelable {
    public static final Parcelable.Creator<CircleList> CREATOR = new Parcelable.Creator<CircleList>() { // from class: com.one.tomato.entity.CircleList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public CircleList mo6357createFromParcel(Parcel parcel) {
            return new CircleList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public CircleList[] mo6358newArray(int i) {
            return new CircleList[i];
        }
    };
    private int blackFlag;
    private String brief;
    private int followFlag;

    /* renamed from: id */
    private int f1702id;
    private String logo;
    private String name;
    private int official;
    private int silenceFlag;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CircleList(int i) {
        this.f1702id = i;
    }

    public CircleList() {
    }

    public CircleList(int i, String str) {
        this.f1702id = i;
        this.name = str;
    }

    public CircleList(int i, String str, String str2, String str3, int i2) {
        this.f1702id = i;
        this.name = str;
        this.logo = str2;
        this.brief = str3;
        this.followFlag = i2;
    }

    public int getId() {
        return this.f1702id;
    }

    public void setId(int i) {
        this.f1702id = i;
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

    public int getOfficial() {
        return this.official;
    }

    public void setOfficial(int i) {
        this.official = i;
    }

    public boolean equals(Object obj) {
        CircleList circleList;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof CircleList) && (this == (circleList = (CircleList) obj) || this.f1702id == circleList.getId());
    }

    public int hashCode() {
        return this.f1702id;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1702id);
        parcel.writeString(this.name);
        parcel.writeString(this.logo);
        parcel.writeString(this.brief);
        parcel.writeInt(this.followFlag);
        parcel.writeInt(this.silenceFlag);
        parcel.writeInt(this.blackFlag);
    }

    protected CircleList(Parcel parcel) {
        this.f1702id = parcel.readInt();
        this.name = parcel.readString();
        this.logo = parcel.readString();
        this.brief = parcel.readString();
        this.followFlag = parcel.readInt();
        this.silenceFlag = parcel.readInt();
        this.blackFlag = parcel.readInt();
    }
}
