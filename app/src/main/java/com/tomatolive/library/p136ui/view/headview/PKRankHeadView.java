package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.headview.PKRankHeadView */
/* loaded from: classes3.dex */
public class PKRankHeadView extends LinearLayout {
    private List<AnchorEntity> anchorList;
    private FrameLayout flSexGradeBg_1;
    private FrameLayout flSexGradeBg_2;
    private FrameLayout flSexGradeBg_3;
    private UserGradeView gradeView_1;
    private UserGradeView gradeView_2;
    private UserGradeView gradeView_3;
    private ImageView ivAvatar_1;
    private ImageView ivAvatar_2;
    private ImageView ivAvatar_3;
    private ImageView ivBadge_1;
    private ImageView ivBadge_2;
    private ImageView ivBadge_3;
    private ImageView ivGender_1;
    private ImageView ivGender_2;
    private ImageView ivGender_3;
    private ImageView iv_guard_1;
    private ImageView iv_guard_2;
    private ImageView iv_guard_3;
    private LinearLayout llGradeBg_1;
    private LinearLayout llGradeBg_2;
    private LinearLayout llGradeBg_3;
    private LinearLayout llMysteryBg_1;
    private LinearLayout llMysteryBg_2;
    private LinearLayout llMysteryBg_3;
    private OnUserCardCallback onUserCardCallback;
    private TextView tvContribution_1;
    private TextView tvContribution_2;
    private TextView tvContribution_3;
    private TextView tvMysteryMe_1;
    private TextView tvMysteryMe_2;
    private TextView tvMysteryMe_3;
    private TextView tvNickName_1;
    private TextView tvNickName_2;
    private TextView tvNickName_3;

    public PKRankHeadView(Context context) {
        super(context);
        initView(context);
    }

