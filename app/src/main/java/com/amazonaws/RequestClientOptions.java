package com.amazonaws;

import com.tomatolive.library.utils.ConstantUtils;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class RequestClientOptions {
    private final Map<Marker, String> markers = new EnumMap(Marker.class);

    /* loaded from: classes2.dex */
    public enum Marker {
        USER_AGENT
    }

    public String getClientMarker(Marker marker) {
        return this.markers.get(marker);
    }

    public void putClientMarker(Marker marker, String str) {
        this.markers.put(marker, str);
    }

    public void appendUserAgent(String str) {
        String str2 = this.markers.get(Marker.USER_AGENT);
        if (str2 == null) {
            str2 = "";
        }
        putClientMarker(Marker.USER_AGENT, createUserAgentMarkerString(str2, str));
    }

    private String createUserAgentMarkerString(String str, String str2) {
        if (str.contains(str2)) {
            return str;
        }
        return str + ConstantUtils.PLACEHOLDER_STR_ONE + str2;
    }
}
