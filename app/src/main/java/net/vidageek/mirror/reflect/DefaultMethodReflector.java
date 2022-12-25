package net.vidageek.mirror.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.list.EqualMethodRemover;
import net.vidageek.mirror.list.SameNameMatcher;
import net.vidageek.mirror.list.dsl.MirrorList;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.MethodReflector;

/* loaded from: classes4.dex */
public final class DefaultMethodReflector implements MethodReflector {
    private final Class<?> clazz;
    private final String methodName;
    private final ReflectionProvider provider;

    public DefaultMethodReflector(ReflectionProvider reflectionProvider, String str, Class<?> cls) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("methodName cannot be null or empty");
        }
        if (cls == null) {
            throw new IllegalArgumentException("clazz cannnot be null");
        }
        this.provider = reflectionProvider;
        this.methodName = str.trim();
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.reflect.dsl.MethodReflector
    public Method withoutArgs() {
        return withArgs(new Class[0]);
    }

    @Override // net.vidageek.mirror.reflect.dsl.MethodReflector
    public Method withArgs(Class<?>... clsArr) {
        if (clsArr == null) {
            throw new IllegalArgumentException("classes cannot be null");
        }
        return this.provider.getClassReflectionProvider(this.clazz).reflectMethod(this.methodName, clsArr);
    }

    @Override // net.vidageek.mirror.reflect.dsl.MethodReflector
    public Method withAnyArgs() {
        MirrorList<Method> matching = new Mirror(this.provider).m78on((Class) this.clazz).reflectAll().methods().matching(new SameNameMatcher(this.methodName));
        if (matching.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(matching.matching(new EqualMethodRemover(matching.get(0))));
        arrayList.add(matching.get(0));
        if (arrayList.size() == 1) {
            return (Method) arrayList.get(0);
        }
        throw new MirrorException("more than one method named " + this.methodName + " was found on class " + this.clazz.getName() + " while attempting to find a uniquely named method. Methods are: " + arrayList);
    }
}
