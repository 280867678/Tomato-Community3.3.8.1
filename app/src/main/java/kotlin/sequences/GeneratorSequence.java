package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Sequences.kt */
/* loaded from: classes4.dex */
final class GeneratorSequence<T> implements Sequence<T> {
    private final Functions<T> getInitialValue;
    private final Function1<T, T> getNextValue;

    /* JADX WARN: Multi-variable type inference failed */
    public GeneratorSequence(Functions<? extends T> getInitialValue, Function1<? super T, ? extends T> getNextValue) {
        Intrinsics.checkParameterIsNotNull(getInitialValue, "getInitialValue");
        Intrinsics.checkParameterIsNotNull(getNextValue, "getNextValue");
        this.getInitialValue = getInitialValue;
        this.getNextValue = getNextValue;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<T> iterator() {
        return new GeneratorSequence$iterator$1(this);
    }
}
