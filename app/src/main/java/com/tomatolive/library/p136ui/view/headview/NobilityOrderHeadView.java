package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.view.headview.NobilityOrderHeadView */
/* loaded from: classes3.dex */
public class NobilityOrderHeadView extends LinearLayout {
    private ImageView ivAvatar;
    private ImageView ivBadge;
    private TextView tvAnchor;
    private TextView tvNickName;
    private TextView tvOverPay;
    private TextView tvValidDate;

    public NobilityOrderHeadView(Context context) {
        super(context);
        initView(context);
    }

    public NobilityOrderHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_nobility_open_order, this);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        this.tvAnchor = (TextView) findViewById(R$id.tv_anchor);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvOverPay = (TextView) findViewById(R$id.tv_over_pay);
        this.tvValidDate = (TextView) findViewById(R$id.tv_valid_date);
        this.tvValidDate.setText(getContext().getString(R$string.fq_nobility_open_valid_date, "1"));
        this.tvOverPay.setText(R$string.fq_userover_loading);
        GlideUtils.loadAvatar(getContext(), this.ivAvatar, UserInfoManager.getInstance().getAvatar());
        this.tvNickName.setText(UserInfoManager.getInstance().getUserNickname());
    }

    public void initData(String str, String str2, boolean z) {
        isHideBenefitAnchor(z);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            this.tvAnchor.setText("");
            return;
        }
        this.tvAnchor.setText(getContext().getString(R$string.fq_nobility_benefit_anchor, StringUtils.formatStrLen(str, 5), str2));
    }

    public void updateBadgeIcon(int i) {
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(i));
    }

    public void updateValidDate(String str) {
        this.tvValidDate.setText(getContext().getString(R$string.fq_nobility_open_valid_date, str));
    }

    public void updateOverPay(String str) {
        this.tvOverPay.setText(getContext().getString(R$string.fq_nobility_over_available, AppUtils.formatDisplayPrice(str, true)));
    }

    public void updateOverPayFail() {
        this.tvOverPay.setText(R$string.fq_userover_loading_fail);
    }

    public void isHideBenefitAnchor(boolean z) {
        this.tvAnchor.setVisibility(z ? 0 : 4);
    }
}
