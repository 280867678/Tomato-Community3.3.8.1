package com.one.tomato.adapter;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TabFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> stringList;

    public TabFragmentAdapter(FragmentManager fragmentManager, List<Fragment> list, List<String> list2) {
        super(fragmentManager);
        this.stringList = new ArrayList();
        this.fragmentList = new ArrayList();
        this.fragmentList = list;
        this.stringList = list2;
    }

    @Override // android.support.p002v4.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.fragmentList.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.stringList.get(i);
    }
}
