package net.vidageek.mirror.reflect.dsl;

import java.lang.reflect.Constructor;

/* loaded from: classes4.dex */
public interface ConstructorReflector<T> {
    Constructor<T> withAnyArgs();

    Constructor<T> withArgs(Class<?>... clsArr);

    Constructor<T> withoutArgs();
}
