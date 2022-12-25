package com.amazonaws.metrics;

/* loaded from: classes2.dex */
public class SimpleServiceMetricType extends SimpleMetricType implements ServiceMetricType {
    private final String name;

    public SimpleServiceMetricType(String str, String str2) {
        this.name = str;
    }

    @Override // com.amazonaws.metrics.SimpleMetricType, com.amazonaws.metrics.MetricType
    public String name() {
        return this.name;
    }
}
