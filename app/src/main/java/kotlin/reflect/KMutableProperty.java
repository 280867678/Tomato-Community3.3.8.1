package kotlin.reflect;

import kotlin.Unit;
import kotlin.reflect.KProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KMutableProperty<R> extends KProperty<R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Setter<R> extends KProperty.Accessor<R>, KFunction<Unit> {
    }
}
