package com.amazonaws.services.p054s3.model;

import java.io.Serializable;
import java.util.EnumSet;

/* renamed from: com.amazonaws.services.s3.model.QueueConfiguration */
/* loaded from: classes2.dex */
public class QueueConfiguration extends NotificationConfiguration implements Serializable {
    private String queueARN;

    public QueueConfiguration() {
    }

    public QueueConfiguration(String str, EnumSet<S3Event> enumSet) {
        super(enumSet);
        this.queueARN = str;
    }

    public QueueConfiguration(String str, String... strArr) {
        super(strArr);
        this.queueARN = str;
    }

    public String getQueueARN() {
        return this.queueARN;
    }

    public void setQueueARN(String str) {
        this.queueARN = str;
    }

    public QueueConfiguration withQueueARN(String str) {
        setQueueARN(str);
        return this;
    }
}
