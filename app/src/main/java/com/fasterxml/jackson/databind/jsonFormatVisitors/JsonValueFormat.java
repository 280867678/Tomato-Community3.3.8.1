package com.fasterxml.jackson.databind.jsonFormatVisitors;

import android.support.p002v4.app.NotificationCompat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sensorsdata.analytics.android.sdk.AopConstants;

/* loaded from: classes2.dex */
public enum JsonValueFormat {
    COLOR("color"),
    DATE("date"),
    DATE_TIME("date-time"),
    EMAIL(NotificationCompat.CATEGORY_EMAIL),
    HOST_NAME("host-name"),
    IP_ADDRESS("ip-address"),
    IPV6("ipv6"),
    PHONE("phone"),
    REGEX("regex"),
    STYLE("style"),
    TIME(AopConstants.TIME_KEY),
    URI("uri"),
    UTC_MILLISEC("utc-millisec");
    
    private final String _desc;

    JsonValueFormat(String str) {
        this._desc = str;
    }

    @Override // java.lang.Enum
    @JsonValue
    public String toString() {
        return this._desc;
    }
}
