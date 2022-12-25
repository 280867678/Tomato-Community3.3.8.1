package sj.keyboard.adpater;

import android.support.p002v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import sj.keyboard.data.PageEntity;
import sj.keyboard.data.PageSetEntity;

/* loaded from: classes4.dex */
public class PageSetAdapter extends PagerAdapter {
    private final ArrayList<PageSetEntity> mPageSetEntityList = new ArrayList<>();

    @Override // android.support.p002v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void notifyData() {
    }

    public ArrayList<PageSetEntity> getPageSetEntityList() {
        return this.mPageSetEntityList;
    }

    public int getPageSetStartPosition(PageSetEntity pageSetEntity) {
        if (pageSetEntity == null || TextUtils.isEmpty(pageSetEntity.getUuid())) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mPageSetEntityList.size(); i2++) {
            if (i2 == this.mPageSetEntityList.size() - 1 && !pageSetEntity.getUuid().equals(this.mPageSetEntityList.get(i2).getUuid())) {
                return 0;
            }
            if (pageSetEntity.getUuid().equals(this.mPageSetEntityList.get(i2).getUuid())) {
                return i;
            }
            i += this.mPageSetEntityList.get(i2).getPageCount();
        }
        return i;
    }

    public void add(View view) {
        add(this.mPageSetEntityList.size(), view);
    }

    public void add(int i, View view) {
        this.mPageSetEntityList.add(i, new PageSetEntity.Builder().addPageEntity(new PageEntity(view)).mo6887setShowIndicator(false).mo6883build());
    }

    public void add(PageSetEntity pageSetEntity) {
        add(this.mPageSetEntityList.size(), pageSetEntity);
    }

    public void add(int i, PageSetEntity pageSetEntity) {
        if (pageSetEntity == null) {
            return;
        }
        this.mPageSetEntityList.add(i, pageSetEntity);
    }

    public PageSetEntity get(int i) {
        return this.mPageSetEntityList.get(i);
    }

    public void remove(int i) {
        this.mPageSetEntityList.remove(i);
        notifyData();
    }

    public PageEntity getPageEntity(int i) {
        Iterator<PageSetEntity> it2 = this.mPageSetEntityList.iterator();
        while (it2.hasNext()) {
            PageSetEntity next = it2.next();
            if (next.getPageCount() > i) {
                return (PageEntity) next.getPageEntityList().get(i);
            }
            i -= next.getPageCount();
        }
        return null;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        Iterator<PageSetEntity> it2 = this.mPageSetEntityList.iterator();
        int i = 0;
        while (it2.hasNext()) {
            i += it2.next().getPageCount();
        }
        return i;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    /* renamed from: instantiateItem */
    public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
        View instantiateItem = getPageEntity(i).instantiateItem(viewGroup, i, null);
        if (instantiateItem == null) {
            return null;
        }
        viewGroup.addView(instantiateItem);
        return instantiateItem;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
