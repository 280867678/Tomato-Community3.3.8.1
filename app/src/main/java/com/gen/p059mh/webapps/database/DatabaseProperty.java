package com.gen.p059mh.webapps.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* renamed from: com.gen.mh.webapps.database.DatabaseProperty */
/* loaded from: classes2.dex */
public @interface DatabaseProperty {
    boolean index() default false;

    boolean nullable() default true;

    boolean primary() default false;

    String value() default "";
}
