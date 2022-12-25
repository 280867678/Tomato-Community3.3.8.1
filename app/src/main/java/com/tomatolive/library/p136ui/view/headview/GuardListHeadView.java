package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.headview.GuardListHeadView */
/* loaded from: classes3.dex */
public class GuardListHeadView extends LinearLayout {
    private ImageView ivAvatar;
    private ImageView ivGender;
    private Context mContext;
    private TextView tvMoney;
    private TextView tvName;
    private UserGradeView userGrade;

    public GuardListHeadView(Context context) {
        super(context);
        initView(context);
    }

    public GuardListHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_guard_list, this);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.tvName = (TextView) findViewById(R$id.tv_name);
        this.tvMoney = (TextView) findViewById(R$id.tv_money);
        this.ivGender = (ImageView) findViewById(R$id.iv_gender);
        this.userGrade = (UserGradeView) findViewById(R$id.user_grade);
    }

    public void initData(List<AnchorEntity> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        AnchorEntity anchorEntity = list.get(0);
        if (NumberUtils.string2int(anchorEntity.contribution, 0) == 0) {
            this.tvName.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_gray));
            this.tvName.setText(R$string.fq_text_list_empty_waiting);
            this.ivAvatar.setImageResource(R$drawable.fq_ic_guard_sofa);
            this.tvMoney.setText("");
            this.ivGender.setVisibility(4);
            this.userGrade.setVisibility(4);
            return;
        }
        this.ivGender.setVisibility(0);
        this.userGrade.setVisibility(0);
        this.tvName.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, anchorEntity.avatar, R$drawable.fq_ic_placeholder_avatar);
        this.tvName.setText(anchorEntity.nickname);
        this.ivGender.setImageResource(AppUtils.getGenderRes(anchorEntity.sex));
        this.tvMoney.setText(this.mContext.getString(R$string.fq_tomato_money_reward, anchorEntity.contribution));
        this.userGrade.initUserGrade(anchorEntity.expGrade);
    }
}
