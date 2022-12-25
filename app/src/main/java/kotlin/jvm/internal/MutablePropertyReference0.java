package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KProperty0;

/* loaded from: classes4.dex */
public abstract class MutablePropertyReference0 extends MutablePropertyReference implements KMutableProperty0 {
    public MutablePropertyReference0() {
    }

    public MutablePropertyReference0(Object obj) {
        super(obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        Reflection.mutableProperty0(this);
        return this;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public Object mo6822invoke() {
        return get();
    }

    @Override // kotlin.reflect.KProperty0
    /* renamed from: getGetter  reason: collision with other method in class */
    public KProperty0.Getter mo6791getGetter() {
        return ((KMutableProperty0) mo6790getReflected()).mo6791getGetter();
    }

    @Override // kotlin.reflect.KMutableProperty0
    /* renamed from: getSetter  reason: collision with other method in class */
    public KMutableProperty0.Setter mo6785getSetter() {
        return ((KMutableProperty0) mo6790getReflected()).mo6785getSetter();
    }

    @Override // kotlin.reflect.KProperty0
    public Object getDelegate() {
        return ((KMutableProperty0) mo6790getReflected()).getDelegate();
    }
}
