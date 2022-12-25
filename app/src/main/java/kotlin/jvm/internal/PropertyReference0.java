package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty0;

/* loaded from: classes4.dex */
public abstract class PropertyReference0 extends PropertyReference implements KProperty0 {
    public PropertyReference0() {
    }

    public PropertyReference0(Object obj) {
        super(obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        Reflection.property0(this);
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
        return ((KProperty0) mo6790getReflected()).mo6791getGetter();
    }

    @Override // kotlin.reflect.KProperty0
    public Object getDelegate() {
        return ((KProperty0) mo6790getReflected()).getDelegate();
    }
}
