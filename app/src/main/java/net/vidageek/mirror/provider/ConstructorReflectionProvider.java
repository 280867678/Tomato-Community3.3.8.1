package net.vidageek.mirror.provider;

/* loaded from: classes4.dex */
public interface ConstructorReflectionProvider<T> extends ReflectionElementReflectionProvider {
    Class<?>[] getParameters();

    T instantiate(Object... objArr);
}
