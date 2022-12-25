package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.blankj.utilcode.util.RegexUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* loaded from: classes3.dex */
public class ComponentsEntity implements Parcelable {
    public static final Parcelable.Creator<ComponentsEntity> CREATOR = new Parcelable.Creator<ComponentsEntity>() { // from class: com.tomatolive.library.model.ComponentsEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public ComponentsEntity mo6548createFromParcel(Parcel parcel) {
            return new ComponentsEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public ComponentsEntity[] mo6549newArray(int i) {
            return new ComponentsEntity[i];
        }
    };
    public int callType;
    public String gameId;
    public int height;

    /* renamed from: id */
    public String f5837id;
    public String imgUrl;
    public String isRecommend;
    public boolean isRedDot;
    public String name;
    public String targetUrl;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ComponentsEntity() {
        this.gameId = "";
        this.width = 0;
        this.height = 0;
        this.isRedDot = false;
    }

    public boolean isCacheLotteryComponents() {
        return this.callType == 2 && TextUtils.equals(this.gameId, "1");
    }

    public boolean isRecommendComponents() {
        return TextUtils.equals("1", this.isRecommend);
    }

    public double getHeightProportion() {
        try {
            double doubleValue = new BigDecimal(Double.toString(this.width)).divide(new BigDecimal(Double.toString(this.height)), 6, RoundingMode.HALF_UP).doubleValue();
            if (doubleValue > 0.0d) {
                return doubleValue;
            }
            return 1.0d;
        } catch (Exception unused) {
            return 1.0d;
        }
    }

    public boolean isCorrectLink() {
        return !TextUtils.isEmpty(this.targetUrl) && RegexUtils.isURL(this.targetUrl);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f5837id);
        parcel.writeString(this.name);
        parcel.writeString(this.imgUrl);
        parcel.writeInt(this.callType);
        parcel.writeString(this.targetUrl);
        parcel.writeString(this.gameId);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeString(this.isRecommend);
        parcel.writeByte(this.isRedDot ? (byte) 1 : (byte) 0);
    }

    protected ComponentsEntity(Parcel parcel) {
        this.gameId = "";
        boolean z = false;
        this.width = 0;
        this.height = 0;
        this.isRedDot = false;
        this.f5837id = parcel.readString();
        this.name = parcel.readString();
        this.imgUrl = parcel.readString();
        this.callType = parcel.readInt();
        this.targetUrl = parcel.readString();
        this.gameId = parcel.readString();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.isRecommend = parcel.readString();
        this.isRedDot = parcel.readByte() != 0 ? true : z;
    }
}
