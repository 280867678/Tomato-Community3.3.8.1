package com.amazonaws.services.p054s3.model;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.amazonaws.services.s3.model.TagSet */
/* loaded from: classes2.dex */
public class TagSet {
    private Map<String, String> tags = new HashMap(1);

    public TagSet() {
    }

    public TagSet(Map<String, String> map) {
        this.tags.putAll(map);
    }

    public Map<String, String> getAllTags() {
        return this.tags;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append("Tags: " + getAllTags());
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
