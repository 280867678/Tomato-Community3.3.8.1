package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class LiveEntity extends AnchorEntity implements MultiItemEntity, Parcelable {
    public static final Parcelable.Creator<LiveEntity> CREATOR = new Parcelable.Creator<LiveEntity>() { // from class: com.tomatolive.library.model.LiveEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LiveEntity mo6560createFromParcel(Parcel parcel) {
            return new LiveEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LiveEntity[] mo6561newArray(int i) {
            return new LiveEntity[i];
        }
    };
    public String anchorContribution;
    public String anchorGuardCount;
    public String anchorId;
    public String banPostAllStatus;
    public List<BannerEntity> bannerList;
    public String chargeType;
    public String coverIdentityUrl;
    public String format;
    public String free;
    public String herald;

    /* renamed from: id */
    public String f5840id;
    public boolean isAd;
    public String isPrivateAnchor;
    public String isRelation;
    public int itemType;

    /* renamed from: k */
    public String f5841k;
    public String label;
    public List<String> markUrls;
    public String markerUrl;
    public String otherChannelCoverIdentityUrl;
    public String pendantUrl;
    public String popularity;
    public String privateAnchorPrice;
    public String publishTime;
    public String pushStreamUrl;
    public String roomWatchWhite;
    public String speakLevel;
    public String ticketPrice;
    public int vipCount;
    public String wsAddress;

    @Override // com.tomatolive.library.model.AnchorEntity, com.tomatolive.library.model.BaseUserEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isBanAll() {
        return TextUtils.equals(this.banPostAllStatus, "1");
    }

    public boolean isOnLiving() {
        return TextUtils.equals("1", this.liveStatus) || TextUtils.equals("1", this.isLiving);
    }

    public String getDefPullStreamUrlStr() {
        List<String> pullStreamUrlList = getPullStreamUrlList();
        if (pullStreamUrlList.isEmpty()) {
            return "";
        }
        for (String str : pullStreamUrlList) {
            if (str.startsWith("rtmp://")) {
                return str;
            }
        }
        return pullStreamUrlList.get(0);
    }

    public List<String> getPullStreamUrlList() {
        if (TextUtils.isEmpty(this.pullStreamUrl)) {
            return new ArrayList();
        }
        return Arrays.asList(this.pullStreamUrl.split(","));
    }

    public String getLivePopularityStr() {
        return AppUtils.formatLivePopularityCount(NumberUtils.string2long(this.popularity));
    }

    public boolean isPayLiveNeedBuyTicket() {
        if (TextUtils.equals(this.chargeType, "1") || isTimePayLive()) {
            return !isRoomWatchWhite();
        }
        if (!isPrivateAnchorByAppId()) {
            return false;
        }
        return !isRoomWatchWhite();
    }

    public String getPayLivePrice() {
        if (TextUtils.equals(this.chargeType, "1") || isTimePayLive()) {
            return this.ticketPrice;
        }
        return isPrivateAnchorByAppId() ? this.privateAnchorPrice : "0";
    }

    public boolean isPayLiveTicket() {
        return TextUtils.equals(this.chargeType, "1") || isTimePayLive() || isPrivateAnchorByAppId();
    }

    public boolean isRelationBoolean() {
        return TextUtils.equals("1", this.isRelation);
    }

    public boolean isPrivateAnchorByAppId() {
        return TextUtils.equals(this.isPrivateAnchor, "1") && !TextUtils.equals(this.appId, UserInfoManager.getInstance().getAppId());
    }

    public boolean isRoomWatchWhite() {
        List<String> listByCommaSplit = StringUtils.getListByCommaSplit(this.roomWatchWhite);
        boolean z = false;
        if (listByCommaSplit == null) {
            return false;
        }
        for (String str : listByCommaSplit) {
            if (TextUtils.equals(this.appId, UserInfoManager.getInstance().getAppId()) && TextUtils.equals(str, UserInfoManager.getInstance().getAppOpenId())) {
                z = true;
            }
        }
        return z;
    }

    public boolean isTimePayLive() {
        return TextUtils.equals(this.chargeType, "2");
    }

    public boolean isCoverPreview() {
        return !(isPayLiveNeedBuyTicket() || this.isAd || this.itemType == 2);
    }

    public String getCoverIdentityUrl() {
        if (!TextUtils.equals(this.appId, UserInfoManager.getInstance().getAppId()) && !TextUtils.isEmpty(this.otherChannelCoverIdentityUrl)) {
            return this.otherChannelCoverIdentityUrl;
        }
        return this.coverIdentityUrl;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.itemType;
    }

    public LiveEntity() {
        this.anchorId = "";
        this.label = "";
        this.free = "";
        this.format = "";
        this.herald = "";
        this.publishTime = "";
        this.anchorGuardCount = "0";
        this.f5841k = "";
        this.anchorContribution = "";
        this.popularity = "0";
        this.vipCount = 0;
        this.markerUrl = "";
        this.isAd = false;
        this.itemType = 1;
        this.chargeType = "";
        this.isPrivateAnchor = "";
        this.privateAnchorPrice = "";
        this.ticketPrice = "";
        this.isRelation = "";
        this.roomWatchWhite = "";
    }

    @Override // com.tomatolive.library.model.AnchorEntity
    public String toString() {
        return "LiveEntity{anchorId='" + this.userId + "', liveId='" + this.liveId + "', avatar='" + this.avatar + "', sex='" + this.sex + "', nickname='" + this.nickname + "', expGrade='" + this.expGrade + "'}";
    }

    @Override // com.tomatolive.library.model.AnchorEntity, com.tomatolive.library.model.BaseUserEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f5840id);
        parcel.writeString(this.anchorId);
        parcel.writeString(this.label);
        parcel.writeString(this.pushStreamUrl);
        parcel.writeString(this.wsAddress);
        parcel.writeString(this.free);
        parcel.writeString(this.format);
        parcel.writeString(this.banPostAllStatus);
        parcel.writeString(this.speakLevel);
        parcel.writeString(this.herald);
        parcel.writeString(this.publishTime);
        parcel.writeString(this.anchorGuardCount);
        parcel.writeString(this.f5841k);
        parcel.writeStringList(this.markUrls);
        parcel.writeString(this.anchorContribution);
        parcel.writeString(this.popularity);
        parcel.writeInt(this.vipCount);
        parcel.writeString(this.markerUrl);
        parcel.writeString(this.pendantUrl);
        parcel.writeString(this.coverIdentityUrl);
        parcel.writeString(this.otherChannelCoverIdentityUrl);
        parcel.writeByte(this.isAd ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.itemType);
        parcel.writeTypedList(this.bannerList);
        parcel.writeString(this.chargeType);
        parcel.writeString(this.isPrivateAnchor);
        parcel.writeString(this.privateAnchorPrice);
        parcel.writeString(this.ticketPrice);
        parcel.writeString(this.isRelation);
        parcel.writeString(this.roomWatchWhite);
    }

    protected LiveEntity(Parcel parcel) {
        super(parcel);
        this.anchorId = "";
        this.label = "";
        this.free = "";
        this.format = "";
        this.herald = "";
        this.publishTime = "";
        this.anchorGuardCount = "0";
        this.f5841k = "";
        this.anchorContribution = "";
        this.popularity = "0";
        boolean z = false;
        this.vipCount = 0;
        this.markerUrl = "";
        this.isAd = false;
        this.itemType = 1;
        this.chargeType = "";
        this.isPrivateAnchor = "";
        this.privateAnchorPrice = "";
        this.ticketPrice = "";
        this.isRelation = "";
        this.roomWatchWhite = "";
        this.f5840id = parcel.readString();
        this.anchorId = parcel.readString();
        this.label = parcel.readString();
        this.pushStreamUrl = parcel.readString();
        this.wsAddress = parcel.readString();
        this.free = parcel.readString();
        this.format = parcel.readString();
        this.banPostAllStatus = parcel.readString();
        this.speakLevel = parcel.readString();
        this.herald = parcel.readString();
        this.publishTime = parcel.readString();
        this.anchorGuardCount = parcel.readString();
        this.f5841k = parcel.readString();
        this.markUrls = parcel.createStringArrayList();
        this.anchorContribution = parcel.readString();
        this.popularity = parcel.readString();
        this.vipCount = parcel.readInt();
        this.markerUrl = parcel.readString();
        this.pendantUrl = parcel.readString();
        this.coverIdentityUrl = parcel.readString();
        this.otherChannelCoverIdentityUrl = parcel.readString();
        this.isAd = parcel.readByte() != 0 ? true : z;
        this.itemType = parcel.readInt();
        this.bannerList = parcel.createTypedArrayList(BannerEntity.CREATOR);
        this.chargeType = parcel.readString();
        this.isPrivateAnchor = parcel.readString();
        this.privateAnchorPrice = parcel.readString();
        this.ticketPrice = parcel.readString();
        this.isRelation = parcel.readString();
        this.roomWatchWhite = parcel.readString();
    }
}
