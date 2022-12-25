package com.one.tomato.entity;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpAchievementBean.kt */
/* loaded from: classes3.dex */
public final class UpAchievementBean {
    private String name;
    private long num;

    public static /* synthetic */ UpAchievementBean copy$default(UpAchievementBean upAchievementBean, long j, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            j = upAchievementBean.num;
        }
        if ((i & 2) != 0) {
            str = upAchievementBean.name;
        }
        return upAchievementBean.copy(j, str);
    }

    public final long component1() {
        return this.num;
    }

    public final String component2() {
        return this.name;
    }

    public final UpAchievementBean copy(long j, String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return new UpAchievementBean(j, name);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof UpAchievementBean) {
                UpAchievementBean upAchievementBean = (UpAchievementBean) obj;
                if (!(this.num == upAchievementBean.num) || !Intrinsics.areEqual(this.name, upAchievementBean.name)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        long j = this.num;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        String str = this.name;
        return i + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        return "UpAchievementBean(num=" + this.num + ", name=" + this.name + ")";
    }

    public UpAchievementBean(long j, String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.num = j;
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public final long getNum() {
        return this.num;
    }

    public final void setName(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.name = str;
    }

    public final void setNum(long j) {
        this.num = j;
    }
}
