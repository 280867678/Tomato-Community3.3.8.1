package com.one.tomato.mvp.p080ui.papa.adapter;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPaPaViewPagerAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.adapter.NewPaPaViewPagerAdapter */
/* loaded from: classes3.dex */
public final class NewPaPaViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    @Override // android.support.p002v4.app.FragmentPagerAdapter, android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup container, int i, Object object) {
        Intrinsics.checkParameterIsNotNull(container, "container");
        Intrinsics.checkParameterIsNotNull(object, "object");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPaPaViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        Intrinsics.checkParameterIsNotNull(fragmentList, "fragmentList");
        this.fragmentList = new ArrayList();
        this.fragmentList = fragmentList;
    }

    @Override // android.support.p002v4.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.fragmentList.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.fragmentList.size();
    }
}
