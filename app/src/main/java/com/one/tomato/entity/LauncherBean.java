package com.one.tomato.entity;

/* compiled from: LauncherBean.kt */
/* loaded from: classes3.dex */
public final class LauncherBean {
    private int res;

    public static /* synthetic */ LauncherBean copy$default(LauncherBean launcherBean, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = launcherBean.res;
        }
        return launcherBean.copy(i);
    }

    public final int component1() {
        return this.res;
    }

    public final LauncherBean copy(int i) {
        return new LauncherBean(i);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof LauncherBean) {
                if (this.res == ((LauncherBean) obj).res) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.res;
    }

    public String toString() {
        return "LauncherBean(res=" + this.res + ")";
    }

    public LauncherBean(int i) {
        this.res = i;
    }

    public final int getRes() {
        return this.res;
    }

    public final void setRes(int i) {
        this.res = i;
    }
}
