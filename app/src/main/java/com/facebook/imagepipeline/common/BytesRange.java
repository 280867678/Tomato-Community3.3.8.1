package com.facebook.imagepipeline.common;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.HashCodeUtil;

/* loaded from: classes2.dex */
public class BytesRange {
    public final int from;

    /* renamed from: to */
    public final int f1258to;

    public BytesRange(int i, int i2) {
        this.from = i;
        this.f1258to = i2;
    }

    public boolean contains(BytesRange bytesRange) {
        return bytesRange != null && this.from <= bytesRange.from && this.f1258to >= bytesRange.f1258to;
    }

    public String toString() {
        return String.format(null, "%s-%s", valueOrEmpty(this.from), valueOrEmpty(this.f1258to));
    }

    private static String valueOrEmpty(int i) {
        return i == Integer.MAX_VALUE ? "" : Integer.toString(i);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BytesRange)) {
            return false;
        }
        BytesRange bytesRange = (BytesRange) obj;
        return this.from == bytesRange.from && this.f1258to == bytesRange.f1258to;
    }

    public int hashCode() {
        return HashCodeUtil.hashCode(this.from, this.f1258to);
    }

    public static BytesRange from(int i) {
        Preconditions.checkArgument(i >= 0);
        return new BytesRange(i, Integer.MAX_VALUE);
    }

    public static BytesRange toMax(int i) {
        Preconditions.checkArgument(i > 0);
        return new BytesRange(0, i);
    }
}
