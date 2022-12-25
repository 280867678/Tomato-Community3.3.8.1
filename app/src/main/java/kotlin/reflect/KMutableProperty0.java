package kotlin.reflect;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KMutableProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KMutableProperty0<R> extends KProperty0<R>, KMutableProperty<R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Setter<R> extends KMutableProperty.Setter<R>, Function1<R, Unit> {
    }

    /* renamed from: getSetter */
    Setter<R> mo6785getSetter();
}
