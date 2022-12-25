package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty1;

/* loaded from: classes4.dex */
public abstract class PropertyReference1 extends PropertyReference implements KProperty1 {
    public PropertyReference1() {
    }

    public PropertyReference1(Object obj) {
        super(obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        Reflection.property1(this);
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
        return ((KProperty1) mo6790getReflected()).mo6792getGetter();
    }

    @Override // kotlin.reflect.KProperty1
    public Object getDelegate(Object obj) {
        return ((KProperty1) mo6790getReflected()).getDelegate(obj);
    }
}
