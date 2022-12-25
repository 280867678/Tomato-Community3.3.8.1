package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class NobilityEntity implements Parcelable {
    public static final Parcelable.Creator<NobilityEntity> CREATOR = new Parcelable.Creator<NobilityEntity>() { // from class: com.tomatolive.library.model.NobilityEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public NobilityEntity mo6578createFromParcel(Parcel parcel) {
            return new NobilityEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public NobilityEntity[] mo6579newArray(int i) {
            return new NobilityEntity[i];
        }
    };
    public AnchorEntity anchorInfoItem;
    public String name;
    public String openPrice;
    public String openType;
    public String rebatePrice;
    public String renewPrice;
    public String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isOpen() {
        return TextUtils.equals(this.openType, "1");
    }

    public boolean isRenew() {
        return TextUtils.equals(this.openType, "2");
    }

    public boolean isBanBuy() {
        return TextUtils.equals(this.openType, "3");
    }

    public String getOpenPrice() {
        return this.openPrice;
    }

    public String getRenewPrice() {
        return this.renewPrice;
    }

    public String getRebatePrice() {
        return this.rebatePrice;
    }

    public NobilityEntity() {
        this.openType = "";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.type);
        parcel.writeString(this.openPrice);
        parcel.writeString(this.renewPrice);
        parcel.writeString(this.rebatePrice);
        parcel.writeString(this.openType);
        parcel.writeParcelable(this.anchorInfoItem, i);
    }

    protected NobilityEntity(Parcel parcel) {
        this.openType = "";
        this.name = parcel.readString();
        this.type = parcel.readString();
        this.openPrice = parcel.readString();
        this.renewPrice = parcel.readString();
        this.rebatePrice = parcel.readString();
        this.openType = parcel.readString();
        this.anchorInfoItem = (AnchorEntity) parcel.readParcelable(AnchorEntity.class.getClassLoader());
    }
}
