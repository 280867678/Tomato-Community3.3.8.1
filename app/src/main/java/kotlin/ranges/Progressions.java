package kotlin.ranges;

import java.util.Iterator;
import kotlin.internal.progressionUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMarkers;

/* renamed from: kotlin.ranges.IntProgression */
/* loaded from: classes4.dex */
public class Progressions implements Iterable<Integer>, KMarkers {
    public static final Companion Companion = new Companion(null);
    private final int first;
    private final int last;
    private final int step;

    public Progressions(int i, int i2, int i3) {
        if (i3 != 0) {
            if (i3 == Integer.MIN_VALUE) {
                throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
            }
            this.first = i;
            this.last = progressionUtil.getProgressionLastElement(i, i2, i3);
            this.step = i3;
            return;
        }
        throw new IllegalArgumentException("Step must be non-zero.");
    }

    public final int getFirst() {
        return this.first;
    }

    public final int getLast() {
        return this.last;
    }

    public final int getStep() {
        return this.step;
    }

    @Override // java.lang.Iterable
    public Iterator<Integer> iterator() {
        return new ProgressionIterators(this.first, this.last, this.step);
    }

    public boolean isEmpty() {
        if (this.step > 0) {
            if (this.first > this.last) {
                return true;
            }
        } else if (this.first < this.last) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Progressions) {
            if (!isEmpty() || !((Progressions) obj).isEmpty()) {
                Progressions progressions = (Progressions) obj;
                if (this.first != progressions.first || this.last != progressions.last || this.step != progressions.step) {
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.first * 31) + this.last) * 31) + this.step;
    }

    public String toString() {
        StringBuilder sb;
        int i;
        if (this.step > 0) {
            sb = new StringBuilder();
            sb.append(this.first);
            sb.append("..");
            sb.append(this.last);
            sb.append(" step ");
            i = this.step;
        } else {
            sb = new StringBuilder();
            sb.append(this.first);
            sb.append(" downTo ");
            sb.append(this.last);
            sb.append(" step ");
            i = -this.step;
        }
        sb.append(i);
        return sb.toString();
    }

    /* compiled from: Progressions.kt */
    /* renamed from: kotlin.ranges.IntProgression$Companion */
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Progressions fromClosedRange(int i, int i2, int i3) {
            return new Progressions(i, i2, i3);
        }
    }
}
