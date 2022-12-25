package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;

/* renamed from: com.tomatolive.library.ui.view.custom.AddressInfoView */
/* loaded from: classes3.dex */
public class AddressInfoView extends RelativeLayout {
    private Context mContext;
    private TextView mTextView;
    private TextView mTvHasGivenPrize;

    public AddressInfoView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_hd_address_info_view, this);
        this.mTextView = (TextView) findViewById(R$id.tv_address);
        this.mTvHasGivenPrize = (TextView) findViewById(R$id.tv_has_given_prize);
    }

    public void setText(String str) {
        this.mTextView.setText(str);
    }

    public void showHasGivePrizeBtn(View.OnClickListener onClickListener) {
        this.mTvHasGivenPrize.setVisibility(0);
        this.mTvHasGivenPrize.setOnClickListener(onClickListener);
    }
}
