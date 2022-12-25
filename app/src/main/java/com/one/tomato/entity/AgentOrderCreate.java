package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class AgentOrderCreate implements Parcelable {
    public static final Parcelable.Creator<AgentOrderCreate> CREATOR = new Parcelable.Creator<AgentOrderCreate>() { // from class: com.one.tomato.entity.AgentOrderCreate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public AgentOrderCreate mo6351createFromParcel(Parcel parcel) {
            return new AgentOrderCreate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public AgentOrderCreate[] mo6352newArray(int i) {
            return new AgentOrderCreate[i];
        }
    };
    private String cancel_url;
    private String message;
    private String platid;
    private String query_url;
    private String status;
    private String upload_url;
    private String view_url;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getPlatid() {
        return this.platid;
    }

    public void setPlatid(String str) {
        this.platid = str;
    }

    public String getView_url() {
        return this.view_url;
    }

    public void setView_url(String str) {
        this.view_url = str;
    }

    public String getQuery_url() {
        return this.query_url;
    }

    public void setQuery_url(String str) {
        this.query_url = str;
    }

    public String getCancel_url() {
        return this.cancel_url;
    }

    public void setCancel_url(String str) {
        this.cancel_url = str;
    }

    public String getUpload_url() {
        return this.upload_url;
    }

    public void setUpload_url(String str) {
        this.upload_url = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.status);
        parcel.writeString(this.platid);
        parcel.writeString(this.view_url);
        parcel.writeString(this.query_url);
        parcel.writeString(this.cancel_url);
        parcel.writeString(this.upload_url);
        parcel.writeString(this.message);
    }

    public AgentOrderCreate() {
    }

    protected AgentOrderCreate(Parcel parcel) {
        this.status = parcel.readString();
        this.platid = parcel.readString();
        this.view_url = parcel.readString();
        this.query_url = parcel.readString();
        this.cancel_url = parcel.readString();
        this.upload_url = parcel.readString();
        this.message = parcel.readString();
    }
}
