package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class LotteryBoomDetailEntity implements Parcelable {
    public static final Parcelable.Creator<LotteryBoomDetailEntity> CREATOR = new Parcelable.Creator<LotteryBoomDetailEntity>() { // from class: com.tomatolive.library.model.LotteryBoomDetailEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LotteryBoomDetailEntity mo6566createFromParcel(Parcel parcel) {
            return new LotteryBoomDetailEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LotteryBoomDetailEntity[] mo6567newArray(int i) {
            return new LotteryBoomDetailEntity[i];
        }
    };
    public int boomMultiple;
    public String boomPropUrl;
    public int boomRemainTime;
    public int boomStatus;
    public int boomTotalTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getBoomMultipleStr() {
        return "x" + this.boomMultiple;
    }

    public String toString() {
        return "LotteryBoomDetailEntity{boomStatus=" + this.boomStatus + ", boomRemainTime=" + this.boomRemainTime + ", boomTotalTime=" + this.boomTotalTime + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.boomStatus);
        parcel.writeInt(this.boomMultiple);
        parcel.writeString(this.boomPropUrl);
        parcel.writeInt(this.boomRemainTime);
        parcel.writeInt(this.boomTotalTime);
    }

    public LotteryBoomDetailEntity() {
        this.boomStatus = -1;
    }

    protected LotteryBoomDetailEntity(Parcel parcel) {
        this.boomStatus = -1;
        this.boomStatus = parcel.readInt();
        this.boomMultiple = parcel.readInt();
        this.boomPropUrl = parcel.readString();
        this.boomRemainTime = parcel.readInt();
        this.boomTotalTime = parcel.readInt();
    }
}
