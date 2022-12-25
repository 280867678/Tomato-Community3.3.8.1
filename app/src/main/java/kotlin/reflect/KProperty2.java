package kotlin.reflect;

import kotlin.jvm.functions.Function2;
import kotlin.reflect.KProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KProperty2<D, E, R> extends KProperty<R>, Function2<D, E, R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Getter<D, E, R> extends KProperty.Getter<R>, Function2<D, E, R> {
    }

    R get(D d, E e);

    Object getDelegate(D d, E e);

    /* renamed from: getGetter */
    Getter<D, E, R> mo6793getGetter();
}
