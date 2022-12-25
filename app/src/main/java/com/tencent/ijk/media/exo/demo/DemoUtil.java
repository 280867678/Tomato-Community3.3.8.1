package com.tencent.ijk.media.exo.demo;

import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.Locale;

/* loaded from: classes3.dex */
final class DemoUtil {
    public static String buildTrackName(Format format) {
        String joinWithSeparator = MimeTypes.isVideo(format.sampleMimeType) ? joinWithSeparator(joinWithSeparator(joinWithSeparator(buildResolutionString(format), buildBitrateString(format)), buildTrackIdString(format)), buildSampleMimeTypeString(format)) : MimeTypes.isAudio(format.sampleMimeType) ? joinWithSeparator(joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildAudioPropertyString(format)), buildBitrateString(format)), buildTrackIdString(format)), buildSampleMimeTypeString(format)) : joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildBitrateString(format)), buildTrackIdString(format)), buildSampleMimeTypeString(format));
        return joinWithSeparator.length() == 0 ? "unknown" : joinWithSeparator;
    }

    private static String buildResolutionString(Format format) {
        if (format.width == -1 || format.height == -1) {
            return "";
        }
        return format.width + "x" + format.height;
    }

    private static String buildAudioPropertyString(Format format) {
        if (format.channelCount == -1 || format.sampleRate == -1) {
            return "";
        }
        return format.channelCount + "ch, " + format.sampleRate + "Hz";
    }

    private static String buildLanguageString(Format format) {
        return (TextUtils.isEmpty(format.language) || "und".equals(format.language)) ? "" : format.language;
    }

    private static String buildBitrateString(Format format) {
        int i = format.bitrate;
        return i == -1 ? "" : String.format(Locale.US, "%.2fMbit", Float.valueOf(i / 1000000.0f));
    }

    private static String joinWithSeparator(String str, String str2) {
        if (str.length() == 0) {
            return str2;
        }
        if (str2.length() == 0) {
            return str;
        }
        return str + ", " + str2;
    }

    private static String buildTrackIdString(Format format) {
        if (format.f1312id == null) {
            return "";
        }
        return "id:" + format.f1312id;
    }

    private static String buildSampleMimeTypeString(Format format) {
        String str = format.sampleMimeType;
        return str == null ? "" : str;
    }

    private DemoUtil() {
    }
}
