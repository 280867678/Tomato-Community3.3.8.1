package net.vidageek.mirror.dsl;

import net.vidageek.mirror.get.dsl.GetterHandler;
import net.vidageek.mirror.invoke.dsl.InvocationHandler;
import net.vidageek.mirror.reflect.dsl.AllReflectionHandler;
import net.vidageek.mirror.reflect.dsl.ReflectionHandler;
import net.vidageek.mirror.set.dsl.SetterHandler;

/* loaded from: classes4.dex */
public interface ClassController<T> {
    GetterHandler get();

    InvocationHandler<T> invoke();

    ReflectionHandler<T> reflect();

    AllReflectionHandler<T> reflectAll();

    SetterHandler set();
}
