package com.amazonaws.services.p054s3.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.services.p054s3.S3ResponseMetadata;
import com.amazonaws.services.p054s3.model.ObjectMetadata;
import com.amazonaws.util.DateUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* renamed from: com.amazonaws.services.s3.internal.AbstractS3ResponseHandler */
/* loaded from: classes2.dex */
public abstract class AbstractS3ResponseHandler<T> implements HttpResponseHandler<AmazonWebServiceResponse<T>> {
    private static final Log log = LogFactory.getLog(S3MetadataResponseHandler.class);
    private static final Set<String> IGNORED_HEADERS = new HashSet();

    @Override // com.amazonaws.http.HttpResponseHandler
    public boolean needsConnectionLeftOpen() {
        return false;
    }

    static {
        IGNORED_HEADERS.add("Date");
        IGNORED_HEADERS.add("Server");
        IGNORED_HEADERS.add("x-amz-request-id");
        IGNORED_HEADERS.add("x-amz-id-2");
        IGNORED_HEADERS.add("X-Amz-Cf-Id");
        IGNORED_HEADERS.add("Connection");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AmazonWebServiceResponse<T> parseResponseMetadata(HttpResponse httpResponse) {
        AmazonWebServiceResponse<T> amazonWebServiceResponse = new AmazonWebServiceResponse<>();
        HashMap hashMap = new HashMap();
        hashMap.put("AWS_REQUEST_ID", httpResponse.getHeaders().get("x-amz-request-id"));
        hashMap.put("HOST_ID", httpResponse.getHeaders().get("x-amz-id-2"));
        hashMap.put("CLOUD_FRONT_ID", httpResponse.getHeaders().get("X-Amz-Cf-Id"));
        amazonWebServiceResponse.setResponseMetadata(new S3ResponseMetadata(hashMap));
        return amazonWebServiceResponse;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void populateObjectMetadata(HttpResponse httpResponse, ObjectMetadata objectMetadata) {
        for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("x-amz-meta-")) {
                objectMetadata.addUserMetadata(key.substring(11), entry.getValue());
            } else if (IGNORED_HEADERS.contains(key)) {
                log.debug(String.format("%s is ignored.", key));
            } else if (key.equalsIgnoreCase("Last-Modified")) {
                try {
                    objectMetadata.setHeader(key, ServiceUtils.parseRfc822Date(entry.getValue()));
                } catch (Exception e) {
                    Log log2 = log;
                    log2.warn("Unable to parse last modified date: " + entry.getValue(), e);
                }
            } else if (key.equalsIgnoreCase("Content-Length")) {
                try {
                    objectMetadata.setHeader(key, Long.valueOf(Long.parseLong(entry.getValue())));
                } catch (NumberFormatException e2) {
                    Log log3 = log;
                    log3.warn("Unable to parse content length: " + entry.getValue(), e2);
                }
            } else if (key.equalsIgnoreCase("ETag")) {
                objectMetadata.setHeader(key, ServiceUtils.removeQuotes(entry.getValue()));
            } else if (key.equalsIgnoreCase("Expires")) {
                try {
                    objectMetadata.setHttpExpiresDate(DateUtils.parseRFC822Date(entry.getValue()));
                } catch (Exception e3) {
                    Log log4 = log;
                    log4.warn("Unable to parse http expiration date: " + entry.getValue(), e3);
                }
            } else if (key.equalsIgnoreCase("x-amz-expiration")) {
                new ObjectExpirationHeaderHandler().handle((ObjectExpirationHeaderHandler) objectMetadata, httpResponse);
            } else if (key.equalsIgnoreCase("x-amz-restore")) {
                new ObjectRestoreHeaderHandler().handle((ObjectRestoreHeaderHandler) objectMetadata, httpResponse);
            } else if (key.equalsIgnoreCase("x-amz-request-charged")) {
                new S3RequesterChargedHeaderHandler().handle((S3RequesterChargedHeaderHandler) objectMetadata, httpResponse);
            } else if (key.equalsIgnoreCase("x-amz-mp-parts-count")) {
                try {
                    objectMetadata.setHeader(key, Integer.valueOf(Integer.parseInt(entry.getValue())));
                } catch (NumberFormatException e4) {
                    throw new AmazonClientException("Unable to parse part count. Header x-amz-mp-parts-count has corrupted data" + e4.getMessage(), e4);
                }
            } else {
                objectMetadata.setHeader(key, entry.getValue());
            }
        }
    }
}
