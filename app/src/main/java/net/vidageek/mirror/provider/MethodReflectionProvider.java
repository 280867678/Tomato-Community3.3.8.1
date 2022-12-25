package net.vidageek.mirror.provider;

/* loaded from: classes4.dex */
public interface MethodReflectionProvider extends ReflectionElementReflectionProvider {
    Class<?>[] getParameters();

    Object invoke(Object[] objArr);
}
