package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.one.tomato.entity.p079db.Tag;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TagList {
    private List<Tag> hotTagList;
    private List<Tag> randomTagList;
    private List<TagCategoryListBean> tagCategoryList;

    public List<Tag> getHotTagList() {
        return this.hotTagList;
    }

    public void setHotTagList(List<Tag> list) {
        this.hotTagList = list;
    }

    public List<TagCategoryListBean> getTagCategoryList() {
        return this.tagCategoryList;
    }

    public void setTagCategoryList(List<TagCategoryListBean> list) {
        this.tagCategoryList = list;
    }

    public List<Tag> getRandomTagList() {
        return this.randomTagList;
    }

    public void setRandomTagList(List<Tag> list) {
        this.randomTagList = list;
    }

    /* loaded from: classes3.dex */
    public static class TagCategoryListBean implements Parcelable {
        public static final Parcelable.Creator<TagCategoryListBean> CREATOR = new Parcelable.Creator<TagCategoryListBean>() { // from class: com.one.tomato.entity.TagList.TagCategoryListBean.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: createFromParcel */
            public TagCategoryListBean mo6367createFromParcel(Parcel parcel) {
                return new TagCategoryListBean(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: newArray */
            public TagCategoryListBean[] mo6368newArray(int i) {
                return new TagCategoryListBean[i];
            }
        };
        private String categoryName;

        /* renamed from: id */
        private int f1739id;
        private ArrayList<Tag> tagList;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getCategoryName() {
            return this.categoryName;
        }

        public void setCategoryName(String str) {
            this.categoryName = str;
        }

        public int getId() {
            return this.f1739id;
        }

        public void setId(int i) {
            this.f1739id = i;
        }

        public ArrayList<Tag> getTagList() {
            return this.tagList;
        }

        public void setTagList(ArrayList<Tag> arrayList) {
            this.tagList = arrayList;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.categoryName);
            parcel.writeInt(this.f1739id);
            parcel.writeTypedList(this.tagList);
        }

        public TagCategoryListBean() {
        }

        protected TagCategoryListBean(Parcel parcel) {
            this.categoryName = parcel.readString();
            this.f1739id = parcel.readInt();
            this.tagList = parcel.createTypedArrayList(Tag.CREATOR);
        }
    }
}
