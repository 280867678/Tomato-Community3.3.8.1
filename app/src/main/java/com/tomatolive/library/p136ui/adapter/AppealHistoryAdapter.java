package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.AppealHistoryEntity;
import com.tomatolive.library.utils.DateUtils;

/* renamed from: com.tomatolive.library.ui.adapter.AppealHistoryAdapter */
/* loaded from: classes3.dex */
public class AppealHistoryAdapter extends BaseQuickAdapter<AppealHistoryEntity, BaseViewHolder> {
    public AppealHistoryAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AppealHistoryEntity appealHistoryEntity) {
        if (appealHistoryEntity == null) {
            return;
        }
        baseViewHolder.setText(R$id.tv_nickname, appealHistoryEntity.getAnchorName()).setText(R$id.tv_award, appealHistoryEntity.getPrizeName()).setText(R$id.tv_status, getStatusStr(this.mContext, appealHistoryEntity.getAppealStatus())).setText(R$id.tv_time, DateUtils.formatSecondToDateFormat(appealHistoryEntity.getCreateTime(), DateUtils.C_TIME_PATTON_DEFAULT_2));
        baseViewHolder.setTextColor(R$id.tv_status, ContextCompat.getColor(this.mContext, getStatusColorRes(appealHistoryEntity.getAppealStatus())));
    }

    public static String getStatusStr(Context context, int i) {
        String[] stringArray = context.getResources().getStringArray(R$array.fq_appeal_status_tips);
        if (i != 1) {
            if (i == 2) {
                return stringArray[1];
            }
            if (i == 3) {
                return stringArray[2];
            }
            if (i == 4) {
                return stringArray[3];
            }
            return i != 5 ? "" : stringArray[4];
        }
        return stringArray[0];
    }

    @ColorRes
    public static int getStatusColorRes(int i) {
        if (i != 1) {
            if (i == 2) {
                return R$color.statusColorBlue;
            }
            if (i == 3) {
                return R$color.statusColorRed;
            }
            if (i == 4) {
                return R$color.statusColorGreen;
            }
            if (i == 5) {
                return R$color.statusColorGrey;
            }
            return R$color.statusColorGrey;
        }
        return R$color.statusColorYellow;
    }
}
