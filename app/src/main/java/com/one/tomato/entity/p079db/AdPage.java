package com.one.tomato.entity.p079db;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.AdPage */
/* loaded from: classes3.dex */
public class AdPage extends LitePalSupport implements Parcelable, Comparable<AdPage> {
    public static final Parcelable.Creator<AdPage> CREATOR = new Parcelable.Creator<AdPage>() { // from class: com.one.tomato.entity.db.AdPage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public AdPage mo6369createFromParcel(Parcel parcel) {
            return new AdPage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public AdPage[] mo6370newArray(int i) {
            return new AdPage[i];
        }
    };
    private String adArticleAvatarSec;
    private String adArticleContent;
    private String adArticleName;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int adId;
    private String adJumpDetail;
    private String adJumpModule;
    private String adLink;
    private String adLinkType;
    private String adName;
    private int adTime;
    private int adType;
    private String andImageUrl10801920Sec;
    private String andImageUrl10802280Sec;
    private String andImageUrl7201280Sec;
    private String categoryType;
    private String channelNo;
    private String directApp;
    private String imageUrlSec;
    private int loopStatus;
    private String materialType;
    private int openType;
    private String random;
    private String size;
    private String sortNum;
    private String videoCoverUrlSec;
    private String videoTime;
    private String videoUrlSec;
    private String viewsNum;
    private boolean webAppNew;
    private int weight;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull AdPage adPage) {
        if (adPage == null || TextUtils.isEmpty(this.sortNum)) {
            return 0;
        }
        return this.sortNum.compareTo(adPage.getSortNum());
    }

    public String getCategoryType() {
        return this.categoryType;
    }

    public void setCategoryType(String str) {
        this.categoryType = str;
    }

    public int getAdId() {
        return this.adId;
    }

    public void setAdId(int i) {
        this.adId = i;
    }

    public String getAdName() {
        return this.adName;
    }

    public void setAdName(String str) {
        this.adName = str;
    }

    public String getChannelNo() {
        return this.channelNo;
    }

    public void setChannelNo(String str) {
        this.channelNo = str;
    }

    public String getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(String str) {
        this.sortNum = str;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int i) {
        this.weight = i;
    }

    public int getAdTime() {
        return this.adTime;
    }

    public void setAdTime(int i) {
        this.adTime = i;
    }

    public int getAdType() {
        return this.adType;
    }

    public void setAdType(int i) {
        this.adType = i;
    }

    public String getAdLink() {
        return this.adLink;
    }

    public void setAdLink(String str) {
        this.adLink = str;
    }

    public String getAdJumpDetail() {
        return this.adJumpDetail;
    }

    public void setAdJumpDetail(String str) {
        this.adJumpDetail = str;
    }

    public String getVideoTime() {
        return this.videoTime;
    }

    public void setVideoTime(String str) {
        this.videoTime = str;
    }

    public String getAndImageUrl10802280Sec() {
        return this.andImageUrl10802280Sec;
    }

    public void setAndImageUrl10802280Sec(String str) {
        this.andImageUrl10802280Sec = str;
    }

    public String getAndImageUrl10801920Sec() {
        return this.andImageUrl10801920Sec;
    }

    public void setAndImageUrl10801920Sec(String str) {
        this.andImageUrl10801920Sec = str;
    }

    public String getAndImageUrl7201280Sec() {
        return this.andImageUrl7201280Sec;
    }

    public void setAndImageUrl7201280Sec(String str) {
        this.andImageUrl7201280Sec = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public String getVideoUrlSec() {
        return this.videoUrlSec;
    }

    public void setVideoUrlSec(String str) {
        this.videoUrlSec = str;
    }

    public int getLoopStatus() {
        return this.loopStatus;
    }

    public void setLoopStatus(int i) {
        this.loopStatus = i;
    }

    public String getAdArticleName() {
        return this.adArticleName;
    }

    public void setAdArticleName(String str) {
        this.adArticleName = str;
    }

    public String getAdArticleAvatarSec() {
        return this.adArticleAvatarSec;
    }

    public void setAdArticleAvatarSec(String str) {
        this.adArticleAvatarSec = str;
    }

    public String getViewsNum() {
        return this.viewsNum;
    }

    public void setViewsNum(String str) {
        this.viewsNum = str;
    }

    public String getRandom() {
        return this.random;
    }

    public void setRandom(String str) {
        this.random = str;
    }

    public String getImageUrlSec() {
        return this.imageUrlSec;
    }

    public void setImageUrlSec(String str) {
        this.imageUrlSec = str;
    }

    public String getVideoCoverUrlSec() {
        return this.videoCoverUrlSec;
    }

    public void setVideoCoverUrlSec(String str) {
        this.videoCoverUrlSec = str;
    }

    public String getAdArticleContent() {
        return this.adArticleContent;
    }

    public void setAdArticleContent(String str) {
        this.adArticleContent = str;
    }

    public String getAdLinkType() {
        return this.adLinkType;
    }

    public void setAdLinkType(String str) {
        this.adLinkType = str;
    }

    public String getAdJumpModule() {
        return this.adJumpModule;
    }

    public void setAdJumpModule(String str) {
        this.adJumpModule = str;
    }

    public String getDirectApp() {
        return this.directApp;
    }

    public void setDirectApp(String str) {
        this.directApp = str;
    }

    public String getMaterialType() {
        return this.materialType;
    }

    public void setMaterialType(String str) {
        this.materialType = str;
    }

    public int getOpenType() {
        return this.openType;
    }

    public void setOpenType(int i) {
        this.openType = i;
    }

    public boolean isWebAppNew() {
        return this.webAppNew;
    }

    public void setWebAppNew(boolean z) {
        this.webAppNew = z;
    }

    public AdPage() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.categoryType);
        parcel.writeInt(this.adId);
        parcel.writeString(this.adName);
        parcel.writeString(this.channelNo);
        parcel.writeString(this.sortNum);
        parcel.writeInt(this.weight);
        parcel.writeInt(this.adTime);
        parcel.writeInt(this.adType);
        parcel.writeString(this.adLink);
        parcel.writeString(this.adLinkType);
        parcel.writeString(this.adJumpModule);
        parcel.writeString(this.adJumpDetail);
        parcel.writeString(this.directApp);
        parcel.writeString(this.materialType);
        parcel.writeString(this.andImageUrl10802280Sec);
        parcel.writeString(this.andImageUrl10801920Sec);
        parcel.writeString(this.andImageUrl7201280Sec);
        parcel.writeString(this.imageUrlSec);
        parcel.writeString(this.videoUrlSec);
        parcel.writeString(this.videoTime);
        parcel.writeString(this.random);
        parcel.writeString(this.videoCoverUrlSec);
        parcel.writeString(this.size);
        parcel.writeInt(this.loopStatus);
        parcel.writeString(this.adArticleName);
        parcel.writeString(this.adArticleAvatarSec);
        parcel.writeString(this.adArticleContent);
        parcel.writeString(this.viewsNum);
        parcel.writeInt(this.openType);
    }

    protected AdPage(Parcel parcel) {
        this.categoryType = parcel.readString();
        this.adId = parcel.readInt();
        this.adName = parcel.readString();
        this.channelNo = parcel.readString();
        this.sortNum = parcel.readString();
        this.weight = parcel.readInt();
        this.adTime = parcel.readInt();
        this.adType = parcel.readInt();
        this.adLink = parcel.readString();
        this.adLinkType = parcel.readString();
        this.adJumpModule = parcel.readString();
        this.adJumpDetail = parcel.readString();
        this.directApp = parcel.readString();
        this.materialType = parcel.readString();
        this.andImageUrl10802280Sec = parcel.readString();
        this.andImageUrl10801920Sec = parcel.readString();
        this.andImageUrl7201280Sec = parcel.readString();
        this.imageUrlSec = parcel.readString();
        this.videoUrlSec = parcel.readString();
        this.videoTime = parcel.readString();
        this.random = parcel.readString();
        this.videoCoverUrlSec = parcel.readString();
        this.size = parcel.readString();
        this.loopStatus = parcel.readInt();
        this.adArticleName = parcel.readString();
        this.adArticleAvatarSec = parcel.readString();
        this.adArticleContent = parcel.readString();
        this.viewsNum = parcel.readString();
        this.openType = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && AdPage.class == obj.getClass() && this.adId == ((AdPage) obj).adId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.adId));
    }
}
