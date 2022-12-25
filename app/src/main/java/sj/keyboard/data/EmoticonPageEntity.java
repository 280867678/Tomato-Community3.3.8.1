package sj.keyboard.data;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import sj.keyboard.interfaces.PageViewInstantiateListener;
import sj.keyboard.widget.EmoticonPageView;

/* loaded from: classes4.dex */
public class EmoticonPageEntity<T> extends PageEntity<EmoticonPageEntity> {
    private DelBtnStatus mDelBtnStatus;
    private List<T> mEmoticonList;
    private int mLine;
    private int mRow;

    /* loaded from: classes4.dex */
    public enum DelBtnStatus {
        GONE,
        FOLLOW,
        LAST;

        public boolean isShow() {
            return !GONE.toString().equals(toString());
        }
    }

    public List<T> getEmoticonList() {
        return this.mEmoticonList;
    }

    public void setEmoticonList(List<T> list) {
        this.mEmoticonList = list;
    }

    public int getLine() {
        return this.mLine;
    }

    public void setLine(int i) {
        this.mLine = i;
    }

    public int getRow() {
        return this.mRow;
    }

    public void setRow(int i) {
        this.mRow = i;
    }

    public DelBtnStatus getDelBtnStatus() {
        return this.mDelBtnStatus;
    }

    public void setDelBtnStatus(DelBtnStatus delBtnStatus) {
        this.mDelBtnStatus = delBtnStatus;
    }

    @Override // sj.keyboard.data.PageEntity, sj.keyboard.interfaces.PageViewInstantiateListener
    public View instantiateItem(ViewGroup viewGroup, int i, EmoticonPageEntity emoticonPageEntity) {
        PageViewInstantiateListener pageViewInstantiateListener = this.mPageViewInstantiateListener;
        if (pageViewInstantiateListener != null) {
            return pageViewInstantiateListener.instantiateItem(viewGroup, i, this);
        }
        if (getRootView() == null) {
            EmoticonPageView emoticonPageView = new EmoticonPageView(viewGroup.getContext());
            emoticonPageView.setNumColumns(this.mRow);
            setRootView(emoticonPageView);
        }
        return getRootView();
    }
}
