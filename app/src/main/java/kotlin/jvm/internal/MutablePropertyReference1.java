package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty1;

/* loaded from: classes4.dex */
public abstract class MutablePropertyReference1 extends MutablePropertyReference implements KMutableProperty1 {
    public MutablePropertyReference1() {
    }

    public MutablePropertyReference1(Object obj) {
        super(obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        Reflection.mutableProperty1(this);
        return this;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public Object mo6794invoke(Object obj) {
        return get(obj);
    }

    @Override // kotlin.reflect.KProperty1
    /* renamed from: getGetter  reason: collision with other method in class */
    public KProperty1.Getter mo6792getGetter() {
        return ((KMutableProperty1) mo6790getReflected()).mo6792getGetter();
    }

    @Override // kotlin.reflect.KMutableProperty1
    /* renamed from: getSetter  reason: collision with other method in class */
    public KMutableProperty1.Setter mo6787getSetter() {
        return ((KMutableProperty1) mo6790getReflected()).mo6787getSetter();
    }

    @Override // kotlin.reflect.KProperty1
    public Object getDelegate(Object obj) {
        return ((KMutableProperty1) mo6790getReflected()).getDelegate(obj);
    }
}
