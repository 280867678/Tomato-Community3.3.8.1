package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.headview.VipFooterView */
/* loaded from: classes3.dex */
public class VipFooterView extends LinearLayout {
    public VipFooterView(Context context) {
        super(context);
        initView(context);
    }

    public VipFooterView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_footer_view_vip, this);
    }
}
