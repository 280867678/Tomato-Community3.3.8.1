package com.tomatolive.library.model.cache;

import android.os.Parcel;
import android.os.Parcelable;
import com.tomatolive.library.model.BannerEntity;
import java.util.List;

/* loaded from: classes3.dex */
public class BannerCacheEntity implements Parcelable {
    public static final Parcelable.Creator<BannerCacheEntity> CREATOR = new Parcelable.Creator<BannerCacheEntity>() { // from class: com.tomatolive.library.model.cache.BannerCacheEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public BannerCacheEntity mo6600createFromParcel(Parcel parcel) {
            return new BannerCacheEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public BannerCacheEntity[] mo6601newArray(int i) {
            return new BannerCacheEntity[i];
        }
    };
    private List<BannerEntity> dataList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<BannerEntity> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<BannerEntity> list) {
        this.dataList = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.dataList);
    }

    public BannerCacheEntity() {
    }

    protected BannerCacheEntity(Parcel parcel) {
        this.dataList = parcel.createTypedArrayList(BannerEntity.CREATOR);
    }
}
