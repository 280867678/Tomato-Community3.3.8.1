package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.NobilityOpenTopNoticeView */
/* loaded from: classes3.dex */
public class NobilityOpenTopNoticeView extends LinearLayout {
    private ImageView ivBadgeLeft;
    private ImageView ivBadgeRight;
    private Context mContext;
    private TextView tvNobilityOpenTips;

    public NobilityOpenTopNoticeView(Context context) {
        super(context);
        initView(context);
    }

    public NobilityOpenTopNoticeView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LinearLayout.inflate(context, R$layout.fq_layout_nobility_open_top_notice, this);
        this.ivBadgeLeft = (ImageView) findViewById(R$id.iv_badge_left);
        this.ivBadgeRight = (ImageView) findViewById(R$id.iv_badge_right);
        this.tvNobilityOpenTips = (TextView) findViewById(R$id.tv_notice_tips);
    }

    public void initData(SocketMessageEvent.ResultData resultData, String str) {
        int i = resultData.nobilityType;
        this.ivBadgeLeft.setImageResource(getBadgeLeftImg(i));
        this.ivBadgeRight.setImageResource(getBadgeRightImg(i));
        this.tvNobilityOpenTips.setBackgroundResource(getTextTipsBg(i));
        String nobilityBadgeName = AppUtils.getNobilityBadgeName(this.mContext, i);
        String string = TextUtils.equals(resultData.type, "2") ? this.mContext.getString(R$string.fq_nobility_renewal, nobilityBadgeName) : this.mContext.getString(R$string.fq_nobility_opened, nobilityBadgeName);
        String formatUserNickName = AppUtils.formatUserNickName(resultData.userName);
        String formatUserNickName2 = AppUtils.formatUserNickName(resultData.anchorName);
        if (i == 7) {
            this.tvNobilityOpenTips.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_nobility_badge_7_open_tips));
            if (TextUtils.equals(resultData.forwardLiveId, "-1")) {
                this.tvNobilityOpenTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_notice_tips_2_3, formatUserNickName, string)));
                return;
            } else if (TextUtils.equals(resultData.forwardLiveId, str)) {
                this.tvNobilityOpenTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_notice_tips_2_2, formatUserNickName, string)));
                return;
            } else {
                this.tvNobilityOpenTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_notice_tips_2, formatUserNickName, formatUserNickName2, string)));
                return;
            }
        }
        this.tvNobilityOpenTips.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_white_color));
        if (TextUtils.equals(resultData.forwardLiveId, "-1")) {
            this.tvNobilityOpenTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_notice_tips_1_3, formatUserNickName, string)));
        } else if (TextUtils.equals(resultData.forwardLiveId, str)) {
            this.tvNobilityOpenTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_notice_tips_1_2, formatUserNickName, string)));
        } else {
            this.tvNobilityOpenTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_notice_tips_1, formatUserNickName, formatUserNickName2, string)));
        }
    }

    @DrawableRes
    private int getBadgeLeftImg(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_open_roll_1_left;
            case 2:
                return R$drawable.fq_ic_nobility_open_roll_2_left;
            case 3:
                return R$drawable.fq_ic_nobility_open_roll_3_left;
            case 4:
                return R$drawable.fq_ic_nobility_open_roll_4_left;
            case 5:
                return R$drawable.fq_ic_nobility_open_roll_5_left;
            case 6:
                return R$drawable.fq_ic_nobility_open_roll_6_left;
            case 7:
                return R$drawable.fq_ic_nobility_open_roll_7_left;
            default:
                return R$drawable.fq_ic_nobility_open_roll_1_left;
        }
    }

    @DrawableRes
    private int getBadgeRightImg(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_open_roll_1_right;
            case 2:
                return R$drawable.fq_ic_nobility_open_roll_2_right;
            case 3:
                return R$drawable.fq_ic_nobility_open_roll_3_right;
            case 4:
                return R$drawable.fq_ic_nobility_open_roll_4_right;
            case 5:
                return R$drawable.fq_ic_nobility_open_roll_5_right;
            case 6:
                return R$drawable.fq_ic_nobility_open_roll_6_right;
            case 7:
                return R$drawable.fq_ic_nobility_open_roll_7_right;
            default:
                return R$drawable.fq_ic_nobility_open_roll_1_right;
        }
    }

    @DrawableRes
    private int getTextTipsBg(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_shape_nobility_open_notice_bg_1;
            case 2:
                return R$drawable.fq_shape_nobility_open_notice_bg_2;
            case 3:
                return R$drawable.fq_shape_nobility_open_notice_bg_3;
            case 4:
                return R$drawable.fq_shape_nobility_open_notice_bg_4;
            case 5:
                return R$drawable.fq_shape_nobility_open_notice_bg_5;
            case 6:
                return R$drawable.fq_shape_nobility_open_notice_bg_6;
            case 7:
                return R$drawable.fq_shape_nobility_open_notice_bg_7;
            default:
                return R$drawable.fq_shape_nobility_open_notice_bg_1;
        }
    }
}
