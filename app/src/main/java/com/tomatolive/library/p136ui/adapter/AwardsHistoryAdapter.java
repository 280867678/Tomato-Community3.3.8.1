package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.AwardHistoryEntity;
import com.tomatolive.library.utils.DateUtils;

/* renamed from: com.tomatolive.library.ui.adapter.AwardsHistoryAdapter */
/* loaded from: classes3.dex */
public class AwardsHistoryAdapter extends BaseQuickAdapter<AwardHistoryEntity, BaseViewHolder> {
    private boolean isAwarded;

    public AwardsHistoryAdapter(boolean z, int i) {
        super(i);
        this.isAwarded = true;
        this.isAwarded = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AwardHistoryEntity awardHistoryEntity) {
        baseViewHolder.setText(R$id.tv_nickname, this.isAwarded ? awardHistoryEntity.getAnchorName() : awardHistoryEntity.getUserName()).setText(R$id.tv_award, this.isAwarded ? awardHistoryEntity.getPrizeName() : DateUtils.formatSecondToDateFormat(awardHistoryEntity.getWinningTime(), DateUtils.C_TIME_PATTON_DEFAULT_2)).setText(R$id.tv_status, getStatusStr(awardHistoryEntity)).setText(R$id.tv_time, DateUtils.formatSecondToDateFormat(awardHistoryEntity.getWinningTime(), DateUtils.C_TIME_PATTON_DEFAULT_2)).setGone(R$id.tv_time, this.isAwarded);
        baseViewHolder.setTextColor(R$id.tv_status, ContextCompat.getColor(this.mContext, getStatusColor(awardHistoryEntity)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String getStatusStr(AwardHistoryEntity awardHistoryEntity) {
        char c;
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_given_award_status);
        String valueOf = String.valueOf(awardHistoryEntity.getWinningStatus());
        switch (valueOf.hashCode()) {
            case 48:
                if (valueOf.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (valueOf.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (valueOf.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (valueOf.equals("3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1) {
                return stringArray[1];
            }
            if (c == 2) {
                return stringArray[2];
            }
            return c != 3 ? "" : stringArray[3];
        }
        return stringArray[0];
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @ColorRes
    private int getStatusColor(AwardHistoryEntity awardHistoryEntity) {
        char c;
        String valueOf = String.valueOf(awardHistoryEntity.getWinningStatus());
        switch (valueOf.hashCode()) {
            case 48:
                if (valueOf.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (valueOf.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (valueOf.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (valueOf.equals("3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1) {
                return R$color.statusColorYellow;
            }
            if (c == 2) {
                return R$color.statusColorGreen;
            }
            if (c == 3) {
                return R$color.statusColorGrey;
            }
            return R$color.fq_text_black;
        }
        return R$color.statusColorRed;
    }
}
