package com.amazonaws.metrics;

/* loaded from: classes2.dex */
public class SimpleThroughputMetricType extends SimpleServiceMetricType implements ThroughputMetricType {
    public SimpleThroughputMetricType(String str, String str2, String str3) {
        super(str, str2);
        new SimpleServiceMetricType(str3, str2);
    }
}
