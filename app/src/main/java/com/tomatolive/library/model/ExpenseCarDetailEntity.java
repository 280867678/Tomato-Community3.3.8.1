package com.tomatolive.library.model;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import com.tomatolive.library.R$string;

/* loaded from: classes3.dex */
public class ExpenseCarDetailEntity extends IncomeEntity {
    private String imgUrl;
    private String createTime = "";
    private String carId = "";
    private String carName = "";
    private String duringDate = "";

    @Override // com.tomatolive.library.model.IncomeEntity
    public int getIconImg() {
        return 0;
    }

    public String getCarId() {
        return this.carId;
    }

    public void setCarId(String str) {
        this.carId = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getCarName() {
        return this.carName;
    }

    public void setCarName(String str) {
        this.carName = str;
    }

    public String getDuringDate() {
        return this.duringDate;
    }

    public void setDuringDate(String str) {
        this.duringDate = str;
    }

    public String toString() {
        return "ExpenseCarDetailEntity{createTime='" + this.createTime + "', carId='" + this.carId + "', gold='" + this.price + "', carName='" + this.carName + "', duringDate='" + this.duringDate + "'}";
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getImgUrl() {
        return TextUtils.isEmpty(this.imgUrl) ? "" : this.imgUrl;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public Spanned getFirstLine(Context context, boolean z) {
        return getHtmlSpanned(context, R$string.fq_expend_pay, this.carName, this.duringDate);
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getRecordTime() {
        return this.createTime;
    }

    @Override // com.tomatolive.library.model.IncomeEntity
    public String getCount(boolean z) {
        return this.price;
    }
}
