package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class LotteryTurntableInfoEntity implements Parcelable {
    public static final Parcelable.Creator<LotteryTurntableInfoEntity> CREATOR = new Parcelable.Creator<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.model.LotteryTurntableInfoEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LotteryTurntableInfoEntity mo6574createFromParcel(Parcel parcel) {
            return new LotteryTurntableInfoEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LotteryTurntableInfoEntity[] mo6575newArray(int i) {
            return new LotteryTurntableInfoEntity[i];
        }
    };
    public LotteryBoomDetailEntity boomDetail;
    public int lotteryTicketNum;
    public int luckGiftGold;
    public int luckValue;
    public Map<String, LotteryPrizeEntity> turntableAward;
    public TurntableMode turntableInfoData;
    public String version;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isBoomStatus() {
        LotteryBoomDetailEntity lotteryBoomDetailEntity = this.boomDetail;
        return lotteryBoomDetailEntity != null && lotteryBoomDetailEntity.boomStatus > -1 && lotteryBoomDetailEntity.boomRemainTime > 0;
    }

    public String toString() {
        return "LotteryTurntableInfoEntity{boomDetail=" + this.boomDetail + ", luckValue='" + this.luckValue + "', version='" + this.version + "', turntableAward=" + this.turntableAward + ", turntableInfoData=" + this.turntableInfoData + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.boomDetail, i);
        parcel.writeInt(this.luckValue);
        parcel.writeInt(this.lotteryTicketNum);
        parcel.writeInt(this.luckGiftGold);
        parcel.writeString(this.version);
        Map<String, LotteryPrizeEntity> map = this.turntableAward;
        parcel.writeInt(map == null ? 0 : map.size());
        Map<String, LotteryPrizeEntity> map2 = this.turntableAward;
        if (map2 != null) {
            for (Map.Entry<String, LotteryPrizeEntity> entry : map2.entrySet()) {
                parcel.writeString(entry.getKey());
                parcel.writeParcelable(entry.getValue(), i);
            }
        }
        parcel.writeParcelable(this.turntableInfoData, i);
    }

    public LotteryTurntableInfoEntity() {
        this.luckGiftGold = -1;
    }

    protected LotteryTurntableInfoEntity(Parcel parcel) {
        this.luckGiftGold = -1;
        this.boomDetail = (LotteryBoomDetailEntity) parcel.readParcelable(LotteryBoomDetailEntity.class.getClassLoader());
        this.luckValue = parcel.readInt();
        this.lotteryTicketNum = parcel.readInt();
        this.luckGiftGold = parcel.readInt();
        this.version = parcel.readString();
        int readInt = parcel.readInt();
        this.turntableAward = new HashMap(readInt);
        for (int i = 0; i < readInt; i++) {
            this.turntableAward.put(parcel.readString(), (LotteryPrizeEntity) parcel.readParcelable(LotteryPrizeEntity.class.getClassLoader()));
        }
        this.turntableInfoData = (TurntableMode) parcel.readParcelable(TurntableMode.class.getClassLoader());
    }
}
