package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.NobilityRecommendHistoryEntity;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.text.SimpleDateFormat;

/* renamed from: com.tomatolive.library.ui.adapter.NobilityRecommendHistoryAdapter */
/* loaded from: classes3.dex */
public class NobilityRecommendHistoryAdapter extends BaseQuickAdapter<NobilityRecommendHistoryEntity, BaseViewHolder> {
    public NobilityRecommendHistoryAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, NobilityRecommendHistoryEntity nobilityRecommendHistoryEntity) {
        baseViewHolder.setText(R$id.tv_list_time, getOpenTime(nobilityRecommendHistoryEntity.createTime, DateUtils.C_DATE_PATTON_DATE_CHINA_7)).setText(R$id.tv_anchor, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_recommend_anchor, nobilityRecommendHistoryEntity.anchorName))).setText(R$id.tv_date, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_recommend_time, getOpenTime(nobilityRecommendHistoryEntity.createTime, "yyyy-MM-dd")))).setText(R$id.tv_time, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_recommend_time_interval, getTime(nobilityRecommendHistoryEntity)))).setText(R$id.tv_type, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_recommend_type, getType(nobilityRecommendHistoryEntity)))).setTextColor(R$id.tv_status, ContextCompat.getColor(this.mContext, getOpenStatusColorRes(nobilityRecommendHistoryEntity.status))).setText(R$id.tv_status, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_recommend_status, getStatus(nobilityRecommendHistoryEntity))));
    }

    @ColorRes
    private int getOpenStatusColorRes(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != 49) {
            if (hashCode == 50 && str.equals("2")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("1")) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return R$color.fq_nobility_open_status_red;
            }
            return R$color.fq_nobility_open_status_red;
        }
        return R$color.fq_nobility_open_status_green;
    }

    private String getTime(NobilityRecommendHistoryEntity nobilityRecommendHistoryEntity) {
        return formatTimeStr(getOpenTime(nobilityRecommendHistoryEntity.createTime, DateUtils.C_TIME_PATTON_DEFAULT_2)) + "-" + formatTimeStr(getOpenTime(nobilityRecommendHistoryEntity.endTime, DateUtils.C_TIME_PATTON_DEFAULT_2));
    }

    private String getType(NobilityRecommendHistoryEntity nobilityRecommendHistoryEntity) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_nobility_recommend_history_type);
        return nobilityRecommendHistoryEntity.isAnonymous() ? stringArray[0] : stringArray[1];
    }

    private String getStatus(NobilityRecommendHistoryEntity nobilityRecommendHistoryEntity) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_nobility_recommend_history_status);
        return TextUtils.equals(nobilityRecommendHistoryEntity.status, "1") ? stringArray[0] : stringArray[1];
    }

    private String getOpenTime(String str, String str2) {
        return TimeUtils.millis2String(NumberUtils.string2long(str) * 1000, new SimpleDateFormat(str2));
    }

    private String formatTimeStr(String str) {
        try {
            return str.substring(11, str.length());
        } catch (Exception unused) {
            return str;
        }
    }
}
