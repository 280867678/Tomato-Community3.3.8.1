package com.tomatolive.library.model.cache;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class VersionCacheEntity implements Parcelable {
    public static final Parcelable.Creator<VersionCacheEntity> CREATOR = new Parcelable.Creator<VersionCacheEntity>() { // from class: com.tomatolive.library.model.cache.VersionCacheEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public VersionCacheEntity mo6604createFromParcel(Parcel parcel) {
            return new VersionCacheEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public VersionCacheEntity[] mo6605newArray(int i) {
            return new VersionCacheEntity[i];
        }
    };
    private long createTime;

    /* renamed from: v */
    private String f5849v;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getVersion() {
        return this.f5849v;
    }

    public void setVersion(String str) {
        this.f5849v = str;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public String toString() {
        return "VersionCacheEntity{v='" + this.f5849v + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f5849v);
        parcel.writeLong(this.createTime);
    }

    public VersionCacheEntity() {
    }

    protected VersionCacheEntity(Parcel parcel) {
        this.f5849v = parcel.readString();
        this.createTime = parcel.readLong();
    }
}
