package com.one.tomato.entity;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpRankListBean.kt */
/* loaded from: classes3.dex */
public final class UpRankListBean {
    private final String avatar;
    private final int income;
    private final int memberId;
    private final String memberName;

    public static /* synthetic */ UpRankListBean copy$default(UpRankListBean upRankListBean, String str, int i, int i2, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = upRankListBean.avatar;
        }
        if ((i3 & 2) != 0) {
            i = upRankListBean.income;
        }
        if ((i3 & 4) != 0) {
            i2 = upRankListBean.memberId;
        }
        if ((i3 & 8) != 0) {
            str2 = upRankListBean.memberName;
        }
        return upRankListBean.copy(str, i, i2, str2);
    }

    public final String component1() {
        return this.avatar;
    }

    public final int component2() {
        return this.income;
    }

    public final int component3() {
        return this.memberId;
    }

    public final String component4() {
        return this.memberName;
    }

    public final UpRankListBean copy(String avatar, int i, int i2, String memberName) {
        Intrinsics.checkParameterIsNotNull(avatar, "avatar");
        Intrinsics.checkParameterIsNotNull(memberName, "memberName");
        return new UpRankListBean(avatar, i, i2, memberName);
    }

    public int hashCode() {
        String str = this.avatar;
        int i = 0;
        int hashCode = (((((str != null ? str.hashCode() : 0) * 31) + this.income) * 31) + this.memberId) * 31;
        String str2 = this.memberName;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "UpRankListBean(avatar=" + this.avatar + ", income=" + this.income + ", memberId=" + this.memberId + ", memberName=" + this.memberName + ")";
    }

    public UpRankListBean(String avatar, int i, int i2, String memberName) {
        Intrinsics.checkParameterIsNotNull(avatar, "avatar");
        Intrinsics.checkParameterIsNotNull(memberName, "memberName");
        this.avatar = avatar;
        this.income = i;
        this.memberId = i2;
        this.memberName = memberName;
    }

    public final String getAvatar() {
        return this.avatar;
    }

    public final int getIncome() {
        return this.income;
    }

    public final int getMemberId() {
        return this.memberId;
    }

    public final String getMemberName() {
        return this.memberName;
    }

    public boolean equals(Object obj) {
        if (obj instanceof UpRankListBean) {
            return this.memberId == ((UpRankListBean) obj).memberId;
        }
        return super.equals(obj);
    }
}
