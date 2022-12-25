package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;

/* compiled from: ReviewWGItemView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.ReviewWGItemView */
/* loaded from: classes3.dex */
public final class ReviewWGItemView extends RelativeLayout {
    public ReviewWGItemView(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_wg_item_view, this);
    }

    public ReviewWGItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_wg_item_view, this);
    }

    public ReviewWGItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_wg_item_view, this);
    }
}
