package kotlin.reflect;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.reflect.KMutableProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KMutableProperty2<D, E, R> extends KProperty2<D, E, R>, KMutableProperty<R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Setter<D, E, R> extends KMutableProperty.Setter<R>, Function3<D, E, R, Unit> {
    }

    /* renamed from: getSetter */
    Setter<D, E, R> mo6789getSetter();
}
