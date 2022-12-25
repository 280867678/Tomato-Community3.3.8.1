package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.UserNickNameGradeView */
/* loaded from: classes3.dex */
public class UserNickNameGradeView extends LinearLayout {
    private AnchorGradeView anchorGradeView;
    private ImageView ivBadge;
    private ImageView ivGender;
    private Context mContext;
    private TextView tvNickName;
    private UserGradeView userGradeView;

    public UserNickNameGradeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LinearLayout.inflate(this.mContext, R$layout.fq_layout_user_nickname_grade, this);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.userGradeView = (UserGradeView) findViewById(R$id.grade_view);
        this.anchorGradeView = (AnchorGradeView) findViewById(R$id.anchor_grade_view);
        this.ivGender = (ImageView) findViewById(R$id.iv_gender);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
    }

    public void initData(String str, @ColorRes int i, String str2, String str3) {
        initData(str, i, str2, str3, -1);
    }

    public void initData(String str, @ColorRes int i, String str2, String str3, int i2) {
        this.tvNickName.setTextColor(ContextCompat.getColor(this.mContext, i));
        this.ivBadge.setVisibility(AppUtils.isNobilityUser(i2) ? 0 : 8);
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeMsgDrawableRes(i2));
        initData(str, str2, str3);
    }

    public void initData(String str, String str2, String str3) {
        this.userGradeView.setVisibility(0);
        this.anchorGradeView.setVisibility(8);
        this.tvNickName.setText(AppUtils.formatUserNickName(str));
        if (AppUtils.getGenderRes(str2) != -1) {
            this.ivGender.setImageResource(AppUtils.getGenderRes(str2));
        }
        this.userGradeView.initUserGrade(str3);
    }

    public void initAnchorData(String str, @ColorRes int i, String str2, String str3) {
        initAnchorData(str, i, str2, str3, -1);
    }

    public void initAnchorData(String str, @ColorRes int i, String str2, String str3, int i2) {
        this.tvNickName.setTextColor(ContextCompat.getColor(this.mContext, i));
        this.ivBadge.setVisibility(AppUtils.isNobilityUser(i2) ? 0 : 8);
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeMsgDrawableRes(i2));
        initAnchorData(str, str2, str3);
    }

    public void initAnchorData(String str, String str2, String str3) {
        this.userGradeView.setVisibility(8);
        this.anchorGradeView.setVisibility(0);
        this.tvNickName.setText(AppUtils.formatUserNickName(str));
        if (AppUtils.getGenderRes(str2) != -1) {
            this.ivGender.setImageResource(AppUtils.getGenderRes(str2));
        }
        this.anchorGradeView.initUserGrade(str3);
    }

    public void initAnchorData(SpannableString spannableString, String str, String str2, int i) {
        int i2 = 8;
        this.userGradeView.setVisibility(8);
        ImageView imageView = this.ivBadge;
        if (AppUtils.isNobilityUser(i)) {
            i2 = 0;
        }
        imageView.setVisibility(i2);
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeMsgDrawableRes(i));
        this.anchorGradeView.setVisibility(0);
        this.tvNickName.setText(spannableString);
        if (AppUtils.getGenderRes(str) != -1) {
            this.ivGender.setImageResource(AppUtils.getGenderRes(str));
        }
        this.anchorGradeView.initUserGrade(str2);
    }
}
