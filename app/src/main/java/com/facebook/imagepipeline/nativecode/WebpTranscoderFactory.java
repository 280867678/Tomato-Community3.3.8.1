package com.facebook.imagepipeline.nativecode;

/* loaded from: classes2.dex */
public class WebpTranscoderFactory {
    private static WebpTranscoder sWebpTranscoder;

    static {
        try {
            sWebpTranscoder = (WebpTranscoder) Class.forName("com.facebook.imagepipeline.nativecode.WebpTranscoderImpl").newInstance();
        } catch (Throwable unused) {
        }
    }

    public static WebpTranscoder getWebpTranscoder() {
        return sWebpTranscoder;
    }
}
