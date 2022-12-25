package com.youdao.sdk.common;

/* loaded from: classes4.dex */
public final class HttpResponses {
    public static String asResponseString(DownloadResponse downloadResponse) {
        if (downloadResponse == null) {
            return null;
        }
        try {
            return new String(downloadResponse.getByteArray(), "UTF-8");
        } catch (Exception unused) {
            return null;
        }
    }
}
