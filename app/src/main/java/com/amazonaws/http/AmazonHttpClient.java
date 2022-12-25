package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.RequestClientOptions;
import com.amazonaws.Response;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.Signer;
import com.amazonaws.handlers.CredentialsRequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.internal.CRC32MismatchException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.TimingInfo;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/* loaded from: classes2.dex */
public class AmazonHttpClient {
    private static final Log REQUEST_LOG = LogFactory.getLog("com.amazonaws.request");
    static final Log log = LogFactory.getLog(AmazonHttpClient.class);
    final ClientConfiguration config;
    final HttpClient httpClient;
    private final HttpRequestFactory requestFactory = new HttpRequestFactory();
    private final RequestMetricCollector requestMetricCollector = null;

    public AmazonHttpClient(ClientConfiguration clientConfiguration, HttpClient httpClient) {
        this.config = clientConfiguration;
        this.httpClient = httpClient;
    }

    public <T> Response<T> execute(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> httpResponseHandler, HttpResponseHandler<AmazonServiceException> httpResponseHandler2, ExecutionContext executionContext) {
        if (executionContext == null) {
            throw new AmazonClientException("Internal SDK Error: No execution context parameter specified.");
        }
        List<RequestHandler2> requestHandler2s = requestHandler2s(request, executionContext);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        Response<T> response = null;
        try {
            response = executeHelper(request, httpResponseHandler, httpResponseHandler2, executionContext);
            afterResponse(request, requestHandler2s, response, awsRequestMetrics.getTimingInfo().endTiming());
            return response;
        } catch (AmazonClientException e) {
            afterError(request, response, requestHandler2s, e);
            throw e;
        }
    }

    void afterError(Request<?> request, Response<?> response, List<RequestHandler2> list, AmazonClientException amazonClientException) {
        for (RequestHandler2 requestHandler2 : list) {
            requestHandler2.afterError(request, response, amazonClientException);
        }
    }

    <T> void afterResponse(Request<?> request, List<RequestHandler2> list, Response<T> response, TimingInfo timingInfo) {
        for (RequestHandler2 requestHandler2 : list) {
            requestHandler2.afterResponse(request, response);
        }
    }

