package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LazyJVM.kt */
/* loaded from: classes4.dex */
final class SynchronizedLazyImpl<T> implements Lazy<T>, Serializable {
    private volatile Object _value;
    private Functions<? extends T> initializer;
    private final Object lock;

    public SynchronizedLazyImpl(Functions<? extends T> initializer, Object obj) {
        Intrinsics.checkParameterIsNotNull(initializer, "initializer");
        this.initializer = initializer;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
        this.lock = obj == null ? this : obj;
    }

    public /* synthetic */ SynchronizedLazyImpl(Functions functions, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(functions, (i & 2) != 0 ? null : obj);
    }

    public T getValue() {
        T t;
        T t2 = (T) this._value;
        if (t2 != UNINITIALIZED_VALUE.INSTANCE) {
            return t2;
        }
        synchronized (this.lock) {
            t = (T) this._value;
            if (t == UNINITIALIZED_VALUE.INSTANCE) {
                Functions<? extends T> functions = this.initializer;
                if (functions == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                t = functions.mo6822invoke();
                this._value = t;
                this.initializer = null;
            }
        }
        return t;
    }

    public boolean isInitialized() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE;
    }

    public String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }
}
