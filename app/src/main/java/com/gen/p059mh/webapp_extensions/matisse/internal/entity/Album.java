package com.gen.p059mh.webapp_extensions.matisse.internal.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.gen.p059mh.webapp_extensions.R$string;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.entity.Album */
/* loaded from: classes2.dex */
public class Album implements Parcelable {
    public static final String ALBUM_NAME_ALL = "All";
    private long mCount;
    private final String mCoverPath;
    private final String mDisplayName;
    private final String mId;
    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() { // from class: com.gen.mh.webapp_extensions.matisse.internal.entity.Album.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @Nullable
        /* renamed from: createFromParcel */
        public Album mo6177createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public Album[] mo6178newArray(int i) {
            return new Album[i];
        }
    };
    public static final String ALBUM_ID_ALL = String.valueOf(-1);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Album(String str, String str2, String str3, long j) {
        this.mId = str;
        this.mCoverPath = str2;
        this.mDisplayName = str3;
        this.mCount = j;
    }

    Album(Parcel parcel) {
        this.mId = parcel.readString();
        this.mCoverPath = parcel.readString();
        this.mDisplayName = parcel.readString();
        this.mCount = parcel.readLong();
    }

    public static Album valueOf(Cursor cursor) {
        return new Album(cursor.getString(cursor.getColumnIndex("bucket_id")), cursor.getString(cursor.getColumnIndex("_data")), cursor.getString(cursor.getColumnIndex("bucket_display_name")), cursor.getLong(cursor.getColumnIndex("count")));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mCoverPath);
        parcel.writeString(this.mDisplayName);
        parcel.writeLong(this.mCount);
    }

    public String getId() {
        return this.mId;
    }

    public String getCoverPath() {
        return this.mCoverPath;
    }

    public long getCount() {
        return this.mCount;
    }

    public void addCaptureCount() {
        this.mCount++;
    }

    public String getDisplayName(Context context) {
        if (isAll()) {
            return context.getString(R$string.album_name_all);
        }
        return this.mDisplayName;
    }

    public boolean isAll() {
        return ALBUM_ID_ALL.equals(this.mId);
    }

    public boolean isEmpty() {
        return this.mCount == 0;
    }
}
