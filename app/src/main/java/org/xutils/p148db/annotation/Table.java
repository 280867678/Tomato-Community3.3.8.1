package org.xutils.p148db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* renamed from: org.xutils.db.annotation.Table */
/* loaded from: classes4.dex */
public @interface Table {
    String name();

    String onCreated() default "";
}
