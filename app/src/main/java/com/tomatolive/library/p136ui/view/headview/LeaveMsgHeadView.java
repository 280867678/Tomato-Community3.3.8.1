package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AwardDetailEntity;
import com.tomatolive.library.utils.DateUtils;

/* renamed from: com.tomatolive.library.ui.view.headview.LeaveMsgHeadView */
/* loaded from: classes3.dex */
public class LeaveMsgHeadView extends LinearLayout {
    private Context context;
    private TextView mTvAddAddress;
    private TextView mTvFirstLine;
    private TextView mTvPrizeName;
    private TextView mTvSecondLine;
    private TextView tvTime;
    private TextView tvTips;

    public LeaveMsgHeadView(Context context) {
        super(context);
        initView(context);
    }

    public LeaveMsgHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_leave_msg, this);
        this.mTvPrizeName = (TextView) findViewById(R$id.tv_prize_name);
        this.mTvFirstLine = (TextView) findViewById(R$id.tv_first_line);
        this.mTvSecondLine = (TextView) findViewById(R$id.tv_second_line);
        this.mTvAddAddress = (TextView) findViewById(R$id.tv_add_receive_address);
        this.tvTips = (TextView) findViewById(R$id.tv_tips);
        this.tvTime = (TextView) findViewById(R$id.tv_time);
    }

    public void initData(boolean z, AwardDetailEntity awardDetailEntity) {
        String[] stringArray = getContext().getResources().getStringArray(R$array.fq_write_address_award_tips);
        this.tvTips.setText(z ? stringArray[0] : stringArray[1]);
        updateWinningStatus(z, awardDetailEntity.getWinningStatus());
        this.tvTime.setText(getTimeStr(awardDetailEntity));
        if (z) {
            this.mTvPrizeName.setText(awardDetailEntity.getPrizeName());
            this.mTvFirstLine.setText(this.context.getString(R$string.fq_hd_room_id, awardDetailEntity.getAnchorName(), awardDetailEntity.getLiveId()));
            this.mTvSecondLine.setText(R$string.fq_hd_receive_award_type);
            return;
        }
        this.mTvPrizeName.setText(awardDetailEntity.getPrizeName());
        this.mTvFirstLine.setText(this.context.getString(R$string.fq_hd_award_detail, awardDetailEntity.getPartDetail()));
        this.mTvSecondLine.setText(this.context.getString(R$string.fq_hd_award_type, awardDetailEntity.getDrawTypeDesc()));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateWinningStatus(boolean z, String str) {
        char c;
        String[] stringArray = getContext().getResources().getStringArray(R$array.fq_write_address_tips);
        String[] stringArray2 = getContext().getResources().getStringArray(R$array.fq_write_address_tips_2);
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (str.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.mTvAddAddress.setBackgroundResource(z ? R$drawable.fq_hd_award_detail_address_bg : R$drawable.fq_hd_award_detail_address_bg2);
            this.mTvAddAddress.setTextColor(getResources().getColor(z ? R$color.fq_hd_lottery_primary : R$color.fq_add_address_color));
            this.mTvAddAddress.setText(z ? stringArray[0] : stringArray2[0]);
            this.mTvAddAddress.setEnabled(true);
        } else if (c == 1) {
            this.mTvAddAddress.setBackgroundResource(R$drawable.fq_hd_award_detail_address_bg2);
            this.mTvAddAddress.setTextColor(getResources().getColor(R$color.fq_add_address_color));
            this.mTvAddAddress.setText(z ? stringArray[1] : stringArray2[1]);
            this.mTvAddAddress.setEnabled(!z);
        } else if (c == 2) {
            this.mTvAddAddress.setBackgroundResource(z ? R$drawable.fq_hd_award_detail_address_bg2 : R$drawable.fq_hd_award_detail_address_bg3);
            this.mTvAddAddress.setTextColor(getResources().getColor(z ? R$color.fq_add_address_color : R$color.fq_add_address_color_gray));
            this.mTvAddAddress.setText(z ? stringArray[2] : stringArray2[2]);
            this.mTvAddAddress.setEnabled(false);
        } else if (c != 3) {
        } else {
            this.mTvAddAddress.setBackgroundResource(R$drawable.fq_hd_award_detail_address_bg3);
            this.mTvAddAddress.setTextColor(getResources().getColor(R$color.fq_add_address_color_gray));
            this.mTvAddAddress.setText(z ? stringArray[3] : stringArray2[3]);
            this.mTvAddAddress.setEnabled(false);
        }
    }

    public void setAddressOnClickListener(View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            return;
        }
        this.mTvAddAddress.setOnClickListener(onClickListener);
    }

    private String getTimeStr(AwardDetailEntity awardDetailEntity) {
        return DateUtils.formatSecondToDateFormat(awardDetailEntity.getWinningTime(), DateUtils.C_DATE_PATTON_DATE_CHINA_7);
    }
}
