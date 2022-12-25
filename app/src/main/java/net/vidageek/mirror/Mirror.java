package net.vidageek.mirror;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import net.vidageek.mirror.dsl.AccessorsController;
import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.dsl.FieldController;
import net.vidageek.mirror.dsl.MemberController;

@Deprecated
/* loaded from: classes4.dex */
public final class Mirror {
    private static final net.vidageek.mirror.dsl.Mirror mirror = new net.vidageek.mirror.dsl.Mirror();

    private Mirror() {
    }

    public static Class<?> reflectClass(String str) {
        return mirror.reflectClass(str);
    }

    /* renamed from: on */
    public static <T> ClassController<T> m83on(Class<T> cls) {
        return mirror.m78on((Class) cls);
    }

    /* renamed from: on */
    public static AccessorsController m82on(Object obj) {
        return mirror.m77on(obj);
    }

    /* renamed from: on */
    public static ClassController<?> m81on(String str) {
        return mirror.m76on(str);
    }

    /* renamed from: on */
    public static MemberController m80on(AnnotatedElement annotatedElement) {
        return mirror.m75on(annotatedElement);
    }

    /* renamed from: on */
    public static FieldController m79on(Field field) {
        return mirror.m74on(field);
    }
}
