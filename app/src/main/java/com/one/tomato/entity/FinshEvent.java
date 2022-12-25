package com.one.tomato.entity;

/* compiled from: FinshEvent.kt */
/* loaded from: classes3.dex */
public final class FinshEvent {
    private boolean isFinsh;

    public static /* synthetic */ FinshEvent copy$default(FinshEvent finshEvent, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = finshEvent.isFinsh;
        }
        return finshEvent.copy(z);
    }

    public final boolean component1() {
        return this.isFinsh;
    }

    public final FinshEvent copy(boolean z) {
        return new FinshEvent(z);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof FinshEvent) {
                if (this.isFinsh == ((FinshEvent) obj).isFinsh) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        boolean z = this.isFinsh;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public String toString() {
        return "FinshEvent(isFinsh=" + this.isFinsh + ")";
    }

    public FinshEvent(boolean z) {
        this.isFinsh = z;
    }

    public final boolean isFinsh() {
        return this.isFinsh;
    }

    public final void setFinsh(boolean z) {
        this.isFinsh = z;
    }
}
