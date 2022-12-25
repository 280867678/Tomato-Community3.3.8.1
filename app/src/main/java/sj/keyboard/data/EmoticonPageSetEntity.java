package sj.keyboard.data;

import java.util.ArrayList;
import sj.keyboard.data.EmoticonPageEntity;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.interfaces.PageViewInstantiateListener;

/* loaded from: classes4.dex */
public class EmoticonPageSetEntity<T> extends PageSetEntity<EmoticonPageEntity> {
    final EmoticonPageEntity.DelBtnStatus mDelBtnStatus;
    final ArrayList<T> mEmoticonList;
    final int mLine;
    final int mRow;

    public EmoticonPageSetEntity(Builder builder) {
        super(builder);
        this.mLine = builder.line;
        this.mRow = builder.row;
        this.mDelBtnStatus = builder.delBtnStatus;
        this.mEmoticonList = builder.emoticonList;
    }

    public int getLine() {
        return this.mLine;
    }

    public int getRow() {
        return this.mRow;
    }

    public EmoticonPageEntity.DelBtnStatus getDelBtnStatus() {
        return this.mDelBtnStatus;
    }

    public ArrayList<T> getEmoticonList() {
        return this.mEmoticonList;
    }

    /* loaded from: classes4.dex */
    public static class Builder<T> extends PageSetEntity.Builder {
        protected EmoticonPageEntity.DelBtnStatus delBtnStatus = EmoticonPageEntity.DelBtnStatus.GONE;
        protected ArrayList<T> emoticonList;
        protected int line;
        protected PageViewInstantiateListener pageViewInstantiateListener;
        protected int row;

        public Builder setLine(int i) {
            this.line = i;
            return this;
        }

        public Builder setRow(int i) {
            this.row = i;
            return this;
        }

        public Builder setShowDelBtn(EmoticonPageEntity.DelBtnStatus delBtnStatus) {
            this.delBtnStatus = delBtnStatus;
            return this;
        }

        public Builder setEmoticonList(ArrayList<T> arrayList) {
            this.emoticonList = arrayList;
            return this;
        }

        public Builder setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) {
            this.pageViewInstantiateListener = pageViewInstantiateListener;
            return this;
        }

        @Override // sj.keyboard.data.PageSetEntity.Builder
        /* renamed from: setShowIndicator */
        public Builder mo6887setShowIndicator(boolean z) {
            this.isShowIndicator = z;
            return this;
        }

        @Override // sj.keyboard.data.PageSetEntity.Builder
        /* renamed from: setIconUri */
        public Builder mo6885setIconUri(String str) {
            this.iconUri = str;
            return this;
        }

        @Override // sj.keyboard.data.PageSetEntity.Builder
        /* renamed from: setIconUri */
        public Builder mo6884setIconUri(int i) {
            this.iconUri = "" + i;
            return this;
        }

        @Override // sj.keyboard.data.PageSetEntity.Builder
        /* renamed from: setSetName */
        public Builder mo6886setSetName(String str) {
            this.setName = str;
            return this;
        }

        @Override // sj.keyboard.data.PageSetEntity.Builder
        /* renamed from: build */
        public EmoticonPageSetEntity<T> mo6883build() {
            int size = this.emoticonList.size();
            int i = (this.row * this.line) - (this.delBtnStatus.isShow() ? 1 : 0);
            this.pageCount = (int) Math.ceil(this.emoticonList.size() / i);
            int i2 = i > size ? size : i;
            if (!this.pageEntityList.isEmpty()) {
                this.pageEntityList.clear();
            }
            int i3 = 0;
            int i4 = i2;
            int i5 = 0;
            while (i3 < this.pageCount) {
                EmoticonPageEntity emoticonPageEntity = new EmoticonPageEntity();
                emoticonPageEntity.setLine(this.line);
                emoticonPageEntity.setRow(this.row);
                emoticonPageEntity.setDelBtnStatus(this.delBtnStatus);
                emoticonPageEntity.setEmoticonList(this.emoticonList.subList(i5, i4));
                emoticonPageEntity.setIPageViewInstantiateItem(this.pageViewInstantiateListener);
                this.pageEntityList.add(emoticonPageEntity);
                i5 = (i3 * i) + i;
                i3++;
                i4 = (i3 * i) + i;
                if (i4 >= size) {
                    i4 = size;
                }
            }
            return new EmoticonPageSetEntity<>(this);
        }
    }
}
