package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers;

/* compiled from: Sequences.kt */
/* loaded from: classes4.dex */
public final class GeneratorSequence$iterator$1 implements Iterator<T>, KMarkers {
    private T nextItem;
    private int nextState = -2;
    final /* synthetic */ GeneratorSequence this$0;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GeneratorSequence$iterator$1(GeneratorSequence generatorSequence) {
        this.this$0 = generatorSequence;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void calcNext() {
        Function1 function1;
        T t;
        Functions functions;
        if (this.nextState == -2) {
            functions = this.this$0.getInitialValue;
            t = functions.mo6822invoke();
        } else {
            function1 = this.this$0.getNextValue;
            T t2 = this.nextItem;
            if (t2 == 0) {
                Intrinsics.throwNpe();
                throw null;
            }
            t = function1.mo6794invoke(t2);
        }
        this.nextItem = t;
        this.nextState = this.nextItem == 0 ? 0 : 1;
    }

    @Override // java.util.Iterator
    public T next() {
        if (this.nextState < 0) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        T t = this.nextItem;
        if (t == 0) {
            throw new TypeCastException("null cannot be cast to non-null type T");
        }
        this.nextState = -1;
        return t;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.nextState < 0) {
            calcNext();
        }
        return this.nextState == 1;
    }
}
