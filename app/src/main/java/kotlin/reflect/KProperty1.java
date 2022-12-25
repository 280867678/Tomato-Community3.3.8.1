package kotlin.reflect;

import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KProperty1<T, R> extends KProperty<R>, Function1<T, R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Getter<T, R> extends KProperty.Getter<R>, Function1<T, R> {
    }

    R get(T t);

    Object getDelegate(T t);

    /* renamed from: getGetter */
    Getter<T, R> mo6792getGetter();
}
