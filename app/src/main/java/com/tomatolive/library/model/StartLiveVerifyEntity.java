package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tomatolive.library.utils.AppUtils;

/* loaded from: classes3.dex */
public class StartLiveVerifyEntity implements Parcelable {
    public static final Parcelable.Creator<StartLiveVerifyEntity> CREATOR = new Parcelable.Creator<StartLiveVerifyEntity>() { // from class: com.tomatolive.library.model.StartLiveVerifyEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public StartLiveVerifyEntity mo6584createFromParcel(Parcel parcel) {
            return new StartLiveVerifyEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public StartLiveVerifyEntity[] mo6585newArray(int i) {
            return new StartLiveVerifyEntity[i];
        }
    };
    public int anchorLevelLimit;
    public int anchorPercent;
    public long maxMinutePrice;
    public long maxTicketPrice;
    public long minMinutePrice;
    public long minTicketPrice;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getTicketPriceIntervalTips() {
        if (this.minTicketPrice == 0 || this.maxTicketPrice == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String formatDisplayPrice = AppUtils.formatDisplayPrice(String.valueOf(this.minTicketPrice), false);
        String formatDisplayPrice2 = AppUtils.formatDisplayPrice(String.valueOf(this.maxTicketPrice), false);
        sb.append("(");
        sb.append(formatDisplayPrice);
        sb.append("~");
        sb.append(formatDisplayPrice2);
        sb.append(")");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.minTicketPrice);
        parcel.writeLong(this.maxTicketPrice);
        parcel.writeLong(this.minMinutePrice);
        parcel.writeLong(this.maxMinutePrice);
        parcel.writeInt(this.anchorPercent);
        parcel.writeInt(this.anchorLevelLimit);
    }

    public StartLiveVerifyEntity() {
        this.minTicketPrice = 0L;
        this.maxTicketPrice = 0L;
    }

    protected StartLiveVerifyEntity(Parcel parcel) {
        this.minTicketPrice = 0L;
        this.maxTicketPrice = 0L;
        this.minTicketPrice = parcel.readLong();
        this.maxTicketPrice = parcel.readLong();
        this.minMinutePrice = parcel.readLong();
        this.maxMinutePrice = parcel.readLong();
        this.anchorPercent = parcel.readInt();
        this.anchorLevelLimit = parcel.readInt();
    }
}
