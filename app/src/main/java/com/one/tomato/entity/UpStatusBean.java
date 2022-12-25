package com.one.tomato.entity;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpStatusBean.kt */
/* loaded from: classes3.dex */
public final class UpStatusBean {
    private final String canWithdrawProfit;
    private final String certificateTime;
    private final long conArticleCount;
    private final long conFansCount;
    private final long conThumbUpCount;
    private final long conViewedCount;
    private final String countOriginal;
    private final String createTime;
    private final long curArticleCount;
    private final long curFansCount;
    private final long curThumbUpCount;
    private final String curUpHostCount;
    private final long curViewedCount;
    private final int level;
    private final int originalFlag;
    private final int priceMonth;
    private final int priceSeason;
    private final int priceYear;
    private final String profit;
    private final String rejectReason;
    private final int remainDays;
    private final String status;
    private final long subscribeCount;
    private final int subscribeSwitch;
    private final long todayViewedCount;
    private final long totalIncome;
    private final int upType;

    public static /* synthetic */ UpStatusBean copy$default(UpStatusBean upStatusBean, String str, long j, long j2, long j3, long j4, String str2, long j5, long j6, long j7, long j8, String str3, int i, String str4, long j9, int i2, int i3, String str5, String str6, String str7, long j10, int i4, String str8, long j11, int i5, int i6, int i7, int i8, int i9, Object obj) {
        String str9 = (i9 & 1) != 0 ? upStatusBean.canWithdrawProfit : str;
        long j12 = (i9 & 2) != 0 ? upStatusBean.conArticleCount : j;
        long j13 = (i9 & 4) != 0 ? upStatusBean.conFansCount : j2;
        long j14 = (i9 & 8) != 0 ? upStatusBean.conThumbUpCount : j3;
        long j15 = (i9 & 16) != 0 ? upStatusBean.conViewedCount : j4;
        String str10 = (i9 & 32) != 0 ? upStatusBean.createTime : str2;
        long j16 = (i9 & 64) != 0 ? upStatusBean.curArticleCount : j5;
        long j17 = (i9 & 128) != 0 ? upStatusBean.curFansCount : j6;
        long j18 = (i9 & 256) != 0 ? upStatusBean.curThumbUpCount : j7;
        long j19 = (i9 & 512) != 0 ? upStatusBean.curViewedCount : j8;
        String str11 = (i9 & 1024) != 0 ? upStatusBean.profit : str3;
        int i10 = (i9 & 2048) != 0 ? upStatusBean.remainDays : i;
        String str12 = (i9 & 4096) != 0 ? upStatusBean.status : str4;
        String str13 = str11;
        long j20 = (i9 & 8192) != 0 ? upStatusBean.todayViewedCount : j9;
        int i11 = (i9 & 16384) != 0 ? upStatusBean.upType : i2;
        return upStatusBean.copy(str9, j12, j13, j14, j15, str10, j16, j17, j18, j19, str13, i10, str12, j20, i11, (32768 & i9) != 0 ? upStatusBean.level : i3, (i9 & 65536) != 0 ? upStatusBean.rejectReason : str5, (i9 & 131072) != 0 ? upStatusBean.certificateTime : str6, (i9 & 262144) != 0 ? upStatusBean.curUpHostCount : str7, (i9 & 524288) != 0 ? upStatusBean.totalIncome : j10, (i9 & 1048576) != 0 ? upStatusBean.originalFlag : i4, (2097152 & i9) != 0 ? upStatusBean.countOriginal : str8, (i9 & 4194304) != 0 ? upStatusBean.subscribeCount : j11, (i9 & 8388608) != 0 ? upStatusBean.priceMonth : i5, (16777216 & i9) != 0 ? upStatusBean.priceSeason : i6, (i9 & 33554432) != 0 ? upStatusBean.priceYear : i7, (i9 & 67108864) != 0 ? upStatusBean.subscribeSwitch : i8);
    }

    public final String component1() {
        return this.canWithdrawProfit;
    }

    public final long component10() {
        return this.curViewedCount;
    }

    public final String component11() {
        return this.profit;
    }

    public final int component12() {
        return this.remainDays;
    }

    public final String component13() {
        return this.status;
    }

