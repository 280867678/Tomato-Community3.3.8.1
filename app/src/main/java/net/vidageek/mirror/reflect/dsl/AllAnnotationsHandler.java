package net.vidageek.mirror.reflect.dsl;

import java.lang.annotation.Annotation;
import java.util.List;

/* loaded from: classes4.dex */
public interface AllAnnotationsHandler {
    List<Annotation> atClass();

    List<Annotation> atField(String str);

    AllMethodAnnotationsHandler atMethod(String str);
}
