package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

/* loaded from: classes4.dex */
public class MutablePropertyReference0Impl extends MutablePropertyReference0 {
    private final String name;
    private final KDeclarationContainer owner;
    private final String signature;

    public MutablePropertyReference0Impl(KDeclarationContainer kDeclarationContainer, String str, String str2) {
        this.owner = kDeclarationContainer;
        this.name = str;
        this.signature = str2;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public KDeclarationContainer getOwner() {
        return this.owner;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public String getName() {
        return this.name;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public String getSignature() {
        return this.signature;
    }

    @Override // kotlin.reflect.KProperty0
    public Object get() {
        return mo6791getGetter().call(new Object[0]);
    }

    public void set(Object obj) {
        mo6785getSetter().call(obj);
    }
}