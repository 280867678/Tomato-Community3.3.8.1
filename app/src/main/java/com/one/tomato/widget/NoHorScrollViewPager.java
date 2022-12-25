package com.one.tomato.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewPager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class NoHorScrollViewPager extends ViewPager {
    public NoHorScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoHorScrollViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.view.ViewPager
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        Object tag;
        if (view.getVisibility() == 0 && view != this && (view instanceof RecyclerView)) {
            RecyclerView recyclerView = (RecyclerView) view;
            if ((recyclerView.getLayoutManager() instanceof LinearLayoutManager) && ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation() == 0 && (tag = view.getTag(R.id.image_list_item_id)) != null && !((Boolean) tag).booleanValue()) {
                return true;
            }
        }
        return super.canScroll(view, z, i, i2, i3);
    }
}
