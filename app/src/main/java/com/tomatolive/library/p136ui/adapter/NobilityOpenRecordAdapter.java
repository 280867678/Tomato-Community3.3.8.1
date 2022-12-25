package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.NobilityOpenRecordEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.text.SimpleDateFormat;

/* renamed from: com.tomatolive.library.ui.adapter.NobilityOpenRecordAdapter */
/* loaded from: classes3.dex */
public class NobilityOpenRecordAdapter extends BaseQuickAdapter<NobilityOpenRecordEntity, BaseViewHolder> {
    public NobilityOpenRecordAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, NobilityOpenRecordEntity nobilityOpenRecordEntity) {
        baseViewHolder.setText(R$id.tv_list_time, getOpenTime(nobilityOpenRecordEntity.createTime, DateUtils.C_DATE_PATTON_DATE_CHINA_7)).setText(R$id.tv_open_title, getOpenTitleSpanned(nobilityOpenRecordEntity)).setText(R$id.tv_buy_user, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_buy_user, nobilityOpenRecordEntity.userName))).setText(R$id.tv_open_time, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_time, getOpenTime(nobilityOpenRecordEntity.createTime)))).setText(R$id.tv_open_price, getOpenPrice(nobilityOpenRecordEntity)).setText(R$id.tv_open_status, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_status, getOpenStatusStr(nobilityOpenRecordEntity.nobilityStatus)))).setTextColor(R$id.tv_open_status, ContextCompat.getColor(this.mContext, getOpenStatusColorRes(nobilityOpenRecordEntity.nobilityStatus))).setText(R$id.tv_rebate_gold, Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_virtual_back, getPriceStr(nobilityOpenRecordEntity.rebatePrice))));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @ColorRes
    private int getOpenStatusColorRes(String str) {
        char c;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1 || c == 2) {
                return R$color.fq_nobility_open_status_red;
            }
            return R$color.fq_text_black;
        }
        return R$color.fq_nobility_open_status_green;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String getOpenStatusStr(String str) {
        char c;
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_nobility_open_status_tips);
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
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
            return c != 2 ? "" : stringArray[2];
        }
        return stringArray[0];
    }

    private Spanned getOpenTitleSpanned(NobilityOpenRecordEntity nobilityOpenRecordEntity) {
        return nobilityOpenRecordEntity.isRenew() ? Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_name_tips_2, nobilityOpenRecordEntity.nobilityName)) : Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_name_tips_1, nobilityOpenRecordEntity.nobilityName));
    }

    private Spanned getOpenPrice(NobilityOpenRecordEntity nobilityOpenRecordEntity) {
        return Html.fromHtml(this.mContext.getString(R$string.fq_nobility_open_price, AppUtils.getLiveMoneyUnitStr(this.mContext) + AppUtils.formatDisplayPrice(nobilityOpenRecordEntity.openPrice, true)));
    }

    private String getPriceStr(String str) {
        return AppUtils.formatDisplayPrice(str, true) + AppUtils.getNobilityGoldUnitStr(this.mContext);
    }

    private String getOpenTime(String str) {
        return TimeUtils.millis2String(NumberUtils.string2long(str) * 1000);
    }

    private String getOpenTime(String str, String str2) {
        return TimeUtils.millis2String(NumberUtils.string2long(str) * 1000, new SimpleDateFormat(str2));
    }
}
