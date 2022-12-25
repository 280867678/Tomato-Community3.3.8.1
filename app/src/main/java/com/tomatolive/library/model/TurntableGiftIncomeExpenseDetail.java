package com.tomatolive.library.model;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import com.blankj.utilcode.util.TimeUtils;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class TurntableGiftIncomeExpenseDetail extends GiftIncomeExpenseDetail {
    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        return z ? getHtmlSpanned(context, R$string.fq_turntable_gift_expend_tips, formatNickName(this.anchorName), this.giftNum, this.giftName) : Html.fromHtml(this.giftName + "x" + this.giftNum);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String formatRecordTime(Context context, boolean z) {
        return z ? TimeUtils.millis2String(Long.parseLong(this.createTime) * 1000) : context.getString(R$string.fq_turntable_gift_income_tips_date, formatNickName(this.userName), TimeUtils.millis2String(Long.parseLong(this.createTime) * 1000));
    }

    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? formatPrice(this.price) : this.anchorIncomePrice;
    }

    private String formatPrice(String str) {
        return String.valueOf(NumberUtils.string2int(this.giftNum) * NumberUtils.string2int(str));
    }
}
