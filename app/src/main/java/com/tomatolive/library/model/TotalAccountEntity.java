package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class TotalAccountEntity {
    private int imgRes;
    private boolean isShowTotal;
    private int titleRes;
    private String totalAccount;
    private int type;

    public TotalAccountEntity(int i, int i2, int i3, String str) {
        this.isShowTotal = true;
        this.type = i;
        this.imgRes = i2;
        this.titleRes = i3;
        this.totalAccount = str;
    }

    public TotalAccountEntity(int i, int i2, int i3, String str, boolean z) {
        this.isShowTotal = true;
        this.type = i;
        this.imgRes = i2;
        this.titleRes = i3;
        this.totalAccount = str;
        this.isShowTotal = z;
    }

    public int getImgRes() {
        return this.imgRes;
    }

    public int getTitleRes() {
        return this.titleRes;
    }

    public String getTotalAccount() {
        return this.totalAccount;
    }

    public int getType() {
        return this.type;
    }

    public boolean isShowTotal() {
        return this.isShowTotal;
    }

    public void setShowTotal(boolean z) {
        this.isShowTotal = z;
    }
}
