package com.one.tomato.mvp.p080ui.papa.adapter;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaTabAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.adapter.PaPaTabAdapter */
/* loaded from: classes3.dex */
public final class PaPaTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> stringList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaPaTabAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> stringList) {
        super(fragmentManager);
        Intrinsics.checkParameterIsNotNull(fragmentList, "fragmentList");
        Intrinsics.checkParameterIsNotNull(stringList, "stringList");
        this.stringList = new ArrayList();
        this.fragmentList = new ArrayList();
        this.stringList = stringList;
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

    @Override // android.support.p002v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.stringList.get(i);
    }
}
