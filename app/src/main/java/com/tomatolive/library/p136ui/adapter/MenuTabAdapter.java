package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentStatePagerAdapter;
import com.tomatolive.library.base.BaseFragment;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.MenuTabAdapter */
/* loaded from: classes3.dex */
public class MenuTabAdapter extends FragmentStatePagerAdapter {
    private List<String> entityList;
    private List<BaseFragment> mFragments;

    public MenuTabAdapter(List<BaseFragment> list, List<String> list2, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mFragments = list;
        this.entityList = list2;
    }

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter
    /* renamed from: getItem  reason: collision with other method in class */
    public BaseFragment mo6639getItem(int i) {
        return this.mFragments.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.mFragments.size();
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.entityList.get(i);
    }
}
