package com.amazonaws.services.p054s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.transform.Unmarshaller;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.internal.ResponseHeaderHandlerChain */
/* loaded from: classes2.dex */
public class ResponseHeaderHandlerChain<T> extends S3XmlResponseHandler<T> {
    private final List<HeaderHandler<T>> headerHandlers;

    public ResponseHeaderHandlerChain(Unmarshaller<T, InputStream> unmarshaller, HeaderHandler<T>... headerHandlerArr) {
        super(unmarshaller);
        this.headerHandlers = Arrays.asList(headerHandlerArr);
    }

    @Override // com.amazonaws.services.p054s3.internal.S3XmlResponseHandler, com.amazonaws.http.HttpResponseHandler
    /* renamed from: handle */
    public AmazonWebServiceResponse<T> mo5855handle(HttpResponse httpResponse) throws Exception {
        AmazonWebServiceResponse<T> mo5855handle = super.mo5855handle(httpResponse);
        T result = mo5855handle.getResult();
        if (result != null) {
            for (HeaderHandler<T> headerHandler : this.headerHandlers) {
                headerHandler.handle(result, httpResponse);
            }
        }
        return mo5855handle;
    }
}
