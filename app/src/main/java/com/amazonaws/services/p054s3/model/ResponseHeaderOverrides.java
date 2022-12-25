package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;

/* renamed from: com.amazonaws.services.s3.model.ResponseHeaderOverrides */
/* loaded from: classes2.dex */
public class ResponseHeaderOverrides extends AmazonWebServiceRequest {
    public String getCacheControl() {
        throw null;
    }

    static {
        new String[]{"response-cache-control", "response-content-disposition", "response-content-encoding", "response-content-language", "response-content-type", "response-expires"};
    }
}
