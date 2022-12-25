package com.tomatolive.library.p136ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.custom.UserNickNameGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.adapter.DedicateTopAdapter */
/* loaded from: classes3.dex */
public class DedicateTopAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    private boolean isDialog;

    private boolean isShowBrand(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    public DedicateTopAdapter(int i) {
        super(i);
        this.isDialog = false;
    }

    public DedicateTopAdapter(int i, boolean z) {
        super(i);
        this.isDialog = false;
        this.isDialog = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        Drawable colorDrawable;
        int adapterPosition = baseViewHolder.getAdapterPosition();
        boolean isRankHideBoolean = anchorEntity.isRankHideBoolean();
        int i = 0;
        baseViewHolder.setText(R$id.tv_income, this.mContext.getString(R$string.fq_tomato_money_reward, anchorEntity.expend)).setBackgroundRes(R$id.fl_avatar_bg, getAvatarBgResId(adapterPosition)).setImageResource(R$id.iv_brand, getBrandResId(adapterPosition)).setVisible(R$id.iv_brand, isShowBrand(adapterPosition)).setVisible(R$id.tv_num, true ^ isShowBrand(adapterPosition)).setVisible(R$id.ll_mystery_bg, isRankHideBoolean).setVisible(R$id.tv_me, isRankHideBoolean && TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId()));
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R$id.ll_content_bg);
        if (isRankHideBoolean) {
            colorDrawable = ContextCompat.getDrawable(this.mContext, this.isDialog ? R$drawable.fq_shape_nobility_stealth_top_bg_2 : R$drawable.fq_shape_nobility_stealth_top_bg);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R$color.fq_color_transparent));
        }
        linearLayout.setBackground(colorDrawable);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_avatar);
        if (isRankHideBoolean) {
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
        } else {
            GlideUtils.loadAvatar(this.mContext, imageView, anchorEntity.avatar);
        }
        UserNickNameGradeView userNickNameGradeView = (UserNickNameGradeView) baseViewHolder.getView(R$id.user_nickname);
        if (isRankHideBoolean) {
            i = 8;
        }
        userNickNameGradeView.setVisibility(i);
        userNickNameGradeView.initData(anchorEntity.nickname, this.isDialog ? R$color.fq_colorWhite : R$color.fq_colorBlack, anchorEntity.sex, anchorEntity.expGrade, anchorEntity.nobilityType);
        AppUtils.formatTvNumTypeface(this.mContext, (TextView) baseViewHolder.getView(R$id.tv_num), getNumStr(adapterPosition));
    }

    private String getNumStr(int i) {
        if (i >= 3) {
            return "" + (i + 1);
        }
        return "" + i;
    }

    private int getAvatarBgResId(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$drawable.fq_shape_top_tag_silver_circle;
            }
            if (i == 2) {
                return R$drawable.fq_shape_top_tag_copper_circle;
            }
            return R$drawable.fq_shape_top_tag_gray_circle;
        }
        return R$drawable.fq_shape_top_tag_gold_circle;
    }

    private int getBrandResId(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$drawable.fq_ic_top_brand_no_2;
            }
            if (i == 2) {
                return R$drawable.fq_ic_top_brand_no_3;
            }
            return R$drawable.fq_ic_top_brand_no_3;
        }
        return R$drawable.fq_ic_top_brand_no_1;
    }
}
