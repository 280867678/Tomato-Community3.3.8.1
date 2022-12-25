package com.one.tomato.entity.p079db;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.Tag */
/* loaded from: classes3.dex */
public class Tag extends LitePalSupport implements Parcelable {
    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() { // from class: com.one.tomato.entity.db.Tag.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public Tag mo6373createFromParcel(Parcel parcel) {
            return new Tag(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public Tag[] mo6374newArray(int i) {
            return new Tag[i];
        }
    };
    private transient boolean isSelect;
    private int tagCategoryId;
    private String tagCategoryName;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int tagId;
    private String tagMd5;
    private String tagName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    protected Tag(Parcel parcel) {
        this.tagId = parcel.readInt();
        this.tagName = parcel.readString();
        this.tagCategoryName = parcel.readString();
        this.tagMd5 = parcel.readString();
        this.tagCategoryId = parcel.readInt();
    }

    public String getTagCategoryName() {
        return this.tagCategoryName;
    }

    public void setTagCategoryName(String str) {
        this.tagCategoryName = str;
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int i) {
        this.tagId = i;
    }

    public String getTagMd5() {
        return this.tagMd5;
    }

    public void setTagMd5(String str) {
        this.tagMd5 = str;
    }

    public int getTagCategoryId() {
        return this.tagCategoryId;
    }

    public void setTagCategoryId(int i) {
        this.tagCategoryId = i;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String str) {
        this.tagName = str;
    }

    public Tag() {
    }

    public Tag(String str) {
        this.tagName = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        return getTagId() == tag.getTagId() && getTagName().equals(tag.getTagName());
    }

    public int hashCode() {
        return this.tagName.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.tagId);
        parcel.writeString(this.tagName);
        parcel.writeString(this.tagCategoryName);
        parcel.writeString(this.tagMd5);
        parcel.writeInt(this.tagCategoryId);
    }
}
