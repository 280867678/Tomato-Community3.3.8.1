package com.tomatolive.library.p136ui.view.emptyview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;

/* renamed from: com.tomatolive.library.ui.view.emptyview.LotteryTopEmptyView */
/* loaded from: classes3.dex */
public class LotteryTopEmptyView extends LinearLayout {
    private int emptyType;
    private Context mContext;

    public LotteryTopEmptyView(Context context) {
        this(context, null, 43);
    }

    public LotteryTopEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 43);
    }

    public LotteryTopEmptyView(Context context, int i) {
        this(context, null, i);
    }

    public LotteryTopEmptyView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, 0, i);
    }

    public LotteryTopEmptyView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mContext = context;
        this.emptyType = i2;
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_empty_view_lottery_top, this);
        TextView textView = (TextView) findViewById(R$id.tv_empty_btn);
        ImageView imageView = (ImageView) findViewById(R$id.iv_empty_img);
        ((TextView) findViewById(R$id.tv_empty_tips)).setText(getEmptyText());
        textView.setText(this.emptyType == 43 ? R$string.fq_lottery_goto : R$string.fq_lottery_me_try);
        imageView.setImageResource(this.emptyType == 43 ? R$drawable.fq_ic_empty_lottery_top_anchor : R$drawable.fq_ic_empty_lottery_top_user);
    }

    private String getEmptyText() {
        int i = this.emptyType;
        if (i != 43) {
            if (i == 44) {
                return this.mContext.getString(R$string.fq_lottery_user_top_empty_tips);
            }
            return this.mContext.getString(R$string.fq_text_list_empty);
        }
        return this.mContext.getString(R$string.fq_lottery_anchor_top_empty_tips);
    }

    public void setOnEventListener(View.OnClickListener onClickListener) {
        findViewById(R$id.tv_empty_btn).setOnClickListener(onClickListener);
    }
}
