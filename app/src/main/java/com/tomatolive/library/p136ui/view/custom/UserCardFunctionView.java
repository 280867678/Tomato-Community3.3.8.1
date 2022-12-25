package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.ImpressionEntity;
import com.tomatolive.library.model.UserCardEntity;
import com.tomatolive.library.p136ui.view.widget.tagview.TagContainerLayout;
import com.tomatolive.library.p136ui.view.widget.tagview.TagView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.custom.UserCardFunctionView */
/* loaded from: classes3.dex */
public class UserCardFunctionView extends LinearLayout {
    private FrameLayout flGradeBg;
    private UserCardFunctionViewListener functionViewListener;
    private List<String> impressionTagList;
    private final int invalidImgResID;
    private ImageView ivAchieve1;
    private ImageView ivAchieve2;
    private ImageView ivAchieve3;
    private ImageView ivGrade;
    private ImageView ivGuardAvatar;
    private ImageView ivNobilityBadge;
    private Context mContext;
    private RelativeLayout rlAchieveBg;
    private RelativeLayout rlAchieveCountBg;
    private RelativeLayout rlGiftWallBg;
    private RelativeLayout rlGradeBg;
    private RelativeLayout rlGuardBg;
    private RelativeLayout rlNobilityBg;
    private TagContainerLayout tagContainerLayout;
    private TextView tvAchieveCount;
    private TextView tvAchieveEmpty;
    private TextView tvGiftLightNum;
    private TextView tvGrade;
    private TextView tvGradeType;
    private TextView tvGuardNum;
    private TextView tvNobilityDesc;
    private TextView tvNobilityName;

    /* renamed from: com.tomatolive.library.ui.view.custom.UserCardFunctionView$UserCardFunctionViewListener */
    /* loaded from: classes3.dex */
    public interface UserCardFunctionViewListener {
        void onAchieveClickListener();

        void onGiftWallClickListener();

        void onGuardClickListener();

        void onImpressionClickListener();
    }

    private String getNobilityPrivilegeDesc(int i) {
        switch (i) {
            case 1:
                return "5";
            case 2:
            case 3:
                return "6";
            case 4:
            case 5:
                return "8";
            case 6:
                return "10";
            case 7:
                return "14";
            default:
                return "0";
        }
    }

    public UserCardFunctionView(Context context) {
        this(context, null);
    }

