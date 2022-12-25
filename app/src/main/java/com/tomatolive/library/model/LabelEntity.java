package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class LabelEntity implements Parcelable {
    public static final Parcelable.Creator<LabelEntity> CREATOR = new Parcelable.Creator<LabelEntity>() { // from class: com.tomatolive.library.model.LabelEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LabelEntity mo6558createFromParcel(Parcel parcel) {
            return new LabelEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LabelEntity[] mo6559newArray(int i) {
            return new LabelEntity[i];
        }
    };

    /* renamed from: id */
    public String f5839id;
    public boolean isSelected;
    public String keyword;
    public String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LabelEntity() {
        this.isSelected = false;
    }

    public LabelEntity(String str) {
        this.isSelected = false;
        this.name = str;
    }

    public LabelEntity(String str, boolean z) {
        this.isSelected = false;
        this.name = str;
        this.isSelected = z;
    }

    public LabelEntity(String str, String str2, boolean z) {
        this.isSelected = false;
        this.f5839id = str;
        this.name = str2;
        this.isSelected = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f5839id);
        parcel.writeString(this.name);
        parcel.writeString(this.keyword);
        parcel.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected LabelEntity(Parcel parcel) {
        boolean z = false;
        this.isSelected = false;
        this.f5839id = parcel.readString();
        this.name = parcel.readString();
        this.keyword = parcel.readString();
        this.isSelected = parcel.readByte() != 0 ? true : z;
    }
}
