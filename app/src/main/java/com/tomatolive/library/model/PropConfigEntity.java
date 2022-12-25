package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class PropConfigEntity implements Parcelable {
    public static final Parcelable.Creator<PropConfigEntity> CREATOR = new Parcelable.Creator<PropConfigEntity>() { // from class: com.tomatolive.library.model.PropConfigEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public PropConfigEntity mo6580createFromParcel(Parcel parcel) {
            return new PropConfigEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public PropConfigEntity[] mo6581newArray(int i) {
            return new PropConfigEntity[i];
        }
    };
    public String createTime;
    public String propCount;
    public String propGold;
    public String propId;
    public String propName;
    public String propUrl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PropConfigEntity() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.propId);
        parcel.writeString(this.propUrl);
        parcel.writeString(this.propCount);
        parcel.writeString(this.propName);
        parcel.writeString(this.propGold);
        parcel.writeString(this.createTime);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PropConfigEntity(Parcel parcel) {
        this.propId = parcel.readString();
        this.propUrl = parcel.readString();
        this.propCount = parcel.readString();
        this.propName = parcel.readString();
        this.propGold = parcel.readString();
        this.createTime = parcel.readString();
    }
}
