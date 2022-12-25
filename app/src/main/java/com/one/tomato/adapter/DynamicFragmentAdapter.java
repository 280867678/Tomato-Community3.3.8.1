package com.one.tomato.adapter;

import android.os.Parcelable;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments = new ArrayList();
    private List<String> stringList = new ArrayList();

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter, android.support.p002v4.view.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    public DynamicFragmentAdapter(FragmentManager fragmentManager, List<Fragment> list, List<String> list2) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        if (list == null) {
            return;
        }
        this.mFragments.addAll(list);
        this.stringList.addAll(list2);
    }

    public void updateData(List<Fragment> list, List<String> list2) {
        if (list == null) {
            return;
        }
        this.mFragments.clear();
        this.mFragments.addAll(list);
        this.stringList.clear();
        this.stringList.addAll(list2);
        notifyDataSetChanged();
    }

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter
    /* renamed from: getItem */
    public Fragment mo6639getItem(int i) {
        return this.mFragments.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.mFragments.size();
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.stringList.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getItemPosition(Object obj) {
        if (!((Fragment) obj).isAdded() || !this.mFragments.contains(obj)) {
            return -2;
        }
        return this.mFragments.indexOf(obj);
    }

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter, android.support.p002v4.view.PagerAdapter
    /* renamed from: instantiateItem */
    public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
        Fragment fragment = (Fragment) super.mo6346instantiateItem(viewGroup, i);
        Fragment fragment2 = this.mFragments.get(i);
        if (fragment == fragment2) {
            return fragment;
        }
        this.mFragmentManager.beginTransaction().add(viewGroup.getId(), fragment2).commitNowAllowingStateLoss();
        return fragment2;
    }

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter, android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        if (this.mFragments.contains(fragment)) {
            super.destroyItem(viewGroup, i, (Object) fragment);
        } else {
            this.mFragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
        }
    }
}
