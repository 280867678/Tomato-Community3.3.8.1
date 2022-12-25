package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.AppUtils;

/* loaded from: classes3.dex */
public class GuardIncomeExpenseDetail extends IncomeEntity {
    private String guardId = "";
    private String userName = "";
    private String createTime = "";
    private String guardName = "";
    private String guardType = "";
    private String anchorName = "";

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return null;
    }

    public String getGuardId() {
        return this.guardId;
    }

    public void setGuardId(String str) {
        this.guardId = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getAnchorIncomePrice() {
        return this.anchorIncomePrice;
    }

    public void setAnchorIncomePrice(String str) {
        this.anchorIncomePrice = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getGuardName() {
        return this.guardName;
    }

    public void setGuardName(String str) {
        this.guardName = str;
    }

    public String getGuardType() {
        return this.guardType;
    }

    public void setGuardType(String str) {
        this.guardType = str;
    }

    public String getAnchorName() {
        return this.anchorName;
    }

    public void setAnchorName(String str) {
        this.anchorName = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String toString() {
        return "GuardIncomeExpenseDetail{guardId='" + this.guardId + "', userName='" + this.userName + "', anchorIncomeGold='" + this.anchorIncomePrice + "', createTime='" + this.createTime + "', guardName='" + this.guardName + "', guardType='" + this.guardType + "', anchorName='" + this.anchorName + "', gold='" + this.price + "'}";
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return TextUtils.equals(this.guardType, "3") ? R$drawable.fq_ic_guard_year_icon : R$drawable.fq_ic_guard_month_icon;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        return z ? getHtmlSpanned(context, R$string.fq_expend_open, formatNickName(this.anchorName), AppUtils.getGuardTypeStr(context, this.guardType)) : getHtmlSpanned(context, R$string.fq_income_open, formatNickName(this.userName), AppUtils.getGuardTypeStr(context, this.guardType));
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? this.price : this.anchorIncomePrice;
    }
}