    public UserCardFunctionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.invalidImgResID = -1;
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_achieve_corner_user_grade_view, this);
        this.mContext = context;
        this.rlGradeBg = (RelativeLayout) findViewById(R$id.rl_grade_bg);
        this.flGradeBg = (FrameLayout) findViewById(R$id.fl_grade_bg);
        this.ivGrade = (ImageView) findViewById(R$id.iv_grade);
        this.tvGradeType = (TextView) findViewById(R$id.tv_grade_type);
        this.tvGrade = (TextView) findViewById(R$id.tv_grade);
        this.rlNobilityBg = (RelativeLayout) findViewById(R$id.rl_nobility_bg);
        this.ivNobilityBadge = (ImageView) findViewById(R$id.iv_nobility_badge);
        this.tvNobilityName = (TextView) findViewById(R$id.tv_nobility_name);
        this.tvNobilityDesc = (TextView) findViewById(R$id.tv_nobility_desc);
        this.rlGuardBg = (RelativeLayout) findViewById(R$id.rl_guard_bg);
        this.tvGuardNum = (TextView) findViewById(R$id.tv_guard_num);
        this.ivGuardAvatar = (ImageView) findViewById(R$id.iv_guard_avatar);
        this.rlGiftWallBg = (RelativeLayout) findViewById(R$id.rl_gift_wall_bg);
        this.tvGiftLightNum = (TextView) findViewById(R$id.tv_gift_count);
        this.rlAchieveBg = (RelativeLayout) findViewById(R$id.rl_achieve_bg);
        this.rlAchieveCountBg = (RelativeLayout) findViewById(R$id.rl_achieve_count_bg);
        this.tvAchieveEmpty = (TextView) findViewById(R$id.tv_achieve_empty);
        this.ivAchieve1 = (ImageView) findViewById(R$id.iv_achieve_1);
        this.ivAchieve2 = (ImageView) findViewById(R$id.iv_achieve_2);
        this.ivAchieve3 = (ImageView) findViewById(R$id.iv_achieve_3);
        this.tvAchieveCount = (TextView) findViewById(R$id.tv_achieve_count);
        this.tagContainerLayout = (TagContainerLayout) findViewById(R$id.tag_impression);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ContextCompat.getColor(getContext(), R$color.colorWhite));
        gradientDrawable.setShape(1);
        this.flGradeBg.setBackground(gradientDrawable);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(Color.parseColor("#A3D5FF"));
        gradientDrawable2.setCornerRadius(getResources().getDimensionPixelOffset(R$dimen.fq_achieve_radius_10));
        this.tvAchieveCount.setBackground(gradientDrawable2);
        this.tagContainerLayout.addTag(this.mContext.getString(R$string.fq_achieve_add));
        this.tvGiftLightNum.setText(Html.fromHtml(this.mContext.getString(R$string.fq_achieve_gift_wall_light, "0")));
        this.tvGuardNum.setText("0");
        this.rlAchieveBg.setEnabled(false);
        this.rlGuardBg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$UserCardFunctionView$PFkfb2ONyrrhBxYrCW_RdXsyiYI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserCardFunctionView.this.lambda$initView$0$UserCardFunctionView(view);
            }
        });
        this.rlAchieveBg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$UserCardFunctionView$e42Po91WU9Q3BXmB_gHgPIvrIp4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserCardFunctionView.this.lambda$initView$1$UserCardFunctionView(view);
            }
        });
        this.rlGiftWallBg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$UserCardFunctionView$4pAHtNg2lheiyQRiuRhoALa1KX4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserCardFunctionView.this.lambda$initView$2$UserCardFunctionView(view);
            }
        });
        this.tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() { // from class: com.tomatolive.library.ui.view.custom.UserCardFunctionView.1
            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onSelectedTagDrag(int i, String str) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagCrossClick(int i) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagLongClick(int i, String str) {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.tagview.TagView.OnTagClickListener
            public void onTagClick(int i, String str) {
                if (UserCardFunctionView.this.functionViewListener == null || i != UserCardFunctionView.this.tagContainerLayout.getTags().size() - 1) {
                    return;
                }
                UserCardFunctionView.this.functionViewListener.onImpressionClickListener();
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$UserCardFunctionView(View view) {
        UserCardFunctionViewListener userCardFunctionViewListener = this.functionViewListener;
        if (userCardFunctionViewListener != null) {
            userCardFunctionViewListener.onGuardClickListener();
        }
    }

    public /* synthetic */ void lambda$initView$1$UserCardFunctionView(View view) {
        UserCardFunctionViewListener userCardFunctionViewListener = this.functionViewListener;
        if (userCardFunctionViewListener != null) {
            userCardFunctionViewListener.onAchieveClickListener();
        }
    }

    public /* synthetic */ void lambda$initView$2$UserCardFunctionView(View view) {
        UserCardFunctionViewListener userCardFunctionViewListener = this.functionViewListener;
        if (userCardFunctionViewListener != null) {
            userCardFunctionViewListener.onGiftWallClickListener();
        }
    }

    public void initData(boolean z, boolean z2, String str, UserCardEntity userCardEntity) {
        initData(z, z2, str, -1, userCardEntity);
    }

    public void initData(boolean z, boolean z2, String str, int i, UserCardEntity userCardEntity) {
        int string2int = NumberUtils.string2int(str, 1);
        int i2 = 0;
        if (!TextUtils.isEmpty(str)) {
            this.rlGradeBg.setVisibility(0);
            this.rlGradeBg.setBackground(getCornerBgDrawable(z, string2int));
        } else {
            this.rlGradeBg.setVisibility(4);
        }
        this.tvGrade.setText(str);
        this.tvGradeType.setText(z ? R$string.fq_my_live_anchor_grade : R$string.fq_my_live_user_grade);
        this.rlAchieveBg.setVisibility(AppUtils.isEnableAchievement() ? 0 : 8);
        if (z) {
            this.rlGuardBg.setVisibility(0);
            this.rlNobilityBg.setVisibility(4);
            this.rlGiftWallBg.setVisibility(AppUtils.isEnableGiftWall() ? 0 : 8);
            TagContainerLayout tagContainerLayout = this.tagContainerLayout;
            if (!z2) {
                i2 = 8;
            }
            tagContainerLayout.setVisibility(i2);
            this.ivGrade.setImageResource(getAnchorGradeDrawable(string2int));
        } else {
            this.rlGuardBg.setVisibility(4);
            this.rlGiftWallBg.setVisibility(4);
            this.tagContainerLayout.setVisibility(8);
            this.ivGrade.setImageResource(AppUtils.getUserGradeIconResource(false, string2int));
            if (AppUtils.isNobilityUser(i)) {
                this.rlNobilityBg.setVisibility(0);
                this.tvNobilityName.setText(AppUtils.getNobilityBadgeName(this.mContext, i) + this.mContext.getString(R$string.fq_nobility));
                this.tvNobilityDesc.setText(this.mContext.getString(R$string.fq_nobility_privilege_desc_count, getNobilityPrivilegeDesc(i)));
                int nobilityBadgeDrawableRes = getNobilityBadgeDrawableRes(i);
                if (nobilityBadgeDrawableRes != -1) {
                    this.ivNobilityBadge.setImageResource(nobilityBadgeDrawableRes);
                }
            } else {
                this.rlNobilityBg.setVisibility(4);
            }
        }
        if (!AppUtils.isEnableAchievement() && !AppUtils.isEnableGiftWall()) {
            this.rlAchieveBg.setVisibility(8);
            this.rlGiftWallBg.setVisibility(8);
        }
        updateData(userCardEntity);
    }

    public void updateData(UserCardEntity userCardEntity) {
        if (userCardEntity == null) {
            return;
        }
        this.tvGuardNum.setText(userCardEntity.getGuardCount());
        if (TextUtils.isEmpty(userCardEntity.getGuardUrl())) {
            this.ivGuardAvatar.setImageResource(R$drawable.fq_ic_achieve_guard_shafa);
        } else {
            loadAvatar(this.ivGuardAvatar, userCardEntity.getGuardUrl());
        }
        initAchieveView(userCardEntity.getAchievementUrls(), userCardEntity.getAchievementTotalNum());
        this.tvGiftLightNum.setText(Html.fromHtml(this.mContext.getString(R$string.fq_achieve_gift_wall_light, userCardEntity.userGiftWallNum)));
        this.impressionTagList = getTagData(userCardEntity.userImpression);
        this.tagContainerLayout.setTags(this.impressionTagList);
    }

    public void updateAnchorGrade(String str) {
        int string2int = NumberUtils.string2int(str, 1);
        this.rlGradeBg.setVisibility(0);
        this.tvGrade.setText(str);
        this.rlGradeBg.setBackground(getCornerBgDrawable(true, string2int));
        this.ivGrade.setImageResource(getAnchorGradeDrawable(string2int));
    }

    public void setOnFunctionViewListener(UserCardFunctionViewListener userCardFunctionViewListener) {
        this.functionViewListener = userCardFunctionViewListener;
    }

    private void initAchieveView(List<String> list, String str) {
        boolean z = list != null && !list.isEmpty();
        this.tvAchieveEmpty.setVisibility(z ? 4 : 0);
        this.rlAchieveCountBg.setVisibility(z ? 0 : 4);
        this.rlAchieveBg.setEnabled(z);
        if (NumberUtils.string2int(str) > 3) {
            this.tvAchieveCount.setVisibility(0);
            this.tvAchieveCount.setText(str);
        } else {
            this.tvAchieveCount.setVisibility(4);
        }
        if (z) {
            int size = list.size();
            if (size >= 3) {
                this.ivAchieve1.setVisibility(0);
                this.ivAchieve2.setVisibility(0);
                this.ivAchieve3.setVisibility(0);
                loadAvatar(this.ivAchieve1, list.get(0));
                loadAvatar(this.ivAchieve2, list.get(1));
                loadAvatar(this.ivAchieve3, list.get(2));
            } else if (size >= 2) {
                this.ivAchieve1.setVisibility(0);
                this.ivAchieve2.setVisibility(0);
                this.ivAchieve3.setVisibility(4);
                this.tvAchieveCount.setVisibility(4);
                loadAvatar(this.ivAchieve1, list.get(0));
                loadAvatar(this.ivAchieve2, list.get(1));
            } else {
                this.ivAchieve1.setVisibility(0);
                this.ivAchieve2.setVisibility(4);
                this.ivAchieve3.setVisibility(4);
                this.tvAchieveCount.setVisibility(4);
                loadAvatar(this.ivAchieve1, list.get(0));
            }
        }
    }

    private GradientDrawable getCornerBgDrawable(boolean z, int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(getResources().getDimensionPixelOffset(R$dimen.fq_user_card_corner_radius));
        int userGradeInterval = AppUtils.getUserGradeInterval(i);
        if (userGradeInterval == 10) {
            gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gradientDrawable.setColors(getUserGradeColors(R$color.fq_user_grade_second_color_10_start, R$color.fq_user_grade_second_color_10_end));
        } else if (userGradeInterval == 11) {
            gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gradientDrawable.setColors(getUserGradeColors(R$color.fq_user_grade_second_color_11_start, R$color.fq_user_grade_second_color_11_end));
        } else if (userGradeInterval == 12) {
            gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gradientDrawable.setColors(getUserGradeColors(R$color.fq_user_grade_second_color_12_start, R$color.fq_user_grade_second_color_12_end));
        } else {
            gradientDrawable.setColor(ContextCompat.getColor(getContext(), z ? getAnchorExpGradeColorRes(i) : getExpGradeColorRes(i)));
        }
        return gradientDrawable;
    }

    @ColorRes
    private int getExpGradeColorRes(int i) {
        int userGradeInterval = AppUtils.getUserGradeInterval(i);
        if (AppUtils.isUserGradeMax60()) {
            switch (userGradeInterval) {
                case 1:
                    return R$color.fq_user_grade_color_1;
                case 2:
                    return R$color.fq_user_grade_color_2;
                case 3:
                    return R$color.fq_user_grade_color_3;
                case 4:
                    return R$color.fq_user_grade_color_4;
                case 5:
                    return R$color.fq_user_grade_color_5;
                case 6:
                    return R$color.fq_user_grade_color_6;
                default:
                    return R$color.fq_user_grade_color_1;
            }
        }
        switch (userGradeInterval) {
            case 1:
                return R$color.fq_user_grade_second_color_1;
            case 2:
                return R$color.fq_user_grade_second_color_2;
            case 3:
                return R$color.fq_user_grade_second_color_3;
            case 4:
                return R$color.fq_user_grade_second_color_4;
            case 5:
                return R$color.fq_user_grade_second_color_5;
            case 6:
                return R$color.fq_user_grade_second_color_6;
            case 7:
                return R$color.fq_user_grade_second_color_7;
            case 8:
                return R$color.fq_user_grade_second_color_8;
            case 9:
                return R$color.fq_user_grade_second_color_9;
            default:
                return R$color.fq_user_grade_second_color_1;
        }
    }

    @ColorRes
    private int getAnchorExpGradeColorRes(int i) {
        switch (AppUtils.getAnchorGradeInterval(i)) {
            case 1:
                return R$color.fq_user_grade_color_1;
            case 2:
                return R$color.fq_user_grade_color_2;
            case 3:
                return R$color.fq_user_grade_color_3;
            case 4:
                return R$color.fq_user_grade_color_4;
            case 5:
                return R$color.fq_user_grade_color_5;
            case 6:
                return R$color.fq_user_grade_color_6;
            default:
                return R$color.fq_user_grade_color_1;
        }
    }

    @DrawableRes
    private int getAnchorGradeDrawable(int i) {
        switch (AppUtils.getAnchorGradeInterval(i)) {
            case 1:
                return R$drawable.fq_ic_grade_anchor_1;
            case 2:
                return R$drawable.fq_ic_grade_anchor_2;
            case 3:
                return R$drawable.fq_ic_grade_anchor_3;
            case 4:
                return R$drawable.fq_ic_grade_anchor_4;
            case 5:
                return R$drawable.fq_ic_grade_anchor_5;
            case 6:
                return R$drawable.fq_ic_grade_anchor_6;
            default:
                return R$drawable.fq_ic_grade_anchor_1;
        }
    }

    private int[] getUserGradeColors(@ColorRes int i, @ColorRes int i2) {
        return new int[]{ContextCompat.getColor(getContext(), i), ContextCompat.getColor(getContext(), i2)};
    }

    @DrawableRes
    private int getNobilityBadgeDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_badge_msg_1;
            case 2:
                return R$drawable.fq_ic_nobility_badge_msg_2;
            case 3:
                return R$drawable.fq_ic_nobility_badge_msg_3;
            case 4:
                return R$drawable.fq_ic_nobility_badge_msg_4;
            case 5:
                return R$drawable.fq_ic_nobility_badge_msg_5;
            case 6:
                return R$drawable.fq_ic_nobility_badge_msg_6;
            case 7:
                return R$drawable.fq_ic_nobility_badge_msg_7;
            default:
                return -1;
        }
    }

    private void loadAvatar(ImageView imageView, String str) {
        GlideUtils.loadAvatar(this.mContext, imageView, str, R$drawable.fq_ic_placeholder_avatar);
    }

    private List<String> getTagData(List<ImpressionEntity> list) {
        List<String> arrayList = new ArrayList<>();
        if (list != null) {
            for (ImpressionEntity impressionEntity : list) {
                arrayList.add(impressionEntity.impressionName);
            }
        }
        if (arrayList.size() > 3) {
            arrayList = arrayList.subList(0, 2);
        }
        arrayList.add(this.mContext.getString(R$string.fq_achieve_add));
        return arrayList;
    }
}
