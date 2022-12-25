package com.amazonaws.services.p054s3.internal;

import com.tomatolive.library.utils.ConstantUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Marker;

/* renamed from: com.amazonaws.services.s3.internal.S3HttpUtils */
/* loaded from: classes2.dex */
public final class S3HttpUtils {
    private static final Pattern ENCODED_CHARACTERS_PATTERN = Pattern.compile(Pattern.quote(Marker.ANY_NON_NULL_MARKER) + "|" + Pattern.quote(Marker.ANY_MARKER) + "|" + Pattern.quote("%7E") + "|" + Pattern.quote("%2F") + "|" + Pattern.quote("%3A") + "|" + Pattern.quote("%27") + "|" + Pattern.quote("%28") + "|" + Pattern.quote("%29") + "|" + Pattern.quote("%21") + "|" + Pattern.quote("%5B") + "|" + Pattern.quote("%5D") + "|" + Pattern.quote("%24"));

    public static String urlEncode(String str, boolean z) {
        if (str == null) {
            return "";
        }
        try {
            String encode = URLEncoder.encode(str, "UTF-8");
            Matcher matcher = ENCODED_CHARACTERS_PATTERN.matcher(encode);
            StringBuffer stringBuffer = new StringBuffer(encode.length());
            while (matcher.find()) {
                String group = matcher.group(0);
                if (Marker.ANY_NON_NULL_MARKER.equals(group)) {
                    group = ConstantUtils.PLACEHOLDER_STR_ONE;
                } else if (Marker.ANY_MARKER.equals(group)) {
                    group = "%2A";
                } else if ("%7E".equals(group)) {
                    group = "~";
                } else if (z && "%2F".equals(group)) {
                    group = "/";
                } else if (z && "%3A".equals(group)) {
                    group = ":";
                } else if (z && "%27".equals(group)) {
                    group = "'";
                } else if (z && "%28".equals(group)) {
                    group = "(";
                } else if (z && "%29".equals(group)) {
                    group = ")";
                } else if (z && "%21".equals(group)) {
                    group = "!";
                } else if (z && "%5B".equals(group)) {
                    group = "[";
                } else if (z && "%5D".equals(group)) {
                    group = "]";
                }
                matcher.appendReplacement(stringBuffer, group);
            }
            matcher.appendTail(stringBuffer);
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlDecode(String str) {
        if (str == null) {
            return null;
        }
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
