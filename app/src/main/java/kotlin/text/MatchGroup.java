package kotlin.text;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;

/* compiled from: Regex.kt */
/* loaded from: classes4.dex */
public final class MatchGroup {
    private final Ranges range;
    private final String value;

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof MatchGroup)) {
                return false;
            }
            MatchGroup matchGroup = (MatchGroup) obj;
            return Intrinsics.areEqual(this.value, matchGroup.value) && Intrinsics.areEqual(this.range, matchGroup.range);
        }
        return true;
    }

    public int hashCode() {
        String str = this.value;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        Ranges ranges = this.range;
        if (ranges != null) {
            i = ranges.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "MatchGroup(value=" + this.value + ", range=" + this.range + ")";
    }

    public MatchGroup(String value, Ranges range) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(range, "range");
        this.value = value;
        this.range = range;
    }
}
