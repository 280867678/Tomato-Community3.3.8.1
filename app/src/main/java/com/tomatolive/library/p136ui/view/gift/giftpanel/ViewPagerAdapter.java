package com.tomatolive.library.p136ui.view.gift.giftpanel;

import android.support.p002v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.ViewPagerAdapter */
/* loaded from: classes3.dex */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    @Override // android.support.p002v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ViewPagerAdapter(List<View> list) {
        this.views = list;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        List<View> list = this.views;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    /* renamed from: instantiateItem */
    public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
        if (i < this.views.size()) {
            viewGroup.addView(this.views.get(i), 0);
        }
        return this.views.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (i < this.views.size()) {
            viewGroup.removeView(this.views.get(i));
        }
    }
}
