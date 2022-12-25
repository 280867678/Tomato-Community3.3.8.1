package net.vidageek.mirror.dsl;

import net.vidageek.mirror.get.dsl.GetterHandler;
import net.vidageek.mirror.invoke.dsl.InvocationHandler;
import net.vidageek.mirror.set.dsl.SetterHandler;

/* loaded from: classes4.dex */
public interface AccessorsController {
    GetterHandler get();

    InvocationHandler<Object> invoke();

    SetterHandler set();
}
