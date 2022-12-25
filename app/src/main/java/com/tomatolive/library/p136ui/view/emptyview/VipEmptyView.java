package com.tomatolive.library.p136ui.view.emptyview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.emptyview.VipEmptyView */
/* loaded from: classes3.dex */
public class VipEmptyView extends LinearLayout {
    public VipEmptyView(Context context) {
        this(context, null);
    }

    public VipEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VipEmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_empty_view_vip, this);
    }
}
