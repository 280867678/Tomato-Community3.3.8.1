package kotlin.sequences;

import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class SequencesKt extends _Sequences {
    public static /* bridge */ /* synthetic */ <T> Iterable<T> asIterable(Sequence<? extends T> sequence) {
        return _Sequences.asIterable(sequence);
    }

    public static /* bridge */ /* synthetic */ <T, R> Sequence<R> map(Sequence<? extends T> sequence, Function1<? super T, ? extends R> function1) {
        return _Sequences.map(sequence, function1);
    }
}
