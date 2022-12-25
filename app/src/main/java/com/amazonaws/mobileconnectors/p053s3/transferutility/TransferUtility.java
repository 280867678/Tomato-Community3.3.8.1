package com.amazonaws.mobileconnectors.p053s3.transferutility;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.RequestClientOptions;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.VersionInfoUtils;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility */
/* loaded from: classes2.dex */
public class TransferUtility {
    private static final Object LOCK = new Object();
    private static String userAgentFromConfig = "";

    static {
        LogFactory.getLog(TransferUtility.class);
    }

    private static String getUserAgentFromConfig() {
        synchronized (LOCK) {
            if (userAgentFromConfig != null && !userAgentFromConfig.trim().isEmpty()) {
                return userAgentFromConfig.trim() + "/";
            }
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <X extends AmazonWebServiceRequest> X appendTransferServiceUserAgentString(X x) {
        RequestClientOptions requestClientOptions = x.getRequestClientOptions();
        requestClientOptions.appendUserAgent("TransferService/" + getUserAgentFromConfig() + VersionInfoUtils.getVersion());
        return x;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <X extends AmazonWebServiceRequest> X appendMultipartTransferServiceUserAgentString(X x) {
        RequestClientOptions requestClientOptions = x.getRequestClientOptions();
        requestClientOptions.appendUserAgent("TransferService_multipart/" + getUserAgentFromConfig() + VersionInfoUtils.getVersion());
        return x;
    }
}
