package kotlin.reflect;

import kotlin.jvm.functions.Functions;
import kotlin.reflect.KProperty;

/* compiled from: KProperty.kt */
/* loaded from: classes4.dex */
public interface KProperty0<R> extends KProperty<R>, Functions<R> {

    /* compiled from: KProperty.kt */
    /* loaded from: classes4.dex */
    public interface Getter<R> extends KProperty.Getter<R>, Functions<R> {
    }

    R get();

    Object getDelegate();

    /* renamed from: getGetter */
    Getter<R> mo6791getGetter();
}
