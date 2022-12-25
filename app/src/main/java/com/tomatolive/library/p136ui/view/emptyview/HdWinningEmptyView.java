package com.tomatolive.library.p136ui.view.emptyview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.emptyview.HdWinningEmptyView */
/* loaded from: classes3.dex */
public class HdWinningEmptyView extends LinearLayout {
    public HdWinningEmptyView(Context context) {
        this(context, null);
    }

    public HdWinningEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HdWinningEmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_hd_winning_empty_view, this);
    }
}
