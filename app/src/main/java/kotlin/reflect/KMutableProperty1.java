package kotlin.reflect;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KMutableProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KMutableProperty1<T, R> extends KProperty1<T, R>, KMutableProperty<R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Setter<T, R> extends KMutableProperty.Setter<R>, Function2<T, R, Unit> {
    }

    /* renamed from: getSetter */
    Setter<T, R> mo6787getSetter();
}
