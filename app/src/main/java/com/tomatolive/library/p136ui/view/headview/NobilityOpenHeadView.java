package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.headview.NobilityOpenHeadView */
/* loaded from: classes3.dex */
public class NobilityOpenHeadView extends LinearLayout {
    private ImageView ivBadge;
    private TextView tvBadgeName;
    private TextView tvPrivilegeCount;

    private int getCount(int i) {
        switch (i) {
            case 1:
                return 5;
            case 2:
            case 3:
                return 6;
            case 4:
                return 8;
            case 5:
                return 9;
            case 6:
                return 10;
            case 7:
                return 14;
            default:
                return 5;
        }
    }

    public NobilityOpenHeadView(Context context) {
        super(context);
        initView(context);
    }

    public NobilityOpenHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_nobility_open, this);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        this.tvBadgeName = (TextView) findViewById(R$id.tv_badge_name);
        this.tvPrivilegeCount = (TextView) findViewById(R$id.tv_privilege_count);
    }

    public void initData(int i) {
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(i));
        this.tvBadgeName.setText(getContext().getString(R$string.fq_nobility_open_badge_name, AppUtils.getNobilityBadgeName(getContext(), i)));
        this.tvBadgeName.setTextColor(ContextCompat.getColor(getContext(), getNameColor(i)));
        this.tvPrivilegeCount.setText(getContext().getString(R$string.fq_nobility_open_privilege_count, String.valueOf(getCount(i))));
    }

    @ColorRes
    private int getNameColor(int i) {
        switch (i) {
            case 1:
                return R$color.fq_nobility_badge_1;
            case 2:
                return R$color.fq_nobility_badge_2;
            case 3:
                return R$color.fq_nobility_badge_3;
            case 4:
                return R$color.fq_nobility_badge_4;
            case 5:
                return R$color.fq_nobility_badge_5;
            case 6:
                return R$color.fq_nobility_badge_6;
            case 7:
                return R$color.fq_nobility_badge_7;
            default:
                return R$color.fq_nobility_badge_1;
        }
    }
}
