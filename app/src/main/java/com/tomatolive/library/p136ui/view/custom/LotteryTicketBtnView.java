package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.custom.LotteryTicketBtnView */
/* loaded from: classes3.dex */
public class LotteryTicketBtnView extends RelativeLayout {
    private ImageView ivSmallTicket;
    private ImageView ivTicket;
    private LinearLayout llLotteryBtnBg;
    private TextView tvTicketName;

    public LotteryTicketBtnView(Context context) {
        super(context);
        initView(context);
    }

    public LotteryTicketBtnView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_layout_lottery_btn_view, this);
        this.ivTicket = (ImageView) findViewById(R$id.iv_ticket);
        this.ivSmallTicket = (ImageView) findViewById(R$id.iv_small_ticket);
        this.tvTicketName = (TextView) findViewById(R$id.tv_ticket_name);
        this.llLotteryBtnBg = (LinearLayout) findViewById(R$id.ll_lottery_btn_bg);
        initTicketBtnBg(false);
    }

    public void setTicketName(String str) {
        this.tvTicketName.setText(str);
    }

    public void setItemSelected(boolean z) {
        int i = 0;
        this.ivTicket.setVisibility(z ? 0 : 4);
        ImageView imageView = this.ivSmallTicket;
        if (z) {
            i = 4;
        }
        imageView.setVisibility(i);
        this.llLotteryBtnBg.setSelected(z);
    }

    public void initTicketBtnBg(boolean z) {
        this.llLotteryBtnBg.setBackgroundResource(z ? R$drawable.fq_shape_lottery_btn_full_bg : R$drawable.fq_shape_lottery_btn_bg);
        this.llLotteryBtnBg.setPadding(ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(4.5f), ConvertUtils.dp2px(20.0f), ConvertUtils.dp2px(4.5f));
    }
}
