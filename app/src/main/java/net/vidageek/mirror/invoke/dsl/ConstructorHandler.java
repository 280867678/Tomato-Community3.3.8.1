package net.vidageek.mirror.invoke.dsl;

/* loaded from: classes4.dex */
public interface ConstructorHandler<T> {
    T bypasser();

    T withArgs(Object... objArr);

    T withoutArgs();
}
