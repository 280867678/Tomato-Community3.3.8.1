package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes3.dex */
public class LogEventConfigCacheEntity implements Parcelable {
    public static final Parcelable.Creator<LogEventConfigCacheEntity> CREATOR = new Parcelable.Creator<LogEventConfigCacheEntity>() { // from class: com.tomatolive.library.model.LogEventConfigCacheEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public LogEventConfigCacheEntity mo6562createFromParcel(Parcel parcel) {
            return new LogEventConfigCacheEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public LogEventConfigCacheEntity[] mo6563newArray(int i) {
            return new LogEventConfigCacheEntity[i];
        }
    };
    private List<LogEventConfigEntity> eventList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<LogEventConfigEntity> getEventList() {
        return this.eventList;
    }

    public void setEventList(List<LogEventConfigEntity> list) {
        this.eventList = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.eventList);
    }

    public LogEventConfigCacheEntity() {
    }

    protected LogEventConfigCacheEntity(Parcel parcel) {
        this.eventList = parcel.createTypedArrayList(LogEventConfigEntity.CREATOR);
    }
}
