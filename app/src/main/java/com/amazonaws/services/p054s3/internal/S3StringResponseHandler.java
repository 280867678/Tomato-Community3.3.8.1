package com.amazonaws.services.p054s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.StringUtils;
import java.io.InputStream;

/* renamed from: com.amazonaws.services.s3.internal.S3StringResponseHandler */
/* loaded from: classes2.dex */
public class S3StringResponseHandler extends AbstractS3ResponseHandler<String> {
    @Override // com.amazonaws.http.HttpResponseHandler
    /* renamed from: handle */
    public AmazonWebServiceResponse<String> mo5855handle(HttpResponse httpResponse) throws Exception {
        AmazonWebServiceResponse<String> parseResponseMetadata = parseResponseMetadata(httpResponse);
        byte[] bArr = new byte[1024];
        StringBuilder sb = new StringBuilder();
        InputStream content = httpResponse.getContent();
        while (true) {
            int read = content.read(bArr);
            if (read > 0) {
                sb.append(new String(bArr, 0, read, StringUtils.UTF8));
            } else {
                parseResponseMetadata.setResult(sb.toString());
                return parseResponseMetadata;
            }
        }
    }
}
