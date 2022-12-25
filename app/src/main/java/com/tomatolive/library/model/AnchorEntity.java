package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tomatolive.library.utils.AppUtils;

/* loaded from: classes3.dex */
public class AnchorEntity extends BaseUserEntity implements Parcelable {
    public static final Parcelable.Creator<AnchorEntity> CREATOR = new Parcelable.Creator<AnchorEntity>() { // from class: com.tomatolive.library.model.AnchorEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public AnchorEntity mo6542createFromParcel(Parcel parcel) {
            return new AnchorEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public AnchorEntity[] mo6543newArray(int i) {
            return new AnchorEntity[i];
        }
    };
    public String anchor_id;
    public String contribution;
    public String count;
    public String expend;
    public String followAnchorCount;
    public String followerCount;

    /* renamed from: fp */
    public String f5827fp;
    public String giftIncomePrice;
    public String income;
    public int isChecked;
    public int isFrozen;
    public String liveCount;
    public String liveCoverUrl;
    public String phone;
    public String pkStatus;
    public String pullStreamUrl;
    public String streamName;
    public String tag;
    public String topic;

    @Override // com.tomatolive.library.model.BaseUserEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isAttention() {
        return AppUtils.isAttentionAnchor(this.userId);
    }

    public boolean isFrozenFlag() {
        return this.isFrozen == 1;
    }

    public String getAnchorIncomePrice() {
        return AppUtils.formatDisplayPrice(this.giftIncomePrice, true);
    }

    public boolean isInvitePK() {
        return TextUtils.equals("1", this.pkStatus);
    }

    public AnchorEntity() {
        this.followerCount = "0";
        this.followAnchorCount = "0";
        this.count = "0";
        this.income = "0";
        this.expend = "0";
        this.contribution = "";
        this.streamName = "";
        this.liveCoverUrl = "";
        this.liveCount = "";
        this.tag = "";
        this.topic = "";
        this.pullStreamUrl = "";
    }

    public String toString() {
        return "AnchorEntity{, isChecked=" + this.isChecked + ", isFrozen=" + this.isFrozen + '}';
    }

    @Override // com.tomatolive.library.model.BaseUserEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.followerCount);
        parcel.writeString(this.followAnchorCount);
        parcel.writeString(this.phone);
        parcel.writeString(this.count);
        parcel.writeString(this.income);
        parcel.writeString(this.expend);
        parcel.writeString(this.contribution);
        parcel.writeString(this.anchor_id);
        parcel.writeString(this.streamName);
        parcel.writeString(this.liveCoverUrl);
        parcel.writeString(this.liveCount);
        parcel.writeString(this.tag);
        parcel.writeString(this.topic);
        parcel.writeInt(this.isChecked);
        parcel.writeInt(this.isFrozen);
        parcel.writeString(this.pullStreamUrl);
        parcel.writeString(this.giftIncomePrice);
        parcel.writeString(this.pkStatus);
        parcel.writeString(this.f5827fp);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AnchorEntity(Parcel parcel) {
        super(parcel);
        this.followerCount = "0";
        this.followAnchorCount = "0";
        this.count = "0";
        this.income = "0";
        this.expend = "0";
        this.contribution = "";
        this.streamName = "";
        this.liveCoverUrl = "";
        this.liveCount = "";
        this.tag = "";
        this.topic = "";
        this.pullStreamUrl = "";
        this.followerCount = parcel.readString();
        this.followAnchorCount = parcel.readString();
        this.phone = parcel.readString();
        this.count = parcel.readString();
        this.income = parcel.readString();
        this.expend = parcel.readString();
        this.contribution = parcel.readString();
        this.anchor_id = parcel.readString();
        this.streamName = parcel.readString();
        this.liveCoverUrl = parcel.readString();
        this.liveCount = parcel.readString();
        this.tag = parcel.readString();
        this.topic = parcel.readString();
        this.isChecked = parcel.readInt();
        this.isFrozen = parcel.readInt();
        this.pullStreamUrl = parcel.readString();
        this.giftIncomePrice = parcel.readString();
        this.pkStatus = parcel.readString();
        this.f5827fp = parcel.readString();
    }
}
