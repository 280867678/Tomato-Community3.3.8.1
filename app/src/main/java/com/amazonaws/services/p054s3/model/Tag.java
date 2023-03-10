package com.amazonaws.services.p054s3.model;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.Tag */
/* loaded from: classes2.dex */
public class Tag implements Serializable {
    private String key;
    private String value;

    public Tag(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public Tag withKey(String str) {
        setKey(str);
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public Tag withValue(String str) {
        setValue(str);
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Tag.class != obj.getClass()) {
            return false;
        }
        Tag tag = (Tag) obj;
        String str = this.key;
        if (str == null ? tag.key != null : !str.equals(tag.key)) {
            return false;
        }
        String str2 = this.value;
        String str3 = tag.value;
        return str2 != null ? str2.equals(str3) : str3 == null;
    }

    public int hashCode() {
        String str = this.key;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.value;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }
}
