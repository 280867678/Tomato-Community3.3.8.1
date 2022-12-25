package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class ScoreGiftIncomeExpenseDetail extends GiftIncomeExpenseDetail {
    public String giftScore = "";
    public String popularValue = "";
    public String totalGold;

    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return 0;
    }

    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return "";
    }

    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        String str = "x" + this.giftNum;
        if (NumberUtils.string2int(this.giftNum) <= 1) {
            str = "";
        }
        return z ? getHtmlSpanned(context, R$string.fq_score_gift_expend_tips, formatNickName(this.anchorName), this.giftName, str) : getHtmlSpanned(context, R$string.fq_score_gift_income_tips, formatNickName(this.userName), this.giftName, str);
    }

    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.GiftIncomeExpenseDetail, com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? String.valueOf(NumberUtils.string2long(this.giftScore) * NumberUtils.string2long(this.giftNum, 1L)) : this.popularValue;
    }
}
