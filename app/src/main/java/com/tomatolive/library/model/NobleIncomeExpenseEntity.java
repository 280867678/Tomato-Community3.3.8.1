package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class NobleIncomeExpenseEntity extends IncomeEntity {
    private static final String TYPE_OPEN = "1";
    private static final String TYPE_RENEW = "2";
    private String userName;
    private String openType = "";
    private String nobilityName = "";
    private String nobilityType = "";
    private String anchorName = "";
    private String createTime = "";

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return null;
    }

    public String getOpenType() {
        return this.openType;
    }

    public void setOpenType(String str) {
        this.openType = str;
    }

    public String getNobilityName() {
        return this.nobilityName;
    }

    public void setNobilityName(String str) {
        this.nobilityName = str;
    }

    public String getNobilityType() {
        return this.nobilityType;
    }

    public void setNobilityType(String str) {
        this.nobilityType = str;
    }

    public String getAnchorName() {
        return this.anchorName;
    }

    public void setAnchorName(String str) {
        this.anchorName = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getAnchorIncomePrice() {
        return this.anchorIncomePrice;
    }

    public String getUserName() {
        return this.userName;
    }

    private String formatNobleOpenType(Context context, String str) {
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
                return context.getString(R$string.fq_text_renew);
            }
            return context.getString(R$string.fq_text_open);
        }
        return context.getString(R$string.fq_text_open);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return AppUtils.getNobilityBadgeDrawableRes(NumberUtils.string2int(this.nobilityType));
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        return z ? getHtmlSpanned(context, R$string.fq_text_noble_expense, formatNobleOpenType(context, this.openType), this.nobilityName) : getHtmlSpanned(context, R$string.fq_text_noble_income, formatNickName(this.userName), formatNobleOpenType(context, this.openType), this.nobilityName);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? this.price : this.anchorIncomePrice;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public boolean hasNobleExtraText(boolean z) {
        return z && TextUtils.equals(this.openType, "1") && !TextUtils.isEmpty(this.anchorName);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getNobleExtraText(Context context, boolean z) {
        int i = R$string.fq_text_beneficiary;
        Object[] objArr = new Object[1];
        objArr[0] = z ? this.anchorName : this.userName;
        return context.getString(i, objArr);
    }
}
