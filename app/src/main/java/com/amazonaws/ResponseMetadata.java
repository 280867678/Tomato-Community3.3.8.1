package com.amazonaws;

import com.j256.ormlite.logger.Logger;
import java.util.Map;

/* loaded from: classes2.dex */
public class ResponseMetadata {
    protected final Map<String, String> metadata;

    public ResponseMetadata(Map<String, String> map) {
        this.metadata = map;
    }

    public String getRequestId() {
        return this.metadata.get("AWS_REQUEST_ID");
    }

    public String toString() {
        Map<String, String> map = this.metadata;
        return map == null ? Logger.ARG_STRING : map.toString();
    }
}
