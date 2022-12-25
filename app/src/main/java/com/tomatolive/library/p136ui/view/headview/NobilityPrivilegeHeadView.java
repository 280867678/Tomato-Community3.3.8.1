package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MyNobilityEntity;
import com.tomatolive.library.p136ui.activity.noble.NobleHiddenInRankListActivity;
import com.tomatolive.library.p136ui.activity.noble.NobleRecommendActivity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.view.headview.NobilityPrivilegeHeadView */
/* loaded from: classes3.dex */
public class NobilityPrivilegeHeadView extends RelativeLayout {
    private CheckBox checkBox;
    private FrameLayout flAvatarBg;
    private boolean isRankHide;
    private ImageView ivAvatar;
    private ImageView ivTopBadge;
    private ImageView ivTopBg;
    private LinearLayout llNobleRankHidden;
    private LinearLayout llNobleRecommend;
    private LinearLayout llSettingBg;
    private LinearLayout llTipsBg1;
    private Context mContext;
    private String mEndTime;
    private String mRecommendNumber;
    private OnEnterHideListener onEnterHideListener;
    private TextView tvBadgeName;
    private TextView tvEndName;
    private TextView tvLave;
    private TextView tvNickName;
    private View vLine2;
    private View vLine3;

    /* renamed from: com.tomatolive.library.ui.view.headview.NobilityPrivilegeHeadView$OnEnterHideListener */
    /* loaded from: classes3.dex */
    public interface OnEnterHideListener {
        void onEnterHideListener(boolean z);
    }

    public NobilityPrivilegeHeadView(Context context) {
        this(context, null);
    }

    public NobilityPrivilegeHeadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(getContext(), R$layout.fq_layout_head_view_nobility_privilege, this);
        this.ivTopBg = (ImageView) findViewById(R$id.iv_top_bg);
        this.ivTopBadge = (ImageView) findViewById(R$id.iv_top_badge);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.tvBadgeName = (TextView) findViewById(R$id.tv_badge_name);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvEndName = (TextView) findViewById(R$id.tv_end_time);
        this.flAvatarBg = (FrameLayout) findViewById(R$id.fl_avatar_bg);
        this.tvLave = (TextView) findViewById(R$id.tv_lave);
        this.vLine2 = findViewById(R$id.v_divider_2);
        this.vLine3 = findViewById(R$id.v_divider_3);
        this.llTipsBg1 = (LinearLayout) findViewById(R$id.ll_tips_bg_1);
        this.llNobleRankHidden = (LinearLayout) findViewById(R$id.ll_noble_rank_hidden);
        this.llNobleRecommend = (LinearLayout) findViewById(R$id.ll_noble_recommend);
        this.llSettingBg = (LinearLayout) findViewById(R$id.ll_setting_bg);
        this.checkBox = (CheckBox) findViewById(R$id.cb_box);
        findViewById(R$id.tv_renewal_fee).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$NobilityPrivilegeHeadView$3aGYBu5lIogJTr-5C0yumh4irow
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobilityPrivilegeHeadView.this.lambda$initView$0$NobilityPrivilegeHeadView(view);
            }
        });
        initListener();
    }

    public /* synthetic */ void lambda$initView$0$NobilityPrivilegeHeadView(View view) {
        AppUtils.toNobilityOpenActivity(this.mContext, null);
    }

    private void initListener() {
        this.llNobleRankHidden.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$NobilityPrivilegeHeadView$pw0G4ZQ3R0iBuIm7t8heLpwesKU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobilityPrivilegeHeadView.this.lambda$initListener$1$NobilityPrivilegeHeadView(view);
            }
        });
        this.llNobleRecommend.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$NobilityPrivilegeHeadView$yFQb00z10SKQyPxwn9hngrFdzBs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobilityPrivilegeHeadView.this.lambda$initListener$2$NobilityPrivilegeHeadView(view);
            }
        });
        this.checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$NobilityPrivilegeHeadView$RqgfURsjuE2C96v1t2GfUR2bDLc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobilityPrivilegeHeadView.this.lambda$initListener$3$NobilityPrivilegeHeadView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$NobilityPrivilegeHeadView(View view) {
        Intent intent = new Intent(this.mContext, NobleHiddenInRankListActivity.class);
        intent.putExtra(ConstantUtils.RESULT_FLAG, this.isRankHide);
        this.mContext.startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$2$NobilityPrivilegeHeadView(View view) {
        Intent intent = new Intent(this.mContext, NobleRecommendActivity.class);
        intent.putExtra(NobleRecommendActivity.KEY_RECOMMEND_NUMBER, this.mRecommendNumber);
        intent.putExtra(NobleRecommendActivity.KEY_RECOMMEND_END_TIME, this.mEndTime);
        this.mContext.startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$3$NobilityPrivilegeHeadView(View view) {
        OnEnterHideListener onEnterHideListener = this.onEnterHideListener;
        if (onEnterHideListener != null) {
            onEnterHideListener.onEnterHideListener(this.checkBox.isChecked());
        }
    }

    public void initData(MyNobilityEntity myNobilityEntity) {
        this.isRankHide = myNobilityEntity.isRankHideBoolean();
        this.mRecommendNumber = myNobilityEntity.recommendNumber;
        this.mEndTime = myNobilityEntity.endTime;
        int string2int = NumberUtils.string2int(myNobilityEntity.nobilityType);
        this.ivTopBg.setImageResource(AppUtils.getNobilityCardBgDrawableRes(string2int));
        this.flAvatarBg.setBackgroundResource(AppUtils.getNobilityAvatarBgDrawableRes(string2int));
        this.ivTopBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(string2int));
        this.tvNickName.setText(UserInfoManager.getInstance().getUserNickname());
        this.tvBadgeName.setText(AppUtils.getNobilityBadgeName(this.mContext, string2int));
        this.tvEndName.setText(this.mContext.getString(R$string.fq_nobility_expire_date, getEndTime(myNobilityEntity.endTime)));
        GlideUtils.loadAvatar(getContext(), this.ivAvatar, UserInfoManager.getInstance().getAvatar(), R$drawable.fq_ic_placeholder_avatar);
        this.checkBox.setChecked(myNobilityEntity.isEnterHideBoolean());
        this.tvLave.setText(this.mContext.getString(R$string.fq_nobility_top_live_lave, myNobilityEntity.recommendNumber));
        if (string2int == 1 || string2int == 2 || string2int == 3 || string2int == 4) {
            this.llSettingBg.setVisibility(8);
            return;
        }
        this.llSettingBg.setVisibility(0);
        if (string2int == 5) {
            this.llTipsBg1.setVisibility(0);
        } else if (string2int == 6) {
            this.llTipsBg1.setVisibility(0);
            this.llNobleRankHidden.setVisibility(0);
            this.vLine2.setVisibility(0);
        } else if (string2int != 7) {
        } else {
            this.llTipsBg1.setVisibility(0);
            this.llNobleRankHidden.setVisibility(0);
            this.llNobleRecommend.setVisibility(0);
            this.vLine2.setVisibility(0);
            this.vLine3.setVisibility(0);
        }
    }

    public void toggleEnterHideCheckBox() {
        this.checkBox.toggle();
    }

    public boolean isEnterHide() {
        return this.checkBox.isChecked();
    }

    public OnEnterHideListener getOnEnterHideListener() {
        return this.onEnterHideListener;
    }

    public void setOnEnterHideListener(OnEnterHideListener onEnterHideListener) {
        this.onEnterHideListener = onEnterHideListener;
    }

    private String getEndTime(String str) {
        return DateUtils.getTimeStrFromLongSecond(str, "yyyy-MM-dd");
    }
}