    List<RequestHandler2> requestHandler2s(Request<?> request, ExecutionContext executionContext) {
        List<RequestHandler2> requestHandler2s = executionContext.getRequestHandler2s();
        if (requestHandler2s == null) {
            return Collections.emptyList();
        }
        for (RequestHandler2 requestHandler2 : requestHandler2s) {
            if (requestHandler2 instanceof CredentialsRequestHandler) {
                ((CredentialsRequestHandler) requestHandler2).setCredentials(executionContext.getCredentials());
            }
            requestHandler2.beforeRequest(request);
        }
        return requestHandler2s;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0354 A[Catch: all -> 0x03df, TRY_ENTER, TryCatch #39 {all -> 0x03df, blocks: (B:43:0x0349, B:46:0x0354, B:47:0x036c, B:49:0x03b2, B:63:0x03de, B:96:0x0289, B:98:0x028f, B:100:0x0295, B:101:0x029c, B:114:0x02c3), top: B:42:0x0349 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x03b2 A[Catch: all -> 0x03df, TRY_LEAVE, TryCatch #39 {all -> 0x03df, blocks: (B:43:0x0349, B:46:0x0354, B:47:0x036c, B:49:0x03b2, B:63:0x03de, B:96:0x0289, B:98:0x028f, B:100:0x0295, B:101:0x029c, B:114:0x02c3), top: B:42:0x0349 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x03de A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x03e4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x03ec A[Catch: IOException -> 0x03f4, TRY_LEAVE, TryCatch #7 {IOException -> 0x03f4, blocks: (B:69:0x03e6, B:71:0x03ec), top: B:68:0x03e6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    <T> Response<T> executeHelper(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> httpResponseHandler, HttpResponseHandler<AmazonServiceException> httpResponseHandler2, ExecutionContext executionContext) {
        Throwable th;
        LinkedHashMap linkedHashMap;
        HashMap hashMap;
        long j;
        Signer signer;
        int i;
        HttpResponse httpResponse;
        AmazonClientException amazonClientException;
        Signer signer2;
        HttpRequest createHttpRequest;
        HttpRequest httpRequest;
        String str;
        Log log2;
        StringBuilder sb;
        ExecutionContext executionContext2;
        ExecutionContext executionContext3 = executionContext;
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.ServiceName, request.getServiceName());
        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.ServiceEndpoint, request.getEndpoint());
        setUserAgent(request);
        request.addHeader("aws-sdk-invocation-id", UUID.randomUUID().toString());
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(request.getParameters());
        HashMap hashMap2 = new HashMap(request.getHeaders());
        InputStream content = request.getContent();
        if (content != null && content.markSupported()) {
            content.mark(-1);
        }
        AWSCredentials credentials = executionContext.getCredentials();
        int i2 = 0;
        long j2 = 0;
        AmazonClientException amazonClientException2 = null;
        Signer signer3 = null;
        HttpResponse httpResponse2 = null;
        HttpRequest httpRequest2 = null;
        URI uri = null;
        boolean z = false;
        while (true) {
            HttpRequest httpRequest3 = httpRequest2;
            int i3 = i2 + 1;
            long j3 = j2;
            awsRequestMetrics.setCounter(AWSRequestMetrics.Field.RequestCount, i3);
            if (i3 > 1) {
                request.setParameters(linkedHashMap2);
                request.setHeaders(hashMap2);
                request.setContent(content);
            }
            if (uri != null && request.getEndpoint() == null && request.getResourcePath() == null) {
                request.setEndpoint(URI.create(uri.getScheme() + "://" + uri.getAuthority()));
                request.setResourcePath(uri.getPath());
            }
            try {
                if (i3 > 1) {
                    try {
                        try {
                            try {
                                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RetryPauseTime);
                                try {
                                    j3 = pauseBeforeNextRetry(request.getOriginalRequest(), amazonClientException2, i3, this.config.getRetryPolicy());
                                    awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RetryPauseTime);
                                    InputStream content2 = request.getContent();
                                    if (content2 != null && content2.markSupported()) {
                                        content2.reset();
                                    }
                                } catch (Throwable th2) {
                                    awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RetryPauseTime);
                                    throw th2;
                                    break;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                executionContext3 = "Cannot close the response content.";
                                if (!z && httpResponse2 != null) {
                                    try {
                                        if (httpResponse2.getRawContent() != null) {
                                            httpResponse2.getRawContent().close();
                                        }
                                    } catch (IOException e) {
                                        log.warn(executionContext3, e);
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException e2) {
                            e = e2;
                            executionContext3 = "Cannot close the response content.";
                            linkedHashMap = linkedHashMap2;
                            hashMap = hashMap2;
                            j = j3;
                            signer = signer3;
                            httpResponse = httpResponse2;
                            i = i3;
                            try {
                                if (log.isDebugEnabled()) {
                                }
                                awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                                amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                httpResponse2 = httpResponse;
                                if (!z) {
                                    if (httpResponse2.getRawContent() != null) {
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Error e3) {
                        e = e3;
                        handleUnexpectedFailure(e, awsRequestMetrics);
                        throw e;
                    } catch (RuntimeException e4) {
                        e = e4;
                        handleUnexpectedFailure(e, awsRequestMetrics);
                        throw e;
                    }
                }
                linkedHashMap = linkedHashMap2;
                hashMap = hashMap2;
                long j4 = j3;
                try {
                    try {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(i3 - 1);
                        sb2.append("/");
                        sb2.append(j4);
                        request.addHeader("aws-sdk-retry", sb2.toString());
                        if (signer3 == null) {
                            try {
                                signer3 = executionContext3.getSignerByURI(request.getEndpoint());
                            } catch (IOException e5) {
                                e = e5;
                                signer = signer3;
                                executionContext3 = "Cannot close the response content.";
                                j = j4;
                                httpResponse = httpResponse2;
                                i = i3;
                                if (log.isDebugEnabled()) {
                                }
                                awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                                amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                }
                            }
                        }
                        signer2 = signer3;
                        if (signer2 != null && credentials != null) {
                            try {
                                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestSigningTime);
                                try {
                                    signer2.sign(request, credentials);
                                    awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestSigningTime);
                                } catch (Throwable th5) {
                                    awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestSigningTime);
                                    throw th5;
                                    break;
                                }
                            } catch (IOException e6) {
                                e = e6;
                                signer = signer2;
                                executionContext3 = "Cannot close the response content.";
                                j = j4;
                                httpResponse = httpResponse2;
                                i = i3;
                                if (log.isDebugEnabled()) {
                                }
                                awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                                amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                }
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        executionContext3 = "Cannot close the response content.";
                    }
                } catch (IOException e7) {
                    e = e7;
                    executionContext3 = "Cannot close the response content.";
                    j = j4;
                    i = i3;
                    signer = signer3;
                }
                try {
                    if (REQUEST_LOG.isDebugEnabled()) {
                        REQUEST_LOG.debug("Sending Request: " + request.toString());
                    }
                    createHttpRequest = this.requestFactory.createHttpRequest(request, this.config, executionContext3);
                    try {
                        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.HttpRequestTime);
                        try {
                            httpResponse2 = this.httpClient.execute(createHttpRequest);
                            try {
                                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.HttpRequestTime);
                            } catch (IOException e8) {
                                e = e8;
                                httpRequest = createHttpRequest;
                                signer = signer2;
                                executionContext3 = "Cannot close the response content.";
                                j = j4;
                            } catch (Error e9) {
                                e = e9;
                            } catch (RuntimeException e10) {
                                e = e10;
                            } catch (Throwable th7) {
                                th = th7;
                                executionContext3 = "Cannot close the response content.";
                            }
                        } catch (Throwable th8) {
                            httpRequest = createHttpRequest;
                            signer = signer2;
                            executionContext3 = "Cannot close the response content.";
                            j = j4;
                            i = i3;
                            try {
                                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.HttpRequestTime);
                                throw th8;
                                break;
                            } catch (IOException e11) {
                                e = e11;
                                httpResponse = httpResponse2;
                                httpRequest3 = httpRequest;
                                if (log.isDebugEnabled()) {
                                }
                                awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                                amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                }
                            } catch (Error e12) {
                                e = e12;
                                handleUnexpectedFailure(e, awsRequestMetrics);
                                throw e;
                            } catch (RuntimeException e13) {
                                e = e13;
                                handleUnexpectedFailure(e, awsRequestMetrics);
                                throw e;
                            }
                        }
                    } catch (IOException e14) {
                        e = e14;
                        httpRequest = createHttpRequest;
                        signer = signer2;
                        executionContext3 = "Cannot close the response content.";
                        j = j4;
                        i = i3;
                    }
                } catch (IOException e15) {
                    e = e15;
                    signer = signer2;
                    executionContext3 = "Cannot close the response content.";
                    j = j4;
                    i = i3;
                    httpResponse = httpResponse2;
                    if (log.isDebugEnabled()) {
                    }
                    awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                    amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                    if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                    }
                }
                if (isRequestSuccessful(httpResponse2)) {
                    try {
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.StatusCode, Integer.valueOf(httpResponse2.getStatusCode()));
                        z = httpResponseHandler.needsConnectionLeftOpen();
                        Response<T> response = new Response<>(handleResponse(request, httpResponseHandler, httpResponse2, executionContext3), httpResponse2);
                        if (!z && httpResponse2 != null) {
                            try {
                                if (httpResponse2.getRawContent() != null) {
                                    httpResponse2.getRawContent().close();
                                }
                            } catch (IOException e16) {
                                log.warn("Cannot close the response content.", e16);
                            }
                        }
                        return response;
                    } catch (IOException e17) {
                        e = e17;
                        httpRequest3 = createHttpRequest;
                        signer = signer2;
                        executionContext3 = "Cannot close the response content.";
                        j = j4;
                        httpResponse = httpResponse2;
                        i = i3;
                        if (log.isDebugEnabled()) {
                        }
                        awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                        amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                        if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                        }
                    }
                } else {
                    if (isTemporaryRedirect(httpResponse2)) {
                        try {
                            str = httpResponse2.getHeaders().get("Location");
                            log2 = log;
                            signer = signer2;
                        } catch (IOException e18) {
                            e = e18;
                            signer = signer2;
                        }
                        try {
                            sb = new StringBuilder();
                            executionContext2 = "Cannot close the response content.";
                        } catch (IOException e19) {
                            e = e19;
                            httpRequest3 = createHttpRequest;
                            executionContext3 = "Cannot close the response content.";
                            j = j4;
                            httpResponse = httpResponse2;
                            i = i3;
                            if (log.isDebugEnabled()) {
                                log.debug("Unable to execute HTTP request: " + e.getMessage(), e);
                            }
                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                            if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                throw amazonClientException;
                            }
                            resetRequestAfterError(request, e);
                            if (!z && httpResponse != null) {
                                try {
                                    if (httpResponse.getRawContent() != null) {
                                        httpResponse.getRawContent().close();
                                    }
                                } catch (IOException e20) {
                                    log.warn(executionContext3, e20);
                                }
                            }
                            httpResponse2 = httpResponse;
                            httpRequest2 = httpRequest3;
                            signer3 = signer;
                            amazonClientException2 = amazonClientException;
                            j2 = j;
                            executionContext3 = executionContext;
                            i2 = i;
                            linkedHashMap2 = linkedHashMap;
                            hashMap2 = hashMap;
                        }
                        try {
                            sb.append("Redirecting to: ");
                            sb.append(str);
                            log2.debug(sb.toString());
                            uri = URI.create(str);
                            request.setEndpoint(null);
                            request.setResourcePath(null);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.StatusCode, Integer.valueOf(httpResponse2.getStatusCode()));
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.RedirectLocation, str);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                            httpRequest = createHttpRequest;
                            j = j4;
                            executionContext3 = executionContext2;
                            amazonClientException2 = null;
                            httpResponse = httpResponse2;
                            i = i3;
                        } catch (IOException e21) {
                            e = e21;
                            httpRequest3 = createHttpRequest;
                            j = j4;
                            executionContext3 = executionContext2;
                            httpResponse = httpResponse2;
                            i = i3;
                            if (log.isDebugEnabled()) {
                            }
                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                            if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                            }
                        } catch (Error e22) {
                            e = e22;
                            handleUnexpectedFailure(e, awsRequestMetrics);
                            throw e;
                        } catch (RuntimeException e23) {
                            e = e23;
                            handleUnexpectedFailure(e, awsRequestMetrics);
                            throw e;
                        } catch (Throwable th9) {
                            th = th9;
                            executionContext3 = executionContext2;
                            if (!z) {
                            }
                            throw th;
                        }
                    } else {
                        signer = signer2;
                        executionContext2 = "Cannot close the response content.";
                        try {
                            z = httpResponseHandler2.needsConnectionLeftOpen();
                            AmazonServiceException handleErrorResponse = handleErrorResponse(request, httpResponseHandler2, httpResponse2);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, handleErrorResponse.getRequestId());
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSErrorCode, handleErrorResponse.getErrorCode());
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.StatusCode, Integer.valueOf(handleErrorResponse.getStatusCode()));
                            httpRequest = createHttpRequest;
                            executionContext3 = executionContext2;
                            j = j4;
                            httpResponse = httpResponse2;
                            i = i3;
                            try {
                                if (!shouldRetry(request.getOriginalRequest(), createHttpRequest.getContent(), handleErrorResponse, i3, this.config.getRetryPolicy())) {
                                    throw handleErrorResponse;
                                    break;
                                }
                                if (RetryUtils.isClockSkewError(handleErrorResponse)) {
                                    SDKGlobalConfiguration.setGlobalTimeOffset(parseClockSkewOffset(httpResponse, handleErrorResponse));
                                }
                                resetRequestAfterError(request, handleErrorResponse);
                                amazonClientException2 = handleErrorResponse;
                            } catch (IOException e24) {
                                e = e24;
                                httpRequest3 = httpRequest;
                                if (log.isDebugEnabled()) {
                                }
                                awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                                amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                }
                            } catch (Error e25) {
                                e = e25;
                                handleUnexpectedFailure(e, awsRequestMetrics);
                                throw e;
                            } catch (RuntimeException e26) {
                                e = e26;
                                handleUnexpectedFailure(e, awsRequestMetrics);
                                throw e;
                            }
                        } catch (IOException e27) {
                            e = e27;
                            httpRequest = createHttpRequest;
                            j = j4;
                            executionContext3 = executionContext2;
                            httpResponse = httpResponse2;
                            i = i3;
                            httpRequest3 = httpRequest;
                            if (log.isDebugEnabled()) {
                            }
                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, null);
                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                            if (shouldRetry(request.getOriginalRequest(), httpRequest3.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                            }
                        } catch (Error e28) {
                            e = e28;
                            handleUnexpectedFailure(e, awsRequestMetrics);
                            throw e;
                        } catch (RuntimeException e29) {
                            e = e29;
                            handleUnexpectedFailure(e, awsRequestMetrics);
                            throw e;
                        } catch (Throwable th10) {
                            th = th10;
                            executionContext3 = executionContext2;
                            th = th;
                            if (!z) {
                            }
                            throw th;
                        }
                    }
                    if (!z && httpResponse != null) {
                        try {
                            if (httpResponse.getRawContent() != null) {
                                httpResponse.getRawContent().close();
                            }
                        } catch (IOException e30) {
                            log.warn(executionContext3, e30);
                        }
                    }
                    httpResponse2 = httpResponse;
                    signer3 = signer;
                    httpRequest2 = httpRequest;
                    j2 = j;
                    executionContext3 = executionContext;
                    i2 = i;
                    linkedHashMap2 = linkedHashMap;
                    hashMap2 = hashMap;
                }
            } catch (Throwable th11) {
                th = th11;
            }
        }
    }

    private <T extends Throwable> T handleUnexpectedFailure(T t, AWSRequestMetrics aWSRequestMetrics) {
        aWSRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
        aWSRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, t);
        return t;
    }

    void resetRequestAfterError(Request<?> request, Exception exc) {
        if (request.getContent() == null) {
            return;
        }
        if (!request.getContent().markSupported()) {
            throw new AmazonClientException("Encountered an exception and stream is not resettable", exc);
        }
        try {
            request.getContent().reset();
        } catch (IOException unused) {
            throw new AmazonClientException("Encountered an exception and couldn't reset the stream to retry", exc);
        }
    }

    void setUserAgent(Request<?> request) {
        RequestClientOptions requestClientOptions;
        String clientMarker;
        String str = ClientConfiguration.DEFAULT_USER_AGENT;
        AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
        if (originalRequest != null && (requestClientOptions = originalRequest.getRequestClientOptions()) != null && (clientMarker = requestClientOptions.getClientMarker(RequestClientOptions.Marker.USER_AGENT)) != null) {
            str = createUserAgentString(str, clientMarker);
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(this.config.getUserAgent())) {
            str = createUserAgentString(str, this.config.getUserAgent());
        }
        request.addHeader("User-Agent", str);
    }

    static String createUserAgentString(String str, String str2) {
        if (str.contains(str2)) {
            return str;
        }
        return str.trim() + ConstantUtils.PLACEHOLDER_STR_ONE + str2.trim();
    }

    public void shutdown() {
        this.httpClient.shutdown();
    }

    private boolean shouldRetry(AmazonWebServiceRequest amazonWebServiceRequest, InputStream inputStream, AmazonClientException amazonClientException, int i, RetryPolicy retryPolicy) {
        int i2 = i - 1;
        int maxErrorRetry = this.config.getMaxErrorRetry();
        if (maxErrorRetry < 0 || !retryPolicy.isMaxErrorRetryInClientConfigHonored()) {
            maxErrorRetry = retryPolicy.getMaxErrorRetry();
        }
        if (i2 >= maxErrorRetry) {
            return false;
        }
        if (inputStream != null && !inputStream.markSupported()) {
            if (log.isDebugEnabled()) {
                log.debug("Content not repeatable");
            }
            return false;
        }
        return retryPolicy.getRetryCondition().shouldRetry(amazonWebServiceRequest, amazonClientException, i2);
    }

    private static boolean isTemporaryRedirect(HttpResponse httpResponse) {
        int statusCode = httpResponse.getStatusCode();
        String str = httpResponse.getHeaders().get("Location");
        return statusCode == 307 && str != null && !str.isEmpty();
    }

    private boolean isRequestSuccessful(HttpResponse httpResponse) {
        int statusCode = httpResponse.getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    <T> T handleResponse(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> httpResponseHandler, HttpResponse httpResponse, ExecutionContext executionContext) throws IOException {
        try {
            AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
            awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ResponseProcessingTime);
            try {
                AmazonWebServiceResponse<T> mo5855handle = httpResponseHandler.mo5855handle(httpResponse);
                if (mo5855handle == null) {
                    throw new RuntimeException("Unable to unmarshall response metadata. Response Code: " + httpResponse.getStatusCode() + ", Response Text: " + httpResponse.getStatusText());
                }
                if (REQUEST_LOG.isDebugEnabled()) {
                    Log log2 = REQUEST_LOG;
                    log2.debug("Received successful response: " + httpResponse.getStatusCode() + ", AWS Request ID: " + mo5855handle.getRequestId());
                }
                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, mo5855handle.getRequestId());
                return mo5855handle.getResult();
            } finally {
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ResponseProcessingTime);
            }
        } catch (CRC32MismatchException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new AmazonClientException("Unable to unmarshall response (" + e3.getMessage() + "). Response Code: " + httpResponse.getStatusCode() + ", Response Text: " + httpResponse.getStatusText(), e3);
        }
    }

    AmazonServiceException handleErrorResponse(Request<?> request, HttpResponseHandler<AmazonServiceException> httpResponseHandler, HttpResponse httpResponse) throws IOException {
        AmazonServiceException amazonServiceException;
        int statusCode = httpResponse.getStatusCode();
        try {
            amazonServiceException = httpResponseHandler.mo5855handle(httpResponse);
            Log log2 = REQUEST_LOG;
            log2.debug("Received error response: " + amazonServiceException.toString());
        } catch (Exception e) {
            if (statusCode == 413) {
                amazonServiceException = new AmazonServiceException("Request entity too large");
                amazonServiceException.setServiceName(request.getServiceName());
                amazonServiceException.setStatusCode(413);
                amazonServiceException.setErrorType(AmazonServiceException.ErrorType.Client);
                amazonServiceException.setErrorCode("Request entity too large");
            } else if (statusCode == 503 && "Service Unavailable".equalsIgnoreCase(httpResponse.getStatusText())) {
                amazonServiceException = new AmazonServiceException("Service unavailable");
                amazonServiceException.setServiceName(request.getServiceName());
                amazonServiceException.setStatusCode(503);
                amazonServiceException.setErrorType(AmazonServiceException.ErrorType.Service);
                amazonServiceException.setErrorCode("Service unavailable");
            } else if (e instanceof IOException) {
                throw ((IOException) e);
            } else {
                throw new AmazonClientException("Unable to unmarshall error response (" + e.getMessage() + "). Response Code: " + statusCode + ", Response Text: " + httpResponse.getStatusText() + ", Response Headers: " + httpResponse.getHeaders(), e);
            }
        }
        amazonServiceException.setStatusCode(statusCode);
        amazonServiceException.setServiceName(request.getServiceName());
        amazonServiceException.fillInStackTrace();
        return amazonServiceException;
    }

    private long pauseBeforeNextRetry(AmazonWebServiceRequest amazonWebServiceRequest, AmazonClientException amazonClientException, int i, RetryPolicy retryPolicy) {
        int i2 = (i - 1) - 1;
        long delayBeforeNextRetry = retryPolicy.getBackoffStrategy().delayBeforeNextRetry(amazonWebServiceRequest, amazonClientException, i2);
        if (log.isDebugEnabled()) {
            log.debug("Retriable error detected, will retry in " + delayBeforeNextRetry + "ms, attempt number: " + i2);
        }
        try {
            Thread.sleep(delayBeforeNextRetry);
            return delayBeforeNextRetry;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AmazonClientException(e.getMessage(), e);
        }
    }

    private String getServerDateFromException(String str) {
        int indexOf;
        int indexOf2 = str.indexOf("(");
        if (str.contains(" + 15")) {
            indexOf = str.indexOf(" + 15");
        } else {
            indexOf = str.indexOf(" - 15");
        }
        return str.substring(indexOf2 + 1, indexOf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v11, types: [int] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v4 */
    int parseClockSkewOffset(HttpResponse httpResponse, AmazonServiceException amazonServiceException) {
        Date parseRFC822Date;
        Date date = new Date();
        String str = httpResponse.getHeaders().get("Date");
        String str2 = null;
        try {
            if (str != 0) {
                try {
                    if (!str.isEmpty()) {
                        parseRFC822Date = DateUtils.parseRFC822Date(str);
                        str = (int) ((date.getTime() - parseRFC822Date.getTime()) / 1000);
                        return str;
                    }
                } catch (RuntimeException e) {
                    e = e;
                    Log log2 = log;
                    log2.warn("Unable to parse clock skew offset from response: " + str2, e);
                    return 0;
                }
            }
            parseRFC822Date = DateUtils.parseCompressedISO8601Date(getServerDateFromException(amazonServiceException.getMessage()));
            str = (int) ((date.getTime() - parseRFC822Date.getTime()) / 1000);
            return str;
        } catch (RuntimeException e2) {
            e = e2;
            str2 = str;
        }
    }

    protected void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }

    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }
}
