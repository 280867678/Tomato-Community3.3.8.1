package net.vidageek.mirror.reflect.dsl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;

/* loaded from: classes4.dex */
public interface AllReflectionHandler<T> {
    AllAnnotationsHandler annotations();

    @Deprecated
    List<Annotation> annotationsMatching(Matcher<Annotation> matcher);

    MirrorList<Constructor<T>> constructors();

    @Deprecated
    List<Constructor<T>> constructorsMatching(Matcher<Constructor<T>> matcher);

    MirrorList<Field> fields();

    @Deprecated
    List<Field> fieldsMatching(Matcher<Field> matcher);

    MirrorList<Method> getters();

    MirrorList<Method> methods();

    @Deprecated
    List<Method> methodsMatching(Matcher<Method> matcher);

    MirrorList<Method> setters();
}
