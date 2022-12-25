package com.amazonaws;

import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.util.VersionInfoUtils;
import javax.net.ssl.TrustManager;

/* loaded from: classes2.dex */
public class ClientConfiguration {
    private String signerOverride;
    public static final String DEFAULT_USER_AGENT = VersionInfoUtils.getUserAgent();
    public static final RetryPolicy DEFAULT_RETRY_POLICY = PredefinedRetryPolicies.DEFAULT;
    private String userAgent = DEFAULT_USER_AGENT;
    private int maxErrorRetry = -1;
    private RetryPolicy retryPolicy = DEFAULT_RETRY_POLICY;
    private Protocol protocol = Protocol.HTTPS;
    private int socketTimeout = 15000;
    private int connectionTimeout = 15000;
    private TrustManager trustManager = null;
    private boolean curlLogging = false;
    private boolean enableGzip = false;

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }

    public int getMaxErrorRetry() {
        return this.maxErrorRetry;
    }

    public void setMaxErrorRetry(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("maxErrorRetry shoud be non-negative");
        }
        this.maxErrorRetry = i;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(int i) {
        this.socketTimeout = i;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int i) {
        this.connectionTimeout = i;
    }

    public String getSignerOverride() {
        return this.signerOverride;
    }

    public TrustManager getTrustManager() {
        return this.trustManager;
    }

    public boolean isCurlLogging() {
        return this.curlLogging;
    }

    public void setCurlLogging(boolean z) {
        this.curlLogging = z;
    }

    public boolean isEnableGzip() {
        return this.enableGzip;
    }
}
