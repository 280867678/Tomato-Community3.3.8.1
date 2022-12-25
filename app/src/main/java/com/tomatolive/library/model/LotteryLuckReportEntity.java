package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class LotteryLuckReportEntity extends PropConfigEntity {
    public static final Parcelable.Creator<LotteryLuckReportEntity> CREATOR = new Parcelable.Creator<LotteryLuckReportEntity>() { // from class: com.tomatolive.library.model.LotteryLuckReportEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LotteryLuckReportEntity mo6568createFromParcel(Parcel parcel) {
            return new LotteryLuckReportEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LotteryLuckReportEntity[] mo6569newArray(int i) {
            return new LotteryLuckReportEntity[i];
        }
    };
    public String drawWay;
    public String userName;

    @Override // com.tomatolive.library.model.PropConfigEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LotteryLuckReportEntity() {
    }

    @Override // com.tomatolive.library.model.PropConfigEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.userName);
        parcel.writeString(this.drawWay);
    }

    protected LotteryLuckReportEntity(Parcel parcel) {
        super(parcel);
        this.userName = parcel.readString();
        this.drawWay = parcel.readString();
    }

    public String toString() {
        return "LotteryLuckReportEntity{userName='" + this.userName + "', drawWay='" + this.drawWay + "'}";
    }
}
