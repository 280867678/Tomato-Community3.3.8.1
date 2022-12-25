package kotlin.coroutines;

import java.io.Serializable;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

/* renamed from: kotlin.coroutines.CombinedContext */
/* loaded from: classes4.dex */
public final class CoroutineContextImpl implements CoroutineContext, Serializable {
    private final CoroutineContext.Element element;
    private final CoroutineContext left;

    public CoroutineContextImpl(CoroutineContext left, CoroutineContext.Element element) {
        Intrinsics.checkParameterIsNotNull(left, "left");
        Intrinsics.checkParameterIsNotNull(element, "element");
        this.left = left;
        this.element = element;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return CoroutineContext.DefaultImpls.plus(this, context);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        CoroutineContextImpl coroutineContextImpl = this;
        while (true) {
            E e = (E) coroutineContextImpl.element.get(key);
            if (e != null) {
                return e;
            }
            CoroutineContext coroutineContext = coroutineContextImpl.left;
            if (coroutineContext instanceof CoroutineContextImpl) {
                coroutineContextImpl = (CoroutineContextImpl) coroutineContext;
            } else {
                return (E) coroutineContext.get(key);
            }
        }
    }

    @Override // kotlin.coroutines.CoroutineContext
    public <R> R fold(R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        return operation.invoke((Object) this.left.fold(r, operation), this.element);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        if (this.element.get(key) != null) {
            return this.left;
        }
        CoroutineContext minusKey = this.left.minusKey(key);
        return minusKey == this.left ? this : minusKey == EmptyCoroutineContext.INSTANCE ? this.element : new CoroutineContextImpl(minusKey, this.element);
    }

    private final int size() {
        int i = 2;
        CoroutineContextImpl coroutineContextImpl = this;
        while (true) {
            CoroutineContext coroutineContext = coroutineContextImpl.left;
            if (!(coroutineContext instanceof CoroutineContextImpl)) {
                coroutineContext = null;
            }
            coroutineContextImpl = (CoroutineContextImpl) coroutineContext;
            if (coroutineContextImpl != null) {
                i++;
            } else {
                return i;
            }
        }
    }

    private final boolean contains(CoroutineContext.Element element) {
        return Intrinsics.areEqual(get(element.getKey()), element);
    }

    private final boolean containsAll(CoroutineContextImpl coroutineContextImpl) {
        while (contains(coroutineContextImpl.element)) {
            CoroutineContext coroutineContext = coroutineContextImpl.left;
            if (!(coroutineContext instanceof CoroutineContextImpl)) {
                if (coroutineContext == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
                }
                return contains((CoroutineContext.Element) coroutineContext);
            }
            coroutineContextImpl = (CoroutineContextImpl) coroutineContext;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof CoroutineContextImpl) {
                CoroutineContextImpl coroutineContextImpl = (CoroutineContextImpl) obj;
                if (coroutineContextImpl.size() != size() || !coroutineContextImpl.containsAll(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    public String toString() {
        return "[" + ((String) fold("", CombinedContext$toString$1.INSTANCE)) + "]";
    }

    private final Object writeReplace() {
        int size = size();
        CoroutineContext[] coroutineContextArr = new CoroutineContext[size];
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        boolean z = false;
        ref$IntRef.element = 0;
        fold(Unit.INSTANCE, new CombinedContext$writeReplace$1(coroutineContextArr, ref$IntRef));
        if (ref$IntRef.element == size) {
            z = true;
        }
        if (!z) {
            throw new IllegalStateException("Check failed.".toString());
        }
        return new Serialized(coroutineContextArr);
    }

    /* compiled from: CoroutineContextImpl.kt */
    /* renamed from: kotlin.coroutines.CombinedContext$Serialized */
    /* loaded from: classes4.dex */
    private static final class Serialized implements Serializable {
        public static final Companion Companion = new Companion(null);
        private static final long serialVersionUID = 0;
        private final CoroutineContext[] elements;

        /* compiled from: CoroutineContextImpl.kt */
        /* renamed from: kotlin.coroutines.CombinedContext$Serialized$Companion */
        /* loaded from: classes4.dex */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Serialized(CoroutineContext[] elements) {
            Intrinsics.checkParameterIsNotNull(elements, "elements");
            this.elements = elements;
        }

        public final CoroutineContext[] getElements() {
            return this.elements;
        }

        private final Object readResolve() {
            CoroutineContext[] coroutineContextArr = this.elements;
            CoroutineContext coroutineContext = EmptyCoroutineContext.INSTANCE;
            for (CoroutineContext coroutineContext2 : coroutineContextArr) {
                coroutineContext = coroutineContext.plus(coroutineContext2);
            }
            return coroutineContext;
        }
    }
}
