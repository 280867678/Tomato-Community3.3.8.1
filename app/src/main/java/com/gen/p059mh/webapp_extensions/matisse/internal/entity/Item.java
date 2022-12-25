package com.gen.p059mh.webapp_extensions.matisse.internal.entity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import com.gen.p059mh.webapp_extensions.matisse.MimeType;
import com.j256.ormlite.field.FieldType;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.entity.Item */
/* loaded from: classes2.dex */
public class Item implements Parcelable {
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() { // from class: com.gen.mh.webapp_extensions.matisse.internal.entity.Item.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @Nullable
        /* renamed from: createFromParcel */
        public Item mo6179createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public Item[] mo6180newArray(int i) {
            return new Item[i];
        }
    };
    public static final String ITEM_DISPLAY_NAME_CAPTURE = "Capture";
    public static final long ITEM_ID_CAPTURE = -1;
    public final long duration;

    /* renamed from: id */
    public final long f1292id;
    public final String mimeType;
    public final long size;
    public final Uri uri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Item(long j, String str, long j2, long j3) {
        Uri contentUri;
        this.f1292id = j;
        this.mimeType = str;
        if (isImage()) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if (isVideo()) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else {
            contentUri = MediaStore.Files.getContentUri("external");
        }
        this.uri = ContentUris.withAppendedId(contentUri, j);
        this.size = j2;
        this.duration = j3;
    }

    private Item(Parcel parcel) {
        this.f1292id = parcel.readLong();
        this.mimeType = parcel.readString();
        this.uri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.size = parcel.readLong();
        this.duration = parcel.readLong();
    }

    public static Item valueOf(Cursor cursor) {
        return new Item(cursor.getLong(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX)), cursor.getString(cursor.getColumnIndex("mime_type")), cursor.getLong(cursor.getColumnIndex("_size")), cursor.getLong(cursor.getColumnIndex("duration")));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f1292id);
        parcel.writeString(this.mimeType);
        parcel.writeParcelable(this.uri, 0);
        parcel.writeLong(this.size);
        parcel.writeLong(this.duration);
    }

    public Uri getContentUri() {
        return this.uri;
    }

    public boolean isCapture() {
        return this.f1292id == -1;
    }

    public boolean isImage() {
        return MimeType.isImage(this.mimeType);
    }

    public boolean isGif() {
        return MimeType.isGif(this.mimeType);
    }

    public boolean isVideo() {
        return MimeType.isVideo(this.mimeType);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }
        Item item = (Item) obj;
        if (this.f1292id != item.f1292id) {
            return false;
        }
        String str = this.mimeType;
        if ((str == null || !str.equals(item.mimeType)) && !(this.mimeType == null && item.mimeType == null)) {
            return false;
        }
        Uri uri = this.uri;
        return ((uri != null && uri.equals(item.uri)) || (this.uri == null && item.uri == null)) && this.size == item.size && this.duration == item.duration;
    }

    public int hashCode() {
        int hashCode = Long.valueOf(this.f1292id).hashCode() + 31;
        String str = this.mimeType;
        if (str != null) {
            hashCode = (hashCode * 31) + str.hashCode();
        }
        return (((((hashCode * 31) + this.uri.hashCode()) * 31) + Long.valueOf(this.size).hashCode()) * 31) + Long.valueOf(this.duration).hashCode();
    }
}
