package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentStatePagerAdapter;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.utils.StringUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.GiftTagAdapter */
/* loaded from: classes3.dex */
public class GiftTagAdapter extends FragmentStatePagerAdapter {
    private List<GiftDownloadItemEntity> entityList;
    private List<BaseFragment> mFragments;

    public GiftTagAdapter(List<BaseFragment> list, List<GiftDownloadItemEntity> list2, FragmentManager fragmentManager) {
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
        return StringUtils.formatStrLen(this.entityList.get(i).name, 4);
    }
}
