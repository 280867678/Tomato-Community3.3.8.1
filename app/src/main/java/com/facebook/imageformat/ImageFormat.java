package com.facebook.imageformat;

/* loaded from: classes2.dex */
public class ImageFormat {
    public static final ImageFormat UNKNOWN = new ImageFormat("UNKNOWN", null);
    private final String mName;

    /* loaded from: classes2.dex */
    public interface FormatChecker {
        ImageFormat determineFormat(byte[] bArr, int i);

        int getHeaderSize();
    }

    public ImageFormat(String str, String str2) {
        this.mName = str;
    }

    public String toString() {
        return getName();
    }

    public String getName() {
        return this.mName;
    }
}
