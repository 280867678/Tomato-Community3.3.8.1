package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class BannerEntity implements Parcelable {
    public static final Parcelable.Creator<BannerEntity> CREATOR = new Parcelable.Creator<BannerEntity>() { // from class: com.tomatolive.library.model.BannerEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public BannerEntity mo6544createFromParcel(Parcel parcel) {
            return new BannerEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public BannerEntity[] mo6545newArray(int i) {
            return new BannerEntity[i];
        }
    };
    public String allow_close;
    public String componentId;
    public String content;
    public String forwardScope;

    /* renamed from: id */
    public String f5830id;
    public String img;
    public String method;
    public String name;
    public String terminal;
    public String type;
    public String url;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BannerEntity() {
        this.content = "";
        this.terminal = "";
    }

    public boolean isJumpLiveRoom() {
        return TextUtils.equals("1", this.method);
    }

    public boolean isJumpWebUrl() {
        return TextUtils.equals("2", this.method);
    }

    public boolean isJumpCustomUrl() {
        return TextUtils.equals("2", this.forwardScope);
    }

    public boolean isLiveSDKCallback() {
        return TextUtils.equals("3", this.method);
    }

    public boolean isGameComponents() {
        return TextUtils.equals("2", this.type);
    }

    public boolean isAllowClose() {
        return TextUtils.equals("2", this.allow_close);
    }

    public boolean isWebH5View() {
        return TextUtils.equals("3", this.type);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f5830id);
        parcel.writeString(this.name);
        parcel.writeString(this.img);
        parcel.writeString(this.url);
        parcel.writeString(this.method);
        parcel.writeString(this.content);
        parcel.writeString(this.allow_close);
        parcel.writeString(this.terminal);
        parcel.writeString(this.forwardScope);
        parcel.writeString(this.type);
        parcel.writeString(this.componentId);
    }

    protected BannerEntity(Parcel parcel) {
        this.content = "";
        this.terminal = "";
        this.f5830id = parcel.readString();
        this.name = parcel.readString();
        this.img = parcel.readString();
        this.url = parcel.readString();
        this.method = parcel.readString();
        this.content = parcel.readString();
        this.allow_close = parcel.readString();
        this.terminal = parcel.readString();
        this.forwardScope = parcel.readString();
        this.type = parcel.readString();
        this.componentId = parcel.readString();
    }
}
