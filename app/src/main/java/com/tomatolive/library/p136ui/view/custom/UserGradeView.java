package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.UserGradeView */
/* loaded from: classes3.dex */
public class UserGradeView extends RelativeLayout {
    private boolean isSmallIcon;
    private boolean isWhiteStroke;
    private Context mContext;
    private TextView tvGrade;

    public UserGradeView(Context context) {
        this(context, null);
    }

    public UserGradeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isWhiteStroke = false;
        this.isSmallIcon = false;
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_layout_user_grade, this);
        this.tvGrade = (TextView) findViewById(R$id.tv_grade);
        AppUtils.formatTvNumTypeface(this.mContext, this.tvGrade, "");
    }

    public void initUserGrade(String str) {
        initUserGrade(NumberUtils.string2int(AppUtils.formatExpGrade(str), 1));
    }

    public void initUserGradeMsg(String str, boolean z) {
        this.isWhiteStroke = z;
        initUserGrade(NumberUtils.string2int(AppUtils.formatExpGrade(str), 1));
    }

    public void initUserGrade(int i) {
        this.tvGrade.setBackgroundResource(AppUtils.getUserGradeBackgroundResource(this.isWhiteStroke, i));
        setTextViewDrawable(AppUtils.getUserGradeIconResource(this.isSmallIcon, i));
        this.tvGrade.setText(String.valueOf(i));
    }

    private void setTextViewDrawable(@DrawableRes int i) {
        this.tvGrade.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this.mContext, i), (Drawable) null, (Drawable) null, (Drawable) null);
    }
}
