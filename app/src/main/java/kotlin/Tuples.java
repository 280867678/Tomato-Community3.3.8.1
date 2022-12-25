package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: kotlin.Pair */
/* loaded from: classes4.dex */
public final class Tuples<A, B> implements Serializable {
    private final A first;
    private final B second;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Tuples copy$default(Tuples tuples, Object obj, Object obj2, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = tuples.first;
        }
        if ((i & 2) != 0) {
            obj2 = tuples.second;
        }
        return tuples.copy(obj, obj2);
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final Tuples<A, B> copy(A a, B b) {
        return new Tuples<>(a, b);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Tuples)) {
                return false;
            }
            Tuples tuples = (Tuples) obj;
            return Intrinsics.areEqual(this.first, tuples.first) && Intrinsics.areEqual(this.second, tuples.second);
        }
        return true;
    }

    public int hashCode() {
        A a = this.first;
        int i = 0;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        if (b != null) {
            i = b.hashCode();
        }
        return hashCode + i;
    }

    public Tuples(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public String toString() {
        return '(' + this.first + ", " + this.second + ')';
    }
}
