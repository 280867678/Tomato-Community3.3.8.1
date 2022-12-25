package com.facebook.common.media;

import com.facebook.common.internal.ImmutableMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class MediaUtils {
    public static final Map<String, String> ADDITIONAL_ALLOWED_MIME_TYPES = ImmutableMap.m4166of("mkv", "video/x-matroska", "glb", "model/gltf-binary");

    public static boolean isVideo(String str) {
        return str != null && str.startsWith("video/");
    }

    public static String extractMime(String str) {
        String extractExtension = extractExtension(str);
        if (extractExtension == null) {
            return null;
        }
        String lowerCase = extractExtension.toLowerCase(Locale.US);
        String mimeTypeFromExtension = MimeTypeMapWrapper.getMimeTypeFromExtension(lowerCase);
        return mimeTypeFromExtension == null ? ADDITIONAL_ALLOWED_MIME_TYPES.get(lowerCase) : mimeTypeFromExtension;
    }

    private static String extractExtension(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf < 0 || lastIndexOf == str.length() - 1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }
}