    public final long component14() {
        return this.todayViewedCount;
    }

    public final int component15() {
        return this.upType;
    }

    public final int component16() {
        return this.level;
    }

    public final String component17() {
        return this.rejectReason;
    }

    public final String component18() {
        return this.certificateTime;
    }

    public final String component19() {
        return this.curUpHostCount;
    }

    public final long component2() {
        return this.conArticleCount;
    }

    public final long component20() {
        return this.totalIncome;
    }

    public final int component21() {
        return this.originalFlag;
    }

    public final String component22() {
        return this.countOriginal;
    }

    public final long component23() {
        return this.subscribeCount;
    }

    public final int component24() {
        return this.priceMonth;
    }

    public final int component25() {
        return this.priceSeason;
    }

    public final int component26() {
        return this.priceYear;
    }

    public final int component27() {
        return this.subscribeSwitch;
    }

    public final long component3() {
        return this.conFansCount;
    }

    public final long component4() {
        return this.conThumbUpCount;
    }

    public final long component5() {
        return this.conViewedCount;
    }

    public final String component6() {
        return this.createTime;
    }

    public final long component7() {
        return this.curArticleCount;
    }

    public final long component8() {
        return this.curFansCount;
    }

    public final long component9() {
        return this.curThumbUpCount;
    }

