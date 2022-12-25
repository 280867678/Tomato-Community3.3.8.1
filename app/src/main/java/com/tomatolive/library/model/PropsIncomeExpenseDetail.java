package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.annotations.SerializedName;
import com.tomatolive.library.R$string;

/* loaded from: classes3.dex */
public class PropsIncomeExpenseDetail extends IncomeEntity {
    @SerializedName("propNum")
    public String count;
    public String createTime;
    public String propId;
    public String propName;
    public String totalGold;
    public String userName = "";
    public String anchorName = "";

    @Override // com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return 0;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return "";
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        return z ? getHtmlSpanned(context, R$string.fq_turntable_gift_expend_tips, formatNickName(this.anchorName), this.count, this.propName) : getHtmlSpanned(context, R$string.fq_props_name_tips, this.propName, this.count);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? "" : this.anchorIncomePrice;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getPropExtraText(Context context, boolean z) {
        return context.getString(z ? R$string.fq_props_expend_tips : R$string.fq_props_income_tips, z ? this.anchorName : this.userName);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String formatRecordTime(Context context, boolean z) {
        return z ? TimeUtils.millis2String(Long.parseLong(this.createTime) * 1000) : context.getString(R$string.fq_turntable_gift_income_tips_date, formatNickName(z ? this.anchorName : this.userName), TimeUtils.millis2String(Long.parseLong(this.createTime) * 1000));
    }
}
