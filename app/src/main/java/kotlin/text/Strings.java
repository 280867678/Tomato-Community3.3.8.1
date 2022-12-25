package kotlin.text;

import java.util.Iterator;
import kotlin.Tuples;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;
import kotlin.sequences.Sequence;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.text.DelimitedRangesSequence */
/* loaded from: classes4.dex */
public final class Strings implements Sequence<Ranges> {
    private final Function2<CharSequence, Integer, Tuples<Integer, Integer>> getNextMatch;
    private final CharSequence input;
    private final int limit;
    private final int startIndex;

    /* JADX WARN: Multi-variable type inference failed */
    public Strings(CharSequence input, int i, int i2, Function2<? super CharSequence, ? super Integer, Tuples<Integer, Integer>> getNextMatch) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Intrinsics.checkParameterIsNotNull(getNextMatch, "getNextMatch");
        this.input = input;
        this.startIndex = i;
        this.limit = i2;
        this.getNextMatch = getNextMatch;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<Ranges> iterator() {
        return new DelimitedRangesSequence$iterator$1(this);
    }
}
