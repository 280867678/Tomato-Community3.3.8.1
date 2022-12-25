package com.amazonaws.services.p054s3.model;

import java.io.Serializable;
import java.util.EnumSet;

/* renamed from: com.amazonaws.services.s3.model.LambdaConfiguration */
/* loaded from: classes2.dex */
public class LambdaConfiguration extends NotificationConfiguration implements Serializable {
    private final String functionARN;

    public LambdaConfiguration(String str, EnumSet<S3Event> enumSet) {
        super(enumSet);
        this.functionARN = str;
    }

    public LambdaConfiguration(String str, String... strArr) {
        super(strArr);
        this.functionARN = str;
    }

    public String getFunctionARN() {
        return this.functionARN;
    }
}
