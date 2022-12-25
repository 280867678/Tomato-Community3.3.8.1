package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class GiftIncomeExpenseDetail extends IncomeEntity {
    public String giftId = "";
    public String giftName = "";
    public String userName = "";
    public String giftNum = "";
    public String createTime = "";
    public String anchorName = "";

    @Override // com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return 0;
    }

    public String getGiftId() {
        return this.giftId;
    }

    public void setGiftId(String str) {
        this.giftId = str;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String str) {
        this.giftName = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getGiftNum() {
        return this.giftNum;
    }

    public void setGiftNum(String str) {
        this.giftNum = str;
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
        return "GiftIncomeExpenseDetail{markId='" + this.giftId + "', giftName='" + this.giftName + "', userName='" + this.userName + "', giftNum='" + this.giftNum + "', anchorIncomeGold='" + this.anchorIncomePrice + "', createTime='" + this.createTime + "', anchorName='" + this.anchorName + "', gold='" + this.price + "'}";
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return getLocalIcon(this.giftId, 1);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        String str = "x" + this.giftNum;
        if (NumberUtils.string2int(this.giftNum) <= 1) {
            str = "";
        }
        return z ? getHtmlSpanned(context, R$string.fq_expend_send, formatNickName(this.anchorName), "1", this.giftName + str) : getHtmlSpanned(context, R$string.fq_income_reward, formatNickName(this.userName), this.giftName, this.giftNum);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return z ? String.valueOf(NumberUtils.string2long(this.price) * NumberUtils.string2long(this.giftNum, 1L)) : this.anchorIncomePrice;
    }
}
