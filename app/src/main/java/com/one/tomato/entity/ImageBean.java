package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class ImageBean implements Parcelable {
    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() { // from class: com.one.tomato.entity.ImageBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public ImageBean mo6359createFromParcel(Parcel parcel) {
            return new ImageBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public ImageBean[] mo6360newArray(int i) {
            return new ImageBean[i];
        }
    };
    private int errorResId;
    private int height;
    private String image;
    private int imageType;
    private boolean isAbsolute;
    private boolean isAd;
    private boolean isGif;
    private boolean isHeader;
    private boolean isLocal;
    private boolean isLongImg;
    private boolean isNeedImagePay;
    private int loadingResId;
    private boolean secret;
    private boolean showImageType;
    private int thumbHeight;
    private int thumbWidth;
    private int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImageBean() {
        this.secret = true;
    }

    public ImageBean(String str) {
        this.secret = true;
        this.image = str;
    }

    public ImageBean(String str, int i) {
        this.secret = true;
        this.image = str;
        this.loadingResId = i;
    }

    public ImageBean(String str, int i, int i2) {
        this.secret = true;
        this.image = str;
        this.loadingResId = i;
        this.errorResId = i2;
    }

    protected ImageBean(Parcel parcel) {
        boolean z = true;
        this.secret = true;
        this.image = parcel.readString();
        this.loadingResId = parcel.readInt();
        this.errorResId = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.thumbWidth = parcel.readInt();
        this.thumbHeight = parcel.readInt();
        this.imageType = parcel.readInt();
        this.showImageType = parcel.readByte() != 0;
        this.secret = parcel.readByte() != 0;
        this.isAbsolute = parcel.readByte() != 0;
        this.isLocal = parcel.readByte() != 0;
        this.isGif = parcel.readByte() != 0;
        this.isAd = parcel.readByte() != 0;
        this.isHeader = parcel.readByte() == 0 ? false : z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.image);
        parcel.writeInt(this.loadingResId);
        parcel.writeInt(this.errorResId);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.thumbWidth);
        parcel.writeInt(this.thumbHeight);
        parcel.writeInt(this.imageType);
        parcel.writeByte(this.showImageType ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.secret ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isAbsolute ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isGif ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isAd ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isHeader ? (byte) 1 : (byte) 0);
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
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

    public int getImageType() {
        return this.imageType;
    }

    public void setImageType(int i) {
        this.imageType = i;
    }

    public int getLoadingResId() {
        return this.loadingResId;
    }

    public void setLoadingResId(int i) {
        this.loadingResId = i;
    }

    public int getErrorResId() {
        return this.errorResId;
    }

    public void setErrorResId(int i) {
        this.errorResId = i;
    }

    public boolean isShowImageType() {
        return this.showImageType;
    }

    public void setShowImageType(boolean z) {
        this.showImageType = z;
    }

    public boolean isLocal() {
        return this.isLocal;
    }

    public void setLocal(boolean z) {
        this.isLocal = z;
    }

    public boolean isSecret() {
        return this.secret;
    }

    public void setSecret(boolean z) {
        this.secret = z;
    }

    public boolean isAbsolute() {
        return this.isAbsolute;
    }

    public void setAbsolute(boolean z) {
        this.isAbsolute = z;
    }

    public int getThumbWidth() {
        return this.thumbWidth;
    }

    public void setThumbWidth(int i) {
        this.thumbWidth = i;
    }

    public int getThumbHeight() {
        return this.thumbHeight;
    }

    public void setThumbHeight(int i) {
        this.thumbHeight = i;
    }

    public void setAd(boolean z) {
        this.isAd = z;
    }

    public boolean getIsAd() {
        return this.isAd;
    }

    public boolean isGif() {
        if (!TextUtils.isEmpty(this.image) && (this.image.endsWith(".GIF") || this.image.endsWith(".gif"))) {
            this.isGif = true;
            if (this.isHeader) {
                this.isGif = false;
            }
        } else {
            this.isGif = false;
        }
        return this.isGif;
    }

    public void setGif(boolean z) {
        this.isGif = z;
    }

    public boolean isHeader() {
        return this.isHeader;
    }

    public void setHeader(boolean z) {
        this.isHeader = z;
    }

    public boolean isLongImg() {
        return this.height > this.width * 3;
    }
}
