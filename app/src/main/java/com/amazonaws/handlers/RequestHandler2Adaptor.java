package com.amazonaws.handlers;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.TimingInfo;

/* loaded from: classes2.dex */
final class RequestHandler2Adaptor extends RequestHandler2 {
    private final RequestHandler old;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestHandler2Adaptor(RequestHandler requestHandler) {
        if (requestHandler == null) {
            throw new IllegalArgumentException();
        }
        this.old = requestHandler;
    }

    @Override // com.amazonaws.handlers.RequestHandler2
    public void beforeRequest(Request<?> request) {
        this.old.beforeRequest(request);
    }

    @Override // com.amazonaws.handlers.RequestHandler2
    public void afterResponse(Request<?> request, Response<?> response) {
        TimingInfo timingInfo = null;
        AWSRequestMetrics aWSRequestMetrics = request == null ? null : request.getAWSRequestMetrics();
        Object awsResponse = response == null ? null : response.getAwsResponse();
        if (aWSRequestMetrics != null) {
            timingInfo = aWSRequestMetrics.getTimingInfo();
        }
        this.old.afterResponse(request, awsResponse, timingInfo);
    }

    @Override // com.amazonaws.handlers.RequestHandler2
    public void afterError(Request<?> request, Response<?> response, Exception exc) {
        this.old.afterError(request, exc);
    }

    public int hashCode() {
        return this.old.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RequestHandler2Adaptor)) {
            return false;
        }
        return this.old.equals(((RequestHandler2Adaptor) obj).old);
    }
}
