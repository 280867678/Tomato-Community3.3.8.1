package sj.keyboard.data;

import android.view.View;
import android.view.ViewGroup;
import sj.keyboard.data.PageEntity;
import sj.keyboard.interfaces.PageViewInstantiateListener;

/* loaded from: classes4.dex */
public class PageEntity<T extends PageEntity> implements PageViewInstantiateListener<T> {
    protected PageViewInstantiateListener mPageViewInstantiateListener;
    protected View mRootView;

    public void setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) {
        this.mPageViewInstantiateListener = pageViewInstantiateListener;
    }

    public View getRootView() {
        return this.mRootView;
    }

    public void setRootView(View view) {
        this.mRootView = view;
    }

    public PageEntity() {
    }

    public PageEntity(View view) {
        this.mRootView = view;
    }

    @Override // sj.keyboard.interfaces.PageViewInstantiateListener
    public View instantiateItem(ViewGroup viewGroup, int i, T t) {
        PageViewInstantiateListener pageViewInstantiateListener = this.mPageViewInstantiateListener;
        if (pageViewInstantiateListener != null) {
            return pageViewInstantiateListener.instantiateItem(viewGroup, i, this);
        }
        return getRootView();
    }
}
