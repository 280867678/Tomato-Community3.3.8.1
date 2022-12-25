package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty2;

/* loaded from: classes4.dex */
public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KMutableProperty2 {
    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        Reflection.mutableProperty2(this);
        return this;
    }

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object obj, Object obj2) {
        return get(obj, obj2);
    }

    @Override // kotlin.reflect.KProperty2
    /* renamed from: getGetter  reason: collision with other method in class */
    public KProperty2.Getter mo6793getGetter() {
        return ((KMutableProperty2) mo6790getReflected()).mo6793getGetter();
    }

    @Override // kotlin.reflect.KMutableProperty2
    /* renamed from: getSetter  reason: collision with other method in class */
    public KMutableProperty2.Setter mo6789getSetter() {
        return ((KMutableProperty2) mo6790getReflected()).mo6789getSetter();
    }

    @Override // kotlin.reflect.KProperty2
    public Object getDelegate(Object obj, Object obj2) {
        return ((KMutableProperty2) mo6790getReflected()).getDelegate(obj, obj2);
    }
}
