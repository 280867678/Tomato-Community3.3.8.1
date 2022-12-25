package com.gen.p059mh.webapps.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* renamed from: com.gen.mh.webapps.database.DatabaseTable */
/* loaded from: classes2.dex */
public @interface DatabaseTable {
    String value() default "";

    String version() default "1.0";
}
