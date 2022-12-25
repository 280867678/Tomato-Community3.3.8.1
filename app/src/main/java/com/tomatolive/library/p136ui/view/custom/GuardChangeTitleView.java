package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.GuardChangeTitleView */
/* loaded from: classes3.dex */
public class GuardChangeTitleView extends RelativeLayout {
    private GuardItemEntity guardItemEntity;
    private ImageView ivArrow;
    private ImageView ivLabel;
    private Context mContext;
    private TextView tvMoney;
    private TextView tvTitle;

    public GuardChangeTitleView(Context context) {
        super(context);
        initView(context);
    }

    public GuardChangeTitleView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_layout_guard_change_title_view, this);
        this.mContext = context;
        this.ivLabel = (ImageView) findViewById(R$id.iv_label);
        this.ivArrow = (ImageView) findViewById(R$id.iv_arrow);
        this.tvTitle = (TextView) findViewById(R$id.tv_guard_title);
        this.tvMoney = (TextView) findViewById(R$id.tv_guard_money);
    }

    public void initData(GuardItemEntity guardItemEntity, String str) {
        this.guardItemEntity = guardItemEntity;
        if (guardItemEntity == null) {
            return;
        }
        this.guardItemEntity.anchorId = str;
        this.ivLabel.setVisibility(TextUtils.equals(guardItemEntity.type, "1") ? 0 : 4);
        this.tvTitle.setText(guardItemEntity.name);
        AppUtils.formatTvNumTypeface(this.mContext, this.tvMoney, AppUtils.formatDisplayPrice(guardItemEntity.price, false));
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_guard_year_label);
        TextView textView = this.tvTitle;
        if (!TextUtils.equals(guardItemEntity.type, "3")) {
            drawable = null;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    public void showArrow(boolean z) {
        this.ivArrow.setVisibility(z ? 0 : 4);
    }

    public GuardItemEntity getGuardItemEntity() {
        return this.guardItemEntity;
    }

    @Override // android.view.View
    public boolean isSelected() {
        return this.ivArrow.getVisibility() == 0;
    }
}
