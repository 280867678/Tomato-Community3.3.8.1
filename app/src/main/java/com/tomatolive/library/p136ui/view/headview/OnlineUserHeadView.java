package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.headview.OnlineUserHeadView */
/* loaded from: classes3.dex */
public class OnlineUserHeadView extends LinearLayout {
    private TextView tvCount;

    public OnlineUserHeadView(Context context) {
        super(context);
        initView(context);
    }

    public OnlineUserHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_online_user, this);
        this.tvCount = (TextView) findViewById(R$id.tv_guard_number);
    }

    public void updateGuardCount(String str) {
        this.tvCount.setText(str);
    }
}
