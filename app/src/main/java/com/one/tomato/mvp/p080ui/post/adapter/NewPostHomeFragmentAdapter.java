package com.one.tomato.mvp.p080ui.post.adapter;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentStatePagerAdapter;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPostHomeFragmentAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostHomeFragmentAdapter */
/* loaded from: classes3.dex */
public final class NewPostHomeFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> stringList;

    @Override // android.support.p002v4.view.PagerAdapter
    public int getItemPosition(Object object) {
        Intrinsics.checkParameterIsNotNull(object, "object");
        return -2;
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

    @Override // android.support.p002v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.stringList.get(i);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void notifyDataSetChanged() {
        getCount();
        super.notifyDataSetChanged();
    }
}
