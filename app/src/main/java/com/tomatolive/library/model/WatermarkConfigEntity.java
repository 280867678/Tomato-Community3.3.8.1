package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class WatermarkConfigEntity implements Parcelable {
    public static final Parcelable.Creator<WatermarkConfigEntity> CREATOR = new Parcelable.Creator<WatermarkConfigEntity>() { // from class: com.tomatolive.library.model.WatermarkConfigEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public WatermarkConfigEntity mo6594createFromParcel(Parcel parcel) {
            return new WatermarkConfigEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public WatermarkConfigEntity[] mo6595newArray(int i) {
            return new WatermarkConfigEntity[i];
        }
    };
    public String dateState;
    public String downloadUrl;
    public String liveIdState;
    public String logoUrl;
    public String platform;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isEnableLiveRoom() {
        return TextUtils.equals(this.liveIdState, "1");
    }

    public boolean isEnableDate() {
        return TextUtils.equals(this.dateState, "1");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.dateState);
        parcel.writeString(this.liveIdState);
        parcel.writeString(this.platform);
        parcel.writeString(this.logoUrl);
        parcel.writeString(this.downloadUrl);
    }

    public WatermarkConfigEntity() {
        this.platform = "";
    }

    protected WatermarkConfigEntity(Parcel parcel) {
        this.platform = "";
        this.dateState = parcel.readString();
        this.liveIdState = parcel.readString();
        this.platform = parcel.readString();
        this.logoUrl = parcel.readString();
        this.downloadUrl = parcel.readString();
    }
}
