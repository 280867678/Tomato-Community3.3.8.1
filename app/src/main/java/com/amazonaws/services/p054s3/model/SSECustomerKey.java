package com.amazonaws.services.p054s3.model;

/* renamed from: com.amazonaws.services.s3.model.SSECustomerKey */
/* loaded from: classes2.dex */
public class SSECustomerKey {
    private String algorithm;
    private final String base64EncodedKey = null;
    private String base64EncodedMd5;

    private SSECustomerKey() {
    }

    public String getKey() {
        return this.base64EncodedKey;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String str) {
        this.algorithm = str;
    }

    public SSECustomerKey withAlgorithm(String str) {
        setAlgorithm(str);
        return this;
    }

    public String getMd5() {
        return this.base64EncodedMd5;
    }

    public static SSECustomerKey generateSSECustomerKeyForPresignUrl(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        SSECustomerKey sSECustomerKey = new SSECustomerKey();
        sSECustomerKey.withAlgorithm(str);
        return sSECustomerKey;
    }
}