    public final UpStatusBean copy(String canWithdrawProfit, long j, long j2, long j3, long j4, String createTime, long j5, long j6, long j7, long j8, String profit, int i, String status, long j9, int i2, int i3, String rejectReason, String certificateTime, String curUpHostCount, long j10, int i4, String countOriginal, long j11, int i5, int i6, int i7, int i8) {
        Intrinsics.checkParameterIsNotNull(canWithdrawProfit, "canWithdrawProfit");
        Intrinsics.checkParameterIsNotNull(createTime, "createTime");
        Intrinsics.checkParameterIsNotNull(profit, "profit");
        Intrinsics.checkParameterIsNotNull(status, "status");
        Intrinsics.checkParameterIsNotNull(rejectReason, "rejectReason");
        Intrinsics.checkParameterIsNotNull(certificateTime, "certificateTime");
        Intrinsics.checkParameterIsNotNull(curUpHostCount, "curUpHostCount");
        Intrinsics.checkParameterIsNotNull(countOriginal, "countOriginal");
        return new UpStatusBean(canWithdrawProfit, j, j2, j3, j4, createTime, j5, j6, j7, j8, profit, i, status, j9, i2, i3, rejectReason, certificateTime, curUpHostCount, j10, i4, countOriginal, j11, i5, i6, i7, i8);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof UpStatusBean) {
                UpStatusBean upStatusBean = (UpStatusBean) obj;
                if (Intrinsics.areEqual(this.canWithdrawProfit, upStatusBean.canWithdrawProfit)) {
                    if (this.conArticleCount == upStatusBean.conArticleCount) {
                        if (this.conFansCount == upStatusBean.conFansCount) {
                            if (this.conThumbUpCount == upStatusBean.conThumbUpCount) {
                                if ((this.conViewedCount == upStatusBean.conViewedCount) && Intrinsics.areEqual(this.createTime, upStatusBean.createTime)) {
                                    if (this.curArticleCount == upStatusBean.curArticleCount) {
                                        if (this.curFansCount == upStatusBean.curFansCount) {
                                            if (this.curThumbUpCount == upStatusBean.curThumbUpCount) {
                                                if ((this.curViewedCount == upStatusBean.curViewedCount) && Intrinsics.areEqual(this.profit, upStatusBean.profit)) {
                                                    if ((this.remainDays == upStatusBean.remainDays) && Intrinsics.areEqual(this.status, upStatusBean.status)) {
                                                        if (this.todayViewedCount == upStatusBean.todayViewedCount) {
                                                            if (this.upType == upStatusBean.upType) {
                                                                if ((this.level == upStatusBean.level) && Intrinsics.areEqual(this.rejectReason, upStatusBean.rejectReason) && Intrinsics.areEqual(this.certificateTime, upStatusBean.certificateTime) && Intrinsics.areEqual(this.curUpHostCount, upStatusBean.curUpHostCount)) {
                                                                    if (this.totalIncome == upStatusBean.totalIncome) {
                                                                        if ((this.originalFlag == upStatusBean.originalFlag) && Intrinsics.areEqual(this.countOriginal, upStatusBean.countOriginal)) {
                                                                            if (this.subscribeCount == upStatusBean.subscribeCount) {
                                                                                if (this.priceMonth == upStatusBean.priceMonth) {
                                                                                    if (this.priceSeason == upStatusBean.priceSeason) {
                                                                                        if (this.priceYear == upStatusBean.priceYear) {
                                                                                            if (this.subscribeSwitch == upStatusBean.subscribeSwitch) {
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.canWithdrawProfit;
        int i = 0;
        int hashCode = str != null ? str.hashCode() : 0;
        long j = this.conArticleCount;
        long j2 = this.conFansCount;
        long j3 = this.conThumbUpCount;
        long j4 = this.conViewedCount;
        int i2 = ((((((((hashCode * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + ((int) (j4 ^ (j4 >>> 32)))) * 31;
        String str2 = this.createTime;
        int hashCode2 = str2 != null ? str2.hashCode() : 0;
        long j5 = this.curArticleCount;
        long j6 = this.curFansCount;
        long j7 = this.curThumbUpCount;
        long j8 = this.curViewedCount;
        int i3 = (((((((((i2 + hashCode2) * 31) + ((int) (j5 ^ (j5 >>> 32)))) * 31) + ((int) (j6 ^ (j6 >>> 32)))) * 31) + ((int) (j7 ^ (j7 >>> 32)))) * 31) + ((int) (j8 ^ (j8 >>> 32)))) * 31;
        String str3 = this.profit;
        int hashCode3 = (((i3 + (str3 != null ? str3.hashCode() : 0)) * 31) + this.remainDays) * 31;
        String str4 = this.status;
        int hashCode4 = str4 != null ? str4.hashCode() : 0;
        long j9 = this.todayViewedCount;
        int i4 = (((((((hashCode3 + hashCode4) * 31) + ((int) (j9 ^ (j9 >>> 32)))) * 31) + this.upType) * 31) + this.level) * 31;
        String str5 = this.rejectReason;
        int hashCode5 = (i4 + (str5 != null ? str5.hashCode() : 0)) * 31;
        String str6 = this.certificateTime;
        int hashCode6 = (hashCode5 + (str6 != null ? str6.hashCode() : 0)) * 31;
        String str7 = this.curUpHostCount;
        int hashCode7 = str7 != null ? str7.hashCode() : 0;
        long j10 = this.totalIncome;
        int i5 = (((((hashCode6 + hashCode7) * 31) + ((int) (j10 ^ (j10 >>> 32)))) * 31) + this.originalFlag) * 31;
        String str8 = this.countOriginal;
        if (str8 != null) {
            i = str8.hashCode();
        }
        long j11 = this.subscribeCount;
        return ((((((((((i5 + i) * 31) + ((int) (j11 ^ (j11 >>> 32)))) * 31) + this.priceMonth) * 31) + this.priceSeason) * 31) + this.priceYear) * 31) + this.subscribeSwitch;
    }

    public String toString() {
        return "UpStatusBean(canWithdrawProfit=" + this.canWithdrawProfit + ", conArticleCount=" + this.conArticleCount + ", conFansCount=" + this.conFansCount + ", conThumbUpCount=" + this.conThumbUpCount + ", conViewedCount=" + this.conViewedCount + ", createTime=" + this.createTime + ", curArticleCount=" + this.curArticleCount + ", curFansCount=" + this.curFansCount + ", curThumbUpCount=" + this.curThumbUpCount + ", curViewedCount=" + this.curViewedCount + ", profit=" + this.profit + ", remainDays=" + this.remainDays + ", status=" + this.status + ", todayViewedCount=" + this.todayViewedCount + ", upType=" + this.upType + ", level=" + this.level + ", rejectReason=" + this.rejectReason + ", certificateTime=" + this.certificateTime + ", curUpHostCount=" + this.curUpHostCount + ", totalIncome=" + this.totalIncome + ", originalFlag=" + this.originalFlag + ", countOriginal=" + this.countOriginal + ", subscribeCount=" + this.subscribeCount + ", priceMonth=" + this.priceMonth + ", priceSeason=" + this.priceSeason + ", priceYear=" + this.priceYear + ", subscribeSwitch=" + this.subscribeSwitch + ")";
    }

    public UpStatusBean(String canWithdrawProfit, long j, long j2, long j3, long j4, String createTime, long j5, long j6, long j7, long j8, String profit, int i, String status, long j9, int i2, int i3, String rejectReason, String certificateTime, String curUpHostCount, long j10, int i4, String countOriginal, long j11, int i5, int i6, int i7, int i8) {
        Intrinsics.checkParameterIsNotNull(canWithdrawProfit, "canWithdrawProfit");
        Intrinsics.checkParameterIsNotNull(createTime, "createTime");
        Intrinsics.checkParameterIsNotNull(profit, "profit");
        Intrinsics.checkParameterIsNotNull(status, "status");
        Intrinsics.checkParameterIsNotNull(rejectReason, "rejectReason");
        Intrinsics.checkParameterIsNotNull(certificateTime, "certificateTime");
        Intrinsics.checkParameterIsNotNull(curUpHostCount, "curUpHostCount");
        Intrinsics.checkParameterIsNotNull(countOriginal, "countOriginal");
        this.canWithdrawProfit = canWithdrawProfit;
        this.conArticleCount = j;
        this.conFansCount = j2;
        this.conThumbUpCount = j3;
        this.conViewedCount = j4;
        this.createTime = createTime;
        this.curArticleCount = j5;
        this.curFansCount = j6;
        this.curThumbUpCount = j7;
        this.curViewedCount = j8;
        this.profit = profit;
        this.remainDays = i;
        this.status = status;
        this.todayViewedCount = j9;
        this.upType = i2;
        this.level = i3;
        this.rejectReason = rejectReason;
        this.certificateTime = certificateTime;
        this.curUpHostCount = curUpHostCount;
        this.totalIncome = j10;
        this.originalFlag = i4;
        this.countOriginal = countOriginal;
        this.subscribeCount = j11;
        this.priceMonth = i5;
        this.priceSeason = i6;
        this.priceYear = i7;
        this.subscribeSwitch = i8;
    }

    public final String getCanWithdrawProfit() {
        return this.canWithdrawProfit;
    }

    public final long getConArticleCount() {
        return this.conArticleCount;
    }

    public final long getConFansCount() {
        return this.conFansCount;
    }

    public final long getConThumbUpCount() {
        return this.conThumbUpCount;
    }

    public final long getConViewedCount() {
        return this.conViewedCount;
    }

    public final String getCreateTime() {
        return this.createTime;
    }

    public final long getCurArticleCount() {
        return this.curArticleCount;
    }

    public final long getCurFansCount() {
        return this.curFansCount;
    }

    public final long getCurThumbUpCount() {
        return this.curThumbUpCount;
    }

    public final long getCurViewedCount() {
        return this.curViewedCount;
    }

    public final String getProfit() {
        return this.profit;
    }

    public final int getRemainDays() {
        return this.remainDays;
    }

    public final String getStatus() {
        return this.status;
    }

    public final long getTodayViewedCount() {
        return this.todayViewedCount;
    }

    public final int getUpType() {
        return this.upType;
    }

    public final int getLevel() {
        return this.level;
    }

    public final String getRejectReason() {
        return this.rejectReason;
    }

    public final String getCertificateTime() {
        return this.certificateTime;
    }

    public final String getCurUpHostCount() {
        return this.curUpHostCount;
    }

    public final long getTotalIncome() {
        return this.totalIncome;
    }

    public final int getOriginalFlag() {
        return this.originalFlag;
    }

    public final String getCountOriginal() {
        return this.countOriginal;
    }

    public final long getSubscribeCount() {
        return this.subscribeCount;
    }

    public final int getPriceMonth() {
        return this.priceMonth;
    }

    public final int getPriceSeason() {
        return this.priceSeason;
    }

    public final int getPriceYear() {
        return this.priceYear;
    }

    public final int getSubscribeSwitch() {
        return this.subscribeSwitch;
    }
}
