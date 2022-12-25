package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.PreviewItemFragment;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.PreviewPagerAdapter */
/* loaded from: classes4.dex */
public class PreviewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Item> mItems = new ArrayList<>();
    private OnPrimaryItemSetListener mListener;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.PreviewPagerAdapter$OnPrimaryItemSetListener */
    /* loaded from: classes4.dex */
    public interface OnPrimaryItemSetListener {
        void onPrimaryItemSet(int i);
    }

    public PreviewPagerAdapter(FragmentManager fragmentManager, OnPrimaryItemSetListener onPrimaryItemSetListener) {
        super(fragmentManager);
        this.mListener = onPrimaryItemSetListener;
    }

    @Override // android.support.p002v4.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return PreviewItemFragment.newInstance(this.mItems.get(i));
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.mItems.size();
    }

    @Override // android.support.p002v4.app.FragmentPagerAdapter, android.support.p002v4.view.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        super.setPrimaryItem(viewGroup, i, obj);
        OnPrimaryItemSetListener onPrimaryItemSetListener = this.mListener;
        if (onPrimaryItemSetListener != null) {
            onPrimaryItemSetListener.onPrimaryItemSet(i);
        }
    }

    public Item getMediaItem(int i) {
        return this.mItems.get(i);
    }

    public void addAll(List<Item> list) {
        this.mItems.addAll(list);
    }
}
