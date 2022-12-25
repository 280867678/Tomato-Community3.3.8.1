package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.AnchorGradeView */
/* loaded from: classes3.dex */
public class AnchorGradeView extends RelativeLayout {
    private ImageView ivGrade;
    private final Context mContext;
    private TextView tvGrade;

    public AnchorGradeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_layout_anchor_grade, this);
        this.tvGrade = (TextView) findViewById(R$id.tv_grade);
        this.ivGrade = (ImageView) findViewById(R$id.iv_grade);
        AppUtils.formatTvNumTypeface(this.mContext, this.tvGrade, "");
    }

    public void initUserGrade(String str) {
        initUserGrade(NumberUtils.string2int(AppUtils.formatExpGrade(str), 1));
    }

    public void initUserGrade(int i) {
        switch (AppUtils.getAnchorGradeInterval(i)) {
            case 1:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_1);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_1);
                return;
            case 2:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_2);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_2);
                return;
            case 3:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_3);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_3);
                return;
            case 4:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_4);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_4);
                return;
            case 5:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_5);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_5);
                return;
            case 6:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_6);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_6);
                return;
            default:
                this.tvGrade.setBackgroundResource(R$drawable.fq_shape_user_grade_bg_1);
                this.tvGrade.setText(String.valueOf(i));
                this.ivGrade.setImageResource(R$drawable.fq_ic_grade_anchor_1);
                return;
        }
    }
}
