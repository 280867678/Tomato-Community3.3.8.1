package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.entity.p079db.Tag;
import java.util.ArrayList;
import org.xutils.common.Callback;

/* loaded from: classes3.dex */
public class PublishInfo implements Parcelable {
    public static final Parcelable.Creator<PublishInfo> CREATOR = new Parcelable.Creator<PublishInfo>() { // from class: com.one.tomato.entity.PublishInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public PublishInfo mo6363createFromParcel(Parcel parcel) {
            return new PublishInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public PublishInfo[] mo6364newArray(int i) {
            return new PublishInfo[i];
        }
    };
    private Callback.Cancelable callback;
    private CircleList circleList;
    private String description;
    private String imgUrl;
    private boolean isOriginal;
    private int postType;
    private ArrayList<LocalMedia> selectList;
    private ArrayList<Tag> tagList;
    private String title;
    private String videoUrl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected PublishInfo(Parcel parcel) {
        this.description = "";
        this.postType = parcel.readInt();
        this.circleList = (CircleList) parcel.readParcelable(CircleList.class.getClassLoader());
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.selectList = parcel.createTypedArrayList(LocalMedia.CREATOR);
        this.imgUrl = parcel.readString();
        this.videoUrl = parcel.readString();
        this.tagList = parcel.createTypedArrayList(Tag.CREATOR);
        this.isOriginal = parcel.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.postType);
        parcel.writeParcelable(this.circleList, i);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeTypedList(this.selectList);
        parcel.writeString(this.imgUrl);
        parcel.writeString(this.videoUrl);
        parcel.writeTypedList(this.tagList);
        parcel.writeByte(this.isOriginal ? (byte) 1 : (byte) 0);
    }

    public int getPostType() {
        return this.postType;
    }

    public void setPostType(int i) {
        this.postType = i;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String str) {
        this.imgUrl = str;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String str) {
        this.videoUrl = str;
    }

    public ArrayList<LocalMedia> getSelectList() {
        return this.selectList;
    }

    public void setSelectList(ArrayList<LocalMedia> arrayList) {
        this.selectList = arrayList;
    }

    public PublishInfo() {
        this.description = "";
    }

    public CircleList getCircleList() {
        return this.circleList;
    }

    public void setCircleList(CircleList circleList) {
        this.circleList = circleList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ArrayList<Tag> getTagList() {
        return this.tagList;
    }

    public void setTagList(ArrayList<Tag> arrayList) {
        this.tagList = arrayList;
    }

    public boolean isOriginal() {
        return this.isOriginal;
    }

    public void setOriginal(boolean z) {
        this.isOriginal = z;
    }
}
