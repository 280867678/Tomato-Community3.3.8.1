package net.vidageek.mirror.invoke.dsl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public interface InvocationHandler<T> {
    ConstructorHandler<T> constructor();

    <C> ConstructorHandler<C> constructor(Constructor<C> constructor);

    Object getterFor(String str);

    Object getterFor(Field field);

    MethodHandler method(String str);

    MethodHandler method(Method method);

    SetterMethodHandler setterFor(String str);

    SetterMethodHandler setterFor(Field field);
}
