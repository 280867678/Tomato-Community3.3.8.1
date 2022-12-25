package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.headview.PrivateMsgTipsView */
/* loaded from: classes3.dex */
public class PrivateMsgTipsView extends LinearLayout {
    private boolean isFromAnchor;

    public PrivateMsgTipsView(Context context, boolean z) {
        super(context);
        this.isFromAnchor = false;
        this.isFromAnchor = z;
        initView(context);
    }

    public PrivateMsgTipsView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isFromAnchor = false;
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_private_msg_tips, this);
        TextView textView = (TextView) findViewById(R$id.tv_tips);
        if (!this.isFromAnchor) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
    }
}
