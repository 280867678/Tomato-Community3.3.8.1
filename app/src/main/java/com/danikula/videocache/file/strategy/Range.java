package com.danikula.videocache.file.strategy;

import java.lang.Comparable;

/* loaded from: classes2.dex */
public final class Range<T extends Comparable<? super T>> {
    private T mLower;
    private T mUpper;

    public Range(T t, T t2) {
        if (t.compareTo(t2) > 0) {
            throw new IllegalArgumentException("lower must be less than or equal to upper");
        }
        this.mLower = t;
        this.mUpper = t2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Range)) {
            return false;
        }
        Range range = (Range) obj;
        return this.mLower.equals(range.mLower) && this.mUpper.equals(range.mUpper);
    }

    public String toString() {
        return String.format("[%s, %s]", this.mLower, this.mUpper);
    }
}
