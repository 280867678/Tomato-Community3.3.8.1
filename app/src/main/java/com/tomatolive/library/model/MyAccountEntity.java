package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class MyAccountEntity implements Parcelable {
    public static final Parcelable.Creator<MyAccountEntity> CREATOR = new Parcelable.Creator<MyAccountEntity>() { // from class: com.tomatolive.library.model.MyAccountEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public MyAccountEntity mo6576createFromParcel(Parcel parcel) {
            return new MyAccountEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public MyAccountEntity[] mo6577newArray(int i) {
            return new MyAccountEntity[i];
        }
    };
    public String nobilityGoldFrozenStatus;
    public String nobilityPrice;
    public String price;
    public String score;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isFrozen() {
        return TextUtils.equals(this.nobilityGoldFrozenStatus, "1");
    }

    public String getAccountBalance() {
        if (isFrozen()) {
            return this.price;
        }
        return String.valueOf(NumberUtils.string2long(this.price) + NumberUtils.string2long(this.nobilityPrice));
    }

    public MyAccountEntity() {
        this.score = "0";
        this.price = "0";
        this.nobilityPrice = "0";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.score);
        parcel.writeString(this.nobilityGoldFrozenStatus);
        parcel.writeString(this.price);
        parcel.writeString(this.nobilityPrice);
    }

    protected MyAccountEntity(Parcel parcel) {
        this.score = "0";
        this.price = "0";
        this.nobilityPrice = "0";
        this.score = parcel.readString();
        this.nobilityGoldFrozenStatus = parcel.readString();
        this.price = parcel.readString();
        this.nobilityPrice = parcel.readString();
    }
}
