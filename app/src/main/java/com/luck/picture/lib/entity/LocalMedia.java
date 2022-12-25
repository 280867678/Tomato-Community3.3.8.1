package com.luck.picture.lib.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class LocalMedia implements Parcelable {
    public static final Parcelable.Creator<LocalMedia> CREATOR = new Parcelable.Creator<LocalMedia>() { // from class: com.luck.picture.lib.entity.LocalMedia.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LocalMedia mo6341createFromParcel(Parcel parcel) {
            return new LocalMedia(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LocalMedia[] mo6342newArray(int i) {
            return new LocalMedia[i];
        }
    };
    private String compressPath;
    private boolean compressed;
    private String cutPath;
    private int demoResId;
    private long duration;
    private int height;
    private boolean isChecked;
    private boolean isCover;
    private boolean isCut;
    private boolean isDemo;
    private int mimeType;
    private int num;
    private String path;
    private String pictureType;
    public int position;
    private int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocalMedia() {
    }

    public LocalMedia(String str) {
        this.path = str;
    }

    public LocalMedia(String str, long j, int i, String str2, int i2, int i3) {
        this.path = str;
        this.duration = j;
        this.mimeType = i;
        this.pictureType = str2;
        this.width = i2;
        this.height = i3;
    }

    public LocalMedia(String str, long j, boolean z, int i, int i2, int i3) {
        this.path = str;
        this.duration = j;
        this.isChecked = z;
        this.position = i;
        this.num = i2;
        this.mimeType = i3;
    }

    public String getPictureType() {
        if (TextUtils.isEmpty(this.pictureType)) {
            this.pictureType = "image/jpeg";
        }
        return this.pictureType;
    }

    public void setPictureType(String str) {
        this.pictureType = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getCompressPath() {
        return this.compressPath;
    }

    public void setCompressPath(String str) {
        this.compressPath = str;
    }

    public String getCutPath() {
        return this.cutPath;
    }

    public void setCutPath(String str) {
        this.cutPath = str;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public boolean isCut() {
        return this.isCut;
    }

    public void setCut(boolean z) {
        this.isCut = z;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int i) {
        this.num = i;
    }

    public int getMimeType() {
        return this.mimeType;
    }

    public int getDemoResId() {
        return this.demoResId;
    }

    public void setDemoResId(int i) {
        this.demoResId = i;
    }

    public void setMimeType(int i) {
        this.mimeType = i;
    }

    public boolean isCompressed() {
        return this.compressed;
    }

    public void setCompressed(boolean z) {
        this.compressed = z;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public boolean isCover() {
        return this.isCover;
    }

    public void setCover(boolean z) {
        this.isCover = z;
    }

    public boolean isDemo() {
        return this.isDemo;
    }

    public void setDemo(boolean z) {
        this.isDemo = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.path);
        parcel.writeString(this.compressPath);
        parcel.writeString(this.cutPath);
        parcel.writeLong(this.duration);
        parcel.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCut ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.position);
        parcel.writeInt(this.num);
        parcel.writeInt(this.mimeType);
        parcel.writeString(this.pictureType);
        parcel.writeByte(this.compressed ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeByte(this.isCover ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isDemo ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.demoResId);
    }

    protected LocalMedia(Parcel parcel) {
        this.path = parcel.readString();
        this.compressPath = parcel.readString();
        this.cutPath = parcel.readString();
        this.duration = parcel.readLong();
        boolean z = true;
        this.isChecked = parcel.readByte() != 0;
        this.isCut = parcel.readByte() != 0;
        this.position = parcel.readInt();
        this.num = parcel.readInt();
        this.mimeType = parcel.readInt();
        this.pictureType = parcel.readString();
        this.compressed = parcel.readByte() != 0;
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.isCover = parcel.readByte() != 0;
        this.isDemo = parcel.readByte() == 0 ? false : z;
        this.demoResId = parcel.readInt();
    }
}