    public PKRankHeadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_pk_rank, this);
        this.ivAvatar_1 = (ImageView) findViewById(R$id.iv_avatar_1);
        this.tvNickName_1 = (TextView) findViewById(R$id.tv_nick_name_1);
        this.llMysteryBg_1 = (LinearLayout) findViewById(R$id.ll_mystery_bg_1);
        this.tvMysteryMe_1 = (TextView) findViewById(R$id.tv_mystery_me_1);
        this.ivGender_1 = (ImageView) findViewById(R$id.iv_gender_1);
        this.gradeView_1 = (UserGradeView) findViewById(R$id.grade_view_1);
        this.ivBadge_1 = (ImageView) findViewById(R$id.iv_badge_1);
        this.iv_guard_1 = (ImageView) findViewById(R$id.iv_guard_1);
        this.flSexGradeBg_1 = (FrameLayout) findViewById(R$id.fl_sex_grade_bg_1);
        this.llGradeBg_1 = (LinearLayout) findViewById(R$id.ll_grade_bg_1);
        this.tvContribution_1 = (TextView) findViewById(R$id.tv_contribution_1);
        this.ivAvatar_2 = (ImageView) findViewById(R$id.iv_avatar_2);
        this.tvNickName_2 = (TextView) findViewById(R$id.tv_nick_name_2);
        this.llMysteryBg_2 = (LinearLayout) findViewById(R$id.ll_mystery_bg_2);
        this.tvMysteryMe_2 = (TextView) findViewById(R$id.tv_mystery_me_2);
        this.ivGender_2 = (ImageView) findViewById(R$id.iv_gender_2);
        this.gradeView_2 = (UserGradeView) findViewById(R$id.grade_view_2);
        this.ivBadge_2 = (ImageView) findViewById(R$id.iv_badge_2);
        this.iv_guard_2 = (ImageView) findViewById(R$id.iv_guard_2);
        this.flSexGradeBg_2 = (FrameLayout) findViewById(R$id.fl_sex_grade_bg_2);
        this.llGradeBg_2 = (LinearLayout) findViewById(R$id.ll_grade_bg_2);
        this.tvContribution_2 = (TextView) findViewById(R$id.tv_contribution_2);
        this.ivAvatar_3 = (ImageView) findViewById(R$id.iv_avatar_3);
        this.tvNickName_3 = (TextView) findViewById(R$id.tv_nick_name_3);
        this.llMysteryBg_3 = (LinearLayout) findViewById(R$id.ll_mystery_bg_3);
        this.tvMysteryMe_3 = (TextView) findViewById(R$id.tv_mystery_me_3);
        this.ivGender_3 = (ImageView) findViewById(R$id.iv_gender_3);
        this.gradeView_3 = (UserGradeView) findViewById(R$id.grade_view_3);
        this.ivBadge_3 = (ImageView) findViewById(R$id.iv_badge_3);
        this.iv_guard_3 = (ImageView) findViewById(R$id.iv_guard_3);
        this.flSexGradeBg_3 = (FrameLayout) findViewById(R$id.fl_sex_grade_bg_3);
        this.llGradeBg_3 = (LinearLayout) findViewById(R$id.ll_grade_bg_3);
        this.tvContribution_3 = (TextView) findViewById(R$id.tv_contribution_3);
        this.ivAvatar_1.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$PKRankHeadView$cgv4OZf_eZHlL74meRftRdQPqbc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKRankHeadView.this.lambda$initView$0$PKRankHeadView(view);
            }
        });
        this.ivAvatar_2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$PKRankHeadView$GDHgt-5ckgfM4d01ycW51eOi-Z0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKRankHeadView.this.lambda$initView$1$PKRankHeadView(view);
            }
        });
        this.ivAvatar_3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$PKRankHeadView$EnJQ6licQhYD6NO9vz9_HM4cXjs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PKRankHeadView.this.lambda$initView$2$PKRankHeadView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$PKRankHeadView(View view) {
        OnUserCardCallback onUserCardCallback;
        List<AnchorEntity> list = this.anchorList;
        if (list == null || list.size() < 1 || (onUserCardCallback = this.onUserCardCallback) == null) {
            return;
        }
        onUserCardCallback.onAnchorItemClickListener(this.anchorList.get(0));
    }

    public /* synthetic */ void lambda$initView$1$PKRankHeadView(View view) {
        OnUserCardCallback onUserCardCallback;
        List<AnchorEntity> list = this.anchorList;
        if (list == null || list.size() < 2 || (onUserCardCallback = this.onUserCardCallback) == null) {
            return;
        }
        onUserCardCallback.onAnchorItemClickListener(this.anchorList.get(1));
    }

    public /* synthetic */ void lambda$initView$2$PKRankHeadView(View view) {
        OnUserCardCallback onUserCardCallback;
        List<AnchorEntity> list = this.anchorList;
        if (list == null || list.size() < 3 || (onUserCardCallback = this.onUserCardCallback) == null) {
            return;
        }
        onUserCardCallback.onAnchorItemClickListener(this.anchorList.get(2));
    }

    public void initData(List<AnchorEntity> list) {
        this.anchorList = list;
        if (list == null || list.isEmpty()) {
            updateItemData(this.ivAvatar_1, this.tvNickName_1, this.llMysteryBg_1, this.tvMysteryMe_1, this.ivGender_1, this.ivBadge_1, this.gradeView_1, this.flSexGradeBg_1, this.llGradeBg_1, this.tvContribution_1, this.iv_guard_1, null);
            updateItemData(this.ivAvatar_2, this.tvNickName_2, this.llMysteryBg_2, this.tvMysteryMe_2, this.ivGender_2, this.ivBadge_2, this.gradeView_2, this.flSexGradeBg_2, this.llGradeBg_2, this.tvContribution_2, this.iv_guard_2, null);
            updateItemData(this.ivAvatar_3, this.tvNickName_3, this.llMysteryBg_3, this.tvMysteryMe_3, this.ivGender_3, this.ivBadge_3, this.gradeView_3, this.flSexGradeBg_3, this.llGradeBg_3, this.tvContribution_3, this.iv_guard_3, null);
        } else if (list.size() >= 3) {
            updateItemData(this.ivAvatar_1, this.tvNickName_1, this.llMysteryBg_1, this.tvMysteryMe_1, this.ivGender_1, this.ivBadge_1, this.gradeView_1, this.flSexGradeBg_1, this.llGradeBg_1, this.tvContribution_1, this.iv_guard_1, list.get(0));
            updateItemData(this.ivAvatar_2, this.tvNickName_2, this.llMysteryBg_2, this.tvMysteryMe_2, this.ivGender_2, this.ivBadge_2, this.gradeView_2, this.flSexGradeBg_2, this.llGradeBg_2, this.tvContribution_2, this.iv_guard_2, list.get(1));
            updateItemData(this.ivAvatar_3, this.tvNickName_3, this.llMysteryBg_3, this.tvMysteryMe_3, this.ivGender_3, this.ivBadge_3, this.gradeView_3, this.flSexGradeBg_3, this.llGradeBg_3, this.tvContribution_3, this.iv_guard_3, list.get(2));
        } else if (list.size() >= 2) {
            updateItemData(this.ivAvatar_1, this.tvNickName_1, this.llMysteryBg_1, this.tvMysteryMe_1, this.ivGender_1, this.ivBadge_1, this.gradeView_1, this.flSexGradeBg_1, this.llGradeBg_1, this.tvContribution_1, this.iv_guard_1, list.get(0));
            updateItemData(this.ivAvatar_2, this.tvNickName_2, this.llMysteryBg_2, this.tvMysteryMe_2, this.ivGender_2, this.ivBadge_2, this.gradeView_2, this.flSexGradeBg_2, this.llGradeBg_2, this.tvContribution_2, this.iv_guard_2, list.get(1));
            updateItemData(this.ivAvatar_3, this.tvNickName_3, this.llMysteryBg_3, this.tvMysteryMe_3, this.ivGender_3, this.ivBadge_3, this.gradeView_3, this.flSexGradeBg_3, this.llGradeBg_3, this.tvContribution_3, this.iv_guard_3, null);
        } else {
            updateItemData(this.ivAvatar_1, this.tvNickName_1, this.llMysteryBg_1, this.tvMysteryMe_1, this.ivGender_1, this.ivBadge_1, this.gradeView_1, this.flSexGradeBg_1, this.llGradeBg_1, this.tvContribution_1, this.iv_guard_1, list.get(0));
            updateItemData(this.ivAvatar_2, this.tvNickName_2, this.llMysteryBg_2, this.tvMysteryMe_2, this.ivGender_2, this.ivBadge_2, this.gradeView_2, this.flSexGradeBg_2, this.llGradeBg_2, this.tvContribution_2, this.iv_guard_2, null);
            updateItemData(this.ivAvatar_3, this.tvNickName_3, this.llMysteryBg_3, this.tvMysteryMe_3, this.ivGender_3, this.ivBadge_3, this.gradeView_3, this.flSexGradeBg_3, this.llGradeBg_3, this.tvContribution_3, this.iv_guard_3, null);
        }
    }

    public OnUserCardCallback getOnUserCardCallback() {
        return this.onUserCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    private void updateItemData(ImageView imageView, TextView textView, LinearLayout linearLayout, TextView textView2, ImageView imageView2, ImageView imageView3, UserGradeView userGradeView, FrameLayout frameLayout, LinearLayout linearLayout2, TextView textView3, ImageView imageView4, AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            imageView.setImageResource(R$drawable.fq_ic_pk_rank_sofa);
            textView.setText("-");
            frameLayout.setVisibility(4);
            textView3.setVisibility(4);
            return;
        }
        frameLayout.setVisibility(0);
        textView3.setVisibility(0);
        int i = 8;
        if (anchorEntity.isRankHideBoolean()) {
            linearLayout2.setVisibility(8);
            linearLayout.setVisibility(0);
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
            if (TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId())) {
                i = 0;
            }
            textView2.setVisibility(i);
            textView.setText(R$string.fq_mystery_man);
            textView3.setText(getContext().getString(R$string.fq_tomato_money_reward, anchorEntity.f5827fp));
            return;
        }
        linearLayout2.setVisibility(0);
        linearLayout.setVisibility(8);
        textView.setText(StringUtils.formatStrLen(anchorEntity.name, 7));
        GlideUtils.loadAvatar(getContext(), imageView, anchorEntity.avatar);
        userGradeView.initUserGrade(anchorEntity.expGrade);
        imageView2.setImageResource(AppUtils.getGenderRes(anchorEntity.sex));
        imageView3.setVisibility(AppUtils.isNobilityUser(anchorEntity.nobilityType) ? 0 : 8);
        imageView3.setImageResource(AppUtils.getNobilityBadgeMsgDrawableRes(anchorEntity.nobilityType));
        if (AppUtils.isGuardUser(NumberUtils.string2int(anchorEntity.guardType))) {
            i = 0;
        }
        imageView4.setVisibility(i);
        imageView4.setImageResource(AppUtils.isYearGuard(anchorEntity.guardType) ? R$drawable.fq_ic_live_msg_year_guard_big : R$drawable.fq_ic_live_msg_mouth_guard_big);
        textView3.setText(getContext().getString(R$string.fq_tomato_money_reward, anchorEntity.f5827fp));
    }
}
