package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Lazy.kt */
/* loaded from: classes4.dex */
public final class UnsafeLazyImpl<T> implements Lazy<T>, Serializable {
    private Object _value = UNINITIALIZED_VALUE.INSTANCE;
    private Functions<? extends T> initializer;

    public UnsafeLazyImpl(Functions<? extends T> initializer) {
        Intrinsics.checkParameterIsNotNull(initializer, "initializer");
        this.initializer = initializer;
    }

    public T getValue() {
        if (this._value == UNINITIALIZED_VALUE.INSTANCE) {
            Functions<? extends T> functions = this.initializer;
            if (functions == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            this._value = functions.mo6822invoke();
            this.initializer = null;
        }
        return (T) this._value;
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
