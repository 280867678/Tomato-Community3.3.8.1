package org.xutils.p148db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/* renamed from: org.xutils.db.annotation.Column */
/* loaded from: classes4.dex */
public @interface Column {
    boolean autoGen() default true;

    boolean isId() default false;

    String name();

    String property() default "";
}
