package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class WeekStarRankingEntity implements MultiItemEntity, Parcelable {
    public static final Parcelable.Creator<WeekStarRankingEntity> CREATOR = new Parcelable.Creator<WeekStarRankingEntity>() { // from class: com.tomatolive.library.model.WeekStarRankingEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public WeekStarRankingEntity mo6598createFromParcel(Parcel parcel) {
            return new WeekStarRankingEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public WeekStarRankingEntity[] mo6599newArray(int i) {
            return new WeekStarRankingEntity[i];
        }
    };
    public List<MenuEntity> anchorRewardList;
    public List<GiftDownloadItemEntity> giftLabelList;
    public int itemType;
    public List<WeekStarAnchorEntity> shineList;
    public List<MenuEntity> userRewardList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.itemType;
    }

    public WeekStarRankingEntity() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.itemType);
        parcel.writeTypedList(this.shineList);
        parcel.writeList(this.anchorRewardList);
        parcel.writeList(this.userRewardList);
        parcel.writeList(this.giftLabelList);
    }

    protected WeekStarRankingEntity(Parcel parcel) {
        this.itemType = parcel.readInt();
        this.shineList = parcel.createTypedArrayList(WeekStarAnchorEntity.CREATOR);
        this.anchorRewardList = new ArrayList();
        parcel.readList(this.anchorRewardList, MenuEntity.class.getClassLoader());
        this.userRewardList = new ArrayList();
        parcel.readList(this.userRewardList, MenuEntity.class.getClassLoader());
        this.giftLabelList = new ArrayList();
        parcel.readList(this.giftLabelList, GiftDownloadItemEntity.class.getClassLoader());
    }
}
