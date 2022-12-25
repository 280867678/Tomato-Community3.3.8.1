package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Tuples;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMarkers;
import kotlin.ranges.Ranges;
import kotlin.ranges._Ranges;

/* compiled from: Strings.kt */
/* loaded from: classes4.dex */
public final class DelimitedRangesSequence$iterator$1 implements Iterator<Ranges>, KMarkers {
    private int counter;
    private int currentStartIndex;
    private Ranges nextItem;
    private int nextSearchIndex;
    private int nextState = -1;
    final /* synthetic */ Strings this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DelimitedRangesSequence$iterator$1(Strings strings) {
        int i;
        CharSequence charSequence;
        int coerceIn;
        this.this$0 = strings;
        i = strings.startIndex;
        charSequence = strings.input;
        coerceIn = _Ranges.coerceIn(i, 0, charSequence.length());
        this.currentStartIndex = coerceIn;
        this.nextSearchIndex = this.currentStartIndex;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0023, code lost:
        if (r0 < r4) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void calcNext() {
        int i;
        CharSequence charSequence;
        Function2 function2;
        CharSequence charSequence2;
        Ranges until;
        CharSequence charSequence3;
        CharSequence charSequence4;
        int i2;
        int i3 = 0;
        if (this.nextSearchIndex >= 0) {
            i = this.this$0.limit;
            if (i > 0) {
                this.counter++;
                int i4 = this.counter;
                i2 = this.this$0.limit;
            }
            int i5 = this.nextSearchIndex;
            charSequence = this.this$0.input;
            if (i5 <= charSequence.length()) {
                function2 = this.this$0.getNextMatch;
                charSequence2 = this.this$0.input;
                Tuples tuples = (Tuples) function2.invoke(charSequence2, Integer.valueOf(this.nextSearchIndex));
                if (tuples == null) {
                    int i6 = this.currentStartIndex;
                    charSequence3 = this.this$0.input;
                    this.nextItem = new Ranges(i6, StringsKt__StringsKt.getLastIndex(charSequence3));
                    this.nextSearchIndex = -1;
                } else {
                    int intValue = ((Number) tuples.component1()).intValue();
                    int intValue2 = ((Number) tuples.component2()).intValue();
                    until = _Ranges.until(this.currentStartIndex, intValue);
                    this.nextItem = until;
                    this.currentStartIndex = intValue + intValue2;
                    int i7 = this.currentStartIndex;
                    if (intValue2 == 0) {
                        i3 = 1;
                    }
                    this.nextSearchIndex = i7 + i3;
                }
                this.nextState = 1;
                return;
            }
            int i8 = this.currentStartIndex;
            charSequence4 = this.this$0.input;
            this.nextItem = new Ranges(i8, StringsKt__StringsKt.getLastIndex(charSequence4));
            this.nextSearchIndex = -1;
            this.nextState = 1;
            return;
        }
        this.nextState = 0;
        this.nextItem = null;
    }

    @Override // java.util.Iterator
    public Ranges next() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        Ranges ranges = this.nextItem;
        if (ranges == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
        }
        this.nextItem = null;
        this.nextState = -1;
        return ranges;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.nextState == -1) {
            calcNext();
        }
        return this.nextState == 1;
    }
}
