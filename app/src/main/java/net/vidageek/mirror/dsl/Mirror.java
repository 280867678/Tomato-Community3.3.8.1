package net.vidageek.mirror.dsl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.vidageek.mirror.DefaultAccessorsController;
import net.vidageek.mirror.DefaultClassController;
import net.vidageek.mirror.DefaultFieldController;
import net.vidageek.mirror.DefaultMemberController;
import net.vidageek.mirror.DefaultProxyHandler;
import net.vidageek.mirror.config.MirrorProviderBuilder;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.proxy.dsl.ProxyHandler;

/* loaded from: classes4.dex */
public final class Mirror {
    private static final String MIRROR_CFG = "/mirror.properties";
    private static final ReflectionProvider cachedProvider = new MirrorProviderBuilder(Mirror.class.getResourceAsStream(MIRROR_CFG)).createProvider();
    private final ReflectionProvider provider;

    public Mirror(ReflectionProvider reflectionProvider) {
        this.provider = reflectionProvider;
    }

    public Mirror() {
        this(cachedProvider);
    }

    public Class<?> reflectClass(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("className cannot be null or empty");
        }
        return this.provider.getClassReflectionProvider(str).reflectClass();
    }

    /* renamed from: on */
    public <T> ClassController<T> m78on(Class<T> cls) {
        return new DefaultClassController(this.provider, cls);
    }

    /* renamed from: on */
    public AccessorsController m77on(Object obj) {
        return new DefaultAccessorsController(this.provider, obj);
    }

    /* renamed from: on */
    public ClassController<?> m76on(String str) {
        return m78on((Class) reflectClass(str));
    }

    /* renamed from: on */
    public MemberController m75on(AnnotatedElement annotatedElement) {
        return new DefaultMemberController(this.provider, annotatedElement);
    }

    /* renamed from: on */
    public FieldController m74on(Field field) {
        return new DefaultFieldController(this.provider, field);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> ProxyHandler<T> proxify(Class<T> cls) {
        return (ProxyHandler<T>) proxify(cls);
    }

    public ProxyHandler<Object> proxify(String str) {
        return proxify(str);
    }

    public ProxyHandler<Object> proxify(String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            arrayList.add(reflectClass(str));
        }
        return proxify((Class[]) arrayList.toArray(new Class[strArr.length]));
    }

    public ProxyHandler<Object> proxify(Class<?>... clsArr) {
        return new DefaultProxyHandler(this.provider, clsArr);
    }
}
