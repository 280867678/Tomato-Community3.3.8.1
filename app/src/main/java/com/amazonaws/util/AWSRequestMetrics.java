package com.amazonaws.util;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricType;

@Deprecated
/* loaded from: classes2.dex */
public class AWSRequestMetrics {
    protected final TimingInfo timingInfo;

    /* loaded from: classes2.dex */
    public enum Field implements RequestMetricType {
        AWSErrorCode,
        AWSRequestID,
        BytesProcessed,
        ClientExecuteTime,
        CredentialsRequestTime,
        Exception,
        HttpRequestTime,
        RedirectLocation,
        RequestMarshallTime,
        RequestSigningTime,
        ResponseProcessingTime,
        RequestCount,
        RetryCount,
        HttpClientRetryCount,
        HttpClientSendRequestTime,
        HttpClientReceiveResponseTime,
        HttpClientPoolAvailableCount,
        HttpClientPoolLeasedCount,
        HttpClientPoolPendingCount,
        RetryPauseTime,
        ServiceEndpoint,
        ServiceName,
        StatusCode
    }

    public void addProperty(MetricType metricType, Object obj) {
    }

    public void endEvent(MetricType metricType) {
    }

    public void incrementCounter(MetricType metricType) {
    }

    public void log() {
    }

    public void setCounter(MetricType metricType, long j) {
    }

    public void startEvent(MetricType metricType) {
    }

    public AWSRequestMetrics() {
        this.timingInfo = TimingInfo.startTiming();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AWSRequestMetrics(TimingInfo timingInfo) {
        this.timingInfo = timingInfo;
    }

    public final TimingInfo getTimingInfo() {
        return this.timingInfo;
    }
}
