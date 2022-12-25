package kotlin.ranges;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* renamed from: kotlin.ranges.IntRange */
/* loaded from: classes4.dex */
public final class Ranges extends Progressions implements Range<Integer> {
    public static final Companion Companion = new Companion(null);
    private static final Ranges EMPTY = new Ranges(1, 0);

    public Ranges(int i, int i2) {
        super(i, i2, 1);
    }

    public Integer getStart() {
        return Integer.valueOf(getFirst());
    }

    public Integer getEndInclusive() {
        return Integer.valueOf(getLast());
    }

    @Override // kotlin.ranges.Progressions
    public boolean isEmpty() {
        return getFirst() > getLast();
    }

    @Override // kotlin.ranges.Progressions
    public boolean equals(Object obj) {
        if (obj instanceof Ranges) {
            if (!isEmpty() || !((Ranges) obj).isEmpty()) {
                Ranges ranges = (Ranges) obj;
                if (getFirst() != ranges.getFirst() || getLast() != ranges.getLast()) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // kotlin.ranges.Progressions
    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (getFirst() * 31) + getLast();
    }

    @Override // kotlin.ranges.Progressions
    public String toString() {
        return getFirst() + ".." + getLast();
    }

    /* compiled from: Ranges.kt */
    /* renamed from: kotlin.ranges.IntRange$Companion */
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Ranges getEMPTY() {
            return Ranges.EMPTY;
        }
    }
}
