package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class LogEventConfigEntity implements Parcelable {
    public static final Parcelable.Creator<LogEventConfigEntity> CREATOR = new Parcelable.Creator<LogEventConfigEntity>() { // from class: com.tomatolive.library.model.LogEventConfigEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LogEventConfigEntity mo6564createFromParcel(Parcel parcel) {
            return new LogEventConfigEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LogEventConfigEntity[] mo6565newArray(int i) {
            return new LogEventConfigEntity[i];
        }
    };
    private String events;

    /* renamed from: id */
    private String f5843id;
    private int quantityLimit;
    private int timeLimit;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.f5843id;
    }

    public void setId(String str) {
        this.f5843id = str;
    }

    public int getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(int i) {
        this.timeLimit = i;
    }

    public int getQuantityLimit() {
        return this.quantityLimit;
    }

    public void setQuantityLimit(int i) {
        this.quantityLimit = i;
    }

    public String getEvents() {
        return this.events;
    }

    public void setEvents(String str) {
        this.events = str;
    }

    public String toString() {
        return "LogEventConfigEntity{id='" + this.f5843id + "', timeLimit=" + this.timeLimit + ", quantityLimit=" + this.quantityLimit + ", events='" + this.events + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f5843id);
        parcel.writeInt(this.timeLimit);
        parcel.writeInt(this.quantityLimit);
        parcel.writeString(this.events);
    }

    public LogEventConfigEntity() {
    }

    protected LogEventConfigEntity(Parcel parcel) {
        this.f5843id = parcel.readString();
        this.timeLimit = parcel.readInt();
        this.quantityLimit = parcel.readInt();
        this.events = parcel.readString();
    }
}
