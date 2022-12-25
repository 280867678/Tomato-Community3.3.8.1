package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.view.custom.AnchorGradeView;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.headview.RankingHeadView */
/* loaded from: classes3.dex */
public class RankingHeadView extends RelativeLayout implements View.OnClickListener {
    private AnchorGradeView anchorGradeView;
    private ImageView ivAttention;
    private ImageView ivAvatar;
    private ImageView ivBadge;
    private ImageView ivLive;
    private ImageView ivSex;
    private List<AnchorEntity> list;
    private LinearLayout llGradeBg;
    private LinearLayout llMysteryBg;
    private OnUserCardCallback onUserCardCallback;
    private RelativeLayout rlContentBg;
    private TextView tvMe;
    private TextView tvNickName;
    private TextView tvNums;
    private UserGradeView userGradeView;

    public RankingHeadView(Context context) {
        this(context, null);
    }

    public RankingHeadView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RankingHeadView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(getContext(), R$layout.fq_layout_head_view_ranking, this);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivSex = (ImageView) findViewById(R$id.iv_gender_sex);
        this.ivLive = (ImageView) findViewById(R$id.iv_live);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvNums = (TextView) findViewById(R$id.tv_num);
        this.userGradeView = (UserGradeView) findViewById(R$id.grade_view);
        this.anchorGradeView = (AnchorGradeView) findViewById(R$id.anchor_grade_view);
        this.ivAttention = (ImageView) findViewById(R$id.iv_attention);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        this.llMysteryBg = (LinearLayout) findViewById(R$id.ll_mystery_bg);
        this.llGradeBg = (LinearLayout) findViewById(R$id.ll_grade_bg);
        this.tvMe = (TextView) findViewById(R$id.tv_me);
        this.rlContentBg = (RelativeLayout) findViewById(R$id.rl_content_bg);
        this.ivAvatar.setOnClickListener(this);
        this.ivAttention.setOnClickListener(this);
    }

    public void initData(List<AnchorEntity> list, int i) {
        this.list = list;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i2 = 0;
        AnchorEntity anchorEntity = list.get(0);
        int i3 = 8;
        if (i == 5 && anchorEntity.isRankHideBoolean()) {
            this.rlContentBg.setBackground(ContextCompat.getDrawable(getContext(), R$drawable.fq_shape_nobility_stealth_top_bg));
            this.llMysteryBg.setVisibility(0);
            this.ivAttention.setVisibility(4);
            this.llGradeBg.setVisibility(8);
            this.ivAvatar.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
            TextView textView = this.tvMe;
            if (!TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId())) {
                i2 = 8;
            }
            textView.setVisibility(i2);
            this.tvNickName.setText(R$string.fq_mystery_man);
            this.ivLive.setVisibility(4);
            this.tvNums.setText(getNumStr(i, i == 4 ? anchorEntity.income : anchorEntity.expend));
            return;
        }
        this.rlContentBg.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R$color.fq_color_transparent)));
        this.llGradeBg.setVisibility(0);
        this.llMysteryBg.setVisibility(4);
        GlideUtils.loadAvatar(getContext(), this.ivAvatar, anchorEntity.avatar);
        this.ivAttention.setVisibility(i == 4 ? 0 : 4);
        this.ivAttention.setSelected(anchorEntity.isAttention());
        this.tvNickName.setText(anchorEntity.nickname);
        this.tvNums.setText(getNumStr(i, i == 4 ? anchorEntity.income : anchorEntity.expend));
        this.ivBadge.setVisibility(AppUtils.isNobilityUser(anchorEntity.nobilityType) ? 0 : 8);
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeMsgDrawableRes(anchorEntity.nobilityType));
        this.anchorGradeView.setVisibility(i == 4 ? 0 : 8);
        UserGradeView userGradeView = this.userGradeView;
        if (i != 4) {
            i3 = 0;
        }
        userGradeView.setVisibility(i3);
        this.anchorGradeView.initUserGrade(anchorEntity.expGrade);
        this.userGradeView.initUserGrade(anchorEntity.expGrade);
        this.ivSex.setImageResource(AppUtils.getGenderRes(anchorEntity.sex));
        if (i == 4) {
            ImageView imageView = this.ivLive;
            if (!AppUtils.isLiving(anchorEntity.liveStatus)) {
                i2 = 4;
            }
            imageView.setVisibility(i2);
            GlideUtils.loadLivingGif(getContext(), this.ivLive);
            return;
        }
        this.ivLive.setVisibility(4);
    }

    private String getNumStr(int i, String str) {
        StringBuilder sb = new StringBuilder();
        if (i == 4) {
            sb.append(getContext().getString(R$string.fq_tomato_money_gain, str));
        } else {
            sb.append(getContext().getString(R$string.fq_tomato_money_reward, str));
        }
        return sb.toString();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnUserCardCallback onUserCardCallback;
        OnUserCardCallback onUserCardCallback2;
        if (this.list == null) {
            return;
        }
        if (view.getId() == R$id.iv_avatar) {
            if (this.list.size() < 1 || (onUserCardCallback2 = this.onUserCardCallback) == null) {
                return;
            }
            onUserCardCallback2.onAnchorItemClickListener(this.list.get(0));
        } else if (view.getId() != R$id.iv_attention || this.list.size() < 1 || (onUserCardCallback = this.onUserCardCallback) == null) {
        } else {
            onUserCardCallback.onAttentionAnchorListener(view, this.list.get(0));
        }
    }

    public OnUserCardCallback getOnUserCardCallback() {
        return this.onUserCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }
}
