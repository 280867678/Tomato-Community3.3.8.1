package net.vidageek.mirror;

import net.vidageek.mirror.dsl.AccessorsController;
import net.vidageek.mirror.get.DefaultGetterHandler;
import net.vidageek.mirror.get.dsl.GetterHandler;
import net.vidageek.mirror.invoke.DefaultInvocationHandler;
import net.vidageek.mirror.invoke.dsl.InvocationHandler;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.set.DefaultSetterHandler;
import net.vidageek.mirror.set.dsl.SetterHandler;

/* loaded from: classes4.dex */
public final class DefaultAccessorsController implements AccessorsController {
    private final ReflectionProvider provider;
    private final Object target;

    public DefaultAccessorsController(ReflectionProvider reflectionProvider, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("target cannot be null");
        }
        this.provider = reflectionProvider;
        this.target = obj;
    }

    @Override // net.vidageek.mirror.dsl.AccessorsController
    public InvocationHandler<Object> invoke() {
        return new DefaultInvocationHandler(this.provider, this.target);
    }

    @Override // net.vidageek.mirror.dsl.AccessorsController
    public SetterHandler set() {
        return new DefaultSetterHandler(this.provider, this.target);
    }

    @Override // net.vidageek.mirror.dsl.AccessorsController
    public GetterHandler get() {
        return new DefaultGetterHandler(this.provider, this.target);
    }
}
