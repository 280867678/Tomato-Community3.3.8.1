package sj.keyboard.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;
import sj.keyboard.data.PageEntity;

/* loaded from: classes4.dex */
public class PageSetEntity<T extends PageEntity> implements Serializable {
    protected final String mIconUri;
    protected final boolean mIsShowIndicator;
    protected final int mPageCount;
    protected final LinkedList<T> mPageEntityList;
    protected final String mSetName;
    protected final String uuid = UUID.randomUUID().toString();

    public PageSetEntity(Builder builder) {
        this.mPageCount = builder.pageCount;
        this.mIsShowIndicator = builder.isShowIndicator;
        this.mPageEntityList = builder.pageEntityList;
        this.mIconUri = builder.iconUri;
        this.mSetName = builder.setName;
    }

    public String getIconUri() {
        return this.mIconUri;
    }

    public int getPageCount() {
        LinkedList<T> linkedList = this.mPageEntityList;
        if (linkedList == null) {
            return 0;
        }
        return linkedList.size();
    }

    public LinkedList<T> getPageEntityList() {
        return this.mPageEntityList;
    }

    public String getUuid() {
        return this.uuid;
    }

    public boolean isShowIndicator() {
        return this.mIsShowIndicator;
    }

    /* loaded from: classes4.dex */
    public static class Builder<T extends PageEntity> {
        protected String iconUri;
        protected int pageCount;
        protected String setName;
        protected boolean isShowIndicator = true;
        protected LinkedList<T> pageEntityList = new LinkedList<>();

        public Builder setPageCount(int i) {
            this.pageCount = i;
            return this;
        }

        /* renamed from: setShowIndicator */
        public Builder mo6887setShowIndicator(boolean z) {
            this.isShowIndicator = z;
            return this;
        }

        public Builder setPageEntityList(LinkedList<T> linkedList) {
            this.pageEntityList = linkedList;
            return this;
        }

        public Builder addPageEntity(T t) {
            this.pageEntityList.add(t);
            return this;
        }

        /* renamed from: setIconUri */
        public Builder mo6885setIconUri(String str) {
            this.iconUri = str;
            return this;
        }

        /* renamed from: setIconUri */
        public Builder mo6884setIconUri(int i) {
            this.iconUri = "" + i;
            return this;
        }

        /* renamed from: setSetName */
        public Builder mo6886setSetName(String str) {
            this.setName = str;
            return this;
        }

        /* renamed from: build */
        public PageSetEntity<T> mo6883build() {
            return new PageSetEntity<>(this);
        }
    }
}
