package com.one.tomato.mvp.p080ui.post.adapter;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewDetailViewPagerAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewDetailViewPagerAdapter */
/* loaded from: classes3.dex */
public final class NewDetailViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewDetailViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        Intrinsics.checkParameterIsNotNull(fm, "fm");
        Intrinsics.checkParameterIsNotNull(fragmentList, "fragmentList");
        this.fragmentList = new ArrayList();
        this.fragmentList = fragmentList;
    }

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter
    /* renamed from: getItem */
    public Fragment mo6639getItem(int i) {
        return this.fragmentList.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override // android.support.p002v4.app.FragmentStatePagerAdapter, android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup container, int i, Object object) {
        Intrinsics.checkParameterIsNotNull(container, "container");
        Intrinsics.checkParameterIsNotNull(object, "object");
        super.destroyItem(container, i, object);
    }
}
