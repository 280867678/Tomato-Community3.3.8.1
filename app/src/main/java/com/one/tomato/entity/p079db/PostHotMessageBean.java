package com.one.tomato.entity.p079db;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.PostHotMessageBean */
/* loaded from: classes3.dex */
public class PostHotMessageBean extends LitePalSupport implements Parcelable {
    public static final Parcelable.Creator<PostHotMessageBean> CREATOR = new Parcelable.Creator<PostHotMessageBean>() { // from class: com.one.tomato.entity.db.PostHotMessageBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public PostHotMessageBean mo6371createFromParcel(Parcel parcel) {
            return new PostHotMessageBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public PostHotMessageBean[] mo6372newArray(int i) {
            return new PostHotMessageBean[i];
        }
    };
    private String appId;
    private String articleIds;
    private int articleNum;
    private String begTime;
    private String createTime;
    private String endTime;
    private String eventCoverUrl;
    private String eventDesc;
    private String eventName;
    private String eventPosition;
    private String eventStartDate;
    private String eventState;
    private int hotViewNum;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int messageId;
    private String updateTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected PostHotMessageBean(Parcel parcel) {
        this.appId = parcel.readString();
        this.articleIds = parcel.readString();
        this.articleNum = parcel.readInt();
        this.begTime = parcel.readString();
        this.createTime = parcel.readString();
        this.endTime = parcel.readString();
        this.eventCoverUrl = parcel.readString();
        this.eventDesc = parcel.readString();
        this.eventName = parcel.readString();
        this.eventPosition = parcel.readString();
        this.eventStartDate = parcel.readString();
        this.eventState = parcel.readString();
        this.hotViewNum = parcel.readInt();
        this.messageId = parcel.readInt();
        this.updateTime = parcel.readString();
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getArticleIds() {
        return this.articleIds;
    }

    public void setArticleIds(String str) {
        this.articleIds = str;
    }

    public int getArticleNum() {
        return this.articleNum;
    }

    public void setArticleNum(int i) {
        this.articleNum = i;
    }

    public String getBegTime() {
        return this.begTime;
    }

    public void setBegTime(String str) {
        this.begTime = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public String getEventCoverUrl() {
        return this.eventCoverUrl;
    }

    public void setEventCoverUrl(String str) {
        this.eventCoverUrl = str;
    }

    public String getEventDesc() {
        return this.eventDesc;
    }

    public void setEventDesc(String str) {
        this.eventDesc = str;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String str) {
        this.eventName = str;
    }

    public String getEventPosition() {
        return this.eventPosition;
    }

    public void setEventPosition(String str) {
        this.eventPosition = str;
    }

    public String getEventStartDate() {
        return this.eventStartDate;
    }

    public void setEventStartDate(String str) {
        this.eventStartDate = str;
    }

    public String getEventState() {
        return this.eventState;
    }

    public void setEventState(String str) {
        this.eventState = str;
    }

    public int getHotViewNum() {
        return this.hotViewNum;
    }

    public void setHotViewNum(int i) {
        this.hotViewNum = i;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int i) {
        this.messageId = i;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.appId);
        parcel.writeString(this.articleIds);
        parcel.writeInt(this.articleNum);
        parcel.writeString(this.begTime);
        parcel.writeString(this.createTime);
        parcel.writeString(this.endTime);
        parcel.writeString(this.eventCoverUrl);
        parcel.writeString(this.eventDesc);
        parcel.writeString(this.eventName);
        parcel.writeString(this.eventPosition);
        parcel.writeString(this.eventStartDate);
        parcel.writeString(this.eventState);
        parcel.writeInt(this.hotViewNum);
        parcel.writeInt(this.messageId);
        parcel.writeString(this.updateTime);
    }
}
