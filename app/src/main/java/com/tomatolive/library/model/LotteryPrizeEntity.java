package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class LotteryPrizeEntity implements Parcelable {
    public static final Parcelable.Creator<LotteryPrizeEntity> CREATOR = new Parcelable.Creator<LotteryPrizeEntity>() { // from class: com.tomatolive.library.model.LotteryPrizeEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LotteryPrizeEntity mo6570createFromParcel(Parcel parcel) {
            return new LotteryPrizeEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LotteryPrizeEntity[] mo6571newArray(int i) {
            return new LotteryPrizeEntity[i];
        }
    };
    public String broadcastScope;
    public int code;
    public String color;
    public String isMaxProp;
    public String propName;
    public String propNum;
    public String propPrice;
    public String propUrl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getPropNumStr() {
        return "x" + this.propNum;
    }

    public String getPropGoldStr() {
        return AppUtils.formatDisplayPrice(String.valueOf(NumberUtils.string2long(this.propPrice) * NumberUtils.string2long(this.propNum)), true);
    }

    public boolean isMaxPriceProp() {
        return TextUtils.equals(this.isMaxProp, "1");
    }

    public boolean isBroadcastScopeRoom() {
        return TextUtils.equals(this.broadcastScope, "2");
    }

    public boolean isBroadcastScopePlatform() {
        return TextUtils.equals(this.broadcastScope, "3");
    }

    public LotteryPrizeEntity() {
        this.propUrl = "";
        this.propNum = "";
        this.propName = "";
        this.propPrice = "0";
        this.color = "";
        this.isMaxProp = "";
    }

    public String toString() {
        return "LotteryPrizeEntity{code=" + this.code + ", propCount='" + this.propNum + "', propName='" + this.propName + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.code);
        parcel.writeString(this.broadcastScope);
        parcel.writeString(this.propUrl);
        parcel.writeString(this.propNum);
        parcel.writeString(this.propName);
        parcel.writeString(this.propPrice);
        parcel.writeString(this.color);
        parcel.writeString(this.isMaxProp);
    }

    protected LotteryPrizeEntity(Parcel parcel) {
        this.propUrl = "";
        this.propNum = "";
        this.propName = "";
        this.propPrice = "0";
        this.color = "";
        this.isMaxProp = "";
        this.code = parcel.readInt();
        this.broadcastScope = parcel.readString();
        this.propUrl = parcel.readString();
        this.propNum = parcel.readString();
        this.propName = parcel.readString();
        this.propPrice = parcel.readString();
        this.color = parcel.readString();
        this.isMaxProp = parcel.readString();
    }
}
