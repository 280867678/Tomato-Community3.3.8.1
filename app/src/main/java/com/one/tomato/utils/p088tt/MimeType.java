package com.one.tomato.utils.p088tt;

import android.support.p002v4.util.ArraySet;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/* renamed from: com.one.tomato.utils.tt.MimeType */
/* loaded from: classes3.dex */
public enum MimeType {
    JPEG("image/jpeg", arraySetOf("jpg", "jpeg")),
    PNG("image/png", arraySetOf("png")),
    GIF("image/gif", arraySetOf("gif")),
    BMP("image/x-ms-bmp", arraySetOf("bmp")),
    WEBP("image/webp", arraySetOf("webp")),
    MPEG("video/mpeg", arraySetOf("mpeg", "mpg")),
    MP4("video/mp4", arraySetOf("mp4", "m4v")),
    QUICKTIME("video/quicktime", arraySetOf("mov")),
    THREEGPP("video/3gpp", arraySetOf("3gp", "3gpp")),
    THREEGPP2("video/3gpp2", arraySetOf("3g2", "3gpp2")),
    MKV("video/x-matroska", arraySetOf("mkv")),
    WEBM("video/webm", arraySetOf("webm")),
    TS("video/mp2ts", arraySetOf("ts")),
    AVI("video/avi", arraySetOf("avi"));
    
    private final Set<String> mExtensions;
    private final String mMimeTypeName;

    MimeType(String str, Set set) {
        this.mMimeTypeName = str;
        this.mExtensions = set;
    }

    public static Set<MimeType> ofAll() {
        return EnumSet.allOf(MimeType.class);
    }

    /* renamed from: of */
    public static Set<MimeType> m3782of(MimeType mimeType, MimeType... mimeTypeArr) {
        return EnumSet.of(mimeType, mimeTypeArr);
    }

    public static Set<MimeType> ofImage() {
        return EnumSet.of(JPEG, PNG, GIF, BMP, WEBP);
    }

    public static Set<MimeType> ofVideo() {
        return EnumSet.of(MPEG, MP4, QUICKTIME, THREEGPP, THREEGPP2, MKV, WEBM, TS, AVI);
    }

    private static Set<String> arraySetOf(String... strArr) {
        return new ArraySet(Arrays.asList(strArr));
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.mMimeTypeName;
    }
}
