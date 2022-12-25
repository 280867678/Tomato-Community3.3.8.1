package com.amazonaws.services.p054s3.metrics;

import com.amazonaws.metrics.ServiceMetricType;
import com.amazonaws.metrics.SimpleMetricType;
import com.amazonaws.metrics.ThroughputMetricType;

/* renamed from: com.amazonaws.services.s3.metrics.S3ServiceMetric */
/* loaded from: classes2.dex */
public class S3ServiceMetric extends SimpleMetricType implements ServiceMetricType {
    private final String name;
    public static final S3ThroughputMetric S3_DOWLOAD_THROUGHPUT = new S3ThroughputMetric(metricName("DownloadThroughput")) { // from class: com.amazonaws.services.s3.metrics.S3ServiceMetric.1
    };
    public static final S3ServiceMetric S3_DOWNLOAD_BYTE_COUNT = new S3ServiceMetric(metricName("DownloadByteCount"));
    public static final S3ThroughputMetric S3_UPLOAD_THROUGHPUT = new S3ThroughputMetric(metricName("UploadThroughput")) { // from class: com.amazonaws.services.s3.metrics.S3ServiceMetric.2
    };
    public static final S3ServiceMetric S3_UPLOAD_BYTE_COUNT = new S3ServiceMetric(metricName("UploadByteCount"));
    private static final S3ServiceMetric[] VALUES = {S3_DOWLOAD_THROUGHPUT, S3_DOWNLOAD_BYTE_COUNT, S3_UPLOAD_THROUGHPUT, S3_UPLOAD_BYTE_COUNT};

    private static final String metricName(String str) {
        return "S3" + str;
    }

    private S3ServiceMetric(String str) {
        this.name = str;
    }

    @Override // com.amazonaws.metrics.SimpleMetricType, com.amazonaws.metrics.MetricType
    public String name() {
        return this.name;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.amazonaws.services.s3.metrics.S3ServiceMetric$S3ThroughputMetric */
    /* loaded from: classes2.dex */
    public static abstract class S3ThroughputMetric extends S3ServiceMetric implements ThroughputMetricType {
        private S3ThroughputMetric(String str) {
            super(str);
        }
    }

    public static S3ServiceMetric[] values() {
        return (S3ServiceMetric[]) VALUES.clone();
    }
}
